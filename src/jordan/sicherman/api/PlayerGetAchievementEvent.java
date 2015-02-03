/**
 * 
 */
package jordan.sicherman.api;

import jordan.sicherman.utilities.MyZAchievement;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * @author Jordan
 * 
 */
public class PlayerGetAchievementEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();

	private final MyZAchievement achievement;

	public PlayerGetAchievementEvent(Player who, MyZAchievement achievement) {
		super(who);

		this.achievement = achievement;
	}

	public MyZAchievement getAchievement() {
		return achievement;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
