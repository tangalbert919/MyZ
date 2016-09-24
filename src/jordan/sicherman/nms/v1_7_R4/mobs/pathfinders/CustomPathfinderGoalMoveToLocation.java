package jordan.sicherman.nms.v1_7_R4.mobs.pathfinders;

import jordan.sicherman.nms.v1_7_R4.mobs.SmartEntity;
import net.minecraft.server.v1_7_R4.PathfinderGoal;
import org.bukkit.Location;

public class CustomPathfinderGoalMoveToLocation extends PathfinderGoal {

    private final SmartEntity creature;
    private Location target;
    private double targetX;
    private double targetY;
    private double targetZ;
    private final double speed;

    public CustomPathfinderGoalMoveToLocation(SmartEntity creature, double speed) {
        this.creature = creature;
        this.speed = speed;
        this.a(1);
    }

    public boolean a() {
        this.target = this.creature.getSmartTarget();
        if (this.target == null) {
            return false;
        } else {
            this.targetX = this.target.getX();
            this.targetY = this.target.getY();
            this.targetZ = this.target.getZ();
            return true;
        }
    }

    public boolean b() {
        return !this.creature.getEntity().getNavigation().g();
    }

    public void d() {
        this.target = null;
    }

    public void c() {
        this.creature.getEntity().getNavigation().a(this.targetX, this.targetY, this.targetZ, this.speed);
    }
}
