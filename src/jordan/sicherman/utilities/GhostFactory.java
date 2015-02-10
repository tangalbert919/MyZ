/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * @author Comphenix
 * @author Jordan
 * 
 */
public class GhostFactory {
	/**
	 * Team of ghosts and people who can see ghosts.
	 */
	private static final String GHOST_TEAM_NAME = "MyZ Ghosts";

	// No players in the ghost factory
	private static final OfflinePlayer[] EMPTY_PLAYERS = new OfflinePlayer[0];
	private Team ghostTeam;

	// Task that must be cleaned up
	private boolean closed;

	private boolean isEnabled() {
		return ConfigEntries.BECOME_GHOST.<Boolean> getValue();
	}

	public GhostFactory() {
		// Initialize
		if (isEnabled()) {
			createGetTeam();
		}
	}

	private void createGetTeam() {
		Scoreboard board = Bukkit.getServer().getScoreboardManager().getMainScoreboard();

		ghostTeam = board.getTeam(GHOST_TEAM_NAME);

		// Create a new ghost team if needed
		if (ghostTeam == null) {
			ghostTeam = board.registerNewTeam(GHOST_TEAM_NAME);
		}
		// Thanks to Rprrr for noticing a bug here
		ghostTeam.setCanSeeFriendlyInvisibles(true);
	}

	/**
	 * Remove all existing player members and ghosts.
	 */
	public void clearMembers() {
		if (ghostTeam != null) {
			for (OfflinePlayer player : getMembers()) {
				ghostTeam.removePlayer(player);
			}
		}
	}

	/**
	 * Add the given player to this ghost manager. This ensures that it can see
	 * ghosts, and later become one.
	 * 
	 * @param player
	 *            - the player to add to the ghost manager.
	 * @param ghost
	 *            - TRUE to initialize as a ghost, FALSE otherwise.
	 */
	public void addPlayer(Player player, boolean ghost) {
		if (!isEnabled()) { return; }
		validateState();
		if (!ghostTeam.hasPlayer(player)) {
			ghostTeam.addPlayer(player);
			// Jordan change - add ghost parameter and implementation
			setGhost(player, ghost);
			/* player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15)); */
		}
	}

	/**
	 * Determine if the given player is tracked by this ghost manager and is a
	 * ghost.
	 * 
	 * @param player
	 *            - the player to test.
	 * @return TRUE if it is, FALSE otherwise.
	 */
	public boolean isGhost(Player player) {
		if (!isEnabled()) { return false; }
		return player != null && hasPlayer(player) && DataWrapper.<Boolean> get(player, UserEntries.GHOST);
	}

	/**
	 * Determine if the current player is tracked by this ghost manager, or is a
	 * ghost.
	 * 
	 * @param player
	 *            - the player to check.
	 * @return TRUE if it is, FALSE otherwise.
	 */
	public boolean hasPlayer(Player player) {
		if (!isEnabled()) { return false; }

		validateState();
		return ghostTeam.hasPlayer(player);
	}

	/**
	 * Set wheter or not a given player is a ghost.
	 * 
	 * @param player
	 *            - the player to set as a ghost.
	 * @param isGhost
	 *            - TRUE to make the given player into a ghost, FALSE otherwise.
	 */
	public void setGhost(Player player, boolean isGhost) {
		if (!isEnabled()) { return; }

		// Make sure the player is tracked by this manager
		if (!hasPlayer(player)) {
			addPlayer(player, isGhost);
		}

		DataWrapper.set(player, UserEntries.GHOST, isGhost);

		if (isGhost && !player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));
		} else if (!isGhost) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
	}

	/**
	 * Remove the given player from the manager, turning it back into the living
	 * and making it unable to see ghosts.
	 * 
	 * @param player
	 *            - the player to remove from the ghost manager.
	 */
	public void removePlayer(Player player) {
		if (!isEnabled()) { return; }

		validateState();
		if (ghostTeam.removePlayer(player)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
	}

	/**
	 * Retrieve every ghost currently tracked by this manager.
	 * 
	 * @return Every tracked ghost.
	 */
	public OfflinePlayer[] getGhosts() {
		if (!isEnabled()) { return new OfflinePlayer[0]; }

		validateState();
		Set<OfflinePlayer> players = new HashSet<OfflinePlayer>(ghostTeam.getPlayers());

		// Remove all non-ghost players
		for (Iterator<OfflinePlayer> it = players.iterator(); it.hasNext();) {
			if (!DataWrapper.<Boolean> get(it.next(), UserEntries.GHOST)) {
				it.remove();
			}
		}
		return toArray(players);
	}

	/**
	 * Retrieve every ghost and every player that can see ghosts.
	 * 
	 * @return Every ghost or every observer.
	 */
	public OfflinePlayer[] getMembers() {
		if (!isEnabled()) { return new OfflinePlayer[0]; }

		validateState();
		return toArray(ghostTeam.getPlayers());
	}

	private OfflinePlayer[] toArray(Set<OfflinePlayer> players) {
		if (players != null) {
			return players.toArray(new OfflinePlayer[0]);
		} else {
			return EMPTY_PLAYERS;
		}
	}

	public void close() {
		if (!isEnabled()) { return; }

		if (!closed) {
			ghostTeam.unregister();
			closed = true;
		}
	}

	public boolean isClosed() {
		return closed;
	}

	private void validateState() {
		if (closed) { throw new IllegalStateException("Ghost factory has closed. Cannot reuse instances."); }
	}

	public void populate(Collection<? extends Player> collection) {
		if (!isEnabled()) { return; }

		for (Player player : collection) {
			if (Utilities.inWorld(player)) {
				addPlayer(player, false);
			}
		}
	}
}
