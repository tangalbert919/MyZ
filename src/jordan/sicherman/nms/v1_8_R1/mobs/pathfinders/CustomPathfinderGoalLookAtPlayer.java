/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs.pathfinders;

import java.util.List;

import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.MobEffectList;
import net.minecraft.server.v1_8_R1.PathfinderGoal;
import net.minecraft.server.v1_8_R1.World;

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
				if (creature.bb().nextFloat() <= 0.5) {
					target = findNearbyPlayer(creature.world, creature.locX, creature.locY, creature.locZ);
				}
			} else {
				target = creature.world.a(classToLookAt, creature.getBoundingBox().grow(range, 3.0D, range), creature);
			}
		}
		return target != null;
	}

	@Override
	public boolean b() {
		if (!target.isAlive()) { return false; }
		if (creature.h(target) > range * range) { return false; }

		return lookAway > 0;
	}

	@Override
	public void c() {
		lookAway = 40 + creature.bb().nextInt(40);
	}

	@Override
	public void d() {
		target = null;
	}

	@Override
	public void e() {
		creature.getControllerLook().a(target.locX, target.locY + target.getHeadHeight(), target.locZ, 10.0F, creature.bP());
		lookAway -= 1;
	}
}
