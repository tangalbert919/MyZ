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

public class CustomPathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget {

    private final Class targetClass;
    private final int randomChanceDoNotAttack;
    private final DistanceComparator distanceComparator;
    private final IEntitySelector entitySelector;
    private WeakReference targetReference;

    public CustomPathfinderGoalNearestAttackableTarget(EntityCreature creature, Class targetClass, int randomChanceDoNotAttack, boolean flag) {
        this(creature, targetClass, randomChanceDoNotAttack, flag, false);
    }

    public CustomPathfinderGoalNearestAttackableTarget(EntityCreature creature, Class targetClass, int randomChanceDoNotAttack, boolean flag, boolean flag1) {
        this(creature, targetClass, randomChanceDoNotAttack, flag, flag1, (IEntitySelector) null);
    }

    public CustomPathfinderGoalNearestAttackableTarget(EntityCreature creature, Class targetClass, int randomChanceDoNotAttack, boolean flag, boolean flag1, IEntitySelector ientityselector) {
        super(creature, flag, flag1);
        this.targetReference = new WeakReference((Object) null);
        this.targetClass = targetClass;
        this.randomChanceDoNotAttack = randomChanceDoNotAttack;
        this.distanceComparator = new DistanceComparator(creature);
        this.a(1);
        this.entitySelector = new CustomEntitySelectorNearestAttackableTarget(this, ientityselector);
    }

    public boolean a() {
        if (this.randomChanceDoNotAttack > 0 && this.c.aI().nextInt(this.randomChanceDoNotAttack) != 0) {
            return false;
        } else {
            double distance = this.f();
            List entities = this.c.world.a(this.targetClass, this.c.boundingBox.grow(distance, 4.0D, distance), this.entitySelector);

            Collections.sort(entities, this.distanceComparator);
            if (entities.isEmpty()) {
                return false;
            } else {
                this.targetReference = new WeakReference((EntityLiving) entities.get(0));
                return true;
            }
        }
    }

    public boolean a(EntityLiving entity, boolean b) {
        return super.a(entity, b);
    }

    public void c() {
        EntityLiving target = (EntityLiving) this.targetReference.get();

        if (target != null && target instanceof EntityHuman) {
            if (!((EntityPlayer) target).playerInteractManager.isCreative() && !target.hasEffect(MobEffectList.WITHER) && !target.hasEffect(MobEffectList.INVISIBILITY)) {
                if (target != null) {
                    double range = (double) (((EntityHuman) target).exp * 32.0F);

                    if (this.c.f(target) > range * range) {
                        target = null;
                    }
                }
            } else {
                target = null;
            }
        }

        this.c.setGoalTarget(target);
        super.c();
    }
}
