/**
 * 
 */
package jordan.sicherman.listeners.player;

import jordan.sicherman.MyZ;
import jordan.sicherman.utilities.ReviveManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * @author Jordan
 * 
 */
public class Revive implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onRevive(PlayerInteractEntityEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (ConfigEntries.ALLOW_REVIVALS.<Boolean> getValue() && e.getRightClicked() instanceof Player) {
			Player clicked = (Player) e.getRightClicked();
			if (MyZ.ghostFactory.isGhost(clicked) && !ReviveManager.getInstance().isBeingRevived(clicked)) {
				ReviveManager.getInstance().begin(e.getPlayer(), clicked);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onRespawn(PlayerRespawnEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		Utilities.respawn(e.getPlayer());
	}
}
