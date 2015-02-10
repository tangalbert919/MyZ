/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

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
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Jordan
 * 
 */
public class CustomPathfinderGoalMeleeAttack extends PathfinderGoal {

	final World world;
	protected final EntityCreature creature;
	private int attackDelay;
	private final double speed;
	boolean bypassVision;
	private PathEntity path;
	private Class<? extends EntityLiving> targetClass;
	private int delay;
	private double targetX;
	private double targetY;
	private double targetZ;

	public CustomPathfinderGoalMeleeAttack(EntityCreature creature, Class<? extends EntityLiving> targetClass, double speed,
			boolean bypassVision) {
		this(creature, speed, bypassVision);
		this.targetClass = targetClass;
	}

	public CustomPathfinderGoalMeleeAttack(EntityCreature creature, double speed, boolean bypassVision) {
		this.creature = creature;
		world = creature.world;
		this.speed = speed;
		this.bypassVision = bypassVision;
		a(3);
	}

	@Override
	public boolean a() {
		EntityLiving localEntityLiving = creature.getGoalTarget();

		if (localEntityLiving == null) { return false; }
		if (!localEntityLiving.isAlive()) { return false; }
		if (targetClass != null && !targetClass.isAssignableFrom(localEntityLiving.getClass())) { return false; }

		path = creature.getNavigation().a(localEntityLiving);
		return path != null;
	}

	@Override
	public boolean b() {
		EntityLiving target = creature.getGoalTarget();

		EntityTargetEvent.TargetReason reason = creature.getGoalTarget() == null || target.hasEffect(MobEffectList.WITHER)
				|| target.hasEffect(MobEffectList.INVISIBILITY) ? EntityTargetEvent.TargetReason.FORGOT_TARGET
				: EntityTargetEvent.TargetReason.TARGET_DIED;
		if (creature.getGoalTarget() == null || !creature.getGoalTarget().isAlive()) {
			CraftEventFactory.callEntityTargetEvent(creature, null, reason);
		}

		return target == null ? false : !bypassVision ? false : !creature.getNavigation().g() ? true : !target.isAlive() ? false : creature
				.b(MathHelper.floor(target.locX), MathHelper.floor(target.locY), MathHelper.floor(target.locZ));
	}

	@Override
	public void c() {
		creature.getNavigation().a(path, speed);
		delay = 0;
	}

	@Override
	public void d() {
		creature.getNavigation().h();
	}

	@Override
	public void e() {
		EntityLiving target = creature.getGoalTarget();
		if (target == null || target.hasEffect(MobEffectList.WITHER) || target.hasEffect(MobEffectList.INVISIBILITY)) {
			creature.setGoalTarget(null);
			return;
		}

		// Look at our target.
		creature.getControllerLook().a(target, 30.0F, 30.0F);

		double distance = creature.e(target.locX, target.boundingBox.b, target.locZ);
		double sizeOfEntities = creature.width * 2.0F * creature.width * 2.0F + target.width;
		delay -= 1;

		// Bypass or can see.
		if ((bypassVision || creature.getEntitySenses().canSee(target)) && delay <= 0
				&& (targetX == 0.0D && targetY == 0.0D && targetZ == 0.0D || target.e(targetX, targetY, targetZ) >= 1.0D)) {
			targetX = target.locX;
			targetY = target.boundingBox.b;
			targetZ = target.locZ;

			delay = 4 + creature.aI().nextInt(7);
			if (distance > 1024.0D) {
				delay += 10;
			} else if (distance > 256.0D) {
				delay += 5;
			}

			if (!creature.getNavigation().a(target, speed)) {
				delay += 15;
			}
		}
		attackDelay = Math.max(attackDelay - 1, 0);
		if (distance <= sizeOfEntities && attackDelay <= 0) {
			if (target != null && target instanceof EntityHuman
					&& ((Player) target.getBukkitEntity()).hasPotionEffect(PotionEffectType.WITHER)) {
				creature.setGoalTarget(null);
			}
			attackDelay = 20;

			// Animate if we're holding a sword.
			if (creature.be() != null) {
				creature.ba();
			}
			// Last damage cause?
			creature.n(target);
		}
	}

	protected double a(EntityLiving paramEntityLiving) {
		return creature.width * 2.0F * (creature.width * 2.0F) + paramEntityLiving.width;
	}
}
