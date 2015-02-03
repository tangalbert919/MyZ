/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.anvil;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Blocks;
import net.minecraft.server.v1_8_R1.ChatMessage;
import net.minecraft.server.v1_8_R1.Container;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PlayerInventory;
import net.minecraft.server.v1_8_R1.TileEntityContainerAnvil;
import net.minecraft.server.v1_8_R1.World;

public class TileEntityCustomContainerAnvil extends TileEntityContainerAnvil {

	private final World world;
	private final BlockPosition position;
	private final boolean isRealAnvil;

	public TileEntityCustomContainerAnvil(World paramWorld, BlockPosition paramBlockPosition, boolean isRealAnvil) {
		super(paramWorld, paramBlockPosition);

		world = paramWorld;
		position = paramBlockPosition;
		this.isRealAnvil = isRealAnvil;
	}

	@Override
	public Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman) {
		return new CustomContainerAnvil(paramPlayerInventory, world, position, paramEntityHuman, isRealAnvil);
	}

	@Override
	public String getName() {
		return "anvil";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatBaseComponent getScoreboardDisplayName() {
		return new ChatMessage(Blocks.ANVIL.a() + ".name", new Object[0]);
	}

	@Override
	public String getContainerName() {
		return "minecraft:anvil";
	}
}
