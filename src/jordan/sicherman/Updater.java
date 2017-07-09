package jordan.sicherman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Updater {

    private static final String TITLE_VALUE = "name";
    private static final String LINK_VALUE = "downloadUrl";
    private static final String TYPE_VALUE = "releaseType";
    private static final String VERSION_VALUE = "gameVersion";
    private static final String QUERY = "/servermods/files?projectIds=";
    private static final String HOST = "https://api.curseforge.com";
    private static final String USER_AGENT = "Updater (by Gravity)";
    private static final String[] NO_UPDATE_TAG = new String[] { "-DEV", "-SNAPSHOT"};
    private static final int BYTE_SIZE = 1024;
    private final Plugin plugin;
    private final Updater.UpdateType type;
    private final boolean announce;
    private final File file;
    private final File updateFolder;
    private final Updater.UpdateCallback callback;
    private int id;
    private final String apiKey;
    private String versionName;
    private String versionLink;
    private String versionType;
    private String versionGameVersion;
    private URL url;
    private Thread thread;
    private Updater.UpdateResult result;

    public Updater(Plugin plugin, int id, File file, Updater.UpdateType type, boolean announce) {
        this(plugin, id, file, type, (Updater.UpdateCallback) null, announce);
    }

    public Updater(Plugin plugin, int id, File file, Updater.UpdateType type, Updater.UpdateCallback callback) {
        this(plugin, id, file, type, callback, false);
    }

    public Updater(Plugin plugin, int id, File file, Updater.UpdateType type, Updater.UpdateCallback callback, boolean announce) {
        this.id = -1;
        this.apiKey = null;
        this.result = Updater.UpdateResult.SUCCESS;
        this.plugin = plugin;
        this.type = type;
        this.announce = announce;
        this.file = file;
        this.id = id;
        this.updateFolder = this.plugin.getServer().getUpdateFolderFile();
        this.callback = callback;

        try {
            this.url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + this.id);
        } catch (MalformedURLException malformedurlexception) {
            this.plugin.getLogger().log(Level.SEVERE, "The project ID provided for updating, " + this.id + " is invalid.", malformedurlexception);
            this.result = Updater.UpdateResult.FAIL_BADID;
        }

        if (this.result != Updater.UpdateResult.FAIL_BADID) {
            this.thread = new Thread(new Updater.UpdateRunnable(null));
            this.thread.start();
        } else {
            this.runUpdater();
        }

    }

    public Updater.UpdateResult getResult() {
        this.waitForThread();
        return this.result;
    }

    public Updater.ReleaseType getLatestType() {
        this.waitForThread();
        if (this.versionType != null) {
            Updater.ReleaseType[] aupdater_releasetype = Updater.ReleaseType.values();
            int i = aupdater_releasetype.length;

            for (int j = 0; j < i; ++j) {
                Updater.ReleaseType type = aupdater_releasetype[j];

                if (this.versionType.equalsIgnoreCase(type.name())) {
                    return type;
                }
            }
        }

        return null;
    }

    public String getLatestGameVersion() {
        this.waitForThread();
        return this.versionGameVersion;
    }

    public String getLatestName() {
        this.waitForThread();
        return this.versionName;
    }

    public String getLatestFileLink() {
        this.waitForThread();
        return this.versionLink;
    }

    private void waitForThread() {
        if (this.thread != null && this.thread.isAlive()) {
            try {
                this.thread.join();
            } catch (InterruptedException interruptedexception) {
                this.plugin.getLogger().log(Level.SEVERE, (String) null, interruptedexception);
            }
        }

    }

    private void saveFile(String file) {
        File folder = this.updateFolder;

        this.deleteOldFiles();
        if (!folder.exists()) {
            this.fileIOOrError(folder, folder.mkdir(), true);
        }

        this.downloadFile();
        File dFile = new File(folder.getAbsolutePath(), file);

        if (dFile.getName().endsWith(".zip")) {
            this.unzip(dFile.getAbsolutePath());
        }

        if (this.announce) {
            this.plugin.getLogger().info("Finished updating.");
        }

    }

    private void downloadFile() {
        BufferedInputStream in = null;
        FileOutputStream fout = null;

        try {
            URL ex = new URL(this.versionLink);
            int fileLength = ex.openConnection().getContentLength();

            in = new BufferedInputStream(ex.openStream());
            fout = new FileOutputStream(new File(this.updateFolder, this.file.getName()));
            byte[] data = new byte[1024];

            if (this.announce) {
                this.plugin.getLogger().info("About to download a new update: " + this.versionName);
            }

            long downloaded = 0L;

            int count;

            while ((count = in.read(data, 0, 1024)) != -1) {
                downloaded += (long) count;
                fout.write(data, 0, count);
                int percent = (int) (downloaded * 100L / (long) fileLength);

                if (this.announce && percent % 10 == 0) {
                    this.plugin.getLogger().info("Downloading update: " + percent + "% of " + fileLength + " bytes.");
                }
            }
        } catch (Exception exception) {
            this.plugin.getLogger().log(Level.WARNING, "The auto-updater tried to download a new update, but was unsuccessful.", exception);
            this.result = Updater.UpdateResult.FAIL_DOWNLOAD;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioexception) {
                this.plugin.getLogger().log(Level.SEVERE, (String) null, ioexception);
            }

            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException ioexception1) {
                this.plugin.getLogger().log(Level.SEVERE, (String) null, ioexception1);
            }

        }

    }

    private void deleteOldFiles() {
        File[] list = this.listFilesOrError(this.updateFolder);
        File[] afile = list;
        int i = list.length;

        for (int j = 0; j < i; ++j) {
            File xFile = afile[j];

            if (xFile.getName().endsWith(".zip")) {
                this.fileIOOrError(xFile, xFile.mkdir(), true);
            }
        }

    }

    private void unzip(String file) {
        File fSourceZip = new File(file);

        try {
            String e = file.substring(0, file.length() - 4);
            ZipFile zipFile = new ZipFile(fSourceZip);
            Enumeration e1 = zipFile.entries();

            while (e1.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) e1.nextElement();
                File destinationFilePath = new File(e, entry.getName());

                this.fileIOOrError(destinationFilePath.getParentFile(), destinationFilePath.getParentFile().mkdirs(), true);
                if (!entry.isDirectory()) {
                    BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                    byte[] buffer = new byte[1024];
                    FileOutputStream fos = new FileOutputStream(destinationFilePath);
                    BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);

                    int b;

                    while ((b = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, b);
                    }

                    bos.flush();
                    bos.close();
                    bis.close();
                    String name = destinationFilePath.getName();

                    if (name.endsWith(".jar") && this.pluginExists(name)) {
                        File output = new File(this.updateFolder, name);

                        this.fileIOOrError(output, destinationFilePath.renameTo(output), true);
                    }
                }
            }

            zipFile.close();
            this.moveNewZipFiles(e);
        } catch (IOException ioexception) {
            this.plugin.getLogger().log(Level.SEVERE, "The auto-updater tried to unzip a new update file, but was unsuccessful.", ioexception);
            this.result = Updater.UpdateResult.FAIL_DOWNLOAD;
        } finally {
            this.fileIOOrError(fSourceZip, fSourceZip.delete(), false);
        }

    }

    private void moveNewZipFiles(String zipPath) {
        File[] list = this.listFilesOrError(new File(zipPath));
        File[] zip = list;
        int i = list.length;

        for (int j = 0; j < i; ++j) {
            File dFile = zip[j];

            if (dFile.isDirectory() && this.pluginExists(dFile.getName())) {
                File oFile = new File(this.plugin.getDataFolder().getParent(), dFile.getName());
                File[] dList = this.listFilesOrError(dFile);
                File[] oList = this.listFilesOrError(oFile);
                File[] afile = dList;
                int k = dList.length;
                int l = 0;

                while (l < k) {
                    File cFile = afile[l];
                    boolean found = false;
                    File[] output = oList;
                    int i1 = oList.length;
                    int j1 = 0;

                    while (true) {
                        if (j1 < i1) {
                            File xFile = output[j1];

                            if (!xFile.getName().equals(cFile.getName())) {
                                ++j1;
                                continue;
                            }

                            found = true;
                        }

                        if (!found) {
                            File file = new File(oFile, cFile.getName());

                            this.fileIOOrError(file, cFile.renameTo(file), true);
                        } else {
                            this.fileIOOrError(cFile, cFile.delete(), false);
                        }

                        ++l;
                        break;
                    }
                }
            }

            this.fileIOOrError(dFile, dFile.delete(), false);
        }

        File file1 = new File(zipPath);

        this.fileIOOrError(file1, file1.delete(), false);
    }

    private boolean pluginExists(String name) {
        File[] plugins = this.listFilesOrError(new File("plugins"));
        File[] afile = plugins;
        int i = plugins.length;

        for (int j = 0; j < i; ++j) {
            File file = afile[j];

            if (file.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private boolean versionCheck() {
        String title = this.versionName;

        if (this.type != Updater.UpdateType.NO_VERSION_CHECK) {
            String localVersion = this.plugin.getDescription().getVersion();
            String authorInfo;

            if (title.split(" ").length != 2) {
                authorInfo = this.plugin.getDescription().getAuthors().isEmpty() ? "" : " (" + (String) this.plugin.getDescription().getAuthors().get(0) + ")";
                this.plugin.getLogger().warning("The author of this plugin" + authorInfo + " has misconfigured their Auto Update system");
                this.plugin.getLogger().warning("File versions should follow the format \'PluginName vVERSION\'");
                this.plugin.getLogger().warning("Please notify the author of this error.");
                this.result = Updater.UpdateResult.FAIL_NOVERSION;
                return false;
            }

            authorInfo = title.split(" ")[1];
            if (this.hasTag(localVersion) || !this.shouldUpdate(localVersion, authorInfo)) {
                this.result = Updater.UpdateResult.NO_UPDATE;
                return false;
            }
        }

        return true;
    }

    public boolean shouldUpdate(String localVersion, String remoteVersion) {
        return !localVersion.equalsIgnoreCase(remoteVersion) && remoteVersion.startsWith("4");
    }

    private boolean hasTag(String version) {
        String[] astring = Updater.NO_UPDATE_TAG;
        int i = astring.length;

        for (int j = 0; j < i; ++j) {
            String string = astring[j];

            if (version.contains(string)) {
                return true;
            }
        }

        return false;
    }

    private boolean read() {
        try {
            URLConnection e = this.url.openConnection();

            e.setConnectTimeout(5000);
            if (this.apiKey != null) {
                e.addRequestProperty("X-API-Key", this.apiKey);
            }

            e.addRequestProperty("User-Agent", "Updater (by Gravity)");
            e.setDoOutput(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(e.getInputStream()));
            String response = reader.readLine();
            JSONArray array = (JSONArray) JSONValue.parse(response);

            if (array.isEmpty()) {
                this.plugin.getLogger().warning("The updater could not find any files for the project id " + this.id);
                this.result = Updater.UpdateResult.FAIL_BADID;
                return false;
            } else {
                JSONObject latestUpdate = (JSONObject) array.get(array.size() - 1);

                this.versionName = (String) latestUpdate.get("name");
                this.versionLink = (String) latestUpdate.get("downloadUrl");
                this.versionType = (String) latestUpdate.get("releaseType");
                this.versionGameVersion = (String) latestUpdate.get("gameVersion");
                return true;
            }
        } catch (IOException ioexception) {
            if (ioexception.getMessage().contains("HTTP response code: 403")) {
                this.plugin.getLogger().severe("dev.bukkit.org rejected the API key provided in plugins/Updater/config.yml");
                this.plugin.getLogger().severe("Please double-check your configuration to ensure it is correct.");
                this.result = Updater.UpdateResult.FAIL_APIKEY;
            } else {
                this.plugin.getLogger().severe("The updater could not contact dev.bukkit.org for updating.");
                this.plugin.getLogger().severe("If you have not recently modified your configuration and this is the first time you are seeing this message, the site may be experiencing temporary downtime.");
                this.result = Updater.UpdateResult.FAIL_DBO;
            }

            this.plugin.getLogger().log(Level.SEVERE, (String) null, ioexception);
            return false;
        }
    }

    private void fileIOOrError(File file, boolean result, boolean create) {
        if (!result) {
            this.plugin.getLogger().severe("The updater could not " + (create ? "create" : "delete") + " file at: " + file.getAbsolutePath());
        }

    }

    private File[] listFilesOrError(File folder) {
        File[] contents = folder.listFiles();

        if (contents == null) {
            this.plugin.getLogger().severe("The updater could not access files at: " + this.updateFolder.getAbsolutePath());
            return new File[0];
        } else {
            return contents;
        }
    }

    private void runUpdater() {
        if (this.url != null && this.read() && this.versionCheck()) {
            if (this.versionLink != null && this.type != Updater.UpdateType.NO_DOWNLOAD) {
                String name = this.file.getName();

                if (this.versionLink.endsWith(".zip")) {
                    name = this.versionLink.substring(this.versionLink.lastIndexOf("/") + 1);
                }

                this.saveFile(name);
            } else {
                this.result = Updater.UpdateResult.UPDATE_AVAILABLE;
            }
        }

        if (this.callback != null) {
            (new BukkitRunnable() {
                public void run() {
                    Updater.this.runCallback();
                }
            }).runTask(this.plugin);
        }

    }

    private void runCallback() {
        this.callback.onFinish(this);
    }

    private class UpdateRunnable implements Runnable {

        private UpdateRunnable() {}

        public void run() {
            Updater.this.runUpdater();
        }

        UpdateRunnable(Object x1) {
            this();
        }
    }

    public interface UpdateCallback {

        void onFinish(Updater updater);
    }

    public static enum ReleaseType {

        ALPHA, BETA, RELEASE;
    }

    public static enum UpdateType {

        DEFAULT, NO_VERSION_CHECK, NO_DOWNLOAD;
    }

    public static enum UpdateResult {

        SUCCESS, NO_UPDATE, DISABLED, FAIL_DOWNLOAD, FAIL_DBO, FAIL_NOVERSION, FAIL_BADID, FAIL_APIKEY, UPDATE_AVAILABLE;
    }
}
