/**
 * 
 */
package jordan.sicherman.listeners.player;

import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Jordan
 * 
 */
public class GearModifications implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void onOpenFurnace(PlayerInteractEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) { return; }

		if (ConfigEntries.ENHANCE_FURNACES.<Boolean> getValue() && e.getClickedBlock().getType() == Material.FURNACE) {
			Furnace f = (Furnace) e.getClickedBlock().getState();
			if (!f.getInventory().getViewers().isEmpty()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(LocaleMessage.UNABLE_TO_ACCESS_INVENTORY.toString(e.getPlayer()));
			}
		}

		if (ConfigEntries.USE_ENHANCED_ANVILS.<Boolean> getValue() && e.getClickedBlock().getType() == Material.ANVIL) {
			e.setCancelled(true);
			CompatibilityManager.interactAnvil(e.getPlayer(), e.getClickedBlock());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onInventoryClick(InventoryClickEvent e) {
		if (!Utilities.inWorld(e.getWhoClicked())) { return; }

		if (ConfigEntries.USE_ENHANCED_ANVILS.<Boolean> getValue() && e.getInventory().getType() == InventoryType.CHEST
				&& e.getInventory().getSize() == 3) {
			if (e.getClick() == ClickType.DOUBLE_CLICK) {
				e.setCancelled(true);
				return;
			}
			if (e.getRawSlot() >= 0 && e.getRawSlot() <= 2) {
				if (e.getClick().isRightClick()) {
					e.setCancelled(true);
					return;
				}
				e.setCancelled(CompatibilityManager.sendInventoryUpdate((Player) e.getWhoClicked(), e.getRawSlot()));
			}
		}
	}
}
