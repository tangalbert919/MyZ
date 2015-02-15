/**
 * 
 */
package jordan.sicherman.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import jordan.sicherman.MyZ;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

/*
*
*    This class is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This class is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this class.  If not, see <http://www.gnu.org/licenses/>.
*
*/

/**
 * 
 * @author Max
 * @author Jordan
 */
public class Schematic {

	private final byte[] blocks, data;
	private final short width, length, height;

	public Schematic(byte[] blocks, byte[] data, short width, short length, short height) {
		this.blocks = blocks;
		this.data = data;
		this.width = width;
		this.length = length;
		this.height = height;
	}

	public byte[] getBlocks() {
		return blocks;
	}

	public byte[] getData() {
		return data;
	}

	public short getWidth() {
		return width;
	}

	public short getLength() {
		return length;
	}

	public short getHeight() {
		return height;
	}

	@SuppressWarnings("deprecation")
	public static void pasteSchematic(World world, Location loc, Schematic schematic, BlockFace direction) {
		byte[] blocks = schematic.getBlocks();
		byte[] blockData = schematic.getData();

		short length = schematic.getLength();
		short width = schematic.getWidth();
		short height = schematic.getHeight();

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				for (int z = 0; z < length; ++z) {
					int index = y * width * length + z * width + x;
					Location inLoc = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ());
					if (direction != null) {
						FallingBlock fallingBlock = world.spawnFallingBlock(inLoc, blocks[index], blockData[index]);
						Vector moveVector = inLoc.getBlock().getRelative(direction, 3).getLocation().toVector().subtract(inLoc.toVector());
						fallingBlock.setVelocity(moveVector);
						fallingBlock.setMetadata("MyZ.airdrop", new FixedMetadataValue(MyZ.instance, true));
					} else {
						Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
						block.setTypeIdAndData(blocks[index], blockData[index], true);
					}
				}
			}
		}
	}

	public static Schematic loadSchematic(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		NBTInputStream nbtStream = new NBTInputStream(stream);

		CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
		if (!schematicTag.getName().equals("Schematic")) {
			nbtStream.close();
			throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");
		}

		Map<String, Tag> schematic = schematicTag.getValue();
		if (!schematic.containsKey("Blocks")) {
			nbtStream.close();
			throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
		}

		short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
		short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
		short height = getChildTag(schematic, "Height", ShortTag.class).getValue();

		String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
		if (!materials.equals("Alpha")) {
			nbtStream.close();
			throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
		}

		byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
		byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();

		nbtStream.close();

		return new Schematic(blocks, blockData, width, length, height);
	}

	private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException {
		if (!items.containsKey(key)) { throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag"); }
		Tag tag = items.get(key);
		if (!expected.isInstance(tag)) { throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName()); }
		return expected.cast(tag);
	}
}