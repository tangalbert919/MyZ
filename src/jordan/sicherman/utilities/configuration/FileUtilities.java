/**
 * 
 */
package jordan.sicherman.utilities.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import jordan.sicherman.MyZ;
import jordan.sicherman.player.User;
import jordan.sicherman.player.User.UFiles;
import jordan.sicherman.sql.SQLManager;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.MyZRank;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class FileUtilities {

	public static FileConfiguration load(OfflinePlayer playerFor, SpecificFileMember file) {
		return load(SQLManager.primaryKeyFor(playerFor), file);
	}

	public static FileConfiguration load(User userFor, SpecificFileMember file) {
		return load(userFor.primaryKeyFor(), file);
	}

	public static FileConfiguration[] load(OfflinePlayer playerFor, SpecificFileMember... files) {
		FileConfiguration[] returned = new FileConfiguration[files.length];
		for (int i = 0; i < files.length; i++) {
			returned[i] = load(SQLManager.primaryKeyFor(playerFor), files[i]);
		}
		return returned;
	}

	public static FileConfiguration load(String key, SpecificFileMember... files) {
		FileConfiguration config = null;

		for (SpecificFileMember file : files) {
			File folder = new File(MyZ.instance.getDataFolder() + File.separator + file.getPath().replace("$0", key));
			if (!folder.exists()) {
				folder.mkdirs();
			}

			File f = new File(folder.getPath() + File.separator + file.getFileID());

			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + e.getMessage());
				}
			}

			config = YamlConfiguration.loadConfiguration(f);
			loadDefaults(config, (UFiles) file, f);
		}
		return config;
	}

	private static void loadDefaults(FileConfiguration config, UFiles file, File saveTo) {
		for (UserEntries entry : UserEntries.values()) {
			if (entry.getFile() == file && !config.isSet(entry.getKey())) {
				config.set(entry.getKey(), entry.getValue(config));
			}
		}

		try {
			config.save(saveTo);
		} catch (Exception e) {
			MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + e.getMessage());
		}
	}

	public static void reload(final CommandSender reportTo, final FileMember... files) {
		MyZ.instance.getServer().getScheduler().runTaskAsynchronously(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				load(files);
				for (FileMember f : files) {
					try {
						if (reportTo instanceof Player) {
							reportTo.sendMessage(ChatColor.GRAY + f.getFileID() + " reloaded.");
						} else {
							MyZ.log(ChatColor.GRAY + f.getFileID() + " reloaded.");
						}
					} catch (Exception exc) {

					}
				}
				try {
					if (reportTo instanceof Player) {
						reportTo.sendMessage(ChatColor.GRAY + "" + files.length + " files were reloaded.");
					} else {
						MyZ.log(ChatColor.GRAY + "" + files.length + " files were reloaded.");
					}
				} catch (Exception exc) {
				}
				ChestType.load();
				MyZ.sql.disconnect();
				MyZ.instance.registerSQL();
				MyZRank.load();
			}
		});
	}

	public static void reloadUserdata(final CommandSender reportTo) {
		MyZ.instance.getServer().getScheduler().runTaskAsynchronously(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				File folder = new File(MyZ.instance.getDataFolder() + File.separator + "userdata");
				File[] files = folder.listFiles();
				for (File in : files) {
					for (SpecificFileMember fm : UFiles.values()) {
						load(in.getName(), fm);
						try {
							reportTo.sendMessage(ChatColor.GRAY + "" + fm.getFileID() + " reloaded.");
						} catch (Exception exc) {
						}
					}
				}
				try {
					reportTo.sendMessage(ChatColor.GRAY + "" + files.length + " users (" + files.length * UFiles.values().length
							+ " files) reloaded.");
				} catch (Exception exc) {
				}
			}
		});
	}

	public static void load(FileMember... files) {
		for (FileMember file : files) {
			File f = new File(MyZ.instance.getDataFolder() + File.separator + file.getPath() + file.getFileID());

			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + e.getMessage());
				}
			}

			file.setFile(YamlConfiguration.loadConfiguration(f));
		}
	}

	public static void save(User playerFor, SpecificFileMember file) {
		if (playerFor.getFile((UFiles) file) == null) { return; }

		File folder = new File(MyZ.instance.getDataFolder() + File.separator + file.getPath().replace("$0", playerFor.primaryKeyFor()));
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File f = new File(folder.getPath() + File.separator + file.getFileID());

		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + e.getMessage());
			}
		}

		try {
			playerFor.getFile((UFiles) file).save(f);
		} catch (IOException e) {
			MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + e.getMessage());
		}
	}

	public static void save(FileMember... files) {
		for (FileMember file : files) {
			if (!file.isLoaded()) {
				continue;
			}

			File f = new File(MyZ.instance.getDataFolder() + File.separator + file.getPath() + file.getFileID());

			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + e.getMessage());
				}
			}

			try {
				file.getFile().save(f);
			} catch (IOException e) {
				MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + e.getMessage());
			}
		}
	}

	public static void set(String key, Object value, User userFor, SpecificFileMember file, boolean save) {
		if (userFor.getFile((UFiles) file) == null) {
			load(userFor, file);
		}

		userFor.getFile((UFiles) file).set(key, value);

		if (save) {
			save(userFor, file);
		}
	}

	public static void set(String key, Object value, FileMember file, boolean save) {
		if (!file.isLoaded()) {
			load(file);
		}

		file.getFile().set(key, value);
		if (save) {
			save(file);
		}
	}

	public static File getSchematicFile(String path) {
		File folder = new File(MyZ.instance.getDataFolder().getAbsolutePath() + File.separator + "schematics");

		if (!folder.exists()) {
			folder.mkdirs();
		}

		File file;
		if (path.contains("$0")) {
			Random random = new Random();
			File internalFolder = new File(folder.getAbsolutePath() + File.separator + path.substring(0, path.lastIndexOf(File.separator)));
			String[] files = internalFolder.list();
			file = new File(internalFolder.getAbsolutePath() + File.separator + files[random.nextInt(files.length)]);
		} else {
			file = new File(folder.getAbsolutePath() + File.separator + path + ".schematic");
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				MyZ.log(ChatColor.RED + "Unable to save new schematic file: " + e.getMessage());
			}
		}

		return file;
	}
}
