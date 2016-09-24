package jordan.sicherman.utilities;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializableLocation {

    private final UUID uid;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public SerializableLocation(UUID uid, double x, double y, double z, float yaw, float pitch) {
        this.uid = uid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String serialize() {
        return this.uid.toString() + ", " + this.x + ", " + this.y + ", " + this.z + (this.yaw != 0.0F ? ", " + this.yaw : "") + (this.pitch != 0.0F ? ", " + this.pitch : "");
    }

    public static Location deserialize(String str) {
        try {
            String[] exc = str.split(", ");
            World e1 = Bukkit.getWorld(UUID.fromString(exc[0]));

            return e1 == null ? null : new Location(e1, Double.parseDouble(exc[1]), Double.parseDouble(exc[2]), Double.parseDouble(exc[3]), Float.parseFloat(exc[4]), Float.parseFloat(exc[5]));
        } catch (Exception exception) {
            try {
                String[] e = str.split(", ");
                World world = Bukkit.getWorld(UUID.fromString(e[0]));

                return world == null ? null : new Location(world, Double.parseDouble(e[1]), Double.parseDouble(e[2]), Double.parseDouble(e[3]));
            } catch (Exception exception1) {
                return null;
            }
        }
    }

    public static SerializableLocation fromLocation(Location loc) {
        return new SerializableLocation(loc.getWorld().getUID(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
}
