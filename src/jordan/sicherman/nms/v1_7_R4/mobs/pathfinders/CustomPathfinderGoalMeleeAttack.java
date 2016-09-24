package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.PathEntity;
import net.minecraft.server.v1_7_R4.PathfinderGoal;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.potion.PotionEffectType;

public class CustomPathfinderGoalMeleeAttack extends PathfinderGoal {

    final World world;
    protected final EntityCreature creature;
    private int attackDelay;
    private final double speed;
    boolean bypassVision;
    private PathEntity path;
    private Class targetClass;
    private int delay;
    private double targetX;
    private double targetY;
    private double targetZ;

    public CustomPathfinderGoalMeleeAttack(EntityCreature creature, Class targetClass, double speed, boolean bypassVision) {
        this(creature, speed, bypassVision);
        this.targetClass = targetClass;
    }

    public CustomPathfinderGoalMeleeAttack(EntityCreature creature, double speed, boolean bypassVision) {
        this.creature = creature;
        this.world = creature.world;
        this.speed = speed;
        this.bypassVision = bypassVision;
        this.a(3);
    }

    public boolean a() {
        EntityLiving localEntityLiving = this.creature.getGoalTarget();

        if (localEntityLiving == null) {
            return false;
        } else if (!localEntityLiving.isAlive()) {
            return false;
        } else if (this.targetClass != null && !this.targetClass.isAssignableFrom(localEntityLiving.getClass())) {
            return false;
        } else {
            this.path = this.creature.getNavigation().a(localEntityLiving);
            return this.path != null;
        }
    }

    public boolean b() {
        EntityLiving target = this.creature.getGoalTarget();
        TargetReason reason = this.creature.getGoalTarget() != null && !target.hasEffect(MobEffectList.WITHER) && !target.hasEffect(MobEffectList.INVISIBILITY) ? TargetReason.TARGET_DIED : TargetReason.FORGOT_TARGET;

        if (this.creature.getGoalTarget() == null || !this.creature.getGoalTarget().isAlive()) {
            CraftEventFactory.callEntityTargetEvent(this.creature, (Entity) null, reason);
        }

        return target == null ? false : (!this.bypassVision ? false : (!this.creature.getNavigation().g() ? true : (!target.isAlive() ? false : this.creature.b(MathHelper.floor(target.locX), MathHelper.floor(target.locY), MathHelper.floor(target.locZ)))));
    }

    public void c() {
        this.creature.getNavigation().a(this.path, this.speed);
        this.delay = 0;
    }

    public void d() {
        this.creature.getNavigation().h();
    }

    public void e() {
        EntityLiving target = this.creature.getGoalTarget();

        if (target != null && !target.hasEffect(MobEffectList.WITHER) && !target.hasEffect(MobEffectList.INVISIBILITY)) {
            this.creature.getControllerLook().a(target, 30.0F, 30.0F);
            double distance = this.creature.e(target.locX, target.boundingBox.b, target.locZ);
            double sizeOfEntities = (double) (this.creature.width * 2.0F * this.creature.width * 2.0F + target.width);

            --this.delay;
            if ((this.bypassVision || this.creature.getEntitySenses().canSee(target)) && this.delay <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || target.e(this.targetX, this.targetY, this.targetZ) >= 1.0D)) {
                this.targetX = target.locX;
                this.targetY = target.boundingBox.b;
                this.targetZ = target.locZ;
                this.delay = 4 + this.creature.aI().nextInt(7);
                if (distance > 1024.0D) {
                    this.delay += 10;
                } else if (distance > 256.0D) {
                    this.delay += 5;
                }

                if (!this.creature.getNavigation().a(target, this.speed)) {
                    this.delay += 15;
                }
            }

            this.attackDelay = Math.max(this.attackDelay - 1, 0);
            if (distance <= sizeOfEntities && this.attackDelay <= 0) {
                if (target != null && target instanceof EntityHuman && ((Player) target.getBukkitEntity()).hasPotionEffect(PotionEffectType.WITHER)) {
                    this.creature.setGoalTarget((EntityLiving) null);
                }

                this.attackDelay = 20;
                if (this.creature.be() != null) {
                    this.creature.ba();
                }

                this.creature.n(target);
            }

        } else {
            this.creature.setGoalTarget((EntityLiving) null);
        }
    }

    protected double a(EntityLiving paramEntityLiving) {
        return (double) (this.creature.width * 2.0F * this.creature.width * 2.0F + paramEntityLiving.width);
    }
}
