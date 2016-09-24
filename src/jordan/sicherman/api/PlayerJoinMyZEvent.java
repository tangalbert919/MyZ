package jordan.sicherman.api;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerJoinMyZEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerJoinMyZEvent(Player who) {
        super(who);
    }

    public HandlerList getHandlers() {
        return PlayerJoinMyZEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return PlayerJoinMyZEvent.handlers;
    }
}
