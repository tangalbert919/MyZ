/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs.pathfinders;

import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.PathfinderGoal;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalRandomLookaround extends PathfinderGoal {

	private final EntityInsentient creature;
	private double xOffset;
	private double zOffset;
	private int lookAway;

	public CustomPathfinderGoalRandomLookaround(EntityInsentient creature) {
		this.creature = creature;
		a(3);
	}

	@Override
	public boolean a() {
		return creature.bb().nextFloat() < 0.02F;
	}

	@Override
	public boolean b() {
		return lookAway >= 0;
	}

	@Override
	public void c() {
		double d1 = 6.283185307179586D * creature.bb().nextDouble();
		xOffset = Math.cos(d1);
		zOffset = Math.sin(d1);
		lookAway = 20 + creature.bb().nextInt(20);
	}

	@Override
	public void e() {
		lookAway -= 1;
		creature.getControllerLook().a(creature.locX + xOffset, creature.locY + creature.getHeadHeight(), creature.locZ + zOffset, 10.0F,
				creature.bP());
	}
}
