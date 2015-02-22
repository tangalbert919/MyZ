/**
 * 
 */
package jordan.sicherman.locales;

/**
 * @author Jordan
 * 
 */
public class LocaleEnglish {

	public static String getMessage(LocaleMessage message) {
		switch (message) {
		case REQUIRES_PLAYER:
			return "&4That command requires you to provide the name of a player.";
		case MURKY_WATER:
			return "Murky Water Bottle";
		case INFECTED:
			return "&4You have been infected.";
		case NO_USER:
			return "&4Unable to find specified user.";
		case INCOMPATIBLE:
			return "MyZ is incompatible with this server version.";
		case DEATH_ARROW:
			return "&4$0 took an {arrow} to the knee.";
		case DEATH_CACTUS:
			return "&4$0 thought a {cactus} was friendly.";
		case DEATH_DROWNED:
			return "&4$0 tried to be a {fish}.";
		case DEATH_EXPLOSION:
			return "&4$0 {blew up}.";
		case DEATH_FALL:
			return "&4$0 tried to be a bird.";
		case DEATH_FIRE:
			return "&4$0 is a {BBQ}.";
		case DEATH_GIANT:
			return "&4$0 took on a {Giant}. And lost.";
		case DEATH_LAVA:
			return "&4$0 went for a swim. In {lava}.";
		case DEATH_MAGIC:
			return "&4$0 got {Harry Potter'd}.";
		case DEATH_PIGMAN:
			return "&4$0 thought {pigmen} were friendly.";
		case DEATH_PLAYER:
			return "&4$0 was MURDERED by $1.";
		case DEATH_POISON:
			return "&4$0 didn't know what {poison} meant.";
		case DEATH_SUFFOCATION:
			return "&4$0 thought he could hide from the zombies {in a wall}.";
		case DEATH_UNKNOWN:
			return "&4$0 died.";
		case DEATH_ZOMBIE:
			return "&4$0 {joined the horde}.";
		case DEATH_STARVED:
			return "&4$0 couldn't find the nearest Golden Arches.";
		case DEATH_VOID:
			return "&4$0 fell forever.";
		case DEATH_AS_GHOST:
			return "&4$0 faced judgement.";
		case CANNOT_COMMAND:
			return "You cannot execute this command as the console.";
		case MANAGING_SPAWNS:
			return "&eYou are now managing spawnpoints.\n&9o&f Right click to cycle spawnpoints\n&9o&f Left click a spawnpoint to remove it\n&9o&f Left click to add a spawnpoint\n&9o&f Left click while crouching to set the home point\n&9o&f Drop wand to end";
		case MANAGING_OVER:
			return "&eYou have finished managing.";
		case ADDED_SPAWN:
			return "&eYou have added a spawn at the current location.";
		case NO_SPAWNS:
			return "&eThere are no spawns.";
		case REMOVED_SPAWN:
			return "&eYou have removed a spawn at the current location.";
		case VIEWING_SPAWN:
			return "&eNow viewing spawnpoint #$0.";
		case HOME_SET:
			return "&eSet the home spawnpoint to the current location.";
		case SPAWNED:
			return "&eYou have spawned in the world.";
		case STARTER_SWORD_DISPLAY:
			return "Training Sword";
		case STARTER_SWORD_LORE:
			return "Get to slashing.";
		case STARTER_TUNIC_DISPLAY:
			return "Training Tunic";
		case STARTER_TUNIC_LORE:
			return "It's better than nothing!";
		case BANDAGE_DISPLAY:
			return "Bandage";
		case BLEEDING:
			return "&4You have been cut and are bleeding!";
		case BLEEDING_ENDED:
			return "&eThe bleeding subsides.";
		case INFECTION_ENDED:
			return "&eYour fever drops as you feel the infection leave your body.";
		case SALT_WATER:
			return "Bottle of Saltwater";
		case WARM_WATER:
			return "Warm Bottle of Water";
		case COLD_WATER:
			return "Cold Bottle of Water";
		case ALREADY_SPAWNED:
			return "&4You have already spawned.";
		case BROKEN:
			return "&4Broken~$0% Effective";
		case DEVASTATED:
			return "&4Devastated~$0% Effective";
		case DULL:
			return "&4Dull~$0% Effective";
		case REINFORCED:
			return "&6Reinforced~$0% Effective";
		case SHATTERED:
			return "&4Shattered~$0% Effective";
		case SHARPENED:
			return "&2Sharpened~$0% Effective";
		case FORTIFIED:
			return "&2Fortified~$0% Effective";
		case ORNATE:
			return "&6Ornate~$0% Effective";
		case TEMPERED:
			return "&2Tempered~$0% Effective";
		case WEAKENED:
			return "&4Weakened~$0% Effective";
		case CRACKED:
			return "&4Cracked~$0% Effective";
		case BOW_SHARPENED:
			return "&2Sharpened~$0% Effective~~&9+$1 Attack Damage";
		case PRECISE:
			return "&2Precise~$0% Effective~~&9+$1 Headshot Damage";
		case HEADSHOT:
			return "&eHeadshot! &4+$0 Damage";
		case SLACK:
			return "&4Slack~$0% Effective";
		case UNABLE_TO_ACCESS_INVENTORY:
			return "&4You cannot open that while another player is using it.";
		case CHAIN:
			return "Chain Links";
		case GRAPPLE_DISPLAY:
			return "Grappling Hook";
		case GRAPPLE_LORE:
			return "&eRight click to use";
		case WEAK:
			return "&4Weak";
		case LIGHTWEIGHT_GRAPPLE:
			return "&6Lightweight~&9Negates Fall Damage";
		case MANAGING_ENGINEER:
			return "&eYou are now managing engineering recipes.\n&9o&f Right click to open the interface\n&9o&f Left click to flip through existing recipes\n&9o&f Place your input items in the left slots\n&9o&f Place your output item in the right slot\n&9o&f Close the interface to create the recipe\n&9o&f Drop wand to end";
		case ENGINEER_RECIPE_NOT_CREATED:
			return "&4The recipe was not created.";
		case ENGINEER_RECIPE_CREATED:
			return "&eA new engineering recipe has been created.";
		case ENGINEER_RECIPE_REMOVED:
			return "&eThe engineering recipe has been removed.";
		case ENGINEER_RECIPE_MODIFIED:
			return "&eThe engineering recipe has been modified.";
		case NO_RECIPES:
			return "&4There are no recipes to view.";
		case VIEWING_RECIPE:
			return "&eNow viewing recipe #$0.";
		case NO:
			return "&4no";
		case YES:
			return "&ayes";
		case CHEST_SET:
			return "&eThis chest now spawns &6$0&e.";
		case NOTHING:
			return "nothing";
		case CREATE_CHEST_INSTRUCTIONS:
			return "&ePlease enter a name for your new chest type. Once you enter a name, an inventory will open where you may add a spawn item.";
		case ADD_ITEM_INSTRUCTIONS:
			return "&ePlease enter a minimum and maximum amount for this item, followed by a probability.\n&9ie. 1, 5, 50 &e(Between 1 and 5, 50% spawn chance)";
		case ADD_ITEM_PARSE_ERROR:
			return "&4Unable to parse format. &ePlease enter a minimum and maximum amount for this item, followed by a probability.\n&9ie. 1, 5, 50 &e(Between 1 and 5, 50% spawn chance)";
		case CHEST_MANAGER:
			return "&eYou are now managing chests.\n&9o&f Click a chest to assign it a lootset\n&9o&f Left click air to create a lootset\n&9o&f Right click air to remove a lootset\n&9o&f Drop wand to end";
		case MANAGING_SPAWN_KIT:
			return "&eYou are now managing spawn kits.\n&9o&f Enter a rank number (0-100) or a player name to save your current inventory and gear contents to.";
		case MANAGING_SPAWN_KIT_CREATED:
			return "&aThe spawn kit has been created.";
		case CHEST_MANAGER_COMPLETE:
			return "&eThe lootset has been completed.";
		case CHEST_MANAGER_REMOVED:
			return "&eThe lootset has been removed.";
		case CHEST_MANAGER_REMOVE:
			return "&ePlease enter the name of the lootset you wish to remove. Options include:";
		case ACHIEVEMENT_EARNED:
			return "&eYou earned the achievement &6$0 &8($1)&r.";
		}
		return message.getKey();
	}
}
