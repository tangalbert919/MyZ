/**
 * 
 */
package jordan.sicherman.utilities;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author Jordan
 * 
 */
public class SerializableLocation {

	private final UUID uid;
	private final double x, y, z;
	private final float yaw, pitch;

	public SerializableLocation(UUID uid, double x, double y, double z, float yaw, float pitch) {
		this.uid = uid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public String serialize() {
		return uid.toString() + ", " + x + ", " + y + ", " + z + (yaw != 0 ? ", " + yaw : "") + (pitch != 0 ? ", " + pitch : "");
	}

	public static Location deserialize(String str) {
		try {
			String[] parts = str.split(", ");
			World world = Bukkit.getWorld(UUID.fromString(parts[0]));
			if (world == null) { return null; }
			return new Location(world, Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
					Float.parseFloat(parts[4]), Float.parseFloat(parts[5]));
		} catch (Exception exc) {
			try {
				String[] parts = str.split(", ");
				World world = Bukkit.getWorld(UUID.fromString(parts[0]));
				if (world == null) { return null; }
				return new Location(world, Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
			} catch (Exception e) {
				return null;
			}
		}
	}

	public static SerializableLocation fromLocation(Location loc) {
		return new SerializableLocation(loc.getWorld().getUID(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
}
