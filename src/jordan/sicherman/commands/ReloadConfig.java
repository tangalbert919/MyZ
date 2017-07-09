package jordan.sicherman.commands;

import jordan.sicherman.utilities.configuration.Configuration;
import jordan.sicherman.utilities.configuration.FileUtilities;
import org.bukkit.command.CommandSender;

public class ReloadConfig extends SimpleCommandExecutor {

    public void execute(CommandSender sender, String[] args, CommandHandler handler) {
        FileUtilities.reload(sender, Configuration.CFiles.values());
    }

    public boolean willExecute(CommandSender sender) {
        return true;
    }
}
