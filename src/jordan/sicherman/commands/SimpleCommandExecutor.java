/**
 * 
 */
package jordan.sicherman.commands;

import org.bukkit.command.CommandSender;

/**
 * @author Jordan
 * 
 */
public abstract class SimpleCommandExecutor {

	public abstract void execute(CommandSender sender, String[] args, CommandHandler handler);

	public abstract boolean willExecute(CommandSender sender);
}
