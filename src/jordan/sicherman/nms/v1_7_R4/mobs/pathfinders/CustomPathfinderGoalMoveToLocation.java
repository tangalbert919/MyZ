/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import jordan.sicherman.nms.v1_7_R4.mobs.SmartEntity;
import net.minecraft.server.v1_7_R4.PathfinderGoal;

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
		targetX = target.getX();
		targetY = target.getY();
		targetZ = target.getZ();
		return true;
	}

	@Override
	public boolean b() {
		return !creature.getEntity().getNavigation().g();
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