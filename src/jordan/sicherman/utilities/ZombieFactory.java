/**
 * 
 */
package jordan.sicherman.utilities;

import jordan.sicherman.utilities.configuration.UserEntries;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 *         Okay so maybe I didn't need an entire "factory" dedicated to this,
 *         but I wanted more than just a booming ghost industry.
 */
public class ZombieFactory {

	public boolean isZombie(Player player) {
		return DataWrapper.<Boolean> get(player, UserEntries.ZOMBIE);
	}

	public void setZombie(Player player, boolean zombie) {
		DataWrapper.set(player, UserEntries.ZOMBIE, zombie);

		if (zombie) {
			MobDisguise disguise = new MobDisguise(DisguiseType.ZOMBIE);
			DisguiseAPI.disguiseToAll(player, disguise);
		} else {
			DisguiseAPI.undisguiseToAll(player);
		}
	}
}
