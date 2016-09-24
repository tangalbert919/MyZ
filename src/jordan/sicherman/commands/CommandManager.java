package jordan.sicherman.commands;

import java.util.ArrayList;
import java.util.List;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandManager implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandHandler[] acommandhandler;
        int i;
        int j;
        CommandHandler h;

        if (args.length == 0) {
            acommandhandler = CommandHandler.values();
            i = acommandhandler.length;

            for (j = 0; j < i; ++j) {
                h = acommandhandler[j];
                if (h.canExecute(sender) && h.getExecutor().willExecute(sender)) {
                    sender.sendMessage(h.toString());
                }
            }

            return true;
        } else {
            acommandhandler = CommandHandler.values();
            i = acommandhandler.length;

            for (j = 0; j < i; ++j) {
                h = acommandhandler[j];
                if (h.execute(sender, args)) {
                    return true;
                }
            }

            acommandhandler = CommandHandler.values();
            i = acommandhandler.length;

            for (j = 0; j < i; ++j) {
                h = acommandhandler[j];
                if (h.canExecute(sender)) {
                    sender.sendMessage(h.toString());
                }
            }

            return true;
        }
    }

    public List onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList result = new ArrayList();
        CommandHandler[] acommandhandler = CommandHandler.values();
        int i = acommandhandler.length;

        for (int j = 0; j < i; ++j) {
            CommandHandler h = acommandhandler[j];

            try {
                String exc = h.getArgs()[args.length - 1];

                if (!result.contains(exc)) {
                    if (args.length - 1 > 0) {
                        if (args[args.length - 2].equalsIgnoreCase(h.getArgs()[args.length - 2]) || args[args.length - 2].startsWith("$")) {
                            int k;
                            int l;

                            if ("$item".equals(exc)) {
                                ItemTag[] aitemtag = ItemTag.values();

                                k = aitemtag.length;

                                for (l = 0; l < k; ++l) {
                                    ItemTag state = aitemtag[l];

                                    result.add(state.toString().toLowerCase());
                                }
                            } else if ("$state".equals(exc)) {
                                EquipmentState[] aequipmentstate = EquipmentState.values();

                                k = aequipmentstate.length;

                                for (l = 0; l < k; ++l) {
                                    EquipmentState equipmentstate = aequipmentstate[l];

                                    result.add(equipmentstate.toString().toLowerCase());
                                }
                            } else {
                                result.add(exc);
                            }
                        }
                    } else {
                        result.add(exc);
                    }
                }
            } catch (Exception exception) {
                ;
            }
        }

        return result;
    }
}
