/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs.pathfinders;

import jordan.sicherman.nms.v1_8_R1.mobs.SmartEntity;
import net.minecraft.server.v1_8_R1.PathfinderGoal;
import net.minecraft.server.v1_8_R1.RandomPositionGenerator;
import net.minecraft.server.v1_8_R1.Vec3D;

import org.bukkit.Location;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalMoveToLocation extends PathfinderGoal {

	private final SmartEntity creature;
	private Location target;
	private double targetX, targetY, targetZ;
	private final double speed;

	public CustomPathfinderGoalMoveToLocation(SmartEntity creature, double speed) {
		this.creature = creature;
		this.speed = speed;
		a(1);
	}

	@Override
	public boolean a() {
		target = creature.getSmartTarget();
		if (target == null) { return false; }
		Vec3D localVec3D = RandomPositionGenerator.a(creature.getEntity(), 16, 7, new Vec3D(target.getX(), target.getY(), target.getZ()));
		if (localVec3D == null) { return false; }
		targetX = localVec3D.a;
		targetY = localVec3D.b;
		targetZ = localVec3D.c;
		return true;
	}

	@Override
	public boolean b() {
		return !creature.getEntity().getNavigation().m();
	}

	@Override
	public void d() {
		target = null;
	}

	@Override
	public void c() {
		creature.getEntity().getNavigation().a(targetX, targetY, targetZ, speed);
	}
}