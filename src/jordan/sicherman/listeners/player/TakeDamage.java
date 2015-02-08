/**
 * 
 */
package jordan.sicherman.listeners.player;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public class TakeDamage implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onEnvironmental(EntityDamageEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getEntity() instanceof Player) {
			Utilities.setBleeding((Player) e.getEntity(), true, false);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onMob(EntityDamageByEntityEvent e) {
		if (!Utilities.inWorld(e.getEntity()) || e.getDamage() == 0.0D) { return; }

		if (e.getEntity() instanceof Player) {
			Utilities.setBleeding((Player) e.getEntity(), true, false);

			if (e.getDamager().getType() == EntityType.ZOMBIE || e.getDamager().getType() == EntityType.PIG_ZOMBIE
					|| e.getDamager().getType() == EntityType.GIANT || e.getDamager() instanceof Player
					&& MyZ.zombieFactory.isZombie((Player) e.getDamager())) {
				Utilities.setPoisoned((Player) e.getEntity(), true, false);
			}
		}

		if (e.getDamager() instanceof Player) {
			if (e.getCause() == DamageCause.ENTITY_ATTACK && e.getDamager() instanceof Player
					&& ((Player) e.getDamager()).getItemInHand() != null) {
				ItemStack item = ((Player) e.getDamager()).getItemInHand();
				if (EquipmentState.getState(item) == EquipmentState.BOW_SHARPENED) {
					e.setDamage(e.getDamage() + ConfigEntries.BOW_SHARPENED_COMBAT_MOD.<Integer> getValue());
				}
			}
			ItemStack hand = ((Player) e.getDamager()).getItemInHand();
			double effectiveness = EquipmentState.getEffectiveness(hand);
			e.setDamage(e.getDamage() * effectiveness);

		}
	}
}
