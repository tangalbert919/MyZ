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
		SPAWN("spawn_manager", -1), ENGINEER("engineer_manager", -1), CHESTS("chest_manager", null);

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
					player.setNoDamageTicks(Integer.MAX_VALUE);
					player.setAllowFlight(true);
				}
				break;
			case ENGINEER:
			case CHESTS:
				player.setGameMode(GameMode.CREATIVE);
				break;
			}

			player.getInventory().setItemInHand(ItemUtilities.getInstance().getTagItem(ItemTag.WAND, 1));
		} else {
			player.removeMetadata(of.getID(), MyZ.instance);

			switch (of) {
			case SPAWN:
				player.setNoDamageTicks(0);
				if (player.getGameMode() != GameMode.CREATIVE) {
					player.setAllowFlight(false);
				}
				break;
			case ENGINEER:
			case CHESTS:
				if (!player.isOp()) {
					player.setGameMode(GameMode.SURVIVAL);
				}
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
