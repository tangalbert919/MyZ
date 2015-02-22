/**
 * 
 */
package jordan.sicherman.utilities.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.utilities.AirDrop;
import jordan.sicherman.utilities.configuration.Configuration.CFiles;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public enum ConfigEntries {

	UPDATE("auto-update", EntryType.BOOLEAN, CFiles.CONFIG, true), LANGUAGE("language", EntryType.STRING, CFiles.CONFIG, "en_CA"), WORLDS(
			"worlds", EntryType.LIST, CFiles.CONFIG, Arrays.asList("world")), THIRST_MAX_DEFAULT("thirst.max.default", EntryType.INTEGER,
			CFiles.EXTRAS, 20), THIRST_DECAY_BEACH("thirst.decay.beach", EntryType.INTEGER, CFiles.EXTRAS, 20), THIRST_DECAY_PLAINS(
			"thirst.decay.plains", EntryType.INTEGER, CFiles.EXTRAS, 38), THIRST_DECAY_TAIGA("thirst.decay.taiga", EntryType.INTEGER,
			CFiles.EXTRAS, 40), THIRST_DECAY_DESERT("thirst.decay.desert", EntryType.INTEGER, CFiles.EXTRAS, 23), THIRST_DECAY_OCEAN(
			"thirst.decay.ocean", EntryType.INTEGER, CFiles.EXTRAS, -120), THIRST_DECAY_JUNGLE("thirst.decay.jungle", EntryType.INTEGER,
			CFiles.EXTRAS, 31), THIRST_DECAY_MESA("thirst.decay.mesa", EntryType.INTEGER, CFiles.EXTRAS, 30), THIRST_DECAY_MUSHROOM(
			"thirst.decay.mushroom", EntryType.INTEGER, CFiles.EXTRAS, 50), THIRST_DECAY_SWAMP("thirst.decay.swamp", EntryType.INTEGER,
			CFiles.EXTRAS, 49), SPAWN_POINTS("spawnpoints",
			EntryType.LIST, CFiles.SPAWNLOCATIONS, new ArrayList<String>()), HOME_SPAWN("home", EntryType.STRING, CFiles.SPAWNLOCATIONS, ""), BLEED_DELAY(
			"bleed.damage_delay", EntryType.INTEGER, CFiles.EXTRAS, 42), INFECTION_DELAY("infection.damage_delay", EntryType.INTEGER,
			CFiles.EXTRAS, 38), BLEED_DAMAGE("bleed.damage_amount", EntryType.DOUBLE, CFiles.EXTRAS, 1.1), INFECTION_DAMAGE(
			"infection.damage_amount", EntryType.DOUBLE, CFiles.EXTRAS, 1.3), DEVASTATED_MOD("equipment.devastated_modifier", EntryType.INTEGER, CFiles.EXTRAS, 50), SHATTERED_MOD(
			"equipment.shattered_modifier", EntryType.INTEGER, CFiles.EXTRAS, 40), BROKEN_MOD("equipment.broken_modifier",
			EntryType.INTEGER, CFiles.EXTRAS, 70), SLACK_MOD("equipment.slack_modifier", EntryType.INTEGER, CFiles.EXTRAS, 90), REINFORCED_MOD(
			"equipment.reinforced_modifier", EntryType.INTEGER, CFiles.EXTRAS, 110), CRACKED_MOD("equipment.cracked_modifier",
			EntryType.INTEGER, CFiles.EXTRAS, 80), PRECISE_MOD("equipment.precise_modifier", EntryType.INTEGER, CFiles.EXTRAS, 120), BOW_SHARPENED_MOD(
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
			EntryType.BOOLEAN, CFiles.ADDONS, false), NATURAL_REGEN("natural_regen.enabled", EntryType.BOOLEAN, CFiles.ADDONS, false), DEATH_MESSAGES("enhanced_deathmessages.enabled",
			EntryType.BOOLEAN, CFiles.ADDONS, true), ENHANCE_FURNACES("enhance_furnaces.enabled",
			EntryType.BOOLEAN, CFiles.ADDONS, true), USE_BLEEDING("bleeding.enabled", EntryType.BOOLEAN, CFiles.ADDONS, true), USE_POISON("infection.enabled",
			EntryType.BOOLEAN, CFiles.ADDONS, true), TASK_SPEED("operation_tick", EntryType.INTEGER, CFiles.CONFIG, 1), PIGMAN_PACK_SPAWN(
			"pigman.spawn.chance", EntryType.INTEGER, CFiles.MOBS, 3), PIGMAN_PACK_MIN("pigman.spawn.pack.minimum", EntryType.INTEGER,
			CFiles.MOBS, 1), PIGMAN_PACK_MAX("pigman.spawn.pack.maximum", EntryType.INTEGER, CFiles.MOBS, 2), ZOMBIE_PACK_SPAWN(
			"zombie.spawn.chance", EntryType.INTEGER, CFiles.MOBS, 80), ZOMBIE_PACK_MIN("zombie.spawn.pack.minimum", EntryType.INTEGER,
			CFiles.MOBS, 1), ZOMBIE_PACK_MAX("zombie.spawn.pack.maximum", EntryType.INTEGER, CFiles.MOBS, 5), GUARD_PACK_SPAWN(
			"guard.spawn.chance", EntryType.INTEGER, CFiles.MOBS, 2), GUARD_PACK_MIN("guard.spawn.pack.minimum", EntryType.INTEGER,
			CFiles.MOBS, 1), GUARD_PACK_MAX("guard.spawn.pack.maximum", EntryType.INTEGER, CFiles.MOBS, 1), GIANT_PACK_SPAWN(
			"giant.spawn.chance", EntryType.INTEGER, CFiles.MOBS, 1), GIANT_PACK_MIN("giant.spawn.pack.minimum", EntryType.INTEGER,
			CFiles.MOBS, 1), GIANT_REINFORCEMENT_MIN("giant.ability.reinforcement.minimum", EntryType.INTEGER, CFiles.MOBS, 1), GIANT_REINFORCEMENT_MAX(
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
			CFiles.EXTRAS, 5), BOW_PRECISE_HEADSHOT_MOD("equipment.bow_precise.headshot_modifier", EntryType.INTEGER, CFiles.EXTRAS, 4), GUARD_MELEE_ITEM("guard.equipment.melee.sword",
			EntryType.STRING, CFiles.MOBS, "IRON_SWORD"), GUARD_BOOTS_ITEM("guard.equipment.boots", EntryType.STRING, CFiles.MOBS,
			"CHAINMAIL_BOOTS"), GUARD_LEGGINGS_ITEM("guard.equipment.leggings", EntryType.STRING, CFiles.MOBS, "CHAINMAIL_LEGGINGS"), GUARD_CHESTPLATE_ITEM(
			"guard.equipment.chestplate", EntryType.STRING, CFiles.MOBS, "AIR"), GUARD_HELMET_ITEM("guard.equipment.helmet",
			EntryType.STRING, CFiles.MOBS, "GOLD_HELMET"), GUARD_ZOMBIE_DEATH("guard.become_zombie_on_death", EntryType.BOOLEAN,
			CFiles.MOBS, true), PIGMAN_MULTIPLY_DEATH("pigman.explode.adult.amount", EntryType.INTEGER, CFiles.MOBS, 4), PIGMAN_EXPLODE_DEATH(
			"pigman.explode.baby.magnitude", EntryType.DOUBLE, CFiles.MOBS, 1.0), ZOMBIE_NORMAL_CHANCE("zombie.type.normal.chance",
			EntryType.INTEGER, CFiles.MOBS, 80), ZOMBIE_LEATHER_CHANCE("zombie.type.leather.chance", EntryType.INTEGER, CFiles.MOBS, 10), ZOMBIE_CHAIN_CHANCE(
			"zombie.type.chain.chance", EntryType.INTEGER, CFiles.MOBS, 5), ZOMBIE_GOLD_CHANCE("zombie.type.gold.chance",
			EntryType.INTEGER, CFiles.MOBS, 3), ZOMBIE_IRON_CHANCE("zombie.type.iron.chance", EntryType.INTEGER, CFiles.MOBS, 2), ZOMBIE_CRAWLER_CHANCE(
			"zombie.subtype.crawler.chance", EntryType.INTEGER, CFiles.MOBS, 20), GIANT_SLOWNESS_RADIUS(
			"giant.ability.stomp.slowness.radius", EntryType.INTEGER, CFiles.MOBS, 10), GIANT_EXPLODE_MAGNITUDE(
			"giant.ability.stomp.magnitude", EntryType.DOUBLE, CFiles.MOBS, 1.5), CHESTS("chests.enabled", EntryType.BOOLEAN,
			CFiles.ADDONS, true), CHEST_TYPES("types", EntryType.CONFIGURATION_SECTION, CFiles.CHESTS, null), CHEST_LOCATIONS("locations",
			EntryType.CONFIGURATION_SECTION, CFiles.CHESTS, null), CHEST_RESPAWN_TIME("chest.respawn_time", EntryType.INTEGER,
			CFiles.EXTRAS, 300), RANKS("ranks", EntryType.CONFIGURATION_SECTION, CFiles.RANKS, null), SAVE_SPEED("save_delay",
			EntryType.INTEGER, CFiles.CONFIG, 6000), DEDICATED("dedicated-mode", EntryType.BOOLEAN, CFiles.CONFIG, false), NOTIFY_SAVE(
			"notify_on_save", EntryType.BOOLEAN, CFiles.CONFIG, true), ZOMBIE_MIN_Z("zombie.spawn.limits.minimum_z", EntryType.INTEGER, CFiles.MOBS, 0), ZOMBIE_MAX_Z(
			"zombie.spawn.limits.maximum_z", EntryType.INTEGER, CFiles.MOBS, 0), ZOMBIE_LIMITER("zombie.spawn.limits.enabled",
			EntryType.BOOLEAN, CFiles.MOBS, false), PIGMAN_MIN_Z("pigman.spawn.limits.minimum_z", EntryType.INTEGER, CFiles.MOBS, 0), PIGMAN_MAX_Z(
			"pigman.spawn.limits.maximum_z", EntryType.INTEGER, CFiles.MOBS, 0), PIGMAN_LIMITER("pigman.spawn.limits.enabled",
			EntryType.BOOLEAN, CFiles.MOBS, false), GIANT_MIN_Z("giant.spawn.limits.minimum_z", EntryType.INTEGER, CFiles.MOBS, 0), GIANT_MAX_Z(
			"giant.spawn.limits.maximum_z", EntryType.INTEGER, CFiles.MOBS, 0), GIANT_LIMITER("giant.spawn.limits.enabled",
			EntryType.BOOLEAN, CFiles.MOBS, false), GUARD_MIN_Z("guard.spawn.limits.minimum_z", EntryType.INTEGER, CFiles.MOBS, 0), GUARD_MAX_Z(
			"guard.spawn.limits.maximum_z", EntryType.INTEGER, CFiles.MOBS, 0), GUARD_LIMITER("guard.spawn.limits.enabled",
			EntryType.BOOLEAN, CFiles.MOBS, false), FIRST_RUN("first_run", EntryType.BOOLEAN, CFiles.CONFIG, true), AIRDROP_BREAK(
			"airdrop.break_blocks", EntryType.BOOLEAN, CFiles.EXTRAS, true), AIRDROP_WRECKAGE("airdrop.create_wreckage", EntryType.BOOLEAN,
			CFiles.EXTRAS, true), AIRDROP_FIRE("airdrop.set_fires", EntryType.BOOLEAN, CFiles.EXTRAS, false), AIRDROP_EXPLOSION_MAGNITUDE(
			"airdrop.explosion_magnitude", EntryType.DOUBLE, CFiles.EXTRAS, 2.0), AIRDROP_CHESTS("airdrop.chest_types", EntryType.LIST,
			CFiles.EXTRAS, Arrays.asList("Supplies - Rare", "Weaponry - Rare", "Armor - Rare", "Food - Rare")), RANK_CARRYOVER(
			"spawnkit.hierarchical", EntryType.BOOLEAN, CFiles.EXTRAS, false);

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
		if (this == IMMUNE) {
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
					ItemTag.STARTER_SWORD, 1)), new ItemStack(Material.POTION)));

			section.set("100.chat_prefix", "");
			section.set("100.equipment.boots", null);
			section.set("100.equipment.helmet", null);
			section.set("100.equipment.leggings", null);
			section.set("100.equipment.chestplate", null);
			section.set("100.equipment.inventory", new ArrayList<ItemStack>());
		}
		return section;
	}

	public static void loadImportantDefaults() {
		for (BlockFace face : AirDrop.directions) {
			File schematicFolder = new File(MyZ.instance.getDataFolder().getAbsolutePath() + File.separator + "schematics" + File.separator
					+ "airdrop" + File.separator + face.name().toLowerCase());

			if (!schematicFolder.exists()) {
				schematicFolder.mkdirs();
			}
		}

		if (!FIRST_RUN.<Boolean> getValue()) { return; }

		ConfigurationSection section = CFiles.CHESTS.getFile().getConfigurationSection("types");

		setLootset(section, "Weaponry - Common", UUID.randomUUID().toString(), 8, 1, 1,
				EquipmentState.DULL.applyTo(new ItemStack(Material.WOOD_SWORD)));
		setLootset(section, "Weaponry - Common", UUID.randomUUID().toString(), 30, 1, 1,
				EquipmentState.DULL.applyTo(damage(Material.WOOD_SWORD, 2)));
		setLootset(section, "Weaponry - Common", UUID.randomUUID().toString(), 10, 1, 1,
				EquipmentState.WEAKENED.applyTo(damage(Material.STONE_SWORD, 2)));
		setLootset(section, "Weaponry - Common", UUID.randomUUID().toString(), 13, 1, 1,
				EquipmentState.DULL.applyTo(new ItemStack(Material.WOOD_SWORD)));

		setLootset(section, "Weaponry - Uncommon", UUID.randomUUID().toString(), 13, 1, 1,
				EquipmentState.SHARPENED.applyTo(new ItemStack(Material.STONE_SWORD)));
		setLootset(section, "Weaponry - Uncommon", UUID.randomUUID().toString(), 9, 1, 1,
				EquipmentState.BROKEN.applyTo(damage(Material.IRON_SWORD, 2)));
		setLootset(section, "Weaponry - Uncommon", UUID.randomUUID().toString(), 34, 1, 1,
				EquipmentState.BROKEN.applyTo(damage(Material.IRON_SWORD, 3)));
		setLootset(section, "Weaponry - Uncommon", UUID.randomUUID().toString(), 18, 1, 1,
				EquipmentState.BROKEN.applyTo(damage(Material.BOW, 5)));

		setLootset(section, "Weaponry - Rare", UUID.randomUUID().toString(), 8, 1, 1,
				EquipmentState.TEMPERED.applyTo(new ItemStack(Material.STONE_SWORD)));
		setLootset(section, "Weaponry - Rare", UUID.randomUUID().toString(), 6, 1, 1,
				EquipmentState.SHARPENED.applyTo(damage(Material.IRON_SWORD, 4)));
		setLootset(section, "Weaponry - Rare", UUID.randomUUID().toString(), 7, 1, 1,
				EquipmentState.SLACK.applyTo(new ItemStack(Material.BOW)));
		setLootset(section, "Weaponry - Rare", UUID.randomUUID().toString(), 23, 1, 1, new ItemStack(Material.IRON_SWORD));

		setLootset(section, "Armor - Common", UUID.randomUUID().toString(), 9, 1, 1,
				EquipmentState.BROKEN.applyTo(new ItemStack(Material.LEATHER_CHESTPLATE)));
		setLootset(section, "Armor - Common", UUID.randomUUID().toString(), 13, 1, 1,
				EquipmentState.BROKEN.applyTo(damage(Material.LEATHER_HELMET, 2)));
		setLootset(section, "Armor - Common", UUID.randomUUID().toString(), 11, 1, 1,
				EquipmentState.WEAKENED.applyTo(damage(Material.LEATHER_LEGGINGS, 1.4)));
		setLootset(section, "Armor - Common", UUID.randomUUID().toString(), 10, 1, 1,
				EquipmentState.BROKEN.applyTo(damage(Material.CHAINMAIL_HELMET, 2)));
		setLootset(section, "Armor - Common", UUID.randomUUID().toString(), 12, 1, 1,
				EquipmentState.WEAKENED.applyTo(damage(Material.CHAINMAIL_BOOTS, 1.2)));
		setLootset(section, "Armor - Common", UUID.randomUUID().toString(), 13, 1, 1, damage(Material.LEATHER_HELMET, 2));
		setLootset(section, "Armor - Common", UUID.randomUUID().toString(), 10, 1, 1, damage(Material.LEATHER_BOOTS, 1.7));

		setLootset(section, "Armor - Uncommon", UUID.randomUUID().toString(), 13, 1, 1, damage(Material.CHAINMAIL_HELMET, 1.1));
		setLootset(section, "Armor - Uncommon", UUID.randomUUID().toString(), 9, 1, 1, damage(Material.CHAINMAIL_LEGGINGS, 1.8));
		setLootset(section, "Armor - Uncommon", UUID.randomUUID().toString(), 20, 1, 1,
				EquipmentState.FORTIFIED.applyTo(damage(Material.LEATHER_LEGGINGS, 1.1)));
		setLootset(section, "Armor - Uncommon", UUID.randomUUID().toString(), 17, 1, 1,
				EquipmentState.REINFORCED.applyTo(damage(Material.LEATHER_CHESTPLATE, 1.1)));
		setLootset(section, "Armor - Uncommon", UUID.randomUUID().toString(), 7, 1, 1,
				EquipmentState.BROKEN.applyTo(damage(Material.IRON_HELMET, 1.7)));
		setLootset(section, "Armor - Uncommon", UUID.randomUUID().toString(), 8, 1, 1,
				EquipmentState.DEVASTATED.applyTo(damage(Material.IRON_CHESTPLATE, 3.1)));
		setLootset(section, "Armor - Uncommon", UUID.randomUUID().toString(), 4, 1, 1,
				EquipmentState.BROKEN.applyTo(damage(Material.IRON_LEGGINGS, 3)));
		setLootset(section, "Armor - Uncommon", UUID.randomUUID().toString(), 15, 1, 1,
				EquipmentState.REINFORCED.applyTo(damage(Material.CHAINMAIL_LEGGINGS, 4.1)));

		setLootset(section, "Armor - Rare", UUID.randomUUID().toString(), 17, 1, 1,
				EquipmentState.ORNATE.applyTo(damage(Material.CHAINMAIL_LEGGINGS, 1.6)));
		setLootset(section, "Armor - Rare", UUID.randomUUID().toString(), 10, 1, 1,
				EquipmentState.ORNATE.applyTo(damage(Material.CHAINMAIL_HELMET, 1.5)));
		setLootset(section, "Armor - Rare", UUID.randomUUID().toString(), 15, 1, 1,
				EquipmentState.WEAKENED.applyTo(damage(Material.IRON_LEGGINGS, 1.2)));
		setLootset(section, "Armor - Rare", UUID.randomUUID().toString(), 8, 1, 1, damage(Material.IRON_CHESTPLATE, 1.6));
		setLootset(section, "Armor - Rare", UUID.randomUUID().toString(), 16, 1, 1,
				EquipmentState.ORNATE.applyTo(damage(Material.CHAINMAIL_CHESTPLATE, 1.1)));
		setLootset(section, "Armor - Rare", UUID.randomUUID().toString(), 13, 1, 1,
				EquipmentState.WEAKENED.applyTo(damage(Material.IRON_HELMET, 1.2)));
		setLootset(section, "Armor - Rare", UUID.randomUUID().toString(), 11, 1, 1, damage(Material.IRON_BOOTS, 1.3));

		setLootset(section, "Supplies - Common", UUID.randomUUID().toString(), 15, 1, 1,
				ItemUtilities.getInstance().getTagItem(ItemTag.WARM_WATER, 1));
		setLootset(section, "Supplies - Common", UUID.randomUUID().toString(), 13, 1, 1,
				ItemUtilities.getInstance().getTagItem(ItemTag.SALTY_WATER, 1));
		setLootset(section, "Supplies - Common", UUID.randomUUID().toString(), 11, 1, 4, new ItemStack(Material.STICK));
		setLootset(section, "Supplies - Common", UUID.randomUUID().toString(), 6, 1, 2, new ItemStack(Material.COBBLESTONE));
		setLootset(section, "Supplies - Common", UUID.randomUUID().toString(), 9, 1, 1, new ItemStack(Material.LEATHER));
		setLootset(section, "Supplies - Common", UUID.randomUUID().toString(), 14, 1, 1,
				ItemUtilities.getInstance().getTagItem(ItemTag.MURKY_WATER, 1));

		setLootset(section, "Supplies - Uncommon", UUID.randomUUID().toString(), 15, 1, 1, new ItemStack(Material.POTION));
		setLootset(section, "Supplies - Uncommon", UUID.randomUUID().toString(), 6, 1, 3,
				ItemUtilities.getInstance().getTagItem(ItemTag.CHAIN, 1));
		setLootset(section, "Supplies - Uncommon", UUID.randomUUID().toString(), 4, 1, 1, new ItemStack(Material.GOLD_INGOT));
		setLootset(section, "Supplies - Uncommon", UUID.randomUUID().toString(), 8, 1, 6, new ItemStack(Material.ARROW));
		setLootset(section, "Supplies - Uncommon", UUID.randomUUID().toString(), 3, 1, 1, new ItemStack(Material.ENDER_PEARL));
		setLootset(section, "Supplies - Uncommon", UUID.randomUUID().toString(), 11, 1, 1, new ItemStack(Material.SNOW_BALL));
		setLootset(section, "Supplies - Uncommon", UUID.randomUUID().toString(), 12, 1, 1, new ItemStack(Material.MILK_BUCKET));

		setLootset(section, "Supplies - Rare", UUID.randomUUID().toString(), 11, 1, 1,
				ItemUtilities.getInstance().getTagItem(ItemTag.COLD_WATER, 1));
		setLootset(section, "Supplies - Rare", UUID.randomUUID().toString(), 7, 1, 1, new ItemStack(Material.IRON_INGOT));
		setLootset(section, "Supplies - Rare", UUID.randomUUID().toString(), 8, 2, 5,
				ItemUtilities.getInstance().getTagItem(ItemTag.CHAIN, 1));
		setLootset(section, "Supplies - Rare", UUID.randomUUID().toString(), 11, 5, 13, new ItemStack(Material.ARROW));
		setLootset(section, "Supplies - Rare", UUID.randomUUID().toString(), 8, 1, 1, new ItemStack(Material.ENDER_PEARL));
		setLootset(section, "Supplies - Rare", UUID.randomUUID().toString(), 8, 1, 1, new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
		setLootset(section, "Supplies - Rare", UUID.randomUUID().toString(), 6, 1, 1,
				EquipmentState.GRAPPLE_WEAK.applyTo(ItemUtilities.getInstance().getTagItem(ItemTag.GRAPPLE, 1)));

		setLootset(section, "Food - Common", UUID.randomUUID().toString(), 11, 1, 2, new ItemStack(Material.ROTTEN_FLESH));
		setLootset(section, "Food - Common", UUID.randomUUID().toString(), 8, 1, 1, new ItemStack(Material.MELON));
		setLootset(section, "Food - Common", UUID.randomUUID().toString(), 9, 1, 1, new ItemStack(Material.COOKIE));
		setLootset(section, "Food - Common", UUID.randomUUID().toString(), 12, 1, 2, new ItemStack(Material.WHEAT));
		setLootset(section, "Food - Common", UUID.randomUUID().toString(), 8, 1, 2, new ItemStack(Material.COCOA));
		setLootset(section, "Food - Common", UUID.randomUUID().toString(), 7, 1, 1, new ItemStack(Material.BOWL));
		setLootset(section, "Food - Common", UUID.randomUUID().toString(), 11, 1, 1, new ItemStack(Material.RED_MUSHROOM));
		setLootset(section, "Food - Common", UUID.randomUUID().toString(), 15, 1, 1, new ItemStack(Material.BROWN_MUSHROOM));

		setLootset(section, "Food - Uncommon", UUID.randomUUID().toString(), 11, 1, 2, new ItemStack(Material.RAW_CHICKEN));
		setLootset(section, "Food - Uncommon", UUID.randomUUID().toString(), 9, 1, 1, new ItemStack(Material.MUSHROOM_SOUP));
		setLootset(section, "Food - Uncommon", UUID.randomUUID().toString(), 13, 1, 2, new ItemStack(Material.APPLE));
		setLootset(section, "Food - Uncommon", UUID.randomUUID().toString(), 15, 1, 2, new ItemStack(Material.BAKED_POTATO));
		setLootset(section, "Food - Uncommon", UUID.randomUUID().toString(), 4, 1, 1, new ItemStack(Material.COOKED_BEEF));
		setLootset(section, "Food - Uncommon", UUID.randomUUID().toString(), 8, 1, 3, new ItemStack(Material.COOKED_FISH));

		setLootset(section, "Food - Rare", UUID.randomUUID().toString(), 11, 3, 6, new ItemStack(Material.WHEAT));
		setLootset(section, "Food - Rare", UUID.randomUUID().toString(), 7, 2, 4, new ItemStack(Material.COOKED_FISH));
		setLootset(section, "Food - Rare", UUID.randomUUID().toString(), 13, 1, 2, new ItemStack(Material.COOKED_CHICKEN));
		setLootset(section, "Food - Rare", UUID.randomUUID().toString(), 16, 1, 3, new ItemStack(Material.PUMPKIN_PIE));
		setLootset(section, "Food - Rare", UUID.randomUUID().toString(), 13, 4, 8, new ItemStack(Material.COOKIE));
		setLootset(section, "Food - Rare", UUID.randomUUID().toString(), 6, 2, 4, new ItemStack(Material.MUSHROOM_SOUP));

		FileUtilities.set(FIRST_RUN.getKey(), false, FIRST_RUN.getFile(), true);
	}

	private static ItemStack damage(Material material, double percent) {
		return new ItemStack(material, 1, (short) (material.getMaxDurability() / percent));
	}

	private static void setLootset(ConfigurationSection section, String name, String uid, int probability, int min, int max, ItemStack item) {
		section.set(name + "." + uid + ".probability", probability);
		section.set(name + "." + uid + ".amount_minimum", min);
		section.set(name + "." + uid + ".amount_maximum", max);
		section.set(name + "." + uid + ".item", item);
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
