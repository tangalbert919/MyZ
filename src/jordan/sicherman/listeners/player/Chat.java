package jordan.sicherman.listeners.player;

import java.util.Arrays;
import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.ManagerManager;
import jordan.sicherman.utilities.MyZRank;
import jordan.sicherman.utilities.StartingKitManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Chat implements Listener {

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    private void onChat(AsyncPlayerChatEvent e) {
        if (Utilities.inWorld(e.getPlayer())) {
            Player from = e.getPlayer();

            if (ManagerManager.isManager(from, ManagerManager.ManagerType.CHESTS)) {
                String exc = ((MetadataValue) from.getMetadata(ManagerManager.ManagerType.CHESTS.getID()).get(0)).asString();

                if (exc != null && exc.isEmpty()) {
                    e.setCancelled(true);
                    ChestType.create(e.getMessage());
                    from.setMetadata(ManagerManager.ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, e.getMessage()));
                    from.openInventory(Bukkit.createInventory((InventoryHolder) null, 9, "Create Lootset"));
                    return;
                }

                if (exc != null && !exc.isEmpty()) {
                    e.setCancelled(true);
                    if ("a769bH3".equals(exc)) {
                        ChestType player1 = ChestType.fromName(e.getMessage());

                        if (player1 != null) {
                            player1.remove();
                            from.sendMessage(LocaleMessage.CHEST_MANAGER_REMOVED.toString((CommandSender) from));
                            return;
                        }

                        from.setMetadata(ManagerManager.ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, (Object) null));
                        return;
                    }

                    String[] player = e.getMessage().split(", ");

                    try {
                        int exc1 = Integer.parseInt(player[0]);
                        int max = Integer.parseInt(player[1]);
                        int prob = Integer.parseInt(player[2]);

                        ChestType.fromName(exc).setRecentProperties(exc1, max, prob);
                        from.openInventory(Bukkit.createInventory((InventoryHolder) null, 9, "Create Lootset"));
                    } catch (Exception exception) {
                        from.sendMessage(LocaleMessage.ADD_ITEM_PARSE_ERROR.toString((CommandSender) from));
                    }

                    return;
                }
            } else if (ManagerManager.isManager(from, ManagerManager.ManagerType.SPAWN_KIT)) {
                e.setCancelled(true);

                try {
                    MyZRank exc2 = MyZRank.forInt(Integer.parseInt(e.getMessage()), true);

                    exc2.setEquipment(StartingKitManager.EquipmentPiece.BOOTS, from.getEquipment().getBoots() != null ? from.getEquipment().getBoots().clone() : null);
                    exc2.setEquipment(StartingKitManager.EquipmentPiece.LEGGINGS, from.getEquipment().getLeggings() != null ? from.getEquipment().getLeggings().clone() : null);
                    exc2.setEquipment(StartingKitManager.EquipmentPiece.CHESTPLATE, from.getEquipment().getChestplate() != null ? from.getEquipment().getChestplate().clone() : null);
                    exc2.setEquipment(StartingKitManager.EquipmentPiece.HELMET, from.getEquipment().getHelmet() != null ? from.getEquipment().getHelmet().clone() : null);
                    exc2.setInventory(Arrays.asList(from.getInventory().getContents() != null ? (ItemStack[]) from.getInventory().getContents().clone() : null));
                } catch (Exception exception1) {
                    OfflinePlayer player2 = Bukkit.getOfflinePlayer(e.getMessage());

                    if (player2 == null) {
                        from.sendMessage(LocaleMessage.NO_USER.toString((CommandSender) from));
                        return;
                    }

                    DataWrapper.set(player2, UserEntries.KIT_BOOTS, from.getEquipment().getBoots() != null ? from.getEquipment().getBoots().clone() : null);
                    DataWrapper.set(player2, UserEntries.KIT_LEGGINGS, from.getEquipment().getLeggings() != null ? from.getEquipment().getLeggings().clone() : null);
                    DataWrapper.set(player2, UserEntries.KIT_CHESTPLATE, from.getEquipment().getChestplate() != null ? from.getEquipment().getChestplate().clone() : null);
                    DataWrapper.set(player2, UserEntries.KIT_HELMET, from.getEquipment().getHelmet() != null ? from.getEquipment().getHelmet().clone() : null);
                    DataWrapper.set(player2, UserEntries.KIT_INVENTORY, Arrays.asList(from.getInventory().getContents() != null ? (ItemStack[]) from.getInventory().getContents().clone() : null));
                }

                from.sendMessage(LocaleMessage.MANAGING_SPAWN_KIT_CREATED.toString((CommandSender) from));
                ManagerManager.setManager(from, false, ManagerManager.ManagerType.SPAWN_KIT);
            }

        }
    }
}
