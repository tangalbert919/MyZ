/**
 * 
 */
package jordan.sicherman.utilities.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Jordan
 * @param <T>
 * 
 */
public class Configuration {

	public Configuration() {
		FileUtilities.load(CFiles.values());
		CFiles.loadValues();
	}

	public static enum CFiles implements FileMember {
		CONFIG("config.yml"), MOBS("mob_settings.yml"), EXTRAS("settings.yml"), SPAWNLOCATIONS("spawns.yml"), CHESTS("chests.yml"), MYSQL(
				"SQL.yml"), ENGINEER("engineering.yml"), ACHIEVEMENTS("achievements.yml"), ADDONS("addons.yml"), RANKS("ranks.yml");

		private final String fileID;
		private FileConfiguration file;

		private CFiles(String fileID) {
			this.fileID = fileID;
		}

		@Override
		public String getFileID() {
			return fileID;
		}

		@Override
		public String getPath() {
			return "";
		}

		public static void loadValues() {
			for (ConfigEntries entry : ConfigEntries.values()) {
				FileUtilities.set(entry.getKey(), entry.getValue(), entry.getFile(), false);
			}
			FileUtilities.save(CFiles.values());
		}

		@Override
		public void setFile(FileConfiguration file) {
			this.file = file;
		}

		@Override
		public FileConfiguration getFile() {
			return file;
		}

		@Override
		public boolean isLoaded() {
			return file != null;
		}
	}
}
