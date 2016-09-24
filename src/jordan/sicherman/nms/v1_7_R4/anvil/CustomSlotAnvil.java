package jordan.sicherman.nms.v1_7_R4.anvil;

import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Slot;

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
