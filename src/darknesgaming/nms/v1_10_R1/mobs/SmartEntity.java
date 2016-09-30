package darknesgaming.nms.v1_10_R1.mobs;

import org.bukkit.Location;

import net.minecraft.server.v1_10_R1.EntityCreature;

public interface SmartEntity {

    void setSmartTarget(Location location, long i);

    Location getSmartTarget();

    EntityCreature getEntity();
}

