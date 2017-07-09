package tangalbert919.nms.v1_9_R1.mobs;

import net.minecraft.server.v1_9_R1.PathfinderGoalSelector;

import java.lang.reflect.Field;

public class CommonMobUtilities {

    protected static Field bField;
    protected static Field cField;

    static {
        try {
            CommonMobUtilities.bField = PathfinderGoalSelector.class.getDeclaredField("b");
            CommonMobUtilities.bField.setAccessible(true);
        } catch (Exception exception) {
            CommonMobUtilities.bField = null;
        }

        try {
            CommonMobUtilities.cField = PathfinderGoalSelector.class.getDeclaredField("c");
            CommonMobUtilities.cField.setAccessible(true);
        } catch (Exception exception1) {
            CommonMobUtilities.cField = null;
        }

    }
}
