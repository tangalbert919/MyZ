package jordan.sicherman.commands;

import jordan.sicherman.utilities.configuration.FileUtilities;
import org.bukkit.command.CommandSender;

public class ReloadUserdata extends SimpleCommandExecutor {

    public void execute(CommandSender sender, String[] args, CommandHandler handler) {
        FileUtilities.reloadUserdata(sender);
    }

    public boolean willExecute(CommandSender sender) {
        return true;
    }
}
