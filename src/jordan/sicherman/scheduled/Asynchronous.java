/**
 * 
 */
package jordan.sicherman.scheduled;

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
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class Asynchronous extends BukkitRunnable {

	private final boolean thirst, bleed, infect;
	private static int ticks = 0;

	public Asynchronous() {
		thirst = ConfigEntries.USE_THIRST.<Boolean> getValue();
		bleed = ConfigEntries.USE_BLEEDING.<Boolean> getValue();
		infect = ConfigEntries.USE_POISON.<Boolean> getValue();
	}

	@Override
	public void run() {
		report();
		ticks++;
		// Reduce some more consumption :)
		if (ticks >= 20) {
			checkChests();
			ticks = 0;
		}
	}

	@Override
	public void cancel() {

	}

	private void report() {
		if (thirst || bleed || infect) {
			for (Player player : MyZ.instance.getServer().getOnlinePlayers()) {
				if (player.getGameMode() != GameMode.CREATIVE && Utilities.inWorld(player)
						&& DataWrapper.<Boolean> get(player, UserEntries.PLAYING) && !player.isDead()) {
					if (thirst) {
						ThirstManager.getInstance().computeThirst(player);
					}

					if (bleed || infect) {
						if (bleed) {
							performNegatives(player, "MyZ.bleed.next", UserEntries.BLEEDING, ConfigEntries.BLEED_DELAY.<Integer> getValue());
						}
						if (infect) {
							performNegatives(player, "MyZ.infection.next", UserEntries.POISONED,
									ConfigEntries.INFECTION_DELAY.<Integer> getValue());
						}
					}
				}
			}
		}
	}

	private void performNegatives(Player player, String key, UserEntries entry, int duration) {
		if (DataWrapper.<Boolean> get(player, entry)) {
			long cur = System.currentTimeMillis();
			if (player.hasMetadata(key) && player.getMetadata(key).size() >= 1) {
				long next = player.getMetadata(key).get(0).asLong();

				if (next <= cur) {
					switch (entry) {
					case BLEEDING:
						Utilities.doBleedingDamage(player);
						break;
					case POISONED:
						Utilities.doPoisonDamage(player);
						break;
					default:
						break;
					}

					player.removeMetadata(key, MyZ.instance);
					player.setMetadata(key, new FixedMetadataValue(MyZ.instance, cur + duration * 1000L));
				}
			} else {
				player.setMetadata(key, new FixedMetadataValue(MyZ.instance, cur + duration * 1000L));
			}
		}
	}

	private void checkChests() {
		MyZ.instance.getServer().getScheduler().runTaskAsynchronously(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				ConfigurationSection chests = ConfigEntries.CHEST_LOCATIONS.<ConfigurationSection> getValue();

				for (String key : chests.getKeys(false)) {
					Location location = SerializableLocation.deserialize(key);
					ChestType.respawn(location, false);
				}
			}
		});
	}
}
