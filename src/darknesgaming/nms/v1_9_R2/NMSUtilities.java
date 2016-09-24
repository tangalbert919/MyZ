package darknesgaming.nms.v1_9_R2;

import java.util.HashMap;
import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.locales.Locale;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.NMS;
import darknesgaming.nms.v1_9_R2.anvil.CustomContainerAnvil;
import darknesgaming.nms.v1_9_R2.anvil.TileEntityCustomContainerAnvil;
import jordan.sicherman.nms.v1_8_R1.mobs.CustomEntityGiantZombie;
import jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityGuard;
import jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityPigZombie;
import jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityZombie;
import jordan.sicherman.nms.v1_7_R4.mobs.SmartEntity;
import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntityInsentient;
import net.minecraft.server.v1_9_R2.EntityLiving;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.GroupDataEntity;
import net.minecraft.server.v1_9_R2.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_9_R2.World;
import net.minecraft.server.v1_9_R2.WorldServer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

public class NMSUtilities {
	
	   public static void attractEntity(LivingEntity entity, Location inLoc, long duration) {
	        EntityLiving livingEntity = ((CraftLivingEntity) entity).getHandle();

	        if (livingEntity instanceof SmartEntity) {
	            ((SmartEntity) livingEntity).setSmartTarget(inLoc, duration);
	        }

	    }

	    public static boolean murderMessage(LocaleMessage message, Player murdered, Player murderer, Player[] audience) {
	        String str = message.toString(false);

	        if (str.indexOf("$0") >= 0 && str.indexOf("$1") >= 0) {
	            String murdered_pre = murdered.getName();
	            String murderer_pre = murderer.getName();
	            HashMap cache = new HashMap();

	            MyZ.log(message.filter(new Object[] { murdered_pre, murderer_pre}).toString());
	            Player[] aplayer = audience;
	            int i = audience.length;

	            for (int j = 0; j < i; ++j) {
	                Player p = aplayer[j];
	                Locale l = Locale.getLocale(p);
	                JSONMessage m = (JSONMessage) cache.get(l);

	                if (m == null) {
	                    str = message.toString(l, false);
	                    m = (new JSONMessage(str.split("\\$0")[0])).then(ChatColor.getLastColors(str) + murdered_pre).itemTooltip(murdered.getItemInHand()).then(ChatColor.getLastColors(str) + str.split("\\$0")[1].split("\\$1")[0]).then(ChatColor.getLastColors(str) + murderer_pre).itemTooltip(murderer.getItemInHand()).then(ChatColor.getLastColors(str) + (str.split("\\$1").length > 1 ? str.split("\\$1")[1] : ""));
	                    cache.put(l, m);
	                }

	                m.send(p);
	            }

	            return true;
	        } else {
	            return false;
	        }
	    }

	    public static boolean deathMessage(LocaleMessage message, Player died, ItemStack displayOnVariable, Player[] audience) {
	        String s = message.toString(false);

	        if (displayOnVariable != null && (s.indexOf("{") < 0 || s.indexOf("}") < 0)) {
	            return false;
	        } else {
	            HashMap cache = new HashMap();
	            String died_pre = died.getName();

	            MyZ.log(message.smartFilter("\\{", "").smartFilter("\\}", "").filter(new Object[] { died_pre}).toString());
	            message.clearSmartFilter();
	            Player[] aplayer = audience;
	            int i = audience.length;

	            for (int j = 0; j < i; ++j) {
	                Player p = aplayer[j];
	                Locale l = Locale.getLocale(p);
	                JSONMessage msg = (JSONMessage) cache.get(l);

	                if (msg == null) {
	                    String str = message.filter(new Object[] { died_pre}).toString(l);

	                    msg = (new JSONMessage(str.split("\\{")[0])).then(ChatColor.getLastColors(str) + str.split("\\{")[1].split("\\}")[0]).itemTooltip(displayOnVariable).then(ChatColor.getLastColors(str) + (str.split("\\}").length > 1 ? str.split("\\}")[1] : ""));
	                    cache.put(l, msg);
	                }

	                msg.send(p);
	            }

	            return true;
	        }
	    }

	    public static void openAnvil(Player player, Block anvil, EngineerRecipe... recipe) {
	        EntityHuman human = (EntityHuman) NMS.castToNMS(player);
	        EntityPlayer ePlayer = (EntityPlayer) human;

	        if (!World.haveWeSilencedAPhysicsCrash) {
	            CustomContainerAnvil container = new CustomContainerAnvil(human.inventory, human.world, 0, 0, 0, human, anvil != null);
	            int count = ePlayer.nextContainerCounter();

	            ePlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(count, 8, "Repairing", 0, true));
	            ePlayer.activeContainer = container;
	            ePlayer.activeContainer.windowId = count;
	            ePlayer.activeContainer.addSlotListener(ePlayer);
	            if (recipe != null && recipe.length == 1) {
	                ((CustomContainerAnvil) human.activeContainer).activeRecipe = recipe[0];
	                ((CustomContainerAnvil) human.activeContainer).result.setItem(0, CraftItemStack.asNMSCopy(recipe[0].getOutput()));
	                ((CustomContainerAnvil) human.activeContainer).process.setItem(0, CraftItemStack.asNMSCopy(recipe[0].getInput(0)));
	                ((CustomContainerAnvil) human.activeContainer).process.setItem(1, CraftItemStack.asNMSCopy(recipe[0].getInput(1)));
	            }
	        }

	    }

	    public static boolean sendInventoryUpdate(Player player, int slot) {
	        EntityPlayer human = (EntityPlayer) NMS.castToNMS(player);

	        return ((CustomContainerAnvil) human.activeContainer).updateOn(slot);
	    }

	    public static void spawnEntity(Location inLoc, EntityType type) {
	        WorldServer world = ((CraftWorld) inLoc.getWorld()).getHandle();
	        Object entity;

	        switch (NMSUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[type.ordinal()]) {
	        case 1:
	            entity = new CustomEntityZombie(world);
	            break;

	        case 2:
	            entity = new CustomEntityGiantZombie(world);
	            break;

	        case 3:
	            entity = new CustomEntityPigZombie(world);
	            break;

	        case 4:
	            entity = new CustomEntityGuard(world);
	            break;

	        default:
	            return;
	        }

	        ((EntityInsentient) entity).setLocation(inLoc.getX(), inLoc.getY(), inLoc.getZ(), inLoc.getYaw(), inLoc.getPitch());
	        ((EntityInsentient) entity).prepare((GroupDataEntity) null);
	        world.addEntity((Entity) entity, SpawnReason.CUSTOM);
	    }

	    static class SyntheticClass_1 {

	        static final int[] $SwitchMap$org$bukkit$entity$EntityType = new int[EntityType.values().length];

	        static {
	            try {
	                NMSUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.ZOMBIE.ordinal()] = 1;
	            } catch (NoSuchFieldError nosuchfielderror) {
	                ;
	            }

	            try {
	                NMSUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.GIANT.ordinal()] = 2;
	            } catch (NoSuchFieldError nosuchfielderror1) {
	                ;
	            }

	            try {
	                NMSUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.PIG_ZOMBIE.ordinal()] = 3;
	            } catch (NoSuchFieldError nosuchfielderror2) {
	                ;
	            }

	            try {
	                NMSUtilities.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.SKELETON.ordinal()] = 4;
	            } catch (NoSuchFieldError nosuchfielderror3) {
	                ;
	            }

	        }
	    }
}
