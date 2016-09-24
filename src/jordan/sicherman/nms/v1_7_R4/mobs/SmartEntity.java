package jordan.sicherman.nms.v1_7_R4.mobs;

import net.minecraft.server.v1_7_R4.EntityCreature;
import org.bukkit.Location;

public interface SmartEntity {

    void setSmartTarget(Location location, long i);

    Location getSmartTarget();

    EntityCreature getEntity();
}
