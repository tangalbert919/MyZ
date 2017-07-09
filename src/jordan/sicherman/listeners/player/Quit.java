package jordan.sicherman.listeners.player;

import jordan.sicherman.utilities.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {

    @EventHandler(
        priority = EventPriority.LOWEST
    )
    private void onQuit(PlayerQuitEvent e) {
        if (Utilities.inWorld(e.getPlayer())) {
            Utilities.doLogout(e.getPlayer());
        }
    }

    @EventHandler(
        priority = EventPriority.MONITOR,
        ignoreCancelled = true
    )
    private void onChangeQuit(PlayerChangedWorldEvent e) {
        if (!Utilities.inWorld(e.getPlayer())) {
            if (Utilities.inWorld(e.getFrom())) {
                Utilities.doLogout(e.getPlayer());
            }

        }
    }
}
