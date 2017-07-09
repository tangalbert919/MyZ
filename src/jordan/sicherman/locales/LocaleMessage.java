package jordan.sicherman.locales;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum LocaleMessage {

    MURKY_WATER("item.murky_water", 0), INFECTED("event.infected", 0), NO_USER("chat.private.error", 0), INCOMPATIBLE("error.incompatible", 0), DEATH_FALL("death.fall_damage", 1), DEATH_ZOMBIE("death.zombie", 2), DEATH_PIGMAN("death.pigman", 2), DEATH_GIANT("death.giant", 2), DEATH_DROWNED("death.drowned", 1), DEATH_CACTUS("death.cactus", 1), DEATH_FIRE("death.fire", 1), DEATH_LAVA("death.lava", 1), DEATH_POISON("death.poison", 1), DEATH_MAGIC("death.magic", 1), DEATH_ARROW("death.shot", 1), DEATH_PLAYER("death.player", 2), DEATH_EXPLOSION("death.explosion", 1), DEATH_SUFFOCATION("death.block_suffocation", 1), DEATH_UNKNOWN("death.unknown_cause", 1), DEATH_STARVED("death.starvation", 1), DEATH_VOID("death.void", 1), DEATH_AS_GHOST("death.as_ghost", 1), CANNOT_COMMAND("command.cannot_execute_as_console", 0), MANAGING_SPAWNS("command.manage.spawn.begin", 0), MANAGING_OVER("command.manage.ended", 0), NO_SPAWNS("command.manage.spawn.no_spawns", 0), VIEWING_SPAWN("command.manage.spawn.viewing_spawn", 1), REMOVED_SPAWN("command.manage.spawn.removed_spawn", 0), ADDED_SPAWN("command.manage.spawn.added_spawn", 0), HOME_SET("command.manage.spawn.home_set", 0), SPAWNED("command.spawn.spawned", 0), STARTER_TUNIC_DISPLAY("equipment.starter.chestplate.displayname", 0), STARTER_TUNIC_LORE("equipment.starter.chestplate.lore", 0), STARTER_SWORD_DISPLAY("equipment.starter.sword.displayname", 0), STARTER_SWORD_LORE("equipment.starter.sword.lore", 0), BANDAGE_DISPLAY("item.healing.bandage.displayname", 0), BLEEDING("event.bleeding", 0), BLEEDING_ENDED("event.stopped_bleeding", 0), INFECTION_ENDED("event.uninfected", 0), SALT_WATER("item.salt_water", 0), ALREADY_SPAWNED("command.spawn.already_spawned", 0), DEVASTATED("equipment.state.devastated", 1), BROKEN("equipment.state.broken", 1), DULL("equipment.state.dull", 1), TEMPERED("equipment.state.tempered", 1), REINFORCED("equipment.state.reinforced", 1), SHATTERED("equipment.state.shattered", 1), WEAKENED("equipment.state.weakened", 1), SHARPENED("equipment.state.sharpened", 1), ORNATE("equipment.state.ornate", 1), FORTIFIED("equipment.state.fortified", 1), SLACK("equipment.state.slack", 1), PRECISE("equipment.state.precise", 2), CRACKED("equipment.state.cracked", 1), BOW_SHARPENED("equipment.state.bow_sharpened", 2), UNABLE_TO_ACCESS_INVENTORY("event.open_inventory.cannot", 0), CHAIN("item.chain", 0), GRAPPLE_DISPLAY("item.grapple.displayname", 0), GRAPPLE_LORE("item.grapple.lore", 0), WEAK("equipment.state.weak_grapple", 0), LIGHTWEIGHT_GRAPPLE("equipment.state.lightweight_grapple", 0), MANAGING_ENGINEER("command.manage.engineer.begin", 0), ENGINEER_RECIPE_NOT_CREATED("command.manage.engineer.recipe.not_created", 0), ENGINEER_RECIPE_CREATED("command.manage.engineer.recipe.created", 0), ENGINEER_RECIPE_REMOVED("command.manage.engineer.recipe.removed", 0), ENGINEER_RECIPE_MODIFIED("command.manage.engineer.recipe.modified", 0), NO_RECIPES("command.manage.engineer.no_recipes", 0), VIEWING_RECIPE("command.manage.engineer.viewing_recipe", 1), ACHIEVEMENT_EARNED("event.earned_achievement", 2), COLD_WATER("item.cold_water", 0), WARM_WATER("item.warm_water", 0), HEADSHOT("event.headshot", 1), YES("statement.yes", 0), NO("statement.no", 0), CHEST_SET("event.chest.set", 1), NOTHING("statement.nothing", 0), CREATE_CHEST_INSTRUCTIONS("event.chest.create", 0), ADD_ITEM_INSTRUCTIONS("event.chest.add_item", 0), ADD_ITEM_PARSE_ERROR("event.chest.add_item_error", 0), CHEST_MANAGER("command.manager.chest.begin", 0), CHEST_MANAGER_COMPLETE("command.manager.chest.lootset_created", 0), CHEST_MANAGER_REMOVED("command.manager.chest.lootset_removed", 0), CHEST_MANAGER_REMOVE("command.manager.chest.lootset_remove", 0), MANAGING_SPAWN_KIT("command.manager.spawn_kit", 0), MANAGING_SPAWN_KIT_CREATED("command.manager.spawn_kit_created", 0), REQUIRES_PLAYER("command.requires_player", 0);

    private final String key;
    private final int variables;
    private String[] format;
    private final Map regexReplace = new HashMap();

    private LocaleMessage(String key, int i) {
        this.key = key;
        this.variables = i;
    }

    public static LocaleMessage getByKey(String key) {
        LocaleMessage[] alocalemessage = values();
        int i = alocalemessage.length;

        for (int j = 0; j < i; ++j) {
            LocaleMessage msg = alocalemessage[j];

            if (msg.getKey().equals(key)) {
                return msg;
            }
        }

        return null;
    }

    public String[] getFormat() {
        return this.format;
    }

    public void setFormat(String[] astring) {
        this.format = astring;
    }

    public int getVariables() {
        return this.variables;
    }

    public String getKey() {
        return this.key;
    }

    public String getDefaultMessage(Locale locale) {
        switch (LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[locale.ordinal()]) {
        case 1:
            return LocalePirate.getMessage(this);

        case 2:
            return LocaleChinese.getMessage(this);

        case 3:
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
        case 37:
        case 38:
        case 39:
        case 40:
        case 41:
        case 42:
        case 43:
        case 44:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
            return LocaleEnglish.getMessage(this);

        default:
            return LocaleEnglish.getMessage(this);
        }
    }

    public String toString() {
        return this.toString(true);
    }

    public String toString(boolean format) {
        return this.toString(Locale.getByCode((String) ConfigEntries.LANGUAGE.getValue()), format);
    }

    public String toString(CommandSender playerFor) {
        return this.toString(playerFor, true);
    }

    public String toString(CommandSender playerFor, boolean format) {
        return this.toString(Locale.getLocale(playerFor), format);
    }

    public String toString(Locale locale) {
        return this.toString(locale, true);
    }

    public String toString(Locale locale, boolean format) {
        String str = ChatColor.translateAlternateColorCodes('&', Locale.getConfigurationFor(locale).getString(this.key));

        return format ? this.format(str) : str;
    }

    public void sendToCrowd(CommandSender... players) {
        CommandSender[] acommandsender = players;
        int i = players.length;

        for (int j = 0; j < i; ++j) {
            CommandSender player = acommandsender[j];

            player.sendMessage(this.toString(player));
        }

    }

    private String format(String message) {
        String e;

        for (Iterator i = this.regexReplace.keySet().iterator(); i.hasNext(); message = message.replaceAll(e, (String) this.regexReplace.get(e))) {
            e = (String) i.next();
        }

        for (int i = 0; i < this.variables; ++i) {
            try {
                message = message.replaceAll("\\$" + i, this.getFormat()[i]);
            } catch (Exception exception) {
                message = message.replaceAll("\\$" + i, "");
            }
        }

        return message;
    }

    public LocaleMessage filter(Object... aobject) {
        String[] format = new String[aobject.length];

        for (int i = 0; i < aobject.length; ++i) {
            format[i] = aobject[i].toString();
        }

        this.setFormat(format);
        return this;
    }

    public LocaleMessage clearSmartFilter() {
        this.regexReplace.clear();
        return this;
    }

    public LocaleMessage smartFilter(String find, String replace) {
        this.regexReplace.put(find, replace);
        return this;
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$jordan$sicherman$locales$Locale = new int[Locale.values().length];

        static {
            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.PIRATE_SPEAK.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.CHINESE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.ENGLISH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.AFRIKAANS.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.ARABIC.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.ARMENIAN.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.BAHASA_INDONESIA.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.BULGARIAN.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.CATALAN.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.CROATIAN.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.CYMRAEG.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.CZECH.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.DANISH.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.DUTCH.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.ESPERANTO.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.ESTONIAN.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.EUSKARA.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.FINNISH.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.FRENCH.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.GAEILGE.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.GALICIAN.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.GEORGIAN.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.GERMAN.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.GREEK.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.HEBREW.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.HUNGARIAN.ordinal()] = 26;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.ICELANDIC.ordinal()] = 27;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.ITALIAN.ordinal()] = 28;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.JAPANESE.ordinal()] = 29;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.KERNEWEK.ordinal()] = 30;
            } catch (NoSuchFieldError nosuchfielderror29) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.KOREAN.ordinal()] = 31;
            } catch (NoSuchFieldError nosuchfielderror30) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.LATVIAN.ordinal()] = 32;
            } catch (NoSuchFieldError nosuchfielderror31) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.LETZEBUERGESCH.ordinal()] = 33;
            } catch (NoSuchFieldError nosuchfielderror32) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.LINGUA_LATINA.ordinal()] = 34;
            } catch (NoSuchFieldError nosuchfielderror33) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.LITHUANIAN.ordinal()] = 35;
            } catch (NoSuchFieldError nosuchfielderror34) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.MALAY.ordinal()] = 36;
            } catch (NoSuchFieldError nosuchfielderror35) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.MALTI.ordinal()] = 37;
            } catch (NoSuchFieldError nosuchfielderror36) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.NORWEGIAN.ordinal()] = 38;
            } catch (NoSuchFieldError nosuchfielderror37) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.OCCITAN.ordinal()] = 39;
            } catch (NoSuchFieldError nosuchfielderror38) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.POLISH.ordinal()] = 40;
            } catch (NoSuchFieldError nosuchfielderror39) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.PORTUGUESE.ordinal()] = 41;
            } catch (NoSuchFieldError nosuchfielderror40) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.QUENYA.ordinal()] = 42;
            } catch (NoSuchFieldError nosuchfielderror41) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.ROMANIAN.ordinal()] = 43;
            } catch (NoSuchFieldError nosuchfielderror42) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.RUSSIAN.ordinal()] = 44;
            } catch (NoSuchFieldError nosuchfielderror43) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.SERBIAN.ordinal()] = 45;
            } catch (NoSuchFieldError nosuchfielderror44) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.SLOVENIAN.ordinal()] = 46;
            } catch (NoSuchFieldError nosuchfielderror45) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.SPANISH.ordinal()] = 47;
            } catch (NoSuchFieldError nosuchfielderror46) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.SWEDISH.ordinal()] = 48;
            } catch (NoSuchFieldError nosuchfielderror47) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.TAGALOG.ordinal()] = 49;
            } catch (NoSuchFieldError nosuchfielderror48) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.THAI.ordinal()] = 50;
            } catch (NoSuchFieldError nosuchfielderror49) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.TURKISH.ordinal()] = 51;
            } catch (NoSuchFieldError nosuchfielderror50) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.UKRAINIAN.ordinal()] = 52;
            } catch (NoSuchFieldError nosuchfielderror51) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.VIETNAMESE.ordinal()] = 53;
            } catch (NoSuchFieldError nosuchfielderror52) {
                ;
            }

            try {
                LocaleMessage.SyntheticClass_1.$SwitchMap$jordan$sicherman$locales$Locale[Locale.tlhIngan_Hol.ordinal()] = 54;
            } catch (NoSuchFieldError nosuchfielderror53) {
                ;
            }

        }
    }
}
