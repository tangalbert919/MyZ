package jordan.sicherman.commands;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import jordan.sicherman.MyZ;
import jordan.sicherman.locales.Locale;
import jordan.sicherman.locales.LocaleMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class Transcribe extends SimpleCommandExecutor {

    public void execute(CommandSender sender, String[] args, CommandHandler handler) {
        Map locales = Locale.getLocales();
        File folder = new File(MyZ.instance.getDataFolder() + File.separator + "locales" + File.separator + "transcriptions");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        Iterator iterator = locales.keySet().iterator();

        while (iterator.hasNext()) {
            Locale l = (Locale) iterator.next();

            try {
                PrintWriter exc = new PrintWriter(folder + File.separator + l.getCode() + ".txt", "UTF-8");
                FileConfiguration existing = (FileConfiguration) locales.get(l);

                exc.println("/**\n *\n */");
                exc.println("package jordan.sicherman.locales;");
                exc.println();
                exc.println("/**\n * @author Jordan\n *\n */");
                exc.println("public class Locale" + l.name().substring(0, 1) + l.name().substring(1).toLowerCase() + " {");
                exc.println();
                exc.println("\tpublic static String getMessage(LocaleMessage message) {");
                exc.println("\t\tswitch (message) {");
                Iterator iterator1 = existing.getKeys(true).iterator();

                while (iterator1.hasNext()) {
                    String key = (String) iterator1.next();
                    LocaleMessage entry = LocaleMessage.getByKey(key);

                    if (entry != null) {
                        exc.println("\t\tcase " + entry.name() + ":");
                        exc.println("\t\t\treturn \"" + existing.get(key) + "\";");
                    }
                }

                exc.println("\t\t}");
                exc.println("\t\treturn message.getKey();");
                exc.println("\t}");
                exc.println("}");
                exc.close();
            } catch (Exception exception) {
                ;
            }
        }

        sender.sendMessage(ChatColor.YELLOW + "" + locales.size() + " locales transcribed.");
    }

    public boolean willExecute(CommandSender sender) {
        return true;
    }
}
