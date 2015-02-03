/**
 * 
 */
package jordan.sicherman.nms.v1_8_R1.anvil;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R1.InventorySubcontainer;
import net.minecraft.server.v1_8_R1.ItemStack;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class CustomContainerAnvilInventory extends InventorySubcontainer {

	final CustomContainerAnvil anvil;
	public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
	public Player player;
	private int maxStack = 64;

	@Override
	public ItemStack[] getContents() {
		return items;
	}

	@Override
	public void onOpen(CraftHumanEntity who) {
		transaction.add(who);
	}

	@Override
	public void onClose(CraftHumanEntity who) {
		transaction.remove(who);
	}

	@Override
	public List<HumanEntity> getViewers() {
		return transaction;
	}

	@Override
	public InventoryHolder getOwner() {
		return player;
	}

	@Override
	public void setMaxStackSize(int size) {
		maxStack = size;
	}

	CustomContainerAnvilInventory(CustomContainerAnvil containeranvil, String title, boolean customName, int size) {
		super(title, customName, size);
		anvil = containeranvil;
	}

	@Override
	public void update() {
		super.update();
		anvil.a(this);
	}

	@Override
	public int getMaxStackSize() {
		return maxStack;
	}
}
