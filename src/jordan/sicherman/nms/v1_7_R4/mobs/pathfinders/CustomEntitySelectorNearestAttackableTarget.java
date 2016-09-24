package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.IEntitySelector;

public class CustomEntitySelectorNearestAttackableTarget implements IEntitySelector {

    private final IEntitySelector entitySelector;
    private final CustomPathfinderGoalNearestAttackableTarget pathfinderGoal;

    public CustomEntitySelectorNearestAttackableTarget(CustomPathfinderGoalNearestAttackableTarget pathfinderGoal, IEntitySelector entitySelector) {
        this.entitySelector = entitySelector;
        this.pathfinderGoal = pathfinderGoal;
    }

    public boolean a(Entity entity) {
        return !(entity instanceof EntityLiving) ? false : (this.entitySelector != null && !this.entitySelector.a(entity) ? false : this.pathfinderGoal.a((EntityLiving) entity, false));
    }
}
