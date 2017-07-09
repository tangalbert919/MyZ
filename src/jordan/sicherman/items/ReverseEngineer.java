package jordan.sicherman.items;

import jordan.sicherman.utilities.configuration.ConfigEntries;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ReverseEngineer {

    public static ItemStack getDecomposition(ItemStack item) {
        Material m = getRawMaterial(item);
        int a = getDecompositionAmount(item);

        return m != null && a != 0 ? ItemUtilities.getInstance().tag(new ItemStack(m, a), ItemTag.CHAIN) : item;
    }

    public static boolean isUpgradable(ItemStack item) {
        switch (ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[item.getType().ordinal()]) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
            return true;

        default:
            return false;
        }
    }

    public static Material getRawMaterial(ItemStack item) {
        switch (ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[item.getType().ordinal()]) {
        case 1:
        case 2:
        case 3:
        case 28:
        case 29:
            return Material.STICK;

        case 4:
        case 30:
        case 31:
        case 32:
        case 33:
            return Material.COBBLESTONE;

        case 5:
        case 20:
        case 21:
        case 22:
        case 23:
        case 34:
        case 35:
        case 36:
        case 37:
        case 38:
        case 39:
            return Material.GOLD_INGOT;

        case 6:
        case 16:
        case 17:
        case 18:
        case 19:
        case 42:
        case 43:
        case 44:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
            return Material.IRON_INGOT;

        case 7:
        case 24:
        case 25:
        case 26:
        case 27:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
            return Material.DIAMOND;

        case 8:
        case 9:
        case 10:
        case 11:
        case 56:
            return Material.LEATHER;

        case 12:
        case 13:
        case 14:
        case 15:
        case 40:
        case 41:
            return Material.IRON_FENCE;

        default:
            return null;
        }
    }

    public static int getDecompositionAmount(ItemStack item) {
        switch (ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[item.getType().ordinal()]) {
        case 1:
        case 2:
        case 28:
        case 39:
        case 40:
        case 41:
        case 42:
        case 48:
        case 56:
            return 1;

        case 3:
        case 9:
        case 11:
        case 13:
        case 15:
        case 17:
        case 19:
        case 21:
        case 23:
        case 25:
        case 27:
            return 3;

        case 4:
        case 5:
        case 6:
        case 7:
        case 10:
        case 14:
        case 18:
        case 22:
        case 26:
        case 30:
        case 31:
        case 32:
        case 33:
        case 34:
        case 35:
        case 36:
        case 37:
        case 44:
        case 45:
        case 46:
        case 47:
        case 49:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
            return 2;

        case 8:
        case 12:
        case 16:
        case 20:
        case 24:
        case 29:
            return 4;

        case 38:
        case 43:
        case 50:
            return 5;

        default:
            return 0;
        }
    }

    public static int getUpgradeAmount() {
        return ((Integer) ConfigEntries.UPGRADE_AMOUNT.getValue()).intValue();
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$org$bukkit$Material = new int[Material.values().length];

        static {
            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.FISHING_ROD.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.BOW.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.WOOD_SWORD.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.STONE_SWORD.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_SWORD.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_SWORD.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_SWORD.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.LEATHER_CHESTPLATE.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.LEATHER_LEGGINGS.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.LEATHER_BOOTS.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.LEATHER_HELMET.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CHAINMAIL_CHESTPLATE.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CHAINMAIL_LEGGINGS.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CHAINMAIL_BOOTS.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.CHAINMAIL_HELMET.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_CHESTPLATE.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_LEGGINGS.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_BOOTS.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_HELMET.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_CHESTPLATE.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_LEGGINGS.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_BOOTS.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_HELMET.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_CHESTPLATE.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_LEGGINGS.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_BOOTS.ordinal()] = 26;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_HELMET.ordinal()] = 27;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.BOWL.ordinal()] = 28;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.WOOD.ordinal()] = 29;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.STONE_SPADE.ordinal()] = 30;
            } catch (NoSuchFieldError nosuchfielderror29) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.STONE_PICKAXE.ordinal()] = 31;
            } catch (NoSuchFieldError nosuchfielderror30) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.STONE_AXE.ordinal()] = 32;
            } catch (NoSuchFieldError nosuchfielderror31) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.STONE_HOE.ordinal()] = 33;
            } catch (NoSuchFieldError nosuchfielderror32) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_SPADE.ordinal()] = 34;
            } catch (NoSuchFieldError nosuchfielderror33) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_PICKAXE.ordinal()] = 35;
            } catch (NoSuchFieldError nosuchfielderror34) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_AXE.ordinal()] = 36;
            } catch (NoSuchFieldError nosuchfielderror35) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_HOE.ordinal()] = 37;
            } catch (NoSuchFieldError nosuchfielderror36) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_BARDING.ordinal()] = 38;
            } catch (NoSuchFieldError nosuchfielderror37) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_ORE.ordinal()] = 39;
            } catch (NoSuchFieldError nosuchfielderror38) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_ORE.ordinal()] = 40;
            } catch (NoSuchFieldError nosuchfielderror39) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_INGOT.ordinal()] = 41;
            } catch (NoSuchFieldError nosuchfielderror40) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.COMPASS.ordinal()] = 42;
            } catch (NoSuchFieldError nosuchfielderror41) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_BARDING.ordinal()] = 43;
            } catch (NoSuchFieldError nosuchfielderror42) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_SPADE.ordinal()] = 44;
            } catch (NoSuchFieldError nosuchfielderror43) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_PICKAXE.ordinal()] = 45;
            } catch (NoSuchFieldError nosuchfielderror44) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_AXE.ordinal()] = 46;
            } catch (NoSuchFieldError nosuchfielderror45) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_HOE.ordinal()] = 47;
            } catch (NoSuchFieldError nosuchfielderror46) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.SHEARS.ordinal()] = 48;
            } catch (NoSuchFieldError nosuchfielderror47) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.BUCKET.ordinal()] = 49;
            } catch (NoSuchFieldError nosuchfielderror48) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_BARDING.ordinal()] = 50;
            } catch (NoSuchFieldError nosuchfielderror49) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_ORE.ordinal()] = 51;
            } catch (NoSuchFieldError nosuchfielderror50) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_SPADE.ordinal()] = 52;
            } catch (NoSuchFieldError nosuchfielderror51) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_PICKAXE.ordinal()] = 53;
            } catch (NoSuchFieldError nosuchfielderror52) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_AXE.ordinal()] = 54;
            } catch (NoSuchFieldError nosuchfielderror53) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_HOE.ordinal()] = 55;
            } catch (NoSuchFieldError nosuchfielderror54) {
                ;
            }

            try {
                ReverseEngineer.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.RABBIT_HIDE.ordinal()] = 56;
            } catch (NoSuchFieldError nosuchfielderror55) {
                ;
            }

        }
    }
}
