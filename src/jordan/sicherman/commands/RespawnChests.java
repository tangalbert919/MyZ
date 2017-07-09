package jordan.sicherman.commands;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.ChestType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RespawnChests extends SimpleCommandExecutor {

    public void execute(CommandSender sender, String[] args, CommandHandler handler) {
        if (!this.willExecute(sender)) {
            MyZ.log(ChatColor.RED + LocaleMessage.CANNOT_COMMAND.toString());
        } else {
            ChestType.respawnAll();
        }
    }

    public boolean willExecute(CommandSender sender) {
        return true;
    }
}
