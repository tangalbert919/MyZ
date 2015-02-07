/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.LocaleMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class MyZItem extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		if (!willExecute(sender)) {
			MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
			return;
		}

		ItemTag tag = ItemTag.fromString(args[2]);
		if (tag != null) {
			((Player) sender).getInventory().addItem(ItemUtilities.getInstance().getTagItem(tag, 1));
		}
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return sender instanceof Player;
	}
}
