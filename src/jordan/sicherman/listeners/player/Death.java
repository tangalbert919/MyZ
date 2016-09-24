package jordan.sicherman.listeners.player;

import java.util.List;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.DeathCause;
import jordan.sicherman.utilities.configuration.UserEntries;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Death implements Listener {

    @EventHandler(
        priority = EventPriority.HIGHEST
    )
    private void onDeath(PlayerDeathEvent e) {
        if (Utilities.inWorld(e.getEntity())) {
            Player player = e.getEntity();

            e.setDroppedExp(0);

            try {
                e.setKeepInventory(false);
            } catch (Exception exception) {
                ;
            }

            e.setDeathMessage((String) null);
            player.setFireTicks(0);
            treatDeathMessage(player);
            realDeath(player, false);
        }
    }

    public static void realDeath(Player playerFor, boolean onLogout) {
        DataWrapper.set((OfflinePlayer) playerFor, UserEntries.DEATHS, Integer.valueOf(((Integer) DataWrapper.get(playerFor, UserEntries.DEATHS)).intValue() + 1));
        Utilities.setBleeding(playerFor, false, true);
        Utilities.setPoisoned(playerFor, false, true);
        if (onLogout) {
            Utilities.respawn(playerFor, true);
        }

    }

    private static void treatDeathMessage(Player player) {
        if (((Boolean) ConfigEntries.DEATH_MESSAGES.getValue()).booleanValue()) {
            List to = player.getWorld().getPlayers();

            to.remove(player);
            switch (Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[player.getLastDamageCause().getCause().ordinal()]) {
            case 1:
            case 2:
                DeathCause.EXPLOSION.compileAndSendAsJSON(player, to);
                break;

            case 3:
                DeathCause.CACTUS.compileAndSendAsJSON(player, to);
                break;

            case 4:
                DeathCause.DROWNING.compileAndSendAsJSON(player, to);
                break;

            case 5:
                Entity ent = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();

                switch (Death.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[ent.getType().ordinal()]) {
                case 1:
                    DeathCause.GIANT.compileAndSendAsJSON(player, to);
                    return;

                case 2:
                    DeathCause.PIGMAN.compileAndSendAsJSON(player, to);
                    return;

                case 3:
                    DeathCause.PLAYER.compileAndSendAsJSON(player, (Player) ent, to);
                    return;

                case 4:
                    DeathCause.ZOMBIE.compileAndSendAsJSON(player, to);
                    return;

                default:
                    DeathCause.OTHER.compileAndSendAsJSON(player, to);
                    return;
                }

            case 6:
                DeathCause.FALL.compileAndSendAsJSON(player, to);
                break;

            case 7:
            case 8:
                DeathCause.SUFFOCATION.compileAndSendAsJSON(player, to);
                break;

            case 9:
            case 10:
                DeathCause.FIRE.compileAndSendAsJSON(player, to);
                break;

            case 11:
                DeathCause.LAVA.compileAndSendAsJSON(player, to);
                break;

            case 12:
                DeathCause.MAGIC.compileAndSendAsJSON(player, to);
                break;

            case 13:
                DeathCause.POISON.compileAndSendAsJSON(player, to);
                break;

            case 14:
                DeathCause.ARROW.compileAndSendAsJSON(player, to);
                break;

            case 15:
                DeathCause.STARVE.compileAndSendAsJSON(player, to);
                break;

            case 16:
                DeathCause.VOID.compileAndSendAsJSON(player, to);
                break;

            default:
                DeathCause.OTHER.compileAndSendAsJSON(player, to);
            }

        }
    }

    static class SyntheticClass_1 {

        static final int[] $SwitchMap$org$bukkit$entity$EntityType;
        static final int[] $SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause = new int[DamageCause.values().length];

        static {
            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.BLOCK_EXPLOSION.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.ENTITY_EXPLOSION.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.CONTACT.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.DROWNING.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.ENTITY_ATTACK.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.FALL.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.FALLING_BLOCK.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.SUFFOCATION.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.FIRE.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.FIRE_TICK.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.LAVA.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.MAGIC.ordinal()] = 12;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.POISON.ordinal()] = 13;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.PROJECTILE.ordinal()] = 14;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.STARVATION.ordinal()] = 15;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$event$entity$EntityDamageEvent$DamageCause[DamageCause.VOID.ordinal()] = 16;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

            $SwitchMap$org$bukkit$entity$EntityType = new int[EntityType.values().length];

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.GIANT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror16) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.PIG_ZOMBIE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror17) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.PLAYER.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror18) {
                ;
            }

            try {
                Death.SyntheticClass_1.$SwitchMap$org$bukkit$entity$EntityType[EntityType.ZOMBIE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror19) {
                ;
            }

        }
    }
}
