/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.anvil;

import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Slot;

/**
 * @author Jordan
 * 
 */
public class CustomSlotAnvil extends Slot {

	private final CustomContainerAnvil anvil;
	private final IInventory result;

	public CustomSlotAnvil(CustomContainerAnvil anvil, CustomContainerAnvilInventory iinventory, IInventory other, int index, int x, int y) {
		super(iinventory, index, x, y);
		this.anvil = anvil;
		result = other;
	}

	@Override
	public boolean isAllowed(EntityHuman human) {
		if (!anvil.isRealAnvil) { return true; }
		return anvil.validConvert || result.getItem(0) == null;
	}

	@Override
	public boolean isAllowed(ItemStack item) {
		if (!anvil.isRealAnvil) { return true; }
		return anvil.validConvert || result.getItem(0) == null;
	}
}
