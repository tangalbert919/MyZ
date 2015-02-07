/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.PathfinderGoal;
import net.minecraft.server.v1_7_R4.RandomPositionGenerator;
import net.minecraft.server.v1_7_R4.Vec3D;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalRandomStroll extends PathfinderGoal {

	private final EntityCreature creature;
	private double destX;
	private double destY;
	private double destZ;
	private final double speed;
	private int chanceToNot;
	private boolean notPathing;

	public CustomPathfinderGoalRandomStroll(EntityCreature creature, double speed) {
		this(creature, speed, 120);
	}

	public CustomPathfinderGoalRandomStroll(EntityCreature creature, double speed, int chanceToNot) {
		this.creature = creature;
		this.speed = speed;
		this.chanceToNot = chanceToNot;
		a(1);
	}

	@Override
	public boolean a() {
		if (!notPathing) {
			// bg() is ticks since last damage.
			if (creature.aN() >= 100) { return false; }
			if (creature.aI().nextInt(chanceToNot) != 0) { return false; }
		}

		// x-range and z-range
		Vec3D destination = RandomPositionGenerator.a(creature, 10, 7);
		if (destination == null) { return false; }

		destX = destination.a;
		destY = destination.b;
		destZ = destination.c;
		notPathing = false;
		return true;
	}

	@Override
	public boolean b() {
		return !creature.getNavigation().g();
	}

	@Override
	public void c() {
		creature.getNavigation().a(destX, destY, destZ, speed);
	}

	public void f() {
		notPathing = true;
	}

	public void b(int paramInt) {
		chanceToNot = paramInt;
	}
}
