package jordan.sicherman.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import jordan.sicherman.MyZ;
import jordan.sicherman.utilities.configuration.FileUtilities;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class AirDrop {

    public static final BlockFace[] directions = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    private static final Random random = new Random();

    public AirDrop(final Location pasteLocation) {
        MyZ.instance.getServer().getScheduler().runTask(MyZ.instance, new Runnable() {
            public void run() {
                try {
                    BlockFace e = AirDrop.directions[AirDrop.random.nextInt(AirDrop.directions.length)];

                    Schematic.pasteSchematic(pasteLocation.getWorld(), pasteLocation, Schematic.loadSchematic(FileUtilities.getSchematicFile("airdrop" + File.separator + e.name().toLowerCase() + File.separator + "craft$0")), e);
                } catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }

            }
        });
    }
}
