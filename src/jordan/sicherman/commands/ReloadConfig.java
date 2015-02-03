/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.utilities.configuration.Configuration;
import jordan.sicherman.utilities.configuration.FileUtilities;

import org.bukkit.command.CommandSender;

/**
 * @author Jordan
 * 
 */
public class ReloadConfig extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		FileUtilities.reload(sender, Configuration.CFiles.values());
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return true;
	}
}
