/**
 * 
 */
package jordan.sicherman.listeners.player;

import java.util.Arrays;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.ManagerManager;
import jordan.sicherman.utilities.ManagerManager.ManagerType;
import jordan.sicherman.utilities.MyZRank;
import jordan.sicherman.utilities.StartingKitManager.EquipmentPiece;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * @author Jordan
 * 
 */
public class Chat implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onChat(AsyncPlayerChatEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		Player from = e.getPlayer();

		if (ManagerManager.isManager(from, ManagerType.CHESTS)) {
			String meta = from.getMetadata(ManagerType.CHESTS.getID()).get(0).asString();
			if (meta != null && meta.isEmpty()) {
				e.setCancelled(true);
				ChestType.create(e.getMessage());
				from.setMetadata(ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, e.getMessage()));
				from.openInventory(Bukkit.createInventory(null, 9, "Create Lootset"));
				return;
			} else if (meta != null && !meta.isEmpty()) {
				e.setCancelled(true);

				if ("a769bH3".equals(meta)) {
					ChestType type = ChestType.fromName(e.getMessage());
					if (type != null) {
						type.remove();
						from.sendMessage(LocaleMessage.CHEST_MANAGER_REMOVED.toString(from));
						return;
					}
					from.setMetadata(ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, null));
					return;
				}

				String[] format = e.getMessage().split(", ");
				try {
					int min = Integer.parseInt(format[0]);
					int max = Integer.parseInt(format[1]);
					int prob = Integer.parseInt(format[2]);
					ChestType.fromName(meta).setRecentProperties(min, max, prob);
					from.openInventory(Bukkit.createInventory(null, 9, "Create Lootset"));
				} catch (Exception exc) {
					from.sendMessage(LocaleMessage.ADD_ITEM_PARSE_ERROR.toString(from));
				}
				return;
			}
		} else if (ManagerManager.isManager(from, ManagerType.SPAWN_KIT)) {
			e.setCancelled(true);

			try {
				MyZRank rank = MyZRank.forInt(Integer.parseInt(e.getMessage()), true);
				rank.setEquipment(EquipmentPiece.BOOTS, from.getEquipment().getBoots() != null ? from.getEquipment().getBoots().clone()
						: null);
				rank.setEquipment(EquipmentPiece.LEGGINGS, from.getEquipment().getLeggings() != null ? from.getEquipment().getLeggings()
						.clone() : null);
				rank.setEquipment(EquipmentPiece.CHESTPLATE, from.getEquipment().getChestplate() != null ? from.getEquipment()
						.getChestplate().clone() : null);
				rank.setEquipment(EquipmentPiece.HELMET, from.getEquipment().getHelmet() != null ? from.getEquipment().getHelmet().clone()
						: null);
				rank.setInventory(Arrays.asList(from.getInventory().getContents() != null ? from.getInventory().getContents().clone()
						: null));
			} catch (Exception exc) {
				OfflinePlayer player = Bukkit.getOfflinePlayer(e.getMessage());
				if (player == null) {
					from.sendMessage(LocaleMessage.NO_USER.toString(from));
					return;
				}
				DataWrapper.set(player, UserEntries.KIT_BOOTS, from.getEquipment().getBoots() != null ? from.getEquipment().getBoots()
						.clone() : null);
				DataWrapper.set(player, UserEntries.KIT_LEGGINGS, from.getEquipment().getLeggings() != null ? from.getEquipment()
						.getLeggings().clone() : null);
				DataWrapper.set(player, UserEntries.KIT_CHESTPLATE, from.getEquipment().getChestplate() != null ? from.getEquipment()
						.getChestplate().clone() : null);
				DataWrapper.set(player, UserEntries.KIT_HELMET, from.getEquipment().getHelmet() != null ? from.getEquipment().getHelmet()
						.clone() : null);
				DataWrapper.set(player, UserEntries.KIT_INVENTORY,
						Arrays.asList(from.getInventory().getContents() != null ? from.getInventory().getContents().clone() : null));
			}
			from.sendMessage(LocaleMessage.MANAGING_SPAWN_KIT_CREATED.toString(from));
			ManagerManager.setManager(from, false, ManagerType.SPAWN_KIT);
		}
	}
}
