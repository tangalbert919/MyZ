/**
 * 
 */
package jordan.sicherman.utilities.configuration;

import java.util.List;

import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;

import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public enum DeathCause {

	FALL(LocaleMessage.DEATH_FALL), ZOMBIE(LocaleMessage.DEATH_ZOMBIE), PIGMAN(LocaleMessage.DEATH_PIGMAN), GIANT(LocaleMessage.DEATH_GIANT), DROWNING(
			LocaleMessage.DEATH_DROWNED), CACTUS(LocaleMessage.DEATH_CACTUS), FIRE(LocaleMessage.DEATH_FIRE), LAVA(LocaleMessage.DEATH_LAVA), POISON(
			LocaleMessage.DEATH_POISON), MAGIC(LocaleMessage.DEATH_MAGIC), ARROW(LocaleMessage.DEATH_ARROW), PLAYER(
			LocaleMessage.DEATH_PLAYER), EXPLOSION(LocaleMessage.DEATH_EXPLOSION), SUFFOCATION(LocaleMessage.DEATH_SUFFOCATION), OTHER(
			LocaleMessage.DEATH_UNKNOWN), STARVE(LocaleMessage.DEATH_STARVED), VOID(LocaleMessage.DEATH_VOID), GHOSTLY(
			LocaleMessage.DEATH_AS_GHOST);

	private final LocaleMessage[] message;

	private DeathCause(LocaleMessage... message) {
		this.message = message;
	}

	public LocaleMessage[] getMessages() {
		return message;
	}

	public boolean compileAndSendAsJSON(Player death, List<Player> audience) {
		return CompatibilityManager.sendDeathMessage(this, death, null, audience.toArray(new Player[0]));
	}

	public boolean compileAndSendAsJSON(Player death, Player killer, List<Player> audience) {
		return CompatibilityManager.sendDeathMessage(this, death, killer, audience.toArray(new Player[0]));
	}
}
