package darknesgaming.nms.v1_8_R3.mobs.pathfinders;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

public class CustomPathfinderGoalRandomLookaround extends PathfinderGoal {

    private final EntityInsentient creature;
    private double xOffset;
    private double zOffset;
    private int lookAway;

    public CustomPathfinderGoalRandomLookaround(EntityInsentient creature) {
        this.creature = creature;
        this.a(3);
    }

    public boolean a() {
        return this.creature.bb().nextFloat() < 0.02F;
    }

    public boolean b() {
        return this.lookAway >= 0;
    }

    public void c() {
        double d1 = 6.283185307179586D * this.creature.bb().nextDouble();

        this.xOffset = Math.cos(d1);
        this.zOffset = Math.sin(d1);
        this.lookAway = 20 + this.creature.bb().nextInt(20);
    }

    public void e() {
        --this.lookAway;
        this.creature.getControllerLook().a(this.creature.locX + this.xOffset, this.creature.locY + (double) this.creature.getHeadHeight(), this.creature.locZ + this.zOffset, 10.0F, (float) this.creature.bP());
    }
}