package jordan.sicherman.commands;

import org.bukkit.command.CommandSender;

public abstract class SimpleCommandExecutor {

    public abstract void execute(CommandSender commandsender, String[] astring, CommandHandler commandhandler);

    public abstract boolean willExecute(CommandSender commandsender);
}
