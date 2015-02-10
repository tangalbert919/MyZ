/**
 * 
 */
package jordan.sicherman.scheduled;

import java.util.HashSet;
import java.util.Set;

import jordan.sicherman.MyZ;
import jordan.sicherman.player.User;
import jordan.sicherman.player.User.UFiles;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.FileUtilities;

import org.bukkit.ChatColor;
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

	public static void report() {
		if (ConfigEntries.NOTIFY_SAVE.<Boolean> getValue()) {
			MyZ.log(ChatColor.YELLOW + "Performing save. Expect lag.");
		}

		doSave(new HashSet<User>(User.cache.values()));
	}

	private static void doSave(Set<User> users) {
		long start = System.currentTimeMillis();
		final Set<User> workingUsers = new HashSet<User>(users);

		for (User user : users) {
			for (UFiles file : UFiles.values()) {
				FileUtilities.save(user, file);
				workingUsers.remove(user);

				if (!workingUsers.isEmpty() && System.currentTimeMillis() - start >= 1000) {
					MyZ.instance.getServer().getScheduler().runTask(MyZ.instance, new Runnable() {
						@Override
						public void run() {
							doSave(workingUsers);
						}
					});
					return;
				}
			}
		}

		if (ConfigEntries.NOTIFY_SAVE.<Boolean> getValue()) {
			MyZ.log(ChatColor.YELLOW + "Save completed.");
		}
	}
}
