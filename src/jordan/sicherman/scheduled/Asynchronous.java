/**
 * 
 */
package jordan.sicherman.scheduled;

import jordan.sicherman.MyZ;
import jordan.sicherman.listeners.player.SpectatorMode;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.SerializableLocation;
import jordan.sicherman.utilities.TemperatureManager;
import jordan.sicherman.utilities.TemperatureManager.TemperatureEffect;
import jordan.sicherman.utilities.ThirstManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.VisibilityManager;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class Asynchronous extends BukkitRunnable {

	private final boolean visibility, thirst, bleed, infect, temperature;

	public Asynchronous() {
		visibility = ConfigEntries.USE_VISIBILITY.<Boolean> getValue();
		thirst = ConfigEntries.USE_THIRST.<Boolean> getValue();
		bleed = ConfigEntries.USE_BLEEDING.<Boolean> getValue();
		infect = ConfigEntries.USE_POISON.<Boolean> getValue();
		temperature = ConfigEntries.USE_TEMP.<Boolean> getValue();
	}

	@Override
	public void run() {
		report();
		checkChests();
	}

	@Override
	public void cancel() {

	}

	private void report() {
		if (visibility || thirst || bleed || infect || temperature) {
			for (Player player : MyZ.instance.getServer().getOnlinePlayers()) {
				if (player.getGameMode() != GameMode.CREATIVE && Utilities.inWorld(player)
						&& DataWrapper.<Boolean> get(player, UserEntries.PLAYING) && !player.isDead()) {
					if (visibility) {
						VisibilityManager.getInstance().computeXPBarVisibility(player);
					}
					if (thirst) {
						ThirstManager.getInstance().computeThirst(player);
					}
					if (temperature) {
						TemperatureManager.getInstance().computeTemperature(player, TemperatureEffect.UPDATE_THERMOMETER);
						performTemperatureDelays(player, "MyZ.temperature.damage.next",
								ConfigEntries.TEMPERATURE_DAMAGE_DELAY.<Integer> getValue(), TemperatureEffect.DAMAGE);
						performTemperatureDelays(player, "MyZ.temperature.potioneffects.next",
								ConfigEntries.TEMPERATURE_POTIONS_DELAY.<Integer> getValue(), TemperatureEffect.CORNEA_FREEZING,
								TemperatureEffect.FATIGUE, TemperatureEffect.WEAKNESS);
						performTemperatureDelays(player, "MyZ.temperature.sweat.next",
								ConfigEntries.TEMPERATURE_SWEAT_DELAY.<Integer> getValue(), TemperatureEffect.SWEAT);
						performTemperatureDelays(player, "MyZ.temperature.frostbite.next",
								ConfigEntries.TEMPERATURE_FROSTBITE_DELAY.<Integer> getValue(), TemperatureEffect.FROSTBITE);
					}

					if ((bleed || infect) && !SpectatorMode.isSpectator(player)) {
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

	private void performTemperatureDelays(Player player, String key, int duration, TemperatureEffect... effects) {
		long cur = System.currentTimeMillis();

		if (player.hasMetadata(key) && player.getMetadata(key).size() >= 1) {
			long next = player.getMetadata(key).get(0).asLong();

			if (next <= cur) {
				TemperatureManager.getInstance().doTemperatureEffects(player, effects);

				player.removeMetadata(key, MyZ.instance);
				player.setMetadata(key, new FixedMetadataValue(MyZ.instance, cur + duration * 1000L));
			}
		} else {
			player.setMetadata(key, new FixedMetadataValue(MyZ.instance, cur + duration * 1000L));
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
			public void run() {
				ConfigurationSection chests = ConfigEntries.CHEST_LOCATIONS.<ConfigurationSection> getValue();

				for (String key : chests.getKeys(false)) {
					ChestType.respawn(SerializableLocation.deserialize(key).getBlock(), false);
				}
			}
		});
	}
}
