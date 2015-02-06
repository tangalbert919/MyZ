/**
 * 
 */
package jordan.sicherman.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * @author Jordan
 * 
 */
public class CommandManager implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			for (CommandHandler h : CommandHandler.values()) {
				if (h.canExecute(sender) && h.getExecutor().willExecute(sender)) {
					sender.sendMessage(h.toString());
				}
			}
			return true;
		}

		for (CommandHandler h : CommandHandler.values()) {
			if (h.execute(sender, args)) { return true; }
		}

		for (CommandHandler h : CommandHandler.values()) {
			if (h.canExecute(sender)) {
				sender.sendMessage(h.toString());
			}
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> result = new ArrayList<String>();
		for (CommandHandler h : CommandHandler.values()) {
			try {
				String key = h.getArgs()[args.length - 1];
				if (!result.contains(key)) {
					if (args.length - 1 > 0) {
						if (args[args.length - 2].equalsIgnoreCase(h.getArgs()[args.length - 2])) {
							result.add(key);
						}
					} else {
						result.add(key);
					}
				}
			} catch (Exception exc) {
			}
		}

		return result;
	}
}
