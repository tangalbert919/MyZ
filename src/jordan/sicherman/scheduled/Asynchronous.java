package jordan.sicherman.scheduled;

import java.util.Iterator;
import jordan.sicherman.MyZ;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.SerializableLocation;
import jordan.sicherman.utilities.ThirstManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class Asynchronous extends BukkitRunnable {

    private final boolean thirst;
    private final boolean bleed;
    private final boolean infect;
    private static int ticks = 0;

    public Asynchronous() {
        this.thirst = ((Boolean) ConfigEntries.USE_THIRST.getValue()).booleanValue();
        this.bleed = ((Boolean) ConfigEntries.USE_BLEEDING.getValue()).booleanValue();
        this.infect = ((Boolean) ConfigEntries.USE_POISON.getValue()).booleanValue();
    }

    public void run() {
        this.report();
        ++Asynchronous.ticks;
        if (Asynchronous.ticks >= 20) {
            this.checkChests();
            Asynchronous.ticks = 0;
        }

    }

    public void cancel() {}

    private void report() {
        if (this.thirst || this.bleed || this.infect) {
            Iterator iterator = MyZ.instance.getServer().getOnlinePlayers().iterator();

            while (iterator.hasNext()) {
                Player player = (Player) iterator.next();

                if (player.getGameMode() != GameMode.CREATIVE && Utilities.inWorld(player) && ((Boolean) DataWrapper.get(player, UserEntries.PLAYING)).booleanValue() && !player.isDead()) {
                    if (this.thirst) {
                        ThirstManager.getInstance().computeThirst(player);
                    }

                    if (this.bleed || this.infect) {
                        if (this.bleed) {
                            this.performNegatives(player, "MyZ.bleed.next", UserEntries.BLEEDING, ((Integer) ConfigEntries.BLEED_DELAY.getValue()).intValue());
                        }

                        if (this.infect) {
                            this.performNegatives(player, "MyZ.infection.next", UserEntries.POISONED, ((Integer) ConfigEntries.INFECTION_DELAY.getValue()).intValue());
                        }
                    }
                }
            }
        }

    }

    private void performNegatives(Player player, String key, UserEntries entry, int duration) {
        if (((Boolean) DataWrapper.get(player, entry)).booleanValue()) {
            long cur = System.currentTimeMillis();

            if (player.hasMetadata(key) && player.getMetadata(key).size() >= 1) {
                long next = ((MetadataValue) player.getMetadata(key).get(0)).asLong();

                if (next <= cur) {
                    switch (Asynchronous.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[entry.ordinal()]) {
                    case 1:
                        Utilities.doBleedingDamage(player);
                        break;

                    case 2:
                        Utilities.doPoisonDamage(player);
                    }

                    player.removeMetadata(key, MyZ.instance);
                    player.setMetadata(key, new FixedMetadataValue(MyZ.instance, Long.valueOf(cur + (long) duration * 1000L)));
                }
            } else {
                player.setMetadata(key, new FixedMetadataValue(MyZ.instance, Long.valueOf(cur + (long) duration * 1000L)));
            }
        }

    }

    private void checkChests() {
        MyZ.instance.getServer().getScheduler().runTaskAsynchronously(MyZ.instance, new Runnable() {
            public void run() {
                ConfigurationSection chests = (ConfigurationSection) ConfigEntries.CHEST_LOCATIONS.getValue();
                Iterator iterator = chests.getKeys(false).iterator();

                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    Location location = SerializableLocation.deserialize(key);

                    ChestType.respawn(location, false);
                }

            }
        });
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$utilities$configuration$UserEntries = new int[UserEntries.values().length];

        static {
            try {
                Asynchronous.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.BLEEDING.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                Asynchronous.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$UserEntries[UserEntries.POISONED.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

        }
    }
}
