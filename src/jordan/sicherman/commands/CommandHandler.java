/**
 * 
 */
package jordan.sicherman.commands;

import jordan.sicherman.utilities.configuration.ConfigEntries;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Jordan
 * 
 */
public enum CommandHandler {

	MANAGE_SPAWNS(new String[] { "manage", "spawns" }, CommandPermission.SPAWN_MANAGER, "Manage spawnpoints", "manage spawns",
			new SpawnManager(), true), CONFIG_RELOAD(new String[] { "reload", "config" }, CommandPermission.CONFIG_MANAGER,
			"Reload the configurations", "reload config", new ReloadConfig(), true), SPAWN(new String[] { "spawn" },
			CommandPermission.PLAY, "Spawn in the world", "spawn", new Spawn(), true), MANAGE_ENGINEER(
			new String[] { "manage", "engineer" }, CommandPermission.ENGINEER_MANAGER, "Manage engineering recipes", "manage engineer",
			new EngineerManager(), ConfigEntries.USE_ENHANCED_ANVILS.<Boolean> getValue()), RELOAD_USERDATA(new String[] { "reload",
			"userdata" }, CommandPermission.CONFIG_MANAGER, "Reload userdata files", "reload userdata", new ReloadUserdata(), true), TRANSCRIBE(
			new String[] { "transcribe" }, CommandPermission.CONFIG_MANAGER, "Flush locale YAMLs to a MyZ-readable format", "transcribe",
			new Transcribe(), true), MANAGE_CHESTS(new String[] { "manage", "chests" }, CommandPermission.CHEST_MANAGER, "Manage chests",
			"manage chests", new ChestManager(), ConfigEntries.CHESTS.<Boolean> getValue());

	private final String[] args;
	private final CommandPermission perm;
	private final boolean isEnabled;
	private final String desc, usage;
	private final SimpleCommandExecutor executor;

	private CommandHandler(String[] args, CommandPermission perm, String desc, String usage, SimpleCommandExecutor executor,
			boolean isEnabled) {
		this.args = args;
		this.perm = perm;
		this.isEnabled = isEnabled;
		this.desc = desc;
		this.usage = usage;
		this.executor = executor;
	}

	public String[] getArgs() {
		return args;
	}

	public String getPermissionNode() {
		return perm.getNode();
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public String getDescription() {
		return desc;
	}

	public String getUsage() {
		return usage;
	}

	public SimpleCommandExecutor getExecutor() {
		return executor;
	}

	private boolean matches(String[] args) {
		if (args.length != getArgs().length) { return false; }

		for (int i = 0; i < args.length; i++) {
			if (!args[i].equalsIgnoreCase(getArgs()[i])) { return false; }
		}

		return true;
	}

	public boolean execute(CommandSender sender, String[] args) {
		if (canExecute(sender) && matches(args)) {
			getExecutor().execute(sender, args, this);
			return true;
		}
		return false;
	}

	public boolean canExecute(CommandSender sender) {
		return isEnabled() && sender.hasPermission(getPermissionNode());
	}

	@Override
	public String toString() {
		return ChatColor.BLUE + "/MyZ " + getUsage() + ": " + ChatColor.WHITE + getDescription();
	}

	public static enum CommandPermission {
		SPAWN_MANAGER("MyZ.manager.spawns"), CONFIG_MANAGER("MyZ.manager.config"), PLAY("MyZ.play"), ENGINEER_MANAGER(
				"MyZ.manager.engineer"), CHEST_MANAGER("MyZ.manager.chests");

		private final String node;

		private CommandPermission(String node) {
			this.node = node;
		}

		public String getNode() {
			return node;
		}
	}
}
