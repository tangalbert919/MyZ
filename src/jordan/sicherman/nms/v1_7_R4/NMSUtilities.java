/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4;

import java.util.HashMap;
import java.util.Map;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.locales.Locale;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.NMS;
import jordan.sicherman.nms.v1_7_R4.anvil.CustomContainerAnvil;
import jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityGiantZombie;
import jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityGuard;
import jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityPigZombie;
import jordan.sicherman.nms.v1_7_R4.mobs.CustomEntityZombie;
import jordan.sicherman.nms.v1_7_R4.mobs.SmartEntity;
import jordan.sicherman.utilities.Utilities;
import net.minecraft.server.v1_7_R4.Container;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.GroupDataEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public class NMSUtilities {

	public static void attractEntity(LivingEntity entity, Location inLoc, long duration) {
		EntityLiving livingEntity = ((CraftLivingEntity) entity).getHandle();
		if (livingEntity instanceof SmartEntity) {
			((SmartEntity) livingEntity).setSmartTarget(inLoc, duration);
		}
	}

	public static boolean murderMessage(LocaleMessage message, Player murdered, Player murderer, Player[] audience) {
		String str = message.toString(false);
		if (str.indexOf("$0") < 0 || str.indexOf("$1") < 0) { return false; }

		String murdered_pre = Utilities.getPrefixFor(murdered) + murdered.getName();
		String murderer_pre = Utilities.getPrefixFor(murderer) + murderer.getName();

		Map<Locale, JSONMessage> cache = new HashMap<Locale, JSONMessage>();

		MyZ.log(message.filter(murdered_pre, murderer_pre).toString());

		for (Player p : audience) {
			Locale l = Locale.getLocale(p);
			JSONMessage m = cache.get(l);
			if (m == null) {
				str = message.toString(l, false);
				m = new JSONMessage(str.split("\\$0")[0]).then(ChatColor.getLastColors(str) + murdered_pre)
						.itemTooltip(murdered.getItemInHand()).then(ChatColor.getLastColors(str) + str.split("\\$0")[1].split("\\$1")[0])
						.then(ChatColor.getLastColors(str) + murderer_pre).itemTooltip(murderer.getItemInHand())
						.then(ChatColor.getLastColors(str) + (str.split("\\$1").length > 1 ? str.split("\\$1")[1] : ""));
				cache.put(l, m);
			}
			m.send(p);
		}
		return true;
	}

	public static boolean deathMessage(LocaleMessage message, Player died, ItemStack displayOnVariable, Player[] audience) {
		String s = message.toString(false);
		if (displayOnVariable != null && (s.indexOf("{") < 0 || s.indexOf("}") < 0)) { return false; }

		Map<Locale, JSONMessage> cache = new HashMap<Locale, JSONMessage>();

		String died_pre = Utilities.getPrefixFor(died) + died.getName();

		MyZ.log(message.smartFilter("\\{", "").smartFilter("\\}", "").filter(died_pre).toString());
		message.clearSmartFilter();

		for (Player p : audience) {
			Locale l = Locale.getLocale(p);
			JSONMessage msg = cache.get(l);
			if (msg == null) {
				String str = message.filter(died_pre).toString(l);
				msg = new JSONMessage(str.split("\\{")[0]).then(ChatColor.getLastColors(str) + str.split("\\{")[1].split("\\}")[0])
						.itemTooltip(displayOnVariable)
						.then(ChatColor.getLastColors(str) + (str.split("\\}").length > 1 ? str.split("\\}")[1] : ""));
				cache.put(l, msg);
			}
			msg.send(p);
		}
		return true;
	}

	public static void openAnvil(Player player, Block anvil, EngineerRecipe... recipe) {
		EntityHuman human = (EntityHuman) NMS.castToNMS(player);
		EntityPlayer ePlayer = (EntityPlayer) human;

		if (!human.world.isStatic) {
			Container container = new CustomContainerAnvil(human.inventory, human.world, 0, 0, 0, human, anvil != null);

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
		EntityInsentient entity;
		World world = ((CraftWorld) inLoc.getWorld()).getHandle();

		switch (type) {
		case ZOMBIE:
			entity = new CustomEntityZombie(world);
			break;
		case GIANT:
			entity = new CustomEntityGiantZombie(world);
			break;
		case PIG_ZOMBIE:
			entity = new CustomEntityPigZombie(world);
			break;
		case SKELETON:
			entity = new CustomEntityGuard(world);
			break;
		default:
			return;
		}

		entity.setLocation(inLoc.getX(), inLoc.getY(), inLoc.getZ(), inLoc.getYaw(), inLoc.getPitch());
		entity.prepare((GroupDataEntity) null);
		world.addEntity(entity, SpawnReason.CUSTOM);
	}
}
