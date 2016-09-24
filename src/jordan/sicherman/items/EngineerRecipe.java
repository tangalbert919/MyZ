package jordan.sicherman.items;

import org.bukkit.inventory.ItemStack;

public class EngineerRecipe {

    private final ItemStack[] in = new ItemStack[2];
    private final ItemStack out;
    private final String key;

    public EngineerRecipe(String key, ItemStack in, ItemStack in2, ItemStack out) {
        this.in[0] = in;
        this.in[1] = in2;
        this.out = out;
        this.key = key;
    }

    public ItemStack getInput(int slot) {
        return this.in[slot].clone();
    }

    public ItemStack getOutput() {
        return this.out.clone();
    }

    public String getKey() {
        return this.key;
    }
}
