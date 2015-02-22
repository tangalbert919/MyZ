/**
 * 
 */
package jordan.sicherman.api;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerManager;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.Locale;
import jordan.sicherman.utilities.AchievementManager;
import jordan.sicherman.utilities.StartingKitManager;
import jordan.sicherman.utilities.ThirstManager;

import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 *         Is it really an API? Not really. But it does help point developers in
 *         the right direction as to where they can find helper utilities to
 *         hook into MyZ.
 */
public class MyZAPI {

	public static MyZAPI getInstance() {
		if (MyZ.myzapi == null) { throw new RuntimeException("MyZ has not loaded yet!"); }

		return MyZ.myzapi;
	}

	public ItemUtilities getItemInterface() {
		return ItemUtilities.getInstance();
	}

	public EngineerManager getEngineerInterface() {
		return EngineerManager.getInstance();
	}

	public Locale getLocale(Player playerFor) {
		return Locale.getLocale(playerFor);
	}

	public ThirstManager getThirstInterface() {
		return ThirstManager.getInstance();
	}

	public AchievementManager getAchievementInterface() {
		return AchievementManager.getInstance();
	}

	public StartingKitManager getStartingKitManager() {
		return StartingKitManager.getInstance();
	}
}
