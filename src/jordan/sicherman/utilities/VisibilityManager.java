/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import jordan.sicherman.MyZ;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.sql.SQLManager;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class VisibilityManager {

	private final Map<String, LazyLocation> movement = new HashMap<String, LazyLocation>();

	private static VisibilityManager instance;

	public VisibilityManager() {
		instance = this;
	}

	public static VisibilityManager getInstance() {
		return instance;
	}

	public void reportMovement(Player playerFor, Location from) {
		movement.put(SQLManager.primaryKeyFor(playerFor), LazyLocation.fromLocation(from));
	}

	@SuppressWarnings("deprecation")
	public void computeXPBarVisibility(Player playerFor) {
		if (!MyZ.isPremium()) { return; }

		float value = ConfigEntries.VISIBILITY_BASE.<Integer> getValue() / 18f;

		if (playerFor.isSneaking()) {
			value += ConfigEntries.VISIBILITY_SNEAKING.<Integer> getValue() / 18f;
		}
		if (playerFor.getItemInHand() != null && playerFor.getItemInHand().getType() == Material.ROTTEN_FLESH) {
			value += ConfigEntries.VISIBILITY_CARRYING_FLESH.<Integer> getValue() / 18f;
		}
		if (playerFor.getEquipment().getHelmet() != null && playerFor.getEquipment().getHelmet().getType() == Material.SKULL_ITEM
				&& playerFor.getEquipment().getHelmet().getDurability() == (short) 2) {
			value += ConfigEntries.VISIBILITY_WEARING_HEAD.<Integer> getValue() / 18f;
		}

		if (playerFor.isSprinting()) {
			value += ConfigEntries.VISIBILITY_SPRINTING.<Integer> getValue() / 18f;
		} else if (movement.containsKey(SQLManager.primaryKeyFor(playerFor))
				&& !movement.get(SQLManager.primaryKeyFor(playerFor)).equals(playerFor.getLocation())) {
			if (playerFor.getWorld().getTime() < 13000) {
				value += ConfigEntries.VISIBILITY_WALKING.<Integer> getValue() / 18f;
			}
		}

		if (!playerFor.isOnGround() && movement.containsKey(SQLManager.primaryKeyFor(playerFor))
				&& movement.get(SQLManager.primaryKeyFor(playerFor)).isBelow(playerFor.getLocation())) {
			value += ConfigEntries.VISIBILITY_JUMPING.<Integer> getValue() / 18f;
		}

		if (playerFor.getFireTicks() > 0) {
			value += ConfigEntries.VISIBILITY_ON_FIRE.<Integer> getValue() / 18f;
		}

		if (DataWrapper.<Boolean> get(playerFor, UserEntries.BLEEDING)) {
			value += ConfigEntries.VISIBILITY_BLEEDING.<Integer> getValue() / 18f;
		}

		if (playerFor.hasMetadata("MyZ.overload_visibility")) {
			// Since this is async, we might do this once our meta is gone.
			try {
				value += playerFor.getMetadata("MyZ.overload_visibility").get(0).asFloat();
			} catch (Exception e) {

			}
		}

		float current = playerFor.getExp();

		if (value == current) { return; }

		if (Math.abs(current - value) < 0.001) {
			playerFor.setExp(value);
		} else {
			if (value < 0) {
				value = 0;
			} else if (value > 1) {
				value = 1;
			}
			float newValue = (value + current) / 2;
			playerFor.setExp(newValue);
		}
	}

	public enum VisibilityCause {
		ACTIVATE_REDSTONE(ConfigEntries.VISIBILITY_ACTIVATE_REDSTONE.<Integer> getValue() / 18f, 40L), SHOOT_ARROW(
				ConfigEntries.VISIBILITY_SHOOTING.<Integer> getValue() / 18f, 100L), TAKE_DAMAGE(ConfigEntries.VISIBILITY_TAKE_DAMAGE
				.<Integer> getValue() / 18f, 40L), EXPLOSION(ConfigEntries.VISIBILITY_EXPLOSION.<Integer> getValue() / 18f, 200L), ARROW_LANDED(
				ConfigEntries.VISIBILITY_ARROW_LANDED.<Integer> getValue() / 18f, 80L), SNOWBALL_LANDED(ConfigEntries.VISIBILITY_SNOWBALL
				.<Integer> getValue() / 18f, 160L), CHAT(ConfigEntries.VISIBILITY_CHAT.<Integer> getValue() / 18f, 180L);

		private final float toFill;
		private final long fillFor;

		private VisibilityCause(float toFill, long fillFor) {
			this.toFill = toFill;
			this.fillFor = fillFor;
		}

		public float toFill() {
			return toFill;
		}

		public long getDuration() {
			return fillFor;
		}
	}

	public void overloadXPBarVisibility(final Player playerFor, VisibilityCause cause) {
		overloadXPBarVisibility(playerFor, cause.toFill(), cause.getDuration());
	}

	public void overloadXPBarVisibility(final Player playerFor, final float fill, final long duration) {
		Biome biome = playerFor.getWorld().getBiome(playerFor.getLocation().getBlockX(), playerFor.getLocation().getBlockZ());
		if (playerFor.getWorld().hasStorm() && biome != Biome.DESERT && biome != Biome.DESERT_HILLS && biome != Biome.DESERT_MOUNTAINS) { return; }

		if (!MyZ.isPremium()) { return; }

		playerFor.setMetadata("MyZ.overload_visibility", new FixedMetadataValue(MyZ.instance, fill));

		new BukkitRunnable() {
			@Override
			public void run() {
				playerFor.removeMetadata("MyZ.overload_visibility", MyZ.instance);
			}

			@Override
			public void cancel() {
				playerFor.removeMetadata("MyZ.overload_visibility", MyZ.instance);
			}
		}.runTaskLater(MyZ.instance, duration);
	}

	private Entity[] getNearbyEntities(Location l, float radius) {
		float radiusSquared = radius * radius;
		float chunkRadius = radius < 16 ? 1 : (radius - radius % 16) / 16;
		HashSet<Entity> found = new HashSet<Entity>();
		for (float cX = -chunkRadius; cX <= chunkRadius; cX++) {
			for (float cZ = -chunkRadius; cZ <= chunkRadius; cZ++) {
				int x = l.getBlockX(), y = l.getBlockY(), z = l.getBlockZ();
				for (Entity entity : new Location(l.getWorld(), x + cX * 16, y, z + cZ * 16).getChunk().getEntities()) {
					if (entity.getLocation().distanceSquared(l) <= radiusSquared) {
						found.add(entity);
					}
				}
			}
		}
		return found.toArray(new Entity[found.size()]);
	}

	public void overloadXPBarVisibility(Location locationFor, VisibilityCause cause) {
		Biome biome = locationFor.getWorld().getBiome(locationFor.getBlockX(), locationFor.getBlockZ());
		if (locationFor.getWorld().hasStorm() && biome != Biome.DESERT && biome != Biome.DESERT_HILLS && biome != Biome.DESERT_MOUNTAINS) { return; }

		if (!MyZ.isPremium()) { return; }

		float range = cause.toFill * 16.0f;

		for (Entity entity : getNearbyEntities(locationFor, range)) {
			if (entity instanceof LivingEntity && !(entity instanceof Player)) {
				CompatibilityManager.attractEntity((LivingEntity) entity, locationFor, cause.fillFor);
			}
		}
	}
}
