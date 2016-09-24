package jordan.sicherman.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.FileMember;
import jordan.sicherman.utilities.configuration.FileUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EngineerManager {

    private final List recipes = new ArrayList();
    private static EngineerManager instance;

    public EngineerManager() {
        EngineerManager.instance = this;
    }

    public static EngineerManager getInstance() {
        return EngineerManager.instance;
    }

    public void reload() {
        this.recipes.clear();
        ConfigurationSection section = (ConfigurationSection) ConfigEntries.ENGINEER_RECIPES.getValue();
        Iterator iterator = section.getKeys(false).iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            try {
                this.recipes.add(new EngineerRecipe(key, section.getItemStack(key + ".input1"), section.getItemStack(key + ".input2"), section.getItemStack(key + ".output")));
            } catch (Exception exception) {
                MyZ.log(ChatColor.RED + "Unable to parse engineer recipe for key \'" + key + "\'. Please fix or remove this entry.");
            }
        }

    }

    public List getRecipes() {
        if (this.recipes.isEmpty()) {
            this.reload();
        }

        return new ArrayList(this.recipes);
    }

    public void createRecipe(Player author, ItemStack in1, ItemStack in2, ItemStack out) {
        if (in1 != null && in2 != null && out != null) {
            if (in1.getType() != Material.AIR && in2.getType() != Material.AIR && out.getType() != Material.AIR) {
                UUID uid = UUID.randomUUID();
                ConfigurationSection section = (ConfigurationSection) ConfigEntries.ENGINEER_RECIPES.getValue();

                section.set(uid.toString() + ".input1", in1);
                section.set(uid.toString() + ".input2", in2);
                section.set(uid.toString() + ".output", out);
                FileUtilities.save(new FileMember[] { ConfigEntries.ENGINEER_RECIPES.getFile()});
                this.reload();
                author.sendMessage(LocaleMessage.ENGINEER_RECIPE_CREATED.toString((CommandSender) author));
            } else {
                author.sendMessage(LocaleMessage.ENGINEER_RECIPE_NOT_CREATED.toString((CommandSender) author));
            }
        }
    }

    public void modifyRecipe(EngineerRecipe recipe, Player author, ItemStack in1, ItemStack in2, ItemStack out) {
        if (recipe != null && in1 != null && in2 != null && out != null) {
            ConfigurationSection section = (ConfigurationSection) ConfigEntries.ENGINEER_RECIPES.getValue();

            if (in1.getType() != Material.AIR && in2.getType() != Material.AIR && out.getType() != Material.AIR) {
                section.set(recipe.getKey() + ".input1", in1);
                section.set(recipe.getKey() + ".input2", in2);
                section.set(recipe.getKey() + ".output", out);
                FileUtilities.save(new FileMember[] { ConfigEntries.ENGINEER_RECIPES.getFile()});
                this.reload();
                author.sendMessage(LocaleMessage.ENGINEER_RECIPE_MODIFIED.toString((CommandSender) author));
            } else {
                section.set(recipe.getKey(), (Object) null);
                FileUtilities.save(new FileMember[] { ConfigEntries.ENGINEER_RECIPES.getFile()});
                this.reload();
                author.sendMessage(LocaleMessage.ENGINEER_RECIPE_REMOVED.toString((CommandSender) author));
            }
        }
    }
}
