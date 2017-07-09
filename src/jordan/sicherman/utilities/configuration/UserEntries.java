package jordan.sicherman.utilities.configuration;

import jordan.sicherman.player.User;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public enum UserEntries {

    ZOMBIE_KILLS("kills.zombie", EntryType.INTEGER, User.UFiles.STATISTICS, Integer.valueOf(0)), PLAYER_KILLS("kills.player", EntryType.INTEGER, User.UFiles.STATISTICS, Integer.valueOf(0)), PIGMAN_KILLS("kills.pigman", EntryType.INTEGER, User.UFiles.STATISTICS, Integer.valueOf(0)), GIANT_KILLS("kills.giant", EntryType.INTEGER, User.UFiles.STATISTICS, Integer.valueOf(0)), FRIENDS("friends", EntryType.INTEGER, User.UFiles.STATISTICS, Integer.valueOf(0)), DRINKS("drinks", EntryType.INTEGER, User.UFiles.STATISTICS, Integer.valueOf(0)), DEATHS("deaths", EntryType.INTEGER, User.UFiles.STATISTICS, Integer.valueOf(0)), THIRST("thirst", EntryType.DOUBLE, User.UFiles.TRACKED, new Double((double) ((Integer) ConfigEntries.THIRST_MAX_DEFAULT.getValue()).intValue() + 1.0D)), POISONED("poisoned", EntryType.BOOLEAN, User.UFiles.TRACKED, Boolean.valueOf(false)), BLEEDING("bleeding", EntryType.BOOLEAN, User.UFiles.TRACKED, Boolean.valueOf(false)), PLAYING("in_game", EntryType.BOOLEAN, User.UFiles.TRACKED, Boolean.valueOf(false)), IMMUNITY("immunity", EntryType.INTEGER, User.UFiles.SKILLS, Integer.valueOf(0)), SKIN("toughness", EntryType.INTEGER, User.UFiles.SKILLS, Integer.valueOf(0)), KIT_HELMET("equipment.helmet", EntryType.ITEMSTACK, User.UFiles.KIT, (Object) null), KIT_CHESTPLATE("equipment.chestplate", EntryType.ITEMSTACK, User.UFiles.KIT, (Object) null), KIT_LEGGINGS("equipment.leggings", EntryType.ITEMSTACK, User.UFiles.KIT, (Object) null), KIT_BOOTS("equipment.boots", EntryType.ITEMSTACK, User.UFiles.KIT, (Object) null), KIT_INVENTORY("equipment.inventory", EntryType.LIST, User.UFiles.KIT, (Object) null), CURRENT_ZOMBIE_KILLS("tracked.kills.zombie", EntryType.INTEGER, User.UFiles.TRACKED, Integer.valueOf(0)), CURRENT_PLAYER_KILLS("tracked.kills.player", EntryType.INTEGER, User.UFiles.TRACKED, Integer.valueOf(0)), CURRENT_PIGMAN_KILLS("tracked.kills.pigman", EntryType.INTEGER, User.UFiles.TRACKED, Integer.valueOf(0)), CURRENT_GIANT_KILLS("tracked.kills.giant", EntryType.INTEGER, User.UFiles.TRACKED, Integer.valueOf(0));

    private final String key;
    private final EntryType type;
    private final User.UFiles file;
    private final Object defaultValue;

    public static UserEntries fromString(String entry) {
        UserEntries[] auserentries = values();
        int i = auserentries.length;

        for (int j = 0; j < i; ++j) {
            UserEntries uEntry = auserentries[j];

            if (uEntry.key.equals(entry)) {
                return uEntry;
            }
        }

        return null;
    }

    private UserEntries(String key, EntryType type, User.UFiles file, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.type = type;
        this.file = file;
    }

    public String getKey() {
        return this.key;
    }

    public User.UFiles getFile() {
        return this.file;
    }

    public EntryType getType() {
        return this.type;
    }

    public Object getValue(FileConfiguration inConfig) {
        switch (UserEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[this.type.ordinal()]) {
        case 1:
            return !inConfig.contains(this.key) ? this.defaultValue : inConfig.getString(this.key);

        case 2:
            return !inConfig.contains(this.key) ? this.defaultValue : Integer.valueOf(inConfig.getInt(this.key));

        case 3:
            return !inConfig.contains(this.key) ? this.defaultValue : Boolean.valueOf(inConfig.getBoolean(this.key));

        case 4:
            return !inConfig.contains(this.key) ? this.defaultValue : inConfig.getItemStack(this.key);

        case 5:
            return !inConfig.contains(this.key) ? this.defaultValue : Double.valueOf(inConfig.getDouble(this.key));

        case 6:
            return !inConfig.contains(this.key) ? this.defaultValue : inConfig.getList(this.key);

        case 7:
            return !this.file.getFile().isConfigurationSection(this.key) ? this.makeDefaultSection() : this.file.getFile().getConfigurationSection(this.key);

        default:
            return null;
        }
    }

    private ConfigurationSection makeDefaultSection() {
        ConfigurationSection section = this.file.getFile().createSection(this.key);

        return section;
    }

    public Object getValue(User userFor) {
        return this.getValue(userFor.getFile(this.file));
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$utilities$configuration$EntryType = new int[EntryType.values().length];

        static {
            try {
                UserEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                UserEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.INTEGER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                UserEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                UserEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.ITEMSTACK.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                UserEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.DOUBLE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                UserEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.LIST.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                UserEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.CONFIGURATION_SECTION.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

        }
    }
}
