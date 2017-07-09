package jordan.sicherman.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import jordan.sicherman.MyZ;
import jordan.sicherman.jnbt.ByteArrayTag;
import jordan.sicherman.jnbt.CompoundTag;
import jordan.sicherman.jnbt.NBTInputStream;
import jordan.sicherman.jnbt.ShortTag;
import jordan.sicherman.jnbt.StringTag;
import jordan.sicherman.jnbt.Tag;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class Schematic {

    private final byte[] blocks;
    private final byte[] data;
    private final short width;
    private final short length;
    private final short height;

    public Schematic(byte[] blocks, byte[] data, short width, short length, short height) {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public byte[] getBlocks() {
        return this.blocks;
    }

    public byte[] getData() {
        return this.data;
    }

    public short getWidth() {
        return this.width;
    }

    public short getLength() {
        return this.length;
    }

    public short getHeight() {
        return this.height;
    }

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
                    Location inLoc = new Location(world, (double) x + loc.getX(), (double) y + loc.getY(), (double) z + loc.getZ());

                    if (direction != null) {
                        FallingBlock block = world.spawnFallingBlock(inLoc, blocks[index], blockData[index]);
                        Vector moveVector = inLoc.getBlock().getRelative(direction, 3).getLocation().toVector().subtract(inLoc.toVector());

                        block.setVelocity(moveVector);
                        block.setMetadata("MyZ.airdrop", new FixedMetadataValue(MyZ.instance, Boolean.valueOf(true)));
                    } else {
                        Block block = (new Location(world, (double) x + loc.getX(), (double) y + loc.getY(), (double) z + loc.getZ())).getBlock();

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
        } else {
            Map schematic = schematicTag.getValue();

            if (!schematic.containsKey("Blocks")) {
                nbtStream.close();
                throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");
            } else {
                short width = ((ShortTag) getChildTag(schematic, "Width", ShortTag.class)).getValue().shortValue();
                short length = ((ShortTag) getChildTag(schematic, "Length", ShortTag.class)).getValue().shortValue();
                short height = ((ShortTag) getChildTag(schematic, "Height", ShortTag.class)).getValue().shortValue();
                String materials = ((StringTag) getChildTag(schematic, "Materials", StringTag.class)).getValue();

                if (!materials.equals("Alpha")) {
                    nbtStream.close();
                    throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
                } else {
                    byte[] blocks = ((ByteArrayTag) getChildTag(schematic, "Blocks", ByteArrayTag.class)).getValue();
                    byte[] blockData = ((ByteArrayTag) getChildTag(schematic, "Data", ByteArrayTag.class)).getValue();

                    nbtStream.close();
                    return new Schematic(blocks, blockData, width, length, height);
                }
            }
        }
    }

    private static Tag getChildTag(Map items, String key, Class expected) throws IllegalArgumentException {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        } else {
            Tag tag = (Tag) items.get(key);

            if (!expected.isInstance(tag)) {
                throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
            } else {
                return (Tag) expected.cast(tag);
            }
        }
    }
}
