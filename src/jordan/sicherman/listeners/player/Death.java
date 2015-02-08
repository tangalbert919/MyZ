/**
 * 
 */
package jordan.sicherman.listeners.player;

import java.util.ArrayList;
import java.util.List;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.sql.SQLManager;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.ReviveManager;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.DeathCause;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Jordan
 * 
 */
public class Death implements Listener {

	private static final List<String> overriding = new ArrayList<String>();

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onDeath(PlayerDeathEvent e) {
		if (!Utilities.inWorld(e.getEntity())) { return; }

		final Player player = e.getEntity();

		e.setDroppedExp(0);
		e.setKeepInventory(false);
		e.setDeathMessage(null);
		player.setFireTicks(0);

		treatDeathMessage(player);

		if (overriding.contains(SQLManager.primaryKeyFor(player))) {
			overriding.remove(SQLManager.primaryKeyFor(player));
			return;
		}

		if (MyZ.zombieFactory.isZombie(player) || player.getLastDamageCause().getCause() == DamageCause.VOID) {
			realDeath(player);
			return;
		}

		if ((!ConfigEntries.BECOME_ZOMBIE.<Boolean> getValue() && !ConfigEntries.BECOME_GHOST.<Boolean> getValue()) || !MyZ.isPremium()) {
			realDeath(player);
			return;
		}

		final long ghostTime = ConfigEntries.GHOST_TIME.<Integer> getValue() * 20L;
		long actualTime = ghostTime;

		player.setHealth(player.getMaxHealth());
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}

		if (MyZ.isPremium() && (ConfigEntries.BECOME_ZOMBIE.<Boolean> getValue() || ConfigEntries.BECOME_GHOST.<Boolean> getValue())) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) ghostTime, 2));
		}

		if (MyZ.isPremium() && ConfigEntries.BECOME_ZOMBIE.<Boolean> getValue() && DataWrapper.<Boolean> get(player, UserEntries.POISONED)) {
			actualTime /= 2L;
			MyZ.zombieFactory.setZombie(player, true);
			player.sendMessage(LocaleMessage.BECAME_ZOMBIE.filter(actualTime / 20L).toString(player));
			player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (int) actualTime, 2));
			DataWrapper.set(player, UserEntries.ZOMBIE_TIMES, DataWrapper.<Integer> get(player, UserEntries.ZOMBIE_TIMES) + 1);
		}

		final long ghostFor = actualTime;

		if (MyZ.isPremium() && ConfigEntries.BECOME_GHOST.<Boolean> getValue()) {
			new BukkitRunnable() {
				@Override
				public void run() {
					if (MyZ.zombieFactory.isZombie(player)) {
						MyZ.zombieFactory.setZombie(player, false);
					}
					if (ReviveManager.getInstance().isVulnerable(player)) {
						realDeath(player);
						return;
					}

					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int) ghostFor, 15));
					MyZ.ghostFactory.setGhost(player, true);
					player.sendMessage(LocaleMessage.BECAME_GHOST.filter(ghostFor / 20L).toString(player));
					DataWrapper.set(player, UserEntries.GHOST_TIMES, DataWrapper.<Integer> get(player, UserEntries.GHOST_TIMES) + 1);

					new BukkitRunnable() {
						@Override
						public void run() {
							if (MyZ.ghostFactory.isGhost(player)) {
								realDeath(player);
							}
						}

						@Override
						public void cancel() {
							player.removePotionEffect(PotionEffectType.WITHER);
							if (MyZ.ghostFactory.isGhost(player)) {
								ReviveManager.getInstance().reportException(player);
								realDeath(player);
							}
						}
					}.runTaskLater(MyZ.instance, ghostFor);
				}

				@Override
				public void cancel() {
					MyZ.zombieFactory.setZombie(player, false);
					MyZ.ghostFactory.setGhost(player, false);
					player.removePotionEffect(PotionEffectType.HUNGER);
					player.removePotionEffect(PotionEffectType.WITHER);
				}
			}.runTaskLater(MyZ.instance, actualTime == ghostTime ? 0L : actualTime);
		}
	}

	public static void realDeath(Player playerFor) {
		MyZ.zombieFactory.setZombie(playerFor, false);
		MyZ.ghostFactory.setGhost(playerFor, false);

		ReviveManager.getInstance().setVulnerable(playerFor, false);
		DataWrapper.set(playerFor, UserEntries.DEATHS, DataWrapper.<Integer> get(playerFor, UserEntries.DEATHS) + 1);

		Utilities.setBleeding(playerFor, false, true);
		Utilities.setPoisoned(playerFor, false, true);

		overriding.add(SQLManager.primaryKeyFor(playerFor));
		if (playerFor.getHealth() > 0) {
			playerFor.setHealth(0);
		}

		// TODO zero stats of unranked players
	}

	private static void treatDeathMessage(Player player) {
		if (!ConfigEntries.DEATH_MESSAGES.<Boolean> getValue()) { return; }

		List<Player> to = Utilities.getPlayersInRange(player.getLocation(), ConfigEntries.CHAT_RADIUS.<Integer> getValue());
		to.remove(player);

		if (!ReviveManager.getInstance().isVulnerable(player) && overriding.contains(SQLManager.primaryKeyFor(player))) {
			DeathCause.GHOSTLY.compileAndSendAsJSON(player, to);
			return;
		}

		switch (player.getLastDamageCause().getCause()) {
		case BLOCK_EXPLOSION:
		case ENTITY_EXPLOSION:
			DeathCause.EXPLOSION.compileAndSendAsJSON(player, to);
			break;
		case CONTACT:
			DeathCause.CACTUS.compileAndSendAsJSON(player, to);
			break;
		case DROWNING:
			DeathCause.DROWNING.compileAndSendAsJSON(player, to);
			break;
		case ENTITY_ATTACK:
			Entity ent = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
			switch (ent.getType()) {
			// TODO maybe pass entities as parameter to determine special stats?
			case GIANT:
				DeathCause.GIANT.compileAndSendAsJSON(player, to);
				break;
			case PIG_ZOMBIE:
				DeathCause.PIGMAN.compileAndSendAsJSON(player, to);
				break;
			case PLAYER:
				DeathCause.PLAYER.compileAndSendAsJSON(player, (Player) ent, to);
				break;
			case ZOMBIE:
				DeathCause.ZOMBIE.compileAndSendAsJSON(player, to);
				break;
			default:
				DeathCause.OTHER.compileAndSendAsJSON(player, to);
				break;
			}
			break;
		case FALL:
			DeathCause.FALL.compileAndSendAsJSON(player, to);
			break;
		case FALLING_BLOCK:
		case SUFFOCATION:
			DeathCause.SUFFOCATION.compileAndSendAsJSON(player, to);
			break;
		case FIRE:
		case FIRE_TICK:
			DeathCause.FIRE.compileAndSendAsJSON(player, to);
			break;
		case LAVA:
			DeathCause.LAVA.compileAndSendAsJSON(player, to);
			break;
		case MAGIC:
			DeathCause.MAGIC.compileAndSendAsJSON(player, to);
			break;
		case POISON:
			DeathCause.POISON.compileAndSendAsJSON(player, to);
			break;
		case PROJECTILE:
			// TODO maybe use shooter instead and treat as player (unless
			// dispenser, etc).
			DeathCause.ARROW.compileAndSendAsJSON(player, to);
			break;
		case STARVATION:
			DeathCause.STARVE.compileAndSendAsJSON(player, to);
			break;
		case VOID:
			DeathCause.VOID.compileAndSendAsJSON(player, to);
			break;
		default:
			DeathCause.OTHER.compileAndSendAsJSON(player, to);
			break;
		}
	}
}
