/**
 * 
 */
package jordan.sicherman.utilities;

import jordan.sicherman.MyZ;
import jordan.sicherman.nms.utilities.EntryType;
import jordan.sicherman.player.User;
import jordan.sicherman.scheduled.Asynchronous;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.FileUtilities;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.OfflinePlayer;

/**
 * @author Jordan
 * 
 */
public class DataWrapper {

	public static enum FlushType {
		SQL_TO_USERDATA, USERDATA_TO_SQL;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(OfflinePlayer playerFor, UserEntries entry) {
		switch (entry.getType()) {
		case BOOLEAN:
			return (T) (Boolean) User.forPlayer(playerFor).getFile(entry.getFile()).getBoolean(entry.getKey());
		case DOUBLE:
			return (T) (Double) User.forPlayer(playerFor).getFile(entry.getFile()).getDouble(entry.getKey());
		case INTEGER:
			return (T) (Integer) User.forPlayer(playerFor).getFile(entry.getFile()).getInt(entry.getKey());
		case ITEMSTACK:
			return (T) User.forPlayer(playerFor).getFile(entry.getFile()).getItemStack(entry.getKey());
		case LIST:
			return (T) User.forPlayer(playerFor).getFile(entry.getFile()).getList(entry.getKey());
		case STRING:
			return (T) User.forPlayer(playerFor).getFile(entry.getFile()).getString(entry.getKey());
		default:
			return null;
		}
	}

	public static void set(OfflinePlayer playerFor, UserEntries entry, Object value) {
		set(playerFor, entry, value, true, false);
	}

	/**
	 * @param options
	 *            options[0] = async ----------------------------------------
	 *            options[1] = save -----------------------------------------
	 *            Any less will result in an exception ----------------------
	 */
	public static void set(OfflinePlayer playerFor, UserEntries entry, Object value, boolean... options) {
		if (MyZ.sql.isConnected() && UserEntries.isMySQLKey(entry)) {
			boolean update = true;
			switch (entry) {
			case TEMPERATURE:
			case THIRST:
				update = Asynchronous.tickCount == 0;
				break;
			default:
				break;
			}

			if (update) {
				MyZ.sql.set(playerFor, entry.getKey().replaceAll("\\.", "_"),
						entry.getType() == EntryType.LIST ? UserEntries.toStringList(value) : value, options[0]);
			}
		}

		doSet(User.forPlayer(playerFor), entry, value, options);

		switch (entry) {
		case DEATHS:
		case DRINKS:
		case FRIENDS:
		case GIANT_KILLS:
		case OTHER_HEALS:
		case PIGMAN_KILLS:
		case PLAYER_KILLS:
		case REVIVALS:
		case REVIVES:
		case SELF_HEALS:
		case UNINFECTS:
		case ZOMBIE_KILLS:
		case GHOST_TIMES:
		case ZOMBIE_TIMES:
			AchievementManager.getInstance().refresh(playerFor, true);
			break;
		default:
			break;

		}
	}

	public static void set(User user, String entry, Object value) {
		doSet(user, UserEntries.fromString(entry), value, true, false);
	}

	private static void doSet(final User userFor, final UserEntries entry, final Object value, final boolean... options) {
		MyZ.instance.getServer().getScheduler().runTask(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				FileUtilities.set(entry.getKey(), value, userFor, entry.getFile(), options[1]);
			}
		});
	}

	public static void set(ConfigEntries entry, Object value, boolean save) {
		entry.getFile().getFile().set(entry.getKey(), value);
		if (save) {
			FileUtilities.save(entry.getFile());
		}
	}
}
