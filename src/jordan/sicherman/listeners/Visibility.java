/**
 * 
 */
package jordan.sicherman.listeners;

import jordan.sicherman.utilities.ReviveManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.VisibilityManager;
import jordan.sicherman.utilities.VisibilityManager.VisibilityCause;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Jordan
 * 
 */
public class Visibility implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onMove(PlayerMoveEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (e.getTo().distanceSquared(e.getFrom()) >= 0.02) {
			ReviveManager.getInstance().reportException(e.getPlayer());
			VisibilityManager.getInstance().reportMovement(e.getPlayer(), e.getFrom());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onShootArrow(ProjectileLaunchEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player) {
			VisibilityManager.getInstance().overloadXPBarVisibility((Player) e.getEntity().getShooter(), VisibilityCause.SHOOT_ARROW);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onActivateRedstone(PlayerInteractEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAP_DOOR
						|| e.getClickedBlock().getType() == Material.TRAPPED_CHEST || e.getClickedBlock().getType() == Material.WOOD_BUTTON
						|| e.getClickedBlock().getType() == Material.WOOD_DOOR || e.getClickedBlock().getType() == Material.IRON_DOOR
						|| e.getClickedBlock().getType() == Material.NOTE_BLOCK || e.getClickedBlock().getType() == Material.STONE_BUTTON)
				|| e.getAction() == Action.PHYSICAL) {
			VisibilityManager.getInstance().overloadXPBarVisibility(e.getPlayer(), VisibilityCause.ACTIVATE_REDSTONE);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onTakeDamage(EntityDamageEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getEntityType() == EntityType.PLAYER) {
			ReviveManager.getInstance().reportException((Player) e.getEntity());
			VisibilityManager.getInstance().overloadXPBarVisibility((Player) e.getEntity(), VisibilityCause.TAKE_DAMAGE);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onProjectileLand(ProjectileHitEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		if (e.getEntity().getShooter() instanceof Player) {
			switch (e.getEntityType()) {
			case ARROW:
				VisibilityManager.getInstance().overloadXPBarVisibility(e.getEntity().getLocation(), VisibilityCause.ARROW_LANDED);
				break;
			case SNOWBALL:
				VisibilityManager.getInstance().overloadXPBarVisibility(e.getEntity().getLocation(), VisibilityCause.SNOWBALL_LANDED);
				break;
			default:
				break;
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onExplosion(EntityExplodeEvent e) {
		if (!Utilities.inWorld(e.getLocation())) { return; }

		VisibilityManager.getInstance().overloadXPBarVisibility(e.getLocation(), VisibilityCause.EXPLOSION);
	}
}
