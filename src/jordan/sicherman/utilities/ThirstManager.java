package jordan.sicherman.utilities;

import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public class ThirstManager {

    private static ThirstManager instance;

    public ThirstManager() {
        ThirstManager.instance = this;
    }

    public static ThirstManager getInstance() {
        return ThirstManager.instance;
    }

    public void replenish(Player playerFor, boolean saltWater, boolean didDrink) {
        if (((Boolean) ConfigEntries.USE_THIRST.getValue()).booleanValue()) {
            double thirst = (new Double((double) ((Integer) ConfigEntries.THIRST_MAX_DEFAULT.getValue()).intValue())).doubleValue();

            if (saltWater) {
                thirst = (((Double) DataWrapper.get(playerFor, UserEntries.THIRST)).doubleValue() + thirst) / 2.0D;
            }

            DataWrapper.set((OfflinePlayer) playerFor, UserEntries.THIRST, Double.valueOf(thirst));
            playerFor.setLevel((int) (thirst + 1.0D));
            DataWrapper.set((OfflinePlayer) playerFor, UserEntries.DRINKS, Integer.valueOf(((Integer) DataWrapper.get(playerFor, UserEntries.DRINKS)).intValue() + 1));
        }
    }

    public void replenish(Player playerFor, boolean saltWater) {
        this.replenish(playerFor, saltWater, true);
    }

    public void computeThirst(Player playerFor) {
        double thirst = ((Double) DataWrapper.get(playerFor, UserEntries.THIRST)).doubleValue();
        int mod;

        switch (ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[playerFor.getWorld().getBiome(playerFor.getLocation().getBlockX(), playerFor.getLocation().getBlockZ()).ordinal()]) {
        case 1:
        case 2:
        case 3:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_BEACH.getValue()).intValue();
            break;

        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_PLAINS.getValue()).intValue();
            break;

        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
        case 31:
        case 32:
        case 33:
        case 34:
        case 35:
        case 36:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_TAIGA.getValue()).intValue();
            break;

        case 37:
        case 38:
        case 39:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_DESERT.getValue()).intValue();
            break;

        case 40:
        case 41:
        case 42:
        case 43:
        case 44:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_OCEAN.getValue()).intValue();
            break;

        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_JUNGLE.getValue()).intValue();
            break;

        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_MESA.getValue()).intValue();
            break;

        case 56:
        case 57:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_MUSHROOM.getValue()).intValue();
            break;

        case 58:
        case 59:
            mod = ((Integer) ConfigEntries.THIRST_DECAY_SWAMP.getValue()).intValue();
            break;

        default:
            mod = 50;
        }

        thirst -= 1.0D / (20.0D * (double) mod);
        if (thirst < 0.0D) {
            thirst = 0.9999D;
            if (playerFor.getHealth() > 1.0D) {
                playerFor.damage(1.0D);
            }
        } else if (thirst > (double) ((Integer) ConfigEntries.THIRST_MAX_DEFAULT.getValue()).intValue()) {
            thirst = (double) ((Integer) ConfigEntries.THIRST_MAX_DEFAULT.getValue()).intValue();
        }

        double display = Math.floor(thirst);

        DataWrapper.set(playerFor, UserEntries.THIRST, Double.valueOf(thirst));
        playerFor.setLevel((int) display);
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$org$bukkit$block$Biome = new int[Biome.values().length];

        static {
            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.BEACHES.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.COLD_BEACH.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.STONE_BEACH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.BIRCH_FOREST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.BIRCH_FOREST_HILLS.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_BIRCH_FOREST_HILLS.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_BIRCH_FOREST.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[BiomeForest.Type.FLOWER.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.FOREST.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.FOREST_HILLS.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.EXTREME_HILLS.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_EXTREME_HILLS.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_EXTREME_HILLS_WITH_TREES.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SMALLER_EXTREME_HILLS.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.PLAINS.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.ROOFED_FOREST.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[BiomeForest.Type.ROOFED.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SMALLER_EXTREME_HILLS.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_ROOFED_FOREST.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SAVANNA.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SAVANNA_ROCK.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_SAVANNA_ROCK.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_SAVANNA.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA_COLD.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA_COLD_HILLS.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_TAIGA_COLD.ordinal()] = 26;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.ICE_MOUNTAINS.ordinal()] = 27;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.ICE_FLATS.ordinal()] = 28;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_ICE_FLATS.ordinal()] = 29;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_REDWOOD_TAIGA.ordinal()] = 30;
            } catch (NoSuchFieldError nosuchfielderror29) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.REDWOOD_TAIGA.ordinal()] = 31;
            } catch (NoSuchFieldError nosuchfielderror30) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.REDWOOD_TAIGA_HILLS.ordinal()] = 32;
            } catch (NoSuchFieldError nosuchfielderror31) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_REDWOOD_TAIGA_HILLS.ordinal()] = 33;
            } catch (NoSuchFieldError nosuchfielderror32) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA.ordinal()] = 34;
            } catch (NoSuchFieldError nosuchfielderror33) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA_HILLS.ordinal()] = 35;
            } catch (NoSuchFieldError nosuchfielderror34) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA_COLD_HILLS.ordinal()] = 36;
            } catch (NoSuchFieldError nosuchfielderror35) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.DESERT.ordinal()] = 37;
            } catch (NoSuchFieldError nosuchfielderror36) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.DESERT_HILLS.ordinal()] = 38;
            } catch (NoSuchFieldError nosuchfielderror37) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_DESERT.ordinal()] = 39;
            } catch (NoSuchFieldError nosuchfielderror38) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.DEEP_OCEAN.ordinal()] = 40;
            } catch (NoSuchFieldError nosuchfielderror39) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.FROZEN_OCEAN.ordinal()] = 41;
            } catch (NoSuchFieldError nosuchfielderror40) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.FROZEN_RIVER.ordinal()] = 42;
            } catch (NoSuchFieldError nosuchfielderror41) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.OCEAN.ordinal()] = 43;
            } catch (NoSuchFieldError nosuchfielderror42) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.RIVER.ordinal()] = 44;
            } catch (NoSuchFieldError nosuchfielderror43) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.JUNGLE.ordinal()] = 45;
            } catch (NoSuchFieldError nosuchfielderror44) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.JUNGLE_EDGE.ordinal()] = 46;
            } catch (NoSuchFieldError nosuchfielderror45) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_JUNGLE_EDGE.ordinal()] = 47;
            } catch (NoSuchFieldError nosuchfielderror46) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.JUNGLE_HILLS.ordinal()] = 48;
            } catch (NoSuchFieldError nosuchfielderror47) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_JUNGLE.ordinal()] = 49;
            } catch (NoSuchFieldError nosuchfielderror48) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MESA.ordinal()] = 50;
            } catch (NoSuchFieldError nosuchfielderror49) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MESA_CLEAR_ROCK.ordinal()] = 51;
            } catch (NoSuchFieldError nosuchfielderror50) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MESA_ROCK.ordinal()] = 52;
            } catch (NoSuchFieldError nosuchfielderror51) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_MESA.ordinal()] = 53;
            } catch (NoSuchFieldError nosuchfielderror52) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_MESA_CLEAR_ROCK.ordinal()] = 54;
            } catch (NoSuchFieldError nosuchfielderror53) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_MESA_ROCK.ordinal()] = 55;
            } catch (NoSuchFieldError nosuchfielderror54) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUSHROOM_ISLAND.ordinal()] = 56;
            } catch (NoSuchFieldError nosuchfielderror55) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUSHROOM_ISLAND_SHORE.ordinal()] = 57;
            } catch (NoSuchFieldError nosuchfielderror56) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SWAMPLAND.ordinal()] = 58;
            } catch (NoSuchFieldError nosuchfielderror57) {
                ;
            }

            try {
                ThirstManager.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MUTATED_SWAMPLAND.ordinal()] = 59;
            } catch (NoSuchFieldError nosuchfielderror58) {
                ;
            }

        }
    }
}
