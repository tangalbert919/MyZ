/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.IEntitySelector;

/**
 * @author Jordan
 * 
 */
public class CustomEntitySelectorNearestAttackableTarget implements IEntitySelector {

	private final IEntitySelector entitySelector;
	private final CustomPathfinderGoalNearestAttackableTarget pathfinderGoal;

	public CustomEntitySelectorNearestAttackableTarget(CustomPathfinderGoalNearestAttackableTarget pathfinderGoal,
			IEntitySelector entitySelector) {
		this.entitySelector = entitySelector;
		this.pathfinderGoal = pathfinderGoal;
	}

	@Override
	public boolean a(Entity entity) {
		if (!(entity instanceof EntityLiving)) { return false; }

		if (entitySelector != null && !entitySelector.a(entity)) { return false; }
		return pathfinderGoal.a((EntityLiving) entity, false);
	}
}
