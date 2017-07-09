package jordan.sicherman.listeners.player;

import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class GearModifications implements Listener {

    @EventHandler(
        priority = EventPriority.LOWEST,
        ignoreCancelled = true
    )
    private void onOpenFurnace(PlayerInteractEvent e) {
        if (Utilities.inWorld(e.getPlayer())) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (((Boolean) ConfigEntries.ENHANCE_FURNACES.getValue()).booleanValue() && e.getClickedBlock().getType() == Material.FURNACE) {
                    Furnace f = (Furnace) e.getClickedBlock().getState();

                    if (!f.getInventory().getViewers().isEmpty()) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(LocaleMessage.UNABLE_TO_ACCESS_INVENTORY.toString((CommandSender) e.getPlayer()));
                    }
                }

                if (((Boolean) ConfigEntries.USE_ENHANCED_ANVILS.getValue()).booleanValue() && e.getClickedBlock().getType() == Material.ANVIL) {
                    e.setCancelled(true);
                    CompatibilityManager.interactAnvil(e.getPlayer(), e.getClickedBlock(), new EngineerRecipe[0]);
                }

            }
        }
    }

    @EventHandler(
        priority = EventPriority.HIGHEST
    )
    private void onInventoryClick(InventoryClickEvent e) {
        if (Utilities.inWorld((Entity) e.getWhoClicked())) {
            if (((Boolean) ConfigEntries.USE_ENHANCED_ANVILS.getValue()).booleanValue() && e.getInventory().getType() == InventoryType.CHEST && e.getInventory().getSize() == 3) {
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
}
