package tangalbert919.nms.v1_9_R2.mobs.pathfinders;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntityLiving;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.MobEffects;

public class CustomEntitySelectorNearestAttackableTarget implements Predicate {

    private final Predicate predicate;
    private final CustomPathfinderGoalNearestAttackableTarget pathfinder;

    public CustomEntitySelectorNearestAttackableTarget(CustomPathfinderGoalNearestAttackableTarget pathfinder, Predicate predicate) {
        this.predicate = predicate;
        this.pathfinder = pathfinder;
    }

    public boolean apply(EntityLiving entity) {
        if (this.predicate != null && !this.predicate.apply(entity)) {
            return false;
        } else {
            if (entity instanceof EntityHuman) {
                if (((EntityPlayer) entity).playerInteractManager.isCreative() || entity.hasEffect(MobEffects.WITHER) || entity.hasEffect(MobEffects.INVISIBILITY)) {
                    return false;
                }

                double range = (double) (((EntityHuman) entity).exp * 32.0F);

                if ((double) entity.g(this.pathfinder.getE()) > range) {
                    return false;
                }
            }

            return this.pathfinder.a(entity, false);
        }
    }

	@Override
	public boolean apply(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
