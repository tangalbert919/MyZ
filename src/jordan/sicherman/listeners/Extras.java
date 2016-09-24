package jordan.sicherman.listeners;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.nms.utilities.CompatibilityManager;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.ThirstManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Extras implements Listener {

    private static final Random random = new Random();

    @EventHandler(
        priority = EventPriority.MONITOR
    )
    private void onAirDrop(EntityChangeBlockEvent e) {
        if (Utilities.inWorld(e.getBlock().getLocation())) {
            if (e.getEntity() instanceof FallingBlock) {
                FallingBlock fall = (FallingBlock) e.getEntity();

                if (fall.hasMetadata("MyZ.airdrop")) {
                    List possible_chests = (List) ConfigEntries.AIRDROP_CHESTS.getValue();

                    if (Extras.random.nextDouble() <= 0.3D) {
                        fall.getWorld().createExplosion(fall.getLocation().getX(), fall.getLocation().getY(), fall.getLocation().getZ(), ((Double) ConfigEntries.AIRDROP_EXPLOSION_MAGNITUDE.getValue()).floatValue(), ((Boolean) ConfigEntries.AIRDROP_FIRE.getValue()).booleanValue(), ((Boolean) ConfigEntries.AIRDROP_BREAK.getValue()).booleanValue());
                    }

                    if (fall.getMaterial() == Material.CHEST) {
                        final ChestType type = ChestType.fromString((String) possible_chests.get(Extras.random.nextInt(possible_chests.size())));

                        if (type != null) {
                            final Block chest = e.getBlock();

                            MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
                                public void run() {
                                    chest.setMetadata("MyZ.airdrop", new FixedMetadataValue(MyZ.instance, Boolean.valueOf(true)));
                                    chest.setTypeIdAndData(Material.CHEST.getId(), (byte) Extras.random.nextInt(5), true);
                                    ((Chest) chest.getState()).getBlockInventory().setContents(type.generate());
                                    CompatibilityManager.renameChest((Chest) chest.getState(), type.getName());
                                }
                            }, 1L);
                        }
                    }

                    if (!((Boolean) ConfigEntries.AIRDROP_WRECKAGE.getValue()).booleanValue()) {
                        fall.setDropItem(false);
                        fall.remove();
                        e.setCancelled(true);
                    }
                }
            }

        }
    }

    @EventHandler(
        priority = EventPriority.LOW,
        ignoreCancelled = true
    )
    private void onNaturalRegen(EntityRegainHealthEvent e) {
        if (Utilities.inWorld(e.getEntity())) {
            if (!((Boolean) ConfigEntries.NATURAL_REGEN.getValue()).booleanValue() && e.getEntityType() == EntityType.PLAYER && e.getRegainReason() == RegainReason.SATIATED) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    private void onZombieCombust(EntityCombustEvent e) {
        if (Utilities.inWorld(e.getEntity())) {
            if (e.getEntityType() == EntityType.SKELETON) {
                e.setCancelled(true);
            } else if (!((Boolean) ConfigEntries.ZOMBIES_BURN.getValue()).booleanValue()) {
                if (e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.PIG_ZOMBIE || e.getEntityType() == EntityType.GIANT) {
                    e.setCancelled(true);
                }

            }
        }
    }

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    private void onZombieTakeFallDamage(EntityDamageEvent e) {
        if (Utilities.inWorld(e.getEntity())) {
            if (((Boolean) ConfigEntries.NO_ZOMBIE_FALL.getValue()).booleanValue() && e.getCause() == DamageCause.FALL && (e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.GIANT)) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler(
        priority = EventPriority.LOW,
        ignoreCancelled = true
    )
    private void onWitherAway(EntityDamageEvent e) {
        if (Utilities.inWorld(e.getEntity())) {
            if (((Boolean) ConfigEntries.PREVENT_WITHERING.getValue()).booleanValue() && e.getCause() == DamageCause.WITHER && e.getEntityType() == EntityType.PLAYER) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler(
        priority = EventPriority.MONITOR
    )
    private void onMobDeath(EntityDeathEvent e) {
        if (Utilities.inWorld((Entity) e.getEntity())) {
            e.setDroppedExp(0);
        }
    }

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    private void onMobSpawn(CreatureSpawnEvent e) {
        if (Utilities.inWorld((Entity) e.getEntity())) {
            if (e.getSpawnReason() == SpawnReason.SPAWNER_EGG && e.getEntityType() == EntityType.MUSHROOM_COW) {
                e.setCancelled(true);
                CompatibilityManager.spawnEntity(e.getLocation(), EntityType.GIANT);
            }

            int maximum_z;
            int minimum_z;

            switch (Extras.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[e.getEntityType().ordinal()]) {
            case 1:
                if (!((Boolean) ConfigEntries.ZOMBIE_LIMITER.getValue()).booleanValue()) {
                    return;
                }

                maximum_z = ((Integer) ConfigEntries.ZOMBIE_MAX_Z.getValue()).intValue();
                minimum_z = ((Integer) ConfigEntries.ZOMBIE_MIN_Z.getValue()).intValue();
                break;

            case 2:
                if (!((Boolean) ConfigEntries.PIGMAN_LIMITER.getValue()).booleanValue()) {
                    return;
                }

                maximum_z = ((Integer) ConfigEntries.PIGMAN_MAX_Z.getValue()).intValue();
                minimum_z = ((Integer) ConfigEntries.PIGMAN_MIN_Z.getValue()).intValue();
                break;

            case 3:
                if (!((Boolean) ConfigEntries.GIANT_LIMITER.getValue()).booleanValue()) {
                    return;
                }

                maximum_z = ((Integer) ConfigEntries.GIANT_MAX_Z.getValue()).intValue();
                minimum_z = ((Integer) ConfigEntries.GIANT_MIN_Z.getValue()).intValue();
                break;

            case 4:
                if (!((Boolean) ConfigEntries.GUARD_LIMITER.getValue()).booleanValue()) {
                    return;
                }

                maximum_z = ((Integer) ConfigEntries.GUARD_MAX_Z.getValue()).intValue();
                minimum_z = ((Integer) ConfigEntries.GUARD_MIN_Z.getValue()).intValue();
                break;

            default:
                return;
            }

            int z = e.getLocation().getBlockZ();

            if (z < minimum_z || z > maximum_z) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler(
        priority = EventPriority.MONITOR,
        ignoreCancelled = true
    )
    private void onGiantDamage(EntityDamageByEntityEvent e) {
        if (Utilities.inWorld(e.getEntity())) {
            if (e.getEntityType() == EntityType.GIANT) {
                if (e.getDamager().getType() != EntityType.ARROW && e.getCause() != DamageCause.ENTITY_EXPLOSION && e.getCause() != DamageCause.BLOCK_EXPLOSION) {
                    if (e.getCause() == DamageCause.FALL && ((Double) ConfigEntries.GIANT_EXPLODE_MAGNITUDE.getValue()).doubleValue() > 0.0D) {
                        e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), ((Double) ConfigEntries.GIANT_EXPLODE_MAGNITUDE.getValue()).floatValue());
                        if (((Integer) ConfigEntries.GIANT_SLOWNESS_RADIUS.getValue()).intValue() > 0) {
                            Iterator iterator = Utilities.getPlayersInRange(e.getEntity().getLocation(), ((Integer) ConfigEntries.GIANT_SLOWNESS_RADIUS.getValue()).intValue()).iterator();

                            while (iterator.hasNext()) {
                                Player player = (Player) iterator.next();

                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
                            }
                        }
                    } else if (e.getDamager() instanceof Player && ((Integer) ConfigEntries.GIANT_REINFORCEMENT_CHANCE.getValue()).intValue() > 0 && Extras.random.nextInt(100) + 1 <= ((Integer) ConfigEntries.GIANT_REINFORCEMENT_CHANCE.getValue()).intValue()) {
                        int max = ((Integer) ConfigEntries.GIANT_REINFORCEMENT_MAX.getValue()).intValue();
                        int min = ((Integer) ConfigEntries.GIANT_REINFORCEMENT_MIN.getValue()).intValue();

                        for (int amount = Extras.random.nextInt(max - min + 1) + min; amount > 0; --amount) {
                            double xoff = Extras.random.nextDouble() * (double) Extras.random.nextInt(2) == 0.0D ? -(3.0D + Extras.random.nextDouble()) : 3.0D + Extras.random.nextDouble();
                            double yoff = Extras.random.nextDouble() * (double) Extras.random.nextInt(2) == 0.0D ? -(3.0D + Extras.random.nextDouble()) : 3.0D + Extras.random.nextDouble();
                            double zoff = Extras.random.nextDouble() * (double) Extras.random.nextInt(2) == 0.0D ? -(3.0D + Extras.random.nextDouble()) : 3.0D + Extras.random.nextDouble();

                            CompatibilityManager.spawnEntity(e.getEntity().getLocation().clone().add(xoff, yoff, zoff), EntityType.ZOMBIE);
                        }
                    }
                } else {
                    e.setCancelled(true);
                }
            }

        }
    }

    @EventHandler(
        priority = EventPriority.MONITOR
    )
    private void onBlock(PlayerInteractEvent e) {
        if (Utilities.inWorld(e.getPlayer())) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getItemInHand() != null) {
                    switch (Extras.SyntheticClass_1.$SwitchMap$org$bukkit$Material[e.getPlayer().getItemInHand().getType().ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        if (e.getPlayer().isSneaking()) {
                            Utilities.addFriendOnSneak(e.getPlayer());
                        }
                        break;

                    default:
                        return;
                    }
                } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getItemInHand() != null) {
                    Material type = e.getClickedBlock().getType();

                    if ((type == Material.WATER || type == Material.CAULDRON || type == Material.CAULDRON_ITEM || type == Material.STATIONARY_WATER) && e.getPlayer().getItemInHand().getType() == Material.GLASS_BOTTLE) {
                        e.setCancelled(true);
                        switch (Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[e.getClickedBlock().getBiome().ordinal()]) {
                        case 1:
                        case 2:
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
                            e.getPlayer().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.COLD_WATER, 1));
                            break;

                        case 18:
                        case 19:
                        case 20:
                        case 21:
                            e.getPlayer().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.SALTY_WATER, 1));
                            break;

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
                            if (e.getPlayer().getWorld().getTime() < 13000L) {
                                e.getPlayer().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.WARM_WATER, 1));
                            } else {
                                e.getPlayer().setItemInHand(new ItemStack(Material.POTION, 1));
                            }
                            break;

                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                        case 42:
                            e.getPlayer().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.MURKY_WATER, 1));
                            break;

                        default:
                            e.getPlayer().setItemInHand(new ItemStack(Material.POTION, 1));
                        }
                    }
                }

            }
        }
    }

    @EventHandler(
        priority = EventPriority.MONITOR,
        ignoreCancelled = true
    )
    private void onFoodConsume(PlayerItemConsumeEvent e) {
        if (Utilities.inWorld(e.getPlayer())) {
            final Player playerFor = e.getPlayer();
            ItemStack item = e.getItem();

            if (((Boolean) ConfigEntries.ENHANCE_FOOD.getValue()).booleanValue() && ItemUtilities.getInstance().isFood(item)) {
                if (playerFor.getHealth() < playerFor.getMaxHealth()) {
                    playerFor.setHealth(playerFor.getHealth() + 1.0D > playerFor.getMaxHealth() ? playerFor.getMaxHealth() : playerFor.getHealth() + 1.0D);
                }
            } else if (item.getType() == Material.POTION && item.getDurability() == 0) {
                if (playerFor.getLevel() < ((Integer) ConfigEntries.THIRST_MAX_DEFAULT.getValue()).intValue()) {
                    ThirstManager.getInstance().replenish(playerFor, ItemUtilities.getInstance().hasTag(item, ItemTag.SALTY_WATER));
                }

                if (ItemUtilities.getInstance().hasTag(item, ItemTag.MURKY_WATER)) {
                    Utilities.setPoisoned(playerFor, true, false);
                }
            } else if ((item.getType() != Material.POTION || item.getDurability() == 0 || ItemUtilities.getInstance().hasTag(item, ItemTag.MURKY_WATER)) && item.getType() != Material.MILK_BUCKET) {
                if (ItemUtilities.getInstance().isPoison(item)) {
                    Utilities.setPoisoned(playerFor, true, false);
                }
            } else {
                if (item.getType() == Material.MILK_BUCKET) {
                    Utilities.setPoisoned(playerFor, false, false);
                }

                if (((Boolean) ConfigEntries.USE_THIRST.getValue()).booleanValue()) {
                    MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
                        public void run() {
                            ItemStack i = playerFor.getItemInHand();

                            if (i != null) {
                                if (i.getAmount() > 1) {
                                    i.setAmount(i.getAmount() - 1);
                                } else {
                                    playerFor.setItemInHand((ItemStack) null);
                                }
                            }

                        }
                    }, 0L);
                }
            }

        }
    }

    private boolean isHumanoid(EntityType type) {
        return type == EntityType.PLAYER || type == EntityType.ZOMBIE || type == EntityType.PIG_ZOMBIE || type == EntityType.SKELETON || type == EntityType.CREEPER;
    }

    @EventHandler(
        priority = EventPriority.MONITOR,
        ignoreCancelled = true
    )
    private void onHeadshot(EntityDamageByEntityEvent e) {
        if (Utilities.inWorld(e.getDamager())) {
            if (e.getCause() == DamageCause.PROJECTILE && ((Integer) ConfigEntries.BOW_PRECISE_HEADSHOT_MOD.getValue()).intValue() > 0) {
                Projectile projectile = (Projectile) e.getDamager();

                if (this.wasHeadshot(e.getEntity(), projectile)) {
                    e.setDamage(e.getDamage() + (double) ((Integer) ConfigEntries.BOW_PRECISE_HEADSHOT_MOD.getValue()).intValue());
                    ((Player) projectile.getShooter()).sendMessage(LocaleMessage.HEADSHOT.filter(new Object[] { ConfigEntries.BOW_PRECISE_HEADSHOT_MOD.getValue()}).toString((CommandSender) ((Player) projectile.getShooter())));
                }
            }

        }
    }

    private boolean wasHeadshot(Entity entity, Projectile arrow) {
        if (arrow instanceof Arrow && arrow.getShooter() instanceof Player) {
            Player player = (Player) arrow.getShooter();
            ItemStack bow = player.getItemInHand();

            if (bow != null && EquipmentState.getState(bow).getPosition(bow) >= 5) {
                if (!this.isHumanoid(entity.getType())) {
                    return false;
                } else {
                    double projectileY = arrow.getLocation().getY();
                    double entityY = entity.getLocation().getY();
                    boolean headshot = projectileY - entityY > 1.75D;

                    return headshot;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$org$bukkit$entity$EntityType;
        static final int[] $SwitchMap$org$bukkit$Material;
        static final int[] $SwitchMap$org$bukkit$block$Biome = new int[Biome.values().length];

        static {
            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.FROZEN_OCEAN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.COLD_BEACH.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.FROZEN_RIVER.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA_HILLS.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.ICE_PLAINS_SPIKES.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.ICE_MOUNTAINS.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SUNFLOWER_PLAINS.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.ICE_PLAINS.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SWAMPLAND_MOUNTAINS.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA_HILLS.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MEGA_TAIGA_HILLS.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MEGA_SPRUCE_TAIGA_HILLS.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.TAIGA_HILLS.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MEGA_SPRUCE_TAIGA_HILLS.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.EXTREME_HILLS.ordinal()] = 17;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.DEEP_OCEAN.ordinal()] = 18;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.OCEAN.ordinal()] = 19;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.COLD_BEACH.ordinal()] = 20;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.STONE_BEACH.ordinal()] = 21;
            } catch (NoSuchFieldError nosuchfielderror20) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.DESERT.ordinal()] = 22;
            } catch (NoSuchFieldError nosuchfielderror21) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.DESERT_HILLS.ordinal()] = 23;
            } catch (NoSuchFieldError nosuchfielderror22) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.DESERT_HILLS.ordinal()] = 24;
            } catch (NoSuchFieldError nosuchfielderror23) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MESA.ordinal()] = 25;
            } catch (NoSuchFieldError nosuchfielderror24) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MESA.ordinal()] = 26;
            } catch (NoSuchFieldError nosuchfielderror25) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.COLD_BEACH.ordinal()] = 27;
            } catch (NoSuchFieldError nosuchfielderror26) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MESA_PLATEAU_FOREST.ordinal()] = 28;
            } catch (NoSuchFieldError nosuchfielderror27) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MESA_BRYCE.ordinal()] = 29;
            } catch (NoSuchFieldError nosuchfielderror28) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.MESA.ordinal()] = 30;
            } catch (NoSuchFieldError nosuchfielderror29) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SAVANNA.ordinal()] = 31;
            } catch (NoSuchFieldError nosuchfielderror30) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.PLAINS.ordinal()] = 32;
            } catch (NoSuchFieldError nosuchfielderror31) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SAVANNA_MOUNTAINS.ordinal()] = 33;
            } catch (NoSuchFieldError nosuchfielderror32) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SAVANNA.ordinal()] = 34;
            } catch (NoSuchFieldError nosuchfielderror33) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.JUNGLE.ordinal()] = 35;
            } catch (NoSuchFieldError nosuchfielderror34) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.JUNGLE_EDGE.ordinal()] = 36;
            } catch (NoSuchFieldError nosuchfielderror35) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.JUNGLE_EDGE.ordinal()] = 37;
            } catch (NoSuchFieldError nosuchfielderror36) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.JUNGLE_HILLS.ordinal()] = 38;
            } catch (NoSuchFieldError nosuchfielderror37) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.JUNGLE_EDGE.ordinal()] = 39;
            } catch (NoSuchFieldError nosuchfielderror38) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.RIVER.ordinal()] = 40;
            } catch (NoSuchFieldError nosuchfielderror39) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SWAMPLAND.ordinal()] = 41;
            } catch (NoSuchFieldError nosuchfielderror40) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$block$Biome[Biome.SWAMPLAND.ordinal()] = 42;
            } catch (NoSuchFieldError nosuchfielderror41) {
                ;
            }

            $SwitchMap$org$bukkit$Material = new int[Material.values().length];

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.WOOD_SWORD.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror42) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.STONE_SWORD.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror43) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.GOLD_SWORD.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror44) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.IRON_SWORD.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror45) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$Material[Material.DIAMOND_SWORD.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror46) {
                ;
            }

            $SwitchMap$org$bukkit$entity$EntityType = new int[EntityType.values().length];

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.ZOMBIE.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror47) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.PIG_ZOMBIE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror48) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.GIANT.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror49) {
                ;
            }

            try {
                Extras.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.SKELETON.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror50) {
                ;
            }

        }
    }
}
