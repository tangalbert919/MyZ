/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Jordan
 * 
 */
public class Version extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		if (!willExecute(sender)) {
			MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
			return;
		}

		sender.sendMessage(ChatColor.GRAY + "This server is running " + ChatColor.GOLD + "MyZ "
				+ MyZ.instance.getDescription().getVersion() + (MyZ.isPremium() ? " Premium" : ""));
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return true;
	}
}
