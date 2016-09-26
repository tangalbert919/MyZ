package darknesgaming.nms.v1_8_R3.anvil;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class TileEntityCustomContainerAnvil extends TileEntity {

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
