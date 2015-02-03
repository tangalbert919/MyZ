/**
 * 
 */
package jordan.sicherman.items;

import jordan.sicherman.utilities.configuration.ConfigEntries;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public class ReverseEngineer {

	public static ItemStack getDecomposition(ItemStack item) {
		Material m = getRawMaterial(item);
		int a = getDecompositionAmount(item);

		if (m != null && a != 0) { return ItemUtilities.getInstance().tag(new ItemStack(m, a), ItemTag.CHAIN); }
		return item;
	}

	public static boolean isUpgradable(ItemStack item) {
		switch (item.getType()) {
		case FISHING_ROD:
		case BOW:
		case WOOD_SWORD:
		case STONE_SWORD:
		case GOLD_SWORD:
		case IRON_SWORD:
		case DIAMOND_SWORD:
		case LEATHER_CHESTPLATE:
		case LEATHER_LEGGINGS:
		case LEATHER_BOOTS:
		case LEATHER_HELMET:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_LEGGINGS:
		case CHAINMAIL_BOOTS:
		case CHAINMAIL_HELMET:
		case IRON_CHESTPLATE:
		case IRON_LEGGINGS:
		case IRON_BOOTS:
		case IRON_HELMET:
		case GOLD_CHESTPLATE:
		case GOLD_LEGGINGS:
		case GOLD_BOOTS:
		case GOLD_HELMET:
		case DIAMOND_CHESTPLATE:
		case DIAMOND_LEGGINGS:
		case DIAMOND_BOOTS:
		case DIAMOND_HELMET:
			return true;
		default:
			return false;
		}
	}

	public static Material getRawMaterial(ItemStack item) {
		switch (item.getType()) {
		case WOOD_SWORD:
		case FISHING_ROD:
		case BOW:
		case BOWL:
		case WOOD:
			return Material.STICK;
		case STONE_SWORD:
		case STONE_SPADE:
		case STONE_PICKAXE:
		case STONE_AXE:
		case STONE_HOE:
			return Material.COBBLESTONE;
		case GOLD_SWORD:
		case GOLD_SPADE:
		case GOLD_PICKAXE:
		case GOLD_AXE:
		case GOLD_HOE:
		case GOLD_CHESTPLATE:
		case GOLD_BARDING:
		case GOLD_ORE:
		case GOLD_LEGGINGS:
		case GOLD_HELMET:
		case GOLD_BOOTS:
			return Material.GOLD_INGOT;
		case CHAINMAIL_HELMET:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_LEGGINGS:
		case CHAINMAIL_BOOTS:
		case IRON_ORE:
		case IRON_INGOT:
			return Material.IRON_FENCE;
		case IRON_SWORD:
		case COMPASS:
		case IRON_CHESTPLATE:
		case IRON_BARDING:
		case IRON_SPADE:
		case IRON_PICKAXE:
		case IRON_AXE:
		case IRON_HOE:
		case SHEARS:
		case IRON_LEGGINGS:
		case IRON_HELMET:
		case IRON_BOOTS:
		case BUCKET:
			return Material.IRON_INGOT;
		case DIAMOND_SWORD:
		case DIAMOND_CHESTPLATE:
		case DIAMOND_BARDING:
		case DIAMOND_ORE:
		case DIAMOND_SPADE:
		case DIAMOND_PICKAXE:
		case DIAMOND_AXE:
		case DIAMOND_HOE:
		case DIAMOND_LEGGINGS:
		case DIAMOND_HELMET:
		case DIAMOND_BOOTS:
			return Material.DIAMOND;
		case LEATHER_CHESTPLATE:
		case LEATHER_LEGGINGS:
		case LEATHER_HELMET:
		case RABBIT_HIDE:
		case LEATHER_BOOTS:
			return Material.LEATHER;
		default:
			return null;
		}
	}

	public static int getDecompositionAmount(ItemStack item) {
		switch (item.getType()) {
		case FISHING_ROD:
		case BOW:
		case BOWL:
		case GOLD_ORE:
		case IRON_ORE:
		case IRON_INGOT:
		case COMPASS:
		case SHEARS:
		case RABBIT_HIDE:
			return 1;
		case STONE_SWORD:
		case GOLD_SWORD:
		case IRON_SWORD:
		case DIAMOND_SWORD:
		case STONE_SPADE:
		case IRON_SPADE:
		case GOLD_SPADE:
		case DIAMOND_SPADE:
		case STONE_PICKAXE:
		case IRON_PICKAXE:
		case GOLD_PICKAXE:
		case DIAMOND_PICKAXE:
		case STONE_AXE:
		case GOLD_AXE:
		case DIAMOND_AXE:
		case IRON_AXE:
		case STONE_HOE:
		case GOLD_HOE:
		case IRON_HOE:
		case DIAMOND_HOE:
		case GOLD_BOOTS:
		case CHAINMAIL_BOOTS:
		case LEATHER_BOOTS:
		case DIAMOND_BOOTS:
		case IRON_BOOTS:
		case BUCKET:
		case DIAMOND_ORE:
			return 2;
		case WOOD_SWORD:
		case GOLD_HELMET:
		case CHAINMAIL_HELMET:
		case LEATHER_HELMET:
		case IRON_HELMET:
		case DIAMOND_HELMET:
		case GOLD_LEGGINGS:
		case IRON_LEGGINGS:
		case CHAINMAIL_LEGGINGS:
		case DIAMOND_LEGGINGS:
		case LEATHER_LEGGINGS:
			return 3;
		case WOOD:
		case GOLD_CHESTPLATE:
		case CHAINMAIL_CHESTPLATE:
		case IRON_CHESTPLATE:
		case DIAMOND_CHESTPLATE:
		case LEATHER_CHESTPLATE:
			return 4;
		case GOLD_BARDING:
		case IRON_BARDING:
		case DIAMOND_BARDING:
			return 5;
		default:
			return 0;
		}
	}

	public static int getUpgradeAmount() {
		return ConfigEntries.UPGRADE_AMOUNT.<Integer> getValue();
	}
}
