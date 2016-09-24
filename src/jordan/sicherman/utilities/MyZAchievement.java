package jordan.sicherman.utilities;

import jordan.sicherman.player.User;
import jordan.sicherman.utilities.configuration.FileUtilities;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public class MyZAchievement {

    private final String description;
    private final String name;
    private final String key;
    private final int zKills;
    private final int pKills;
    private final int gKills;
    private final int plKills;
    private final int drinks;
    private final int deaths;
    private final int friends;

    public MyZAchievement(ConfigurationSection section) {
        this.key = section.getCurrentPath();
        this.name = section.getString("name");
        this.description = section.getString("description");
        this.zKills = section.getInt("requirements.kills.zombie");
        this.pKills = section.getInt("requirements.kills.pigman");
        this.plKills = section.getInt("requirements.kills.player");
        this.gKills = section.getInt("requirements.kills.giant");
        this.drinks = section.getInt("requirements.drinks");
        this.deaths = section.getInt("requirements.deaths");
        this.friends = section.getInt("requirements.friends");
    }

    public String toString() {
        return this.getName() + " (" + this.getDescription() + "): [zombie: " + this.zKills + " pigmen: " + this.pKills + " giant: " + this.gKills + " player: " + this.plKills + " drinks: " + this.drinks + " deaths: " + this.deaths + " friends: " + this.friends + "]";
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean equals(Object other) {
        return other instanceof MyZAchievement ? this.key.equals(((MyZAchievement) other).key) : super.equals(other);
    }

    public boolean doesQualify(OfflinePlayer player) {
        return ((Integer) DataWrapper.get(player, UserEntries.ZOMBIE_KILLS)).intValue() < this.zKills ? false : (((Integer) DataWrapper.get(player, UserEntries.PIGMAN_KILLS)).intValue() < this.pKills ? false : (((Integer) DataWrapper.get(player, UserEntries.PLAYER_KILLS)).intValue() < this.plKills ? false : (((Integer) DataWrapper.get(player, UserEntries.GIANT_KILLS)).intValue() < this.gKills ? false : (((Integer) DataWrapper.get(player, UserEntries.DRINKS)).intValue() < this.drinks ? false : (((Integer) DataWrapper.get(player, UserEntries.DEATHS)).intValue() < this.deaths ? false : ((Integer) DataWrapper.get(player, UserEntries.FRIENDS)).intValue() >= this.friends)))));
    }

    public boolean hasAchievement(OfflinePlayer player) {
        return User.forPlayer(player).getFile(User.UFiles.ACHIEVEMENTS).isSet(this.key);
    }

    public void set(OfflinePlayer playerFor, boolean hasAchievement) {
        User u = User.forPlayer(playerFor);

        if (!hasAchievement) {
            if (!this.hasAchievement(playerFor)) {
                return;
            }

            u.getFile(User.UFiles.ACHIEVEMENTS).set(this.key, (Object) null);
        } else {
            if (this.hasAchievement(playerFor)) {
                return;
            }

            u.getFile(User.UFiles.ACHIEVEMENTS).set(this.key, Long.valueOf(System.currentTimeMillis()));
        }

        FileUtilities.save(User.forPlayer(playerFor), User.UFiles.ACHIEVEMENTS);
    }
}
