/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MobEffectList;

import com.google.common.base.Predicate;

/**
 * @author Jordan
 * 
 */
public class CustomEntitySelectorNearestAttackableTarget implements Predicate<EntityLiving> {

	private final Predicate<EntityLiving> predicate;
	private final CustomPathfinderGoalNearestAttackableTarget pathfinder;

	public CustomEntitySelectorNearestAttackableTarget(CustomPathfinderGoalNearestAttackableTarget pathfinder,
			Predicate<EntityLiving> predicate) {
		this.predicate = predicate;
		this.pathfinder = pathfinder;
	}

	@Override
	public boolean apply(EntityLiving entity) {
		if (predicate != null && !predicate.apply(entity)) { return false; }
		if (entity instanceof EntityHuman) {
			if (((EntityPlayer) entity).playerInteractManager.isCreative() || entity.hasEffect(MobEffectList.WITHER)
					|| entity.hasEffect(MobEffectList.INVISIBILITY)) { return false; }
			double range = ((EntityHuman) entity).exp * 32;

			if (entity.e(pathfinder.getE()) > range) { return false; }
		}
		return pathfinder.a(entity, false);
	}
}
