package darknesgaming.nms.v1_9_R2.mobs.pathfinders;

import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_9_R2.AxisAlignedBB;
import net.minecraft.server.v1_9_R2.EntityCreature;
import net.minecraft.server.v1_9_R2.EntityLiving;
import net.minecraft.server.v1_9_R2.PathfinderGoalTarget;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class CustomPathfinderGoalHurtByTarget extends PathfinderGoalTarget {

    private final boolean favorTargets;
    private int hurtTimestamp;
    private final Class[] targets;

    public CustomPathfinderGoalHurtByTarget(EntityCreature creature, boolean favorTargets, Class... targets) {
        super(creature, false);
        this.favorTargets = favorTargets;
        this.targets = targets;
        this.a(1);
    }

    public boolean a() {
        int hurtTimestamp = this.e.bd();

        return hurtTimestamp != this.hurtTimestamp && this.a(this.e.getLastDamager(), false);
    }

    public void c() {
        this.e.setGoalTarget(this.e.getLastDamager(), TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true);
        this.hurtTimestamp = this.e.bd();
        if (this.favorTargets) {
            double range = this.f();
            List list = this.e.world.a(this.e.getClass(), (new AxisAlignedBB(this.e.locX, this.e.locY, this.e.locZ, this.e.locX + 1.0D, this.e.locY + 1.0D, this.e.locZ + 1.0D)).grow(range, 10.0D, range));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityCreature entitycreature = (EntityCreature) iterator.next();

                if (this.e != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(this.e.getLastDamager())) {
                    boolean toAttack = false;
                    Class[] validTargets = this.targets;
                    int numberOfTargets = validTargets.length;

                    for (int j = 0; j < numberOfTargets; ++j) {
                        Class receivedTarget = validTargets[j];

                        if (entitycreature.getClass() == receivedTarget) {
                            toAttack = true;
                            break;
                        }
                    }

                    if (!toAttack) {
                        this.a(entitycreature, this.e.getLastDamager());
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
