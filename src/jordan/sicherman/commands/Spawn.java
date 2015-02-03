/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class Spawn extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		if (!(sender instanceof Player)) {
			MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
			return;
		}

		Utilities.spawnInWorld((Player) sender);
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return sender instanceof Player && Utilities.canSpawn((Player) sender);
	}
}
