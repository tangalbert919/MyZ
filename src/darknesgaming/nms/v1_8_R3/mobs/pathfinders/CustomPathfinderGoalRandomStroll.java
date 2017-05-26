package darknesgaming.nms.v1_8_R3.mobs.pathfinders;

import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.RandomPositionGenerator;
import net.minecraft.server.v1_8_R3.Vec3D;

public class CustomPathfinderGoalRandomStroll extends PathfinderGoal {

    private final EntityCreature creature;
    private double destX;
    private double destY;
    private double destZ;
    private final double speed;
    private int chanceToNot;
    private boolean notPathing;

    public CustomPathfinderGoalRandomStroll(EntityCreature creature, double speed) {
        this(creature, speed, 120);
    }

    public CustomPathfinderGoalRandomStroll(EntityCreature creature, double speed, int chanceToNot) {
        this.creature = creature;
        this.speed = speed;
        this.chanceToNot = chanceToNot;
        this.a(1);
    }

    public boolean a() {
        if (!this.notPathing) {
            if (this.creature.bg() >= 100) {
                return false;
            }

            if (this.creature.bc().nextInt(this.chanceToNot) != 0) {
                return false;
            }
        }

        Vec3D destination = RandomPositionGenerator.a(this.creature, 10, 7);

        if (destination == null) {
            return false;
        } else {
            this.destX = destination.a;
            this.destY = destination.b;
            this.destZ = destination.c;
            this.notPathing = false;
            return true;
        }
    }

    public boolean b() {
        return !this.creature.getNavigation().m();
    }

    public void c() {
        this.creature.getNavigation().a(this.destX, this.destY, this.destZ, this.speed);
    }

    public void f() {
        this.notPathing = true;
    }

    public void b(int paramInt) {
        this.chanceToNot = paramInt;
    }
}

