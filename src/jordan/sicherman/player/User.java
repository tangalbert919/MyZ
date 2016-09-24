package jordan.sicherman.player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import jordan.sicherman.MyZ;
import jordan.sicherman.utilities.configuration.FileUtilities;
import jordan.sicherman.utilities.configuration.SpecificFileMember;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

public class User {

    private static final String path = File.separator + "userdata" + File.separator + "$0";
    private final User.UserFile files;
    private final String storedPrimaryKey;
    public static Map cache = new HashMap();

    public static User forPlayer(OfflinePlayer playerFor) {
        User u = (User) User.cache.get(playerFor.getUniqueId().toString());

        if (u != null) {
            return u;
        } else {
            User user = new User(new User.UserFile(FileUtilities.load(playerFor, (SpecificFileMember[]) User.UFiles.values())), playerFor.getUniqueId().toString());

            User.cache.put(playerFor.getUniqueId().toString(), user);
            return user;
        }
    }

    public static User forPrimaryKey(String key) {
        User u = (User) User.cache.get(key);

        if (u != null) {
            return u;
        } else {
            User user = new User(new User.UserFile(new FileConfiguration[] { FileUtilities.load(key, (SpecificFileMember[]) User.UFiles.values())}), key);

            User.cache.put(key, user);
            return user;
        }
    }

    public static void freePlayer(final OfflinePlayer playerFor) {
        final User user = forPlayer(playerFor);

        MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
            public void run() {
                User.UFiles[] auser_ufiles = User.UFiles.values();
                int i = auser_ufiles.length;

                for (int j = 0; j < i; ++j) {
                    User.UFiles file = auser_ufiles[j];

                    FileUtilities.save(user, file);
                }

                User.cache.remove(playerFor.getUniqueId().toString());
            }
        }, 1L);
    }

    private User(User.UserFile files, String storedPrimaryKey) {
        this.files = files;
        this.storedPrimaryKey = storedPrimaryKey;
    }

    public FileConfiguration getFile(User.UFiles filesFor) {
        return this.files.getFile(filesFor);
    }

    public String primaryKeyFor() {
        return this.storedPrimaryKey;
    }

    static class UserFile {

        private final FileConfiguration[] configurations;

        public UserFile(FileConfiguration... configurations) {
            this.configurations = configurations;
        }

        public FileConfiguration getFile(User.UFiles option) {
            return this.configurations[option.getID()];
        }
    }

    public static enum UFiles implements SpecificFileMember {

        PLAYER(0, "player.yml"), ACHIEVEMENTS(1, "achievements.yml"), STATISTICS(2, "stats.yml"), TRACKED(3, "tracked.yml"), SKILLS(4, "skills.yml"), KIT(5, "kit.yml");

        private final int id;
        private final String fileID;

        private UFiles(int id, String fileID) {
            this.id = id;
            this.fileID = fileID;
        }

        public String getFileID() {
            return this.fileID;
        }

        public int getID() {
            return this.id;
        }

        public String getPath() {
            return User.path;
        }

        public void setFile(FileConfiguration file) {}

        public FileConfiguration getFile() {
            return null;
        }

        public boolean isLoaded() {
            return false;
        }
    }
}
