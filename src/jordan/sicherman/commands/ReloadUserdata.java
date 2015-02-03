/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.utilities.configuration.FileUtilities;

import org.bukkit.command.CommandSender;

/**
 * @author Jordan
 * 
 */
public class ReloadUserdata extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		FileUtilities.reloadUserdata(sender);
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return true;
	}
}
