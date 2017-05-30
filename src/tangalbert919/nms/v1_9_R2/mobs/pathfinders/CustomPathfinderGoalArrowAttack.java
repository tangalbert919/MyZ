package tangalbert919.nms.v1_9_R2.mobs.pathfinders;

import net.minecraft.server.v1_9_R2.*;

public class CustomPathfinderGoalArrowAttack extends PathfinderGoal {

    private final EntityInsentient insentientCreature;
    private final IRangedEntity creature;
    private EntityLiving target;
    private int shootDelay;
    private final double speed;
    private int waitTicks;
    private final int xAccuracy;
    private final int zAccuracy;
    private final float range;
    private final float rangeSquared;

    public CustomPathfinderGoalArrowAttack(IRangedEntity creature, double speed, int accuracy, float range) {
        this(creature, speed, accuracy, accuracy, range);
    }

    public CustomPathfinderGoalArrowAttack(IRangedEntity creature, double speed, int xAccuracy, int zAccuracy, float range) {
        this.shootDelay = -1;
        this.creature = creature;
        this.insentientCreature = (EntityInsentient) creature;
        this.speed = speed;
        this.xAccuracy = xAccuracy;
        this.zAccuracy = zAccuracy;
        this.range = range;
        this.rangeSquared = range * range;
        this.a(3);
    }

    public boolean a() {
        EntityLiving target = this.insentientCreature.getGoalTarget();

        if (target == null) {
            return false;
        } else {
            this.target = target;
            return true;
        }
    }

    public boolean b() {
        return this.a() || !this.insentientCreature.getNavigation().n();
    }

    public void d() {
        this.target = null;
        this.waitTicks = 0;
        this.shootDelay = -1;
    }

    public void e() {
        double distance = this.insentientCreature.e(this.target.locX, this.target.getBoundingBox().b, this.target.locZ);
        boolean canSee = this.insentientCreature.getEntitySenses().a(this.target);

        if (canSee) {
            ++this.waitTicks;
        } else {
            this.waitTicks = 0;
        }

        if (distance <= (double) this.rangeSquared && this.waitTicks >= 20) {
            this.insentientCreature.getNavigation().n();
        } else {
            this.insentientCreature.getNavigation().a(this.target, this.speed);
        }

        this.insentientCreature.getControllerLook().a(this.target, 30.0F, 30.0F);
        float f1;

        if (--this.shootDelay == 0) {
            if (distance > (double) this.rangeSquared || !canSee) {
                return;
            }

            f1 = MathHelper.sqrt(distance) / this.range;
            float f2 = MathHelper.a(f1, 0.1F, 1.0F);

            this.creature.a(this.target, f2);
            this.shootDelay = MathHelper.d(f1 * (float) (this.zAccuracy - this.xAccuracy) + (float) this.xAccuracy);
        } else if (this.shootDelay < 0) {
            f1 = MathHelper.sqrt(distance) / this.range;
            this.shootDelay = MathHelper.d(f1 * (float) (this.zAccuracy - this.xAccuracy) + (float) this.xAccuracy);
        }

    }
}

