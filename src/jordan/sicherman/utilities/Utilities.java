/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jordan.sicherman.MyZ;
import jordan.sicherman.api.PlayerJoinMyZEvent;
import jordan.sicherman.listeners.player.Death;
import jordan.sicherman.listeners.player.SpectatorMode;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.particles.ParticleEffect;
import jordan.sicherman.player.User;
import jordan.sicherman.utilities.StartingKitManager.EquipmentPiece;
import jordan.sicherman.utilities.TemperatureManager.TemperatureEffect;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Jordan
 * 
 */
public class Utilities {

	private static final BlockFace[] cardinal = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST,
			BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

	public static boolean inWorld(Player playerFor) {
		return inWorld(playerFor.getWorld());
	}

	public static boolean inWorld(Entity entityFor) {
		return inWorld(entityFor.getWorld());
	}

	public static boolean inWorld(World worldFor) {
		return inWorld(worldFor.getName());
	}

	public static boolean inWorld(Location locationFor) {
		return inWorld(locationFor.getWorld());
	}

	private static boolean inWorld(String world) {
		return ConfigEntries.WORLDS.<List<String>> getValue().contains(world);
	}

	public static void doLogin(Player player) {
		MyZ.sql.add(player);
		User.forPlayer(player);
		MyZ.ghostFactory.addPlayer(player, false);
		MyZ.instance.getServer().getPluginManager().callEvent(new PlayerJoinMyZEvent(player));
		if (!player.isOp()) {
			MyZ.giveMyZTip(player);
		}
		if (!DataWrapper.<Boolean> get(player, UserEntries.PLAYING)) {
			player.setNoDamageTicks(Integer.MAX_VALUE);
		}
	}

	public static void doLogout(Player player) {
		player.setNoDamageTicks(0);
		if (SpectatorMode.isSpectator(player)) {
			Death.realDeath(player);
		}

		TemperatureManager.getInstance().doTemperatureEffects(player, TemperatureEffect.RELEASE_THERMOMETER);

		MyZ.ghostFactory.removePlayer(player);
		User.freePlayer(player);
	}

	public static void setPoisoned(Player playerFor, boolean poisoned, boolean force) {
		if (!ConfigEntries.USE_POISON.<Boolean> getValue()) { return; }

		if (!poisoned) {
			if (DataWrapper.<Boolean> get(playerFor, UserEntries.POISONED)) {
				DataWrapper.set(playerFor, UserEntries.POISONED, false);
				playerFor.removePotionEffect(PotionEffectType.WITHER);
				playerFor.removePotionEffect(PotionEffectType.CONFUSION);
				if (!force) {
					playerFor.sendMessage(LocaleMessage.INFECTION_ENDED.toString(playerFor));
					DataWrapper.set(playerFor, UserEntries.UNINFECTS, DataWrapper.<Integer> get(playerFor, UserEntries.UNINFECTS) + 1);
				}
			}
			return;
		}

		if (!DataWrapper.<Boolean> get(playerFor, UserEntries.POISONED)) {
			double immunityLevel = DataWrapper.<Integer> get(playerFor, UserEntries.IMMUNITY);
			double percentChance = 100.0;
			if (immunityLevel < 4) {
				percentChance = 100.0 - 25.0 * immunityLevel;
			} else {
				percentChance = 25.0 / (2.0 * (immunityLevel - 3.0));
			}

			if (DataWrapper.<Boolean> get(playerFor, UserEntries.BLEEDING)) {
				percentChance *= 2;
			}

			if (random.nextDouble() <= percentChance / 100.0) {
				DataWrapper.set(playerFor, UserEntries.POISONED, true);
				doPoisonDamage(playerFor, true);
				playerFor.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 0));
				playerFor.sendMessage(LocaleMessage.INFECTED.toString(playerFor));
			}
		}
	}

	public static void doPoisonDamage(Player playerFor) {
		doPoisonDamage(playerFor, false);
	}

	private static void doPoisonDamage(Player playerFor, boolean initial) {
		if (playerFor.getHealth() > 0.6 * playerFor.getMaxHealth()) {
			playerFor.setHealth(playerFor.getMaxHealth() * 0.6);
			playerFor.damage(0);
		}

		double damage = ConfigEntries.INFECTION_DAMAGE.<Double> getValue();
		if (!initial && playerFor.getHealth() > damage) {
			playerFor.damage(damage);
		}
		playerFor.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 10, 3));
	}

	public static void setBleeding(Player playerFor, boolean bleeding, boolean force) {
		if (!ConfigEntries.USE_BLEEDING.<Boolean> getValue()) { return; }
		if (!bleeding) {
			if (DataWrapper.<Boolean> get(playerFor, UserEntries.BLEEDING)) {
				DataWrapper.set(playerFor, UserEntries.BLEEDING, false);
				playerFor.removePotionEffect(PotionEffectType.BLINDNESS);
				if (!force) {
					playerFor.sendMessage(LocaleMessage.BLEEDING_ENDED.toString(playerFor));
					DataWrapper.set(playerFor, UserEntries.SELF_HEALS, DataWrapper.<Integer> get(playerFor, UserEntries.SELF_HEALS) + 1);
				}
			}
			return;
		}

		if (!DataWrapper.<Boolean> get(playerFor, UserEntries.BLEEDING)) {
			double toughnessLevel = DataWrapper.<Integer> get(playerFor, UserEntries.SKIN);

			double percentChance = 12.5 - toughnessLevel;
			if (percentChance < 1) {
				percentChance = 1;
			}

			if (random.nextDouble() <= percentChance / 100.0) {
				DataWrapper.set(playerFor, UserEntries.BLEEDING, true);
				doBleedingDamage(playerFor);
				playerFor.sendMessage(LocaleMessage.BLEEDING.toString(playerFor));
			}
		}
	}

	public static void doBleedingDamage(Player playerFor) {
		double damage = ConfigEntries.BLEED_DAMAGE.<Double> getValue();
		if (playerFor.getHealth() > damage) {
			playerFor.damage(damage);
		}
		playerFor.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 3));
		ParticleEffect.REDSTONE.display(0.25f, 0.25f, 0.25f, 0f, 50, playerFor.getEyeLocation().subtract(0, 0.5, 0), 10.0);
	}

	public static List<Player> getPlayersInRange(Location location, int radius) {
		List<Player> players = new ArrayList<Player>();
		int d2 = radius * radius;
		for (Player p : location.getWorld().getPlayers()) {
			if (p.getWorld() == location.getWorld()) {
				if (p.getLocation().distanceSquared(location) <= d2) {
					players.add(p);
				}
			}
		}
		return players;
	}

	// Source:
	// [url]http://www.gamedev.net/topic/338987-aabb---line-segment-intersection-test/[/url]
	public static boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
		final double epsilon = 0.0001f;

		Vector3D d = p2.subtract(p1).multiply(0.5);
		Vector3D e = max.subtract(min).multiply(0.5);
		Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
		Vector3D ad = d.abs();

		if (Math.abs(c.x) > e.x + ad.x) { return false; }
		if (Math.abs(c.y) > e.y + ad.y) { return false; }
		if (Math.abs(c.z) > e.z + ad.z) { return false; }

		if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon) { return false; }
		if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon) { return false; }
		if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon) { return false; }

		return true;
	}

	public static void addFriendOnSneak(final Player player) {
		MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				if (player != null && !player.isDead() && player.isOnline() && player.isSneaking() && player.isBlocking()) {
					// Unknown source
					List<Player> nearby = getPlayersInRange(player.getLocation(), 20);
					if (nearby.isEmpty()) { return; }

					Location eyePosition = player.getEyeLocation();
					Vector3D POV = new Vector3D(eyePosition.getDirection());

					Vector3D eyeLocation = new Vector3D(eyePosition);
					Vector3D POV_end = eyeLocation.add(POV.multiply(20));

					Player hit = null;

					for (Player target : nearby) {
						Vector3D targetPosition = new Vector3D(target.getLocation());
						Vector3D minimum = targetPosition.add(-0.5, 0, -0.5);
						Vector3D maximum = targetPosition.add(0.5, 1.67, 0.5);

						if (target != player && hasIntersection(eyeLocation, POV_end, minimum, maximum)) {
							if (hit == null
									|| hit.getLocation().distanceSquared(eyePosition) > target.getLocation().distanceSquared(eyePosition)) {
								hit = target;
							}
						}
					}

					if (hit != null && /* TODO NOT FRIENDS */true) {
						// TODO add friend and change number in datawrapper
					}
				}
			}
		}, 20L);
	}

	public static String getPrefixFor(Player from) {
		String custom = DataWrapper.<String> get(from, UserEntries.PREFIX);
		if (custom == null || custom.isEmpty()) {
			// TODO return for rank.
		}
		return custom;
	}

	public static List<Location> getSpawns() {
		List<String> locations = ConfigEntries.SPAWN_POINTS.<List<String>> getValue();
		List<Location> returned = new ArrayList<Location>();
		for (String s : locations) {
			Location toLoc = SerializableLocation.deserialize(s);
			if (toLoc != null) {
				returned.add(toLoc);
			}
		}
		return returned;
	}

	private static final Random random = new Random();

	public static void respawn(Player player) {
		Location home = null;

		try {
			home = SerializableLocation.deserialize(ConfigEntries.HOME_SPAWN.<String> getValue());
		} catch (Exception exc) {

		}

		if (home != null) {
			player.teleport(home);
		}

		DataWrapper.set(player, UserEntries.PLAYING, false);
		TemperatureManager.getInstance().doTemperatureEffects(player, TemperatureEffect.RELEASE_THERMOMETER);

		setBleeding(player, false, true);
		setPoisoned(player, false, true);

		MyZ.zombieFactory.setZombie(player, false);
		MyZ.ghostFactory.setGhost(player, false);

		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0);

		if (!Utilities.inWorld(player)) { return; }

		player.setNoDamageTicks(Integer.MAX_VALUE);
	}

	public static boolean canSpawn(Player player) {
		return !DataWrapper.<Boolean> get(player, UserEntries.PLAYING) && getSpawns().size() != 0;
	}

	public static void spawnInWorld(Player player) {
		if (DataWrapper.<Boolean> get(player, UserEntries.PLAYING)) {
			player.sendMessage(LocaleMessage.ALREADY_SPAWNED.toString(player));
			return;
		}

		List<Location> spawns = getSpawns();
		if (spawns.size() == 0) {
			player.sendMessage(LocaleMessage.NO_SPAWNS.toString(player));
			return;
		}

		setBleeding(player, false, true);
		setPoisoned(player, false, true);

		MyZ.zombieFactory.setZombie(player, false);
		MyZ.ghostFactory.setGhost(player, false);

		player.setNoDamageTicks(0);
		player.setSaturation(0.3f);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		player.sendMessage(LocaleMessage.SPAWNED.toString(player));
		DataWrapper.set(player, UserEntries.PLAYING, true);
		ThirstManager.getInstance().replenish(player, false, false);

		player.getInventory().clear();
		player.getEquipment().clear();

		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}

		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 1));

		player.getInventory().setContents(StartingKitManager.getInstance().getInventory(player));

		player.getEquipment().setBoots(StartingKitManager.getInstance().getEquipment(player, EquipmentPiece.BOOTS));
		player.getEquipment().setLeggings(StartingKitManager.getInstance().getEquipment(player, EquipmentPiece.LEGGINGS));
		player.getEquipment().setChestplate(StartingKitManager.getInstance().getEquipment(player, EquipmentPiece.CHESTPLATE));
		player.getEquipment().setHelmet(StartingKitManager.getInstance().getEquipment(player, EquipmentPiece.HELMET));

		player.teleport(spawns.get(random.nextInt(spawns.size())));
	}

	public static synchronized void faceCompass(Player playerFor, boolean unspecific, BlockFace direction) {
		if (unspecific) {
			playerFor.setCompassTarget(playerFor.getWorld().getSpawnLocation());
			return;
		}

		Location at = playerFor.getLocation();
		BlockFace facing = getFacing(at);
		switch (direction) {
		case NORTH:
			playerFor.setCompassTarget(at.getBlock().getRelative(facing, 1000).getLocation());
			break;
		case SOUTH:
			playerFor.setCompassTarget(at.getBlock().getRelative(facing.getOppositeFace(), 1000).getLocation());
			break;
		case EAST:
			playerFor.setCompassTarget(at.getBlock().getRelative(getRightShifts(facing, 2), 1000).getLocation());
			break;
		case WEST:
			playerFor.setCompassTarget(at.getBlock().getRelative(getRightShifts(facing, 6), 1000).getLocation());
			break;
		case NORTH_EAST:
			playerFor.setCompassTarget(at.getBlock().getRelative(getRightShifts(facing, 1), 1000).getLocation());
			break;
		case SOUTH_EAST:
			playerFor.setCompassTarget(at.getBlock().getRelative(getRightShifts(facing, 3), 1000).getLocation());
			break;
		case SOUTH_WEST:
			playerFor.setCompassTarget(at.getBlock().getRelative(getRightShifts(facing, 5), 1000).getLocation());
			break;
		case NORTH_WEST:
			playerFor.setCompassTarget(at.getBlock().getRelative(getRightShifts(facing, 7), 1000).getLocation());
			break;
		default:
			throw new IllegalArgumentException(direction + " must be a subcardinal direction!");
		}
	}

	private static BlockFace getRightShifts(BlockFace from, int shifts) {
		if (shifts <= 0) { return from; }

		shifts--;
		switch (from) {
		case NORTH:
			return getRightShifts(BlockFace.NORTH_EAST, shifts);
		case NORTH_EAST:
			return getRightShifts(BlockFace.EAST, shifts);
		case EAST:
			return getRightShifts(BlockFace.SOUTH_EAST, shifts);
		case SOUTH_EAST:
			return getRightShifts(BlockFace.SOUTH, shifts);
		case SOUTH:
			return getRightShifts(BlockFace.SOUTH_WEST, shifts);
		case SOUTH_WEST:
			return getRightShifts(BlockFace.WEST, shifts);
		case WEST:
			return getRightShifts(BlockFace.NORTH_WEST, shifts);
		case NORTH_WEST:
			return getRightShifts(BlockFace.NORTH, shifts);
		default:
			throw new IllegalArgumentException(from + " must be a subcardinal direction!");
		}
	}

	private static BlockFace getFacing(Location inLoc) {
		return cardinal[Math.round(inLoc.getYaw() / 45.0f) & 0x7];
	}

	public static String yesNo(Player playerFor, boolean bool) {
		return bool ? LocaleMessage.YES.toString(playerFor) : LocaleMessage.NO.toString(playerFor);
	}
}
