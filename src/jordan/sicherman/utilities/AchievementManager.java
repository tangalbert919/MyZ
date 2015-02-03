/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.ArrayList;
import java.util.List;

import jordan.sicherman.MyZ;
import jordan.sicherman.api.PlayerGetAchievementEvent;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.player.User;
import jordan.sicherman.player.User.UFiles;
import jordan.sicherman.utilities.configuration.Configuration.CFiles;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class AchievementManager {

	private final List<MyZAchievement> achievements = new ArrayList<MyZAchievement>();

	private static AchievementManager instance;

	public AchievementManager() {
		instance = this;
	}

	public static AchievementManager getInstance() {
		return instance;
	}

	public void reload() {
		if (!CFiles.ACHIEVEMENTS.isLoaded()) { return; }
		achievements.clear();

		for (String key : CFiles.ACHIEVEMENTS.getFile().getKeys(false)) {
			achievements.add(new MyZAchievement(CFiles.ACHIEVEMENTS.getFile().getConfigurationSection(key)));
		}
	}

	public List<MyZAchievement> getAchievements() {
		return achievements;
	}

	public List<MyZAchievement> getAchievements(OfflinePlayer playerFor) {
		List<MyZAchievement> list = new ArrayList<MyZAchievement>();

		for (String key : User.forPlayer(playerFor).getFile(UFiles.ACHIEVEMENTS).getKeys(false)) {
			list.add(new MyZAchievement(CFiles.ACHIEVEMENTS.getFile().getConfigurationSection(key)));
		}

		return list;
	}

	public void refresh(OfflinePlayer playerFor, boolean notifyOfNew) {
		for (MyZAchievement achievement : getAchievements(playerFor)) {
			if (!achievement.doesQualify(playerFor)) {
				achievement.set(playerFor, false);
			}
		}

		List<MyZAchievement> owned = getAchievements(playerFor);

		for (MyZAchievement achievement : getAchievements()) {
			if (achievement.doesQualify(playerFor)) {
				if (!owned.contains(achievement)) {
					achievement.set(playerFor, true);

					if (playerFor.isOnline()) {
						MyZ.instance.getServer().getPluginManager()
								.callEvent(new PlayerGetAchievementEvent((Player) playerFor, achievement));
						if (notifyOfNew) {
							((Player) playerFor).sendMessage(LocaleMessage.ACHIEVEMENT_EARNED.filter(achievement.getName(),
									achievement.getDescription()).toString((Player) playerFor));
						}
					}
				}
			}
		}
	}
}
