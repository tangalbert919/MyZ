/**
 * 
 */
package jordan.sicherman.api;

import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.OfflinePlayer;

/**
 * @author Jordan
 * 
 */
public class DataManager {

	private static DataManager instance;

	public DataManager() {
		instance = this;
	}

	public static DataManager getInstance() {
		return instance;
	}

	public <T> T get(OfflinePlayer playerFor, UserEntries entry) {
		return DataWrapper.get(playerFor, entry);
	}

	public void set(OfflinePlayer playerFor, UserEntries entry, Object value, boolean async) {
		set(playerFor, entry, value, async, true);
	}

	public void set(OfflinePlayer playerFor, UserEntries entry, Object value, boolean async, boolean save) {
		DataWrapper.set(playerFor, entry, value, async, save);
	}

	public void set(ConfigEntries entry, Object value, boolean save) {
		DataWrapper.set(entry, value, save);
	}
}
