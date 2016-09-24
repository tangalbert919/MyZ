package jordan.sicherman;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import jordan.sicherman.api.DataManager;
import jordan.sicherman.api.MyZAPI;
import jordan.sicherman.commands.CommandManager;
import jordan.sicherman.items.EngineerManager;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.listeners.Extras;
import jordan.sicherman.listeners.player.Chat;
import jordan.sicherman.listeners.player.Death;
import jordan.sicherman.listeners.player.GearModifications;
import jordan.sicherman.listeners.player.GetAchievement;
import jordan.sicherman.listeners.player.Grappler;
import jordan.sicherman.listeners.player.Join;
import jordan.sicherman.listeners.player.Quit;
import jordan.sicherman.listeners.player.SpectatorMode;
import jordan.sicherman.listeners.player.TakeDamage;
import jordan.sicherman.locales.Locale;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.nms.utilities.NMS;
import jordan.sicherman.nms.utilities.PseudoEnchant;
import jordan.sicherman.scheduled.Asynchronous;
import jordan.sicherman.scheduled.Synchronous;
import jordan.sicherman.utilities.AchievementManager;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.MyZRank;
import jordan.sicherman.utilities.StartingKitManager;
import jordan.sicherman.utilities.ThirstManager;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MyZ extends JavaPlugin {

    public static MyZ instance;
    public static MyZAPI myzapi;
    public static Configuration configuration;
    private static Enchantment pseudoEnchant = new PseudoEnchant(69);
    private static final Set listeners = new HashSet();
    private static final Random random = new Random();

    public void onEnable() {
        this.getDataFolder().mkdir();
        MyZ.instance = this;
        Locale.load(new Locale[0]);
        new ItemUtilities();
        MyZ.configuration = new Configuration();
        ChestType.load();
        new EngineerManager();
        new ThirstManager();
        (new AchievementManager()).reload();
        new DataManager();
        new StartingKitManager();
        MyZ.myzapi = new MyZAPI();
        MyZRank.load();
        if (((Boolean) ConfigEntries.UPDATE.getValue()).booleanValue()) {
            new Updater(this, '\ud905', this.getFile(), Updater.UpdateType.DEFAULT, false);
        }

        if (NMS.compatibilityVersion == null) {
            log(ChatColor.RED + LocaleMessage.INCOMPATIBLE.toString());
            this.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
                public void run() {
                    MyZ.this.getServer().getPluginManager().disablePlugin(MyZ.instance);
                }
            }, 0L);
        } else {
            registerPseudoEnchantment();
            EngineerManager.getInstance().reload();
            CompatibilityManager.registerEntities();
            this.getCommand("MyZ").setExecutor(new CommandManager());
            PluginManager pm = this.getServer().getPluginManager();

            MyZ.listeners.addAll(Arrays.asList(new Listener[] { new Join(), new Quit(), new Extras(), new Chat(), new Death(), new SpectatorMode(), new Grappler(), new GetAchievement(), new TakeDamage()}));
            if (((Boolean) ConfigEntries.USE_ENHANCED_ANVILS.getValue()).booleanValue()) {
                MyZ.listeners.add(new GearModifications());
            }

            Iterator exc = MyZ.listeners.iterator();

            while (exc.hasNext()) {
                Listener listener = (Listener) exc.next();

                pm.registerEvents(listener, this);
            }

            (new Asynchronous()).runTaskTimerAsynchronously(MyZ.instance, 0L, (long) ((Integer) ConfigEntries.TASK_SPEED.getValue()).intValue() * 1L);
            (new Synchronous()).runTaskTimer(MyZ.instance, (long) ((Integer) ConfigEntries.SAVE_SPEED.getValue()).intValue() * 1L, (long) ((Integer) ConfigEntries.SAVE_SPEED.getValue()).intValue() * 1L);
            this.getServer().addRecipe(new FurnaceRecipe(ItemUtilities.getInstance().getTagItem(ItemTag.WARM_WATER, 1), Material.POTION));

            try {
                MetricsLite exc1 = new MetricsLite(this);

                exc1.start();
            } catch (Exception exception) {
                ;
            }

            giveMyZTip(Bukkit.getConsoleSender());
        }
    }

    public void onDisable() {
        Synchronous.save();
        this.getServer().getScheduler().cancelTasks(this);
        Iterator iterator = MyZ.listeners.iterator();

        while (iterator.hasNext()) {
            Listener id = (Listener) iterator.next();

            HandlerList.unregisterAll(id);
        }

        MyZ.listeners.clear();
        iterator = ((List) ConfigEntries.WORLDS.getValue()).iterator();

        while (iterator.hasNext()) {
            String id1 = (String) iterator.next();
            World world = Bukkit.getWorld(id1);
            Iterator iterator1 = world.getEntities().iterator();

            while (iterator1.hasNext()) {
                Entity entity = (Entity) iterator1.next();

                if (entity.getType() == EntityType.SKELETON || entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.PIG_ZOMBIE || entity.getType() == EntityType.GIANT) {
                    entity.remove();
                }
            }
        }

        CompatibilityManager.unregisterEntities();
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[MyZ 4] " + ChatColor.RESET + message);
    }

    private static boolean registerPseudoEnchantment() {
        try {
            Field e = Enchantment.class.getDeclaredField("acceptingNew");

            e.setAccessible(true);
            e.set((Object) null, Boolean.valueOf(true));

            try {
                Enchantment.registerEnchantment(MyZ.pseudoEnchant);
                return true;
            } catch (IllegalArgumentException illegalargumentexception) {
                ;
            }
        } catch (Exception exception) {
            ;
        }

        return false;
    }

    public static Enchantment getPseudoEnchantment() {
        return MyZ.pseudoEnchant;
    }

    public static void giveMyZTip(final CommandSender sender) {
        MyZ.instance.getServer().getScheduler().runTaskLaterAsynchronously(MyZ.instance, new Runnable() {
            public void run() {
                String msg = MyZ.Tip.forInt(MyZ.random.nextInt(MyZ.Tip.values().length)).toString();

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }, 100L);
    }

    private static enum Tip {

        A(0, "&eOctober 8th is &4World Zombie Day&e!"), B(1, "&4MyZ&e is a spinoff on &4MineZ&e (which is a spinoff on &4DayZ&e!)"), C(2, "&eThis server is running &4MyZ 4.0&e. Download it for your server at http://dev.bukkit.org/bukkit-plugins/MyZ"), D(3, "&eThere is a law in Haiti that makes it &4illegal&e to turn someone into a zombie."), E(4, "&eMyZ is highly configurable, with &6" + ConfigEntries.values().length + "&e configuration options spanning &6" + Configuration.CFiles.values().length + "&e files!"), F(5, "&eThe abbreviation &4RLF&e stands for &4reanimated life form&e - another way of saying &4zombie&e!"), G(6, "&eIn a fight between a &4zombie&e and a &4vampire&e, the &4vampire would most likely win."), H(7, "&eScientists claim that a zombie apocalypse &oisn\'t actually an impossibility&r&e."), I(8, "&eThe top 5 safest countries during an apocalypse:\n&65) Kazakhstan\n&64) Russia\n&63) U.S.\n&62) Canada\n&61) Australia"), J(9, "&4Haiti&e is the source of most modern zombie stories."), K(10, "&4Shaun of the Dead&e is a 2004 &orom zom com&r&e (also a &ozomedy&r&e!)"), L(11, "&eThe very first zombie movie ever made was the 1932 film &oWhite Zombie"), M(12, "&eFor $4, you can join real-world zombie survivalist organization &4ZCORE&e - &oZombie Coalition Offensive Response Elite&r&e."), N(13, "&eMyZ has &6" + LocaleMessage.values().length + "&e localizable messages that automatically adapt to one of &6" + Locale.values().length + " &elanguages!");

        private final int id;
        private final String message;

        private Tip(int id, String message) {
            this.id = id;
            this.message = message;
        }

        private static MyZ.Tip forInt(int id) {
            MyZ.Tip[] amyz_tip = values();
            int i = amyz_tip.length;

            for (int j = 0; j < i; ++j) {
                MyZ.Tip tip = amyz_tip[j];

                if (tip.id == id) {
                    return tip;
                }
            }

            return MyZ.Tip.A;
        }

        public String toString() {
            return this.message;
        }
    }
}
