/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs;

import java.util.Calendar;

import jordan.sicherman.MyZ;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalArrowAttack;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalHurtByTarget;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalLookAtPlayer;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalMeleeAttack;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalMoveToLocation;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalNearestAttackableTarget;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalRandomLookaround;
import jordan.sicherman.nms.v1_7_R4.mobs.pathfinders.CustomPathfinderGoalRandomStroll;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.minecraft.server.v1_7_R4.AttributeModifier;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntitySkeleton;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.GroupDataEntity;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_7_R4.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R4.StepSound;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.util.UnsafeList;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class CustomEntityGuard extends EntitySkeleton implements SmartEntity {

	private Location smartTarget;

	@Override
	public void setSmartTarget(Location inLoc, long duration) {
		smartTarget = inLoc;

		new BukkitRunnable() {
			@Override
			public void run() {
				smartTarget = null;
			}
		}.runTaskLater(MyZ.instance, duration);
	}

	@Override
	public Location getSmartTarget() {
		return smartTarget;
	}

	@Override
	public EntityCreature getEntity() {
		return this;
	}

	private static enum GuardType {
		MELEE, RANGED;
	}

	private final GuardType type;

	private final CustomPathfinderGoalMeleeAttack humanAttack = new CustomPathfinderGoalMeleeAttack(this, EntityHuman.class,
			ConfigEntries.GUARD_SPEED_TARGET.<Double> getValue(), false);
	private final CustomPathfinderGoalMeleeAttack zombieAttack = new CustomPathfinderGoalMeleeAttack(this, CustomEntityZombie.class,
			ConfigEntries.GUARD_SPEED_TARGET.<Double> getValue(), true);
	private final CustomPathfinderGoalMeleeAttack giantAttack = new CustomPathfinderGoalMeleeAttack(this, CustomEntityGiantZombie.class,
			ConfigEntries.GUARD_SPEED_TARGET.<Double> getValue(), true);
	private final CustomPathfinderGoalMeleeAttack pigmanAttack = new CustomPathfinderGoalMeleeAttack(this, CustomEntityPigZombie.class,
			ConfigEntries.GUARD_SPEED_TARGET.<Double> getValue(), true);

	private final CustomPathfinderGoalArrowAttack rangedAttack = new CustomPathfinderGoalArrowAttack(this,
			ConfigEntries.GUARD_SPEED_TARGET.<Double> getValue(), 20, 50, 15.0F);

	@Override
	protected void bj() {
		motY = 0.4199999868869782D * ConfigEntries.GUARD_JUMP_MULTIPLIER.<Double> getValue();
		if (hasEffect(MobEffectList.JUMP)) {
			motY += (getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F;
		}
		if (isSprinting()) {
			float f = yaw * 0.01745329F;

			motX -= MathHelper.sin(f) * 0.2F;
			motZ += MathHelper.cos(f) * 0.2F;
		}
		al = true;
	}

	@SuppressWarnings("unchecked")
	public CustomEntityGuard(World world) {
		super(world);

		type = GuardType.values()[aI().nextInt(GuardType.values().length)];

		DisguiseAPI.disguiseToAll(getBukkitEntity(),
				new PlayerDisguise(ChatColor.translateAlternateColorCodes('&', ConfigEntries.GUARD_NAME.<String> getValue())));

		try {
			CommonMobUtilities.bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			CommonMobUtilities.bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			CommonMobUtilities.cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			CommonMobUtilities.cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		goalSelector.a(0, new PathfinderGoalFloat(this));
		goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		goalSelector.a(7, new CustomPathfinderGoalRandomStroll(this, 1.0D));
		goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityZombie.class, 8.0F));
		goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityPigZombie.class, 8.0F));
		goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityGiantZombie.class, 8.0F));
		goalSelector.a(4, new CustomPathfinderGoalMoveToLocation(this, 1.2D));
		goalSelector.a(8, new CustomPathfinderGoalRandomLookaround(this));
		targetSelector.a(1, new CustomPathfinderGoalHurtByTarget(this, true, new Class[] { CustomEntityZombie.class,
				CustomEntityPigZombie.class, CustomEntityGiantZombie.class }));
		targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityZombie.class, false));
		targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityPigZombie.class, true));
		targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityGiantZombie.class, true));
	}

	@Override
	public boolean canSpawn() {
		return world.a(boundingBox, this) && world.getCubes(this, boundingBox).isEmpty() && !world.containsLiquid(boundingBox);
	}

	@Override
	public void a(EntityLiving entityliving, float f) {
		if (type != GuardType.RANGED) { return; }

		super.a(entityliving, f);
	}

	@Override
	public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
		getAttributeInstance(GenericAttributes.b).b(new AttributeModifier("Random spawn bonus", random.nextGaussian() * 0.05D, 1));

		a();

		h(random.nextFloat() < 0.55F * world.b(locX, locY, locZ));
		if (getEquipment(4) == null) {
			Calendar calendar = world.V();
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && random.nextFloat() < 0.25F) {
				setEquipment(4, new ItemStack(random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.PUMPKIN));
				dropChances[4] = 0.0F;
			}
		}
		return groupdataentity;
	}

	protected void a() {
		switch (type) {
		case MELEE:
			setEquipment(0, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial(ConfigEntries.GUARD_MELEE_ITEM
					.<String> getValue()))));
			break;
		case RANGED:
			setEquipment(0, new ItemStack(Items.BOW));
			break;
		}

		setEquipment(1, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial(ConfigEntries.GUARD_BOOTS_ITEM
				.<String> getValue()))));
		setEquipment(2, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial(ConfigEntries.GUARD_LEGGINGS_ITEM
				.<String> getValue()))));
		setEquipment(3, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material
				.getMaterial(ConfigEntries.GUARD_CHESTPLATE_ITEM.<String> getValue()))));
		setEquipment(4, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial(ConfigEntries.GUARD_HELMET_ITEM
				.<String> getValue()))));
	}

	@Override
	protected String t() {
		return "mob.villager.idle";
	}

	@Override
	protected String aT() {
		return "game.player.hurt";
	}

	@Override
	protected String aU() {
		return "game.player.die";
	}

	@Override
	protected void a(int i, int j, int k, Block block) {
		StepSound stepsound = block.stepSound;
		makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
	}

	@Override
	public void die() {
		if (ConfigEntries.GUARD_ZOMBIE_DEATH.<Boolean> getValue()) {
			CustomEntityZombie zombie = new CustomEntityZombie(world);
			zombie.setEquipment(0, getEquipment(0));
			zombie.setEquipment(1, getEquipment(1));
			zombie.setEquipment(2, getEquipment(2));
			zombie.setEquipment(3, getEquipment(3));
			zombie.setEquipment(4, getEquipment(4));
			zombie.setLocation(locX, locY, locZ, yaw, pitch);
			world.addEntity(zombie);
		}
		super.die();
	}

	@Override
	protected Item getLoot() {
		return null;
	}

	@Override
	protected void getRareDrop(int i) {
	}

	@Override
	public void bZ() {
		if (type == null) { return; }

		goalSelector.a(humanAttack);
		goalSelector.a(zombieAttack);
		goalSelector.a(giantAttack);
		goalSelector.a(pigmanAttack);
		goalSelector.a(rangedAttack);

		switch (type) {
		case RANGED:
			goalSelector.a(4, rangedAttack);
			break;
		case MELEE:
			goalSelector.a(4, humanAttack);
			goalSelector.a(4, zombieAttack);
			goalSelector.a(2, zombieAttack);
			goalSelector.a(4, pigmanAttack);
			break;
		}
	}

	@Override
	protected void aD() {
		super.aD();
		getAttributeInstance(GenericAttributes.maxHealth).setValue(ConfigEntries.GUARD_HEALTH.<Double> getValue());
		getAttributeInstance(GenericAttributes.c).setValue(ConfigEntries.GUARD_KNOCKBACK_RESIST.<Double> getValue());
		getAttributeInstance(GenericAttributes.d).setValue(ConfigEntries.GUARD_SPEED.<Double> getValue());
		getAttributeInstance(GenericAttributes.e).setValue(ConfigEntries.GUARD_DAMAGE.<Double> getValue());
	}
}
