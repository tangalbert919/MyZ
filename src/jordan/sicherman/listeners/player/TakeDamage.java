package jordan.sicherman.listeners.player;

import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class TakeDamage implements Listener {

    @EventHandler(
        priority = EventPriority.MONITOR,
        ignoreCancelled = true
    )
    private void onEnvironmental(EntityDamageEvent e) {
        if (Utilities.inWorld(e.getEntity())) {
            if (e.getEntity() instanceof Player) {
                Utilities.setBleeding((Player) e.getEntity(), true, false);
            }

        }
    }

    @EventHandler(
        priority = EventPriority.MONITOR,
        ignoreCancelled = true
    )
    private void onMob(EntityDamageByEntityEvent e) {
        if (Utilities.inWorld(e.getEntity()) && e.getDamage() != 0.0D) {
            if (e.getEntity() instanceof Player) {
                Utilities.setBleeding((Player) e.getEntity(), true, false);
                if (e.getDamager().getType() == EntityType.ZOMBIE || e.getDamager().getType() == EntityType.PIG_ZOMBIE || e.getDamager().getType() == EntityType.GIANT) {
                    Utilities.setPoisoned((Player) e.getEntity(), true, false);
                }
            }

            if (e.getDamager() instanceof Player) {
                ItemStack hand;

                if (e.getCause() == DamageCause.ENTITY_ATTACK && e.getDamager() instanceof Player && ((Player) e.getDamager()).getItemOnCursor() != null) {
                    hand = ((Player) e.getDamager()).getItemOnCursor();
                    if (EquipmentState.getState(hand) == EquipmentState.BOW_SHARPENED) {
                        e.setDamage(e.getDamage() + (double) ((Integer) ConfigEntries.BOW_SHARPENED_COMBAT_MOD.getValue()).intValue());
                    }
                }

                hand = ((Player) e.getDamager()).getItemOnCursor();
                double effectiveness = EquipmentState.getEffectiveness(hand);

                e.setDamage(e.getDamage() * effectiveness);
            }

        }
    }
}
