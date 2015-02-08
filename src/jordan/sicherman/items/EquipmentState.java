/**
 * 
 */
package jordan.sicherman.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.configuration.ConfigEntries;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Jordan
 * 
 */
public enum EquipmentState {

	// Fishing Rod: Weak > Normal > Lightweight

	// Bow: Broken > Cracked > Slack > Normal > Reinforced > Precise > Sharpened

	// Sword: Shattered > Devastated > Broken > Weakened > Dull > Normal >
	// Sharpened > Tempered

	// Armor: Devastated > Broken > Weakened > Normal > Reinforced > Fortified >
	// Ornate

	DEVASTATED(1, 0, -1, -1, LocaleMessage.DEVASTATED, ConfigEntries.DEVASTATED_MOD), BROKEN(2, 1, 0, -1, LocaleMessage.BROKEN,
			ConfigEntries.BROKEN_MOD), NORMAL(5, 3, 3, 1, null, null), REINFORCED(-1, 4, 4, -1, LocaleMessage.REINFORCED,
			ConfigEntries.REINFORCED_MOD), SHARPENED(6, -1, -1, -1, LocaleMessage.SHARPENED, ConfigEntries.SHARPENED_MOD), TEMPERED(7, -1,
			0, -1, LocaleMessage.TEMPERED, ConfigEntries.TEMPERED_MOD), SHATTERED(0, -1, 0, -1, LocaleMessage.SHATTERED,
			ConfigEntries.SHATTERED_MOD), DULL(4, -1, 0, -1, LocaleMessage.DULL, ConfigEntries.DULL_MOD), WEAKENED(3, 2, 0, -1,
			LocaleMessage.WEAKENED, ConfigEntries.WEAKENED_MOD), FORTIFIED(-1, 5, 0, -1, LocaleMessage.FORTIFIED,
			ConfigEntries.FORTIFIED_MOD), ORNATE(-1, 6, 0, -1, LocaleMessage.ORNATE, ConfigEntries.ORNATE_MOD), CRACKED(-1, -1, 1, -1,
			LocaleMessage.CRACKED, ConfigEntries.CRACKED_MOD), SLACK(-1, -1, 2, -1, LocaleMessage.SLACK, ConfigEntries.SLACK_MOD), PRECISE(
			-1, -1, 5, -1, LocaleMessage.PRECISE, ConfigEntries.PRECISE_MOD), BOW_SHARPENED(-1, -1, 6, -1, LocaleMessage.BOW_SHARPENED,
			ConfigEntries.BOW_SHARPENED_MOD), GRAPPLE_WEAK(-1, -1, -1, 0, LocaleMessage.WEAK, null), LIGHTWEIGHT(-1, -1, -1, 2,
			LocaleMessage.LIGHTWEIGHT_GRAPPLE, null);

	private static final int MAX_SWORD = 7, MAX_ARMOR = 6, MAX_BOW = 6, MAX_ROD = 2;

	private final List<String> loreModifier;
	private final int sP, gP, bP, rP, effectiveness;

	public boolean isCompatibleWith(Material material) {
		if (material == null) { return false; }
		if (this == NORMAL) { return true; }

		if (isBow(material)) {
			switch (this) {
			case BROKEN:
			case CRACKED:
			case SLACK:
			case REINFORCED:
			case PRECISE:
			case BOW_SHARPENED:
				return true;
			default:
				return false;
			}
		} else if (isRod(material)) {
			switch (this) {
			case GRAPPLE_WEAK:
			case LIGHTWEIGHT:
				return true;
			default:
				return false;
			}
		} else if (isSword(material)) {
			switch (this) {
			case SHATTERED:
			case DEVASTATED:
			case BROKEN:
			case WEAKENED:
			case DULL:
			case SHARPENED:
			case TEMPERED:
				return true;
			default:
				return false;
			}
		} else if (isArmor(material)) {
			switch (this) {
			case DEVASTATED:
			case BROKEN:
			case WEAKENED:
			case REINFORCED:
			case FORTIFIED:
			case ORNATE:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	public boolean isCompatibleWith(ItemStack item) {
		return isCompatibleWith(item.getType());
	}

	private EquipmentState(int sP, int gP, int bP, int rP, LocaleMessage loreModifier, ConfigEntries entry) {
		this.sP = sP;
		this.gP = gP;
		this.bP = bP;
		this.rP = rP;

		if (entry == null) {
			effectiveness = 100;
		} else {
			effectiveness = entry.<Integer> getValue();
		}

		String lore = loreModifier == null ? null : bP == 5 ? loreModifier.filter(effectiveness,
				ConfigEntries.BOW_PRECISE_HEADSHOT_MOD.<Integer> getValue()).toString() : bP == 6 ? loreModifier.filter(effectiveness,
				ConfigEntries.BOW_SHARPENED_COMBAT_MOD.<Integer> getValue()).toString() : loreModifier.filter(effectiveness).toString();
		if (lore != null) {
			String[] cLore = lore.split("~");
			String[] loreBuilder = new String[cLore.length];
			for (int i = 0; i < cLore.length; i++) {
				loreBuilder[i] = ChatColor.RESET + cLore[i];
			}
			this.loreModifier = Arrays.asList(loreBuilder);
		} else {
			this.loreModifier = null;
		}
	}

	public int getPosition(ItemStack itemOn) {
		boolean s = isSword(itemOn.getType());
		boolean b = isBow(itemOn.getType());
		boolean r = isRod(itemOn.getType());
		boolean a = isArmor(itemOn.getType());
		return s ? sP : b ? bP : r ? rP : a ? gP : -1;
	}

	public List<String> getLoreModifier() {
		return loreModifier;
	}

	public double getEffectiveness() {
		return effectiveness / 100.0;
	}

	public static double getEffectiveness(ItemStack item) {
		return getState(item).getEffectiveness();
	}

	public static ItemStack applyPrevious(ItemStack item) {
		EquipmentState other = getPrevious(item);
		return other == null ? item : other.applyTo(item);
	}

	public static ItemStack applyNext(ItemStack item) {
		EquipmentState other = getNext(item);
		return other == null ? item : other.applyTo(item);
	}

	public static EquipmentState getNext(ItemStack item) {
		boolean s = isSword(item.getType());
		boolean b = isBow(item.getType());
		boolean r = isRod(item.getType());
		boolean a = isArmor(item.getType());
		if (!s && !b && !r && !a) { return null; }

		EquipmentState current = getState(item);
		int cur = s ? current.sP : b ? current.bP : r ? current.rP : current.gP;
		int max = s ? MAX_SWORD : b ? MAX_BOW : r ? MAX_ROD : MAX_ARMOR;
		if (cur >= max) { return null; }
		cur++;
		for (EquipmentState state : values()) {
			if (state.isCompatibleWith(item)) {
				if (cur == (s ? state.sP : b ? state.bP : r ? state.rP : state.gP)) { return state; }
			}
		}
		return null;
	}

	public static EquipmentState getPrevious(ItemStack item) {
		boolean s = isSword(item.getType());
		boolean b = isBow(item.getType());
		boolean r = isRod(item.getType());
		boolean a = isArmor(item.getType());
		if (!s && !b && !r && !a) { return null; }

		EquipmentState current = getState(item);
		int cur = s ? current.sP : b ? current.bP : r ? current.rP : current.gP;
		if (cur <= 0) { return null; }
		cur--;
		for (EquipmentState state : values()) {
			if (state.isCompatibleWith(item)) {
				if (cur == (s ? state.sP : b ? state.bP : r ? state.rP : state.gP)) { return state; }
			}
		}
		return null;
	}

	private static boolean isSword(Material item) {
		switch (item) {
		case WOOD_SWORD:
		case STONE_SWORD:
		case GOLD_SWORD:
		case IRON_SWORD:
		case DIAMOND_SWORD:
			return true;
		default:
			return false;
		}
	}

	public static boolean isArmor(Material item) {
		switch (item) {
		case LEATHER_HELMET:
		case LEATHER_CHESTPLATE:
		case LEATHER_LEGGINGS:
		case LEATHER_BOOTS:
		case GOLD_HELMET:
		case GOLD_CHESTPLATE:
		case GOLD_LEGGINGS:
		case GOLD_BOOTS:
		case CHAINMAIL_HELMET:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_LEGGINGS:
		case CHAINMAIL_BOOTS:
		case DIAMOND_HELMET:
		case DIAMOND_CHESTPLATE:
		case DIAMOND_LEGGINGS:
		case DIAMOND_BOOTS:
		case IRON_HELMET:
		case IRON_CHESTPLATE:
		case IRON_LEGGINGS:
		case IRON_BOOTS:
			return true;
		default:
			return false;
		}
	}

	private static boolean isBow(Material item) {
		return item == Material.BOW;
	}

	private static boolean isRod(Material item) {
		return item == Material.FISHING_ROD;
	}

	public ItemStack applyTo(ItemStack item) {
		if (!isCompatibleWith(item)) { return item; }
		EquipmentState state = getState(item);
		if (state != NORMAL) {
			removeFrom(item);
		}

		if (getLoreModifier() == null) { return item; }

		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) {
			meta.setLore(getLoreModifier());
		} else {
			List<String> lore = new ArrayList<String>(meta.getLore());
			for (int i = getLoreModifier().size() - 1; i >= 0; i--) {
				lore.add(0, getLoreModifier().get(i));
			}
			meta.setLore(lore);
		}
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack removeFrom(ItemStack item) {
		EquipmentState state = getState(item);
		if (state == NORMAL) { return item; }

		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>(meta.getLore());
		for (int i = 0; i < meta.getLore().size(); i++) {
			if (meta.getLore().get(i).equals(state.getLoreModifier().get(i))) {
				for (int x = 0; x < state.getLoreModifier().size(); x++) {
					try {
						lore.remove(i);
					} catch (Exception e) {
					}
				}
				break;
			}
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static EquipmentState getState(ItemStack item) {
		if (item == null) { return NORMAL; }
		ItemMeta meta = item.getItemMeta();
		if (meta == null || !meta.hasLore()) { return NORMAL; }

		for (EquipmentState state : EquipmentState.values()) {
			boolean match = false;
			for (int i = 0; i < meta.getLore().size(); i++) {
				try {
					if (meta.getLore().get(i).equals(state.getLoreModifier().get(i))) {
						match = true;
						for (int x = 0; x < state.getLoreModifier().size(); x++) {
							try {
								if (!meta.getLore().get(i + x).equals(state.getLoreModifier().get(x))) {
									match = false;
								}
							} catch (Exception e) {
								match = false;
							}
						}
						break;
					}
				} catch (Exception e) {
					break;
				}
			}
			if (match) { return state; }
		}
		return NORMAL;
	}

	public static EquipmentState fromString(String string) {
		for (EquipmentState state : values()) {
			if (state.toString().toLowerCase().equals(string)) { return state; }
		}
		return null;
	}
}
