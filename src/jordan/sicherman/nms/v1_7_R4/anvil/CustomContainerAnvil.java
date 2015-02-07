/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4.anvil;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerManager;
import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ReverseEngineer;
import net.minecraft.server.v1_7_R4.Container;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.ICrafting;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.InventoryCraftResult;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.PlayerInventory;
import net.minecraft.server.v1_7_R4.Slot;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryAnvil;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class CustomContainerAnvil extends Container {

	public EngineerRecipe activeRecipe;
	public final IInventory result = new InventoryCraftResult();
	public final CustomContainerAnvilInventory process = new CustomContainerAnvilInventory(this, "Repair", true, 2);
	private final World world;
	public int expCost;
	private final EntityHuman human;
	private CraftInventoryView bukkitEntity = null;
	private final PlayerInventory pInventory;

	public boolean isRealAnvil, validConvert, flag;
	private int remainingRight, remainingLeft;

	public CustomContainerAnvil(PlayerInventory playerinventory, World world, int i, int j, int k, EntityHuman entityhuman,
			boolean isRealAnvil) {
		pInventory = playerinventory;
		this.world = world;
		human = entityhuman;
		a(new CustomSlotAnvil(this, process, result, 0, 27, 47));
		a(new CustomSlotAnvil(this, process, result, 1, 76, 47));
		a(new Slot(result, 2, 134, 47));

		for (int l = 0; l < 3; l++) {
			for (int i1 = 0; i1 < 9; i1++) {
				a(new Slot(playerinventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}
		for (int l = 0; l < 9; l++) {
			a(new Slot(playerinventory, l, 8 + l * 18, 142));
		}

		this.isRealAnvil = isRealAnvil;
	}

	@Override
	/**
	 * Called when a player puts an item or clicks in a repair slot.
	 */
	public void a(IInventory iinventory) {
		super.a(iinventory);
	}

	public boolean updateOn(int slot) {
		if (!isRealAnvil) { return false; }

		if (slot == 2) {
			if (validConvert || process.getItem(0) == null && process.getItem(1) == null) {
				if (flag && process.getItem(1) != null) {
					if (remainingRight > 0) {
						process.getItem(1).count = remainingRight;
					} else {
						process.setItem(1, null);
					}
					if (remainingLeft > 0) {
						process.getItem(0).count = remainingLeft;
					} else {
						process.setItem(0, null);
					}
					flag = false;
				} else {
					process.setItem(0, null);
					process.setItem(1, null);
				}

				validConvert = false;
				updateEngineer();
				return false;
			}
			return true;
		} else {
			if (validConvert) {
				result.setItem(0, null);
				validConvert = false;
			}
			updateEngineer();
			return false;
		}
	}

	private void updateEngineer() {
		MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
			@Override
			public void run() {
				if (result.getItem(0) != null && process.getItem(0) == null && process.getItem(1) == null) {
					// Reverse engineer result(0)
					org.bukkit.inventory.ItemStack item = CraftItemStack.asBukkitCopy(result.getItem(0));
					org.bukkit.inventory.ItemStack itemResult = ReverseEngineer.getDecomposition(item);
					if (ReverseEngineer.getDecompositionAmount(item) == 0) {
						for (EngineerRecipe recipe : EngineerManager.getInstance().getRecipes()) {
							if (recipe.getOutput().isSimilar(CraftItemStack.asBukkitCopy(result.getItem(0)))) {
								int require = recipe.getOutput().getAmount();
								if (result.getItem(0).count >= require) {
									if (result.getItem(0).count % require != 0) { return; }

									int times = result.getItem(0).count / require;
									org.bukkit.inventory.ItemStack a = recipe.getInput(0);
									a.setAmount(a.getAmount() * times);
									org.bukkit.inventory.ItemStack b = recipe.getInput(1);
									b.setAmount(b.getAmount() * times);
									process.setItem(0, CraftItemStack.asNMSCopy(a));
									process.setItem(1, CraftItemStack.asNMSCopy(b));
									validConvert = true;
									return;
								}
							}
						}
						return;
					}

					org.bukkit.inventory.ItemStack result2 = itemResult.clone();

					int half = itemResult.getAmount() * item.getAmount() / 2;
					int other_half = itemResult.getAmount() * item.getAmount() - half;
					itemResult.setAmount(half);
					result2.setAmount(other_half);

					process.setItem(0, half > 0 ? CraftItemStack.asNMSCopy(itemResult) : null);
					process.setItem(1, other_half > 0 ? CraftItemStack.asNMSCopy(result2) : null);
					validConvert = true;
				} else if (result.getItem(0) == null && process.getItem(0) != null && process.getItem(1) != null) {
					// Forward engineer process(0) and (1).
					org.bukkit.inventory.ItemStack primary = CraftItemStack.asBukkitCopy(process.getItem(0));
					org.bukkit.inventory.ItemStack material = CraftItemStack.asBukkitCopy(process.getItem(1));

					Material m = ReverseEngineer.getRawMaterial(primary);
					if (!ReverseEngineer.isUpgradable(primary)) {
						for (EngineerRecipe recipe : EngineerManager.getInstance().getRecipes()) {
							if (recipe.getInput(0).isSimilar(CraftItemStack.asBukkitCopy(process.getItem(0)))
									&& recipe.getInput(0).isSimilar(CraftItemStack.asBukkitCopy(process.getItem(0)))) {
								int requireLeft = recipe.getInput(0).getAmount();
								int requireRight = recipe.getInput(1).getAmount();
								if (process.getItem(0).count >= requireLeft && process.getItem(1).count >= requireRight) {
									result.setItem(0, CraftItemStack.asNMSCopy(recipe.getOutput()));
									validConvert = true;
									flag = true;
									remainingRight = process.getItem(1).count - requireRight;
									remainingLeft = process.getItem(0).count - requireLeft;
									return;
								}
							}
						}
						return;
					}

					if (m == null) { return; }

					if (m == material.getType()) {
						EquipmentState upgrade = EquipmentState.getNext(primary);
						if (upgrade == null) { return; }

						if (process.getItem(1).count >= ReverseEngineer.getUpgradeAmount()) {
							result.setItem(0, CraftItemStack.asNMSCopy(upgrade.applyTo(primary.clone())));
							validConvert = true;
							flag = true;
							remainingLeft = 0;
							remainingRight = process.getItem(1).count - ReverseEngineer.getUpgradeAmount();
						}
					}
				}
			}
		}, 0L);
	}

	@Override
	/**
	 * Called when the inventory is opened.
	 */
	public void addSlotListener(ICrafting icrafting) {
		super.addSlotListener(icrafting);
		icrafting.setContainerData(this, 0, expCost);
	}

	@Override
	/**
	 * Called when the inventory closes.
	 */
	public void b(EntityHuman entityhuman) {
		super.b(entityhuman);

		if (!world.isStatic) {
			if (!isRealAnvil) {
				ItemStack in1 = process.getItem(0);
				ItemStack in2 = process.getItem(1);
				ItemStack out = result.getItem(0);
				if (activeRecipe != null) {
					EngineerManager.getInstance().modifyRecipe(activeRecipe, (Player) entityhuman.getBukkitEntity(),
							CraftItemStack.asBukkitCopy(in1), CraftItemStack.asBukkitCopy(in2), CraftItemStack.asBukkitCopy(out));
				} else {
					EngineerManager.getInstance().createRecipe((Player) entityhuman.getBukkitEntity(), CraftItemStack.asBukkitCopy(in1),
							CraftItemStack.asBukkitCopy(in2), CraftItemStack.asBukkitCopy(out));
				}
				return;
			}

			if (!validConvert || flag) {
				for (int i = 0; i < process.getSize(); i++) {
					ItemStack itemstack = process.splitWithoutUpdate(i);
					if (itemstack != null) {
						entityhuman.drop(itemstack, false);
					}
				}
			} else {
				ItemStack itemstack = result.splitWithoutUpdate(0);
				if (itemstack != null) {
					entityhuman.drop(itemstack, false);
				}
			}
		}
	}

	@Override
	/**
	 * Called when this display is showing.
	 */
	public boolean a(EntityHuman entityhuman) {
		((EntityPlayer) human).a(human.activeContainer, human.activeContainer.a());
		return true;
	}

	@Override
	/**
	 * Called when shift clicking an item into this inventory.
	 */
	public ItemStack b(EntityHuman entityhuman, int index) {
		ItemStack itemResult = null;
		Slot slot = (Slot) c.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack inSlot = slot.getItem();

			itemResult = inSlot.cloneItemStack();
			if (index == 2) {
				if (!a(inSlot, 3, 39, true)) { return null; }
				slot.a(inSlot, itemResult);
			} else if (index != 0 && index != 1) {
				if (index >= 3 && index < 39 && !a(inSlot, 0, 2, false)) { return null; }
			} else if (!a(inSlot, 3, 39, false)) { return null; }
			if (inSlot.count == 0) {
				slot.set((ItemStack) null);
			} else {
				slot.f();
			}
			if (inSlot.count == itemResult.count) { return null; }
			slot.a(entityhuman, inSlot);
		}
		if (index > 2 && itemResult != null) {
			if (ItemStack.equals(itemResult, process.getItem(0))) {
				updateOn(0);
			} else if (ItemStack.equals(itemResult, process.getItem(1))) {
				updateOn(1);
			}
		}
		return itemResult;
	}

	@Override
	public CraftInventoryView getBukkitView() {
		if (bukkitEntity != null) { return bukkitEntity; }
		CraftInventory inventory = new CraftInventoryAnvil(process, result);
		bukkitEntity = new CraftInventoryView(pInventory.player.getBukkitEntity(), inventory, this);
		return bukkitEntity;
	}
}