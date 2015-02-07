/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.locales.LocaleMessage;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public class MyZItemState extends SimpleCommandExecutor {

	@Override
	public void execute(CommandSender sender, String[] args, CommandHandler handler) {
		if (!willExecute(sender)) {
			MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
			return;
		}

		ItemStack hand = ((Player) sender).getItemInHand();
		if (hand == null || hand.getType() == Material.AIR) { return; }

		EquipmentState state = EquipmentState.fromString(args[2]);
		if (state != null && state.isCompatibleWith(hand)) {
			state.applyTo(hand);
		}
	}

	@Override
	public boolean willExecute(CommandSender sender) {
		return sender instanceof Player;
	}
}
