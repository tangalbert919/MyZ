/**
 * 
 */
package jordan.sicherman.player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jordan.sicherman.sql.SQLManager;
import jordan.sicherman.utilities.configuration.FileUtilities;
import jordan.sicherman.utilities.configuration.SpecificFileMember;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Jordan
 * 
 */
public class User {

	private static final String path = File.separator + "userdata" + File.separator + "$0";

	public static enum UFiles implements SpecificFileMember {
		PLAYER(0, "player.yml"), ACHIEVEMENTS(1, "achievements.yml"), STATISTICS(2, "stats.yml"), TRACKED(3, "tracked.yml"), SKILLS(4,
				"skills.yml"), KIT(5, "kit.yml");

		private final int id;
		private final String fileID;

		private UFiles(int id, String fileID) {
			this.id = id;
			this.fileID = fileID;
		}

		@Override
		public String getFileID() {
			return fileID;
		}

		public int getID() {
			return id;
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public void setFile(FileConfiguration file) {
		}

		@Override
		public FileConfiguration getFile() {
			return null;
		}

		@Override
		public boolean isLoaded() {
			return false;
		}
	}

	static class UserFile {
		private final FileConfiguration[] configurations;

		public UserFile(FileConfiguration... configurations) {
			this.configurations = configurations;
		}

		public FileConfiguration getFile(UFiles option) {
			return configurations[option.getID()];
		}
	}

	private final UserFile files;
	private final String storedPrimaryKey;
	public static Map<String, User> cache = new HashMap<String, User>();

	public static User forPlayer(OfflinePlayer playerFor) {
		User u = cache.get(SQLManager.primaryKeyFor(playerFor));
		if (u != null) { return u; }

		User user = new User(new UserFile(FileUtilities.load(playerFor, UFiles.values())), SQLManager.primaryKeyFor(playerFor));

		cache.put(SQLManager.primaryKeyFor(playerFor), user);
		return user;
	}

	public static User forPrimaryKey(String key) {
		User u = cache.get(key);
		if (u != null) { return u; }

		User user = new User(new UserFile(FileUtilities.load(key, UFiles.values())), key);

		cache.put(key, user);
		return user;
	}

	public static void freePlayer(OfflinePlayer playerFor) {
		User user = forPlayer(playerFor);
		for (UFiles file : UFiles.values()) {
			FileUtilities.save(user, file);
		}

		cache.remove(SQLManager.primaryKeyFor(playerFor));
	}

	private User(UserFile files, String storedPrimaryKey) {
		this.files = files;
		this.storedPrimaryKey = storedPrimaryKey;
	}

	public FileConfiguration getFile(UFiles filesFor) {
		return files.getFile(filesFor);
	}

	public String primaryKeyFor() {
		return storedPrimaryKey;
	}
}