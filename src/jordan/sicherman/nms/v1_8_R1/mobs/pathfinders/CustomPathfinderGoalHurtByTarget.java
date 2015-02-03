/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs.pathfinders;

import java.util.Iterator;
import java.util.List;

import net.minecraft.server.v1_8_R1.AxisAlignedBB;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.PathfinderGoalTarget;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalHurtByTarget extends PathfinderGoalTarget {

	private final boolean favorTargets;
	private int hurtTimestamp;
	private final Class<? extends EntityLiving>[] targets;

	public CustomPathfinderGoalHurtByTarget(EntityCreature creature, boolean favorTargets, Class<? extends EntityLiving>... targets) {
		super(creature, false);
		this.favorTargets = favorTargets;
		this.targets = targets;
		a(1);
	}

	@Override
	public boolean a() {
		int hurtTimestamp = e.bd();

		return hurtTimestamp != this.hurtTimestamp && a(e.getLastDamager(), false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void c() {
		e.setGoalTarget(e.getLastDamager(), TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true);
		hurtTimestamp = e.bd();

		if (favorTargets) {
			double range = f();
			List<EntityCreature> list = e.world.a(e.getClass(), new AxisAlignedBB(e.locX, e.locY, e.locZ, e.locX + 1.0D, e.locY + 1.0D,
					e.locZ + 1.0D).grow(range, 10.0D, range));
			Iterator<EntityCreature> iterator = list.iterator();

			while (iterator.hasNext()) {
				EntityCreature entitycreature = iterator.next();

				if (e != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(e.getLastDamager())) {
					boolean toAttack = false;

					Class<? extends EntityLiving>[] validTargets = targets;
					int numberOfTargets = validTargets.length;

					for (int j = 0; j < numberOfTargets; j++) {
						Class<? extends EntityLiving> receivedTarget = validTargets[j];
						if (entitycreature.getClass() == receivedTarget) {
							toAttack = true;
							break;
						}
					}
					if (!toAttack) {
						a(entitycreature, e.getLastDamager());
					}
				}
			}
		}
		super.c();
	}

	protected void a(EntityCreature entitycreature, EntityLiving entityliving) {
		entitycreature.setGoalTarget(entityliving, TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true);
	}
}
