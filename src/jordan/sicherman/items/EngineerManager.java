/**
 * 
 */
package jordan.sicherman.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jordan.sicherman.MyZ;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.FileUtilities;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jordan
 * 
 */
public class EngineerManager {

	private final List<EngineerRecipe> recipes = new ArrayList<EngineerRecipe>();

	private static EngineerManager instance;

	public EngineerManager() {
		instance = this;
	}

	public static EngineerManager getInstance() {
		return instance;
	}

	public void reload() {
		recipes.clear();

		ConfigurationSection section = ConfigEntries.ENGINEER_RECIPES.<ConfigurationSection> getValue();
		for (String key : section.getKeys(false)) {
			try {
				recipes.add(new EngineerRecipe(key, section.getItemStack(key + ".input1"), section.getItemStack(key + ".input2"), section
						.getItemStack(key + ".output")));
			} catch (Exception exc) {
				MyZ.log(ChatColor.RED + "Unable to parse engineer recipe for key '" + key + "'. Please fix or remove this entry.");
			}
		}
	}

	public List<EngineerRecipe> getRecipes() {
		if (recipes.isEmpty()) {
			reload();
		}
		return new ArrayList<EngineerRecipe>(recipes);
	}

	public void createRecipe(Player author, ItemStack in1, ItemStack in2, ItemStack out) {
		if (in1 == null || in2 == null || out == null) { return; }

		if (in1.getType() == Material.AIR || in2.getType() == Material.AIR || out.getType() == Material.AIR) {
			author.sendMessage(LocaleMessage.ENGINEER_RECIPE_NOT_CREATED.toString(author));
			return;
		}

		UUID uid = UUID.randomUUID();
		ConfigurationSection section = ConfigEntries.ENGINEER_RECIPES.<ConfigurationSection> getValue();
		section.set(uid.toString() + ".input1", in1);
		section.set(uid.toString() + ".input2", in2);
		section.set(uid.toString() + ".output", out);
		FileUtilities.save(ConfigEntries.ENGINEER_RECIPES.getFile());

		reload();

		author.sendMessage(LocaleMessage.ENGINEER_RECIPE_CREATED.toString(author));
	}

	public void modifyRecipe(EngineerRecipe recipe, Player author, ItemStack in1, ItemStack in2, ItemStack out) {
		if (recipe == null || in1 == null || in2 == null || out == null) { return; }

		ConfigurationSection section = ConfigEntries.ENGINEER_RECIPES.<ConfigurationSection> getValue();
		if (in1.getType() == Material.AIR || in2.getType() == Material.AIR || out.getType() == Material.AIR) {
			section.set(recipe.getKey(), null);
			FileUtilities.save(ConfigEntries.ENGINEER_RECIPES.getFile());

			reload();

			author.sendMessage(LocaleMessage.ENGINEER_RECIPE_REMOVED.toString(author));
			return;
		} else {
			section.set(recipe.getKey() + ".input1", in1);
			section.set(recipe.getKey() + ".input2", in2);
			section.set(recipe.getKey() + ".output", out);
			FileUtilities.save(ConfigEntries.ENGINEER_RECIPES.getFile());

			reload();

			author.sendMessage(LocaleMessage.ENGINEER_RECIPE_MODIFIED.toString(author));
		}
	}
}
