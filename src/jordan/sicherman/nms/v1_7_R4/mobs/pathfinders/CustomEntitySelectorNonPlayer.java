/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;

import com.google.common.base.Predicate;

/**
 * @author Jordan
 * 
 */
final class CustomEntitySelectorNonPlayer implements Predicate<EntityLiving> {

	@Override
	public boolean apply(EntityLiving entity) {
		return !(entity instanceof EntityHuman);
	}
}
