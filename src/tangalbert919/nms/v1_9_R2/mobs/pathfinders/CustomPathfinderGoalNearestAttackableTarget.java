package tangalbert919.nms.v1_9_R2.mobs.pathfinders;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.server.v1_9_R2.*;
import net.minecraft.server.v1_9_R2.PathfinderGoalNearestAttackableTarget.DistanceComparator;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import java.util.Collections;
import java.util.List;

public class CustomPathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget {

    protected final Class classToTarget;
    protected final DistanceComparator distanceComparator;
    protected Predicate entitySelector;
    protected EntityLiving target;

    public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class targetClass, boolean flag) {
        this(entitycreature, targetClass, flag, false);
    }

    public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class targetClass, boolean flag, boolean flag1) {
        this(entitycreature, targetClass, 10, flag, flag1, (Predicate) null);
    }

    public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class targetClass, int chanceToCancel, boolean flag, boolean flag1, Predicate predicate) {
        super(entitycreature, flag, flag1);
        this.classToTarget = targetClass;
        this.distanceComparator = new DistanceComparator(entitycreature);
        this.a(1);
        this.entitySelector = new CustomEntitySelectorNearestAttackableTarget(this, predicate);
    }

    public boolean a() {
        double range = this.f();
        List list = this.e.world.a(this.classToTarget, this.e.getBoundingBox().grow(range, 4.0D, range), Predicates.and(this.entitySelector, IEntitySelector.d));

        Collections.sort(list, this.distanceComparator);
        if (list.isEmpty()) {
            return false;
        } else {
            this.target = (EntityLiving) list.get(0);
            return true;
        }
    }

    public void c() {
        this.e.setGoalTarget(this.target, TargetReason.CLOSEST_PLAYER, true);
        super.c();
    }

    protected boolean a(EntityLiving creature, boolean bool) {
        return super.a(creature, bool);
    }

    protected double f() {
        return super.f();
    }

    public Entity getE() {
        return this.e;
    }
}

