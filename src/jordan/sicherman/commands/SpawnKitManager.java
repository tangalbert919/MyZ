package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.ManagerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnKitManager extends SimpleCommandExecutor {

    public void execute(CommandSender sender, String[] args, CommandHandler handler) {
        if (!this.willExecute(sender)) {
            MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
        } else {
            ManagerManager.setManager((Player) sender, true, ManagerManager.ManagerType.SPAWN_KIT);
            sender.sendMessage(LocaleMessage.MANAGING_SPAWN_KIT.toString(sender));
        }
    }

    public boolean willExecute(CommandSender sender) {
        return sender instanceof Player;
    }
}
