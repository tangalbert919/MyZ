/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_7_R4.DistanceComparator;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IEntitySelector;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.PathfinderGoalTarget;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget {

	private final Class<?> targetClass;
	private final int randomChanceDoNotAttack;
	private final DistanceComparator distanceComparator;
	private final IEntitySelector entitySelector;
	private WeakReference<EntityLiving> targetReference = new WeakReference<EntityLiving>(null);

	public CustomPathfinderGoalNearestAttackableTarget(EntityCreature creature, Class<?> targetClass, int randomChanceDoNotAttack,
			boolean flag) {
		this(creature, targetClass, randomChanceDoNotAttack, flag, false);
	}

	public CustomPathfinderGoalNearestAttackableTarget(EntityCreature creature, Class<?> targetClass, int randomChanceDoNotAttack,
			boolean flag, boolean flag1) {
		this(creature, targetClass, randomChanceDoNotAttack, flag, flag1, (IEntitySelector) null);
	}

	public CustomPathfinderGoalNearestAttackableTarget(EntityCreature creature, Class<?> targetClass, int randomChanceDoNotAttack,
			boolean flag, boolean flag1, IEntitySelector ientityselector) {
		super(creature, flag, flag1);
		this.targetClass = targetClass;
		this.randomChanceDoNotAttack = randomChanceDoNotAttack;
		distanceComparator = new DistanceComparator(creature);
		a(1);
		entitySelector = new CustomEntitySelectorNearestAttackableTarget(this, ientityselector);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean a() {
		if (randomChanceDoNotAttack > 0 && c.aI().nextInt(randomChanceDoNotAttack) != 0) { return false; }
		double distance = f();
		List<?> entities = c.world.a(targetClass, c.boundingBox.grow(distance, 4.0D, distance), entitySelector);

		Collections.sort(entities, distanceComparator);
		if (entities.isEmpty()) { return false; }
		targetReference = new WeakReference<EntityLiving>((EntityLiving) entities.get(0));
		return true;
	}

	@Override
	public boolean a(EntityLiving entity, boolean b) {
		return super.a(entity, b);
	}

	@Override
	public void c() {
		EntityLiving target = targetReference.get();

		if (target != null && target instanceof EntityHuman) {
			if (((EntityPlayer) target).playerInteractManager.isCreative() || target.hasEffect(MobEffectList.WITHER)
					|| target.hasEffect(MobEffectList.INVISIBILITY)) {
				target = null;
			} else if (target != null) {
				double range = ((EntityHuman) target).exp * 32;
				if (c.f(target) > range * range) {
					target = null;
				}
			}
		}
		c.setGoalTarget(target);
		super.c();
	}
}
