package jordan.sicherman.utilities.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    public Configuration() {
        FileUtilities.load(Configuration.CFiles.values());
        Configuration.CFiles.loadValues();
    }

    public static enum CFiles implements FileMember {

        CONFIG("config.yml"), MOBS("mob_settings.yml"), EXTRAS("settings.yml"), SPAWNLOCATIONS("spawns.yml"), CHESTS("chests.yml"), ENGINEER("engineering.yml"), ACHIEVEMENTS("achievements.yml"), ADDONS("addons.yml"), RANKS("ranks.yml");

        private final String fileID;
        private FileConfiguration file;

        private CFiles(String fileID) {
            this.fileID = fileID;
        }

        public String getFileID() {
            return this.fileID;
        }

        public String getPath() {
            return "";
        }

        public static void loadValues() {
            ConfigEntries[] aconfigentries = ConfigEntries.values();
            int i = aconfigentries.length;

            for (int j = 0; j < i; ++j) {
                ConfigEntries entry = aconfigentries[j];

                FileUtilities.set(entry.getKey(), entry.getValue(), entry.getFile(), false);
            }

            ConfigEntries.loadCrackshot();
            ConfigEntries.loadImportantDefaults();
            FileUtilities.save(values());
        }

        public void setFile(FileConfiguration file) {
            this.file = file;
        }

        public FileConfiguration getFile() {
            return this.file;
        }

        public boolean isLoaded() {
            return this.file != null;
        }
    }
}
