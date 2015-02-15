/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.ArrayList;
import java.util.List;

import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public class StartingKitManager {

	public static enum EquipmentPiece {
		HELMET(UserEntries.KIT_HELMET), CHESTPLATE(UserEntries.KIT_CHESTPLATE), LEGGINGS(UserEntries.KIT_LEGGINGS), BOOTS(
				UserEntries.KIT_BOOTS);

		private final UserEntries entry;

		private EquipmentPiece(UserEntries entry) {
			this.entry = entry;
		}
	}

	private static StartingKitManager instance;

	public StartingKitManager() {
		instance = this;
	}

	public static StartingKitManager getInstance() {
		return instance;
	}

	public void addToKit(Player player, ItemStack... item) {
		List<ItemStack> contents = new ArrayList<ItemStack>(DataWrapper.<List<ItemStack>> get(player, UserEntries.KIT_INVENTORY));
		for (ItemStack i : item) {
			contents.add(i);
		}
		DataWrapper.set(player, UserEntries.KIT_INVENTORY, contents);
	}

	public void setEquipment(Player player, ItemStack item, EquipmentPiece piece) {
		DataWrapper.set(player, piece.entry, item);
	}

	public ItemStack getEquipment(Player playerFor, EquipmentPiece piece) {
		ItemStack item = DataWrapper.<ItemStack> get(playerFor, piece.entry);

		if (item == null || item.getType() == Material.AIR) {
			MyZRank rank = Utilities.getRank(playerFor);
			if (rank != null) {
				item = rank.getEquipment(piece);
			}
		}

		return item != null ? item.clone() : item;
	}

	public ItemStack[] getInventory(Player playerFor) {
		List<ItemStack> custom = DataWrapper.<List<ItemStack>> get(playerFor, UserEntries.KIT_INVENTORY);
		List<ItemStack> inventory = new ArrayList<ItemStack>();
		if (custom != null && !custom.isEmpty()) {
			for (ItemStack i : custom) {
				if (i != null) {
					inventory.add(i.clone());
				}
			}
		}

		MyZRank rank = Utilities.getRank(playerFor);
		if (rank != null) {
			for (ItemStack i : rank.getInventory()) {
				if (i != null) {
					inventory.add(i.clone());
				}
			}
		}

		return inventory.toArray(new ItemStack[0]);
	}
}
