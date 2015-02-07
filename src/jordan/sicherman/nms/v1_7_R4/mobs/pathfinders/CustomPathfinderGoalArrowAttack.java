/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.IRangedEntity;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.PathfinderGoal;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalArrowAttack extends PathfinderGoal {

	private final EntityInsentient insentientCreature;
	private final IRangedEntity creature;
	private EntityLiving target;
	private int shootDelay = -1;
	private final double speed;
	private int waitTicks;
	private final int xAccuracy;
	private final int zAccuracy;
	private final float range;
	private final float rangeSquared;

	public CustomPathfinderGoalArrowAttack(IRangedEntity creature, double speed, int accuracy, float range) {
		this(creature, speed, accuracy, accuracy, range);
	}

	public CustomPathfinderGoalArrowAttack(IRangedEntity creature, double speed, int xAccuracy, int zAccuracy, float range) {
		this.creature = creature;
		insentientCreature = (EntityInsentient) creature;
		this.speed = speed;
		this.xAccuracy = xAccuracy;
		this.zAccuracy = zAccuracy;
		this.range = range;
		rangeSquared = range * range;
		a(3);
	}

	@Override
	public boolean a() {
		EntityLiving target = insentientCreature.getGoalTarget();
		if (target == null) { return false; }

		this.target = target;
		return true;
	}

	@Override
	public boolean b() {
		return a() || !insentientCreature.getNavigation().g();
	}

	@Override
	public void d() {
		target = null;
		waitTicks = 0;
		shootDelay = -1;
	}

	@Override
	public void e() {
		double distance = insentientCreature.e(target.locX, target.boundingBox.b, target.locZ);
		boolean canSee = insentientCreature.getEntitySenses().canSee(target);
		if (canSee) {
			waitTicks += 1;
		} else {
			waitTicks = 0;
		}
		if (distance > rangeSquared || waitTicks < 20) {
			insentientCreature.getNavigation().a(target, speed);
		} else {
			insentientCreature.getNavigation().h();
		}
		insentientCreature.getControllerLook().a(target, 30.0F, 30.0F);
		float f1;
		if (--shootDelay == 0) {
			if (distance > rangeSquared || !canSee) { return; }

			f1 = MathHelper.sqrt(distance) / range;
			float f2 = f1;
			f2 = MathHelper.a(f2, 0.1F, 1.0F);

			creature.a(target, f2);
			shootDelay = MathHelper.d(f1 * (zAccuracy - xAccuracy) + xAccuracy);
		} else if (shootDelay < 0) {
			f1 = MathHelper.sqrt(distance) / range;
			shootDelay = MathHelper.d(f1 * (zAccuracy - xAccuracy) + xAccuracy);
		}
	}
}