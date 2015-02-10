/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.Arrays;
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
	private ItemStack[] inventory;
	private ItemStack helmet, chestplate, leggings, boots;
	private String chat;

	public MyZRank(int identifier) {
		this.identifier = identifier;
	}

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

		chat = prefix;
	}

	public void setEquipment(EquipmentPiece piece, ItemStack item) {
		ConfigurationSection ranks = ConfigEntries.RANKS.<ConfigurationSection> getValue();

		String slug = "helmet";
		switch (piece) {
		case BOOTS:
			slug = "boots";
			boots = item;
			break;
		case CHESTPLATE:
			slug = "chestplate";
			chestplate = item;
			break;
		case HELMET:
			slug = "helmet";
			helmet = item;
			break;
		case LEGGINGS:
			slug = "leggings";
			leggings = item;
			break;
		}
		ranks.set(identifier + ".equipment." + slug, item);
		FileUtilities.save(ConfigEntries.RANKS.getFile());
	}

	public void setInventory(List<ItemStack> inventory) {
		ConfigurationSection ranks = ConfigEntries.RANKS.<ConfigurationSection> getValue();

		ranks.set(identifier + ".equipment.inventory", inventory);
		FileUtilities.save(ConfigEntries.RANKS.getFile());

		this.inventory = inventory.toArray(new ItemStack[0]);
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

	public static MyZRank forInt(int identifier, boolean create) {
		MyZRank nearest = null;
		for (MyZRank rank : values) {
			if (rank.identifier == identifier) {
				return rank;
			} else if (rank.identifier < identifier) {
				if (nearest == null || rank.identifier > nearest.identifier) {
					nearest = rank;
				}
			}
		}

		if (create) {
			nearest = new MyZRank(identifier);
			values.add(nearest);
		}
		return nearest;
	}

	public static MyZRank forInt(int identifier) {
		return forInt(identifier, false);
	}

	public ItemStack getEquipment(EquipmentPiece piece) {
		ItemStack item = null;
		switch (piece) {
		case BOOTS:
			item = boots;
			break;
		case CHESTPLATE:
			item = chestplate;
			break;
		case HELMET:
			item = helmet;
			break;
		case LEGGINGS:
			item = leggings;
			break;
		default:
			return null;
		}

		if (item == null && identifier > 0) { return forInt(identifier - 1).getEquipment(piece); }

		return item;
	}

	public String getChatPrefix() {
		return ChatColor.translateAlternateColorCodes('&', chat);
	}

	public ItemStack[] getInventory() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.addAll(Arrays.asList(inventory));

		Set<Integer> ids = new HashSet<Integer>();
		for (int i = identifier - 1; i >= 0; i--) {
			MyZRank rank = forInt(i);
			if (!ids.contains(rank.identifier)) {
				ids.add(rank.identifier);
				items.addAll(Arrays.asList(rank.inventory));
			}
		}

		return items.toArray(new ItemStack[0]);
	}
}
