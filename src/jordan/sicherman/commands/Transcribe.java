/**
 * 
 */
package jordan.sicherman.commands;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.Locale;
import jordan.sicherman.locales.LocaleMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Jordan
 * 
 */
public class Transcribe extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		Map<Locale, FileConfiguration> locales = Locale.getLocales();

		File folder = new File(MyZ.instance.getDataFolder() + File.separator + "locales" + File.separator + "transcriptions");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		for (Locale l : locales.keySet()) {
			try {
				PrintWriter writer = new PrintWriter(folder + File.separator + l.getCode() + ".txt", "UTF-8");
				FileConfiguration existing = locales.get(l);

				writer.println("/**\n *\n */");
				writer.println("package jordan.sicherman.locales;");
				writer.println();
				writer.println("/**\n * @author Jordan\n *\n */");
				writer.println("public class Locale" + l.name().substring(0, 1) + l.name().substring(1).toLowerCase() + " {");
				writer.println();
				writer.println("\tpublic static String getMessage(LocaleMessage message) {");
				writer.println("\t\tswitch (message) {");

				for (String key : existing.getKeys(true)) {
					LocaleMessage entry = LocaleMessage.getByKey(key);
					if (entry == null) {
						continue;
					}
					writer.println("\t\tcase " + entry.name() + ":");
					writer.println("\t\t\treturn \"" + existing.get(key) + "\";");
				}
				writer.println("\t\t}");
				writer.println("\t\treturn message.getKey();");
				writer.println("\t}");
				writer.println("}");
				writer.close();
			} catch (Exception exc) {

			}
		}

		sender.sendMessage(ChatColor.YELLOW + "" + locales.size() + " locales transcribed.");
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return true;
	}
}
