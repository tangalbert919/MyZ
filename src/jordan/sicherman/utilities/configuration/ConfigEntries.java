package jordan.sicherman.utilities.configuration;

import com.shampaggon.crackshot.CSUtility;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;
import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.utilities.AirDrop;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public enum ConfigEntries {

    UPDATE("auto-update", EntryType.BOOLEAN, Configuration.CFiles.CONFIG, Boolean.valueOf(true)), LANGUAGE("language", EntryType.STRING, Configuration.CFiles.CONFIG, "en_CA"), WORLDS("worlds", EntryType.LIST, Configuration.CFiles.CONFIG, Arrays.asList(new String[] { "world"})), THIRST_MAX_DEFAULT("thirst.max.default", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(20)), THIRST_DECAY_BEACH("thirst.decay.beach", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(20)), THIRST_DECAY_PLAINS("thirst.decay.plains", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(38)), THIRST_DECAY_TAIGA("thirst.decay.taiga", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(40)), THIRST_DECAY_DESERT("thirst.decay.desert", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(23)), THIRST_DECAY_OCEAN("thirst.decay.ocean", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(-120)), THIRST_DECAY_JUNGLE("thirst.decay.jungle", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(31)), THIRST_DECAY_MESA("thirst.decay.mesa", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(30)), THIRST_DECAY_MUSHROOM("thirst.decay.mushroom", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(50)), THIRST_DECAY_SWAMP("thirst.decay.swamp", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(49)), SPAWN_POINTS("spawnpoints", EntryType.LIST, Configuration.CFiles.SPAWNLOCATIONS, new ArrayList()), HOME_SPAWN("home", EntryType.STRING, Configuration.CFiles.SPAWNLOCATIONS, ""), BLEED_DELAY("bleed.damage_delay", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(42)), INFECTION_DELAY("infection.damage_delay", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(38)), BLEED_DAMAGE("bleed.damage_amount", EntryType.DOUBLE, Configuration.CFiles.EXTRAS, Double.valueOf(1.1D)), INFECTION_DAMAGE("infection.damage_amount", EntryType.DOUBLE, Configuration.CFiles.EXTRAS, Double.valueOf(1.3D)), DEVASTATED_MOD("equipment.devastated_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(50)), SHATTERED_MOD("equipment.shattered_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(40)), BROKEN_MOD("equipment.broken_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(70)), SLACK_MOD("equipment.slack_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(90)), REINFORCED_MOD("equipment.reinforced_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(110)), CRACKED_MOD("equipment.cracked_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(80)), PRECISE_MOD("equipment.precise_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(120)), BOW_SHARPENED_MOD("equipment.bow_sharpened.ranged_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(150)), WEAKENED_MOD("equipment.weakened_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(85)), DULL_MOD("equipment.dull_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(90)), SHARPENED_MOD("equipment.sharpened_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(130)), TEMPERED_MOD("equipment.tempered_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(150)), FORTIFIED_MOD("equipment.fortified_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(120)), ORNATE_MOD("equipment.ornate_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(150)), USE_ENHANCED_ANVILS("enhance_envils.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), UPGRADE_AMOUNT("equipment.upgrade.material_cost", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(6)), ENGINEER_RECIPES("recipes", EntryType.CONFIGURATION_SECTION, Configuration.CFiles.ENGINEER, (Object) null), FRIENDLY("friends", EntryType.CONFIGURATION_SECTION, Configuration.CFiles.ACHIEVEMENTS, (Object) null), THIRSTY("thirsty", EntryType.CONFIGURATION_SECTION, Configuration.CFiles.ACHIEVEMENTS, (Object) null), IMMUNE("immune", EntryType.CONFIGURATION_SECTION, Configuration.CFiles.ACHIEVEMENTS, (Object) null), LIFECYCLE("lifecycle", EntryType.CONFIGURATION_SECTION, Configuration.CFiles.ACHIEVEMENTS, (Object) null), BANDAGE_HEAL("heal.bandage.amount", EntryType.DOUBLE, Configuration.CFiles.EXTRAS, Double.valueOf(1.0D)), USE_THIRST("thirst.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), ENHANCE_FOOD("enhanced_food.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), PREVENT_WITHERING("wither_damage.disabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), NO_ZOMBIE_FALL("zombie.falldamage.disabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), ZOMBIES_BURN("zombie.burn_in_daylight.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(false)), NATURAL_REGEN("natural_regen.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(false)), DEATH_MESSAGES("enhanced_deathmessages.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), ENHANCE_FURNACES("enhance_furnaces.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), USE_BLEEDING("bleeding.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), USE_POISON("infection.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), TASK_SPEED("operation_tick", EntryType.INTEGER, Configuration.CFiles.CONFIG, Integer.valueOf(1)), PIGMAN_PACK_SPAWN("pigman.spawn.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(3)), PIGMAN_PACK_MIN("pigman.spawn.pack.minimum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(1)), PIGMAN_PACK_MAX("pigman.spawn.pack.maximum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(2)), ZOMBIE_PACK_SPAWN("zombie.spawn.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(80)), ZOMBIE_PACK_MIN("zombie.spawn.pack.minimum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(1)), ZOMBIE_PACK_MAX("zombie.spawn.pack.maximum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(5)), GUARD_PACK_SPAWN("guard.spawn.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(2)), GUARD_PACK_MIN("guard.spawn.pack.minimum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(1)), GUARD_PACK_MAX("guard.spawn.pack.maximum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(1)), GIANT_PACK_SPAWN("giant.spawn.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(1)), GIANT_PACK_MIN("giant.spawn.pack.minimum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(1)), GIANT_REINFORCEMENT_MIN("giant.ability.reinforcement.minimum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(1)), GIANT_REINFORCEMENT_MAX("giant.ability.reinforcement.maximum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(6)), GIANT_REINFORCEMENT_CHANCE("giant.ability.reinforcement.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(30)), GIANT_PACK_MAX("giant.spawn.pack.maximum", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(1)), GIANT_INCLUDES("giant.spawn.biome.includes", EntryType.LIST, Configuration.CFiles.MOBS, new ArrayList(Arrays.asList(new String[] { "Mesa Plateau"}))), ZOMBIE_EXCLUDES("zombie.spawn.biome.excludes", EntryType.LIST, Configuration.CFiles.MOBS, new ArrayList(Arrays.asList(new String[] { "Mesa Plateau"}))), PIGMAN_EXCLUDES("pigman.spawn.biome.excludes", EntryType.LIST, Configuration.CFiles.MOBS, new ArrayList(Arrays.asList(new String[] { "Mesa Plateau"}))), GUARD_EXCLUDES("guard.spawn.biome.excludes", EntryType.LIST, Configuration.CFiles.MOBS, new ArrayList()), GENERAL_EXCLUDES("general.spawn.biome.excludes", EntryType.LIST, Configuration.CFiles.MOBS, new ArrayList(Arrays.asList(new String[] { "Sky", "Ocean", "Deep Ocean"}))), GUARD_NAME("guard.name", EntryType.STRING, Configuration.CFiles.MOBS, "MrTeePee"), ZOMBIE_HEALTH("zombie.attribute.health", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(20.0D)), ZOMBIE_SPEED("zombie.attribute.speed.no_target", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(0.23D)), ZOMBIE_SPEED_TARGET("zombie.attribute.speed.target_multiplier", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.5D)), ZOMBIE_KNOCKBACK_RESIST("zombie.attribute.knockback_resistance", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(0.0D)), ZOMBIE_DAMAGE("zombie.attribute.damage", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(3.0D)), PIGMAN_HEALTH("pigman.attribute.health", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.0D)), PIGMAN_SPEED("pigman.attribute.speed.no_target", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(0.21D)), PIGMAN_SPEED_TARGET("pigman.attribute.speed.target_multiplier", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.45D)), PIGMAN_KNOCKBACK_RESIST("pigman.attribute.knockback_resistance", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(0.0D)), PIGMAN_DAMAGE("pigman.attribute.damage", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(10.0D)), GIANT_HEALTH("giant.attribute.health", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(100.0D)), GIANT_SPEED("giant.attribute.speed.no_target", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(0.23D)), GIANT_SPEED_TARGET("giant.attribute.speed.target_multiplier", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.5D)), GIANT_JUMP_MULTIPLIER("giant.attribute.jump.multiplier", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(4.1D)), ZOMBIE_JUMP_MULTIPLIER("zombie.attribute.jump.multiplier", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.05D)), PIGMAN_JUMP_MULTIPLIER("pigman.attribute.jump.multiplier", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.05D)), GUARD_JUMP_MULTIPLIER("guard.attribute.jump.multiplier", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.05D)), GIANT_KNOCKBACK_RESIST("giant.attribute.knockback_resistance", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(0.0D)), GIANT_DAMAGE("giant.attribute.damage", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(12.0D)), GUARD_HEALTH("guard.attribute.health", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(20.0D)), GUARD_SPEED("guard.attribute.speed.no_target", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(0.23D)), GUARD_SPEED_TARGET("guard.attribute.speed.target_multiplier", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.0D)), GUARD_KNOCKBACK_RESIST("guard.attribute.knockback_resistance", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(0.0D)), GUARD_DAMAGE("guard.attribute.damage", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(3.0D)), BOW_SHARPENED_COMBAT_MOD("equipment.bow_sharpened.combat_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(5)), BOW_PRECISE_HEADSHOT_MOD("equipment.bow_precise.headshot_modifier", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(4)), GUARD_MELEE_ITEM("guard.equipment.melee.sword", EntryType.STRING, Configuration.CFiles.MOBS, "IRON_SWORD"), GUARD_BOOTS_ITEM("guard.equipment.boots", EntryType.STRING, Configuration.CFiles.MOBS, "CHAINMAIL_BOOTS"), GUARD_LEGGINGS_ITEM("guard.equipment.leggings", EntryType.STRING, Configuration.CFiles.MOBS, "CHAINMAIL_LEGGINGS"), GUARD_CHESTPLATE_ITEM("guard.equipment.chestplate", EntryType.STRING, Configuration.CFiles.MOBS, "AIR"), GUARD_HELMET_ITEM("guard.equipment.helmet", EntryType.STRING, Configuration.CFiles.MOBS, "GOLD_HELMET"), GUARD_ZOMBIE_DEATH("guard.become_zombie_on_death", EntryType.BOOLEAN, Configuration.CFiles.MOBS, Boolean.valueOf(true)), PIGMAN_MULTIPLY_DEATH("pigman.explode.adult.amount", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(4)), PIGMAN_EXPLODE_DEATH("pigman.explode.baby.magnitude", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.0D)), ZOMBIE_NORMAL_CHANCE("zombie.type.normal.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(80)), ZOMBIE_LEATHER_CHANCE("zombie.type.leather.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(10)), ZOMBIE_CHAIN_CHANCE("zombie.type.chain.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(5)), ZOMBIE_GOLD_CHANCE("zombie.type.gold.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(3)), ZOMBIE_IRON_CHANCE("zombie.type.iron.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(2)), ZOMBIE_CRAWLER_CHANCE("zombie.subtype.crawler.chance", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(20)), GIANT_SLOWNESS_RADIUS("giant.ability.stomp.slowness.radius", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(10)), GIANT_EXPLODE_MAGNITUDE("giant.ability.stomp.magnitude", EntryType.DOUBLE, Configuration.CFiles.MOBS, Double.valueOf(1.5D)), CHESTS("chests.enabled", EntryType.BOOLEAN, Configuration.CFiles.ADDONS, Boolean.valueOf(true)), CHEST_TYPES("types", EntryType.CONFIGURATION_SECTION, Configuration.CFiles.CHESTS, (Object) null), CHEST_LOCATIONS("locations", EntryType.CONFIGURATION_SECTION, Configuration.CFiles.CHESTS, (Object) null), CHEST_RESPAWN_TIME("chest.respawn_time", EntryType.INTEGER, Configuration.CFiles.EXTRAS, Integer.valueOf(300)), RANKS("ranks", EntryType.CONFIGURATION_SECTION, Configuration.CFiles.RANKS, (Object) null), SAVE_SPEED("save_delay", EntryType.INTEGER, Configuration.CFiles.CONFIG, Integer.valueOf(6000)), DEDICATED("dedicated-mode", EntryType.BOOLEAN, Configuration.CFiles.CONFIG, Boolean.valueOf(false)), NOTIFY_SAVE("notify_on_save", EntryType.BOOLEAN, Configuration.CFiles.CONFIG, Boolean.valueOf(true)), ZOMBIE_MIN_Z("zombie.spawn.limits.minimum_z", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(0)), ZOMBIE_MAX_Z("zombie.spawn.limits.maximum_z", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(0)), ZOMBIE_LIMITER("zombie.spawn.limits.enabled", EntryType.BOOLEAN, Configuration.CFiles.MOBS, Boolean.valueOf(false)), PIGMAN_MIN_Z("pigman.spawn.limits.minimum_z", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(0)), PIGMAN_MAX_Z("pigman.spawn.limits.maximum_z", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(0)), PIGMAN_LIMITER("pigman.spawn.limits.enabled", EntryType.BOOLEAN, Configuration.CFiles.MOBS, Boolean.valueOf(false)), GIANT_MIN_Z("giant.spawn.limits.minimum_z", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(0)), GIANT_MAX_Z("giant.spawn.limits.maximum_z", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(0)), GIANT_LIMITER("giant.spawn.limits.enabled", EntryType.BOOLEAN, Configuration.CFiles.MOBS, Boolean.valueOf(false)), GUARD_MIN_Z("guard.spawn.limits.minimum_z", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(0)), GUARD_MAX_Z("guard.spawn.limits.maximum_z", EntryType.INTEGER, Configuration.CFiles.MOBS, Integer.valueOf(0)), GUARD_LIMITER("guard.spawn.limits.enabled", EntryType.BOOLEAN, Configuration.CFiles.MOBS, Boolean.valueOf(false)), FIRST_RUN("first_run", EntryType.BOOLEAN, Configuration.CFiles.CONFIG, Boolean.valueOf(true)), AIRDROP_BREAK("airdrop.break_blocks", EntryType.BOOLEAN, Configuration.CFiles.EXTRAS, Boolean.valueOf(true)), AIRDROP_WRECKAGE("airdrop.create_wreckage", EntryType.BOOLEAN, Configuration.CFiles.EXTRAS, Boolean.valueOf(true)), AIRDROP_FIRE("airdrop.set_fires", EntryType.BOOLEAN, Configuration.CFiles.EXTRAS, Boolean.valueOf(false)), AIRDROP_EXPLOSION_MAGNITUDE("airdrop.explosion_magnitude", EntryType.DOUBLE, Configuration.CFiles.EXTRAS, Double.valueOf(2.0D)), AIRDROP_CHESTS("airdrop.chest_types", EntryType.LIST, Configuration.CFiles.EXTRAS, Arrays.asList(new String[] { "Supplies - Rare", "Weaponry - Rare", "Armor - Rare", "Food - Rare"})), RANK_CARRYOVER("spawnkit.hierarchical", EntryType.BOOLEAN, Configuration.CFiles.EXTRAS, Boolean.valueOf(false));

    private final String key;
    private final EntryType type;
    private final Configuration.CFiles file;
    private final Object defaultValue;

    private ConfigEntries(String key, EntryType type, Configuration.CFiles file, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.type = type;
        this.file = file;
    }

    public String getKey() {
        return this.key;
    }

    public Configuration.CFiles getFile() {
        return this.file;
    }

    public EntryType getType() {
        return this.type;
    }

    public Object getValue() {
        switch (ConfigEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[this.type.ordinal()]) {
        case 1:
            return !this.file.getFile().contains(this.key) ? this.defaultValue : this.file.getFile().getString(this.key);

        case 2:
            return !this.file.getFile().contains(this.key) ? this.defaultValue : Integer.valueOf(this.file.getFile().getInt(this.key));

        case 3:
            return !this.file.getFile().contains(this.key) ? this.defaultValue : Boolean.valueOf(this.file.getFile().getBoolean(this.key));

        case 4:
            return !this.file.getFile().contains(this.key) ? this.defaultValue : this.file.getFile().getItemStack(this.key);

        case 5:
            return !this.file.getFile().contains(this.key) ? this.defaultValue : Double.valueOf(this.file.getFile().getDouble(this.key));

        case 6:
            return !this.file.getFile().contains(this.key) ? this.defaultValue : this.file.getFile().getList(this.key);

        case 7:
            return !this.file.getFile().isConfigurationSection(this.key) ? this.makeDefaultSection() : this.file.getFile().getConfigurationSection(this.key);

        default:
            return null;
        }
    }

    private ConfigurationSection makeDefaultSection() {
        ConfigurationSection section = this.file.getFile().createSection(this.key);

        if (this == ConfigEntries.IMMUNE) {
            section.set("name", "Building Immunity");
            section.set("description", "Heal yourself from infection 10 times.");
            section.set("requirements.kills.zombie", Integer.valueOf(0));
            section.set("requirements.kills.pigman", Integer.valueOf(0));
            section.set("requirements.kills.player", Integer.valueOf(0));
            section.set("requirements.kills.giant", Integer.valueOf(0));
            section.set("requirements.heals.self.bleeding", Integer.valueOf(0));
            section.set("requirements.heals.self.infection", Integer.valueOf(10));
            section.set("requirements.heals.other", Integer.valueOf(0));
            section.set("requirements.drinks", Integer.valueOf(0));
            section.set("requirements.revive.other", Integer.valueOf(0));
            section.set("requirements.revive.self", Integer.valueOf(0));
            section.set("requirements.deaths", Integer.valueOf(0));
            section.set("requirements.deathstate.zombie", Integer.valueOf(0));
            section.set("requirements.deathstate.ghost", Integer.valueOf(0));
            section.set("requirements.friends", Integer.valueOf(0));
        } else if (this == ConfigEntries.FRIENDLY) {
            section.set("name", "Making Friends");
            section.set("description", "make a friend");
            section.set("requirements.kills.zombie", Integer.valueOf(0));
            section.set("requirements.kills.pigman", Integer.valueOf(0));
            section.set("requirements.kills.player", Integer.valueOf(0));
            section.set("requirements.kills.giant", Integer.valueOf(0));
            section.set("requirements.heals.self.bleeding", Integer.valueOf(0));
            section.set("requirements.heals.self.infection", Integer.valueOf(0));
            section.set("requirements.heals.other", Integer.valueOf(0));
            section.set("requirements.drinks", Integer.valueOf(0));
            section.set("requirements.revive.other", Integer.valueOf(0));
            section.set("requirements.revive.self", Integer.valueOf(0));
            section.set("requirements.deaths", Integer.valueOf(0));
            section.set("requirements.deathstate.zombie", Integer.valueOf(0));
            section.set("requirements.deathstate.ghost", Integer.valueOf(0));
            section.set("requirements.friends", Integer.valueOf(1));
        } else if (this == ConfigEntries.THIRSTY) {
            section.set("name", "Staying Hydrated");
            section.set("description", "drink 20 bottles of water");
            section.set("requirements.kills.zombie", Integer.valueOf(0));
            section.set("requirements.kills.pigman", Integer.valueOf(0));
            section.set("requirements.kills.player", Integer.valueOf(0));
            section.set("requirements.kills.giant", Integer.valueOf(0));
            section.set("requirements.heals.self.bleeding", Integer.valueOf(0));
            section.set("requirements.heals.self.infection", Integer.valueOf(0));
            section.set("requirements.heals.other", Integer.valueOf(0));
            section.set("requirements.drinks", Integer.valueOf(20));
            section.set("requirements.revive.other", Integer.valueOf(0));
            section.set("requirements.revive.self", Integer.valueOf(0));
            section.set("requirements.deaths", Integer.valueOf(0));
            section.set("requirements.deathstate.zombie", Integer.valueOf(0));
            section.set("requirements.deathstate.ghost", Integer.valueOf(0));
            section.set("requirements.friends", Integer.valueOf(0));
        } else if (this == ConfigEntries.LIFECYCLE) {
            section.set("name", "The Circle of Life");
            section.set("description", "reach the end of your life");
            section.set("requirements.kills.zombie", Integer.valueOf(0));
            section.set("requirements.kills.pigman", Integer.valueOf(0));
            section.set("requirements.kills.player", Integer.valueOf(0));
            section.set("requirements.kills.giant", Integer.valueOf(0));
            section.set("requirements.heals.self.bleeding", Integer.valueOf(0));
            section.set("requirements.heals.self.infection", Integer.valueOf(0));
            section.set("requirements.heals.other", Integer.valueOf(0));
            section.set("requirements.drinks", Integer.valueOf(0));
            section.set("requirements.revive.other", Integer.valueOf(0));
            section.set("requirements.revive.self", Integer.valueOf(0));
            section.set("requirements.deaths", Integer.valueOf(1));
            section.set("requirements.deathstate.zombie", Integer.valueOf(0));
            section.set("requirements.deathstate.ghost", Integer.valueOf(0));
            section.set("requirements.friends", Integer.valueOf(0));
        } else if (this == ConfigEntries.RANKS) {
            section.set("0.chat_prefix", "");
            section.set("0.equipment.boots", (Object) null);
            section.set("0.equipment.helmet", (Object) null);
            section.set("0.equipment.leggings", (Object) null);
            section.set("0.equipment.chestplate", EquipmentState.BROKEN.applyTo(ItemUtilities.getInstance().getTagItem(ItemTag.STARTER_CHESTPLATE, 1)));
            section.set("0.equipment.inventory", Arrays.asList(new ItemStack[] { EquipmentState.DEVASTATED.applyTo(ItemUtilities.getInstance().getTagItem(ItemTag.STARTER_SWORD, 1)), new ItemStack(Material.POTION)}));
            section.set("100.chat_prefix", "");
            section.set("100.equipment.boots", (Object) null);
            section.set("100.equipment.helmet", (Object) null);
            section.set("100.equipment.leggings", (Object) null);
            section.set("100.equipment.chestplate", (Object) null);
            section.set("100.equipment.inventory", new ArrayList());
        }

        return section;
    }

    public static void loadImportantDefaults() {
        BlockFace[] section = AirDrop.directions;
        int i = section.length;

        for (int j = 0; j < i; ++j) {
            BlockFace face = section[j];
            File schematicFolder = new File(MyZ.instance.getDataFolder().getAbsolutePath() + File.separator + "schematics" + File.separator + "airdrop" + File.separator + face.name().toLowerCase());

            if (!schematicFolder.exists()) {
                schematicFolder.mkdirs();
            }
        }

        if (((Boolean) ConfigEntries.FIRST_RUN.getValue()).booleanValue()) {
            ConfigurationSection configurationsection = Configuration.CFiles.CHESTS.getFile().getConfigurationSection("types");

            setLootset(configurationsection, "Weaponry - Common", UUID.randomUUID().toString(), 8, 1, 1, EquipmentState.DULL.applyTo(new ItemStack(Material.WOOD_SWORD)));
            setLootset(configurationsection, "Weaponry - Common", UUID.randomUUID().toString(), 30, 1, 1, EquipmentState.DULL.applyTo(damage(Material.WOOD_SWORD, 2.0D)));
            setLootset(configurationsection, "Weaponry - Common", UUID.randomUUID().toString(), 10, 1, 1, EquipmentState.WEAKENED.applyTo(damage(Material.STONE_SWORD, 2.0D)));
            setLootset(configurationsection, "Weaponry - Common", UUID.randomUUID().toString(), 13, 1, 1, EquipmentState.DULL.applyTo(new ItemStack(Material.WOOD_SWORD)));
            setLootset(configurationsection, "Weaponry - Uncommon", UUID.randomUUID().toString(), 13, 1, 1, EquipmentState.SHARPENED.applyTo(new ItemStack(Material.STONE_SWORD)));
            setLootset(configurationsection, "Weaponry - Uncommon", UUID.randomUUID().toString(), 9, 1, 1, EquipmentState.BROKEN.applyTo(damage(Material.IRON_SWORD, 2.0D)));
            setLootset(configurationsection, "Weaponry - Uncommon", UUID.randomUUID().toString(), 34, 1, 1, EquipmentState.BROKEN.applyTo(damage(Material.IRON_SWORD, 3.0D)));
            setLootset(configurationsection, "Weaponry - Uncommon", UUID.randomUUID().toString(), 18, 1, 1, EquipmentState.BROKEN.applyTo(damage(Material.BOW, 5.0D)));
            setLootset(configurationsection, "Weaponry - Rare", UUID.randomUUID().toString(), 8, 1, 1, EquipmentState.TEMPERED.applyTo(new ItemStack(Material.STONE_SWORD)));
            setLootset(configurationsection, "Weaponry - Rare", UUID.randomUUID().toString(), 6, 1, 1, EquipmentState.SHARPENED.applyTo(damage(Material.IRON_SWORD, 4.0D)));
            setLootset(configurationsection, "Weaponry - Rare", UUID.randomUUID().toString(), 7, 1, 1, EquipmentState.SLACK.applyTo(new ItemStack(Material.BOW)));
            setLootset(configurationsection, "Weaponry - Rare", UUID.randomUUID().toString(), 23, 1, 1, new ItemStack(Material.IRON_SWORD));
            setLootset(configurationsection, "Armor - Common", UUID.randomUUID().toString(), 9, 1, 1, EquipmentState.BROKEN.applyTo(new ItemStack(Material.LEATHER_CHESTPLATE)));
            setLootset(configurationsection, "Armor - Common", UUID.randomUUID().toString(), 13, 1, 1, EquipmentState.BROKEN.applyTo(damage(Material.LEATHER_HELMET, 2.0D)));
            setLootset(configurationsection, "Armor - Common", UUID.randomUUID().toString(), 11, 1, 1, EquipmentState.WEAKENED.applyTo(damage(Material.LEATHER_LEGGINGS, 1.4D)));
            setLootset(configurationsection, "Armor - Common", UUID.randomUUID().toString(), 10, 1, 1, EquipmentState.BROKEN.applyTo(damage(Material.CHAINMAIL_HELMET, 2.0D)));
            setLootset(configurationsection, "Armor - Common", UUID.randomUUID().toString(), 12, 1, 1, EquipmentState.WEAKENED.applyTo(damage(Material.CHAINMAIL_BOOTS, 1.2D)));
            setLootset(configurationsection, "Armor - Common", UUID.randomUUID().toString(), 13, 1, 1, damage(Material.LEATHER_HELMET, 2.0D));
            setLootset(configurationsection, "Armor - Common", UUID.randomUUID().toString(), 10, 1, 1, damage(Material.LEATHER_BOOTS, 1.7D));
            setLootset(configurationsection, "Armor - Uncommon", UUID.randomUUID().toString(), 13, 1, 1, damage(Material.CHAINMAIL_HELMET, 1.1D));
            setLootset(configurationsection, "Armor - Uncommon", UUID.randomUUID().toString(), 9, 1, 1, damage(Material.CHAINMAIL_LEGGINGS, 1.8D));
            setLootset(configurationsection, "Armor - Uncommon", UUID.randomUUID().toString(), 20, 1, 1, EquipmentState.FORTIFIED.applyTo(damage(Material.LEATHER_LEGGINGS, 1.1D)));
            setLootset(configurationsection, "Armor - Uncommon", UUID.randomUUID().toString(), 17, 1, 1, EquipmentState.REINFORCED.applyTo(damage(Material.LEATHER_CHESTPLATE, 1.1D)));
            setLootset(configurationsection, "Armor - Uncommon", UUID.randomUUID().toString(), 7, 1, 1, EquipmentState.BROKEN.applyTo(damage(Material.IRON_HELMET, 1.7D)));
            setLootset(configurationsection, "Armor - Uncommon", UUID.randomUUID().toString(), 8, 1, 1, EquipmentState.DEVASTATED.applyTo(damage(Material.IRON_CHESTPLATE, 3.1D)));
            setLootset(configurationsection, "Armor - Uncommon", UUID.randomUUID().toString(), 4, 1, 1, EquipmentState.BROKEN.applyTo(damage(Material.IRON_LEGGINGS, 3.0D)));
            setLootset(configurationsection, "Armor - Uncommon", UUID.randomUUID().toString(), 15, 1, 1, EquipmentState.REINFORCED.applyTo(damage(Material.CHAINMAIL_LEGGINGS, 4.1D)));
            setLootset(configurationsection, "Armor - Rare", UUID.randomUUID().toString(), 17, 1, 1, EquipmentState.ORNATE.applyTo(damage(Material.CHAINMAIL_LEGGINGS, 1.6D)));
            setLootset(configurationsection, "Armor - Rare", UUID.randomUUID().toString(), 10, 1, 1, EquipmentState.ORNATE.applyTo(damage(Material.CHAINMAIL_HELMET, 1.5D)));
            setLootset(configurationsection, "Armor - Rare", UUID.randomUUID().toString(), 15, 1, 1, EquipmentState.WEAKENED.applyTo(damage(Material.IRON_LEGGINGS, 1.2D)));
            setLootset(configurationsection, "Armor - Rare", UUID.randomUUID().toString(), 8, 1, 1, damage(Material.IRON_CHESTPLATE, 1.6D));
            setLootset(configurationsection, "Armor - Rare", UUID.randomUUID().toString(), 16, 1, 1, EquipmentState.ORNATE.applyTo(damage(Material.CHAINMAIL_CHESTPLATE, 1.1D)));
            setLootset(configurationsection, "Armor - Rare", UUID.randomUUID().toString(), 13, 1, 1, EquipmentState.WEAKENED.applyTo(damage(Material.IRON_HELMET, 1.2D)));
            setLootset(configurationsection, "Armor - Rare", UUID.randomUUID().toString(), 11, 1, 1, damage(Material.IRON_BOOTS, 1.3D));
            setLootset(configurationsection, "Supplies - Common", UUID.randomUUID().toString(), 15, 1, 1, ItemUtilities.getInstance().getTagItem(ItemTag.WARM_WATER, 1));
            setLootset(configurationsection, "Supplies - Common", UUID.randomUUID().toString(), 13, 1, 1, ItemUtilities.getInstance().getTagItem(ItemTag.SALTY_WATER, 1));
            setLootset(configurationsection, "Supplies - Common", UUID.randomUUID().toString(), 11, 1, 4, new ItemStack(Material.STICK));
            setLootset(configurationsection, "Supplies - Common", UUID.randomUUID().toString(), 6, 1, 2, new ItemStack(Material.COBBLESTONE));
            setLootset(configurationsection, "Supplies - Common", UUID.randomUUID().toString(), 9, 1, 1, new ItemStack(Material.LEATHER));
            setLootset(configurationsection, "Supplies - Common", UUID.randomUUID().toString(), 14, 1, 1, ItemUtilities.getInstance().getTagItem(ItemTag.MURKY_WATER, 1));
            setLootset(configurationsection, "Supplies - Uncommon", UUID.randomUUID().toString(), 15, 1, 1, new ItemStack(Material.POTION));
            setLootset(configurationsection, "Supplies - Uncommon", UUID.randomUUID().toString(), 6, 1, 3, ItemUtilities.getInstance().getTagItem(ItemTag.CHAIN, 1));
            setLootset(configurationsection, "Supplies - Uncommon", UUID.randomUUID().toString(), 4, 1, 1, new ItemStack(Material.GOLD_INGOT));
            setLootset(configurationsection, "Supplies - Uncommon", UUID.randomUUID().toString(), 8, 1, 6, new ItemStack(Material.ARROW));
            setLootset(configurationsection, "Supplies - Uncommon", UUID.randomUUID().toString(), 3, 1, 1, new ItemStack(Material.ENDER_PEARL));
            setLootset(configurationsection, "Supplies - Uncommon", UUID.randomUUID().toString(), 11, 1, 1, new ItemStack(Material.SNOW_BALL));
            setLootset(configurationsection, "Supplies - Uncommon", UUID.randomUUID().toString(), 12, 1, 1, new ItemStack(Material.MILK_BUCKET));
            setLootset(configurationsection, "Supplies - Rare", UUID.randomUUID().toString(), 11, 1, 1, ItemUtilities.getInstance().getTagItem(ItemTag.COLD_WATER, 1));
            setLootset(configurationsection, "Supplies - Rare", UUID.randomUUID().toString(), 7, 1, 1, new ItemStack(Material.IRON_INGOT));
            setLootset(configurationsection, "Supplies - Rare", UUID.randomUUID().toString(), 8, 2, 5, ItemUtilities.getInstance().getTagItem(ItemTag.CHAIN, 1));
            setLootset(configurationsection, "Supplies - Rare", UUID.randomUUID().toString(), 11, 5, 13, new ItemStack(Material.ARROW));
            setLootset(configurationsection, "Supplies - Rare", UUID.randomUUID().toString(), 8, 1, 1, new ItemStack(Material.ENDER_PEARL));
            setLootset(configurationsection, "Supplies - Rare", UUID.randomUUID().toString(), 8, 1, 1, new ItemStack(Material.SKULL_ITEM, 1, (short) 2));
            setLootset(configurationsection, "Supplies - Rare", UUID.randomUUID().toString(), 6, 1, 1, EquipmentState.GRAPPLE_WEAK.applyTo(ItemUtilities.getInstance().getTagItem(ItemTag.GRAPPLE, 1)));
            setLootset(configurationsection, "Food - Common", UUID.randomUUID().toString(), 11, 1, 2, new ItemStack(Material.ROTTEN_FLESH));
            setLootset(configurationsection, "Food - Common", UUID.randomUUID().toString(), 8, 1, 1, new ItemStack(Material.MELON));
            setLootset(configurationsection, "Food - Common", UUID.randomUUID().toString(), 9, 1, 1, new ItemStack(Material.COOKIE));
            setLootset(configurationsection, "Food - Common", UUID.randomUUID().toString(), 12, 1, 2, new ItemStack(Material.WHEAT));
            setLootset(configurationsection, "Food - Common", UUID.randomUUID().toString(), 8, 1, 2, new ItemStack(Material.COCOA));
            setLootset(configurationsection, "Food - Common", UUID.randomUUID().toString(), 7, 1, 1, new ItemStack(Material.BOWL));
            setLootset(configurationsection, "Food - Common", UUID.randomUUID().toString(), 11, 1, 1, new ItemStack(Material.RED_MUSHROOM));
            setLootset(configurationsection, "Food - Common", UUID.randomUUID().toString(), 15, 1, 1, new ItemStack(Material.BROWN_MUSHROOM));
            setLootset(configurationsection, "Food - Uncommon", UUID.randomUUID().toString(), 11, 1, 2, new ItemStack(Material.RAW_CHICKEN));
            setLootset(configurationsection, "Food - Uncommon", UUID.randomUUID().toString(), 9, 1, 1, new ItemStack(Material.MUSHROOM_SOUP));
            setLootset(configurationsection, "Food - Uncommon", UUID.randomUUID().toString(), 13, 1, 2, new ItemStack(Material.APPLE));
            setLootset(configurationsection, "Food - Uncommon", UUID.randomUUID().toString(), 15, 1, 2, new ItemStack(Material.BAKED_POTATO));
            setLootset(configurationsection, "Food - Uncommon", UUID.randomUUID().toString(), 4, 1, 1, new ItemStack(Material.COOKED_BEEF));
            setLootset(configurationsection, "Food - Uncommon", UUID.randomUUID().toString(), 8, 1, 3, new ItemStack(Material.COOKED_FISH));
            setLootset(configurationsection, "Food - Rare", UUID.randomUUID().toString(), 11, 3, 6, new ItemStack(Material.WHEAT));
            setLootset(configurationsection, "Food - Rare", UUID.randomUUID().toString(), 7, 2, 4, new ItemStack(Material.COOKED_FISH));
            setLootset(configurationsection, "Food - Rare", UUID.randomUUID().toString(), 13, 1, 2, new ItemStack(Material.COOKED_CHICKEN));
            setLootset(configurationsection, "Food - Rare", UUID.randomUUID().toString(), 16, 1, 3, new ItemStack(Material.PUMPKIN_PIE));
            setLootset(configurationsection, "Food - Rare", UUID.randomUUID().toString(), 13, 4, 8, new ItemStack(Material.COOKIE));
            setLootset(configurationsection, "Food - Rare", UUID.randomUUID().toString(), 6, 2, 4, new ItemStack(Material.MUSHROOM_SOUP));
            FileUtilities.set(ConfigEntries.FIRST_RUN.getKey(), Boolean.valueOf(false), ConfigEntries.FIRST_RUN.getFile(), true);
        }
    }

    private static ItemStack damage(Material material, double percent) {
        return new ItemStack(material, 1, (short) ((int) ((double) material.getMaxDurability() / percent)));
    }

    private static void setLootset(ConfigurationSection section, String name, String uid, int probability, int min, int max, ItemStack item) {
        section.set(name + "." + uid + ".probability", Integer.valueOf(probability));
        section.set(name + "." + uid + ".amount_minimum", Integer.valueOf(min));
        section.set(name + "." + uid + ".amount_maximum", Integer.valueOf(max));
        section.set(name + "." + uid + ".item", item);
    }

    public static void loadCrackshot() {
        if (MyZ.instance.getServer().getPluginManager().isPluginEnabled("CrackShot")) {
            FileConfiguration file = Configuration.CFiles.EXTRAS.getFile();
            CSUtility crackshot = new CSUtility();
            Iterator iterator = crackshot.getHandle().parentlist.values().iterator();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (!file.isSet("visibility.player.crackshot." + key)) {
                    file.set("visibility.player.crackshot." + key + ".shoot", Integer.valueOf(7));
                    file.set("visibility.player.crackshot." + key + ".reload", Integer.valueOf(2));
                }
            }
        }

    }

    public static int getCrackshot(String name, String action) {
        return Configuration.CFiles.EXTRAS.getFile().getInt("visibility.player.crackshot." + name + "." + action);
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$utilities$configuration$EntryType = new int[EntryType.values().length];

        static {
            try {
                ConfigEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                ConfigEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.INTEGER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                ConfigEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                ConfigEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.ITEMSTACK.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                ConfigEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.DOUBLE.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                ConfigEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.LIST.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                ConfigEntries.SyntheticClass_1.$SwitchMap$jordan$sicherman$utilities$configuration$EntryType[EntryType.CONFIGURATION_SECTION.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

        }
    }
}
