/**
 * 
 */
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
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityPigZombie;
import net.minecraft.server.v1_8_R1.GenericAttributes;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.util.UnsafeList;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class CustomEntityPigZombie extends EntityPigZombie implements SmartEntity {

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

	@SuppressWarnings("unchecked")
	public CustomEntityPigZombie(World world) {
		super(world);

		try {
			CommonMobUtilities.bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			CommonMobUtilities.bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			CommonMobUtilities.cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			CommonMobUtilities.cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		goalSelector.a(0, new PathfinderGoalFloat(this));
		goalSelector.a(2,
				new CustomPathfinderGoalMeleeAttack(this, EntityHuman.class, ConfigEntries.PIGMAN_SPEED_TARGET.<Double> getValue()
						* (isBaby() ? 0.5D : 1.0D), false));
		goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		goalSelector.a(4, new CustomPathfinderGoalMoveToLocation(this, 1.2D));
		goalSelector.a(7, new CustomPathfinderGoalRandomStroll(this, 1.0D));
		goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		goalSelector.a(8, new CustomPathfinderGoalLookAtPlayer(this, CustomEntityGuard.class, 8.0F));
		goalSelector.a(8, new CustomPathfinderGoalRandomLookaround(this));
		goalSelector.a(4,
				new CustomPathfinderGoalMeleeAttack(this, CustomEntityGuard.class, ConfigEntries.PIGMAN_SPEED_TARGET.<Double> getValue()
						* (isBaby() ? 0.5D : 1.0D), true));
		targetSelector.a(1, new CustomPathfinderGoalHurtByTarget(this, true, new Class[] { EntityHuman.class }));
		targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
		targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, CustomEntityGuard.class, false));
	}

	@Override
	public boolean canSpawn() {
		return world.a(getBoundingBox(), this) && world.getCubes(this, getBoundingBox()).isEmpty()
				&& !world.containsLiquid(getBoundingBox());
	}

	@Override
	protected void aW() {
		super.aW();
		getAttributeInstance(GenericAttributes.maxHealth).setValue(ConfigEntries.PIGMAN_HEALTH.<Double> getValue());
		getAttributeInstance(GenericAttributes.c).setValue(ConfigEntries.PIGMAN_KNOCKBACK_RESIST.<Double> getValue());
		getAttributeInstance(GenericAttributes.d).setValue(ConfigEntries.PIGMAN_SPEED.<Double> getValue());
		getAttributeInstance(GenericAttributes.e).setValue(ConfigEntries.PIGMAN_DAMAGE.<Double> getValue());
	}

	@Override
	public void die() {
		if (!isBaby()) {
			int amount = ConfigEntries.PIGMAN_MULTIPLY_DEATH.<Integer> getValue();
			while (amount > 0) {
				CustomEntityPigZombie zombie = new CustomEntityPigZombie(world);
				zombie.setBaby(true);
				zombie.setLocation(
						locX + (random.nextInt(2) == 0 ? random.nextDouble() + 1 : -(1 + random.nextDouble())) * random.nextDouble(), locY,
						locZ + (random.nextInt(2) == 0 ? random.nextDouble() + 1 : -(1 + random.nextDouble())) * random.nextDouble(), yaw,
						pitch);
				world.addEntity(zombie);
				amount--;
			}
		} else {
			float magnitude = ConfigEntries.PIGMAN_EXPLODE_DEATH.<Double> getValue().floatValue();
			if (magnitude > 0) {
				world.createExplosion(this, locX, locY, locZ, magnitude, false, false);
			}
		}
		super.die();
	}

	@Override
	protected Item getLoot() {
		return null;
	}

	@Override
	protected void getRareDrop() {
		switch (random.nextInt(2)) {
		case 0:
			a(new ItemStack(Items.STICK), 0.0F);
			break;
		case 1:
			a(new ItemStack(Items.GOLD_INGOT), 0.0F);
			break;
		}
	}
}
