/**
 * 
 */
package jordan.sicherman.listeners.player;

import jordan.sicherman.MyZ;
import jordan.sicherman.utilities.Utilities;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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
	}
}
