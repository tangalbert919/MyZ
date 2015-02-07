/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Chunk;
import net.minecraft.server.v1_7_R4.DistanceComparator;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.PathfinderGoalTarget;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.craftbukkit.v1_7_R4.util.UnsafeList;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget {

	private static final Predicate<EntityLiving> d = new CustomEntitySelectorNonPlayer();

	protected final Class<? extends EntityLiving> classToTarget;
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

	public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class<? extends EntityLiving> targetClass,
			int chanceToCancel, boolean flag, boolean flag1, Predicate<EntityLiving> predicate) {
		super(entitycreature, flag, flag1);
		classToTarget = targetClass;
		distanceComparator = new DistanceComparator(entitycreature);
		a(1);
		entitySelector = new CustomEntitySelectorNearestAttackableTarget(this, predicate);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean a() {
		double range = f();
		List list = a(c.world, classToTarget, c.boundingBox.grow(range, 4.0D, range), Predicates.and(entitySelector, d));

		Collections.sort(list, distanceComparator);

		if (list.isEmpty()) { return false; }

		target = (EntityLiving) list.get(0);
		return true;
	}

	public List<EntityLiving> a(World world, Class<? extends EntityLiving> oclass, AxisAlignedBB axisalignedbb,
			Predicate<EntityLiving> predicate) {
		int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
		int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
		int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
		int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
		ArrayList<EntityLiving> arraylist = Lists.newArrayList();
		for (int i1 = i; i1 <= j; i1++) {
			for (int j1 = k; j1 <= l; j1++) {
				if (world.chunkProvider.isChunkLoaded(i1, j1)) {
					a(world.getChunkAt(i1, j1), oclass, axisalignedbb, arraylist, predicate);
				}
			}
		}
		return arraylist;
	}

	@SuppressWarnings("unchecked")
	public void a(Chunk chunk, Class<? extends Entity> oclass, AxisAlignedBB axisalignedbb, List<EntityLiving> list,
			Predicate<EntityLiving> predicate) {
		int i = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
		int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);

		i = MathHelper.a(i, 0, chunk.entitySlices.length - 1);
		j = MathHelper.a(j, 0, chunk.entitySlices.length - 1);
		for (int k = i; k <= j; k++) {
			Iterator<Entity> iterator = ((UnsafeList<Entity>) chunk.entitySlices[k]).iterator();
			while (iterator.hasNext()) {
				Entity entity = iterator.next();
				if (oclass.isInstance(entity)) {
					if (entity instanceof EntityLiving) {
						if (entity.boundingBox.b(axisalignedbb) && (predicate == null || predicate.apply((EntityLiving) entity))) {
							list.add((EntityLiving) entity);
						}
					}
				}
			}
		}
	}

	@Override
	public void c() {
		c.setGoalTarget(target);
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
		return c;
	}
}
