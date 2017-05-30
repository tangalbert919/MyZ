package jordan.sicherman.utilities;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ManagerManager {

    public static boolean setManager(Player player, boolean manager, ManagerManager.ManagerType of) {
        if (manager && isManager(player)) {
            stopManaging(player);
        }

        if (manager) {
            player.setMetadata(of.getID(), new FixedMetadataValue(MyZ.instance, of.getStartValue()));
            switch (ManagerManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$ManagerManager$ManagerType[of.ordinal()]) {
            case 1:
                if (player.getGameMode() != GameMode.CREATIVE) {
                    player.setAllowFlight(true);
                }
                break;

            case 2:
            case 3:
            case 4:
                player.setGameMode(GameMode.CREATIVE);
            }

            if (of != ManagerManager.ManagerType.SPAWN_KIT) {
                player.getInventory().setItemInMainHand(ItemUtilities.getInstance().getTagItem(ItemTag.WAND, 1));
            }
        } else {
            player.removeMetadata(of.getID(), MyZ.instance);
            switch (ManagerManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$ManagerManager$ManagerType[of.ordinal()]) {
            case 1:
                player.setAllowFlight(false);
                break;

            case 2:
            case 3:
            case 4:
                if (!player.isOp()) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        }

        return true;
    }

    public static boolean isManager(Player player, ManagerManager.ManagerType of) {
        return player.hasMetadata(of.getID());
    }

    public static boolean isManager(Player player) {
        ManagerManager.ManagerType[] amanagermanager_managertype = ManagerManager.ManagerType.values();
        int i = amanagermanager_managertype.length;

        for (int j = 0; j < i; ++j) {
            ManagerManager.ManagerType type = amanagermanager_managertype[j];

            if (isManager(player, type)) {
                return true;
            }
        }

        return false;
    }

    public static void stopManaging(Player player) {
        ManagerManager.ManagerType[] amanagermanager_managertype = ManagerManager.ManagerType.values();
        int i = amanagermanager_managertype.length;

        for (int j = 0; j < i; ++j) {
            ManagerManager.ManagerType type = amanagermanager_managertype[j];

            if (isManager(player, type)) {
                setManager(player, false, type);
            }
        }

    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$utilities$ManagerManager$ManagerType = new int[ManagerManager.ManagerType.values().length];

        static {
            try {
                ManagerManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$ManagerManager$ManagerType[ManagerManager.ManagerType.SPAWN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                ManagerManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$ManagerManager$ManagerType[ManagerManager.ManagerType.ENGINEER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                ManagerManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$ManagerManager$ManagerType[ManagerManager.ManagerType.CHESTS.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                ManagerManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$ManagerManager$ManagerType[ManagerManager.ManagerType.SPAWN_KIT.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }

    public static enum ManagerType {

        SPAWN("spawn_manager", Integer.valueOf(-1)), ENGINEER("engineer_manager", Integer.valueOf(-1)), CHESTS("chest_manager", (Object) null), SPAWN_KIT("spawnkit_manager", (Object) null);

        private final String id;
        private final Object startValue;

        private ManagerType(String id, Object startValue) {
            this.id = "MyZ." + id;
            this.startValue = startValue;
        }

        public String getID() {
            return this.id;
        }

        public Object getStartValue() {
            return this.startValue;
        }
    }
}
