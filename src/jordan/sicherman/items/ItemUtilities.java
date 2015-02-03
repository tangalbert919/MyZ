/**
 * 
 */
package jordan.sicherman.items;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Jordan
 * 
 */
public class ItemUtilities {

	private static ItemUtilities instance;

	public ItemUtilities() {
		instance = this;
	}

	public static ItemUtilities getInstance() {
		return instance;
	}

	public ItemStack getTagItem(ItemTag tag, int amount) {
		return tag(new ItemStack(tag.getType(), amount), tag);
	}

	public ItemStack tag(ItemStack item, ItemTag tag) {
		if (item == null || tag == null) { return null; }
		if (item.getType() != tag.getType()) { return item; }

		item.setDurability(tag.getDurability());
		ItemMeta meta = item.getItemMeta();
		if (tag.getDisplayName() != null && !tag.getDisplayName().isEmpty()) {
			meta.setDisplayName(ChatColor.RESET + tag.getDisplayName());
		}
		if (tag.getLore() != null && !tag.getLore().isEmpty()) {
			meta.setLore(tag.getLore());
		}
		if (tag.getEnchantments() != null && !tag.getEnchantments().isEmpty()) {
			for (Enchantment key : tag.getEnchantments().keySet()) {
				meta.addEnchant(key, tag.getEnchantments().get(key), true);
			}
		}
		item.setItemMeta(meta);
		return item;
	}

	public boolean hasTag(ItemStack item, ItemTag tag) {
		if (item == null || tag == null) { return false; }

		return tag.matches(item);
	}

	public boolean isPoison(ItemStack item) {
		switch (item.getType()) {
		case POISONOUS_POTATO:
		case PORK:
		case RABBIT:
		case RAW_BEEF:
		case RAW_CHICKEN:
		case RAW_FISH:
		case SPIDER_EYE:
			return true;
		default:
			return false;
		}
	}

	public boolean isFood(ItemStack item) {
		switch (item.getType()) {
		case APPLE:
		case BAKED_POTATO:
		case BREAD:
		case CARROT:
		case COOKED_BEEF:
		case COOKED_CHICKEN:
		case COOKED_FISH:
		case COOKED_MUTTON:
		case COOKED_RABBIT:
		case COOKIE:
		case GOLDEN_APPLE:
		case GRILLED_PORK:
		case MELON:
		case MUSHROOM_SOUP:
		case MUTTON:
		case PORK:
		case POTATO:
		case PUMPKIN_PIE:
		case RABBIT_STEW:
			return true;
		default:
			return false;

		}
	}
}
