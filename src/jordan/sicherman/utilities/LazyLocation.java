package jordan.sicherman.utilities;

import org.bukkit.Location;

public class LazyLocation {

    private final double x;
    private final double y;
    private final double z;

    public LazyLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static LazyLocation fromLocation(Location inLoc) {
        return new LazyLocation(inLoc.getX(), inLoc.getY(), inLoc.getZ());
    }

    public boolean equals(Location other) {
        return other.getX() == this.x && other.getY() == this.y && other.getZ() == this.z;
    }

    boolean isBelow(Location other) {
        return other.getY() > this.y;
    }
}
