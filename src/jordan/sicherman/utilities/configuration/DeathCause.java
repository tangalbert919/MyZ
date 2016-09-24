package jordan.sicherman.utilities.configuration;

import java.util.List;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import org.bukkit.entity.Player;

public enum DeathCause {

    FALL(new LocaleMessage[] { LocaleMessage.DEATH_FALL}), ZOMBIE(new LocaleMessage[] { LocaleMessage.DEATH_ZOMBIE}), PIGMAN(new LocaleMessage[] { LocaleMessage.DEATH_PIGMAN}), GIANT(new LocaleMessage[] { LocaleMessage.DEATH_GIANT}), DROWNING(new LocaleMessage[] { LocaleMessage.DEATH_DROWNED}), CACTUS(new LocaleMessage[] { LocaleMessage.DEATH_CACTUS}), FIRE(new LocaleMessage[] { LocaleMessage.DEATH_FIRE}), LAVA(new LocaleMessage[] { LocaleMessage.DEATH_LAVA}), POISON(new LocaleMessage[] { LocaleMessage.DEATH_POISON}), MAGIC(new LocaleMessage[] { LocaleMessage.DEATH_MAGIC}), ARROW(new LocaleMessage[] { LocaleMessage.DEATH_ARROW}), PLAYER(new LocaleMessage[] { LocaleMessage.DEATH_PLAYER}), EXPLOSION(new LocaleMessage[] { LocaleMessage.DEATH_EXPLOSION}), SUFFOCATION(new LocaleMessage[] { LocaleMessage.DEATH_SUFFOCATION}), OTHER(new LocaleMessage[] { LocaleMessage.DEATH_UNKNOWN}), STARVE(new LocaleMessage[] { LocaleMessage.DEATH_STARVED}), VOID(new LocaleMessage[] { LocaleMessage.DEATH_VOID}), GHOSTLY(new LocaleMessage[] { LocaleMessage.DEATH_AS_GHOST});

    private final LocaleMessage[] message;

    private DeathCause(LocaleMessage... message) {
        this.message = message;
    }

    public LocaleMessage[] getMessages() {
        return this.message;
    }

    public boolean compileAndSendAsJSON(Player death, List audience) {
        return CompatibilityManager.sendDeathMessage(this, death, (Player) null, (Player[]) audience.toArray(new Player[0]));
    }

    public boolean compileAndSendAsJSON(Player death, Player killer, List audience) {
        return CompatibilityManager.sendDeathMessage(this, death, killer, (Player[]) audience.toArray(new Player[0]));
    }
}
