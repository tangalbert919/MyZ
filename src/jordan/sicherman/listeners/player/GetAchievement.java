package jordan.sicherman.listeners.player;

import jordan.sicherman.api.PlayerGetAchievementEvent;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.MyZAchievement;
import jordan.sicherman.utilities.StartingKitManager;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class GetAchievement implements Listener {

    @EventHandler
    private void onAchieve(PlayerGetAchievementEvent e) {
        MyZAchievement a = e.getAchievement();

        if ("Building Immunity".equals(a.getName())) {
            DataWrapper.set((OfflinePlayer) e.getPlayer(), UserEntries.IMMUNITY, Integer.valueOf(1));
        } else if ("Staying Hydrated".equals(a.getName())) {
            StartingKitManager.getInstance().addToKit(e.getPlayer(), new ItemStack[] { new ItemStack(Material.POTION, 1)});
        }

    }
}
