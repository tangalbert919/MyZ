/**
 * 
 */
package jordan.sicherman.listeners.player;

import java.util.List;

import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.DeathCause;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * @author Jordan
 * 
 */
public class Death implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onDeath(PlayerDeathEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		final Player player = e.getEntity();

		e.setDroppedExp(0);
		try {
			e.setKeepInventory(false);
		} catch (Exception exc) {

		}
		e.setDeathMessage(null);
		player.setFireTicks(0);

		treatDeathMessage(player);

		realDeath(player, false);
	}

	public static void realDeath(Player playerFor, boolean onLogout) {
		DataWrapper.set(playerFor, UserEntries.DEATHS, DataWrapper.<Integer> get(playerFor, UserEntries.DEATHS) + 1);

		Utilities.setBleeding(playerFor, false, true);
		Utilities.setPoisoned(playerFor, false, true);

		if (onLogout) {
			Utilities.respawn(playerFor, true);
		}

		// TODO zero stats of unranked players
	}

	private static void treatDeathMessage(Player player) {
		if (!ConfigEntries.DEATH_MESSAGES.<Boolean> getValue()) { return; }

		List<Player> to = player.getWorld().getPlayers();
		to.remove(player);

		switch (player.getLastDamageCause().getCause()) {
		case BLOCK_EXPLOSION:
		case ENTITY_EXPLOSION:
			DeathCause.EXPLOSION.compileAndSendAsJSON(player, to);
			break;
		case CONTACT:
			DeathCause.CACTUS.compileAndSendAsJSON(player, to);
			break;
		case DROWNING:
			DeathCause.DROWNING.compileAndSendAsJSON(player, to);
			break;
		case ENTITY_ATTACK:
			Entity ent = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
			switch (ent.getType()) {
			// TODO maybe pass entities as parameter to determine special stats?
			case GIANT:
				DeathCause.GIANT.compileAndSendAsJSON(player, to);
				break;
			case PIG_ZOMBIE:
				DeathCause.PIGMAN.compileAndSendAsJSON(player, to);
				break;
			case PLAYER:
				DeathCause.PLAYER.compileAndSendAsJSON(player, (Player) ent, to);
				break;
			case ZOMBIE:
				DeathCause.ZOMBIE.compileAndSendAsJSON(player, to);
				break;
			default:
				DeathCause.OTHER.compileAndSendAsJSON(player, to);
				break;
			}
			break;
		case FALL:
			DeathCause.FALL.compileAndSendAsJSON(player, to);
			break;
		case FALLING_BLOCK:
		case SUFFOCATION:
			DeathCause.SUFFOCATION.compileAndSendAsJSON(player, to);
			break;
		case FIRE:
		case FIRE_TICK:
			DeathCause.FIRE.compileAndSendAsJSON(player, to);
			break;
		case LAVA:
			DeathCause.LAVA.compileAndSendAsJSON(player, to);
			break;
		case MAGIC:
			DeathCause.MAGIC.compileAndSendAsJSON(player, to);
			break;
		case POISON:
			DeathCause.POISON.compileAndSendAsJSON(player, to);
			break;
		case PROJECTILE:
			// TODO maybe use shooter instead and treat as player (unless
			// dispenser, etc).
			DeathCause.ARROW.compileAndSendAsJSON(player, to);
			break;
		case STARVATION:
			DeathCause.STARVE.compileAndSendAsJSON(player, to);
			break;
		case VOID:
			DeathCause.VOID.compileAndSendAsJSON(player, to);
			break;
		default:
			DeathCause.OTHER.compileAndSendAsJSON(player, to);
			break;
		}
	}
}
