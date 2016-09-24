package jordan.sicherman.locales;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import jordan.sicherman.MyZ;
import jordan.sicherman.nms.utilities.NMS;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public enum Locale {

    AFRIKAANS("Afrikaans", "af_ZA"), ARABIC("العربية", "ar_SA"), BULGARIAN("Българ�?ки", "bg_BG"), CATALAN("Català", "ca_ES"), CZECH("Čeština", "cs_CZ"), CYMRAEG("Cymraeg", "cy_GB"), DANISH("Dansk", "da_DK"), GERMAN("Deutsch", "de_DE"), GREEK("Ελληνικά", "el_GR"), ENGLISH("English", "en_CA"), PIRATE_SPEAK("Pirate Speak", "en_PT"), ESPERANTO("Esperanto", "eo_EO"), SPANISH("Espanol", "es_ES"), ESTONIAN("Eesti", "et_EE"), EUSKARA("Euskara", "eu_ES"), FINNISH("Suomi", "fi_FI"), TAGALOG("Tagalog", "fil_PH"), FRENCH("Francais", "fr_FR"), GAEILGE("Gaeilge", "ga_IE"), GALICIAN("Galego", "gl_ES"), HEBREW("עברית", "he_IL"), CROATIAN("Hrvatski", "hr_HR"), HUNGARIAN("Magyar", "hu_HU"), ARMENIAN("Հայերեն", "hy_AM"), BAHASA_INDONESIA("Bahasa Indonesia", "id_ID"), ICELANDIC("�?slenska", "is_IS"), ITALIAN("Italiano", "it_IT"), JAPANESE("日本語", "ja_JP"), GEORGIAN("ქ�?რთული", "ka_GE"), KOREAN("한국어", "ko_KR"), KERNEWEK("Kernewek", "kw_GB"), LINGUA_LATINA("Lingua latina", "la_LA"), LETZEBUERGESCH("Lëtzebuergesch", "lb_LU"), LITHUANIAN("Lietuvių", "lt_LT"), LATVIAN("Latviešu", "lv_LV"), MALAY("Bahasa Melayu", "mi_NZ"), MALTI("Malti", "mt_MT"), NORWEGIAN("Norsk", "nb_NO"), DUTCH("Nederlands", "nl_NL"), OCCITAN("Occitan", "oc_FR"), PORTUGUESE("Português", "pt_BR"), QUENYA("Quenya", "qya_AA"), ROMANIAN("Română", "ro_RO"), RUSSIAN("Ру�?�?кий", "ru_RU"), SLOVENIAN("Slovenš�?ina", "sl_SI"), SERBIAN("Срп�?ки", "sr_SP"), SWEDISH("Svenska", "sv_SE"), THAI("ภาษาไทย", "th_TH"), tlhIngan_Hol("tlhIngan Hol", "tlh_AA"), TURKISH("Türkçe", "tr_TR"), UKRAINIAN("Україн�?ька", "uk_UA"), VIETNAMESE("Tiếng Việt", "vi_VI"), CHINESE("简体中文", "zh_CN"), POLISH("Polski", "pl_PL");

    private static final String[] spanishVariants = new String[] { "es_AR", "es_MX", "es_UY", "es_VE"};
    private static final String[] frenchVariants = new String[] { "fr_CA"};
    private static final String[] norwegianVariants = new String[] { "no_NO", "nn_NO"};
    private static final String[] malayVariants = new String[] { "ms_MY"};
    private static final String[] portugueseVariants = new String[] { "pt_PT"};
    private static final String[] chineseVariants = new String[] { "zh_TW"};
    private static final String path = File.separator + "locales" + File.separator + "$0";
    private static final Map configs = new HashMap();
    private String name;
    private String code;
    private static Field field;

    private Locale(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static Locale getByCode(String code) {
        Locale[] alocale = values();
        int i = alocale.length;

        int j;

        for (j = 0; j < i; ++j) {
            Locale s = alocale[j];

            if (s.getCode().equalsIgnoreCase(code)) {
                return s;
            }
        }

        String[] astring = Locale.spanishVariants;

        i = astring.length;

        String s;

        for (j = 0; j < i; ++j) {
            s = astring[j];
            if (s.equalsIgnoreCase(code)) {
                return Locale.SPANISH;
            }
        }

        astring = Locale.frenchVariants;
        i = astring.length;

        for (j = 0; j < i; ++j) {
            s = astring[j];
            if (s.equalsIgnoreCase(code)) {
                return Locale.FRENCH;
            }
        }

        astring = Locale.norwegianVariants;
        i = astring.length;

        for (j = 0; j < i; ++j) {
            s = astring[j];
            if (s.equalsIgnoreCase(code)) {
                return Locale.NORWEGIAN;
            }
        }

        astring = Locale.malayVariants;
        i = astring.length;

        for (j = 0; j < i; ++j) {
            s = astring[j];
            if (s.equalsIgnoreCase(code)) {
                return Locale.MALAY;
            }
        }

        astring = Locale.portugueseVariants;
        i = astring.length;

        for (j = 0; j < i; ++j) {
            s = astring[j];
            if (s.equalsIgnoreCase(code)) {
                return Locale.PORTUGUESE;
            }
        }

        astring = Locale.chineseVariants;
        i = astring.length;

        for (j = 0; j < i; ++j) {
            s = astring[j];
            if (s.equalsIgnoreCase(code)) {
                return Locale.CHINESE;
            }
        }

        return Locale.ENGLISH;
    }

    public static Locale getLocale(CommandSender playerFor) {
        if (!(playerFor instanceof Player)) {
            return getByCode((String) ConfigEntries.LANGUAGE.getValue());
        } else {
            try {
                Object exc = NMS.castToNMS((Player) playerFor);

                if (Locale.field == null) {
                    Locale.field = exc.getClass().getDeclaredField("locale");
                    Locale.field.setAccessible(true);
                }

                Locale code = getByCode((String) Locale.field.get(exc));

                return code;
            } catch (Exception exception) {
                exception.printStackTrace();
                return getByCode("en_CA");
            }
        }
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static void load(Locale... locales) {
        if (locales.length == 0) {
            load(values());
        } else {
            File folder = new File(MyZ.instance.getDataFolder() + Locale.path.replace("$0", ""));

            if (!folder.exists()) {
                folder.mkdirs();
            }

            Locale[] alocale = locales;
            int i = locales.length;

            for (int j = 0; j < i; ++j) {
                Locale locale = alocale[j];
                File f = new File(MyZ.instance.getDataFolder() + Locale.path.replace("$0", locale.getCode()) + ".yml");

                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (IOException ioexception) {
                        MyZ.log(ChatColor.RED + "Unable to save locale " + locale.getCode() + ": " + ioexception.getMessage());
                    }
                }

                YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

                loadDefaults(config, locale, f);
                Locale.configs.put(locale, config);
            }

        }
    }

    public static Map getLocales() {
        return new HashMap(Locale.configs);
    }

    public static FileConfiguration getConfigurationFor(Locale locale) {
        return (FileConfiguration) Locale.configs.get(locale);
    }

    private static void loadDefaults(FileConfiguration config, Locale locale, File saveTo) {
        LocaleMessage[] e = LocaleMessage.values();
        int i = e.length;

        for (int j = 0; j < i; ++j) {
            LocaleMessage lm = e[j];

            if (!config.isSet(lm.getKey())) {
                config.set(lm.getKey(), lm.getDefaultMessage(locale));
            }
        }

        try {
            config.save(saveTo);
        } catch (Exception exception) {
            MyZ.log(ChatColor.RED + "Unable to save locale " + locale.getCode() + ": " + exception.getMessage());
        }

    }
}
