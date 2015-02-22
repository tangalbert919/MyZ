/**
 * 
 */
package jordan.sicherman.utilities;

import jordan.sicherman.player.User;
import jordan.sicherman.player.User.UFiles;
import jordan.sicherman.utilities.configuration.FileUtilities;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Jordan
 * 
 */
public class MyZAchievement {

	private final String description, name, key;
	private final int zKills, pKills, gKills, plKills, drinks, deaths, friends;

	public MyZAchievement(ConfigurationSection section) {
		key = section.getCurrentPath();
		name = section.getString("name");
		description = section.getString("description");
		zKills = section.getInt("requirements.kills.zombie");
		pKills = section.getInt("requirements.kills.pigman");
		plKills = section.getInt("requirements.kills.player");
		gKills = section.getInt("requirements.kills.giant");
		drinks = section.getInt("requirements.drinks");
		deaths = section.getInt("requirements.deaths");
		friends = section.getInt("requirements.friends");
	}

	@Override
	public String toString() {
		return getName() + " (" + getDescription() + "): [zombie: " + zKills + " pigmen: " + pKills + " giant: " + gKills + " player: "
				+ plKills + " drinks: " + drinks + " deaths: " + deaths + " friends: " + friends + "]";
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof MyZAchievement) { return key.equals(((MyZAchievement) other).key); }
		return super.equals(other);
	}

	public boolean doesQualify(OfflinePlayer player) {
		if (DataWrapper.<Integer> get(player, UserEntries.ZOMBIE_KILLS) < zKills) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.PIGMAN_KILLS) < pKills) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.PLAYER_KILLS) < plKills) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.GIANT_KILLS) < gKills) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.DRINKS) < drinks) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.DEATHS) < deaths) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.FRIENDS) < friends) { return false; }
		return true;
	}

	public boolean hasAchievement(OfflinePlayer player) {
		return User.forPlayer(player).getFile(UFiles.ACHIEVEMENTS).isSet(key);
	}

	public void set(OfflinePlayer playerFor, boolean hasAchievement) {
		User u = User.forPlayer(playerFor);
		if (!hasAchievement) {
			if (!hasAchievement(playerFor)) { return; }
			u.getFile(UFiles.ACHIEVEMENTS).set(key, null);
		} else {
			if (hasAchievement(playerFor)) { return; }
			u.getFile(UFiles.ACHIEVEMENTS).set(key, System.currentTimeMillis());
		}
		FileUtilities.save(User.forPlayer(playerFor), UFiles.ACHIEVEMENTS);
	}
}
