package tangalbert919.nms.v1_9_R2.anvil;

import net.minecraft.server.v1_9_R2.*;

public class TileEntityCustomContainerAnvil extends BlockAnvil.TileEntityContainerAnvil {

	   private final World world;
	    private final BlockPosition position;
	    private final boolean isRealAnvil;

	    public TileEntityCustomContainerAnvil(World paramWorld, BlockPosition paramBlockPosition, boolean isRealAnvil) {
	        super(paramWorld, paramBlockPosition);
	        this.world = paramWorld;
	        this.position = paramBlockPosition;
	        this.isRealAnvil = isRealAnvil;
	    }

	    public Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman) {
	        return new CustomContainerAnvil(paramPlayerInventory, this.world, this.position, paramEntityHuman, this.isRealAnvil);
	    }

	    public String getName() {
	        return "anvil";
	    }

	    public boolean hasCustomName() {
	        return false;
	    }

	    public IChatBaseComponent getScoreboardDisplayName() {
	        return new ChatMessage(Blocks.ANVIL.a() + ".name", new Object[0]);
	    }

	    public String getContainerName() {
	        return "minecraft:anvil";
	    }
}
