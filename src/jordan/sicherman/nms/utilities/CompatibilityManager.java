package jordan.sicherman.nms.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.v1_8_R1.NMSUtilities;
import jordan.sicherman.nms.v1_8_R1.mobs.CustomEntityType;
import jordan.sicherman.utilities.configuration.DeathCause;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CompatibilityManager {

    private static final Random random = new Random();
    private static Class craftChest;
    private static Class tileEntity;
    private static Field inventoryField;
    private static Method a;

    public static boolean sendDeathMessage(DeathCause cause, Player murdered, Player murderer, Player[] audience) {
        if (murderer != null) {
            return sendMurderedMessage(cause.getMessages(), murdered, murderer, audience);
        } else {
            switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[cause.ordinal()]) {
            case 1:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.ARROW), audience);

            case 2:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.CACTUS), audience);

            case 3:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.WATER_BUCKET), audience);

            case 4:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.ENDER_PEARL), audience);

            case 5:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.FIRE), audience);

            case 6:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.SKULL_ITEM, 1, (short) 2), audience);

            case 7:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.LAVA_BUCKET), audience);

            case 8:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.POTION, 1, (short) 16460), audience);

            case 9:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.SKULL_ITEM, 1, (short) 2), audience);

            case 10:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.POTION, 1, (short) 8196), audience);

            case 11:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.STONE), audience);

            case 12:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.SKULL_ITEM, 1, (short) 2), audience);

            case 13:
                return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.BREAD), audience);

            default:
                return sendDeathMessage(cause.getMessages(), murdered, (ItemStack) null, audience);
            }
        }
    }

    private static boolean sendMurderedMessage(LocaleMessage[] messages, Player murdered, Player murderer, Player[] audience) {
        LocaleMessage message = messages[CompatibilityManager.random.nextInt(messages.length)];

        switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.compatibilityVersion.ordinal()]) {
        case 1:
            return NMSUtilities.murderMessage(message, murdered, murderer, audience);

        case 2:
            return jordan.sicherman.nms.v1_7_R4.NMSUtilities.murderMessage(message, murdered, murderer, audience);

        default:
            return true;
        }
    }

    private static boolean sendDeathMessage(LocaleMessage[] messages, Player died, ItemStack displayOnVariable, Player[] audience) {
        LocaleMessage message = messages[CompatibilityManager.random.nextInt(messages.length)];

        if (displayOnVariable == null) {
            message.smartFilter("\\{", "").smartFilter("\\}", "").filter(new Object[] { died.getName()}).sendToCrowd(audience);
            MyZ.log(message.toString());
            message.clearSmartFilter();
            return true;
        } else {
            switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.compatibilityVersion.ordinal()]) {
            case 1:
                return NMSUtilities.deathMessage(message, died, displayOnVariable, audience);

            case 2:
                return jordan.sicherman.nms.v1_7_R4.NMSUtilities.deathMessage(message, died, displayOnVariable, audience);

            default:
                return true;
            }
        }
    }

    public static void interactAnvil(Player player, Block isAnvil, EngineerRecipe... recipe) {
        switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.compatibilityVersion.ordinal()]) {
        case 1:
            NMSUtilities.openAnvil(player, isAnvil, recipe);
            break;

        case 2:
            jordan.sicherman.nms.v1_7_R4.NMSUtilities.openAnvil(player, isAnvil, recipe);
        }

    }

    public static boolean sendInventoryUpdate(Player player, int slot) {
        switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.compatibilityVersion.ordinal()]) {
        case 1:
            return NMSUtilities.sendInventoryUpdate(player, slot);

        case 2:
            return jordan.sicherman.nms.v1_7_R4.NMSUtilities.sendInventoryUpdate(player, slot);

        default:
            return false;
        }
    }

    public static void registerEntities() {
        switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.compatibilityVersion.ordinal()]) {
        case 1:
            CustomEntityType.registerEntities();
            break;

        case 2:
            jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityType.registerEntities();
        }

    }

    public static void unregisterEntities() {
        switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.compatibilityVersion.ordinal()]) {
        case 1:
            CustomEntityType.unregisterEntities();
            break;

        case 2:
            jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityType.unregisterEntities();
        }

    }

    public static void spawnEntity(Location inLoc, EntityType type) {
        switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.compatibilityVersion.ordinal()]) {
        case 1:
            NMSUtilities.spawnEntity(inLoc, type);
            break;

        case 2:
            jordan.sicherman.nms.v1_7_R4.NMSUtilities.spawnEntity(inLoc, type);
        }

    }

    public static void renameChest(Chest block, String name) {
        try {
            Object exc = CompatibilityManager.craftChest.cast(block.getBlock().getState());

            CompatibilityManager.inventoryField.setAccessible(true);
            Object tileChest = CompatibilityManager.tileEntity.cast(CompatibilityManager.inventoryField.get(exc));

            CompatibilityManager.a.invoke(tileChest, new Object[] { name});
            CompatibilityManager.inventoryField.setAccessible(false);
        } catch (Exception exception) {
            ;
        }

    }

    public static void attractEntity(LivingEntity entity, Location inLoc, long duration) {
        switch (CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.compatibilityVersion.ordinal()]) {
        case 1:
            NMSUtilities.attractEntity(entity, inLoc, duration);
            break;

        case 2:
            jordan.sicherman.nms.v1_7_R4.NMSUtilities.attractEntity(entity, inLoc, duration);
        }

    }

    static {
        try {
            CompatibilityManager.craftChest = Class.forName("org.bukkit.craftbukkit." + NMS.version + ".block.CraftChest");
            CompatibilityManager.inventoryField = CompatibilityManager.craftChest.getDeclaredField("chest");
            CompatibilityManager.tileEntity = Class.forName("net.minecraft.server." + NMS.version + ".TileEntityChest");
            CompatibilityManager.a = CompatibilityManager.tileEntity.getMethod("a", new Class[] { String.class});
        } catch (Exception exception) {
            ;
        }

    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$utilities$configuration$DeathCause;
        static final int[] $SwitchMap$jordan$sicherman$nms$utilities$NMS$Version = new int[NMS.Version.values().length];

        static {
            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.Version.v1_8_R1.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$utilities$NMS$Version[NMS.Version.v1_7_R4.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            $SwitchMap$jordan$sicherman$utilities$configuration$DeathCause = new int[DeathCause.values().length];

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.ARROW.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.CACTUS.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.DROWNING.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.EXPLOSION.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.FIRE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.GIANT.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.LAVA.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.MAGIC.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.PIGMAN.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.POISON.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.SUFFOCATION.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.ZOMBIE.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                CompatibilityManager.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$DeathCause[DeathCause.STARVE.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

        }
    }
}
