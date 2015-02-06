/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jordan.sicherman.utilities.StartingKitManager.EquipmentPiece;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.FileUtilities;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public class MyZRank {

	private static Set<MyZRank> values = new HashSet<MyZRank>();

	private final int identifier;
	private final ItemStack[] inventory;
	private final ItemStack helmet, chestplate, leggings, boots;
	private final String chat;

	public MyZRank(int identifier, String chat, ItemStack[] inventory, ItemStack helmet, ItemStack chestplate, ItemStack leggings,
			ItemStack boots) {
		this.identifier = identifier;
		this.chat = chat;
		this.inventory = inventory;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}

	public void setPrefix(String prefix) {
		ConfigurationSection ranks = ConfigEntries.RANKS.<ConfigurationSection> getValue();

		ranks.set(identifier + ".chat_prefix", prefix);
		FileUtilities.save(ConfigEntries.RANKS.getFile());
	}

	public void setEquipment(EquipmentPiece piece, ItemStack item) {
		ConfigurationSection ranks = ConfigEntries.RANKS.<ConfigurationSection> getValue();

		String slug = "helmet";
		switch (piece) {
		case BOOTS:
			slug = "boots";
			break;
		case CHESTPLATE:
			slug = "chestplate";
			break;
		case HELMET:
			slug = "helmet";
			break;
		case LEGGINGS:
			slug = "leggings";
			break;
		}
		ranks.set(identifier + ".equipment." + slug, item);
		FileUtilities.save(ConfigEntries.RANKS.getFile());
	}

	public void setInventory(List<ItemStack> inventory) {
		ConfigurationSection ranks = ConfigEntries.RANKS.<ConfigurationSection> getValue();

		ranks.set(identifier + ".equipment.inventory", inventory);
		FileUtilities.save(ConfigEntries.RANKS.getFile());
	}

	@SuppressWarnings("unchecked")
	public static void load() {
		ConfigurationSection ranks = ConfigEntries.RANKS.<ConfigurationSection> getValue();

		values.clear();

		for (String key : ranks.getKeys(false)) {
			String chat = ranks.getString(key + ".chat_prefix");
			ItemStack boots = ranks.getItemStack(key + ".equipment.boots");
			ItemStack helmet = ranks.getItemStack(key + ".equipment.helmet");
			ItemStack chestplate = ranks.getItemStack(key + ".equipment.chestplate");
			ItemStack leggings = ranks.getItemStack(key + ".equipment.leggings");
			List<ItemStack> inventory = (List<ItemStack>) ranks.getList(key + ".equipment.inventory");
			ItemStack[] items = new ItemStack[0];
			if (inventory != null) {
				items = inventory.toArray(new ItemStack[0]);
			}
			values.add(new MyZRank(Integer.valueOf(key), chat, items, helmet, chestplate, leggings, boots));
		}
	}

	public static MyZRank forInt(int identifier) {
		MyZRank forZero = null;
		for (MyZRank rank : values) {
			if (rank.identifier == identifier) {
				return rank;
			} else if (rank.identifier == 0) {
				forZero = rank;
			}
		}

		return forZero;
	}

	public ItemStack getEquipment(EquipmentPiece piece) {
		switch (piece) {
		case BOOTS:
			return boots;
		case CHESTPLATE:
			return chestplate;
		case HELMET:
			return helmet;
		case LEGGINGS:
			return leggings;
		default:
			return null;
		}
	}

	public String getChatPrefix() {
		return ChatColor.translateAlternateColorCodes('&', chat);
	}

	public ItemStack[] getInventory() {
		return inventory;
	}
}
