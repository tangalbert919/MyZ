/**
 * 
 */
package jordan.sicherman.utilities.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.utilities.configuration.Configuration.CFiles;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public enum ConfigEntries {

	UPDATE("auto-update", EntryType.BOOLEAN, CFiles.CONFIG, true), SQL_USERNAME("sql.username", EntryType.STRING, CFiles.MYSQL, "root"), SQL_PASSWORD(
			"sql.password", EntryType.STRING, CFiles.MYSQL, "alpine"), SQL_HOST("sql.host", EntryType.STRING, CFiles.MYSQL, "127.0.0.1"), SQL_PORT(
			"sql.port", EntryType.INTEGER, CFiles.MYSQL, 3306), SQL_DATABASE("sql.database", EntryType.STRING, CFiles.MYSQL, "test"), LANGUAGE(
			"language", EntryType.STRING, CFiles.CONFIG, "en_CA"), WORLDS("worlds", EntryType.LIST, CFiles.CONFIG, Arrays.asList("world")), VISIBILITY_WALKING(
			"visibility.player.walking", EntryType.INTEGER, CFiles.EXTRAS, 3), VISIBILITY_SPRINTING("visibility.player.sprinting",
			EntryType.INTEGER, CFiles.EXTRAS, 7), VISIBILITY_JUMPING("visibility.player.airborne", EntryType.INTEGER, CFiles.EXTRAS, 2), VISIBILITY_ACTIVATE_REDSTONE(
			"visibility.player.activate_redstone", EntryType.INTEGER, CFiles.EXTRAS, 3), VISIBILITY_TAKE_DAMAGE(
			"visibility.player.take_damage", EntryType.INTEGER, CFiles.EXTRAS, 2), VISIBILITY_SHOOTING("visibility.player.shooting",
			EntryType.INTEGER, CFiles.EXTRAS, 1), VISIBILITY_SNEAKING("visibility.player.sneaking", EntryType.INTEGER, CFiles.EXTRAS, -2), VISIBILITY_CARRYING_FLESH(
			"visibility.player.holding_flesh", EntryType.INTEGER, CFiles.EXTRAS, -1), VISIBILITY_WEARING_HEAD(
			"visibility.player.wearing_zombie_head", EntryType.INTEGER, CFiles.EXTRAS, -3), VISIBILITY_EXPLOSION(
			"visibility.environment.explosion", EntryType.INTEGER, CFiles.EXTRAS, 6), VISIBILITY_ARROW_LANDED(
			"visibility.environment.arrow_landed", EntryType.INTEGER, CFiles.EXTRAS, 4), VISIBILITY_SNOWBALL(
			"visibility.environment.snowball_landed", EntryType.INTEGER, CFiles.EXTRAS, 5), VISIBILITY_BASE("visibility.player.base",
			EntryType.INTEGER, CFiles.EXTRAS, 5), VISIBILITY_BLEEDING("visibility.player.bleeding", EntryType.INTEGER, CFiles.EXTRAS, 2), THIRST_MAX_DEFAULT(
			"thirst.max.default", EntryType.INTEGER, CFiles.EXTRAS, 20), THIRST_DECAY_BEACH("thirst.decay.beach", EntryType.INTEGER,
			CFiles.EXTRAS, 20), THIRST_DECAY_PLAINS("thirst.decay.plains", EntryType.INTEGER, CFiles.EXTRAS, 38), THIRST_DECAY_TAIGA(
			"thirst.decay.taiga", EntryType.INTEGER, CFiles.EXTRAS, 40), THIRST_DECAY_DESERT("thirst.decay.desert", EntryType.INTEGER,
			CFiles.EXTRAS, 23), THIRST_DECAY_OCEAN("thirst.decay.ocean", EntryType.INTEGER, CFiles.EXTRAS, -120), THIRST_DECAY_JUNGLE(
			"thirst.decay.jungle", EntryType.INTEGER, CFiles.EXTRAS, 31), THIRST_DECAY_MESA("thirst.decay.mesa", EntryType.INTEGER,
			CFiles.EXTRAS, 30), THIRST_DECAY_MUSHROOM("thirst.decay.mushroom", EntryType.INTEGER, CFiles.EXTRAS, 50), THIRST_DECAY_SWAMP(
			"thirst.decay.swamp", EntryType.INTEGER, CFiles.EXTRAS, 49), CHAT_RADIUS("chat.local.radius", EntryType.INTEGER, CFiles.EXTRAS,
			20), VULNERABLE_TIME("death.vulnerable_time", EntryType.INTEGER, CFiles.EXTRAS, 300), REVIVE_TIME("death.revive_time",
			EntryType.INTEGER, CFiles.EXTRAS, 10), UNDEAD_TIME("death.transition_time", EntryType.INTEGER, CFiles.EXTRAS, 60), VISIBILITY_CHAT(
			"visibility.player.chat", EntryType.INTEGER, CFiles.EXTRAS, 2), VISIBILITY_ON_FIRE("visibility.player.on_fire",
			EntryType.INTEGER, CFiles.EXTRAS, 7), SPAWN_POINTS("spawnpoints", EntryType.LIST, CFiles.SPAWNLOCATIONS,
			new ArrayList<String>()), HOME_SPAWN("home", EntryType.STRING, CFiles.SPAWNLOCATIONS, ""), BLEED_DELAY("bleed.damage_delay",
			EntryType.INTEGER, CFiles.EXTRAS, 42), INFECTION_DELAY("infection.damage_delay", EntryType.INTEGER, CFiles.EXTRAS, 38), BLEED_DAMAGE(
			"bleed.damage_amount", EntryType.DOUBLE, CFiles.EXTRAS, 1.1), INFECTION_DAMAGE("infection.damage_amount", EntryType.DOUBLE,
			CFiles.EXTRAS, 1.3), CHAT_FORMATTING("chat.autocorrect", EntryType.BOOLEAN, CFiles.EXTRAS, true), DEVASTATED_MOD(
			"equipment.devastated_modifier", EntryType.INTEGER, CFiles.EXTRAS, 50), SHATTERED_MOD("equipment.shattered_modifier",
			EntryType.INTEGER, CFiles.EXTRAS, 40), BROKEN_MOD("equipment.broken_modifier", EntryType.INTEGER, CFiles.EXTRAS, 70), SLACK_MOD(
			"equipment.slack_modifier", EntryType.INTEGER, CFiles.EXTRAS, 90), REINFORCED_MOD("equipment.reinforced_modifier",
			EntryType.INTEGER, CFiles.EXTRAS, 110), CRACKED_MOD("equipment.cracked_modifier", EntryType.INTEGER, CFiles.EXTRAS, 80), PRECISE_MOD(
			"equipment.precise_modifier", EntryType.INTEGER, CFiles.EXTRAS, 120), BOW_SHARPENED_MOD(
			"equipment.bow_sharpened.ranged_modifier", EntryType.INTEGER, CFiles.EXTRAS, 150), WEAKENED_MOD("equipment.weakened_modifier",
			EntryType.INTEGER, CFiles.EXTRAS, 85), DULL_MOD("equipment.dull_modifier", EntryType.INTEGER, CFiles.EXTRAS, 90), SHARPENED_MOD(
			"equipment.sharpened_modifier", EntryType.INTEGER, CFiles.EXTRAS, 130), TEMPERED_MOD("equipment.tempered_modifier",
			EntryType.INTEGER, CFiles.EXTRAS, 150), FORTIFIED_MOD("equipment.fortified_modifier", EntryType.INTEGER, CFiles.EXTRAS, 120), ORNATE_MOD(
			"equipment.ornate_modifier", EntryType.INTEGER, CFiles.EXTRAS, 150), USE_ENHANCED_ANVILS("enhance_envils.enabled",
			EntryType.BOOLEAN, CFiles.ADDONS, true), UPGRADE_AMOUNT("equipment.upgrade.material_cost", EntryType.INTEGER, CFiles.EXTRAS, 6), ENGINEER_RECIPES(
			"recipes", EntryType.CONFIGURATION_SECTION, CFiles.ENGINEER, null), FRIENDLY("friends", EntryType.CONFIGURATION_SECTION,
			CFiles.ACHIEVEMENTS, null), THIRSTY("thirsty", EntryType.CONFIGURATION_SECTION, CFiles.ACHIEVEMENTS, null), IMMUNE("immune",
			EntryType.CONFIGURATION_SECTION, CFiles.ACHIEVEMENTS, null), LIFECYCLE("lifecycle", EntryType.CONFIGURATION_SECTION,
			CFiles.ACHIEVEMENTS, null), BANDAGE_HEAL("heal.bandage.amount", EntryType.DOUBLE, CFiles.EXTRAS, 1.0), USE_THIRST(
			"thirst.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), ENHANCE_FOOD("enhanced_food.enabled", EntryType.BOOLEAN,
			CFiles.ADDONS, true), PREVENT_WITHERING("wither_damage.disabled", EntryType.BOOLEAN, CFiles.ADDONS, true), NO_ZOMBIE_FALL(
			"zombie.falldamage.disabled", EntryType.BOOLEAN, CFiles.ADDONS, true), ZOMBIES_BURN("zombie.burn_in_daylight.enabled",
			EntryType.BOOLEAN, CFiles.ADDONS, false), NATURAL_REGEN("natural_regen.enabled", EntryType.BOOLEAN, CFiles.ADDONS, false), EXPLOSIVE_PEARLS(
			"enderpearls_explode.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), LOCAL_CHAT("chat.local.enabled", EntryType.BOOLEAN,
			CFiles.ADDONS, true), RADIO_CHAT("chat.radio.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), PRIVATE_CHAT(
			"chat.private.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), DEATH_MESSAGES("enhanced_deathmessages.enabled",
			EntryType.BOOLEAN, CFiles.ADDONS, true), BECOME_GHOST("death.become_ghost.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), BECOME_ZOMBIE(
			"death.become_zombie.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), ENHANCE_FURNACES("enhance_furnaces.enabled",
			EntryType.BOOLEAN, CFiles.ADDONS, true), USE_BANDAGES("bandages.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), USE_HEALING(
			"use_healing.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), ALLOW_REVIVALS("reviving.enabled", EntryType.BOOLEAN,
			CFiles.ADDONS, true), USE_VISIBILITY("visibility.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), USE_BLEEDING(
			"bleeding.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), USE_POISON("infection.enabled", EntryType.BOOLEAN, CFiles.ADDONS,
			true), USE_TEMP("temperature.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), TASK_SPEED("operation_tick", EntryType.INTEGER,
			CFiles.CONFIG, 1), DEFAULT_TEMPERATURE("temperature.default", EntryType.DOUBLE, CFiles.EXTRAS, 37.0), TEMPERATURE_ICY(
			"temperature.biome.frozen", EntryType.DOUBLE, CFiles.EXTRAS, 31.0), TEMPERATURE_COLD("temperature.biome.cold",
			EntryType.DOUBLE, CFiles.EXTRAS, 35.5), TEMPERATURE_MEDIUM("temperature.biome.medium", EntryType.DOUBLE, CFiles.EXTRAS, 37.0), TEMPERATURE_WARM(
			"temperature.biome.warm", EntryType.DOUBLE, CFiles.EXTRAS, 39.9), TEMPERATURE_OCEAN("temperature.biome.ocean",
			EntryType.DOUBLE, CFiles.EXTRAS, 30.5), THIRSTY_THRESHOLD("temperature.modify.thirst.threshold", EntryType.DOUBLE,
			CFiles.EXTRAS, 3), HEATSTROKE_3_THRESHOLD("temperature.threshold.heatstroke.3", EntryType.DOUBLE, CFiles.EXTRAS, 42.7), HEATSTROKE_2_THRESHOLD(
			"temperature.threshold.heatstroke.2", EntryType.DOUBLE, CFiles.EXTRAS, 40.6), HEATSTROKE_1_THRESHOLD(
			"temperature.threshold.heatstroke.1", EntryType.DOUBLE, CFiles.EXTRAS, 39.2), SWEATING_THRESHOLD(
			"temperature.threshold.sweating", EntryType.DOUBLE, CFiles.EXTRAS, 38.2), SHIVERING_THRESHOLD(
			"temperature.threshold.shivering", EntryType.DOUBLE, CFiles.EXTRAS, 35.8), HYPOTHERMIA_1_THRESHOLD(
			"temperature.threshold.hypothermia.1", EntryType.DOUBLE, CFiles.EXTRAS, 34.8), HYPOTHERMIA_2_THRESHOLD(
			"temperature.threshold.hypothermia.2", EntryType.DOUBLE, CFiles.EXTRAS, 33.4), HYPOTHERMIA_3_THRESHOLD(
			"temperature.threshold.hypothermia.3", EntryType.DOUBLE, CFiles.EXTRAS, 32.3), TEMPERATURE_THIRSTY(
			"temperature.modify.thirst.thirsty", EntryType.DOUBLE, CFiles.EXTRAS, 0.5), TEMPERATURE_WATER(
			"temperature.modify.water.in_non_ocean_water", EntryType.DOUBLE, CFiles.EXTRAS, -2.2), TEMPERATURE_RAIN(
			"temperature.modify.downfall", EntryType.DOUBLE, CFiles.EXTRAS, -1.6), TEMPERATURE_FIRE("temperature.modify.player.on_fire",
			EntryType.DOUBLE, CFiles.EXTRAS, 15.0), TEMPERATURE_LEATHER_ARMOUR("temperature.modify.armour_per_piece.leather",
			EntryType.DOUBLE, CFiles.EXTRAS, 0.3), TEMPERATURE_CHAIN_ARMOUR("temperature.modify.armour_per_piece.chainmail",
			EntryType.DOUBLE, CFiles.EXTRAS, 0.11), TEMPERATURE_IRON_ARMOUR("temperature.modify.armour_per_piece.iron", EntryType.DOUBLE,
			CFiles.EXTRAS, 0.15), TEMPERATURE_GOLD_ARMOUR("temperature.modify.armour_per_piece.gold", EntryType.DOUBLE, CFiles.EXTRAS, 0.21), TEMPERATURE_DIAMOND_ARMOUR(
			"temperature.modify.armour_per_piece.diamond", EntryType.DOUBLE, CFiles.EXTRAS, 0.27), TEMPERATURE_INFECTED(
			"temperature.modify.infected", EntryType.DOUBLE, CFiles.EXTRAS, 2.1), TEMPERATURE_NEAR_FIRE("temperature.modify.infected",
			EntryType.DOUBLE, CFiles.EXTRAS, 3.2), TEMPERATURE_RUNNING("temperature.modify.sprinting", EntryType.DOUBLE, CFiles.EXTRAS, 0.9), TEMPERATURE_NIGHT(
			"temperature.modify.nighttime", EntryType.DOUBLE, CFiles.EXTRAS, -1.2), THIRST_DECAY_THIRST(
			"thirst.decay.modifier.per_dehydrated_level", EntryType.INTEGER, CFiles.EXTRAS, -4), DAMAGE_HEATSTROKE(
			"temperature.heatstroke.damage_per_level", EntryType.DOUBLE, CFiles.EXTRAS, 1.0), DAMAGE_HYPOTHERMIA(
			"temperature.hypothermia.damage_per_level", EntryType.DOUBLE, CFiles.EXTRAS, 1.0), TEMPERATURE_DAMAGE_DELAY(
			"temperature.execute_after_delay.damage", EntryType.INTEGER, CFiles.EXTRAS, 40), TEMPERATURE_FROSTBITE_DELAY(
			"temperature.execute_after_delay.frostbite", EntryType.INTEGER, CFiles.EXTRAS, 62), TEMPERATURE_POTIONS_DELAY(
			"temperature.execute_after_delay.potion_effects", EntryType.INTEGER, CFiles.EXTRAS, 33), TEMPERATURE_SWEAT_DELAY(
			"temperature.execute_after_delay.sweat", EntryType.INTEGER, CFiles.EXTRAS, 3), PIGMAN_PACK_SPAWN("pigman.spawn.chance",
			EntryType.INTEGER, CFiles.MOBS, 3), PIGMAN_PACK_MIN("pigman.spawn.pack.minimum", EntryType.INTEGER, CFiles.MOBS, 1), PIGMAN_PACK_MAX(
			"pigman.spawn.pack.maximum", EntryType.INTEGER, CFiles.MOBS, 2), ZOMBIE_PACK_SPAWN("zombie.spawn.chance", EntryType.INTEGER,
			CFiles.MOBS, 80), ZOMBIE_PACK_MIN("zombie.spawn.pack.minimum", EntryType.INTEGER, CFiles.MOBS, 1), ZOMBIE_PACK_MAX(
			"zombie.spawn.pack.maximum", EntryType.INTEGER, CFiles.MOBS, 5), GUARD_PACK_SPAWN("guard.spawn.chance", EntryType.INTEGER,
			CFiles.MOBS, 2), GUARD_PACK_MIN("guard.spawn.pack.minimum", EntryType.INTEGER, CFiles.MOBS, 1), GUARD_PACK_MAX(
			"guard.spawn.pack.maximum", EntryType.INTEGER, CFiles.MOBS, 1), GIANT_PACK_SPAWN("giant.spawn.chance", EntryType.INTEGER,
			CFiles.MOBS, 1), GIANT_PACK_MIN("giant.spawn.pack.minimum", EntryType.INTEGER, CFiles.MOBS, 1), GIANT_REINFORCEMENT_MIN(
			"giant.ability.reinforcement.minimum", EntryType.INTEGER, CFiles.MOBS, 1), GIANT_REINFORCEMENT_MAX(
			"giant.ability.reinforcement.maximum", EntryType.INTEGER, CFiles.MOBS, 6), GIANT_REINFORCEMENT_CHANCE(
			"giant.ability.reinforcement.chance", EntryType.INTEGER, CFiles.MOBS, 30), GIANT_PACK_MAX("giant.spawn.pack.maximum",
			EntryType.INTEGER, CFiles.MOBS, 1), GIANT_INCLUDES("giant.spawn.biome.includes", EntryType.LIST, CFiles.MOBS,
			new ArrayList<String>(Arrays.asList("Mesa Plateau"))), ZOMBIE_EXCLUDES("zombie.spawn.biome.excludes", EntryType.LIST,
			CFiles.MOBS, new ArrayList<String>(Arrays.asList("Mesa Plateau"))), PIGMAN_EXCLUDES("pigman.spawn.biome.excludes",
			EntryType.LIST, CFiles.MOBS, new ArrayList<String>(Arrays.asList("Mesa Plateau"))), GUARD_EXCLUDES(
			"guard.spawn.biome.excludes", EntryType.LIST, CFiles.MOBS, new ArrayList<String>()), GENERAL_EXCLUDES(
			"general.spawn.biome.excludes", EntryType.LIST, CFiles.MOBS, new ArrayList<String>(Arrays.asList("Sky", "Ocean", "Deep Ocean"))), GUARD_NAME(
			"guard.name", EntryType.STRING, CFiles.MOBS, "MrTeePee"), ZOMBIE_HEALTH("zombie.attribute.health", EntryType.DOUBLE,
			CFiles.MOBS, 20.0), ZOMBIE_SPEED("zombie.attribute.speed.no_target", EntryType.DOUBLE, CFiles.MOBS, 0.23), ZOMBIE_SPEED_TARGET(
			"zombie.attribute.speed.target_multiplier", EntryType.DOUBLE, CFiles.MOBS, 1.5), ZOMBIE_KNOCKBACK_RESIST(
			"zombie.attribute.knockback_resistance", EntryType.DOUBLE, CFiles.MOBS, 0.0), ZOMBIE_DAMAGE("zombie.attribute.damage",
			EntryType.DOUBLE, CFiles.MOBS, 3.0), PIGMAN_HEALTH("pigman.attribute.health", EntryType.DOUBLE, CFiles.MOBS, 1.0), PIGMAN_SPEED(
			"pigman.attribute.speed.no_target", EntryType.DOUBLE, CFiles.MOBS, 0.21), PIGMAN_SPEED_TARGET(
			"pigman.attribute.speed.target_multiplier", EntryType.DOUBLE, CFiles.MOBS, 1.45), PIGMAN_KNOCKBACK_RESIST(
			"pigman.attribute.knockback_resistance", EntryType.DOUBLE, CFiles.MOBS, 0.0), PIGMAN_DAMAGE("pigman.attribute.damage",
			EntryType.DOUBLE, CFiles.MOBS, 10.0), GIANT_HEALTH("giant.attribute.health", EntryType.DOUBLE, CFiles.MOBS, 100.0), GIANT_SPEED(
			"giant.attribute.speed.no_target", EntryType.DOUBLE, CFiles.MOBS, 0.23), GIANT_SPEED_TARGET(
			"giant.attribute.speed.target_multiplier", EntryType.DOUBLE, CFiles.MOBS, 1.5), GIANT_JUMP_MULTIPLIER(
			"giant.attribute.jump.multiplier", EntryType.DOUBLE, CFiles.MOBS, 4.1), ZOMBIE_JUMP_MULTIPLIER(
			"zombie.attribute.jump.multiplier", EntryType.DOUBLE, CFiles.MOBS, 1.05), PIGMAN_JUMP_MULTIPLIER(
			"pigman.attribute.jump.multiplier", EntryType.DOUBLE, CFiles.MOBS, 1.05), GUARD_JUMP_MULTIPLIER(
			"guard.attribute.jump.multiplier", EntryType.DOUBLE, CFiles.MOBS, 1.05), GIANT_KNOCKBACK_RESIST(
			"giant.attribute.knockback_resistance", EntryType.DOUBLE, CFiles.MOBS, 0.0), GIANT_DAMAGE("giant.attribute.damage",
			EntryType.DOUBLE, CFiles.MOBS, 12.0), GUARD_HEALTH("guard.attribute.health", EntryType.DOUBLE, CFiles.MOBS, 20.0), GUARD_SPEED(
			"guard.attribute.speed.no_target", EntryType.DOUBLE, CFiles.MOBS, 0.23), GUARD_SPEED_TARGET(
			"guard.attribute.speed.target_multiplier", EntryType.DOUBLE, CFiles.MOBS, 1.0), GUARD_KNOCKBACK_RESIST(
			"guard.attribute.knockback_resistance", EntryType.DOUBLE, CFiles.MOBS, 0.0), GUARD_DAMAGE("guard.attribute.damage",
			EntryType.DOUBLE, CFiles.MOBS, 3.0), BOW_SHARPENED_COMBAT_MOD("equipment.bow_sharpened.combat_modifier", EntryType.INTEGER,
			CFiles.EXTRAS, 5), BOW_PRECISE_HEADSHOT_MOD("equipment.bow_precise.headshot_modifier", EntryType.INTEGER, CFiles.EXTRAS, 4), HEAL_TIMEOUT(
			"heal.time_to_complete", EntryType.INTEGER, CFiles.EXTRAS, 15), HEAL_COOLDOWN("heal.time_to_cooldown", EntryType.INTEGER,
			CFiles.EXTRAS, 300), DEFAULT_HEAL_DURATION("heal.default.heal.duration", EntryType.INTEGER, CFiles.EXTRAS, 5), DEFAULT_HEAL_LEVEL(
			"heal.default.heal.level", EntryType.INTEGER, CFiles.EXTRAS, 0), DEFAULT_ABSORPTION_DURATION(
			"heal.default.absorption.duration", EntryType.INTEGER, CFiles.EXTRAS, 0), DEFAULT_ABSORPTION_LEVEL(
			"heal.default.absorption.level", EntryType.INTEGER, CFiles.EXTRAS, 0), MEDICINE_HEAL_EXTENSION("heal.medicine.heal.extention",
			EntryType.INTEGER, CFiles.EXTRAS, 5), MEDICINE_ABSORPTION_EXTENSION("heal.medicine.absorption.extention", EntryType.INTEGER,
			CFiles.EXTRAS, 20), MEDICINE_HEAL_LEVEL("heal.medicine.heal.level", EntryType.INTEGER, CFiles.EXTRAS, 1), MEDICINE_ABSORPTION_LEVEL(
			"heal.medicine.absorption.level", EntryType.INTEGER, CFiles.EXTRAS, 1), OINTMENT_HEAL_EXTENSION("heal.ointment.heal.extention",
			EntryType.INTEGER, CFiles.EXTRAS, 3), OINTMENT_ABSORPTION_EXTENSION("heal.ointment.absorption.extention", EntryType.INTEGER,
			CFiles.EXTRAS, 8), OINTMENT_HEAL_LEVEL("heal.ointment.heal.level", EntryType.INTEGER, CFiles.EXTRAS, 0), OINTMENT_ABSORPTION_LEVEL(
			"heal.ointment.absorption.level", EntryType.INTEGER, CFiles.EXTRAS, 0), ANTISEPTIC_HEAL_EXTENSION(
			"heal.antiseptic.heal.extention", EntryType.INTEGER, CFiles.EXTRAS, 0), ANTISEPTIC_ABSORPTION_EXTENSION(
			"heal.antiseptic.absorption.extention", EntryType.INTEGER, CFiles.EXTRAS, 0), ANTISEPTIC_HEAL_LEVEL(
			"heal.antiseptic.heal.level", EntryType.INTEGER, CFiles.EXTRAS, 0), ANTISEPTIC_ABSORPTION_LEVEL(
			"heal.antiseptic.absorption.level", EntryType.INTEGER, CFiles.EXTRAS, 0), DEFAULT_ANTISEPTIC("heal.default.antiseptic",
			EntryType.BOOLEAN, CFiles.EXTRAS, false), OINTMENT_ANTISEPTIC("heal.ointment.antiseptic", EntryType.BOOLEAN, CFiles.EXTRAS,
			false), ANTISEPTIC_ANTISEPTIC("heal.antiseptic.antiseptic", EntryType.BOOLEAN, CFiles.EXTRAS, true), MEDICINE_ANTISEPTIC(
			"heal.medicine.antiseptic", EntryType.BOOLEAN, CFiles.EXTRAS, true), GUARD_MELEE_ITEM("guard.equipment.melee.sword",
			EntryType.STRING, CFiles.MOBS, "IRON_SWORD"), GUARD_BOOTS_ITEM("guard.equipment.boots", EntryType.STRING, CFiles.MOBS,
			"CHAINMAIL_BOOTS"), GUARD_LEGGINGS_ITEM("guard.equipment.leggings", EntryType.STRING, CFiles.MOBS, "CHAINMAIL_LEGGINGS"), GUARD_CHESTPLATE_ITEM(
			"guard.equipment.chestplate", EntryType.STRING, CFiles.MOBS, "AIR"), GUARD_HELMET_ITEM("guard.equipment.helmet",
			EntryType.STRING, CFiles.MOBS, "GOLD_HELMET"), GUARD_ZOMBIE_DEATH("guard.become_zombie_on_death", EntryType.BOOLEAN,
			CFiles.MOBS, true), PIGMAN_MULTIPLY_DEATH("pigman.explode.adult.amount", EntryType.INTEGER, CFiles.MOBS, 4), PIGMAN_EXPLODE_DEATH(
			"pigman.explode.baby.magnitude", EntryType.DOUBLE, CFiles.MOBS, 1.0), ZOMBIE_NORMAL_CHANCE("zombie.type.normal.chance",
			EntryType.INTEGER, CFiles.MOBS, 80), ZOMBIE_LEATHER_CHANCE("zombie.type.leather.chance", EntryType.INTEGER, CFiles.MOBS, 10), ZOMBIE_CHAIN_CHANCE(
			"zombie.type.chain.chance", EntryType.INTEGER, CFiles.MOBS, 5), ZOMBIE_GOLD_CHANCE("zombie.type.gold.chance",
			EntryType.INTEGER, CFiles.MOBS, 3), ZOMBIE_IRON_CHANCE("zombie.type.iron.chance", EntryType.INTEGER, CFiles.MOBS, 2), ZOMBIE_CRAWLER_CHANCE(
			"zombie.subtype.crawler.chance", EntryType.INTEGER, CFiles.MOBS, 20), WARM_WATER_TEMPERATURE("temperature.modify.warm_water",
			EntryType.DOUBLE, CFiles.EXTRAS, 0.5), COLD_WATER_TEMPERATURE("temperature.modify.cold_water", EntryType.DOUBLE, CFiles.EXTRAS,
			-0.9), GIANT_SLOWNESS_RADIUS("giant.ability.stomp.slowness.radius", EntryType.INTEGER, CFiles.MOBS, 10), GIANT_EXPLODE_MAGNITUDE(
			"giant.ability.stomp.magnitude", EntryType.DOUBLE, CFiles.MOBS, 1.5), CHESTS("chests.enabled", EntryType.BOOLEAN,
			CFiles.ADDONS, true), CHEST_TYPES("types", EntryType.CONFIGURATION_SECTION, CFiles.CHESTS, null), CHEST_LOCATIONS("locations",
			EntryType.CONFIGURATION_SECTION, CFiles.CHESTS, null), CHEST_RESPAWN_TIME("chest.respawn_time", EntryType.INTEGER,
			CFiles.EXTRAS, 300), RANKS("ranks", EntryType.CONFIGURATION_SECTION, CFiles.RANKS, null), SAVE_SPEED("save_delay",
			EntryType.INTEGER, CFiles.CONFIG, 6000), SQL_BEHAVIOUR("behaviour", EntryType.STRING, CFiles.MYSQL, "Userdata->MySQL"), DEDICATED(
			"dedicated-mode", EntryType.BOOLEAN, CFiles.CONFIG, false), SQL_TICKER("sql.transmit_period", EntryType.INTEGER, CFiles.CONFIG,
			60), NOTIFY_SAVE("notify_on_save", EntryType.BOOLEAN, CFiles.CONFIG, true), ALWAYS_ZOMBIE("death.become_zombie.always",
			EntryType.BOOLEAN, CFiles.ADDONS, false), ZOMBIE_MIN_Z("zombie.spawn.limits.minimum_z", EntryType.INTEGER, CFiles.MOBS, 0), ZOMBIE_MAX_Z(
			"zombie.spawn.limits.maximum_z", EntryType.INTEGER, CFiles.MOBS, 0), ZOMBIE_LIMITER("zombie.spawn.limits.enabled",
			EntryType.BOOLEAN, CFiles.MOBS, false), PIGMAN_MIN_Z("pigman.spawn.limits.minimum_z", EntryType.INTEGER, CFiles.MOBS, 0), PIGMAN_MAX_Z(
			"pigman.spawn.limits.maximum_z", EntryType.INTEGER, CFiles.MOBS, 0), PIGMAN_LIMITER("pigman.spawn.limits.enabled",
			EntryType.BOOLEAN, CFiles.MOBS, false), GIANT_MIN_Z("giant.spawn.limits.minimum_z", EntryType.INTEGER, CFiles.MOBS, 0), GIANT_MAX_Z(
			"giant.spawn.limits.maximum_z", EntryType.INTEGER, CFiles.MOBS, 0), GIANT_LIMITER("giant.spawn.limits.enabled",
			EntryType.BOOLEAN, CFiles.MOBS, false), GUARD_MIN_Z("guard.spawn.limits.minimum_z", EntryType.INTEGER, CFiles.MOBS, 0), GUARD_MAX_Z(
			"guard.spawn.limits.maximum_z", EntryType.INTEGER, CFiles.MOBS, 0), GUARD_LIMITER("guard.spawn.limits.enabled",
			EntryType.BOOLEAN, CFiles.MOBS, false);

	private final String key;
	private final EntryType type;
	private final CFiles file;
	private final Object defaultValue;

	private ConfigEntries(String key, EntryType type, CFiles file, Object defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
		this.type = type;
		this.file = file;
	}

	public String getKey() {
		return key;
	}

	public CFiles getFile() {
		return file;
	}

	public EntryType getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public <E> E getValue() {
		switch (type) {
		case STRING:
			return (E) (!file.getFile().contains(key) ? defaultValue : file.getFile().getString(key));
		case INTEGER:
			return (E) (!file.getFile().contains(key) ? defaultValue : file.getFile().getInt(key));
		case BOOLEAN:
			return (E) (!file.getFile().contains(key) ? defaultValue : file.getFile().getBoolean(key));
		case ITEMSTACK:
			return (E) (!file.getFile().contains(key) ? defaultValue : file.getFile().getItemStack(key));
		case DOUBLE:
			return (E) (!file.getFile().contains(key) ? defaultValue : file.getFile().getDouble(key));
		case LIST:
			return (E) (!file.getFile().contains(key) ? defaultValue : file.getFile().getList(key));
		case CONFIGURATION_SECTION:
			return (E) (!file.getFile().isConfigurationSection(key) ? makeDefaultSection() : file.getFile().getConfigurationSection(key));
		default:
			return null;
		}
	}

	private ConfigurationSection makeDefaultSection() {
		ConfigurationSection section = file.getFile().createSection(key);
		if (this == ENGINEER_RECIPES) {
			String random = UUID.randomUUID().toString();
			section.set(random + ".input1", ItemUtilities.getInstance().getTagItem(ItemTag.OINTMENT, 2));
			section.set(random + ".input2", ItemUtilities.getInstance().getTagItem(ItemTag.ANTISEPTIC, 2));
			section.set(random + ".output", ItemUtilities.getInstance().getTagItem(ItemTag.MEDICINE, 1));
		} else if (this == IMMUNE) {
			section.set("name", "Building Immunity");
			section.set("description", "Heal yourself from infection 10 times.");
			section.set("requirements.kills.zombie", 0);
			section.set("requirements.kills.pigman", 0);
			section.set("requirements.kills.player", 0);
			section.set("requirements.kills.giant", 0);
			section.set("requirements.heals.self.bleeding", 0);
			section.set("requirements.heals.self.infection", 10);
			section.set("requirements.heals.other", 0);
			section.set("requirements.drinks", 0);
			section.set("requirements.revive.other", 0);
			section.set("requirements.revive.self", 0);
			section.set("requirements.deaths", 0);
			section.set("requirements.deathstate.zombie", 0);
			section.set("requirements.deathstate.ghost", 0);
			section.set("requirements.friends", 0);
		} else if (this == FRIENDLY) {
			section.set("name", "Making Friends");
			section.set("description", "make a friend");
			section.set("requirements.kills.zombie", 0);
			section.set("requirements.kills.pigman", 0);
			section.set("requirements.kills.player", 0);
			section.set("requirements.kills.giant", 0);
			section.set("requirements.heals.self.bleeding", 0);
			section.set("requirements.heals.self.infection", 0);
			section.set("requirements.heals.other", 0);
			section.set("requirements.drinks", 0);
			section.set("requirements.revive.other", 0);
			section.set("requirements.revive.self", 0);
			section.set("requirements.deaths", 0);
			section.set("requirements.deathstate.zombie", 0);
			section.set("requirements.deathstate.ghost", 0);
			section.set("requirements.friends", 1);
		} else if (this == THIRSTY) {
			section.set("name", "Staying Hydrated");
			section.set("description", "drink 20 bottles of water");
			section.set("requirements.kills.zombie", 0);
			section.set("requirements.kills.pigman", 0);
			section.set("requirements.kills.player", 0);
			section.set("requirements.kills.giant", 0);
			section.set("requirements.heals.self.bleeding", 0);
			section.set("requirements.heals.self.infection", 0);
			section.set("requirements.heals.other", 0);
			section.set("requirements.drinks", 20);
			section.set("requirements.revive.other", 0);
			section.set("requirements.revive.self", 0);
			section.set("requirements.deaths", 0);
			section.set("requirements.deathstate.zombie", 0);
			section.set("requirements.deathstate.ghost", 0);
			section.set("requirements.friends", 0);
		} else if (this == LIFECYCLE) {
			section.set("name", "The Circle of Life");
			section.set("description", "reach the end of your life");
			section.set("requirements.kills.zombie", 0);
			section.set("requirements.kills.pigman", 0);
			section.set("requirements.kills.player", 0);
			section.set("requirements.kills.giant", 0);
			section.set("requirements.heals.self.bleeding", 0);
			section.set("requirements.heals.self.infection", 0);
			section.set("requirements.heals.other", 0);
			section.set("requirements.drinks", 0);
			section.set("requirements.revive.other", 0);
			section.set("requirements.revive.self", 0);
			section.set("requirements.deaths", 1);
			section.set("requirements.deathstate.zombie", 0);
			section.set("requirements.deathstate.ghost", 0);
			section.set("requirements.friends", 0);
		} else if (this == RANKS) {
			section.set("0.chat_prefix", "");
			section.set("0.equipment.boots", null);
			section.set("0.equipment.helmet", null);
			section.set("0.equipment.leggings", null);
			section.set("0.equipment.chestplate",
					EquipmentState.BROKEN.applyTo(ItemUtilities.getInstance().getTagItem(ItemTag.STARTER_CHESTPLATE, 1)));
			section.set("0.equipment.inventory", Arrays.asList(EquipmentState.DEVASTATED.applyTo(ItemUtilities.getInstance().getTagItem(
					ItemTag.STARTER_SWORD, 1)), ItemUtilities.getInstance().getTagItem(ItemTag.THERMOMETER, 1), ItemUtilities.getInstance()
					.getTagItem(ItemTag.BANDAGE, 1), new ItemStack(Material.POTION),
					ItemUtilities.getInstance().getTagItem(ItemTag.RADIO, 1)));

			section.set("100.chat_prefix", "");
			section.set("100.equipment.boots", null);
			section.set("100.equipment.helmet", null);
			section.set("100.equipment.leggings", null);
			section.set("100.equipment.chestplate", null);
			section.set("100.equipment.inventory", new ArrayList<ItemStack>());
		}
		return section;
	}

	public static void loadCrackshot() {
		if (MyZ.instance.getServer().getPluginManager().isPluginEnabled("CrackShot")) {
			FileConfiguration file = CFiles.EXTRAS.getFile();

			com.shampaggon.crackshot.CSUtility crackshot = new com.shampaggon.crackshot.CSUtility();
			for (String key : crackshot.getHandle().parentlist.values()) {
				if (!file.isSet("visibility.player.crackshot." + key)) {
					file.set("visibility.player.crackshot." + key + ".shoot", 7);
					file.set("visibility.player.crackshot." + key + ".reload", 2);
				}
			}
		}
	}

	public static int getCrackshot(String name, String action) {
		return CFiles.EXTRAS.getFile().getInt("visibility.player.crackshot." + name + "." + action);
	}
}
