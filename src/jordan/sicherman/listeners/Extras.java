/**
 * 
 */
package jordan.sicherman.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.TemperatureManager;
import jordan.sicherman.utilities.TemperatureManager.TemperatureState;
import jordan.sicherman.utilities.ThirstManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Jordan
 * 
 */
public class Extras implements Listener {

	private static final Random random = new Random();

	@EventHandler(priority = EventPriority.MONITOR)
	private void onAirDrop(EntityChangeBlockEvent e) {
		if (!Utilities.inWorld(e.getBlock().getLocation())) { return; }

		if (e.getEntity() instanceof FallingBlock) {
			FallingBlock fall = (FallingBlock) e.getEntity();

			if (fall.hasMetadata("MyZ.airdrop")) {
				List<String> possible_chests = ConfigEntries.AIRDROP_CHESTS.<List<String>> getValue();

				if (random.nextDouble() <= 0.3) {
					fall.getWorld().createExplosion(fall.getLocation().getX(), fall.getLocation().getY(), fall.getLocation().getZ(),
							ConfigEntries.AIRDROP_EXPLOSION_MAGNITUDE.<Double> getValue().floatValue(),
							ConfigEntries.AIRDROP_FIRE.<Boolean> getValue(), ConfigEntries.AIRDROP_BREAK.<Boolean> getValue());
				}

				if (fall.getMaterial() == Material.CHEST) {
					final ChestType type = ChestType.fromString(possible_chests.get(random.nextInt(possible_chests.size())));

					if (type != null) {
						final Block chest = e.getBlock();
						MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
							@Override
							@SuppressWarnings("deprecation")
							public void run() {
								chest.setMetadata("MyZ.airdrop", new FixedMetadataValue(MyZ.instance, true));
								chest.setTypeIdAndData(Material.CHEST.getId(), (byte) random.nextInt(5), true);
								((Chest) chest.getState()).getBlockInventory().setContents(type.generate());
								CompatibilityManager.renameChest((Chest) chest.getState(), type.getName());
							}
						}, 1L);
					}
				}

				if (!ConfigEntries.AIRDROP_WRECKAGE.<Boolean> getValue()) {
					fall.setDropItem(false);
					fall.remove();
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onGrenadeLand(PlayerTeleportEvent e) {
		if (!Utilities.inWorld(e.getPlayer()) || !MyZ.isPremium()) { return; }

		if (ConfigEntries.EXPLOSIVE_PEARLS.<Boolean> getValue() && e.getCause() == TeleportCause.ENDER_PEARL) {
			e.setCancelled(true);
			e.getTo().getWorld().createExplosion(e.getTo().getX(), e.getTo().getY(), e.getTo().getZ(), 1.0f, false, false);
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	private void onNaturalRegen(EntityRegainHealthEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (!ConfigEntries.NATURAL_REGEN.<Boolean> getValue() && e.getEntityType() == EntityType.PLAYER
				&& e.getRegainReason() == RegainReason.SATIATED) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onZombieCombust(EntityCombustEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getEntityType() == EntityType.SKELETON) {
			e.setCancelled(true);
			return;
		}

		if (ConfigEntries.ZOMBIES_BURN.<Boolean> getValue()) { return; }

		if (e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.PIG_ZOMBIE || e.getEntityType() == EntityType.GIANT) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onZombieTakeFallDamage(EntityDamageEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (ConfigEntries.NO_ZOMBIE_FALL.<Boolean> getValue() && e.getCause() == DamageCause.FALL
				&& (e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.GIANT)) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	private void onWitherAway(EntityDamageEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (ConfigEntries.PREVENT_WITHERING.<Boolean> getValue() && e.getCause() == DamageCause.WITHER
				&& e.getEntityType() == EntityType.PLAYER) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onMobDeath(EntityDeathEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		e.setDroppedExp(0);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onMobSpawn(CreatureSpawnEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getSpawnReason() == SpawnReason.SPAWNER_EGG && e.getEntityType() == EntityType.MUSHROOM_COW) {
			e.setCancelled(true);
			CompatibilityManager.spawnEntity(e.getLocation(), EntityType.GIANT);
		}

		int maximum_z, minimum_z;

		switch (e.getEntityType()) {
		case ZOMBIE:
			if (!ConfigEntries.ZOMBIE_LIMITER.<Boolean> getValue()) { return; }

			maximum_z = ConfigEntries.ZOMBIE_MAX_Z.<Integer> getValue();
			minimum_z = ConfigEntries.ZOMBIE_MIN_Z.<Integer> getValue();
			break;
		case PIG_ZOMBIE:
			if (!ConfigEntries.PIGMAN_LIMITER.<Boolean> getValue()) { return; }

			maximum_z = ConfigEntries.PIGMAN_MAX_Z.<Integer> getValue();
			minimum_z = ConfigEntries.PIGMAN_MIN_Z.<Integer> getValue();
			break;
		case GIANT:
			if (!ConfigEntries.GIANT_LIMITER.<Boolean> getValue()) { return; }

			maximum_z = ConfigEntries.GIANT_MAX_Z.<Integer> getValue();
			minimum_z = ConfigEntries.GIANT_MIN_Z.<Integer> getValue();
			break;
		case SKELETON:
			if (!ConfigEntries.GUARD_LIMITER.<Boolean> getValue()) { return; }

			maximum_z = ConfigEntries.GUARD_MAX_Z.<Integer> getValue();
			minimum_z = ConfigEntries.GUARD_MIN_Z.<Integer> getValue();
			break;
		default:
			return;
		}

		int z = e.getLocation().getBlockZ();

		if (z < minimum_z || z > maximum_z) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onGiantDamage(EntityDamageByEntityEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getEntityType() == EntityType.GIANT) {
			if (e.getDamager().getType() == EntityType.ARROW || e.getCause() == DamageCause.ENTITY_EXPLOSION
					|| e.getCause() == DamageCause.BLOCK_EXPLOSION) {
				e.setCancelled(true);
			} else if (e.getCause() == DamageCause.FALL && ConfigEntries.GIANT_EXPLODE_MAGNITUDE.<Double> getValue() > 0.0) {
				e.getEntity()
						.getWorld()
						.createExplosion(e.getEntity().getLocation(),
								ConfigEntries.GIANT_EXPLODE_MAGNITUDE.<Double> getValue().floatValue());
				if (ConfigEntries.GIANT_SLOWNESS_RADIUS.<Integer> getValue() > 0) {
					for (Player player : Utilities.getPlayersInRange(e.getEntity().getLocation(),
							ConfigEntries.GIANT_SLOWNESS_RADIUS.<Integer> getValue())) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 30, 0));
					}
				}
			} else if (e.getDamager() instanceof Player && ConfigEntries.GIANT_REINFORCEMENT_CHANCE.<Integer> getValue() > 0) {
				if (random.nextInt(100) + 1 <= ConfigEntries.GIANT_REINFORCEMENT_CHANCE.<Integer> getValue()) {
					int max = ConfigEntries.GIANT_REINFORCEMENT_MAX.<Integer> getValue();
					int min = ConfigEntries.GIANT_REINFORCEMENT_MIN.<Integer> getValue();
					int amount = random.nextInt(max - min + 1) + min;
					while (amount > 0) {
						double xoff = random.nextDouble() * random.nextInt(2) == 0 ? -(3 + random.nextDouble()) : 3 + random.nextDouble();
						double yoff = random.nextDouble() * random.nextInt(2) == 0 ? -(3 + random.nextDouble()) : 3 + random.nextDouble();
						double zoff = random.nextDouble() * random.nextInt(2) == 0 ? -(3 + random.nextDouble()) : 3 + random.nextDouble();
						CompatibilityManager.spawnEntity(e.getEntity().getLocation().clone().add(xoff, yoff, zoff), EntityType.ZOMBIE);
						amount--;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onBlock(PlayerInteractEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) { return; }

		if (e.getPlayer().getItemInHand() != null && ItemUtilities.getInstance().hasTag(e.getPlayer().getItemInHand(), ItemTag.THERMOMETER)) {
			ItemStack item = e.getPlayer().getItemInHand();
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>(ItemTag.THERMOMETER.getLore());

			TemperatureState state = TemperatureManager.getInstance().getState(e.getPlayer());
			double temperature = Math.round(DataWrapper.<Double> get(e.getPlayer(), UserEntries.TEMPERATURE) * 100.0) / 100.0;

			switch (state) {
			case HEATSTROKE_1:
				lore.add(LocaleMessage.THERMOMETER_HEAT1.filter(temperature).toString());
				break;
			case HEATSTROKE_2:
				lore.add(LocaleMessage.THERMOMETER_HEAT2.filter(temperature).toString());
				break;
			case HEATSTROKE_3:
				lore.add(LocaleMessage.THERMOMETER_HEAT3.filter(temperature).toString());
				break;
			case HYPOTHERMIA_1:
				lore.add(LocaleMessage.THERMOMETER_HYPO1.filter(temperature).toString());
				break;
			case HYPOTHERMIA_2:
				lore.add(LocaleMessage.THERMOMETER_HYPO1.filter(temperature).toString());
				break;
			case HYPOTHERMIA_3:
				lore.add(LocaleMessage.THERMOMETER_HYPO1.filter(temperature).toString());
				break;
			case NORMAL:
				lore.add(LocaleMessage.THERMOMETER_NORMAL.filter(temperature).toString());
				break;
			case SHIVERING:
				lore.add(LocaleMessage.THERMOMETER_SHIVERING.filter(temperature).toString());
				break;
			case SWEATING:
				lore.add(LocaleMessage.THERMOMETER_SWEATING.filter(temperature).toString());
				break;
			default:
				break;
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			e.setCancelled(true);
			return;
		}

		if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getItemInHand() != null) {
			switch (e.getPlayer().getItemInHand().getType()) {
			case WOOD_SWORD:
			case STONE_SWORD:
			case GOLD_SWORD:
			case IRON_SWORD:
			case DIAMOND_SWORD:
				if (e.getPlayer().isSneaking()) {
					Utilities.addFriendOnSneak(e.getPlayer());
				}
				break;
			default:
				return;
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getItemInHand() != null) {
			Material type = e.getClickedBlock().getType();
			if (type == Material.WATER || type == Material.CAULDRON || type == Material.CAULDRON_ITEM || type == Material.STATIONARY_WATER) {
				if (e.getPlayer().getItemInHand().getType() == Material.GLASS_BOTTLE) {
					e.setCancelled(true);
					switch (e.getClickedBlock().getBiome()) {
					case FROZEN_OCEAN:
					case COLD_BEACH:
					case FROZEN_RIVER:
					case COLD_TAIGA:
					case COLD_TAIGA_HILLS:
					case COLD_TAIGA_MOUNTAINS:
					case ICE_MOUNTAINS:
					case ICE_PLAINS:
					case ICE_PLAINS_SPIKES:
					case MEGA_SPRUCE_TAIGA:
					case MEGA_SPRUCE_TAIGA_HILLS:
					case MEGA_TAIGA:
					case MEGA_TAIGA_HILLS:
					case TAIGA:
					case TAIGA_HILLS:
					case TAIGA_MOUNTAINS:
					case EXTREME_HILLS:
						e.getPlayer().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.COLD_WATER, 1));
						break;
					case DEEP_OCEAN:
					case OCEAN:
					case BEACH:
					case STONE_BEACH:
						e.getPlayer().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.SALTY_WATER, 1));
						break;
					case DESERT:
					case DESERT_HILLS:
					case DESERT_MOUNTAINS:
					case MESA:
					case MESA_BRYCE:
					case MESA_PLATEAU:
					case MESA_PLATEAU_FOREST:
					case MESA_PLATEAU_FOREST_MOUNTAINS:
					case MESA_PLATEAU_MOUNTAINS:
					case SAVANNA:
					case SAVANNA_PLATEAU:
					case SAVANNA_PLATEAU_MOUNTAINS:
					case SAVANNA_MOUNTAINS:
						if (e.getPlayer().getWorld().getTime() < 13000) {
							e.getPlayer().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.WARM_WATER, 1));
						} else {
							e.getPlayer().setItemInHand(new ItemStack(Material.POTION, 1));
						}
						break;
					case JUNGLE:
					case JUNGLE_EDGE:
					case JUNGLE_EDGE_MOUNTAINS:
					case JUNGLE_HILLS:
					case JUNGLE_MOUNTAINS:
					case RIVER:
					case SWAMPLAND:
					case SWAMPLAND_MOUNTAINS:
						e.getPlayer().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.MURKY_WATER, 1));
						break;
					default:
						e.getPlayer().setItemInHand(new ItemStack(Material.POTION, 1));
						break;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onFoodConsume(PlayerItemConsumeEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		final Player playerFor = e.getPlayer();
		ItemStack item = e.getItem();

		if (ConfigEntries.ENHANCE_FOOD.<Boolean> getValue() && ItemUtilities.getInstance().isFood(item)) {
			if (playerFor.getHealth() < playerFor.getMaxHealth()) {
				playerFor.setHealth(playerFor.getHealth() + 1.0 > playerFor.getMaxHealth() ? playerFor.getMaxHealth() : playerFor
						.getHealth() + 1.0);
			}
		} else if (item.getType() == Material.POTION && item.getDurability() == (short) 0) {
			if (playerFor.getLevel() < ConfigEntries.THIRST_MAX_DEFAULT.<Integer> getValue()) {
				ThirstManager.getInstance().replenish(playerFor, ItemUtilities.getInstance().hasTag(item, ItemTag.SALTY_WATER));
			}
			if (ItemUtilities.getInstance().hasTag(item, ItemTag.MURKY_WATER)) {
				Utilities.setPoisoned(playerFor, true, false);
			} else if (ItemUtilities.getInstance().hasTag(item, ItemTag.WARM_WATER)) {
				DataWrapper.set(playerFor, UserEntries.TEMPERATURE, DataWrapper.<Double> get(playerFor, UserEntries.TEMPERATURE)
						+ ConfigEntries.WARM_WATER_TEMPERATURE.<Double> getValue());
			} else if (ItemUtilities.getInstance().hasTag(item, ItemTag.COLD_WATER)) {
				DataWrapper.set(playerFor, UserEntries.TEMPERATURE, DataWrapper.<Double> get(playerFor, UserEntries.TEMPERATURE)
						+ ConfigEntries.COLD_WATER_TEMPERATURE.<Double> getValue());
			}
		} else if (item.getType() == Material.POTION && item.getDurability() != (short) 0
				&& !ItemUtilities.getInstance().hasTag(item, ItemTag.MURKY_WATER) || item.getType() == Material.MILK_BUCKET) {
			if (item.getType() == Material.MILK_BUCKET) {
				Utilities.setPoisoned(playerFor, false, false);
			}

			if (ConfigEntries.USE_THIRST.<Boolean> getValue()) {
				MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
					@Override
					public void run() {
						ItemStack i = playerFor.getItemInHand();
						if (i != null) {
							if (i.getAmount() > 1) {
								i.setAmount(i.getAmount() - 1);
							} else {
								playerFor.setItemInHand(null);
							}
						}
					}
				}, 0L);
			}
		} else if (ItemUtilities.getInstance().isPoison(item)) {
			Utilities.setPoisoned(playerFor, true, false);
		}
	}

	private boolean isHumanoid(EntityType type) {
		return type == EntityType.PLAYER || type == EntityType.ZOMBIE || type == EntityType.PIG_ZOMBIE || type == EntityType.SKELETON
				|| type == EntityType.CREEPER;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onHeadshot(EntityDamageByEntityEvent e) {
		if (!Utilities.inWorld(e.getDamager())) { return; }

		if (e.getCause() == DamageCause.PROJECTILE && ConfigEntries.BOW_PRECISE_HEADSHOT_MOD.<Integer> getValue() > 0) {
			Projectile projectile = (Projectile) e.getDamager();
			if (wasHeadshot(e.getEntity(), projectile)) {
				e.setDamage(e.getDamage() + ConfigEntries.BOW_PRECISE_HEADSHOT_MOD.<Integer> getValue());
				((Player) projectile.getShooter()).sendMessage(LocaleMessage.HEADSHOT.filter(
						ConfigEntries.BOW_PRECISE_HEADSHOT_MOD.<Integer> getValue()).toString((Player) projectile.getShooter()));
			}
		}
	}

	private boolean wasHeadshot(Entity entity, Projectile arrow) {
		if (!(arrow instanceof Arrow) || !(arrow.getShooter() instanceof Player)) { return false; }

		Player player = (Player) arrow.getShooter();

		ItemStack bow = player.getItemInHand();
		if (bow == null || EquipmentState.getState(bow).getPosition(bow) < 5) { return false; }

		if (!isHumanoid(entity.getType())) { return false; }

		double projectileY = arrow.getLocation().getY();
		double entityY = entity.getLocation().getY();
		boolean headshot = projectileY - entityY > 1.75d;

		return headshot;
	}
}
