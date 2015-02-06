/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.ChestType;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Jordan
 * 
 */
public class RespawnChests extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		if (!willExecute(sender)) {
			MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
			return;
		}

		ChestType.respawnAll();
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return true;
	}
}
