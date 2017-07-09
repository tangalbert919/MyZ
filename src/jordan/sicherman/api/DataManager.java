package jordan.sicherman.api;

import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.OfflinePlayer;

public class DataManager {

    private static DataManager instance;

    public DataManager() {
        DataManager.instance = this;
    }

    public static DataManager getInstance() {
        return DataManager.instance;
    }

    public Object get(OfflinePlayer playerFor, UserEntries entry) {
        return DataWrapper.get(playerFor, entry);
    }

    public void set(OfflinePlayer playerFor, UserEntries entry, Object value, boolean async) {
        this.set(playerFor, entry, value, async, true);
    }

    public void set(OfflinePlayer playerFor, UserEntries entry, Object value, boolean async, boolean save) {
        DataWrapper.set(playerFor, entry, value, new boolean[] { async, save});
    }

    public void set(ConfigEntries entry, Object value, boolean save) {
        DataWrapper.set(entry, value, save);
    }
}
