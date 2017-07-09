package jordan.sicherman.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import jordan.sicherman.MyZ;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.particles.ParticleEffect;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.Configuration;
import jordan.sicherman.utilities.configuration.FileMember;
import jordan.sicherman.utilities.configuration.FileUtilities;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.DirectionalContainer;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestType {

    private static final List chestTypes = new ArrayList();
    private static final Random random = new Random();
    private final String key;
    private final Map contents = new HashMap();
    private ItemStack recent;

    public String getName() {
        return this.key;
    }

    public static ChestType[] values() {
        return (ChestType[]) ChestType.chestTypes.toArray(new ChestType[0]);
    }

    public static void respawnAll() {
        ConfigurationSection section = (ConfigurationSection) ConfigEntries.CHEST_LOCATIONS.getValue();
        Iterator iterator = section.getKeys(false).iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Location loc = SerializableLocation.deserialize(key);

            respawn(loc, true);
        }

    }

    public ItemStack[] generate() {
        ArrayList items = new ArrayList();

        while (items.isEmpty()) {
            Iterator iterator = this.contents.keySet().iterator();

            while (iterator.hasNext()) {
                ChestType.ItemProperties properties = (ChestType.ItemProperties) iterator.next();

                if (properties.prob > 0 && ChestType.random.nextInt(101) <= properties.prob) {
                    int amount = ChestType.random.nextInt(properties.max - properties.min + 1) + properties.min;

                    if (amount > 0) {
                        ItemStack item = ((ItemStack) this.contents.get(properties)).clone();

                        item.setAmount(amount);
                        items.add(item);
                    }
                }
            }
        }

        return (ItemStack[]) items.toArray(new ItemStack[0]);
    }

    public static ChestType getType(Block block) {
        ConfigurationSection section = (ConfigurationSection) ConfigEntries.CHEST_LOCATIONS.getValue();
        Iterator iterator = section.getKeys(false).iterator();

        String key;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            key = (String) iterator.next();
        } while (block.getLocation().distanceSquared(SerializableLocation.deserialize(key)) != 0.0D);

        return fromString(section.getString(key + ".type"));
    }

    public static ChestType fromString(String key) {
        ChestType[] achesttype = values();
        int i = achesttype.length;

        for (int j = 0; j < i; ++j) {
            ChestType type = achesttype[j];

            if (type.key.equals(key)) {
                return type;
            }
        }

        return null;
    }

    public static void despawn(Block block) {
        despawn(block, false);
    }

    public static void despawn(Block block, boolean force) {
        ConfigurationSection section = (ConfigurationSection) ConfigEntries.CHEST_LOCATIONS.getValue();
        String key = SerializableLocation.fromLocation(block.getLocation()).serialize().replaceAll("\\.0", "");
        boolean contains = section.contains(key);

        if (contains || force) {
            if (contains) {
                section.set(key + ".respawn_time", Long.valueOf(System.currentTimeMillis() + (long) (((Integer) ConfigEntries.CHEST_RESPAWN_TIME.getValue()).intValue() * 1000)));
            }

            if (block.getType() != Material.AIR) {
                block.setType(Material.AIR);
                final Location inLoc = block.getLocation();

                (new BukkitRunnable() {
                    public void run() {
                        ParticleEffect.CLOUD.display(0.0F, 0.0F, 0.0F, 0.025F, 25, inLoc, 20.0D);
                    }
                }).runTaskAsynchronously(MyZ.instance);
            }
        }

    }

    public static void respawn(final Location location, boolean force) {
        final ConfigurationSection section = (ConfigurationSection) ConfigEntries.CHEST_LOCATIONS.getValue();
        final String key = SerializableLocation.fromLocation(location).serialize().replaceAll("\\.0", "");

        if (section.contains(key)) {
            long respawn = section.getLong(key + ".respawn_time");

            if (respawn < 0L && !force) {
                return;
            }

            if (force || System.currentTimeMillis() >= respawn) {
                section.set(key + ".respawn_time", Integer.valueOf(-1));
                (new BukkitRunnable() {
                    public void run() {
                        Material matl = Material.valueOf(section.getString(key + ".material"));
                        Block block = location.getBlock();

                        if (block.getType() == matl) {
                            if (!ChestType.isEmpty(((Chest) block.getState()).getBlockInventory().getContents())) {
                                return;
                            }
                        } else {
                            block.setType(matl);
                        }

                        Chest chest = (Chest) block.getState();

                        ((DirectionalContainer) block.getState().getData()).setFacingDirection(BlockFace.valueOf(section.getString(key + ".orientation")));
                        CompatibilityManager.renameChest(chest, section.getString(key + ".type"));
                        block.getState().update(true);
                        ChestType type = ChestType.fromName(section.getString(key + ".type"));

                        if (type != null) {
                            chest.getBlockInventory().addItem(type.generate());
                        }

                    }
                }).runTaskLater(MyZ.instance, 0L);
            }
        }

    }

    public static void setType(Block block, ChestType type) {
        ConfigurationSection section = (ConfigurationSection) ConfigEntries.CHEST_LOCATIONS.getValue();
        String key = SerializableLocation.fromLocation(block.getLocation()).serialize().replaceAll("\\.0", "");

        if (type == null) {
            section.set(key, (Object) null);
        } else {
            section.set(key + ".material", block.getType().toString());
            section.set(key + ".orientation", ((DirectionalContainer) block.getState().getData()).getFacing().name());
            section.set(key + ".type", type.key);
            section.set(key + ".respawn_time", Integer.valueOf(-1));
        }

        FileUtilities.save(new FileMember[] { Configuration.CFiles.CHESTS});
        respawn(block.getLocation(), true);
    }

    public static ChestType fromName(String name) {
        ChestType[] achesttype = values();
        int i = achesttype.length;

        for (int j = 0; j < i; ++j) {
            ChestType type = achesttype[j];

            if (type.key.equals(name)) {
                return type;
            }
        }

        return null;
    }

    public ChestType(String key) {
        this.key = key;
        ChestType.chestTypes.add(this);
        ConfigurationSection section = ((ConfigurationSection) ConfigEntries.CHEST_TYPES.getValue()).getConfigurationSection(key);
        ItemStack item;
        ConfigurationSection subsection;

        if (section != null) {
            for (Iterator iterator = section.getKeys(false).iterator(); iterator.hasNext(); this.contents.put(new ChestType.ItemProperties(subsection.getInt("amount_minimum"), subsection.getInt("amount_maximum"), subsection.getInt("probability")), item)) {
                String cKey = (String) iterator.next();

                item = section.getConfigurationSection(cKey).getItemStack("item").clone();
                subsection = section.getConfigurationSection(cKey);
                if (subsection.isSet("probablity")) {
                    subsection.set("probability", subsection.get("probablity"));
                    subsection.set("probablity", (Object) null);
                    FileUtilities.save(new FileMember[] { ConfigEntries.CHEST_TYPES.getFile()});
                }
            }
        }

    }

    public static void load() {
        ChestType.chestTypes.clear();
        Iterator iterator = ((ConfigurationSection) ConfigEntries.CHEST_TYPES.getValue()).getKeys(false).iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            new ChestType(key);
        }

    }

    public static ChestType nextType(ChestType type) {
        if (values().length == 0) {
            return null;
        } else if (type == null) {
            return (ChestType) ChestType.chestTypes.get(0);
        } else {
            for (int i = 0; i <= values().length - 2; ++i) {
                if (type.equals(values()[i])) {
                    return values()[i + 1];
                }
            }

            return null;
        }
    }

    public static void create(String name) {
        new ChestType(name);
    }

    public void addItem(ItemStack item) {
        this.recent = item.clone();
    }

    public void setRecentProperties(int min, int max, int prob) {
        if (this.recent != null) {
            this.contents.put(new ChestType.ItemProperties(min, max, prob), this.recent);
            String uuid = UUID.randomUUID().toString();
            ConfigurationSection section = ((ConfigurationSection) ConfigEntries.CHEST_TYPES.getValue()).getConfigurationSection(this.key);

            if (section == null) {
                section = ((ConfigurationSection) ConfigEntries.CHEST_TYPES.getValue()).createSection(this.key);
            }

            section.set(uuid + ".probability", Integer.valueOf(prob));
            section.set(uuid + ".amount_minimum", Integer.valueOf(min));
            section.set(uuid + ".amount_maximum", Integer.valueOf(max));
            section.set(uuid + ".item", this.recent);
            FileUtilities.save(new FileMember[] { Configuration.CFiles.CHESTS});
            this.recent = null;
        }

    }

    public void remove() {
        ConfigurationSection chests = (ConfigurationSection) ConfigEntries.CHEST_LOCATIONS.getValue();
        Iterator iterator = (new HashSet(chests.getKeys(false))).iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            if (key.equals(chests.get(key + ".type"))) {
                respawn(SerializableLocation.deserialize(chests.getString(key)), true);
                chests.set(key, (Object) null);
            }
        }

        ((ConfigurationSection) ConfigEntries.CHEST_TYPES.getValue()).set(this.key, (Object) null);
        FileUtilities.save(new FileMember[] { Configuration.CFiles.CHESTS});
    }

    public static boolean isEmpty(ItemStack[] contents) {
        ItemStack[] aitemstack = contents;
        int i = contents.length;

        for (int j = 0; j < i; ++j) {
            ItemStack item = aitemstack[j];

            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }

        return true;
    }

    private static class ItemProperties {

        final int min;
        final int max;
        final int prob;

        public ItemProperties(int min, int max, int prob) {
            this.min = min;
            this.max = max;
            this.prob = prob;
        }
    }
}
