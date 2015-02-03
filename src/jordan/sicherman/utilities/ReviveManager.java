/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.HashSet;
import java.util.Set;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.sql.SQLManager;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class ReviveManager {

	private final Set<String> vulnerable = new HashSet<String>();
	// Faster indexing by storing a list of those in the task list. Important as
	// the index is scanned every tick (with movement).
	private final Set<String> inProgress = new HashSet<String>();
	private final Set<ReviveTimer> timers = new HashSet<ReviveTimer>();

	private static ReviveManager instance;

	public ReviveManager() {
		instance = this;
	}

	public static ReviveManager getInstance() {
		return instance;
	}

	public void reportException(Player player) {
		for (String s : inProgress) {
			if (SQLManager.primaryKeyFor(player).equals(s)) {
				for (ReviveTimer timer : timers) {
					if (player.equals(timer.clicked) || player.equals(timer.healer)) {
						timer.cancel();
					}
				}
				return;
			}
		}
	}

	public boolean isBeingRevived(Player clicked) {
		return inProgress.contains(SQLManager.primaryKeyFor(clicked));
	}

	public void begin(Player healer, Player clicked) {
		inProgress.add(SQLManager.primaryKeyFor(healer));
		inProgress.add(SQLManager.primaryKeyFor(clicked));

		healer.sendMessage(LocaleMessage.REVIVER_STARTED.filter(Utilities.getPrefixFor(clicked) + clicked.getName()).toString(healer));
		clicked.sendMessage(LocaleMessage.REVIVED_STARTED.filter(Utilities.getPrefixFor(healer) + healer.getName()).toString(healer));

		ReviveTimer timer = new ReviveTimer(healer, clicked);
		timers.add(timer);
		timer.runTaskLater(MyZ.instance, ConfigEntries.REVIVE_TIME.<Integer> getValue() * 20L);
	}

	private class ReviveTimer extends BukkitRunnable {

		private Player healer, clicked;

		public ReviveTimer(Player healer, Player clicked) {
			this.healer = healer;
			this.clicked = clicked;
		}

		@Override
		public void run() {
			timers.remove(this);
			if (healer != null && clicked != null && healer.isOnline() && clicked.isOnline() && !healer.isDead() && !clicked.isDead()) {
				inProgress.remove(SQLManager.primaryKeyFor(healer));
				inProgress.remove(SQLManager.primaryKeyFor(clicked));
				MyZ.ghostFactory.setGhost(clicked, false);
				clicked.removePotionEffect(PotionEffectType.WITHER);
				clicked.setHealth(1.0);
				clicked.setFoodLevel(4);
				DataWrapper.set(healer, UserEntries.REVIVES, DataWrapper.<Integer> get(healer, UserEntries.REVIVES) + 1);
				healer.sendMessage(LocaleMessage.REVIVER.filter(Utilities.getPrefixFor(clicked) + clicked.getName()).toString(healer));
				clicked.sendMessage(LocaleMessage.REVIVED.filter(Utilities.getPrefixFor(healer) + healer.getName()).toString(clicked));
				DataWrapper.set(clicked, UserEntries.REVIVALS, DataWrapper.<Integer> get(clicked, UserEntries.REVIVALS) + 1);
				final String key = SQLManager.primaryKeyFor(clicked);
				vulnerable.add(key);
				new BukkitRunnable() {
					@Override
					public void run() {
						vulnerable.remove(key);
					}

					@Override
					public void cancel() {
						vulnerable.remove(key);
					}
				}.runTaskLater(MyZ.instance, ConfigEntries.VULNERABLE_TIME.<Integer> getValue() * 20L);
			}
		}

		@Override
		public void cancel() {
			inProgress.remove(SQLManager.primaryKeyFor(healer));
			inProgress.remove(SQLManager.primaryKeyFor(clicked));
			if (healer.isOnline() && clicked.isOnline() && !healer.isDead() && !clicked.isDead()) {
				healer.sendMessage(LocaleMessage.REVIVER_CANCELLED.filter(Utilities.getPrefixFor(clicked) + clicked.getName()).toString(
						healer));
				clicked.sendMessage(LocaleMessage.REVIVED_CANCELLED.filter(Utilities.getPrefixFor(healer) + healer.getName()).toString(
						healer));
			}
			healer = null;
			clicked = null;
			timers.remove(this);
		}
	}

	public boolean isVulnerable(Player player) {
		return vulnerable.contains(SQLManager.primaryKeyFor(player));
	}

	public void setVulnerable(Player playerFor, boolean isVulnerable) {
		String key = SQLManager.primaryKeyFor(playerFor);
		if (isVulnerable && !vulnerable.contains(key)) {
			vulnerable.add(key);
		} else if (!isVulnerable && vulnerable.contains(key)) {
			vulnerable.remove(key);
		}
	}
}
