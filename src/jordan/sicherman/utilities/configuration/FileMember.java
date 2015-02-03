/**
 * 
 */
package jordan.sicherman.utilities.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Jordan
 * 
 */
public interface FileMember {

	public String getFileID();

	public String getPath();

	public void setFile(FileConfiguration file);

	public FileConfiguration getFile();

	public boolean isLoaded();
}
