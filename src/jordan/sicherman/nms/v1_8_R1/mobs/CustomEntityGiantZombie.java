package jordan.sicherman.nms.v1_8_R1.mobs;

import jordan.sicherman.MyZ;
import jordan.sicherman.nms.v1_8_R1.mobs.pathfinders.CustomPathfinderGoalHurtByTarget;
import jordan.sicherman.nms.v1_8_R1.mobs.pathfinders.CustomPathfinderGoalLookAtPlayer;
import jordan.sicherman.nms.v1_8_R1.mobs.pathfinders.CustomPathfinderGoalMeleeAttack;
import jordan.sicherman.nms.v1_8_R1.mobs.pathfinders.CustomPathfinderGoalMoveToLocation;
import jordan.sicherman.nms.v1_8_R1.mobs.pathfinders.CustomPathfinderGoalNearestAttackableTarget;
import jordan.sicherman.nms.v1_8_R1.mobs.pathfinders.CustomPathfinderGoalRandomLookaround;
import jordan.sicherman.nms.v1_8_R1.mobs.pathfinders.CustomPathfinderGoalRandomStroll;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import net.minecraft.server.v1_8_R1.Enchantment;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityGiantZombie;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.GenericAttributes;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R1.World;
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

    protected float bD() {
        return (float) ((double) super.bD() * ((Double) ConfigEntries.GIANT_JUMP_MULTIPLIER.getValue()).doubleValue());
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
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityGuard.class, false));
    }

    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
    }

    protected void aW() {
        super.aW();
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

    protected void getRareDrop() {}
}
