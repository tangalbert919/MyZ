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
	private final int zKills, pKills, gKills, plKills, sRevives, oRevives, sHeals, oHeals, iHeals, drinks, deaths, friends, zombies,
			ghosts;

	public MyZAchievement(ConfigurationSection section) {
		key = section.getCurrentPath();
		name = section.getString("name");
		description = section.getString("description");
		zKills = section.getInt("requirements.kills.zombie");
		pKills = section.getInt("requirements.kills.pigman");
		plKills = section.getInt("requirements.kills.player");
		gKills = section.getInt("requirements.kills.giant");
		sHeals = section.getInt("requirements.heals.self.bleeding");
		iHeals = section.getInt("requirements.heals.self.infection");
		oHeals = section.getInt("requirements.heals.other");
		drinks = section.getInt("requirements.drinks");
		oRevives = section.getInt("requirements.revive.other");
		sRevives = section.getInt("requirements.revive.self");
		deaths = section.getInt("requirements.deaths");
		friends = section.getInt("requirements.friends");
		ghosts = section.getInt("requirements.deathstate.ghost");
		zombies = section.getInt("requirements.deathstate.zombie");
	}

	@Override
	public String toString() {
		return getName() + " (" + getDescription() + "): [zombie: " + zKills + " pigmen: " + pKills + " giant: " + gKills + " player: "
				+ plKills + " revives: " + oRevives + " revivals: " + sRevives + " heals: " + oHeals + " healed: " + sHeals
				+ " uninfects: " + sHeals + " drinks: " + drinks + " deaths: " + deaths + " friends: " + friends + " zombie_encounters: "
				+ zombies + " ghost encounters: " + ghosts + "]";
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
		if (DataWrapper.<Integer> get(player, UserEntries.REVIVES) < oRevives) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.REVIVALS) < sRevives) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.OTHER_HEALS) < oHeals) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.UNINFECTS) < iHeals) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.SELF_HEALS) < sHeals) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.DRINKS) < drinks) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.DEATHS) < deaths) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.FRIENDS) < friends) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.ZOMBIE_TIMES) < zombies) { return false; }
		if (DataWrapper.<Integer> get(player, UserEntries.GHOST_TIMES) < ghosts) { return false; }
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
