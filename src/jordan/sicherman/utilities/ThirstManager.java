/**
 * 
 */
package jordan.sicherman.utilities;

import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class ThirstManager {

	private static ThirstManager instance;

	public ThirstManager() {
		instance = this;
	}

	public static ThirstManager getInstance() {
		return instance;
	}

	public void replenish(Player playerFor, boolean saltWater, boolean didDrink) {
		if (!ConfigEntries.USE_THIRST.<Boolean> getValue()) { return; }

		double thirst = new Double(ConfigEntries.THIRST_MAX_DEFAULT.<Integer> getValue());
		if (saltWater) {
			thirst = (DataWrapper.<Double> get(playerFor, UserEntries.THIRST) + thirst) / 2.0;
		}
		DataWrapper.set(playerFor, UserEntries.THIRST, thirst);
		playerFor.setLevel((int) (thirst + 1.0));
		DataWrapper.set(playerFor, UserEntries.DRINKS, DataWrapper.<Integer> get(playerFor, UserEntries.DRINKS) + 1);
	}

	public void replenish(Player playerFor, boolean saltWater) {
		replenish(playerFor, saltWater, true);
	}

	public void computeThirst(Player playerFor) {
		double thirst = DataWrapper.get(playerFor, UserEntries.THIRST);

		int mod;
		switch (playerFor.getWorld().getBiome(playerFor.getLocation().getBlockX(), playerFor.getLocation().getBlockZ())) {
		case BEACH:
		case COLD_BEACH:
		case STONE_BEACH:
			mod = ConfigEntries.THIRST_DECAY_BEACH.getValue();
			break;
		case BIRCH_FOREST:
		case BIRCH_FOREST_HILLS:
		case BIRCH_FOREST_HILLS_MOUNTAINS:
		case BIRCH_FOREST_MOUNTAINS:
		case FLOWER_FOREST:
		case FOREST:
		case FOREST_HILLS:
		case EXTREME_HILLS:
		case EXTREME_HILLS_MOUNTAINS:
		case EXTREME_HILLS_PLUS:
		case EXTREME_HILLS_PLUS_MOUNTAINS:
		case PLAINS:
		case ROOFED_FOREST:
		case ROOFED_FOREST_MOUNTAINS:
		case SMALL_MOUNTAINS:
		case SUNFLOWER_PLAINS:
		case SAVANNA:
		case SAVANNA_MOUNTAINS:
		case SAVANNA_PLATEAU:
		case SAVANNA_PLATEAU_MOUNTAINS:
			mod = ConfigEntries.THIRST_DECAY_PLAINS.getValue();
			break;
		case COLD_TAIGA:
		case COLD_TAIGA_HILLS:
		case COLD_TAIGA_MOUNTAINS:
		case ICE_MOUNTAINS:
		case ICE_PLAINS:
		case ICE_PLAINS_SPIKES:
		case MEGA_SPRUCE_TAIGA:
		case MEGA_SPRUCE_TAIGA_HILLS:
		case MEGA_TAIGA:
		case MEGA_TAIGA_HILLS:
		case TAIGA:
		case TAIGA_HILLS:
		case TAIGA_MOUNTAINS:
			mod = ConfigEntries.THIRST_DECAY_TAIGA.getValue();
			break;
		case DESERT:
		case DESERT_HILLS:
		case DESERT_MOUNTAINS:
			mod = ConfigEntries.THIRST_DECAY_DESERT.getValue();
			break;
		case DEEP_OCEAN:
		case FROZEN_OCEAN:
		case FROZEN_RIVER:
		case OCEAN:
		case RIVER:
			mod = ConfigEntries.THIRST_DECAY_OCEAN.getValue();
			break;
		case JUNGLE:
		case JUNGLE_EDGE:
		case JUNGLE_EDGE_MOUNTAINS:
		case JUNGLE_HILLS:
		case JUNGLE_MOUNTAINS:
			mod = ConfigEntries.THIRST_DECAY_JUNGLE.getValue();
			break;
		case MESA:
		case MESA_BRYCE:
		case MESA_PLATEAU:
		case MESA_PLATEAU_FOREST:
		case MESA_PLATEAU_FOREST_MOUNTAINS:
		case MESA_PLATEAU_MOUNTAINS:
			mod = ConfigEntries.THIRST_DECAY_MESA.getValue();
			break;
		case MUSHROOM_ISLAND:
		case MUSHROOM_SHORE:
			mod = ConfigEntries.THIRST_DECAY_MUSHROOM.getValue();
			break;
		case SWAMPLAND:
		case SWAMPLAND_MOUNTAINS:
			mod = ConfigEntries.THIRST_DECAY_SWAMP.getValue();
			break;
		default:
			mod = 50;
			break;
		}

		thirst -= 1.0 / (20.0 * mod);

		if (thirst < 0) {
			thirst = 0.9999;
			if (playerFor.getHealth() > 1.0) {
				playerFor.damage(1.0);
			}
		} else if (thirst > ConfigEntries.THIRST_MAX_DEFAULT.<Integer> getValue()) {
			thirst = ConfigEntries.THIRST_MAX_DEFAULT.<Integer> getValue();
		}

		double display = Math.floor(thirst);
		DataWrapper.set(playerFor, UserEntries.THIRST, thirst);

		playerFor.setLevel((int) display);
	}
}