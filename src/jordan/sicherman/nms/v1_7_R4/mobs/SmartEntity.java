/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.mobs;

import net.minecraft.server.v1_7_R4.EntityCreature;

import org.bukkit.Location;

/**
 * @author Jordan
 * 
 */
public interface SmartEntity {

	public void setSmartTarget(Location loc, long duration);

	public Location getSmartTarget();

	public EntityCreature getEntity();
}
