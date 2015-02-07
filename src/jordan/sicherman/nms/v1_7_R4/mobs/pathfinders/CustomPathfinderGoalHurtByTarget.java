/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import java.util.Iterator;
import java.util.List;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.PathfinderGoalTarget;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalHurtByTarget extends PathfinderGoalTarget {

	private final boolean favorTargets;
	private int hurtTimestamp;

	public CustomPathfinderGoalHurtByTarget(EntityCreature creature, boolean favorTargets, Class<? extends EntityLiving>... targets) {
		super(creature, false);
		this.favorTargets = favorTargets;
		a(1);
	}

	@Override
	public boolean a() {
		int hurtTimestamp = c.aK();

		return hurtTimestamp != this.hurtTimestamp && a(c.getLastDamager(), false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void c() {
		c.setGoalTarget(c.getLastDamager());
		hurtTimestamp = c.aK();

		if (favorTargets) {
			double range = f();
			List<EntityCreature> list = c.world.a(c.getClass(),
					AxisAlignedBB.a(c.locX, c.locY, c.locZ, c.locX + 1.0D, c.locY + 1.0D, c.locZ + 1.0D).grow(range, 10.0D, range));
			Iterator<EntityCreature> iterator = list.iterator();

			while (iterator.hasNext()) {
				EntityCreature entitycreature = iterator.next();

				if (c != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(c.getLastDamager())) {
					if ((this.c != entitycreature) && (entitycreature.getGoalTarget() == null)
							&& (!entitycreature.c(this.c.getLastDamager()))) {
						EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(entitycreature,
								this.c.getLastDamager(), EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY);
						if (!event.isCancelled()) {
							entitycreature.setGoalTarget(event.getTarget() == null ? null : ((CraftLivingEntity) event.getTarget())
									.getHandle());
						}
					}
				}
			}
		}
		super.c();
	}
}
