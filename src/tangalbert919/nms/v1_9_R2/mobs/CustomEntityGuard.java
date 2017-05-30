package tangalbert919.nms.v1_9_R2.mobs;

import jordan.sicherman.MyZ;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_9_R2.util.UnsafeList;
import org.bukkit.scheduler.BukkitRunnable;
import tangalbert919.nms.v1_9_R2.mobs.pathfinders.*;

import java.util.Calendar;

public class CustomEntityGuard extends EntitySkeleton implements SmartEntity {

    private Location smartTarget;
    private final CustomEntityGuard.GuardType type;
    private final CustomPathfinderGoalMeleeAttack humanAttack;
    private final CustomPathfinderGoalMeleeAttack zombieAttack;
    private final CustomPathfinderGoalMeleeAttack giantAttack;
    private final CustomPathfinderGoalMeleeAttack pigmanAttack;
    private final CustomPathfinderGoalArrowAttack rangedAttack;

    public void setSmartTarget(Location inLoc, long duration) {
        this.smartTarget = inLoc;
        (new BukkitRunnable() {
            public void run() {
                CustomEntityGuard.this.smartTarget = null;
            }
        }).runTaskLater(MyZ.instance, duration);
    }

    public Location getSmartTarget() {
        return this.smartTarget;
    }

    public EntityCreature getEntity() {
        return this;
    }

    protected float ch() {
        return (float) ((double) super.ch() * ((Double) ConfigEntries.GUARD_JUMP_MULTIPLIER.getValue()).doubleValue());
    }

    public CustomEntityGuard(World world) {
        super(world);
        this.humanAttack = new CustomPathfinderGoalMeleeAttack(this, EntityHuman.class, ((Double) ConfigEntries.GUARD_SPEED_TARGET.getValue()).doubleValue(), false);
        this.zombieAttack = new CustomPathfinderGoalMeleeAttack(this, CustomEntityZombie.class, ((Double) ConfigEntries.GUARD_SPEED_TARGET.getValue()).doubleValue(), true);
        this.giantAttack = new CustomPathfinderGoalMeleeAttack(this, CustomEntityGiantZombie.class, ((Double) ConfigEntries.GUARD_SPEED_TARGET.getValue()).doubleValue(), true);
        this.pigmanAttack = new CustomPathfinderGoalMeleeAttack(this, CustomEntityPigZombie.class, ((Double) ConfigEntries.GUARD_SPEED_TARGET.getValue()).doubleValue(), true);
        this.rangedAttack = new CustomPathfinderGoalArrowAttack(this, ((Double) ConfigEntries.GUARD_SPEED_TARGET.getValue()).doubleValue(), 20, 50, 15.0F);
        this.type = CustomEntityGuard.GuardType.values()[this.getRandom().nextInt(CustomEntityGuard.GuardType.values().length)];
        DisguiseAPI.disguiseToAll(this.getBukkitEntity(), new PlayerDisguise(ChatColor.translateAlternateColorCodes('&', (String) ConfigEntries.GUARD_NAME.getValue())));

        try {
            CommonMobUtilities.bField.set(this.goalSelector, new UnsafeList());
            CommonMobUtilities.bField.set(this.targetSelector, new UnsafeList());
            CommonMobUtilities.cField.set(this.goalSelector, new UnsafeList());
            CommonMobUtilities.cField.set(this.targetSelector, new UnsafeList());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(7, new CustomPathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityZombie.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityPigZombie.class, 8.0F));
        this.goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityGiantZombie.class, 8.0F));
        this.goalSelector.a(4, new CustomPathfinderGoalMoveToLocation(this, 1.2D));
        this.goalSelector.a(8, new CustomPathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new CustomPathfinderGoalHurtByTarget(this, true, new Class[] { CustomEntityZombie.class, CustomEntityPigZombie.class, CustomEntityGiantZombie.class, EntityHuman.class}));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityZombie.class, false));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityPigZombie.class, true));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityGiantZombie.class, true));
    }

    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
    }

    public void a(EntityLiving entityliving, float f) {
        if (this.type == CustomEntityGuard.GuardType.RANGED) {
            super.a(entityliving, f);
        }
    }

    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).b(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05D, 1));
        this.a(difficultydamagescaler);
        this.b(difficultydamagescaler);
        this.j(this.random.nextFloat() < 0.55F * difficultydamagescaler.c());
        if (this.getEquipment(EnumItemSlot.HEAD) == null) {
            Calendar calendar = this.world.ac();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.random.nextFloat() < 0.25F) {
                this.setEquipment(EnumItemSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.dropChanceArmor[4] = 0.0F;
            }
        }

        return groupdataentity;
    }

    protected void a(DifficultyDamageScaler difficultydamagescaler) {
        switch (CustomEntityGuard.SyntheticClass_1.$SwitchMap$tangalbert919$nms$v1_9_R1$mobs$CustomEntityGuard$GuardType[this.type.ordinal()]) {
        case 1:
            this.setEquipment(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial((String) ConfigEntries.GUARD_MELEE_ITEM.getValue()))));
            break;

        case 2:
            this.setEquipment(EnumItemSlot.MAINHAND, new ItemStack(Items.BOW));
        }

        this.setEquipment(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial((String) ConfigEntries.GUARD_BOOTS_ITEM.getValue()))));
        this.setEquipment(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial((String) ConfigEntries.GUARD_LEGGINGS_ITEM.getValue()))));
        this.setEquipment(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial((String) ConfigEntries.GUARD_CHESTPLATE_ITEM.getValue()))));
        this.setEquipment(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial((String) ConfigEntries.GUARD_HELMET_ITEM.getValue()))));
    }

    protected String bn() {
        return "game.player.hurt";
    }

    public void die() {
        if (((Boolean) ConfigEntries.GUARD_ZOMBIE_DEATH.getValue()).booleanValue()) {
            CustomEntityZombie zombie = new CustomEntityZombie(this.world);

            zombie.setEquipment(EnumItemSlot.MAINHAND, this.getEquipment(EnumItemSlot.MAINHAND));
            zombie.setEquipment(EnumItemSlot.FEET, this.getEquipment(EnumItemSlot.FEET));
            zombie.setEquipment(EnumItemSlot.LEGS, this.getEquipment(EnumItemSlot.LEGS));
            zombie.setEquipment(EnumItemSlot.CHEST, this.getEquipment(EnumItemSlot.CHEST));
            zombie.setEquipment(EnumItemSlot.HEAD, this.getEquipment(EnumItemSlot.HEAD));
            zombie.setLocation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            this.world.addEntity(zombie);
        }

        super.die();
    }

    protected Item getLoot() {
        return null;
    }

    protected void getRareDrop() {}

    public void n() {
        if (this.type != null) {
            this.goalSelector.a(this.humanAttack);
            this.goalSelector.a(this.zombieAttack);
            this.goalSelector.a(this.giantAttack);
            this.goalSelector.a(this.pigmanAttack);
            this.goalSelector.a(this.rangedAttack);
            switch (CustomEntityGuard.SyntheticClass_1.$SwitchMap$tangalbert919$nms$v1_9_R1$mobs$CustomEntityGuard$GuardType[this.type.ordinal()]) {
            case 1:
                this.goalSelector.a(4, this.humanAttack);
                this.goalSelector.a(4, this.zombieAttack);
                this.goalSelector.a(2, this.zombieAttack);
                this.goalSelector.a(4, this.pigmanAttack);
                break;

            case 2:
                this.goalSelector.a(4, this.rangedAttack);
            }

        }
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(((Double) ConfigEntries.GUARD_HEALTH.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.c).setValue(((Double) ConfigEntries.GUARD_KNOCKBACK_RESIST.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(((Double) ConfigEntries.GUARD_SPEED.getValue()).doubleValue());
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(((Double) ConfigEntries.GUARD_DAMAGE.getValue()).doubleValue());
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$tangalbert919$nms$v1_9_R1$mobs$CustomEntityGuard$GuardType = new int[CustomEntityGuard.GuardType.values().length];

        static {
            try {
                CustomEntityGuard.SyntheticClass_1.$SwitchMap$tangalbert919$nms$v1_9_R1$mobs$CustomEntityGuard$GuardType[CustomEntityGuard.GuardType.MELEE.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                CustomEntityGuard.SyntheticClass_1.$SwitchMap$tangalbert919$nms$v1_9_R1$mobs$CustomEntityGuard$GuardType[CustomEntityGuard.GuardType.RANGED.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

        }
    }

    private static enum GuardType {

        MELEE, RANGED;
    }

}
