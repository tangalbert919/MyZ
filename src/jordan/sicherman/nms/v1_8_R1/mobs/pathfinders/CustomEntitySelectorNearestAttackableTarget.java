package jordan.sicherman.nms.v1_8_R1.mobs.pathfinders;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.MobEffectList;

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
                if (((EntityPlayer) entity).playerInteractManager.isCreative() || entity.hasEffect(MobEffectList.WITHER) || entity.hasEffect(MobEffectList.INVISIBILITY)) {
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
