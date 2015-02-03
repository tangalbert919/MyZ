/**
 * 
 */
package jordan.sicherman.utilities;

import org.bukkit.Location;

/**
 * @author Jordan
 * 
 */
public class LazyLocation {

	private final double x, y, z;

	public LazyLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static LazyLocation fromLocation(Location inLoc) {
		return new LazyLocation(inLoc.getX(), inLoc.getY(), inLoc.getZ());
	}

	public boolean equals(Location other) {
		return other.getX() == x && other.getY() == y && other.getZ() == z;
	}

	boolean isBelow(Location other) {
		return other.getY() > y;
	}
}
