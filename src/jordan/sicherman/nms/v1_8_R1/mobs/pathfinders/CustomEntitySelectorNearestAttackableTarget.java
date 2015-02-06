/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs.pathfinders;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityLiving;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

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
			if (((EntityHuman) entity).getBukkitEntity().getGameMode() == GameMode.CREATIVE
					|| ((Player) entity.getBukkitEntity()).hasPotionEffect(PotionEffectType.WITHER)
					|| ((Player) entity.getBukkitEntity()).hasPotionEffect(PotionEffectType.INVISIBILITY)) { return false; }

			double range = ((Player) entity.getBukkitEntity()).getExp() * 32;

			if (entity.g(pathfinder.getE()) > range) { return false; }
		}
		return pathfinder.a(entity, false);
	}
}
