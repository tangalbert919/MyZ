/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import com.google.common.base.Predicate;

import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;

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
