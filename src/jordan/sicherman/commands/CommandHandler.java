package jordan.sicherman.commands;

import jordan.sicherman.utilities.configuration.ConfigEntries;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum CommandHandler {

    MANAGE_SPAWNS(new String[] { "manage", "spawns"}, CommandHandler.CommandPermission.SPAWN_MANAGER, "Manage spawnpoints", "manage spawns", new SpawnManager(), true), CONFIG_RELOAD(new String[] { "reload", "config"}, CommandHandler.CommandPermission.CONFIG_MANAGER, "Reload the configurations", "reload config", new ReloadConfig(), true), SPAWN(new String[] { "spawn"}, CommandHandler.CommandPermission.PLAY, "Spawn in the world", "spawn", new Spawn(), true), MANAGE_ENGINEER(new String[] { "manage", "engineer"}, CommandHandler.CommandPermission.ENGINEER_MANAGER, "Manage engineering recipes", "manage engineer", new EngineerManager(), ((Boolean) ConfigEntries.USE_ENHANCED_ANVILS.getValue()).booleanValue()), RELOAD_USERDATA(new String[] { "reload", "userdata"}, CommandHandler.CommandPermission.CONFIG_MANAGER, "Reload userdata files", "reload userdata", new ReloadUserdata(), true), TRANSCRIBE(new String[] { "transcribe"}, CommandHandler.CommandPermission.CONFIG_MANAGER, "Flush locale YAMLs to a MyZ-readable format", "transcribe", new Transcribe(), true), MANAGE_CHESTS(new String[] { "manage", "chests"}, CommandHandler.CommandPermission.CHEST_MANAGER, "Manage chests", "manage chests", new ChestManager(), ((Boolean) ConfigEntries.CHESTS.getValue()).booleanValue()), MANAGE_SPAWN_KIT(new String[] { "manage", "spawn", "kit"}, CommandHandler.CommandPermission.SPAWN_KIT_MANAGER, "Manage spawn kits", "manage spawn kit", new SpawnKitManager(), true), RESPAWN_CHESTS(new String[] { "respawn", "chests"}, CommandHandler.CommandPermission.CHEST_MANAGER, "Respawn MyZ chests", "respawn chests", new RespawnChests(), ((Boolean) ConfigEntries.CHESTS.getValue()).booleanValue()), VERSION(new String[] { "version"}, CommandHandler.CommandPermission.OP, "Check the version of MyZ on your server", "version", new Version(), true), MYZ_ITEM(new String[] { "item", "get", "$item"}, CommandHandler.CommandPermission.CHEST_MANAGER, "Get a MyZ item", "item <TAB>", new MyZItem(), true), MYZ_ITEM_STATE(new String[] { "item", "apply", "$state"}, CommandHandler.CommandPermission.CHEST_MANAGER, "Apply a MyZ state to the item in your hand", "item <TAB>", new MyZItemState(), true);

    private final String[] args;
    private final CommandHandler.CommandPermission perm;
    private final boolean isEnabled;
    private final String desc;
    private final String usage;
    private final SimpleCommandExecutor executor;

    private CommandHandler(String[] args, CommandHandler.CommandPermission perm, String desc, String usage, SimpleCommandExecutor executor, boolean isEnabled) {
        this.args = args;
        this.perm = perm;
        this.isEnabled = isEnabled;
        this.desc = desc;
        this.usage = usage;
        this.executor = executor;
    }

    public String[] getArgs() {
        return this.args;
    }

    public String getPermissionNode() {
        return this.perm.getNode();
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getUsage() {
        return this.usage;
    }

    public SimpleCommandExecutor getExecutor() {
        return this.executor;
    }

    private boolean matches(String[] args) {
        if (args.length < this.getArgs().length) {
            return false;
        } else if ((this == CommandHandler.MYZ_ITEM || this == CommandHandler.MYZ_ITEM_STATE) && args[0].equalsIgnoreCase(this.getArgs()[0]) && args[1].equalsIgnoreCase(this.getArgs()[1])) {
            return true;
        } else {
            for (int i = 0; i < args.length; ++i) {
                if (!args[i].equalsIgnoreCase(this.getArgs()[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (this.canExecute(sender) && this.matches(args)) {
            this.getExecutor().execute(sender, args, this);
            return true;
        } else {
            return false;
        }
    }

    public boolean canExecute(CommandSender sender) {
        return this.isEnabled() && sender.hasPermission(this.getPermissionNode());
    }

    public String toString() {
        return ChatColor.BLUE + "/MyZ " + this.getUsage() + ": " + ChatColor.WHITE + this.getDescription();
    }

    public static enum CommandPermission {

        SPAWN_MANAGER("MyZ.manager.spawns"), CONFIG_MANAGER("MyZ.manager.config"), PLAY("MyZ.play"), ENGINEER_MANAGER("MyZ.manager.engineer"), CHEST_MANAGER("MyZ.manager.chests"), SPAWN_KIT_MANAGER("MyZ.manager.spawn_kits"), OP("MyZ.*");

        private final String node;

        private CommandPermission(String node) {
            this.node = node;
        }

        public String getNode() {
            return this.node;
        }
    }
}
