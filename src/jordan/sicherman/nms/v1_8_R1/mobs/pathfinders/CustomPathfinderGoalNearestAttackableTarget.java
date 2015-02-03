/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs.pathfinders;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R1.DistanceComparator;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.IEntitySelector;
import net.minecraft.server.v1_8_R1.PathfinderGoalTarget;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget {

	protected final Class<? extends EntityLiving> classToTarget;
	private final int chanceToNot;
	protected final DistanceComparator distanceComparator;
	protected Predicate<EntityLiving> entitySelector;
	protected EntityLiving target;

	public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class<? extends EntityLiving> targetClass,
			boolean flag) {
		this(entitycreature, targetClass, flag, false);
	}

	public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class<? extends EntityLiving> targetClass,
			boolean flag, boolean flag1) {
		this(entitycreature, targetClass, 10, flag, flag1, (Predicate<EntityLiving>) null);
	}

	public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class<? extends EntityLiving> targetClass, int i,
			boolean flag, boolean flag1, Predicate<EntityLiving> predicate) {
		super(entitycreature, flag, flag1);
		classToTarget = targetClass;
		chanceToNot = i;
		distanceComparator = new DistanceComparator(entitycreature);
		a(1);
		entitySelector = new CustomEntitySelectorNearestAttackableTarget(this, predicate);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean a() {
		if (chanceToNot > 0 && e.bb().nextInt(chanceToNot) != 0) {
			// return false;
		}

		double range = f();
		List list = e.world
				.a(classToTarget, e.getBoundingBox().grow(range, 4.0D, range), Predicates.and(entitySelector, IEntitySelector.d));

		Collections.sort(list, distanceComparator);

		if (list.isEmpty()) { return false; }

		target = (EntityLiving) list.get(0);
		return true;
	}

	@Override
	public void c() {
		e.setGoalTarget(target, TargetReason.CLOSEST_PLAYER, true);
		super.c();
	}

	@Override
	protected boolean a(EntityLiving creature, boolean bool) {
		return super.a(creature, bool);
	}

	@Override
	protected double f() {
		return super.f();
	}

	public Entity getE() {
		return e;
	}
}
