package darknesgaming.nms.v1_9_R2.mobs;

import org.bukkit.Location;

import net.minecraft.server.v1_9_R2.EntityCreature;

public interface SmartEntity {

    void setSmartTarget(Location location, long i);

    Location getSmartTarget();

    EntityCreature getEntity();
}

