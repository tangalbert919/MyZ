/**
 * 
 */
package jordan.sicherman.nms.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.DeathCause;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public class CompatibilityManager {

	public static boolean sendDeathMessage(DeathCause cause, Player murdered, Player murderer, Player[] audience) {
		if (murderer != null) {
			return sendMurderedMessage(cause.getMessages(), murdered, murderer, audience);
		} else {
			switch (cause) {
			case ARROW:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.ARROW), audience);
			case CACTUS:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.CACTUS), audience);
			case DROWNING:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.WATER_BUCKET), audience);
			case EXPLOSION:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.ENDER_PEARL), audience);
			case FIRE:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.FIRE), audience);
			case GIANT:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.SKULL_ITEM, 1, (short) 2), audience);
			case LAVA:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.LAVA_BUCKET), audience);
			case MAGIC:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.POTION, 1, (short) 16460), audience);
			case PIGMAN:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.SKULL_ITEM, 1, (short) 2), audience);
			case POISON:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.POTION, 1, (short) 8196), audience);
			case SUFFOCATION:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.STONE), audience);
			case ZOMBIE:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.SKULL_ITEM, 1, (short) 2), audience);
			case STARVE:
				return sendDeathMessage(cause.getMessages(), murdered, new ItemStack(Material.BREAD), audience);
			default:
				return sendDeathMessage(cause.getMessages(), murdered, null, audience);
			}
		}
	}

	private static final Random random = new Random();

	private static boolean sendMurderedMessage(LocaleMessage[] messages, Player murdered, Player murderer, Player[] audience) {
		LocaleMessage message = messages[random.nextInt(messages.length)];

		switch (NMS.compatibilityVersion) {
		case v1_8_R1:
			return jordan.sicherman.nms.v1_8_R1.NMSUtilities.murderMessage(message, murdered, murderer, audience);
		}

		return true;
	}

	private static boolean sendDeathMessage(LocaleMessage[] messages, Player died, ItemStack displayOnVariable, Player[] audience) {
		LocaleMessage message = messages[random.nextInt(messages.length)];

		if (displayOnVariable == null) {
			message.smartFilter("\\{", "").smartFilter("\\}", "").filter(Utilities.getPrefixFor(died) + died.getName())
					.sendToCrowd(audience);
			MyZ.log(message.toString());
			message.clearSmartFilter();
			return true;
		}

		switch (NMS.compatibilityVersion) {
		case v1_8_R1:
			return jordan.sicherman.nms.v1_8_R1.NMSUtilities.deathMessage(message, died, displayOnVariable, audience);
		}
		return true;
	}

	public static void interactAnvil(Player player, Block isAnvil, EngineerRecipe... recipe) {
		switch (NMS.compatibilityVersion) {
		case v1_8_R1:
			jordan.sicherman.nms.v1_8_R1.NMSUtilities.openAnvil(player, isAnvil, recipe);
			break;
		}
	}

	public static boolean sendInventoryUpdate(Player player, int slot) {
		switch (NMS.compatibilityVersion) {
		case v1_8_R1:
			return jordan.sicherman.nms.v1_8_R1.NMSUtilities.sendInventoryUpdate(player, slot);
		}
		return false;
	}

	public static void registerEntities() {
		switch (NMS.compatibilityVersion) {
		case v1_8_R1:
			jordan.sicherman.nms.v1_8_R1.mobs.CustomEntityType.registerEntities();
			break;
		}
	}

	public static void unregisterEntities() {
		switch (NMS.compatibilityVersion) {
		case v1_8_R1:
			jordan.sicherman.nms.v1_8_R1.mobs.CustomEntityType.unregisterEntities();
			break;
		}
	}

	public static void spawnEntity(Location inLoc, EntityType type) {
		switch (NMS.compatibilityVersion) {
		case v1_8_R1:
			jordan.sicherman.nms.v1_8_R1.NMSUtilities.spawnEntity(inLoc, type);
			break;
		}
	}

	private static Class<?> craftChest, tileEntity;
	private static Field inventoryField;
	private static Method a;

	static {
		try {
			craftChest = Class.forName("org.bukkit.craftbukkit." + NMS.version + ".block.CraftChest");
			inventoryField = craftChest.getDeclaredField("chest");
			tileEntity = Class.forName("net.minecraft.server." + NMS.version + ".TileEntityChest");
			a = tileEntity.getMethod("a", String.class);
		} catch (Exception exc) {
		}
	}

	public static void renameChest(Chest block, String name) {
		try {
			Object chest = craftChest.cast(block.getBlock().getState());

			inventoryField.setAccessible(true);
			Object tileChest = tileEntity.cast(inventoryField.get(chest));
			a.invoke(tileChest, name);
			inventoryField.setAccessible(false);
		} catch (Exception exc) {
		}
	}

	public static void attractEntity(LivingEntity entity, Location inLoc, long duration) {
		switch (NMS.compatibilityVersion) {
		case v1_8_R1:
			jordan.sicherman.nms.v1_8_R1.NMSUtilities.attractEntity(entity, inLoc, duration);
			break;
		}
	}
}
