package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Version extends SimpleCommandExecutor {

    public void execute(CommandSender sender, String[] args, CommandHandler handler) {
        if (!this.willExecute(sender)) {
            MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
        } else {
            sender.sendMessage(ChatColor.GRAY + "This server is running " + ChatColor.GOLD + "MyZ " + MyZ.instance.getDescription().getVersion());
        }
    }

    public boolean willExecute(CommandSender sender) {
        return true;
    }
}
