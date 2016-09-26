package jordan.sicherman.listeners.player;

import java.util.Iterator;
import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.nms.utilities.NMS;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

public class Grappler implements Listener {

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (e.getCause() == DamageCause.FALL && e.getEntity().hasMetadata("MyZ.grapple.in_air")) {
            e.setCancelled(true);
            e.getEntity().removeMetadata("MyZ.grapple.in_air", MyZ.instance);
        }

    }

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    public void fishEvent(PlayerFishEvent e) {
        Player player = e.getPlayer();

        if (ItemUtilities.getInstance().hasTag(player.getItemInHand(), ItemTag.GRAPPLE)) {
            if (e.getState() == State.IN_GROUND) {
                Location hooked = e.getHook().getLocation();
                Iterator iterator = e.getHook().getNearbyEntities(1.5D, 1.0D, 1.5D).iterator();

                while (iterator.hasNext()) {
                    Entity ent = (Entity) iterator.next();

                    if (ent instanceof Item) {
                        this.grapple(player, ent, player.getLocation());
                        return;
                    }
                }

                this.grapple(player, player, hooked);
            } else if (e.getState() == State.CAUGHT_ENTITY) {
                if (e.getCaught() instanceof Player) {
                    Player hooked1 = (Player) e.getCaught();

                    this.grapple(player, hooked1, player.getLocation());
                } else {
                    this.grapple(player, e.getCaught(), player.getLocation());
                }
            }

        }
    }

    public void grapple(Player grappler, Entity pulled, Location to) {
        if (grappler.equals(pulled)) {
            if (grappler.getLocation().distance(to) >= 3.0D && EquipmentState.getState(grappler.getItemInHand()) != EquipmentState.GRAPPLE_WEAK) {
                this.pullEntityToLocation(grappler, to, EquipmentState.getState(grappler.getItemInHand()) == EquipmentState.LIGHTWEIGHT);
            } else {
                this.pullEntitySlightly(grappler, to);
            }
        } else if (EquipmentState.getState(grappler.getItemInHand()) == EquipmentState.GRAPPLE_WEAK) {
            this.pullEntitySlightly(pulled, to);
        } else {
            this.pullEntityToLocation(pulled, to, false);
        }
        if (NMS.Version.v1_7_R4 != null)
            to.getWorld().playSound(to, Sound.MAGMACUBE_JUMP, 10.0F, 1.0F);
        else if (NMS.Version.v1_8_R1 != null)
        	to.getWorld().playSound(to, Sound.MAGMACUBE_JUMP, 10.0F, 1.0F);
        else if (NMS.Version.v1_8_R2 != null)
        	to.getWorld().playSound(to, Sound.MAGMACUBE_JUMP, 10.0F, 1.0F);
        else if (NMS.Version.v1_8_R3 != null)
        	to.getWorld().playSound(to, Sound.MAGMACUBE_JUMP, 10.0F, 1.0F);
        else if (NMS.Version.v1_9_R1 != null)
        	to.getWorld().playSound(to, Sound.ENTITY_MAGMACUBE_JUMP, 10.0F, 1.0F);
        else if (NMS.Version.v1_9_R2 != null)
        	to.getWorld().playSound(to, Sound.ENTITY_MAGMACUBE_JUMP, 10.0F, 1.0F);
        else if (NMS.Version.v1_10_R1 != null)
        	to.getWorld().playSound(to, Sound.ENTITY_MAGMACUBE_JUMP, 10.0F, 1.0F);
    }

    private void pullEntitySlightly(Entity entityFor, Location to) {
        if (to.getY() > entityFor.getLocation().getY()) {
            entityFor.setVelocity(new Vector(0.0D, 0.25D, 0.0D));
        } else {
            Location from = entityFor.getLocation();
            Vector vector = to.toVector().subtract(from.toVector());

            entityFor.setVelocity(vector);
        }
    }

    private void pullEntityToLocation(Entity entityFor, Location to, boolean negate) {
        Location from = entityFor.getLocation();

        from.setY(from.getY() + 0.5D);
        entityFor.teleport(from);
        double gravity = -0.08D;
        double distance = to.distance(from);
        double x = (1.0D + 0.07D * distance) * (to.getX() - from.getX()) / distance;
        double y = (1.0D + 0.03D * distance) * (to.getY() - from.getY()) / distance - 0.5D * gravity * distance;
        double z = (1.0D + 0.07D * distance) * (to.getZ() - from.getZ()) / distance;
        Vector v = entityFor.getVelocity();

        v.setX(x);
        v.setY(y);
        v.setZ(z);
        entityFor.setVelocity(v);
        if (negate) {
            this.addNoFall((Player) entityFor, 100);
        }

    }

    public void addNoFall(final Player playerFor, int duration) {
        if (playerFor.hasMetadata("MyZ.grapple.in_air")) {
            Bukkit.getServer().getScheduler().cancelTask(((MetadataValue) playerFor.getMetadata("MyZ.grapple.in_air").get(0)).asInt());
        }

        int id = MyZ.instance.getServer().getScheduler().scheduleSyncDelayedTask(MyZ.instance, new Runnable() {
            public void run() {
                playerFor.removeMetadata("MyZ.grapple.in_air", MyZ.instance);
            }
        }, (long) duration);

        playerFor.setMetadata("MyZ.grapple.in_air", new FixedMetadataValue(MyZ.instance, Integer.valueOf(id)));
    }
}
