package jordan.sicherman.utilities;

import jordan.sicherman.MyZ;
import jordan.sicherman.player.User;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.EntryType;
import jordan.sicherman.utilities.configuration.FileMember;
import jordan.sicherman.utilities.configuration.FileUtilities;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.OfflinePlayer;

public class DataWrapper {

    public static Object get(OfflinePlayer playerFor, UserEntries entry) {
        switch (DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[entry.getType().ordinal()]) {
        case 1:
            return Boolean.valueOf(User.forPlayer(playerFor).getFile(entry.getFile()).getBoolean(entry.getKey()));

        case 2:
            return Double.valueOf(User.forPlayer(playerFor).getFile(entry.getFile()).getDouble(entry.getKey()));

        case 3:
            return Integer.valueOf(User.forPlayer(playerFor).getFile(entry.getFile()).getInt(entry.getKey()));

        case 4:
            return User.forPlayer(playerFor).getFile(entry.getFile()).getItemStack(entry.getKey());

        case 5:
            return User.forPlayer(playerFor).getFile(entry.getFile()).getList(entry.getKey());

        case 6:
            return User.forPlayer(playerFor).getFile(entry.getFile()).getString(entry.getKey());

        default:
            return null;
        }
    }

    public static void set(OfflinePlayer playerFor, UserEntries entry, Object value) {
        set(playerFor, entry, value, new boolean[] { true, false});
    }

    public static void set(OfflinePlayer playerFor, UserEntries entry, Object value, boolean... options) {
        doSet(User.forPlayer(playerFor), entry, value, options);
        switch (DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[entry.ordinal()]) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
            AchievementManager.getInstance().refresh(playerFor, true);

        default:
        }
    }

    public static void set(User user, String entry, Object value) {
        doSet(user, UserEntries.fromString(entry), value, new boolean[] { true, false});
    }

    private static void doSet(final User userFor, final UserEntries entry, final Object value, final boolean... options) {
        MyZ.instance.getServer().getScheduler().runTask(MyZ.instance, new Runnable() {
            public void run() {
                FileUtilities.set(entry.getKey(), value, userFor, entry.getFile(), options[1]);
            }
        });
    }

    public static void set(ConfigEntries entry, Object value, boolean save) {
        entry.getFile().getFile().set(entry.getKey(), value);
        if (save) {
            FileUtilities.save(new FileMember[] { entry.getFile()});
        }

    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$utilities$configuration$EntryType;
        static final int[] $SwitchMap$jordan$sicherman$utilities$configuration$UserEntries = new int[UserEntries.values().length];

        static {
            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.DEATHS.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.DRINKS.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.FRIENDS.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.GIANT_KILLS.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.PIGMAN_KILLS.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.PLAYER_KILLS.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.ZOMBIE_KILLS.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            $SwitchMap$jordan$sicherman$utilities$configuration$EntryType = new int[EntryType.values().length];

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.DOUBLE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.INTEGER.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.ITEMSTACK.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.LIST.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                DataWrapper.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

        }
    }

    public static enum FlushType {

        SQL_TO_USERDATA, USERDATA_TO_SQL;
    }
}
