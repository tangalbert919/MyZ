package jordan.sicherman.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jordan.sicherman.MyZ;
import jordan.sicherman.api.PlayerGetAchievementEvent;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.player.User;
import jordan.sicherman.utilities.configuration.Configuration;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AchievementManager {

    private final List achievements = new ArrayList();
    private static AchievementManager instance;

    public AchievementManager() {
        AchievementManager.instance = this;
    }

    public static AchievementManager getInstance() {
        return AchievementManager.instance;
    }

    public void reload() {
        if (Configuration.CFiles.ACHIEVEMENTS.isLoaded()) {
            this.achievements.clear();
            Iterator iterator = Configuration.CFiles.ACHIEVEMENTS.getFile().getKeys(false).iterator();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                this.achievements.add(new MyZAchievement(Configuration.CFiles.ACHIEVEMENTS.getFile().getConfigurationSection(key)));
            }

        }
    }

    public List getAchievements() {
        return this.achievements;
    }

    public List getAchievements(OfflinePlayer playerFor) {
        ArrayList list = new ArrayList();
        Iterator iterator = User.forPlayer(playerFor).getFile(User.UFiles.ACHIEVEMENTS).getKeys(false).iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            list.add(new MyZAchievement(Configuration.CFiles.ACHIEVEMENTS.getFile().getConfigurationSection(key)));
        }

        return list;
    }

    public void refresh(OfflinePlayer playerFor, boolean notifyOfNew) {
        Iterator owned = this.getAchievements(playerFor).iterator();

        while (owned.hasNext()) {
            MyZAchievement achievement = (MyZAchievement) owned.next();

            if (!achievement.doesQualify(playerFor)) {
                achievement.set(playerFor, false);
            }
        }

        List owned1 = this.getAchievements(playerFor);
        Iterator achievement2 = this.getAchievements().iterator();

        while (achievement2.hasNext()) {
            MyZAchievement achievement1 = (MyZAchievement) achievement2.next();

            if (achievement1.doesQualify(playerFor) && !owned1.contains(achievement1)) {
                achievement1.set(playerFor, true);
                if (playerFor.isOnline()) {
                    MyZ.instance.getServer().getPluginManager().callEvent(new PlayerGetAchievementEvent((Player) playerFor, achievement1));
                    if (notifyOfNew) {
                        ((Player) playerFor).sendMessage(LocaleMessage.ACHIEVEMENT_EARNED.filter(new Object[] { achievement1.getName(), achievement1.getDescription()}).toString((CommandSender) ((Player) playerFor)));
                    }
                }
            }
        }

    }
}
