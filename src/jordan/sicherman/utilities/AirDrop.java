/**
 * 
 */
package jordan.sicherman.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import jordan.sicherman.MyZ;
import jordan.sicherman.utilities.configuration.FileUtilities;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

/**
 * @author Jordan
 * 
 */
public class AirDrop {

	public static final BlockFace[] directions = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
	private static final Random random = new Random();

	public AirDrop(final Location pasteLocation) {
		MyZ.instance.getServer().getScheduler().runTask(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				try {
					BlockFace direction = directions[random.nextInt(directions.length)];
					Schematic.pasteSchematic(
							pasteLocation.getWorld(),
							pasteLocation,
							Schematic.loadSchematic(FileUtilities.getSchematicFile("airdrop" + File.separator
									+ direction.name().toLowerCase() + File.separator + "craft$0")), direction);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
