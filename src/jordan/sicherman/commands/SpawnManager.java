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
public class SpawnManager extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		if (!willExecute(sender)) {
			MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
			return;
		}

		ManagerManager.setManager((Player) sender, true, ManagerType.SPAWN);
		sender.sendMessage(LocaleMessage.MANAGING_SPAWNS.toString(sender));
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return sender instanceof Player;
	}
}
