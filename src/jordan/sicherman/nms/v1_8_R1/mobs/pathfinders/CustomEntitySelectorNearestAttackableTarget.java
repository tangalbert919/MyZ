/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs.pathfinders;

import jordan.sicherman.utilities.configuration.ConfigEntries;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.MobEffectList;

import com.google.common.base.Predicate;

/**
 * @author Jordan
 * 
 */
public class CustomEntitySelectorNearestAttackableTarget implements Predicate<EntityLiving> {

	private final Predicate<EntityLiving> predicate;
	private final CustomPathfinderGoalNearestAttackableTarget pathfinder;
	private static final double stretch = ConfigEntries.VISIBILITY_STRETCHER.<Double> getValue();

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
			double range = ((EntityHuman) entity).exp * stretch;

			if (entity.g(pathfinder.getE()) > range) { return false; }
		}
		return pathfinder.a(entity, false);
	}
}
