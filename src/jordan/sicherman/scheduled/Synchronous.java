package jordan.sicherman.scheduled;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jordan.sicherman.MyZ;
import jordan.sicherman.player.User;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.FileUtilities;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Synchronous extends BukkitRunnable {

    public void run() {
        report();
    }

    public void cancel() {
        report();
    }

    public static void report() {
        if (((Boolean) ConfigEntries.NOTIFY_SAVE.getValue()).booleanValue()) {
            MyZ.log(ChatColor.YELLOW + "Performing save. Expect lag.");
        }

        doSave(new HashSet(User.cache.values()));
    }

    public static void save() {
        if (((Boolean) ConfigEntries.NOTIFY_SAVE.getValue()).booleanValue()) {
            MyZ.log(ChatColor.YELLOW + "Performing save. Expect lag.");
        }

        Iterator iterator = User.cache.values().iterator();

        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            User.UFiles[] auser_ufiles = User.UFiles.values();
            int i = auser_ufiles.length;

            for (int j = 0; j < i; ++j) {
                User.UFiles file = auser_ufiles[j];

                FileUtilities.save(user, file);
            }
        }

        if (((Boolean) ConfigEntries.NOTIFY_SAVE.getValue()).booleanValue()) {
            MyZ.log(ChatColor.YELLOW + "Save completed.");
        }

    }

    private static void doSave(Set users) {
        long start = System.currentTimeMillis();
        final HashSet workingUsers = new HashSet(users);
        Iterator iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            User.UFiles[] auser_ufiles = User.UFiles.values();
            int i = auser_ufiles.length;

            for (int j = 0; j < i; ++j) {
                User.UFiles file = auser_ufiles[j];

                FileUtilities.save(user, file);
                workingUsers.remove(user);
                if (!workingUsers.isEmpty() && System.currentTimeMillis() - start >= 1000L) {
                    MyZ.instance.getServer().getScheduler().runTask(MyZ.instance, new Runnable() {
                        public void run() {
                            Synchronous.doSave(workingUsers);
                        }
                    });
                    return;
                }
            }
        }

        if (((Boolean) ConfigEntries.NOTIFY_SAVE.getValue()).booleanValue()) {
            MyZ.log(ChatColor.YELLOW + "Save completed.");
        }

    }
}
