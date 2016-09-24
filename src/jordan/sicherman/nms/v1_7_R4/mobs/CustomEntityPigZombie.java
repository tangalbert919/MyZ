package jordan.sicherman.nms.v1_7_R4.mobs;

import jordan.sicherman.MyZ;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalHurtByTarget;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalLookAtPlayer;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalMeleeAttack;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalMoveToLocation;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalNearestAttackableTarget;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalRandomLookaround;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalRandomStroll;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPigZombie;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.util.UnsafeList;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomEntityPigZombie extends EntityPigZombie implements SmartEntity {

    private Location smartTarget;

    public void setSmartTarget(Location inLoc, long duration) {
        this.smartTarget = inLoc;
        (new BukkitRunnable() {
            public void run() {
                CustomEntityPigZombie.this.smartTarget = null;
            }
        }).runTaskLater(MyZ.instance, duration);
    }

    public Location getSmartTarget() {
        return this.smartTarget;
    }

    public EntityCreature getEntity() {
        return this;
    }

    protected void bj() {
        this.motY = 0.46D * ((Double) ConfigEntries.PIGMAN_JUMP_MULTIPLIER.getValue()).doubleValue();
        if (this.hasEffect(MobEffectList.JUMP)) {
            this.motY += (double) ((float) (this.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F);
        }

        if (this.isSprinting()) {
            float f = this.yaw * 0.01745329F;

            this.motX -= (double) (MathHelper.sin(f) * 0.2F);
            this.motZ += (double) (MathHelper.cos(f) * 0.2F);
        }

        this.al = true;
    }

    public CustomEntityPigZombie(World world) {
        super(world);

        try {
            CommonMobUtilities.bField.set(this.goalSelector, new UnsafeList());
            CommonMobUtilities.bField.set(this.targetSelector, new UnsafeList());
            CommonMobUtilities.cField.set(this.goalSelector, new UnsafeList());
            CommonMobUtilities.cField.set(this.targetSelector, new UnsafeList());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new CustomPathfinderGoalMeleeAttack(this, EntityHuman.class, ((Double) ConfigEntries.PIGMAN_SPEED_TARGET.getValue()).doubleValue() * (this.isBaby() ? 0.5D : 1.0D), false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(4, new CustomPathfinderGoalMoveToLocation(this, 1.2D));
        this.goalSelector.a(7, new CustomPathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityGuard.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalRandomLookaround(this));
        this.goalSelector.a(4, new CustomPathfinderGoalMeleeAttack(this, CustomEntityGuard.class, ((Double) ConfigEntries.PIGMAN_SPEED_TARGET.getValue()).doubleValue() * (this.isBaby() ? 0.5D : 1.0D), true));
        this.targetSelector.a(1, new CustomPathfinderGoalHurtByTarget(this, true, new Class[] { EntityHuman.class, CustomEntityGuard.class}));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityGuard.class, 0, false));
    }

    public boolean canSpawn() {
        return this.world.a(this.boundingBox, this) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(((Double) ConfigEntries.PIGMAN_HEALTH.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.c).setValue(((Double) ConfigEntries.PIGMAN_KNOCKBACK_RESIST.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.d).setValue(((Double) ConfigEntries.PIGMAN_SPEED.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.e).setValue(((Double) ConfigEntries.PIGMAN_DAMAGE.getValue()).doubleValue());
    }

    public void die() {
        if (!this.isBaby()) {
            for (int magnitude = ((Integer) ConfigEntries.PIGMAN_MULTIPLY_DEATH.getValue()).intValue(); magnitude > 0; --magnitude) {
                CustomEntityPigZombie zombie = new CustomEntityPigZombie(this.world);

                zombie.setBaby(true);
                zombie.setLocation(this.locX + (this.random.nextInt(2) == 0 ? this.random.nextDouble() + 1.0D : -(1.0D + this.random.nextDouble())) * this.random.nextDouble(), this.locY, this.locZ + (this.random.nextInt(2) == 0 ? this.random.nextDouble() + 1.0D : -(1.0D + this.random.nextDouble())) * this.random.nextDouble(), this.yaw, this.pitch);
                this.world.addEntity(zombie);
            }
        } else {
            float f = ((Double) ConfigEntries.PIGMAN_EXPLODE_DEATH.getValue()).floatValue();

            if (f > 0.0F) {
                this.world.createExplosion(this, this.locX, this.locY, this.locZ, f, false, false);
            }
        }

        super.die();
    }

    protected Item getLoot() {
        return null;
    }

    protected void getRareDrop(int i) {
        switch (this.random.nextInt(2)) {
        case 0:
            this.a(new ItemStack(Items.STICK), 0.0F);
            break;

        case 1:
            this.a(new ItemStack(Items.GOLD_INGOT), 0.0F);
        }

    }
}
