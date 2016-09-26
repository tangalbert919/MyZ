package darknesgaming.nms.v1_9_R2.mobs.pathfinders;

import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntityInsentient;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.MobEffectList;
import net.minecraft.server.v1_9_R2.MobEffects;
import net.minecraft.server.v1_9_R2.PathfinderGoal;
import net.minecraft.server.v1_9_R2.World;

public class CustomPathfinderGoalLookAtPlayer extends PathfinderGoal {

    protected EntityInsentient creature;
    protected Entity target;
    protected float range;
    private int lookAway;
    protected Class classToLookAt;

    public CustomPathfinderGoalLookAtPlayer(EntityInsentient creature, Class classToLookAt, float range) {
        this(creature, classToLookAt, range, 0.02F);
    }

    public CustomPathfinderGoalLookAtPlayer(EntityInsentient creature, Class classToLookAt, float range, float chanceToNot) {
        this.creature = creature;
        this.classToLookAt = classToLookAt;
        this.range = range;
        this.a(2);
    }

    public EntityHuman findNearbyPlayer(World world, double iX, double iY, double iZ) {
        double nearestSquared = -1.0D;
        EntityHuman nearest = null;
        List players = world.players;
        Iterator iterator = players.iterator();

        while (iterator.hasNext()) {
            EntityHuman found = (EntityHuman) iterator.next();
            double range = (double) (found.exp * 32.0F);

            if (found != null && found.isAlive() && !((EntityPlayer) found).playerInteractManager.isCreative() && !found.hasEffect(MobEffects.INVISIBILITY) && !found.hasEffect(MobEffects.WITHER)) {
                double fX = found.locX;
                double fY = found.locY;
                double fZ = found.locZ;
                double distSquared = (fX - iX) * (fX - iX) + (fY - iY) * (fY - iY) * (fZ - iZ) * (fZ - iZ);

                if (distSquared < range * range && (nearestSquared == -1.0D || distSquared < nearestSquared)) {
                    nearestSquared = distSquared;
                    nearest = found;
                }
            }
        }

        return nearest;
    }

    public boolean a() {
        if (this.creature.getGoalTarget() != null) {
            this.target = this.creature.getGoalTarget();
        }

        if (this.target == null) {
            if (this.classToLookAt == EntityHuman.class) {
                if ((double) this.creature.bb().nextFloat() <= 0.5D) {
                    this.target = this.findNearbyPlayer(this.creature.world, this.creature.locX, this.creature.locY, this.creature.locZ);
                }
            } else {
                this.target = this.creature.world.a(this.classToLookAt, this.creature.getBoundingBox().grow((double) this.range, 3.0D, (double) this.range), this.creature);
            }
        }

        return this.target != null;
    }

    public boolean b() {
        return !this.target.isAlive() ? false : (this.creature.h(this.target) > (double) (this.range * this.range) ? false : this.lookAway > 0);
    }

    public void c() {
        this.lookAway = 40 + this.creature.bb().nextInt(40);
    }

    public void d() {
        this.target = null;
    }

    public void e() {
        this.creature.getControllerLook().a(this.target.locX, this.target.locY + (double) this.target.getHeadHeight(), this.target.locZ, 10.0F, (float) this.creature.bP());
        --this.lookAway;
    }
}
