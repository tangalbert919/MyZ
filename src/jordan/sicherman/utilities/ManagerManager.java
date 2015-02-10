/**
 * 
 */
package jordan.sicherman.utilities;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * @author Jordan
 * 
 */
public class ManagerManager {

	public static enum ManagerType {
		SPAWN("spawn_manager", -1), ENGINEER("engineer_manager", -1), CHESTS("chest_manager", null), SPAWN_KIT("spawnkit_manager", null), CHAT_PREFIX(
				"chatprefix_manager", null);

		private final String id;
		private final Object startValue;

		private ManagerType(String id, Object startValue) {
			this.id = "MyZ." + id;
			this.startValue = startValue;
		}

		public String getID() {
			return id;
		}

		public Object getStartValue() {
			return startValue;
		}
	}

	public static boolean setManager(Player player, boolean manager, ManagerType of) {
		if (manager && isManager(player)) {
			stopManaging(player);
		}

		if (manager) {
			player.setMetadata(of.getID(), new FixedMetadataValue(MyZ.instance, of.getStartValue()));
			switch (of) {
			case SPAWN:
				if (player.getGameMode() != GameMode.CREATIVE) {
					player.setAllowFlight(true);
				}
				break;
			case ENGINEER:
			case CHESTS:
			case SPAWN_KIT:
				player.setGameMode(GameMode.CREATIVE);
				break;
			default:
				break;
			}

			if (of != ManagerType.SPAWN_KIT && of != ManagerType.CHAT_PREFIX) {
				player.getInventory().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.WAND, 1));
			}
		} else {
			player.removeMetadata(of.getID(), MyZ.instance);

			switch (of) {
			case SPAWN:
				player.setAllowFlight(false);
				break;
			case ENGINEER:
			case CHESTS:
			case SPAWN_KIT:
				if (!player.isOp()) {
					player.setGameMode(GameMode.SURVIVAL);
				}
				break;
			default:
				break;
			}
		}
		return true;
	}

	public static boolean isManager(Player player, ManagerType of) {
		return player.hasMetadata(of.getID());
	}

	public static boolean isManager(Player player) {
		for (ManagerType type : ManagerType.values()) {
			if (isManager(player, type)) { return true; }
		}
		return false;
	}

	public static void stopManaging(Player player) {
		for (ManagerType type : ManagerType.values()) {
			if (isManager(player, type)) {
				setManager(player, false, type);
			}
		}
	}
}
