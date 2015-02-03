/**
 * 
 */
package jordan.sicherman.listeners.player;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class Healer implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void oneHealSelf(PlayerInteractEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (!ConfigEntries.USE_BANDAGES.<Boolean> getValue()) { return; }

		Player playerFor = e.getPlayer();
		ItemStack with = e.getPlayer().getItemInHand();
		if (ItemUtilities.getInstance().hasTag(with, ItemTag.BANDAGE)) {
			Utilities.setBleeding(playerFor, false, false);
			double value = ConfigEntries.BANDAGE_HEAL.<Double> getValue();
			if (playerFor.getHealth() + value < playerFor.getMaxHealth()) {
				playerFor.setHealth(playerFor.getHealth() + value);
			}
			if (with.getAmount() > 1) {
				with.setAmount(with.getAmount() - 1);
			} else {
				playerFor.setItemInHand(null);
			}
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	private void oneHealOther(EntityDamageByEntityEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (!ConfigEntries.USE_HEALING.<Boolean> getValue() || !(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) { return; }

		final Player other = (Player) e.getEntity();
		final Player healer = (Player) e.getDamager();

		final ItemStack with = healer.getItemInHand();

		if (with == null || !ItemUtilities.getInstance().hasTag(with, ItemTag.BANDAGE)
				&& !ItemUtilities.getInstance().hasTag(with, ItemTag.SCISSORS)
				&& !ItemUtilities.getInstance().hasTag(with, ItemTag.OINTMENT)
				&& !ItemUtilities.getInstance().hasTag(with, ItemTag.ANTISEPTIC)
				&& !ItemUtilities.getInstance().hasTag(with, ItemTag.MEDICINE)) { return; }

		e.setCancelled(true);

		if (other.hasMetadata("MyZ.heal.cooldown")) {
			if (System.currentTimeMillis() >= other.getMetadata("MyZ.heal.cooldown").get(0).asLong()) {
				other.removeMetadata("MyZ.heal.cooldown", MyZ.instance);
			}
		}

		if ((!other.hasMetadata("MyZ.heal.process") || !healer.hasMetadata("MyZ.heal.healee"))
				&& ItemUtilities.getInstance().hasTag(with, ItemTag.SCISSORS)) {
			healer.sendMessage(LocaleMessage.HEAL_CHECK.filter(
					Utilities.yesNo(healer, DataWrapper.<Boolean> get(other, UserEntries.BLEEDING)), (int) other.getHealth()).toString(
					healer));
			return;
		}

		if (other.hasMetadata("MyZ.heal.cooldown") || other.hasMetadata("MyZ.heal.process") || healer.hasMetadata("MyZ.heal.healee")) {
			if (other.hasMetadata("MyZ.heal.cooldown")) {
				healer.sendMessage(LocaleMessage.HEAL_COOLING_DOWN.filter(
						(int) ((other.getMetadata("MyZ.heal.cooldown").get(0).asLong() - System.currentTimeMillis()) / 1000)).toString(
						healer));
				return;
			}

			int healer_id = other.getMetadata("MyZ.heal.healer").get(0).asInt();
			int healee_id = healer.getMetadata("MyZ.heal.healee").get(0).asInt();

			if (healee_id != other.getEntityId() || healer_id != healer.getEntityId()) {
				healer.sendMessage(LocaleMessage.HEALING_ALREADY.toString(healer));
				return;
			}
		}

		if (ItemUtilities.getInstance().hasTag(with, ItemTag.BANDAGE) && !other.hasMetadata("MyZ.heal.process")
				&& !healer.hasMetadata("MyZ.heal.healee")) {
			other.setMetadata("MyZ.heal.process", new FixedMetadataValue(MyZ.instance, 1));
			other.setMetadata("MyZ.heal.healer", new FixedMetadataValue(MyZ.instance, healer.getEntityId()));
			healer.setMetadata("MyZ.heal.healee", new FixedMetadataValue(MyZ.instance, other.getEntityId()));
			new BukkitRunnable() {
				@Override
				public void run() {
					if (other != null && healer != null) {
						if (other.isOnline() && !other.isDead()) {
							if (other.hasMetadata("MyZ.heal.process")) {
								other.sendMessage(LocaleMessage.HEAL_NOT_COMPLETE.toString(other));
							}
						}
						if (healer.isOnline() && !healer.isDead()) {
							if (healer.hasMetadata("MyZ.heal.healee")) {
								healer.sendMessage(LocaleMessage.HEAL_NOT_COMPLETED.toString(other));
							}
						}
						other.removeMetadata("MyZ.heal.process", MyZ.instance);
						other.removeMetadata("MyZ.heal.healer", MyZ.instance);
						healer.removeMetadata("MyZ.heal.healee", MyZ.instance);
					}
				}

				@Override
				public void cancel() {
					if (other != null && healer != null) {
						other.removeMetadata("MyZ.heal.process", MyZ.instance);
						other.removeMetadata("MyZ.heal.healer", MyZ.instance);
						healer.removeMetadata("MyZ.heal.healee", MyZ.instance);
					}
				}
			}.runTaskLater(MyZ.instance, 20L * ConfigEntries.HEAL_TIMEOUT.<Integer> getValue());
			return;
		}

		if (ItemUtilities.getInstance().hasTag(with, ItemTag.OINTMENT)) {
			int id = other.getMetadata("MyZ.heal.process").get(0).asInt();
			other.setMetadata("MyZ.heal.process", new FixedMetadataValue(MyZ.instance, id == 1 ? 2 : 4));
		} else if (ItemUtilities.getInstance().hasTag(with, ItemTag.ANTISEPTIC)) {
			int id = other.getMetadata("MyZ.heal.process").get(0).asInt();
			other.setMetadata("MyZ.heal.process", new FixedMetadataValue(MyZ.instance, id == 1 ? 3 : 4));
		} else if (ItemUtilities.getInstance().hasTag(with, ItemTag.MEDICINE)) {
			other.setMetadata("MyZ.heal.process", new FixedMetadataValue(MyZ.instance, 4));
		} else if (ItemUtilities.getInstance().hasTag(with, ItemTag.SCISSORS)) {
			int id = other.getMetadata("MyZ.heal.process").get(0).asInt();
			int duration = ConfigEntries.DEFAULT_HEAL_DURATION.<Integer> getValue();
			int absorbDuration = ConfigEntries.DEFAULT_ABSORPTION_DURATION.<Integer> getValue();
			int level = ConfigEntries.DEFAULT_HEAL_LEVEL.<Integer> getValue();
			int absorbLevel = ConfigEntries.DEFAULT_ABSORPTION_LEVEL.<Integer> getValue();
			boolean infectHeal = ConfigEntries.DEFAULT_ANTISEPTIC.<Boolean> getValue();
			switch (id) {
			case 4:
				duration += ConfigEntries.MEDICINE_HEAL_EXTENSION.<Integer> getValue();
				level = ConfigEntries.MEDICINE_HEAL_LEVEL.<Integer> getValue();
				absorbLevel = ConfigEntries.MEDICINE_ABSORPTION_LEVEL.<Integer> getValue();
				infectHeal = ConfigEntries.MEDICINE_ANTISEPTIC.<Boolean> getValue();
				absorbDuration += ConfigEntries.MEDICINE_ABSORPTION_EXTENSION.<Integer> getValue();
				break;
			case 2:
				duration += ConfigEntries.OINTMENT_HEAL_EXTENSION.<Integer> getValue();
				level = ConfigEntries.OINTMENT_HEAL_LEVEL.<Integer> getValue();
				absorbLevel = ConfigEntries.OINTMENT_ABSORPTION_LEVEL.<Integer> getValue();
				infectHeal = ConfigEntries.OINTMENT_ANTISEPTIC.<Boolean> getValue();
				absorbDuration += ConfigEntries.OINTMENT_ABSORPTION_EXTENSION.<Integer> getValue();
				break;
			case 3:
				duration += ConfigEntries.ANTISEPTIC_HEAL_EXTENSION.<Integer> getValue();
				level = ConfigEntries.ANTISEPTIC_HEAL_LEVEL.<Integer> getValue();
				absorbLevel = ConfigEntries.ANTISEPTIC_ABSORPTION_LEVEL.<Integer> getValue();
				infectHeal = ConfigEntries.ANTISEPTIC_ANTISEPTIC.<Boolean> getValue();
				absorbDuration += ConfigEntries.ANTISEPTIC_ABSORPTION_EXTENSION.<Integer> getValue();
			}
			if (absorbDuration > 0) {
				other.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, absorbDuration * 20, absorbLevel));
			}
			if (duration > 0) {
				other.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration * 20, level));
			}
			if (infectHeal) {
				Utilities.setPoisoned(other, false, false);
			}

			DataWrapper.set(healer, UserEntries.OTHER_HEALS, DataWrapper.<Integer> get(healer, UserEntries.OTHER_HEALS) + 1);
			other.removeMetadata("MyZ.heal.process", MyZ.instance);
			other.removeMetadata("MyZ.heal.healer", MyZ.instance);
			healer.removeMetadata("MyZ.heal.healee", MyZ.instance);
			other.setMetadata("MyZ.heal.cooldown", new FixedMetadataValue(MyZ.instance, System.currentTimeMillis()
					+ ConfigEntries.HEAL_COOLDOWN.<Integer> getValue() * 1000L));
			healer.sendMessage(LocaleMessage.HEALED_PLAYER.filter(Utilities.getPrefixFor(other) + other.getName()).toString(healer));
			other.sendMessage(LocaleMessage.HEALED.filter(Utilities.getPrefixFor(healer) + healer.getName()).toString(other));
		}
	}
}
