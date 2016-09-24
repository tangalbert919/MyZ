package jordan.sicherman.utilities.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import jordan.sicherman.MyZ;
import jordan.sicherman.player.User;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.MyZRank;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileUtilities {

    public static FileConfiguration load(OfflinePlayer playerFor, SpecificFileMember file) {
        return load(playerFor.getUniqueId().toString(), new SpecificFileMember[] { file});
    }

    public static FileConfiguration load(User userFor, SpecificFileMember file) {
        return load(userFor.primaryKeyFor(), new SpecificFileMember[] { file});
    }

    public static FileConfiguration[] load(OfflinePlayer playerFor, SpecificFileMember... files) {
        FileConfiguration[] returned = new FileConfiguration[files.length];

        for (int i = 0; i < files.length; ++i) {
            returned[i] = load(playerFor.getUniqueId().toString(), new SpecificFileMember[] { files[i]});
        }

        return returned;
    }

    public static FileConfiguration load(String key, SpecificFileMember... files) {
        YamlConfiguration config = null;
        SpecificFileMember[] aspecificfilemember = files;
        int i = files.length;

        for (int j = 0; j < i; ++j) {
            SpecificFileMember file = aspecificfilemember[j];
            File folder = new File(MyZ.instance.getDataFolder() + File.separator + file.getPath().replace("$0", key));

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File f = new File(folder.getPath() + File.separator + file.getFileID());

            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException ioexception) {
                    MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + ioexception.getMessage());
                }
            }

            config = YamlConfiguration.loadConfiguration(f);
            loadDefaults(config, (User.UFiles) file, f);
        }

        return config;
    }

    private static void loadDefaults(FileConfiguration config, User.UFiles file, File saveTo) {
        UserEntries[] e = UserEntries.values();
        int i = e.length;

        for (int j = 0; j < i; ++j) {
            UserEntries entry = e[j];

            if (entry.getFile() == file && !config.isSet(entry.getKey())) {
                config.set(entry.getKey(), entry.getValue(config));
            }
        }

        try {
            config.save(saveTo);
        } catch (Exception exception) {
            MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + exception.getMessage());
        }

    }

    public static void reload(final CommandSender reportTo, final FileMember... files) {
        MyZ.instance.getServer().getScheduler().runTaskAsynchronously(MyZ.instance, new Runnable() {
            public void run() {
                FileUtilities.load(files);
                FileMember[] exc = files;
                int i = exc.length;

                for (int j = 0; j < i; ++j) {
                    FileMember f = exc[j];

                    try {
                        if (reportTo instanceof Player) {
                            reportTo.sendMessage(ChatColor.GRAY + f.getFileID() + " reloaded.");
                        } else {
                            MyZ.log(ChatColor.GRAY + f.getFileID() + " reloaded.");
                        }
                    } catch (Exception exception) {
                        ;
                    }
                }

                try {
                    if (reportTo instanceof Player) {
                        reportTo.sendMessage(ChatColor.GRAY + "" + files.length + " files were reloaded.");
                    } else {
                        MyZ.log(ChatColor.GRAY + "" + files.length + " files were reloaded.");
                    }
                } catch (Exception exception1) {
                    ;
                }

                ChestType.load();
                MyZRank.load();
            }
        });
    }

    public static void reloadUserdata(final CommandSender reportTo) {
        MyZ.instance.getServer().getScheduler().runTaskAsynchronously(MyZ.instance, new Runnable() {
            public void run() {
                File folder = new File(MyZ.instance.getDataFolder() + File.separator + "userdata");
                File[] files = folder.listFiles();
                File[] exc = files;
                int i = files.length;

                for (int j = 0; j < i; ++j) {
                    File in = exc[j];
                    User.UFiles[] auser_ufiles = User.UFiles.values();
                    int k = auser_ufiles.length;

                    for (int l = 0; l < k; ++l) {
                        User.UFiles fm = auser_ufiles[l];

                        FileUtilities.load(in.getName(), new SpecificFileMember[] { fm});

                        try {
                            reportTo.sendMessage(ChatColor.GRAY + "" + fm.getFileID() + " reloaded.");
                        } catch (Exception exception) {
                            ;
                        }
                    }
                }

                try {
                    reportTo.sendMessage(ChatColor.GRAY + "" + files.length + " users (" + files.length * User.UFiles.values().length + " files) reloaded.");
                } catch (Exception exception1) {
                    ;
                }

            }
        });
    }

    public static void load(FileMember... files) {
        FileMember[] afilemember = files;
        int i = files.length;

        for (int j = 0; j < i; ++j) {
            FileMember file = afilemember[j];
            File f = new File(MyZ.instance.getDataFolder() + File.separator + file.getPath() + file.getFileID());

            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException ioexception) {
                    MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + ioexception.getMessage());
                }
            }

            file.setFile(YamlConfiguration.loadConfiguration(f));
        }

    }

    public static void save(User playerFor, SpecificFileMember file) {
        if (playerFor.getFile((User.UFiles) file) != null) {
            File folder = new File(MyZ.instance.getDataFolder() + File.separator + file.getPath().replace("$0", playerFor.primaryKeyFor()));

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File f = new File(folder.getPath() + File.separator + file.getFileID());

            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException ioexception) {
                    MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + ioexception.getMessage());
                }
            }

            try {
                playerFor.getFile((User.UFiles) file).save(f);
            } catch (IOException ioexception1) {
                MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + ioexception1.getMessage());
            }

        }
    }

    public static void save(FileMember... files) {
        FileMember[] afilemember = files;
        int i = files.length;

        for (int j = 0; j < i; ++j) {
            FileMember file = afilemember[j];

            if (file.isLoaded()) {
                File f = new File(MyZ.instance.getDataFolder() + File.separator + file.getPath() + file.getFileID());

                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (IOException ioexception) {
                        MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + ioexception.getMessage());
                    }
                }

                try {
                    file.getFile().save(f);
                } catch (IOException ioexception1) {
                    MyZ.log(ChatColor.RED + "Unable to save " + file.getFileID() + ": " + ioexception1.getMessage());
                }
            }
        }

    }

    public static void set(String key, Object value, User userFor, SpecificFileMember file, boolean save) {
        if (userFor.getFile((User.UFiles) file) == null) {
            load(userFor, file);
        }

        userFor.getFile((User.UFiles) file).set(key, value);
        if (save) {
            save(userFor, file);
        }

    }

    public static void set(String key, Object value, FileMember file, boolean save) {
        if (!file.isLoaded()) {
            load(new FileMember[] { file});
        }

        file.getFile().set(key, value);
        if (save) {
            save(new FileMember[] { file});
        }

    }

    public static File getSchematicFile(String path) {
        File folder = new File(MyZ.instance.getDataFolder().getAbsolutePath() + File.separator + "schematics");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file;

        if (path.contains("$0")) {
            Random e = new Random();
            File internalFolder = new File(folder.getAbsolutePath() + File.separator + path.substring(0, path.lastIndexOf(File.separator)));
            String[] files = internalFolder.list();

            file = new File(internalFolder.getAbsolutePath() + File.separator + files[e.nextInt(files.length)]);
        } else {
            file = new File(folder.getAbsolutePath() + File.separator + path + ".schematic");
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioexception) {
                MyZ.log(ChatColor.RED + "Unable to save new schematic file: " + ioexception.getMessage());
            }
        }

        return file;
    }
}
