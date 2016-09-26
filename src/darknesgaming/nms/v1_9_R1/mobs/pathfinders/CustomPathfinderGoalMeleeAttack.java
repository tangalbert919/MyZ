package darknesgaming.nms.v1_9_R1.mobs.pathfinders;

import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.EntityCreature;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.MobEffectList;
import net.minecraft.server.v1_9_R1.PathEntity;
import net.minecraft.server.v1_9_R1.PathfinderGoal;
import net.minecraft.server.v1_9_R1.World;
import org.bukkit.entity.Player;
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

        return target == null ? false : (!target.isAlive() ? false : (!this.bypassVision ? !this.creature.getNavigation().m() : this.creature.d(new BlockPosition(target))));
    }

    public void c() {
        this.creature.getNavigation().a(this.path, this.speed);
        this.delay = 0;
    }

    public void d() {
        this.creature.getNavigation().n();
    }

    public void e() {
        EntityLiving target = this.creature.getGoalTarget();

        if (target != null && (target.hasEffect(MobEffectList.WITHER) || target.hasEffect(MobEffectList.INVISIBILITY))) {
            this.creature.setGoalTarget((EntityLiving) null);
        } else {
            this.creature.getControllerLook().a(target, 30.0F, 30.0F);
            double distance = this.creature.e(target.locX, target.getBoundingBox().b, target.locZ);
            double sizeOfEntities = this.a(target);

            --this.delay;
            if ((this.bypassVision || this.creature.getEntitySenses().a(target)) && this.delay <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || target.e(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.creature.bb().nextFloat() < 0.05F)) {
                this.targetX = target.locX;
                this.targetY = target.getBoundingBox().b;
                this.targetZ = target.locZ;
                this.delay = 4 + this.creature.bb().nextInt(7);
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
                if (this.creature.bz() != null) {
                    this.creature.bv();
                }

                this.creature.r(target);
            }

        }
    }

    protected double a(EntityLiving paramEntityLiving) {
        return (double) (this.creature.width * 2.0F * this.creature.width * 2.0F + paramEntityLiving.width);
    }
}
