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
import net.minecraft.server.v1_7_R4.Enchantment;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityGiantZombie;
import net.minecraft.server.v1_7_R4.EntityHuman;
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
import org.bukkit.scheduler.BukkitRunnable;

public class CustomEntityGiantZombie extends EntityGiantZombie implements SmartEntity {

    private Location smartTarget;

    public void setSmartTarget(Location inLoc, long duration) {
        this.smartTarget = inLoc;
        (new BukkitRunnable() {
            public void run() {
                CustomEntityGiantZombie.this.smartTarget = null;
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
        this.motY = 0.46D * ((Double) ConfigEntries.GIANT_JUMP_MULTIPLIER.getValue()).doubleValue();
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

    public CustomEntityGiantZombie(World world) {
        super(world);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new CustomPathfinderGoalMeleeAttack(this, EntityHuman.class, ((Double) ConfigEntries.GIANT_SPEED_TARGET.getValue()).doubleValue(), false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(7, new CustomPathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(4, new CustomPathfinderGoalMoveToLocation(this, 1.2D));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityGuard.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalRandomLookaround(this));
        this.goalSelector.a(4, new CustomPathfinderGoalMeleeAttack(this, CustomEntityGuard.class, ((Double) ConfigEntries.GIANT_SPEED_TARGET.getValue()).doubleValue(), true));
        this.targetSelector.a(1, new CustomPathfinderGoalHurtByTarget(this, true, new Class[] { EntityHuman.class, CustomEntityGuard.class}));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityGuard.class, 0, false));
    }

    public boolean canSpawn() {
        return this.world.a(this.boundingBox, this) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox);
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(((Double) ConfigEntries.GIANT_HEALTH.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.c).setValue(((Double) ConfigEntries.GIANT_KNOCKBACK_RESIST.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.d).setValue(((Double) ConfigEntries.GIANT_SPEED.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.e).setValue(((Double) ConfigEntries.GIANT_DAMAGE.getValue()).doubleValue());
    }

    protected Item getLoot() {
        return null;
    }

    protected void dropDeathLoot(boolean killedByPlayer, int enchantmentLevel) {
        int amount = this.random.nextInt(10 + enchantmentLevel * enchantmentLevel) + 1;

        int dropped;

        for (dropped = 0; dropped <= amount; ++dropped) {
            this.a(Items.ROTTEN_FLESH, 1);
        }

        amount = this.random.nextInt(5);

        for (dropped = 0; dropped <= amount; ++dropped) {
            ItemStack drop = new ItemStack(Items.BOW, 1);

            drop.addEnchantment(Enchantment.ARROW_INFINITE, 0);
            switch (this.random.nextInt(3)) {
            case 0:
                drop = new ItemStack(Items.DIAMOND_SWORD, 1, Items.DIAMOND_SWORD.getMaxDurability() / 2);
                break;

            case 1:
                drop = new ItemStack(Items.GOLDEN_APPLE);
            }

            this.a(drop, 0.0F);
        }

    }

    protected void getRareDrop(int i) {}
}
