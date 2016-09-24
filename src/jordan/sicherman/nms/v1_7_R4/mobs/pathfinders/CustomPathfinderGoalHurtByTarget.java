package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.PathfinderGoalTarget;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class CustomPathfinderGoalHurtByTarget extends PathfinderGoalTarget {

    private final boolean favorTargets;
    private int hurtTimestamp;

    public CustomPathfinderGoalHurtByTarget(EntityCreature creature, boolean favorTargets, Class... targets) {
        super(creature, false);
        this.favorTargets = favorTargets;
        this.a(1);
    }

    public boolean a() {
        int hurtTimestamp = this.c.aK();

        return hurtTimestamp != this.hurtTimestamp && this.a(this.c.getLastDamager(), false);
    }

    public void c() {
        this.c.setGoalTarget(this.c.getLastDamager());
        this.hurtTimestamp = this.c.aK();
        if (this.favorTargets) {
            double range = this.f();
            List list = this.c.world.a(this.c.getClass(), AxisAlignedBB.a(this.c.locX, this.c.locY, this.c.locZ, this.c.locX + 1.0D, this.c.locY + 1.0D, this.c.locZ + 1.0D).grow(range, 10.0D, range));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityCreature entitycreature = (EntityCreature) iterator.next();

                if (this.c != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(this.c.getLastDamager()) && this.c != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(this.c.getLastDamager())) {
                    EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(entitycreature, this.c.getLastDamager(), TargetReason.TARGET_ATTACKED_NEARBY_ENTITY);

                    if (!event.isCancelled()) {
                        entitycreature.setGoalTarget(event.getTarget() == null ? null : ((CraftLivingEntity) event.getTarget()).getHandle());
                    }
                }
            }
        }

        super.c();
    }
}
