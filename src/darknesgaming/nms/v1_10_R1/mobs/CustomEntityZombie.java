package darknesgaming.nms.v1_10_R1.mobs;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import darknesgaming.nms.v1_10_R1.mobs.pathfinders.*;
import net.minecraft.server.v1_10_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_10_R1.EntityCreature;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EntityZombie;
import net.minecraft.server.v1_10_R1.GenericAttributes;
import net.minecraft.server.v1_10_R1.Item;
import net.minecraft.server.v1_10_R1.ItemStack;
import net.minecraft.server.v1_10_R1.Items;
import net.minecraft.server.v1_10_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_10_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_10_R1.util.UnsafeList;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomEntityZombie extends EntityZombie implements SmartEntity {

    private Location smartTarget;
    private final CustomEntityZombie.ZombieType type;
    private final boolean crawler;

    public void setSmartTarget(Location inLoc, long duration) {
        this.smartTarget = inLoc;
        (new BukkitRunnable() {
            public void run() {
                CustomEntityZombie.this.smartTarget = null;
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
        return (float) ((double) super.bD() * ((Double) ConfigEntries.ZOMBIE_JUMP_MULTIPLIER.getValue()).doubleValue());
    }

    public CustomEntityZombie(World world) {
        super(world);
        int choices = CustomEntityZombie.ZombieType.values().length;

        CustomEntityZombie.ZombieType exc;

        do {
            exc = CustomEntityZombie.ZombieType.values()[this.random.nextInt(choices)];
        } while (this.random.nextInt(100) + 1 > exc.chance);

        this.type = exc;
        this.crawler = this.random.nextInt(100) + 1 <= ((Integer) ConfigEntries.ZOMBIE_CRAWLER_CHANCE.getValue()).intValue();

        try {
            CommonMobUtilities.bField.set(this.goalSelector, new UnsafeList());
            CommonMobUtilities.bField.set(this.targetSelector, new UnsafeList());
            CommonMobUtilities.cField.set(this.goalSelector, new UnsafeList());
            CommonMobUtilities.cField.set(this.targetSelector, new UnsafeList());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new CustomPathfinderGoalMeleeAttack(this, EntityHuman.class, this.crawler ? (this.isBaby() ? 0.25D : 0.5D) : ((Double) ConfigEntries.ZOMBIE_SPEED_TARGET.getValue()).doubleValue() * (this.isBaby() ? 0.5D : 1.0D), false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(7, new CustomPathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityGuard.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalRandomLookaround(this));
        this.goalSelector.a(4, new CustomPathfinderGoalMoveToLocation(this, 1.2D));
        this.goalSelector.a(4, new CustomPathfinderGoalMeleeAttack(this, CustomEntityGuard.class, this.crawler ? (this.isBaby() ? 0.25D : 0.5D) : ((Double) ConfigEntries.ZOMBIE_SPEED_TARGET.getValue()).doubleValue() * (this.isBaby() ? 0.5D : 1.0D), true));
        this.targetSelector.a(1, new CustomPathfinderGoalHurtByTarget(this, true, new Class[] { EntityHuman.class, CustomEntityGuard.class}));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityGuard.class, false));
    }

    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
    }

    protected void aW() {
        super.aW();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(((Double) ConfigEntries.ZOMBIE_HEALTH.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.c).setValue(((Double) ConfigEntries.ZOMBIE_KNOCKBACK_RESIST.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.d).setValue(((Double) ConfigEntries.ZOMBIE_SPEED.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.e).setValue(((Double) ConfigEntries.ZOMBIE_DAMAGE.getValue()).doubleValue());
    }

    protected void a(DifficultyDamageScaler difficultydamagescaler) {
        switch (CustomEntityZombie.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$v1_9_R2$mobs$CustomEntityZombie$ZombieType[this.type.ordinal()]) {
        case 1:
            this.setEquipment(1, new ItemStack(Items.CHAINMAIL_BOOTS));
            this.setEquipment(2, new ItemStack(Items.CHAINMAIL_LEGGINGS));
            this.setEquipment(3, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
            this.setEquipment(4, new ItemStack(Items.CHAINMAIL_HELMET));
            break;

        case 2:
            this.setEquipment(1, new ItemStack(Items.GOLDEN_BOOTS));
            this.setEquipment(2, new ItemStack(Items.GOLDEN_LEGGINGS));
            this.setEquipment(3, new ItemStack(Items.GOLDEN_CHESTPLATE));
            this.setEquipment(4, new ItemStack(Items.GOLDEN_HELMET));
            break;

        case 3:
            this.setEquipment(1, new ItemStack(Items.IRON_BOOTS));
            this.setEquipment(2, new ItemStack(Items.IRON_LEGGINGS));
            this.setEquipment(3, new ItemStack(Items.IRON_CHESTPLATE));
            this.setEquipment(4, new ItemStack(Items.IRON_HELMET));
            break;

        case 4:
            this.setEquipment(1, new ItemStack(Items.LEATHER_BOOTS));
            this.setEquipment(2, new ItemStack(Items.LEATHER_LEGGINGS));
            this.setEquipment(3, new ItemStack(Items.LEATHER_CHESTPLATE));
            this.setEquipment(4, new ItemStack(Items.LEATHER_HELMET));
        }

    }

    protected Item getLoot() {
        return null;
    }

    protected void dropDeathLoot(boolean killedByPlayer, int enchantmentLevel) {
        int amount = this.random.nextInt(4) == 0 ? this.random.nextInt(2) + 1 : 0;

        for (int dropped = 0; dropped <= amount; ++dropped) {
            this.a(Items.ROTTEN_FLESH, 1);
        }

    }

    protected void getRareDrop() {
        switch (this.random.nextInt(2)) {
        case 0:
            this.a(CraftItemStack.asNMSCopy(ItemUtilities.getInstance().getTagItem(ItemTag.MURKY_WATER, 1)), 0.0F);
            break;

        case 1:
            this.a(CraftItemStack.asNMSCopy(ItemUtilities.getInstance().getTagItem(ItemTag.CHAIN, 1)), 0.0F);
        }

    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$nms$v1_9_R2$mobs$CustomEntityZombie$ZombieType = new int[CustomEntityZombie.ZombieType.values().length];

        static {
            try {
                CustomEntityZombie.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$v1_9_R2$mobs$CustomEntityZombie$ZombieType[CustomEntityZombie.ZombieType.CHAIN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                CustomEntityZombie.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$v1_9_R2$mobs$CustomEntityZombie$ZombieType[CustomEntityZombie.ZombieType.GOLD.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                CustomEntityZombie.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$v1_9_R2$mobs$CustomEntityZombie$ZombieType[CustomEntityZombie.ZombieType.IRON.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                CustomEntityZombie.SyntheticClass_1.$SwitchMap$jordan$sicherman$nms$v1_9_R2$mobs$CustomEntityZombie$ZombieType[CustomEntityZombie.ZombieType.LEATHER.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }

    private static enum ZombieType {

        NORMAL(((Integer) ConfigEntries.ZOMBIE_NORMAL_CHANCE.getValue()).intValue()), LEATHER(((Integer) ConfigEntries.ZOMBIE_LEATHER_CHANCE.getValue()).intValue()), CHAIN(((Integer) ConfigEntries.ZOMBIE_CHAIN_CHANCE.getValue()).intValue()), GOLD(((Integer) ConfigEntries.ZOMBIE_GOLD_CHANCE.getValue()).intValue()), IRON(((Integer) ConfigEntries.ZOMBIE_IRON_CHANCE.getValue()).intValue());

        private final int chance;

        private ZombieType(int chance) {
            this.chance = chance;
        }
    }
}
