package jordan.sicherman.particles;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public enum ParticleEffect {

    EXPLOSION_NORMAL("explode", 0, -1), EXPLOSION_LARGE("largeexplode", 1, -1), EXPLOSION_HUGE("hugeexplosion", 2, -1), FIREWORKS_SPARK("fireworksSpark", 3, -1), WATER_BUBBLE("bubble", 4, -1, false, true), WATER_SPLASH("splash", 5, -1), WATER_WAKE("wake", 6, 7), SUSPENDED("suspended", 7, -1, false, true), SUSPENDED_DEPTH("depthSuspend", 8, -1), CRIT("crit", 9, -1), CRIT_MAGIC("magicCrit", 10, -1), SMOKE_NORMAL("smoke", 11, -1), SMOKE_LARGE("largesmoke", 12, -1), SPELL("spell", 13, -1), SPELL_INSTANT("instantSpell", 14, -1), SPELL_MOB("mobSpell", 15, -1), SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1), SPELL_WITCH("witchMagic", 17, -1), DRIP_WATER("dripWater", 18, -1), DRIP_LAVA("dripLava", 19, -1), VILLAGER_ANGRY("angryVillager", 20, -1), VILLAGER_HAPPY("happyVillager", 21, -1), TOWN_AURA("townaura", 22, -1), NOTE("note", 23, -1), PORTAL("portal", 24, -1), ENCHANTMENT_TABLE("enchantmenttable", 25, -1), FLAME("flame", 26, -1), LAVA("lava", 27, -1), FOOTSTEP("footstep", 28, -1), CLOUD("cloud", 29, -1), REDSTONE("reddust", 30, -1), SNOWBALL("snowballpoof", 31, -1), SNOW_SHOVEL("snowshovel", 32, -1), SLIME("slime", 33, -1), HEART("heart", 34, -1), BARRIER("barrier", 35, 8), ITEM_CRACK("iconcrack", 36, -1, true), BLOCK_CRACK("blockcrack", 37, -1, true), BLOCK_DUST("blockdust", 38, 7, true), WATER_DROP("droplet", 39, 8), ITEM_TAKE("take", 40, 8), MOB_APPEARANCE("mobappearance", 41, 8);

    private static final Map NAME_MAP = new HashMap();
    private static final Map ID_MAP = new HashMap();
    private final String name;
    private final int id;
    private final int requiredVersion;
    private final boolean requiresData;
    private final boolean requiresWater;

    private ParticleEffect(String name, int id, int requiredVersion, boolean requiresData, boolean requiresWater) {
        this.name = name;
        this.id = id;
        this.requiredVersion = requiredVersion;
        this.requiresData = requiresData;
        this.requiresWater = requiresWater;
    }

    private ParticleEffect(String name, int id, int requiredVersion, boolean requiresData) {
        this(name, id, requiredVersion, requiresData, false);
    }

    private ParticleEffect(String name, int id, int requiredVersion) {
        this(name, id, requiredVersion, false);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getRequiredVersion() {
        return this.requiredVersion;
    }

    public boolean getRequiresData() {
        return this.requiresData;
    }

    public boolean getRequiresWater() {
        return this.requiresWater;
    }

    public boolean isSupported() {
        return this.requiredVersion == -1 ? true : ParticleEffect.ParticlePacket.version >= this.requiredVersion;
    }

    public static ParticleEffect fromName(String name) {
        Iterator iterator = ParticleEffect.NAME_MAP.entrySet().iterator();

        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entry = (Entry) iterator.next();
        } while (!((String) entry.getKey()).equalsIgnoreCase(name));

        return (ParticleEffect) entry.getValue();
    }

    public static ParticleEffect fromId(int id) {
        Iterator iterator = ParticleEffect.ID_MAP.entrySet().iterator();

        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entry = (Entry) iterator.next();
        } while (((Integer) entry.getKey()).intValue() != id);

        return (ParticleEffect) entry.getValue();
    }

    private static boolean isWater(Location location) {
        Material material = location.getBlock().getType();

        return material == Material.WATER || material == Material.STATIONARY_WATER;
    }

    private static boolean isLongDistance(Location location, List players) {
        Iterator iterator = players.iterator();

        Player player;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            player = (Player) iterator.next();
        } while (player.getLocation().distanceSquared(location) < 65536.0D);

        return true;
    }

    private static boolean isDataCorrect(ParticleEffect effect, ParticleEffect.ParticleData data) {
        return (effect == ParticleEffect.BLOCK_CRACK || effect == ParticleEffect.BLOCK_DUST) && data instanceof ParticleEffect.BlockData || effect == ParticleEffect.ITEM_CRACK && data instanceof ParticleEffect.ItemData;
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        if (!this.isSupported()) {
            throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
        } else if (this.requiresData) {
            throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
        } else if (this.requiresWater && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        } else {
            (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256.0D, (ParticleEffect.ParticleData) null)).sendTo(center, range);
        }
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        if (!this.isSupported()) {
            throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
        } else if (this.requiresData) {
            throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
        } else if (this.requiresWater && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        } else {
            (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), (ParticleEffect.ParticleData) null)).sendTo(center, players);
        }
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        this.display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(Vector direction, float speed, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        if (!this.isSupported()) {
            throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
        } else if (this.requiresData) {
            throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
        } else if (this.requiresWater && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        } else {
            (new ParticleEffect.ParticlePacket(this, direction, speed, range > 256.0D, (ParticleEffect.ParticleData) null)).sendTo(center, range);
        }
    }

    public void display(Vector direction, float speed, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        if (!this.isSupported()) {
            throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
        } else if (this.requiresData) {
            throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
        } else if (this.requiresWater && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        } else {
            (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), (ParticleEffect.ParticleData) null)).sendTo(center, players);
        }
    }

    public void display(Vector direction, float speed, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        this.display(direction, speed, center, Arrays.asList(players));
    }

    public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        if (!this.isSupported()) {
            throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
        } else if (!this.requiresData) {
            throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
        } else if (!isDataCorrect(this, data)) {
            throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256.0D, data)).sendTo(center, range);
        }
    }

    public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        if (!this.isSupported()) {
            throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
        } else if (!this.requiresData) {
            throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
        } else if (!isDataCorrect(this, data)) {
            throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data)).sendTo(center, players);
        }
    }

    public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        this.display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        if (!this.isSupported()) {
            throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
        } else if (!this.requiresData) {
            throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
        } else if (!isDataCorrect(this, data)) {
            throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, direction, speed, range > 256.0D, data)).sendTo(center, range);
        }
    }

    public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        if (!this.isSupported()) {
            throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
        } else if (!this.requiresData) {
            throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
        } else if (!isDataCorrect(this, data)) {
            throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), data)).sendTo(center, players);
        }
    }

    public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        this.display(data, direction, speed, center, Arrays.asList(players));
    }

    static {
        ParticleEffect[] aparticleeffect = values();
        int i = aparticleeffect.length;

        for (int j = 0; j < i; ++j) {
            ParticleEffect effect = aparticleeffect[j];

            ParticleEffect.NAME_MAP.put(effect.name, effect);
            ParticleEffect.ID_MAP.put(Integer.valueOf(effect.id), effect);
        }

    }

    public static final class ParticlePacket {

        private static int version;
        private static Class enumParticle;
        private static Constructor packetConstructor;
        private static Method getHandle;
        private static Field playerConnection;
        private static Method sendPacket;
        private static boolean initialized;
        private final ParticleEffect effect;
        private final float offsetX;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private final boolean longDistance;
        private final ParticleEffect.ParticleData data;
        private Object packet;

        public ParticlePacket(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleEffect.ParticleData data) throws IllegalArgumentException {
            initialize();
            if (speed < 0.0F) {
                throw new IllegalArgumentException("The speed is lower than 0");
            } else if (amount < 1) {
                throw new IllegalArgumentException("The amount is lower than 1");
            } else {
                this.effect = effect;
                this.offsetX = offsetX;
                this.offsetY = offsetY;
                this.offsetZ = offsetZ;
                this.speed = speed;
                this.amount = amount;
                this.longDistance = longDistance;
                this.data = data;
            }
        }

        public ParticlePacket(ParticleEffect effect, Vector direction, float speed, boolean longDistance, ParticleEffect.ParticleData data) throws IllegalArgumentException {
            initialize();
            if (speed < 0.0F) {
                throw new IllegalArgumentException("The speed is lower than 0");
            } else {
                this.effect = effect;
                this.offsetX = (float) direction.getX();
                this.offsetY = (float) direction.getY();
                this.offsetZ = (float) direction.getZ();
                this.speed = speed;
                this.amount = 0;
                this.longDistance = longDistance;
                this.data = data;
            }
        }

        public static void initialize() throws ParticleEffect.ParticlePacket.VersionIncompatibleException {
            if (!ParticleEffect.ParticlePacket.initialized) {
                try {
                    ParticleEffect.ParticlePacket.version = Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(3)));
                    if (ParticleEffect.ParticlePacket.version > 7) {
                        ParticleEffect.ParticlePacket.enumParticle = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
                    }

                    Class exception = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass(ParticleEffect.ParticlePacket.version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");

                    ParticleEffect.ParticlePacket.packetConstructor = ReflectionUtils.getConstructor(exception, new Class[0]);
                    ParticleEffect.ParticlePacket.getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle", new Class[0]);
                    ParticleEffect.ParticlePacket.playerConnection = ReflectionUtils.getField("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, "playerConnection");
                    ParticleEffect.ParticlePacket.sendPacket = ReflectionUtils.getMethod(ParticleEffect.ParticlePacket.playerConnection.getType(), "sendPacket", new Class[] { ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet")});
                } catch (Exception exception) {
                    throw new ParticleEffect.ParticlePacket.VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", exception);
                }

                ParticleEffect.ParticlePacket.initialized = true;
            }
        }

        public static int getVersion() {
            return ParticleEffect.ParticlePacket.version;
        }

        public static boolean isInitialized() {
            return ParticleEffect.ParticlePacket.initialized;
        }

        public void sendTo(Location center, Player player) throws ParticleEffect.ParticlePacket.PacketInstantiationException, ParticleEffect.ParticlePacket.PacketSendingException {
            if (this.packet == null) {
                try {
                    this.packet = ParticleEffect.ParticlePacket.packetConstructor.newInstance(new Object[0]);
                    Object exception;

                    if (ParticleEffect.ParticlePacket.version < 8) {
                        exception = this.effect.getName() + (this.data == null ? "" : this.data.getPacketDataString());
                    } else {
                        exception = ParticleEffect.ParticlePacket.enumParticle.getEnumConstants()[this.effect.getId()];
                    }

                    ReflectionUtils.setValue(this.packet, true, "a", exception);
                    ReflectionUtils.setValue(this.packet, true, "b", Float.valueOf((float) center.getX()));
                    ReflectionUtils.setValue(this.packet, true, "c", Float.valueOf((float) center.getY()));
                    ReflectionUtils.setValue(this.packet, true, "d", Float.valueOf((float) center.getZ()));
                    ReflectionUtils.setValue(this.packet, true, "e", Float.valueOf(this.offsetX));
                    ReflectionUtils.setValue(this.packet, true, "f", Float.valueOf(this.offsetY));
                    ReflectionUtils.setValue(this.packet, true, "g", Float.valueOf(this.offsetZ));
                    ReflectionUtils.setValue(this.packet, true, "h", Float.valueOf(this.speed));
                    ReflectionUtils.setValue(this.packet, true, "i", Integer.valueOf(this.amount));
                    if (ParticleEffect.ParticlePacket.version > 7) {
                        ReflectionUtils.setValue(this.packet, true, "j", Boolean.valueOf(this.longDistance));
                        ReflectionUtils.setValue(this.packet, true, "k", this.data == null ? new int[0] : this.data.getPacketData());
                    }
                } catch (Exception exception) {
                    throw new ParticleEffect.ParticlePacket.PacketInstantiationException("Packet instantiation failed", exception);
                }
            }

            try {
                ParticleEffect.ParticlePacket.sendPacket.invoke(ParticleEffect.ParticlePacket.playerConnection.get(ParticleEffect.ParticlePacket.getHandle.invoke(player, new Object[0])), new Object[] { this.packet});
            } catch (Exception exception1) {
                throw new ParticleEffect.ParticlePacket.PacketSendingException("Failed to send the packet to player \'" + player.getName() + "\'", exception1);
            }
        }

        public void sendTo(Location center, List players) throws IllegalArgumentException {
            if (players.isEmpty()) {
                throw new IllegalArgumentException("The player list is empty");
            } else {
                Iterator iterator = players.iterator();

                while (iterator.hasNext()) {
                    Player player = (Player) iterator.next();

                    this.sendTo(center, player);
                }

            }
        }

        public void sendTo(Location center, double range) throws IllegalArgumentException {
            if (range < 1.0D) {
                throw new IllegalArgumentException("The range is lower than 1");
            } else {
                String worldName = center.getWorld().getName();
                double squared = range * range;
                Iterator iterator = Bukkit.getOnlinePlayers().iterator();

                while (iterator.hasNext()) {
                    Player player = (Player) iterator.next();

                    if (player.getWorld().getName().equals(worldName) && player.getLocation().distanceSquared(center) <= squared) {
                        this.sendTo(center, player);
                    }
                }

            }
        }

        private static final class PacketSendingException extends RuntimeException {

            private static final long serialVersionUID = 3203085387160737484L;

            public PacketSendingException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        private static final class PacketInstantiationException extends RuntimeException {

            private static final long serialVersionUID = 3203085387160737484L;

            public PacketInstantiationException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        private static final class VersionIncompatibleException extends RuntimeException {

            private static final long serialVersionUID = 3203085387160737484L;

            public VersionIncompatibleException(String message, Throwable cause) {
                super(message, cause);
            }
        }
    }

    private static final class ParticleVersionException extends RuntimeException {

        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleVersionException(String message) {
            super(message);
        }
    }

    private static final class ParticleDataException extends RuntimeException {

        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleDataException(String message) {
            super(message);
        }
    }

    public static final class BlockData extends ParticleEffect.ParticleData {

        public BlockData(Material material, byte data) throws IllegalArgumentException {
            super(material, data);
            if (!material.isBlock()) {
                throw new IllegalArgumentException("The material is not a block");
            }
        }
    }

    public static final class ItemData extends ParticleEffect.ParticleData {

        public ItemData(Material material, byte data) {
            super(material, data);
        }
    }

    public abstract static class ParticleData {

        private final Material material;
        private final byte data;
        private final int[] packetData;

        public ParticleData(Material material, byte data) {
            this.material = material;
            this.data = data;
            this.packetData = new int[] { material.getId(), data};
        }

        public Material getMaterial() {
            return this.material;
        }

        public byte getData() {
            return this.data;
        }

        public int[] getPacketData() {
            return this.packetData;
        }

        public String getPacketDataString() {
            return "_" + this.packetData[0] + "_" + this.packetData[1];
        }
    }
}
