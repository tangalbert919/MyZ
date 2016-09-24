package jordan.sicherman.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import jordan.sicherman.MyZ;
import jordan.sicherman.api.PlayerJoinMyZEvent;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.particles.ParticleEffect;
import jordan.sicherman.player.User;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Utilities {

    private static final BlockFace[] cardinal = new BlockFace[] { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};
    private static final Random random = new Random();

    public static boolean inWorld(Player playerFor) {
        return inWorld(playerFor.getWorld());
    }

    public static boolean inWorld(Entity entityFor) {
        return inWorld(entityFor.getWorld());
    }

    public static boolean inWorld(World worldFor) {
        return inWorld(worldFor.getName());
    }

    public static boolean inWorld(Location locationFor) {
        return inWorld(locationFor.getWorld());
    }

    private static boolean inWorld(String world) {
        return ((List) ConfigEntries.WORLDS.getValue()).contains(world);
    }

    public static void doLogin(Player player) {
        User.forPlayer(player);
        MyZ.instance.getServer().getPluginManager().callEvent(new PlayerJoinMyZEvent(player));
        if (!player.isOp()) {
            MyZ.giveMyZTip(player);
        }

        if (((Boolean) ConfigEntries.DEDICATED.getValue()).booleanValue()) {
            spawnInWorld(player, true);
        }

    }

    public static void doLogout(Player player) {
        User.freePlayer(player);
    }

    public static void setPoisoned(Player playerFor, boolean poisoned, boolean force) {
        if (((Boolean) ConfigEntries.USE_POISON.getValue()).booleanValue()) {
            if (!poisoned) {
                if (((Boolean) DataWrapper.get(playerFor, UserEntries.POISONED)).booleanValue()) {
                    DataWrapper.set((OfflinePlayer) playerFor, UserEntries.POISONED, Boolean.valueOf(false));
                    playerFor.removePotionEffect(PotionEffectType.CONFUSION);
                    if (!force) {
                        playerFor.sendMessage(LocaleMessage.INFECTION_ENDED.toString((CommandSender) playerFor));
                    }
                }

            } else {
                if (!((Boolean) DataWrapper.get(playerFor, UserEntries.POISONED)).booleanValue()) {
                    double immunityLevel = (double) ((Integer) DataWrapper.get(playerFor, UserEntries.IMMUNITY)).intValue();
                    double percentChance = 100.0D;

                    if (immunityLevel < 4.0D) {
                        percentChance = 100.0D - 25.0D * immunityLevel;
                    } else {
                        percentChance = 25.0D / (2.0D * (immunityLevel - 3.0D));
                    }

                    if (((Boolean) DataWrapper.get(playerFor, UserEntries.BLEEDING)).booleanValue()) {
                        percentChance *= 2.0D;
                    }

                    if (Utilities.random.nextDouble() <= percentChance / 100.0D) {
                        DataWrapper.set((OfflinePlayer) playerFor, UserEntries.POISONED, Boolean.valueOf(true));
                        doPoisonDamage(playerFor, true);
                        playerFor.sendMessage(LocaleMessage.INFECTED.toString((CommandSender) playerFor));
                    }
                }

            }
        }
    }

    public static void doPoisonDamage(Player playerFor) {
        doPoisonDamage(playerFor, false);
    }

    private static void doPoisonDamage(final Player playerFor, final boolean initial) {
        final double damage = ((Double) ConfigEntries.INFECTION_DAMAGE.getValue()).doubleValue();

        (new BukkitRunnable() {
            public void run() {
                if (playerFor.getHealth() > 0.6D * playerFor.getMaxHealth()) {
                    playerFor.setHealth(playerFor.getMaxHealth() * 0.6D);
                    playerFor.damage(0.0D);
                }

                if (!initial && playerFor.getHealth() > damage) {
                    playerFor.damage(damage);
                }

                playerFor.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 3));
            }
        }).runTaskLater(MyZ.instance, 0L);
    }

    public static void setBleeding(Player playerFor, boolean bleeding, boolean force) {
        if (((Boolean) ConfigEntries.USE_BLEEDING.getValue()).booleanValue()) {
            if (!bleeding) {
                if (((Boolean) DataWrapper.get(playerFor, UserEntries.BLEEDING)).booleanValue()) {
                    DataWrapper.set((OfflinePlayer) playerFor, UserEntries.BLEEDING, Boolean.valueOf(false));
                    playerFor.removePotionEffect(PotionEffectType.BLINDNESS);
                    if (!force) {
                        playerFor.sendMessage(LocaleMessage.BLEEDING_ENDED.toString((CommandSender) playerFor));
                    }
                }

            } else {
                if (!((Boolean) DataWrapper.get(playerFor, UserEntries.BLEEDING)).booleanValue()) {
                    double toughnessLevel = (double) ((Integer) DataWrapper.get(playerFor, UserEntries.SKIN)).intValue();
                    double percentChance = 12.5D - toughnessLevel;

                    if (percentChance < 1.0D) {
                        percentChance = 1.0D;
                    }

                    if (Utilities.random.nextDouble() <= percentChance / 100.0D) {
                        DataWrapper.set((OfflinePlayer) playerFor, UserEntries.BLEEDING, Boolean.valueOf(true));
                        doBleedingDamage(playerFor);
                        playerFor.sendMessage(LocaleMessage.BLEEDING.toString((CommandSender) playerFor));
                    }
                }

            }
        }
    }

    public static void doBleedingDamage(final Player playerFor) {
        final double damage = ((Double) ConfigEntries.BLEED_DAMAGE.getValue()).doubleValue();

        (new BukkitRunnable() {
            public void run() {
                if (playerFor.getHealth() > damage) {
                    playerFor.damage(damage);
                }

                playerFor.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 3));
                (new BukkitRunnable() {
                    public void run() {
                        ParticleEffect.REDSTONE.display(0.25F, 0.25F, 0.25F, 0.0F, 50, playerFor.getEyeLocation().subtract(0.0D, 0.5D, 0.0D), 10.0D);
                    }
                }).runTaskAsynchronously(MyZ.instance);
            }
        }).runTaskLater(MyZ.instance, 0L);
    }

    public static List getPlayersInRange(Location location, int radius) {
        ArrayList players = new ArrayList();
        int d2 = radius * radius;
        Iterator iterator = location.getWorld().getPlayers().iterator();

        while (iterator.hasNext()) {
            Player p = (Player) iterator.next();

            if (p.getWorld() == location.getWorld() && p.getLocation().distanceSquared(location) <= (double) d2) {
                players.add(p);
            }
        }

        return players;
    }

    public static boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
        double epsilon = 9.999999747378752E-5D;
        Vector3D d = p2.subtract(p1).multiply(0.5D);
        Vector3D e = max.subtract(min).multiply(0.5D);
        Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5D));
        Vector3D ad = d.abs();

        return Math.abs(c.x) > e.x + ad.x ? false : (Math.abs(c.y) > e.y + ad.y ? false : (Math.abs(c.z) > e.z + ad.z ? false : (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + 9.999999747378752E-5D ? false : (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + 9.999999747378752E-5D ? false : Math.abs(d.x * c.y - d.y * c.x) <= e.x * ad.y + e.y * ad.x + 9.999999747378752E-5D))));
    }

    public static void addFriendOnSneak(final Player player) {
        MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
            public void run() {
                if (player != null && !player.isDead() && player.isOnline() && player.isSneaking() && player.isBlocking()) {
                    List nearby = Utilities.getPlayersInRange(player.getLocation(), 20);

                    if (nearby.isEmpty()) {
                        return;
                    }

                    Location eyePosition = player.getEyeLocation();
                    Vector3D POV = new Vector3D(eyePosition.getDirection());
                    Vector3D eyeLocation = new Vector3D(eyePosition);
                    Vector3D POV_end = eyeLocation.add(POV.multiply(20));
                    Player hit = null;
                    Iterator iterator = nearby.iterator();

                    while (iterator.hasNext()) {
                        Player target = (Player) iterator.next();
                        Vector3D targetPosition = new Vector3D(target.getLocation());
                        Vector3D minimum = targetPosition.add(-0.5D, 0.0D, -0.5D);
                        Vector3D maximum = targetPosition.add(0.5D, 1.67D, 0.5D);

                        if (target != player && Utilities.hasIntersection(eyeLocation, POV_end, minimum, maximum) && (hit == null || hit.getLocation().distanceSquared(eyePosition) > target.getLocation().distanceSquared(eyePosition))) {
                            hit = target;
                        }
                    }

                    if (hit != null) {
                        ;
                    }
                }

            }
        }, 20L);
    }

    public static List getSpawns() {
        List locations = (List) ConfigEntries.SPAWN_POINTS.getValue();
        ArrayList returned = new ArrayList();
        Iterator iterator = locations.iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            Location toLoc = SerializableLocation.deserialize(s);

            if (toLoc != null) {
                returned.add(toLoc);
            }
        }

        return returned;
    }

    public static void respawn(Player player) {
        respawn(player, false);
    }

    public static void respawn(Player player, boolean onLogout) {
        boolean dedicated = ((Boolean) ConfigEntries.DEDICATED.getValue()).booleanValue();

        if (!dedicated) {
            Location home = null;

            try {
                home = SerializableLocation.deserialize((String) ConfigEntries.HOME_SPAWN.getValue());
            } catch (Exception exception) {
                ;
            }

            if (home != null) {
                player.teleport(home);
            }
        }

        DataWrapper.set((OfflinePlayer) player, UserEntries.PLAYING, Boolean.valueOf(false));
        if (!onLogout) {
            setBleeding(player, false, true);
            setPoisoned(player, false, true);
        }

        Iterator home1 = player.getActivePotionEffects().iterator();

        while (home1.hasNext()) {
            PotionEffect effect = (PotionEffect) home1.next();

            player.removePotionEffect(effect.getType());
        }

        player.getInventory().clear();
        player.setSaturation(1.0F);
        player.setFireTicks(0);
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0.0F);
        if (inWorld(player)) {
            if (dedicated) {
                spawnInWorld(player, dedicated);
            }

        }
    }

    public static boolean canSpawn(Player player) {
        return !((Boolean) DataWrapper.get(player, UserEntries.PLAYING)).booleanValue() && getSpawns().size() != 0;
    }

    public static void spawnInWorld(Player player) {
        spawnInWorld(player, false);
    }

    private static void spawnInWorld(Player player, boolean dedicated) {
        if (((Boolean) DataWrapper.get(player, UserEntries.PLAYING)).booleanValue()) {
            if (!dedicated) {
                player.sendMessage(LocaleMessage.ALREADY_SPAWNED.toString((CommandSender) player));
            }

        } else {
            List spawns = getSpawns();

            if (spawns.size() == 0) {
                player.sendMessage(LocaleMessage.NO_SPAWNS.toString((CommandSender) player));
            } else {
                setBleeding(player, false, true);
                setPoisoned(player, false, true);
                player.setNoDamageTicks(0);
                player.setSaturation(0.3F);
                player.setFoodLevel(20);
                player.setFireTicks(0);
                player.sendMessage(LocaleMessage.SPAWNED.toString((CommandSender) player));
                DataWrapper.set((OfflinePlayer) player, UserEntries.PLAYING, Boolean.valueOf(true));
                ThirstManager.getInstance().replenish(player, false, false);
                player.getInventory().clear();
                player.getEquipment().clear();
                Iterator iterator = player.getActivePotionEffects().iterator();

                while (iterator.hasNext()) {
                    PotionEffect effect = (PotionEffect) iterator.next();

                    player.removePotionEffect(effect.getType());
                }

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1));
                player.getInventory().setContents(StartingKitManager.getInstance().getInventory(player));
                player.getEquipment().setBoots(StartingKitManager.getInstance().getEquipment(player, StartingKitManager.EquipmentPiece.BOOTS));
                player.getEquipment().setLeggings(StartingKitManager.getInstance().getEquipment(player, StartingKitManager.EquipmentPiece.LEGGINGS));
                player.getEquipment().setChestplate(StartingKitManager.getInstance().getEquipment(player, StartingKitManager.EquipmentPiece.CHESTPLATE));
                player.getEquipment().setHelmet(StartingKitManager.getInstance().getEquipment(player, StartingKitManager.EquipmentPiece.HELMET));
                player.teleport((Location) spawns.get(Utilities.random.nextInt(spawns.size())));
            }
        }
    }

    public static MyZRank getRank(Player playerFor) {
        if (playerFor.hasPermission("MyZ.rank.100")) {
            return MyZRank.forInt(100);
        } else {
            for (int i = 1; i <= 101; ++i) {
                if (!playerFor.hasPermission("MyZ.rank." + i)) {
                    return MyZRank.forInt(i - 1);
                }
            }

            return MyZRank.forInt(0);
        }
    }

    public static void faceCompass(final Player playerFor, boolean unspecific, final BlockFace direction) {
        if (unspecific) {
            playerFor.setCompassTarget(playerFor.getWorld().getSpawnLocation());
        } else {
            final Location at = playerFor.getLocation();
            final BlockFace facing = getFacing(at);

            (new BukkitRunnable() {
                public void run() {
                    switch (Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[direction.ordinal()]) {
                    case 1:
                        playerFor.setCompassTarget(at.getBlock().getRelative(facing, 1000).getLocation());
                        break;

                    case 2:
                        playerFor.setCompassTarget(at.getBlock().getRelative(facing.getOppositeFace(), 1000).getLocation());
                        break;

                    case 3:
                        playerFor.setCompassTarget(at.getBlock().getRelative(Utilities.getRightShifts(facing, 2), 1000).getLocation());
                        break;

                    case 4:
                        playerFor.setCompassTarget(at.getBlock().getRelative(Utilities.getRightShifts(facing, 6), 1000).getLocation());
                        break;

                    case 5:
                        playerFor.setCompassTarget(at.getBlock().getRelative(Utilities.getRightShifts(facing, 1), 1000).getLocation());
                        break;

                    case 6:
                        playerFor.setCompassTarget(at.getBlock().getRelative(Utilities.getRightShifts(facing, 3), 1000).getLocation());
                        break;

                    case 7:
                        playerFor.setCompassTarget(at.getBlock().getRelative(Utilities.getRightShifts(facing, 5), 1000).getLocation());
                        break;

                    case 8:
                        playerFor.setCompassTarget(at.getBlock().getRelative(Utilities.getRightShifts(facing, 7), 1000).getLocation());
                    }

                }
            }).runTaskLater(MyZ.instance, 0L);
        }
    }

    private static BlockFace getRightShifts(BlockFace from, int shifts) {
        if (shifts <= 0) {
            return from;
        } else {
            --shifts;
            switch (Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[from.ordinal()]) {
            case 1:
                return getRightShifts(BlockFace.NORTH_EAST, shifts);

            case 2:
                return getRightShifts(BlockFace.SOUTH_WEST, shifts);

            case 3:
                return getRightShifts(BlockFace.SOUTH_EAST, shifts);

            case 4:
                return getRightShifts(BlockFace.NORTH_WEST, shifts);

            case 5:
                return getRightShifts(BlockFace.EAST, shifts);

            case 6:
                return getRightShifts(BlockFace.SOUTH, shifts);

            case 7:
                return getRightShifts(BlockFace.WEST, shifts);

            case 8:
                return getRightShifts(BlockFace.NORTH, shifts);

            default:
                throw new IllegalArgumentException(from + " must be a subcardinal direction!");
            }
        }
    }

    private static BlockFace getFacing(Location inLoc) {
        return Utilities.cardinal[Math.round(inLoc.getYaw() / 45.0F) & 7];
    }

    public static String yesNo(Player playerFor, boolean bool) {
        return bool ? LocaleMessage.YES.toString((CommandSender) playerFor) : LocaleMessage.NO.toString((CommandSender) playerFor);
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$org$bukkit$block$BlockFace = new int[BlockFace.values().length];

        static {
            try {
                Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[BlockFace.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[BlockFace.SOUTH.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[BlockFace.EAST.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[BlockFace.WEST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[BlockFace.NORTH_EAST.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[BlockFace.SOUTH_EAST.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[BlockFace.SOUTH_WEST.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                Utilities.SyntheticClass_1.$SwitchMap$org$bukkit$block$BlockFace[BlockFace.NORTH_WEST.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

        }
    }
}
