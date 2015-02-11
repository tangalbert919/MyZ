/**
 * 
 */
package jordan.sicherman.listeners.player;

import java.util.ArrayList;
import java.util.List;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerManager;
import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.ManagerManager;
import jordan.sicherman.utilities.ManagerManager.ManagerType;
import jordan.sicherman.utilities.SerializableLocation;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.Configuration.CFiles;
import jordan.sicherman.utilities.configuration.FileUtilities;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class SpectatorMode implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	private void onCloseChest(final InventoryCloseEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (!ConfigEntries.CHESTS.<Boolean> getValue()) { return; }

		if (e.getPlayer().hasMetadata("MyZ.bypassCheck")) {
			e.getPlayer().removeMetadata("MyZ.bypassCheck", MyZ.instance);
			return;
		}

		if (e.getInventory().getType() == InventoryType.CHEST && e.getInventory().getHolder() instanceof Chest) {
			if (ChestType.isEmpty(e.getInventory().getContents())) {
				MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
					@Override
					public void run() {
						ChestType.despawn(((Chest) e.getInventory().getHolder()).getBlock(), ((Chest) e.getInventory().getHolder())
								.getBlock().hasMetadata("MyZ.airdrop"));
					}
				}, 20L);
			}
		} else if ("Create Lootset".equals(e.getInventory().getName())) {
			e.getPlayer().setMetadata(ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, null));
			((Player) e.getPlayer()).sendMessage(LocaleMessage.CHEST_MANAGER_COMPLETE.toString((Player) e.getPlayer()));
			FileUtilities.save(CFiles.CHESTS);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onModifyInventory(final InventoryClickEvent e) {
		if (!Utilities.inWorld(e.getWhoClicked())) { return; }

		if (!ConfigEntries.CHESTS.<Boolean> getValue()) { return; }

		if ("Create Lootset".equals(e.getInventory().getName())) {
			new BukkitRunnable() {
				@Override
				public void run() {
					for (int i : new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }) {
						if (e.getInventory().getItem(i) != null && e.getInventory().getItem(i).getType() != Material.AIR) {
							ChestType.fromName(e.getWhoClicked().getMetadata(ManagerType.CHESTS.getID()).get(0).asString()).addItem(
									e.getInventory().getItem(i));
							e.getWhoClicked().setMetadata("MyZ.bypassCheck", new FixedMetadataValue(MyZ.instance, true));
							e.getWhoClicked().closeInventory();
							((Player) e.getWhoClicked()).sendMessage(LocaleMessage.ADD_ITEM_INSTRUCTIONS.toString((Player) e
									.getWhoClicked()));
						}
					}
				}
			}.runTaskLater(MyZ.instance, 1L);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onPickup(PlayerPickupItemEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (isSpectator(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (!Utilities.inWorld(player)) { return; }

		if (isSpectator(player)) {
			e.setCancelled(true);
		}

		if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& ItemUtilities.getInstance().hasTag(e.getItem(), ItemTag.RADIO)) {
			e.setCancelled(true);
		}

		if (ManagerManager.isManager(player, ManagerType.ENGINEER)
				&& ItemUtilities.getInstance().hasTag(player.getItemInHand(), ItemTag.WAND)) {
			switch (e.getAction()) {
			case RIGHT_CLICK_AIR:
			case RIGHT_CLICK_BLOCK:
				e.setCancelled(true);
				CompatibilityManager.interactAnvil(player, null);
				break;
			case LEFT_CLICK_AIR:
			case LEFT_CLICK_BLOCK:
				e.setCancelled(true);
				int index = player.getMetadata(ManagerType.ENGINEER.getID()).get(0).asInt() + 1;
				List<EngineerRecipe> recipes = EngineerManager.getInstance().getRecipes();
				if (recipes.isEmpty()) {
					player.sendMessage(LocaleMessage.NO_RECIPES.toString(player));
					break;
				}
				EngineerRecipe recipe;
				try {
					recipe = recipes.get(index);
				} catch (Exception exc) {
					recipe = recipes.get(0);
				}
				player.sendMessage(LocaleMessage.VIEWING_RECIPE.filter(index).toString(player));
				player.setMetadata(ManagerType.ENGINEER.getID(), new FixedMetadataValue(MyZ.instance, index));
				CompatibilityManager.interactAnvil(player, null, recipe);
				break;
			default:
				break;
			}
		} else if (ManagerManager.isManager(player, ManagerType.SPAWN)
				&& ItemUtilities.getInstance().hasTag(player.getItemInHand(), ItemTag.WAND)) {
			switch (e.getAction()) {
			case RIGHT_CLICK_AIR:
			case RIGHT_CLICK_BLOCK:
				e.setCancelled(true);
				List<Location> spawns = Utilities.getSpawns();
				if (spawns.isEmpty()) {
					player.sendMessage(LocaleMessage.NO_SPAWNS.toString(player));
					break;
				}

				int at = player.getMetadata(ManagerType.SPAWN.getID()).get(0).asInt() + 1;
				try {
					player.teleport(spawns.get(at));
				} catch (Exception exc) {
					at = 0;
					player.teleport(spawns.get(at));
				}

				player.sendMessage(LocaleMessage.VIEWING_SPAWN.filter(at).toString(player));

				player.setMetadata(ManagerType.SPAWN.getID(), new FixedMetadataValue(MyZ.instance, at));
				break;
			case LEFT_CLICK_AIR:
			case LEFT_CLICK_BLOCK:
				e.setCancelled(true);

				if (player.isSneaking()) {
					DataWrapper.set(ConfigEntries.HOME_SPAWN, SerializableLocation.fromLocation(player.getLocation()).serialize(), true);
					player.sendMessage(LocaleMessage.HOME_SET.toString(player));
					return;
				}

				List<String> spawnpoints = new ArrayList<String>(ConfigEntries.SPAWN_POINTS.<List<String>> getValue());
				for (Location spawn : Utilities.getSpawns()) {
					if (player.getLocation().equals(spawn)) {
						spawnpoints.remove(SerializableLocation.fromLocation(spawn).serialize());
						DataWrapper.set(ConfigEntries.SPAWN_POINTS, spawnpoints, true);

						player.sendMessage(LocaleMessage.REMOVED_SPAWN.toString(player));
						return;
					}
				}
				spawnpoints.add(SerializableLocation.fromLocation(player.getLocation()).serialize());
				DataWrapper.set(ConfigEntries.SPAWN_POINTS, spawnpoints, true);

				player.sendMessage(LocaleMessage.ADDED_SPAWN.toString(player));
				break;
			default:
				break;
			}
		} else if (ManagerManager.isManager(e.getPlayer(), ManagerType.CHESTS)
				&& ItemUtilities.getInstance().hasTag(e.getPlayer().getItemInHand(), ItemTag.WAND)) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				e.setCancelled(true);
				Block block = e.getClickedBlock();
				if (block.getState().getData() instanceof DirectionalContainer) {
					ChestType type = ChestType.nextType(ChestType.getType(block));
					ChestType.setType(block, type);
					e.getPlayer().sendMessage(
							LocaleMessage.CHEST_SET.filter(type == null ? LocaleMessage.NOTHING.toString(e.getPlayer()) : type.getName())
									.toString(e.getPlayer()));
				}
			} else if (e.getAction() == Action.LEFT_CLICK_AIR) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(LocaleMessage.CREATE_CHEST_INSTRUCTIONS.toString(e.getPlayer()));
				e.getPlayer().setMetadata(ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, ""));
			} else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				e.setCancelled(true);

				e.getPlayer().sendMessage(LocaleMessage.CHEST_MANAGER_REMOVE.toString(e.getPlayer()));

				ChatColor color = ChatColor.BLUE;
				for (ChestType type : ChestType.values()) {
					e.getPlayer().sendMessage(color + type.getName());
					color = color == ChatColor.BLUE ? ChatColor.DARK_GRAY : ChatColor.BLUE;
				}

				e.getPlayer().setMetadata(ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, "a769bH3"));
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onDrop(PlayerDropItemEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (ManagerManager.isManager(e.getPlayer())) {
			if (ItemUtilities.getInstance().hasTag(e.getItemDrop().getItemStack(), ItemTag.WAND)) {
				ManagerManager.stopManaging(e.getPlayer());
				e.getItemDrop().remove();
				e.getPlayer().sendMessage(LocaleMessage.MANAGING_OVER.toString(e.getPlayer()));
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onInteractEntity(PlayerInteractEntityEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (isSpectator(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onTakeDamage(EntityDamageEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getEntity() instanceof Player && MyZ.ghostFactory.isGhost((Player) e.getEntity())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onTakeEntityDamage(EntityDamageByEntityEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getEntity() instanceof Player && MyZ.ghostFactory.isGhost((Player) e.getEntity())) {
			e.setCancelled(true);
		}
	}

	public static boolean isSpectator(Player player) {
		return MyZ.ghostFactory.isGhost(player) || MyZ.zombieFactory.isZombie(player);
	}
}
