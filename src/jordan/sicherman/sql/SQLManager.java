package jordan.sicherman.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class SQLManager {

	private static final String[] values = UserEntries.valueKeysForSQL();
	private Connection sql;
	public final String hostname, database, username, password;
	public final int port;
	private boolean connected, pseudo;
	private final Map<String, Map<String, Object>> cachedValues = new HashMap<String, Map<String, Object>>();
	private final List<String> cachedKeys = new ArrayList<String>();

	/**
	 * @param hostname
	 *            Host
	 * @param port
	 *            Port
	 * @param database
	 *            Database
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 */
	public SQLManager(String hostname, int port, String database, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		MyZ.instance.getServer().getScheduler().runTaskAsynchronously(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				connect();

				if (!isConnected()) {
					MyZ.sql = new SQLManager();
				}
			}
		});
	}

	/**
	 * A pseudo SQLManager, for when the session is disconnected.
	 */
	public SQLManager() {
		hostname = "";
		port = 0;
		database = "";
		username = "";
		password = "";
		pseudo = true;
	}

	private ResultSet query(String cmd) {
		if (isPseudo()) { return null; }
		if (!isConnected()) { return null; }

		try {
			return sql.createStatement().executeQuery(cmd);
		} catch (Exception e) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + " '" + cmd + "': " + e.getMessage());
			return null;
		}
	}

	public void connect() {
		if (isPseudo()) { return; }
		if (isConnected()) { return; }

		MyZ.log(ChatColor.YELLOW + LocaleMessage.SQL_CONNECTING.toString());

		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database;

		try {
			sql = DriverManager.getConnection(url, username, password);
			connected = true;
			MyZ.log(ChatColor.GREEN + LocaleMessage.SQL_CONNECT.toString());
			setup();
		} catch (Exception e) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_NO_CONNECT.toString() + ": " + e.getMessage());
			MyZ.log(ChatColor.YELLOW + LocaleMessage.NO_SQL.toString());
			connected = false;
		}
	}

	public void disconnect() {
		if (isPseudo()) { return; }

		if (sql != null && isConnected()) {
			MyZ.log(ChatColor.YELLOW + LocaleMessage.SQL_DISCONNECT.toString());
			try {
				sql.close();
			} catch (SQLException e) {
				MyZ.log(ChatColor.RED + LocaleMessage.SQL_NO_DISCONNECT.toString() + ": " + e.getMessage());
			}
		}
	}

	public void executeQuery(String query) throws SQLException {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		sql.createStatement().executeUpdate(query);
	}

	public void add(OfflinePlayer playerFor) {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		if (!isIn(playerFor)) {
			try {
				executeQuery("INSERT INTO playerdata (username) VALUES ('" + primaryKeyFor(playerFor) + "')");
			} catch (Exception e) {
				MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + ": " + e.getMessage());
			}
		}

		load(playerFor);
	}

	public boolean isIn(OfflinePlayer player) {
		if (isPseudo()) { return true; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return false;
		}

		try {
			return query("SELECT * FROM playerdata WHERE username = '" + primaryKeyFor(player) + "' LIMIT 1").next();
		} catch (Exception e) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + ": " + e.getMessage());
			return false;
		}
	}

	public void load(final OfflinePlayer player) {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		if (!isIn(player)) { return; }

		MyZ.instance.getServer().getScheduler().runTaskLaterAsynchronously(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				for (String key : values) {
					get(player, key);
				}
			}
		}, 0L);
	}

	public List<String> getKeys() {
		if (isPseudo()) { return null; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return null;
		}

		if (!cachedKeys.isEmpty()) { return cachedKeys; }

		List<String> list = new ArrayList<String>();

		try {
			ResultSet rs = query("SELECT * FROM playerdata WHERE username != 'null'");
			if (rs != null) {
				while (rs.next()) {
					if (rs.getString("username") != null) {
						list.add(rs.getString("username"));
					}
				}
			}
		} catch (Exception e) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + ": " + e.getMessage());
		}

		cachedKeys.clear();
		cachedKeys.addAll(list);

		return list;
	}

	public boolean isConnected() {
		return connected;
	}

	public void set(final OfflinePlayer playerFor, final String field, final Object value, boolean aSync) {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		if (aSync) {
			MyZ.instance.getServer().getScheduler().runTaskLaterAsynchronously(MyZ.instance, new Runnable() {
				@Override
				public void run() {
					set(playerFor, field, value, false);
				}
			}, 0L);
			return;
		}

		String pk = primaryKeyFor(playerFor);

		try {
			executeQuery("UPDATE playerdata SET " + field + " = " + (value instanceof String ? "'" + value + "'" : value)
					+ " WHERE username = '" + pk + "' LIMIT 1");
			if (!cachedValues.containsKey(pk)) {
				cachedValues.put(pk, new HashMap<String, Object>());
			}
			cachedValues.get(pk).put(field, value);
		} catch (Exception e) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + ": " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(OfflinePlayer playerFor, String field) {
		if (isPseudo()) { return null; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return null;
		}

		T gotten = null;
		String pk = primaryKeyFor(playerFor);

		if (!cachedValues.containsKey(pk) || !cachedValues.get(pk).containsKey(field)) {
			if (!cachedValues.containsKey(pk)) {
				cachedValues.put(pk, new HashMap<String, Object>());
			}

			try {
				ResultSet rs = query("SELECT * FROM playerdata WHERE username = '" + pk + "' LIMIT 1");
				Object retrieved = null;
				if (rs.next()) {
					retrieved = rs.getObject(field);
				}
				gotten = (T) retrieved;

				cachedValues.get(pk).put(field, retrieved);
			} catch (Exception e) {
				MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + ": " + e.getMessage());
			}
		} else {
			gotten = (T) cachedValues.get(pk).get(field);
		}

		return gotten;
	}

	public static String primaryKeyFor(OfflinePlayer playerFor) {
		return playerFor.getUniqueId().toString();
	}

	public static Player fromPrimaryKey(String playerKey) {
		return Bukkit.getPlayer(UUID.fromString(playerKey));
	}

	public boolean isPseudo() {
		return pseudo;
	}

	public void setup() {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		try {
			executeQuery("CREATE TABLE IF NOT EXISTS playerdata (" + UserEntries.convertToMySQLQuery() + ")");
		} catch (Exception e) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + ": " + e.getMessage());
		}
	}
}
