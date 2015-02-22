/**
 * 
 */
package jordan.sicherman.utilities.configuration;

import jordan.sicherman.player.User;
import jordan.sicherman.player.User.UFiles;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Jordan
 * 
 */
public enum UserEntries {

	ZOMBIE_KILLS("kills.zombie", EntryType.INTEGER, UFiles.STATISTICS, 0), PLAYER_KILLS("kills.player", EntryType.INTEGER,
			UFiles.STATISTICS, 0), PIGMAN_KILLS("kills.pigman", EntryType.INTEGER, UFiles.STATISTICS, 0), GIANT_KILLS("kills.giant",
			EntryType.INTEGER, UFiles.STATISTICS, 0), FRIENDS("friends", EntryType.INTEGER, UFiles.STATISTICS, 0), DRINKS("drinks",
			EntryType.INTEGER, UFiles.STATISTICS, 0), DEATHS("deaths", EntryType.INTEGER, UFiles.STATISTICS, 0), THIRST("thirst",
			EntryType.DOUBLE, UFiles.TRACKED, new Double(ConfigEntries.THIRST_MAX_DEFAULT.<Integer> getValue() + 1.0)), POISONED(
			"poisoned", EntryType.BOOLEAN, UFiles.TRACKED, false), BLEEDING("bleeding", EntryType.BOOLEAN, UFiles.TRACKED, false), PLAYING(
			"in_game", EntryType.BOOLEAN, UFiles.TRACKED, false), IMMUNITY("immunity", EntryType.INTEGER, UFiles.SKILLS, 0), SKIN(
			"toughness", EntryType.INTEGER, UFiles.SKILLS, 0), KIT_HELMET("equipment.helmet", EntryType.ITEMSTACK, UFiles.KIT, null), KIT_CHESTPLATE(
			"equipment.chestplate", EntryType.ITEMSTACK, UFiles.KIT, null), KIT_LEGGINGS("equipment.leggings", EntryType.ITEMSTACK,
			UFiles.KIT, null), KIT_BOOTS("equipment.boots", EntryType.ITEMSTACK, UFiles.KIT, null), KIT_INVENTORY("equipment.inventory",
			EntryType.LIST, UFiles.KIT, null), CURRENT_ZOMBIE_KILLS("tracked.kills.zombie", EntryType.INTEGER, UFiles.TRACKED, 0), CURRENT_PLAYER_KILLS(
			"tracked.kills.player", EntryType.INTEGER, UFiles.TRACKED, 0), CURRENT_PIGMAN_KILLS("tracked.kills.pigman", EntryType.INTEGER,
			UFiles.TRACKED, 0), CURRENT_GIANT_KILLS("tracked.kills.giant", EntryType.INTEGER, UFiles.TRACKED, 0);

	public static UserEntries fromString(String entry) {
		for (UserEntries uEntry : values()) {
			if (uEntry.key.equals(entry)) { return uEntry; }
		}
		return null;
	}

	private final String key;
	private final EntryType type;
	private final UFiles file;
	private final Object defaultValue;

	private UserEntries(String key, EntryType type, UFiles file, Object defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
		this.type = type;
		this.file = file;
	}

	public String getKey() {
		return key;
	}

	public UFiles getFile() {
		return file;
	}

	public EntryType getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public <E> E getValue(FileConfiguration inConfig) {
		switch (type) {
		case STRING:
			return (E) (!inConfig.contains(key) ? defaultValue : inConfig.getString(key));
		case INTEGER:
			return (E) (!inConfig.contains(key) ? defaultValue : inConfig.getInt(key));
		case BOOLEAN:
			return (E) (!inConfig.contains(key) ? defaultValue : inConfig.getBoolean(key));
		case ITEMSTACK:
			return (E) (!inConfig.contains(key) ? defaultValue : inConfig.getItemStack(key));
		case DOUBLE:
			return (E) (!inConfig.contains(key) ? defaultValue : inConfig.getDouble(key));
		case LIST:
			return (E) (!inConfig.contains(key) ? defaultValue : inConfig.getList(key));
		case CONFIGURATION_SECTION:
			return (E) (!file.getFile().isConfigurationSection(key) ? makeDefaultSection() : file.getFile().getConfigurationSection(key));
		default:
			return null;
		}
	}

	private ConfigurationSection makeDefaultSection() {
		ConfigurationSection section = file.getFile().createSection(key);

		return section;
	}

	public <E> E getValue(User userFor) {
		return getValue(userFor.getFile(file));
	}
}