/**
 * 
 */
package jordan.sicherman.listeners.player;

import jordan.sicherman.utilities.Utilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Jordan
 * 
 */
public class Join implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	private void onJoin(PlayerJoinEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		Utilities.doLogin(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onChangeJoin(PlayerChangedWorldEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (!Utilities.inWorld(e.getFrom())) {
			Utilities.doLogin(e.getPlayer());
		}
	}
}
