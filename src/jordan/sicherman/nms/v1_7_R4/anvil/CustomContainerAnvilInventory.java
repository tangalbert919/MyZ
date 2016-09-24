package jordan.sicherman.nms.v1_7_R4.anvil;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.InventorySubcontainer;
import net.minecraft.server.v1_7_R4.ItemStack;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class CustomContainerAnvilInventory extends InventorySubcontainer {

    final CustomContainerAnvil anvil;
    public List transaction = new ArrayList();
    public Player player;
    private int maxStack = 64;

    public ItemStack[] getContents() {
        return this.items;
    }

    public void onOpen(CraftHumanEntity who) {
        this.transaction.add(who);
    }

    public void onClose(CraftHumanEntity who) {
        this.transaction.remove(who);
    }

    public List getViewers() {
        return this.transaction;
    }

    public InventoryHolder getOwner() {
        return this.player;
    }

    public void setMaxStackSize(int size) {
        this.maxStack = size;
    }

    CustomContainerAnvilInventory(CustomContainerAnvil containeranvil, String title, boolean customName, int size) {
        super(title, customName, size);
        this.anvil = containeranvil;
    }

    public void update() {
        super.update();
        this.anvil.a((IInventory) this);
    }

    public int getMaxStackSize() {
        return this.maxStack;
    }
}
