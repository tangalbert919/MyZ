/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.ManagerManager;
import jordan.sicherman.utilities.ManagerManager.ManagerType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class ChatPrefixManager extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		if (!willExecute(sender)) {
			MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
			return;
		}

		ManagerManager.setManager((Player) sender, true, ManagerType.CHAT_PREFIX);
		sender.sendMessage(LocaleMessage.CHAT_PREFIX_MANAGER_0.toString(sender));
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return true;
	}
}
