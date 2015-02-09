package jordan.sicherman.sql;

import java.io.File;
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
import jordan.sicherman.player.User;
import jordan.sicherman.player.User.UFiles;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

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

	public void executeQuery(final String query, boolean async) {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		if (async) {
			MyZ.instance.getServer().getScheduler().runTaskAsynchronously(MyZ.instance, new Runnable() {
				@Override
				public void run() {
					try {
						sql.createStatement().executeUpdate(query);
					} catch (Exception exc) {

					}
				}
			});
		} else {
			try {
				sql.createStatement().executeUpdate(query);
			} catch (Exception e) {
				MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + ": " + e.getMessage());
			}
		}
	}

	public void add(OfflinePlayer playerFor) {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		add(primaryKeyFor(playerFor));

		load(playerFor);
	}

	private void add(String key) {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		if (!isIn(key)) {
			executeQuery("INSERT INTO playerdata (username) VALUES ('" + key + "')", true);
		}
	}

	public boolean isIn(String key) {
		if (isPseudo()) { return true; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return false;
		}

		try {
			return query("SELECT * FROM playerdata WHERE username = '" + key + "' LIMIT 1").next();
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

		if (!isIn(primaryKeyFor(player))) { return; }

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

	public void set(OfflinePlayer playerFor, String field, Object value, boolean aSync) {
		set(primaryKeyFor(playerFor), field, value, aSync);
	}

	private void set(final String key, final String field, final Object value, final boolean aSync) {
		if (isPseudo()) { return; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return;
		}

		if (aSync) {
			MyZ.instance.getServer().getScheduler().runTaskLaterAsynchronously(MyZ.instance, new Runnable() {
				@Override
				public void run() {
					set(key, field, value, false);
				}
			}, 0L);
			return;
		}

		executeQuery("UPDATE playerdata SET " + field + " = " + (value instanceof String ? "'" + value + "'" : value)
				+ " WHERE username = '" + key + "' LIMIT 1", false);
		if (!cachedValues.containsKey(key)) {
			cachedValues.put(key, new HashMap<String, Object>());
		}
		cachedValues.get(key).put(field, value);
	}

	@SuppressWarnings("unchecked")
	private <T> T get(String key, String field) {
		if (isPseudo()) { return null; }

		if (!isConnected()) {
			MyZ.log(ChatColor.RED + LocaleMessage.SQL_MODIFICATION.toString());
			return null;
		}

		T gotten = null;

		if (!cachedValues.containsKey(key) || !cachedValues.get(key).containsKey(field)) {
			if (!cachedValues.containsKey(key)) {
				cachedValues.put(key, new HashMap<String, Object>());
			}

			try {
				ResultSet rs = query("SELECT * FROM playerdata WHERE username = '" + key + "' LIMIT 1");
				Object retrieved = null;
				if (rs.next()) {
					retrieved = rs.getObject(field);
				}
				gotten = (T) retrieved;

				cachedValues.get(key).put(field, retrieved);
			} catch (Exception e) {
				MyZ.log(ChatColor.RED + LocaleMessage.SQL_FAIL.toString() + ": " + e.getMessage());
			}
		} else {
			gotten = (T) cachedValues.get(key).get(field);
		}

		return gotten;
	}

	public <T> T get(OfflinePlayer playerFor, String field) {
		return get(primaryKeyFor(playerFor), field);
	}

	public static String primaryKeyFor(OfflinePlayer playerFor) {
		return playerFor.getUniqueId().toString();
	}

	public static OfflinePlayer fromPrimaryKey(String playerKey) {
		return Bukkit.getOfflinePlayer(UUID.fromString(playerKey));
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

		executeQuery("CREATE TABLE IF NOT EXISTS playerdata (" + UserEntries.convertToMySQLQuery() + ")", false);

		String behaviour = ConfigEntries.SQL_BEHAVIOUR.<String> getValue();
		if ("MySQL->Userdata".equalsIgnoreCase(behaviour)) {
			List<String> keys = getKeys();
			int i = 0;
			if (keys != null && keys.size() > 0) {
				MyZ.log(ChatColor.RED + "Pushing data from MySQL to Userdata. Expect lag.");
				MyZ.log(ChatColor.RED + "" + i + "/" + keys.size() + " completed.");
				for (String key : keys) {
					User user = User.forPrimaryKey(key);
					for (String subkey : values) {
						DataWrapper.set(user, subkey.replaceAll("_", "\\.").replaceAll("in\\.game", "in_game"), get(key, subkey));
					}
					i++;
					if (i <= keys.size()) {
						MyZ.log(ChatColor.RED + "" + i + "/" + keys.size() + " completed.");
					}
				}
			}
		} else if ("Userdata->MySQL".equalsIgnoreCase(behaviour)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					File userdata = new File(MyZ.instance.getDataFolder().getAbsolutePath() + File.separator + "userdata");
					String[] keys = userdata.list();
					int i = 0;
					if (keys != null && keys.length > 0) {
						MyZ.log(ChatColor.RED + "Pushing data from Userdata to MySQL. Expect high load.");
						MyZ.log(ChatColor.RED + "" + i + "/" + keys.length + " completed.");
						for (String uuid : keys) {
							add(uuid);
							File unique = new File(userdata.getAbsolutePath() + File.separator + uuid);
							for (String file : new String[] { UFiles.STATISTICS.getFileID(), UFiles.TRACKED.getFileID(),
									UFiles.SKILLS.getFileID() }) {
								FileConfiguration yaml = YamlConfiguration.loadConfiguration(new File(unique.getAbsolutePath()
										+ File.separator + file));
								for (UserEntries entry : UserEntries.values()) {
									if (entry.getFile().getFileID().equals(file) && UserEntries.isMySQLKey(entry)) {
										set(uuid, entry.getKey().replaceAll("\\.", "_"), yaml.get(entry.getKey()), true);
									}
								}
							}
							i++;
							if (i <= keys.length) {
								MyZ.log(ChatColor.RED + "" + i + "/" + keys.length + " completed.");
							}
						}
					}
				}
			}.runTaskLaterAsynchronously(MyZ.instance, 0L);
		}
	}
}
