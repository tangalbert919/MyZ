/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import java.util.List;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.PathfinderGoal;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalLookAtPlayer extends PathfinderGoal {

	protected EntityInsentient creature;
	protected Entity target;
	protected float range;
	private int lookAway;
	protected Class<? extends EntityLiving> classToLookAt;

	public CustomPathfinderGoalLookAtPlayer(EntityInsentient creature, Class<? extends EntityLiving> classToLookAt, float range) {
		this(creature, classToLookAt, range, 0.02f);
	}

	public CustomPathfinderGoalLookAtPlayer(EntityInsentient creature, Class<? extends EntityLiving> classToLookAt, float range,
			float chanceToNot) {
		this.creature = creature;
		this.classToLookAt = classToLookAt;
		this.range = range;
		a(2);
	}

	public Player findNearbyPlayer(Location inLoc) {
		double nearestSquared = -1.0D;
		Player nearest = null;

		List<Player> players = inLoc.getWorld().getPlayers();

		for (int i = 0; i < players.size(); i++) {
			Player found = players.get(i);
			double range = found.getExp() * 32;
			if (found != null && !found.isDead() && found.getGameMode() != GameMode.CREATIVE
					&& !found.hasPotionEffect(PotionEffectType.INVISIBILITY) && !found.hasPotionEffect(PotionEffectType.WITHER)) {
				double distSquared = found.getLocation().distanceSquared(inLoc);
				if (distSquared < range * range && (nearestSquared == -1.0D || distSquared < nearestSquared)) {
					nearestSquared = distSquared;
					nearest = found;
				}
			}
		}
		return nearest;
	}

	@Override
	public boolean a() {
		if (creature.getGoalTarget() != null) {
			target = creature.getGoalTarget();
		}
		if (target == null) {
			if (classToLookAt == EntityHuman.class) {
				if (creature.aI().nextFloat() <= 0.5) {
					CraftPlayer player = (CraftPlayer) findNearbyPlayer(creature.getBukkitEntity().getLocation());
					target = player == null ? null : player.getHandle();
				}
			} else {
				target = creature.world.a(classToLookAt, creature.boundingBox.grow(range, 3.0D, range), creature);
			}
		}
		return target != null;
	}

	@Override
	public boolean b() {
		if (!target.isAlive()) { return false; }
		if (creature.f(target) > range * range) { return false; }

		return lookAway > 0;
	}

	@Override
	public void c() {
		lookAway = 40 + creature.aI().nextInt(40);
	}

	@Override
	public void d() {
		target = null;
	}

	@Override
	public void e() {
		creature.getControllerLook().a(target.locX, target.locY + target.getHeadHeight(), target.locZ, 10.0F, creature.x());
		lookAway -= 1;
	}
}
