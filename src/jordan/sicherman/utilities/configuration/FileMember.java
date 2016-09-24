package jordan.sicherman.utilities.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public interface FileMember {

    String getFileID();

    String getPath();

    void setFile(FileConfiguration fileconfiguration);

    FileConfiguration getFile();

    boolean isLoaded();
}
