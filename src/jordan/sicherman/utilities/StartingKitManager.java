/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.ArrayList;
import java.util.List;

import jordan.sicherman.utilities.configuration.UserEntries;

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

	public void addToKit(Player player, ItemStack item) {
		List<ItemStack> contents = new ArrayList<ItemStack>(DataWrapper.<List<ItemStack>> get(player, UserEntries.KIT_INVENTORY));
		contents.add(item);
		DataWrapper.set(player, UserEntries.KIT_INVENTORY, contents);
	}

	public void setEquipment(Player player, ItemStack item, EquipmentPiece piece) {
		DataWrapper.set(player, piece.entry, item);
	}

	public ItemStack getEquipment(Player playerFor, EquipmentPiece piece) {
		return DataWrapper.<ItemStack> get(playerFor, piece.entry);
	}

	public ItemStack[] getInventory(Player playerFor) {
		return DataWrapper.<List<ItemStack>> get(playerFor, UserEntries.KIT_INVENTORY).toArray(new ItemStack[0]);
	}
}
