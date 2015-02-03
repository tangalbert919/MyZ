/**
 * 
 */
package jordan.sicherman.listeners.player;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;

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
import org.bukkit.util.Vector;

/**
 * @author snowgears
 * 
 */
public class Grappler implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamageEvent(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL && e.getEntity().hasMetadata("MyZ.grapple.in_air")) {
			e.setCancelled(true);
			e.getEntity().removeMetadata("MyZ.grapple.in_air", MyZ.instance);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void fishEvent(PlayerFishEvent e) {
		Player player = e.getPlayer();

		if (!ItemUtilities.getInstance().hasTag(player.getItemInHand(), ItemTag.GRAPPLE)) { return; }

		if (e.getState() == State.IN_GROUND) {
			Location loc = e.getHook().getLocation();
			for (Entity ent : e.getHook().getNearbyEntities(1.5, 1, 1.5)) {
				if (ent instanceof Item) {
					grapple(player, ent, player.getLocation());
					return;
				}
			}

			grapple(player, player, loc);
		} else if (e.getState() == State.CAUGHT_ENTITY) {
			if (e.getCaught() instanceof Player) {
				Player hooked = (Player) e.getCaught();
				grapple(player, hooked, player.getLocation());
			} else {
				grapple(player, e.getCaught(), player.getLocation());
			}
		}
	}

	public void grapple(Player grappler, Entity pulled, Location to) {
		if (grappler.equals(pulled)) {
			if (grappler.getLocation().distance(to) < 3 || EquipmentState.getState(grappler.getItemInHand()) == EquipmentState.GRAPPLE_WEAK) {
				pullEntitySlightly(grappler, to);
			} else {
				pullEntityToLocation(grappler, to, EquipmentState.getState(grappler.getItemInHand()) == EquipmentState.LIGHTWEIGHT);
			}
		} else {
			if (EquipmentState.getState(grappler.getItemInHand()) == EquipmentState.GRAPPLE_WEAK) {
				pullEntitySlightly(pulled, to);
			} else {
				pullEntityToLocation(pulled, to, false);
			}
		}

		to.getWorld().playSound(to, Sound.MAGMACUBE_JUMP, 10f, 1f);
	}

	private void pullEntitySlightly(Entity entityFor, Location to) {
		if (to.getY() > entityFor.getLocation().getY()) {
			entityFor.setVelocity(new Vector(0, 0.25, 0));
			return;
		}

		Location from = entityFor.getLocation();

		Vector vector = to.toVector().subtract(from.toVector());
		entityFor.setVelocity(vector);
	}

	private void pullEntityToLocation(Entity entityFor, Location to, boolean negate) {
		Location from = entityFor.getLocation();

		from.setY(from.getY() + 0.5);
		entityFor.teleport(from);

		double gravity = -0.08;
		double distance = to.distance(from);
		double time = distance;
		double x = (1.0 + 0.07 * time) * (to.getX() - from.getX()) / time;
		double y = (1.0 + 0.03 * time) * (to.getY() - from.getY()) / time - 0.5 * gravity * time;
		double z = (1.0 + 0.07 * time) * (to.getZ() - from.getZ()) / time;

		Vector v = entityFor.getVelocity();
		v.setX(x);
		v.setY(y);
		v.setZ(z);
		entityFor.setVelocity(v);

		if (negate) {
			addNoFall((Player) entityFor, 100);
		}
	}

	public void addNoFall(final Player playerFor, int duration) {
		if (playerFor.hasMetadata("MyZ.grapple.in_air")) {
			Bukkit.getServer().getScheduler().cancelTask(playerFor.getMetadata("MyZ.grapple.in_air").get(0).asInt());
		}

		int id = MyZ.instance.getServer().getScheduler().scheduleSyncDelayedTask(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				playerFor.removeMetadata("MyZ.grapple.in_air", MyZ.instance);
			}
		}, duration);

		playerFor.setMetadata("MyZ.grapple.in_air", new FixedMetadataValue(MyZ.instance, id));
	}
}
