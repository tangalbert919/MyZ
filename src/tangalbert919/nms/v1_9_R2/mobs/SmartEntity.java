package tangalbert919.nms.v1_9_R2.mobs;

import net.minecraft.server.v1_9_R2.EntityCreature;
import org.bukkit.Location;

public interface SmartEntity {

    void setSmartTarget(Location location, long i);

    Location getSmartTarget();

    EntityCreature getEntity();
    
}
