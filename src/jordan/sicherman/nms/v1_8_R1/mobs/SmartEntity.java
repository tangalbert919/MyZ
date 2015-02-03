/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.mobs;

import net.minecraft.server.v1_8_R1.EntityCreature;

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
