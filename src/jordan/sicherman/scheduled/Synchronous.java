/**
 * 
 */
package jordan.sicherman.scheduled;

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

	}

	private void report() {
	}
}
