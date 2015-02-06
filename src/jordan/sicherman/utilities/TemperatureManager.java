/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.Arrays;
import java.util.List;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.particles.ParticleEffect;
import jordan.sicherman.utilities.TemperatureManager.TemperatureState.Goldilocks;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class TemperatureManager {

	private static TemperatureManager instance;

	public TemperatureManager() {
		instance = this;
	}

	public static TemperatureManager getInstance() {
		return instance;
	}

	public static enum TemperatureEffect {
		SWEAT, FROSTBITE, FATIGUE, CORNEA_FREEZING, WEAKNESS, DAMAGE, UPDATE_THERMOMETER, RELEASE_THERMOMETER;
	}

	public static enum TemperatureState {
		HEATSTROKE_3(ConfigEntries.HEATSTROKE_3_THRESHOLD, Goldilocks.TOO_HOT, 4), HEATSTROKE_2(ConfigEntries.HEATSTROKE_2_THRESHOLD,
				Goldilocks.TOO_HOT, 3), HEATSTROKE_1(ConfigEntries.HEATSTROKE_1_THRESHOLD, Goldilocks.TOO_HOT, 2), SWEATING(
				ConfigEntries.SWEATING_THRESHOLD, Goldilocks.TOO_HOT, 1), NORMAL(ConfigEntries.DEFAULT_TEMPERATURE, Goldilocks.JUST_RIGHT,
				0), SHIVERING(ConfigEntries.SHIVERING_THRESHOLD, Goldilocks.TOO_COLD, 1), HYPOTHERMIA_1(
				ConfigEntries.HYPOTHERMIA_1_THRESHOLD, Goldilocks.TOO_COLD, 2), HYPOTHERMIA_2(ConfigEntries.HYPOTHERMIA_2_THRESHOLD,
				Goldilocks.TOO_COLD, 3), HYPOTHERMIA_3(ConfigEntries.HYPOTHERMIA_3_THRESHOLD, Goldilocks.TOO_COLD, 4);

		public static enum Goldilocks {
			TOO_HOT, JUST_RIGHT, TOO_COLD;
		}

		private final double threshold;
		private final Goldilocks raw;
		private final int level;

		private TemperatureState(ConfigEntries entry, Goldilocks raw, int level) {
			threshold = entry.<Double> getValue();
			this.raw = raw;
			this.level = level;
		}

		public Goldilocks getRaw() {
			return raw;
		}

		public int getLevel() {
			return level;
		}

		public double getThreshold() {
			return threshold;
		}
	}

	public void computeTemperature(Player playerFor, TemperatureEffect... effectsToApply) {
		if (!MyZ.isPremium()) { return; }

		double temperature = DataWrapper.get(playerFor, UserEntries.TEMPERATURE);

		World world = playerFor.getWorld();
		Location loc = playerFor.getLocation();

		Biome biome = world.getBiome(loc.getBlockX(), loc.getBlockZ());
		double biome_temperature = getTemperature(world, biome);
		boolean rainedOn = world.isThundering() && world.getHighestBlockYAt(loc) <= loc.getY();
		boolean onFire = playerFor.getFireTicks() > 0;
		boolean night = world.getTime() < 13000;
		int leather_armour = getArmourOfType(playerFor.getEquipment().getArmorContents(), "leather");
		int chain_armour = getArmourOfType(playerFor.getEquipment().getArmorContents(), "chain");
		int iron_armour = getArmourOfType(playerFor.getEquipment().getArmorContents(), "iron");
		int gold_armour = getArmourOfType(playerFor.getEquipment().getArmorContents(), "gold");
		int diamond_armour = getArmourOfType(playerFor.getEquipment().getArmorContents(), "diamond");
		Material at = world.getBlockAt(playerFor.getLocation()).getType();
		boolean inWater = biome != Biome.OCEAN && biome != Biome.DEEP_OCEAN && biome != Biome.FROZEN_OCEAN
				&& at == Material.STATIONARY_WATER || at == Material.WATER;
		boolean infected = DataWrapper.get(playerFor, UserEntries.POISONED);
		boolean proximity_to_hot = closeToHot(loc);
		boolean thirsty = DataWrapper.<Double> get(playerFor, UserEntries.THIRST) < ConfigEntries.THIRSTY_THRESHOLD.<Double> getValue();

		double new_temperature = biome_temperature;
		new_temperature += night ? ConfigEntries.TEMPERATURE_NIGHT.<Double> getValue() : 0;
		new_temperature += playerFor.isSprinting() ? ConfigEntries.TEMPERATURE_RUNNING.<Double> getValue() : 0;
		new_temperature += rainedOn ? ConfigEntries.TEMPERATURE_RAIN.<Double> getValue() : 0;
		new_temperature += leather_armour * ConfigEntries.TEMPERATURE_LEATHER_ARMOUR.<Double> getValue();
		new_temperature += chain_armour * ConfigEntries.TEMPERATURE_CHAIN_ARMOUR.<Double> getValue();
		new_temperature += iron_armour * ConfigEntries.TEMPERATURE_IRON_ARMOUR.<Double> getValue();
		new_temperature += gold_armour * ConfigEntries.TEMPERATURE_GOLD_ARMOUR.<Double> getValue();
		new_temperature += diamond_armour * ConfigEntries.TEMPERATURE_DIAMOND_ARMOUR.<Double> getValue();
		new_temperature += infected ? ConfigEntries.TEMPERATURE_INFECTED.<Double> getValue() : 0;
		new_temperature += proximity_to_hot ? ConfigEntries.TEMPERATURE_NEAR_FIRE.<Double> getValue() : 0;
		new_temperature += thirsty ? ConfigEntries.TEMPERATURE_THIRSTY.<Double> getValue() : 0;
		new_temperature += onFire ? ConfigEntries.TEMPERATURE_FIRE.<Double> getValue() : 0;
		new_temperature += inWater ? ConfigEntries.TEMPERATURE_WATER.<Double> getValue() : 0;

		// Place more weight on the old temperature to ensure gradual shifts.
		double final_temperature = (temperature * 499.0 + new_temperature) / 500.0;

		DataWrapper.set(playerFor, UserEntries.TEMPERATURE, final_temperature);

		if (effectsToApply != null && effectsToApply.length != 0) {
			doTemperatureEffects(playerFor, effectsToApply);
		}
	}

	public void doTemperatureEffects(final Player playerFor, final TemperatureEffect... effects) {
		if (!MyZ.isPremium()) { return; }

		new BukkitRunnable() {
			@Override
			public void run() {
				List<TemperatureEffect> effectList = Arrays.<TemperatureEffect> asList(effects);
				if (effectList.contains(TemperatureEffect.RELEASE_THERMOMETER)) {
					Utilities.faceCompass(playerFor, true, null);
					if (effects.length == 1) { return; }
				}

				TemperatureState state = getState(playerFor);

				if (effectList.contains(TemperatureEffect.UPDATE_THERMOMETER)) {
					BlockFace face = BlockFace.NORTH;
					switch (state) {
					case HEATSTROKE_1:
						face = BlockFace.EAST;
						break;
					case HEATSTROKE_2:
						face = BlockFace.NORTH_EAST;
						break;
					case HEATSTROKE_3:
					case HYPOTHERMIA_3:
						face = BlockFace.NORTH;
						break;
					case HYPOTHERMIA_1:
						face = BlockFace.WEST;
						break;
					case HYPOTHERMIA_2:
						face = BlockFace.NORTH_WEST;
						break;
					case NORMAL:
						face = BlockFace.SOUTH;
						break;
					case SHIVERING:
						face = BlockFace.SOUTH_WEST;
						break;
					case SWEATING:
						face = BlockFace.SOUTH_EAST;
						break;
					default:
						break;
					}

					for (int i = 0; i < 9; i++) {
						ItemStack item = playerFor.getInventory().getItem(i);
						if (item != null && item.getType() == ItemTag.THERMOMETER.getType()) {
							Utilities.faceCompass(playerFor, false, face);
						}
					}
				}

				if (state.getRaw() == Goldilocks.JUST_RIGHT) { return; }

				if (state.getRaw() == Goldilocks.TOO_HOT) {
					if (playerFor.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) { return; }

					switch (state) {
					case HEATSTROKE_3:
					case HEATSTROKE_2:
					case HEATSTROKE_1:
						if (effectList.contains(TemperatureEffect.DAMAGE)) {
							if ((int) (playerFor.getHealth() - ConfigEntries.DAMAGE_HEATSTROKE.<Double> getValue() * state.getLevel()) > 0) {
								playerFor.damage(ConfigEntries.DAMAGE_HEATSTROKE.<Double> getValue() * state.getLevel());
							}
						}
					case SWEATING:
						if (effectList.contains(TemperatureEffect.SWEAT)) {
							ParticleEffect.DRIP_WATER.display(0.25f, 0.25f, 0.25f, 0f, 50, playerFor.getEyeLocation().subtract(0, 0.5, 0),
									10.0);
						}
						if (effectList.contains(TemperatureEffect.FATIGUE)) {
							playerFor.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 35, state.getLevel() - 1));
						}
						break;
					default:
						break;
					}
				} else if (state.getRaw() == Goldilocks.TOO_COLD) {
					switch (state) {
					case HYPOTHERMIA_3:
					case HYPOTHERMIA_2:
						if (effectList.contains(TemperatureEffect.FROSTBITE)) {
							if (playerFor.getItemInHand() != null && playerFor.getItemInHand().getType() != Material.AIR) {
								playerFor.getWorld().dropItemNaturally(playerFor.getLocation(), playerFor.getItemInHand().clone());
								playerFor.setItemInHand(null);
							}
						}
					case HYPOTHERMIA_1:
						if (effectList.contains(TemperatureEffect.DAMAGE)) {
							if ((int) (playerFor.getHealth() - ConfigEntries.DAMAGE_HYPOTHERMIA.<Double> getValue() * state.getLevel()) > 0) {
								playerFor.damage(ConfigEntries.DAMAGE_HYPOTHERMIA.<Double> getValue() * state.getLevel());
							}
						}
						if (effectList.contains(TemperatureEffect.CORNEA_FREEZING)) {
							playerFor.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 35, state.getLevel() - 1));
						}
					case SHIVERING:
						if (effectList.contains(TemperatureEffect.FATIGUE)) {
							playerFor.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 35, state.getLevel() - 1));
						}
						if (effectList.contains(TemperatureEffect.WEAKNESS)) {
							playerFor.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 35, state.getLevel() - 1));
						}
						break;
					default:
						break;
					}
				}
			}
		}.runTaskLater(MyZ.instance, 0L);
	}

	public TemperatureState getState(Player playerFor) {
		double temperature = DataWrapper.get(playerFor, UserEntries.TEMPERATURE);

		if (temperature >= TemperatureState.HEATSTROKE_3.getThreshold()) { return TemperatureState.HEATSTROKE_3; }
		if (temperature >= TemperatureState.HEATSTROKE_2.getThreshold()) { return TemperatureState.HEATSTROKE_2; }
		if (temperature >= TemperatureState.HEATSTROKE_1.getThreshold()) { return TemperatureState.HEATSTROKE_1; }
		if (temperature >= TemperatureState.SWEATING.getThreshold()) { return TemperatureState.SWEATING; }
		if (temperature <= TemperatureState.HYPOTHERMIA_3.getThreshold()) { return TemperatureState.HYPOTHERMIA_3; }
		if (temperature <= TemperatureState.HYPOTHERMIA_2.getThreshold()) { return TemperatureState.HYPOTHERMIA_2; }
		if (temperature <= TemperatureState.HYPOTHERMIA_1.getThreshold()) { return TemperatureState.HYPOTHERMIA_1; }
		if (temperature <= TemperatureState.SHIVERING.getThreshold()) { return TemperatureState.SHIVERING; }

		return TemperatureState.NORMAL;
	}

	private boolean closeToHot(Location centre) {
		for (int x = centre.getBlockX() - 2; x < centre.getBlockX() + 2; x++) {
			for (int y = centre.getBlockY() - 3; y < centre.getBlockY(); y++) {
				for (int z = centre.getBlockZ() - 2; z < centre.getBlockZ(); z++) {
					Block at = centre.getWorld().getBlockAt(x, y, z);
					if (at != null && at.getType() == Material.FIRE || at.getType() == Material.LAVA || at.getType() == Material.TORCH) { return true; }
				}
			}
		}
		return false;
	}

	private int getArmourOfType(ItemStack[] armor, String type) {
		int pieces = 0;
		for (ItemStack i : armor) {
			if (i != null && EquipmentState.isArmor(i) && i.getType().toString().toLowerCase().contains(type)) {
				pieces++;
			}
		}

		return pieces;
	}

	public void setToDefault(Player playerFor) {
		DataWrapper.set(playerFor, UserEntries.TEMPERATURE, ConfigEntries.DEFAULT_TEMPERATURE.<Double> getValue());
	}

	public double getTemperature(World world, Biome biome) {
		switch (biome) {
		case DEEP_OCEAN:
		case OCEAN:
		case FROZEN_OCEAN:
			return ConfigEntries.TEMPERATURE_OCEAN.<Double> getValue();
		case COLD_BEACH:
		case FROZEN_RIVER:
		case COLD_TAIGA:
		case COLD_TAIGA_HILLS:
		case COLD_TAIGA_MOUNTAINS:
		case ICE_MOUNTAINS:
		case ICE_PLAINS:
		case ICE_PLAINS_SPIKES:
			return ConfigEntries.TEMPERATURE_ICY.<Double> getValue();
		case MEGA_SPRUCE_TAIGA:
		case MEGA_SPRUCE_TAIGA_HILLS:
		case MEGA_TAIGA:
		case MEGA_TAIGA_HILLS:
		case TAIGA:
		case TAIGA_HILLS:
		case TAIGA_MOUNTAINS:
		case EXTREME_HILLS:
		case STONE_BEACH:
			return ConfigEntries.TEMPERATURE_COLD.<Double> getValue();
		case DESERT:
		case DESERT_HILLS:
		case DESERT_MOUNTAINS:
		case MESA:
		case MESA_BRYCE:
		case MESA_PLATEAU:
		case MESA_PLATEAU_FOREST:
		case MESA_PLATEAU_FOREST_MOUNTAINS:
		case MESA_PLATEAU_MOUNTAINS:
		case SAVANNA:
		case SAVANNA_PLATEAU:
		case SAVANNA_PLATEAU_MOUNTAINS:
		case SAVANNA_MOUNTAINS:
			if (world.getTime() < 13000) {
				return ConfigEntries.TEMPERATURE_WARM.<Double> getValue();
			} else {
				return ConfigEntries.TEMPERATURE_COLD.<Double> getValue();
			}
		default:
			return ConfigEntries.TEMPERATURE_MEDIUM.<Double> getValue();
		}
	}
}
