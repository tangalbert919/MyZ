package jordan.sicherman.nms.v1_8_R1.anvil;

import java.util.Iterator;
import jordan.sicherman.MyZ;
import jordan.sicherman.items.EngineerManager;
import jordan.sicherman.items.EngineerRecipe;
import jordan.sicherman.items.EquipmentState;
import jordan.sicherman.items.ReverseEngineer;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Container;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.ICrafting;
import net.minecraft.server.v1_8_R1.IInventory;
import net.minecraft.server.v1_8_R1.InventoryCraftResult;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.PlayerInventory;
import net.minecraft.server.v1_8_R1.Slot;
import net.minecraft.server.v1_8_R1.World;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftInventoryAnvil;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
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
    public boolean isRealAnvil;
    public boolean validConvert;
    public boolean flag;
    private int remainingRight;
    private int remainingLeft;

    public CustomContainerAnvil(PlayerInventory playerinventory, World world, BlockPosition blockposition, EntityHuman entityhuman, boolean isRealAnvil) {
        this.pInventory = playerinventory;
        this.world = world;
        this.human = entityhuman;
        this.a((Slot) (new CustomSlotAnvil(this, this.process, this.result, 0, 27, 47)));
        this.a((Slot) (new CustomSlotAnvil(this, this.process, this.result, 1, 76, 47)));
        this.a(new Slot(this.result, 2, 134, 47));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.a(new Slot(playerinventory, i, 8 + i * 18, 142));
        }

        this.isRealAnvil = isRealAnvil;
    }

    public void a(IInventory iinventory) {
        super.a(iinventory);
    }

    public boolean updateOn(int slot) {
        if (!this.isRealAnvil) {
            return false;
        } else if (slot != 2) {
            if (this.validConvert) {
                this.result.setItem(0, (ItemStack) null);
                this.validConvert = false;
            }

            this.updateEngineer();
            return false;
        } else if (!this.validConvert && (this.process.getItem(0) != null || this.process.getItem(1) != null)) {
            return true;
        } else {
            if (this.flag && this.process.getItem(1) != null) {
                if (this.remainingRight > 0) {
                    this.process.getItem(1).count = this.remainingRight;
                } else {
                    this.process.setItem(1, (ItemStack) null);
                }

                if (this.remainingLeft > 0) {
                    this.process.getItem(0).count = this.remainingLeft;
                } else {
                    this.process.setItem(0, (ItemStack) null);
                }

                this.flag = false;
            } else {
                this.process.setItem(0, (ItemStack) null);
                this.process.setItem(1, (ItemStack) null);
            }

            this.validConvert = false;
            this.updateEngineer();
            return false;
        }
    }

    private void updateEngineer() {
        MyZ.instance.getServer().getScheduler().runTaskLater(MyZ.instance, new Runnable() {
            public void run() {
                org.bukkit.inventory.ItemStack primary;
                org.bukkit.inventory.ItemStack material;
                int requireLeft;

                if (CustomContainerAnvil.this.result.getItem(0) != null && CustomContainerAnvil.this.process.getItem(0) == null && CustomContainerAnvil.this.process.getItem(1) == null) {
                    primary = CraftItemStack.asBukkitCopy(CustomContainerAnvil.this.result.getItem(0));
                    material = ReverseEngineer.getDecomposition(primary);
                    int recipe1;

                    if (ReverseEngineer.getDecompositionAmount(primary) == 0) {
                        Iterator m2 = EngineerManager.getInstance().getRecipes().iterator();

                        while (m2.hasNext()) {
                            EngineerRecipe upgrade3 = (EngineerRecipe) m2.next();

                            if (upgrade3.getOutput().isSimilar(CraftItemStack.asBukkitCopy(CustomContainerAnvil.this.result.getItem(0)))) {
                                recipe1 = upgrade3.getOutput().getAmount();
                                if (CustomContainerAnvil.this.result.getItem(0).count >= recipe1) {
                                    if (CustomContainerAnvil.this.result.getItem(0).count % recipe1 != 0) {
                                        return;
                                    }

                                    requireLeft = CustomContainerAnvil.this.result.getItem(0).count / recipe1;
                                    org.bukkit.inventory.ItemStack requireRight1 = upgrade3.getInput(0);

                                    requireRight1.setAmount(requireRight1.getAmount() * requireLeft);
                                    org.bukkit.inventory.ItemStack b = upgrade3.getInput(1);

                                    b.setAmount(b.getAmount() * requireLeft);
                                    CustomContainerAnvil.this.process.setItem(0, CraftItemStack.asNMSCopy(requireRight1));
                                    CustomContainerAnvil.this.process.setItem(1, CraftItemStack.asNMSCopy(b));
                                    CustomContainerAnvil.this.validConvert = true;
                                    return;
                                }
                            }
                        }

                        return;
                    }

                    org.bukkit.inventory.ItemStack m1 = material.clone();
                    int upgrade2 = material.getAmount() * primary.getAmount() / 2;

                    recipe1 = material.getAmount() * primary.getAmount() - upgrade2;
                    material.setAmount(upgrade2);
                    m1.setAmount(recipe1);
                    CustomContainerAnvil.this.process.setItem(0, upgrade2 > 0 ? CraftItemStack.asNMSCopy(material) : null);
                    CustomContainerAnvil.this.process.setItem(1, recipe1 > 0 ? CraftItemStack.asNMSCopy(m1) : null);
                    CustomContainerAnvil.this.validConvert = true;
                } else if (CustomContainerAnvil.this.result.getItem(0) == null && CustomContainerAnvil.this.process.getItem(0) != null && CustomContainerAnvil.this.process.getItem(1) != null) {
                    primary = CraftItemStack.asBukkitCopy(CustomContainerAnvil.this.process.getItem(0));
                    material = CraftItemStack.asBukkitCopy(CustomContainerAnvil.this.process.getItem(1));
                    Material m = ReverseEngineer.getRawMaterial(primary);

                    if (!ReverseEngineer.isUpgradable(primary)) {
                        Iterator upgrade1 = EngineerManager.getInstance().getRecipes().iterator();

                        while (upgrade1.hasNext()) {
                            EngineerRecipe recipe = (EngineerRecipe) upgrade1.next();

                            if (recipe.getInput(0).isSimilar(CraftItemStack.asBukkitCopy(CustomContainerAnvil.this.process.getItem(0))) && recipe.getInput(0).isSimilar(CraftItemStack.asBukkitCopy(CustomContainerAnvil.this.process.getItem(0)))) {
                                requireLeft = recipe.getInput(0).getAmount();
                                int requireRight = recipe.getInput(1).getAmount();

                                if (CustomContainerAnvil.this.process.getItem(0).count >= requireLeft && CustomContainerAnvil.this.process.getItem(1).count >= requireRight) {
                                    CustomContainerAnvil.this.result.setItem(0, CraftItemStack.asNMSCopy(recipe.getOutput()));
                                    CustomContainerAnvil.this.validConvert = true;
                                    CustomContainerAnvil.this.flag = true;
                                    CustomContainerAnvil.this.remainingRight = CustomContainerAnvil.this.process.getItem(1).count - requireRight;
                                    CustomContainerAnvil.this.remainingLeft = CustomContainerAnvil.this.process.getItem(0).count - requireLeft;
                                    return;
                                }
                            }
                        }

                        return;
                    }

                    if (m == null) {
                        return;
                    }

                    if (m == material.getType()) {
                        EquipmentState upgrade = EquipmentState.getNext(primary);

                        if (upgrade == null) {
                            return;
                        }

                        if (CustomContainerAnvil.this.process.getItem(1).count >= ReverseEngineer.getUpgradeAmount()) {
                            CustomContainerAnvil.this.result.setItem(0, CraftItemStack.asNMSCopy(upgrade.applyTo(primary.clone())));
                            CustomContainerAnvil.this.validConvert = true;
                            CustomContainerAnvil.this.flag = true;
                            CustomContainerAnvil.this.remainingLeft = 0;
                            CustomContainerAnvil.this.remainingRight = CustomContainerAnvil.this.process.getItem(1).count - ReverseEngineer.getUpgradeAmount();
                        }
                    }
                }

            }
        }, 0L);
    }

    public void addSlotListener(ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.expCost);
    }

    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
        if (!this.world.isStatic) {
            ItemStack itemstack1;
            ItemStack itemstack;

            if (!this.isRealAnvil) {
                itemstack = this.process.getItem(0);
                itemstack1 = this.process.getItem(1);
                ItemStack out = this.result.getItem(0);

                if (this.activeRecipe != null) {
                    EngineerManager.getInstance().modifyRecipe(this.activeRecipe, (Player) entityhuman.getBukkitEntity(), CraftItemStack.asBukkitCopy(itemstack), CraftItemStack.asBukkitCopy(itemstack1), CraftItemStack.asBukkitCopy(out));
                } else {
                    EngineerManager.getInstance().createRecipe((Player) entityhuman.getBukkitEntity(), CraftItemStack.asBukkitCopy(itemstack), CraftItemStack.asBukkitCopy(itemstack1), CraftItemStack.asBukkitCopy(out));
                }

                return;
            }

            if (this.validConvert && !this.flag) {
                itemstack = this.result.splitWithoutUpdate(0);
                if (itemstack != null) {
                    entityhuman.drop(itemstack, false);
                }
            } else {
                for (int itemstack2 = 0; itemstack2 < this.process.getSize(); ++itemstack2) {
                    itemstack1 = this.process.splitWithoutUpdate(itemstack2);
                    if (itemstack1 != null) {
                        entityhuman.drop(itemstack1, false);
                    }
                }
            }
        }

    }

    public boolean a(EntityHuman entityhuman) {
        ((EntityPlayer) this.human).a(this.human.activeContainer, this.human.activeContainer.a());
        return true;
    }

    public ItemStack b(EntityHuman entityhuman, int index) {
        ItemStack itemResult = null;
        Slot slot = (Slot) this.c.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack inSlot = slot.getItem();

            itemResult = inSlot.cloneItemStack();
            if (index == 2) {
                if (!this.a(inSlot, 3, 39, true)) {
                    return null;
                }

                slot.a(inSlot, itemResult);
            } else if (index != 0 && index != 1) {
                if (index >= 3 && index < 39 && !this.a(inSlot, 0, 2, false)) {
                    return null;
                }
            } else if (!this.a(inSlot, 3, 39, false)) {
                return null;
            }

            if (inSlot.count == 0) {
                slot.set((ItemStack) null);
            } else {
                slot.f();
            }

            if (inSlot.count == itemResult.count) {
                return null;
            }

            slot.a(entityhuman, inSlot);
        }

        if (index > 2 && itemResult != null) {
            if (ItemStack.equals(itemResult, this.process.getItem(0))) {
                this.updateOn(0);
            } else if (ItemStack.equals(itemResult, this.process.getItem(1))) {
                this.updateOn(1);
            }
        }

        return itemResult;
    }

    public CraftInventoryView getBukkitView() {
        if (this.bukkitEntity != null) {
            return this.bukkitEntity;
        } else {
            CraftInventoryAnvil inventory = new CraftInventoryAnvil(this.process, this.result);

            this.bukkitEntity = new CraftInventoryView(this.pInventory.player.getBukkitEntity(), inventory, this);
            return this.bukkitEntity;
        }
    }
}
