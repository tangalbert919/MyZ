/**
 * 
 */
package jordan.sicherman.scheduled;

import jordan.sicherman.player.User;
import jordan.sicherman.player.User.UFiles;
import jordan.sicherman.utilities.configuration.FileUtilities;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class Synchronous extends BukkitRunnable {

	@Override
	public void run() {
		report();
	}

	@Override
	public void cancel() {
		report();
	}

	private void report() {
		for (User user : User.cache.values()) {
			for (UFiles file : UFiles.values()) {
				FileUtilities.save(user, file);
			}
		}
	}
}
