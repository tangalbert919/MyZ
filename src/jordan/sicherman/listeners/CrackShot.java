/**
 * 
 */
package jordan.sicherman.listeners;

import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.VisibilityManager;
import jordan.sicherman.utilities.configuration.ConfigEntries;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.shampaggon.crackshot.events.WeaponReloadEvent;
import com.shampaggon.crackshot.events.WeaponShootEvent;

/**
 * @author Jordan
 * 
 */
public class CrackShot implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onReload(WeaponReloadEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		VisibilityManager.getInstance().overloadXPBarVisibility(e.getPlayer(), ConfigEntries.getCrackshot(e.getWeaponTitle(), "reload")/18f,
				100L);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onShoot(WeaponShootEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		VisibilityManager.getInstance().overloadXPBarVisibility(e.getPlayer(), ConfigEntries.getCrackshot(e.getWeaponTitle(), "shoot")/18f,
				100L);
	}
}
