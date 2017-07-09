package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.LocaleMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MyZItem extends SimpleCommandExecutor {

    public void execute(CommandSender sender, String[] args, CommandHandler handler) {
        if (!this.willExecute(sender)) {
            MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
        } else {
            ItemTag tag = ItemTag.fromString(args[2]);

            if (tag != null) {
                if (sender instanceof Player) {
                    ((Player) sender).getInventory().addItem(new ItemStack[] { ItemUtilities.getInstance().getTagItem(tag, 1)});
                } else {
                    if (args.length < 4) {
                        sender.sendMessage(LocaleMessage.REQUIRES_PLAYER.toString());
                        return;
                    }

                    Player player = Bukkit.getPlayer(args[3]);
                    int amount = 1;

                    try {
                        amount = Integer.parseInt(args[4]);
                    } catch (Exception exception) {
                        ;
                    }

                    if (player != null && player.isOnline()) {
                        player.getInventory().addItem(new ItemStack[] { ItemUtilities.getInstance().getTagItem(tag, amount)});
                    } else {
                        sender.sendMessage(LocaleMessage.NO_USER.toString());
                    }
                }
            }

        }
    }

    public boolean willExecute(CommandSender sender) {
        return true;
    }
}
