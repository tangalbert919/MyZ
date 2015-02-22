/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import java.util.List;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.PathfinderGoal;
import net.minecraft.server.v1_7_R4.World;

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

	public EntityHuman findNearbyPlayer(World world, double iX, double iY, double iZ) {
		double nearestSquared = -1.0D;
		EntityHuman nearest = null;

		@SuppressWarnings("unchecked")
		List<EntityHuman> players = world.players;

		for (EntityHuman found : players) {
			double range = found.exp * 32;
			if (found != null && found.isAlive() && !((EntityPlayer) found).playerInteractManager.isCreative()
					&& !found.hasEffect(MobEffectList.INVISIBILITY) && !found.hasEffect(MobEffectList.WITHER)) {
				double fX = found.locX, fY = found.locY, fZ = found.locZ;

				double distSquared = (fX - iX) * (fX - iX) + (fY - iY) * (fY - iY) * +(fZ - iZ) * (fZ - iZ);
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
					target = findNearbyPlayer(creature.world, creature.locX, creature.locY, creature.locZ);
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
