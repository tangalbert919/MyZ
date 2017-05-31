package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.locales.LocaleMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MyZItemState extends SimpleCommandExecutor {

    public void execute(CommandSender sender, String[] args, CommandHandler handler) {
        if (!this.willExecute(sender)) {
            MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
        } else {
            Player player;

            if (sender instanceof Player) {
                player = (Player) sender;
            } else {
                if (args.length < 4) {
                    sender.sendMessage(LocaleMessage.REQUIRES_PLAYER.toString());
                    return;
                }

                player = Bukkit.getPlayer(args[3]);
                if (player == null || !player.isOnline()) {
                    sender.sendMessage(LocaleMessage.NO_USER.toString());
                    return;
                }
            }

            ItemStack hand = player.getItemOnCursor();

            if (hand != null && hand.getType() != Material.AIR) {
                EquipmentState state = EquipmentState.fromString(args[2]);

                if (state != null && state.isCompatibleWith(hand)) {
                    state.applyTo(hand);
                }

            }
        }
    }

    public boolean willExecute(CommandSender sender) {
        return true;
    }
}
