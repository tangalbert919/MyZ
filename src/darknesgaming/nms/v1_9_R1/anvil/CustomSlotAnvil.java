package darknesgaming.nms.v1_9_R1.anvil;

import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.IInventory;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.Slot;

public class CustomSlotAnvil extends Slot {

    private final CustomContainerAnvil anvil;
    private final IInventory result;

    public CustomSlotAnvil(CustomContainerAnvil anvil, CustomContainerAnvilInventory iinventory, IInventory other, int index, int x, int y) {
        super(iinventory, index, x, y);
        this.anvil = anvil;
        this.result = other;
    }

    public boolean isAllowed(EntityHuman human) {
        return !this.anvil.isRealAnvil ? true : this.anvil.validConvert || this.result.getItem(0) == null;
    }

    public boolean isAllowed(ItemStack item) {
        return !this.anvil.isRealAnvil ? true : this.anvil.validConvert || this.result.getItem(0) == null;
    }
}
