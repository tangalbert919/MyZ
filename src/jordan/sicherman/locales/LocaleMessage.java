/**
 * 
 */
package jordan.sicherman.locales;

import java.util.HashMap;
import java.util.Map;

import jordan.sicherman.utilities.configuration.ConfigEntries;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Jordan
 * 
 */
public enum LocaleMessage {
	NO_SQL("sql.is_off", 0), SQL_MODIFICATION("sql.modified_off", 0), SQL_CONNECT("sql.connect.success", 0), SQL_DISCONNECT(
			"sql.disconnect.success", 0), SQL_NO_CONNECT("sql.connect.fail", 0), SQL_CONNECTING("sql.connect.connecting", 0), SQL_NO_DISCONNECT(
			"sql.disconnect.fail", 0), SQL_FAIL("sql.execute.fail", 0), MURKY_WATER("item.murky_water", 0), INFECTED("event.infected", 0), RADIO_DISPLAY(
			"item.radio.displayname", 0), RADIO_LORE("item.radio.lore", 0), RADIO_CHAT("chat.radio", 1), PRIVATE_CHAT_FROM(
			"chat.private.from", 0), PRIVATE_CHAT_TO("chat.private.to", 0), NO_USER("chat.private.error", 0), DID_POKE(
			"chat.private.did_poke", 1), POKED("chat.private.poked", 1), LOCAL_CHAT("chat.local.format", 0), INCOMPATIBLE(
			"error.incompatible", 0), REVIVER("revive.finished_revive", 1), REVIVED("revive.was_revived", 1), REVIVER_STARTED(
			"revive.reviver_started", 1), REVIVED_STARTED("revive.revived_started", 1), REVIVER_CANCELLED("revive.reviver_cancelled", 1), REVIVED_CANCELLED(
			"revive.revived_cancelled", 1), BECAME_GHOST("death.became_ghost", 1), BECAME_ZOMBIE("death.became_zombie", 1), DEATH_FALL(
			"death.fall_damage", 1), DEATH_ZOMBIE("death.zombie", 2), DEATH_PIGMAN("death.pigman", 2), DEATH_GIANT("death.giant", 2), DEATH_DROWNED(
			"death.drowned", 1), DEATH_CACTUS("death.cactus", 1), DEATH_FIRE("death.fire", 1), DEATH_LAVA("death.lava", 1), DEATH_POISON(
			"death.poison", 1), DEATH_MAGIC("death.magic", 1), DEATH_ARROW("death.shot", 1), DEATH_PLAYER("death.player", 2), DEATH_EXPLOSION(
			"death.explosion", 1), DEATH_SUFFOCATION("death.block_suffocation", 1), DEATH_UNKNOWN("death.unknown_cause", 1), DEATH_STARVED(
			"death.starvation", 1), DEATH_VOID("death.void", 1), DEATH_AS_GHOST("death.as_ghost", 1), CANNOT_COMMAND(
			"command.cannot_execute_as_console", 0), MANAGING_SPAWNS("command.manage.spawn.begin", 0), MANAGING_OVER(
			"command.manage.ended", 0), NO_SPAWNS("command.manage.spawn.no_spawns", 0), VIEWING_SPAWN("command.manage.spawn.viewing_spawn",
			1), REMOVED_SPAWN("command.manage.spawn.removed_spawn", 0), ADDED_SPAWN("command.manage.spawn.added_spawn", 0), HOME_SET(
			"command.manage.spawn.home_set", 0), SPAWNED("command.spawn.spawned", 0), STARTER_TUNIC_DISPLAY(
			"equipment.starter.chestplate.displayname", 0), STARTER_TUNIC_LORE("equipment.starter.chestplate.lore", 0), STARTER_SWORD_DISPLAY(
			"equipment.starter.sword.displayname", 0), STARTER_SWORD_LORE("equipment.starter.sword.lore", 0), BANDAGE_DISPLAY(
			"item.healing.bandage.displayname", 0), BLEEDING("event.bleeding", 0), BLEEDING_ENDED("event.stopped_bleeding", 0), INFECTION_ENDED(
			"event.uninfected", 0), SALT_WATER("item.salt_water", 0), OINTMENT_DISPLAY("item.healing.ointment.displayname", 0), OINTMENT_LORE(
			"item.healing.ointment.lore", 0), ANTISEPTIC_DISPLAY("item.healing.antiseptic.displayname", 0), ANTISEPTIC_LORE(
			"item.healing.antiseptic.lore", 0), SCISSORS_DISPLAY("item.healing.scissors.displayname", 0), SCISSORS_LORE(
			"item.healing.scissors.lore", 0), BANDAGE_LORE("items.healing.bandage.lore", 0), ALREADY_SPAWNED(
			"command.spawn.already_spawned", 0), DEVASTATED("equipment.state.devastated", 1), BROKEN("equipment.state.broken", 1), DULL(
			"equipment.state.dull", 1), TEMPERED("equipment.state.tempered", 1), REINFORCED("equipment.state.reinforced", 1), SHATTERED(
			"equipment.state.shattered", 1), WEAKENED("equipment.state.weakened", 1), SHARPENED("equipment.state.sharpened", 1), ORNATE(
			"equipment.state.ornate", 1), FORTIFIED("equipment.state.fortified", 1), SLACK("equipment.state.slack", 1), PRECISE(
			"equipment.state.precise", 2), CRACKED("equipment.state.cracked", 1), BOW_SHARPENED("equipment.state.bow_sharpened", 2), UNABLE_TO_ACCESS_INVENTORY(
			"event.open_inventory.cannot", 0), CHAIN("item.chain", 0), GRAPPLE_DISPLAY("item.grapple.displayname", 0), GRAPPLE_LORE(
			"item.grapple.lore", 0), WEAK("equipment.state.weak_grapple", 0), LIGHTWEIGHT_GRAPPLE("equipment.state.lightweight_grapple", 0), MANAGING_ENGINEER(
			"command.manage.engineer.begin", 0), ENGINEER_RECIPE_NOT_CREATED("command.manage.engineer.recipe.not_created", 0), ENGINEER_RECIPE_CREATED(
			"command.manage.engineer.recipe.created", 0), ENGINEER_RECIPE_REMOVED("command.manage.engineer.recipe.removed", 0), ENGINEER_RECIPE_MODIFIED(
			"command.manage.engineer.recipe.modified", 0), NO_RECIPES("command.manage.engineer.no_recipes", 0), VIEWING_RECIPE(
			"command.manage.engineer.viewing_recipe", 1), MEDICINE_DISPLAY("item.healing.medicine.displayname", 0), MEDICINE_LORE(
			"item.healing.medicine.lore", 0), ACHIEVEMENT_EARNED("event.earned_achievement", 2), COLD_WATER("item.cold_water", 0), WARM_WATER(
			"item.warm_water", 0), THERMOMETER_DISPLAY("item.thermometer.displayname", 0), THERMOMETER_LORE("item.thermometer.lore", 0), THERMOMETER_NORMAL(
			"item.thermometer.state.normal", 1), THERMOMETER_SWEATING("item.thermometer.state.sweating", 1), THERMOMETER_HEAT1(
			"item.thermometer.state.heatstroke.1", 1), THERMOMETER_HEAT2("item.thermometer.state.heatstroke.2", 1), THERMOMETER_HEAT3(
			"item.thermometer.state.heatstroke.3", 1), THERMOMETER_SHIVERING("item.thermometer.state.shivering", 1), THERMOMETER_HYPO1(
			"item.thermometer.state.hypothermia.1", 1), THERMOMETER_HYPO2("item.thermometer.state.hypothermia.2", 1), THERMOMETER_HYPO3(
			"item.thermometer.state.hypothermia.3", 1), HEADSHOT("event.headshot", 1), HEALING_ALREADY("event.heal.unable", 0), HEAL_NOT_COMPLETE(
			"event.heal.not_completed_healed", 0), HEAL_NOT_COMPLETED("event.heal.not_completed_healer", 0), HEAL_COOLING_DOWN(
			"event.heal.cooling_down", 1), YES("statement.yes", 0), NO("statement.no", 0), HEAL_CHECK("event.heal.check", 2), HEALED_PLAYER(
			"event.heal.completed_healer", 1), HEALED("event.heal.completed_healed", 1), CHEST_SET("event.chest.set", 1), NOTHING(
			"statement.nothing", 0), CREATE_CHEST_INSTRUCTIONS("event.chest.create", 0), ADD_ITEM_INSTRUCTIONS("event.chest.add_item", 0), ADD_ITEM_PARSE_ERROR(
			"event.chest.add_item_error", 0), CHEST_MANAGER("command.manager.chest.begin", 0), CHEST_MANAGER_COMPLETE(
			"command.manager.chest.lootset_created", 0), CHEST_MANAGER_REMOVED("command.manager.chest.lootset_removed", 0), CHEST_MANAGER_REMOVE(
			"command.manager.chest.lootset_remove", 0), CHAT_PREFIX_MANAGER_0("command.manager.chat_prefix.start", 0), CHAT_PREFIX_MANAGER_1(
			"command.manager.chat_prefix.continue", 0), CHAT_PREFIX_MANAGER_2("command.manager.chat_prefix.finish", 0), MANAGING_SPAWN_KIT(
			"command.manager.spawn_kit", 0), MANAGING_SPAWN_KIT_CREATED("command.manager.spawn_kit_created", 0);

	private final String key;
	private final int variables;
	private String[] format;
	private final Map<String, String> regexReplace = new HashMap<String, String>();

	private LocaleMessage(String key, int variables) {
		this.key = key;
		this.variables = variables;
	}

	public static LocaleMessage getByKey(String key) {
		for (LocaleMessage msg : values()) {
			if (msg.getKey().equals(key)) { return msg; }
		}
		return null;
	}

	public String[] getFormat() {
		return format;
	}

	public void setFormat(String[] variables) {
		format = variables;
	}

	public int getVariables() {
		return variables;
	}

	public String getKey() {
		return key;
	}

	public String getDefaultMessage(Locale locale) {
		switch (locale) {
		case PIRATE_SPEAK:
			return LocalePirate.getMessage(this);
		case CHINESE:
			return LocaleChinese.getMessage(this);
		case ENGLISH:
		case AFRIKAANS:
		case ARABIC:
		case ARMENIAN:
		case BAHASA_INDONESIA:
		case BULGARIAN:
		case CATALAN:
		case CROATIAN:
		case CYMRAEG:
		case CZECH:
		case DANISH:
		case DUTCH:
		case ESPERANTO:
		case ESTONIAN:
		case EUSKARA:
		case FINNISH:
		case FRENCH:
		case GAEILGE:
		case GALICIAN:
		case GEORGIAN:
		case GERMAN:
		case GREEK:
		case HEBREW:
		case HUNGARIAN:
		case ICELANDIC:
		case ITALIAN:
		case JAPANESE:
		case KERNEWEK:
		case KOREAN:
		case LATVIAN:
		case LETZEBUERGESCH:
		case LINGUA_LATINA:
		case LITHUANIAN:
		case MALAY:
		case MALTI:
		case NORWEGIAN:
		case OCCITAN:
		case POLISH:
		case PORTUGUESE:
		case QUENYA:
		case ROMANIAN:
		case RUSSIAN:
		case SERBIAN:
		case SLOVENIAN:
		case SPANISH:
		case SWEDISH:
		case TAGALOG:
		case THAI:
		case TURKISH:
		case UKRAINIAN:
		case VIETNAMESE:
		case tlhIngan_Hol:
			return LocaleEnglish.getMessage(this);
		default:
			return LocaleEnglish.getMessage(this);
		}
	}

	@Override
	public String toString() {
		return toString(true);
	}

	public String toString(boolean format) {
		return toString(Locale.getByCode(ConfigEntries.LANGUAGE.<String> getValue()), format);
	}

	public String toString(CommandSender playerFor) {
		return toString(playerFor, true);
	}

	public String toString(CommandSender playerFor, boolean format) {
		return toString(Locale.getLocale(playerFor), format);
	}

	public String toString(Locale locale) {
		return toString(locale, true);
	}

	public String toString(Locale locale, boolean format) {
		String str = ChatColor.translateAlternateColorCodes('&', Locale.getConfigurationFor(locale).getString(key));
		return format ? format(str) : str;
	}

	public void sendToCrowd(CommandSender... players) {
		for (CommandSender player : players) {
			player.sendMessage(toString(player));
		}
	}

	private String format(String message) {
		for (String s : regexReplace.keySet()) {
			message = message.replaceAll(s, regexReplace.get(s));
		}

		for (int i = 0; i < variables; i++) {
			try {
				message = message.replaceAll("\\$" + i, getFormat()[i]);
			} catch (Exception e) {
				message = message.replaceAll("\\$" + i, "");
			}
		}
		return message;
	}

	public LocaleMessage filter(Object... variables) {
		String[] format = new String[variables.length];
		for (int i = 0; i < variables.length; i++) {
			format[i] = variables[i].toString();
		}
		setFormat(format);
		return this;
	}

	public LocaleMessage clearSmartFilter() {
		regexReplace.clear();
		return this;
	}

	public LocaleMessage smartFilter(String find, String replace) {
		regexReplace.put(find, replace);
		return this;
	}
}
