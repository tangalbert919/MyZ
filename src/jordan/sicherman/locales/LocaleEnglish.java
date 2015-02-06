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
		case NO_SQL:
			return "Not using MySQL for this session.";
		case SQL_MODIFICATION:
			return "Attempting to modify MySQL while disconnected.";
		case SQL_CONNECT:
			return "Connection successful.";
		case SQL_NO_CONNECT:
			return "Unable to connect";
		case SQL_DISCONNECT:
			return "Disconnected from MySQL.";
		case SQL_CONNECTING:
			return "Connecting to MySQL...";
		case SQL_NO_DISCONNECT:
			return "Unable to close MySQL connection";
		case SQL_FAIL:
			return "Unable to execute MySQL command";
		case MURKY_WATER:
			return "Murky Water Bottle";
		case INFECTED:
			return "&4You have been infected.";
		case RADIO_DISPLAY:
			return "Radio";
		case RADIO_LORE:
			return "&eHold and chat to use";
		case RADIO_CHAT:
			return "%s: &a1.$0 Hz - %s";
		case PRIVATE_CHAT_TO:
			return "&8To %s: %s";
		case PRIVATE_CHAT_FROM:
			return "&8From %s: %s";
		case NO_USER:
			return "&4Unable to find specified user.";
		case INCOMPATIBLE:
			return "MyZ is incompatible with this server version.";
		case DID_POKE:
			return "&8You poked $0.";
		case POKED:
			return "&8$0 poked you.";
		case LOCAL_CHAT:
			return "%s: %s";
		case REVIVER:
			return "&eYou revived $0.";
		case REVIVED:
			return "&aYou were revived by $0. You are vulnerable for the next &efive minutes&a.";
		case REVIVER_STARTED:
			return "&eYou have begun reviving $0. Hold tight.";
		case REVIVED_STARTED:
			return "&e$0Has begun to revive you. Hold tight.";
		case REVIVER_CANCELLED:
			return "&4You slipped and failed to revive $0.";
		case REVIVED_CANCELLED:
			return "&4$0 was unable to revive you.";
		case BECAME_GHOST:
			return "&eYou are a &fghost&e! If you aren't revived within $0 seconds, you will die.";
		case BECAME_ZOMBIE:
			return "&eYou are a &2zombie&e! Hold out for $0 seconds to face judgement.";
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
			return "&eYou are now managing spawnpoints.\n&9o&f Right click to cycle spawnpoints.\n&9o&f Left click a spawnpoint to remove it\n&9o&f Left click to add a spawnpoint\n&9o&f Left click while crouching to set the home point\n&9o&f Drop wand to end";
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
		case ANTISEPTIC_DISPLAY:
			return "&aAntiseptic";
		case ANTISEPTIC_LORE:
			return "Treats infection.~~&6Immunity X";
		case BANDAGE_LORE:
			return "Stops bleeding.~~&eRight-click to use~&eLeft-click friend to use";
		case OINTMENT_DISPLAY:
			return "&4Ointment";
		case OINTMENT_LORE:
			return "Basic medicine.~~&6Heal X~Absorption I";
		case SALT_WATER:
			return "Bottle of Saltwater";
		case WARM_WATER:
			return "Warm Bottle of Water";
		case COLD_WATER:
			return "Cold Bottle of Water";
		case SCISSORS_DISPLAY:
			return "Scissors";
		case SCISSORS_LORE:
			return "Used to cut bandages.~~&eLeft-click friend~&eto cut bandage";
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
			return "&2Sharpened~$0% Effective~&9+$1 Attack Damage";
		case PRECISE:
			return "&2Precise~$0% Effective~&9+$1 Headshot Damage";
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
		case MEDICINE_DISPLAY:
			return "&eMedicine";
		case MEDICINE_LORE:
			return "Advanced medicine.~~&6Heal II~&6Absorption II~&6Immunity X";
		case ACHIEVEMENT_EARNED:
			return "&eYou earned the achievement &6$0 &8($1)&r.";
		case THERMOMETER_DISPLAY:
			return "Thermometer";
		case THERMOMETER_HEAT1:
			return "&4Heatstroke I - $0 degrees";
		case THERMOMETER_HEAT2:
			return "&4Heatstroke II - $0 degrees";
		case THERMOMETER_HEAT3:
			return "&4Heatstroke III - $0 degrees";
		case THERMOMETER_HYPO1:
			return "&bHypothermia I - $0 degrees";
		case THERMOMETER_HYPO2:
			return "&bHypothermia II - $0 degrees";
		case THERMOMETER_HYPO3:
			return "&bHypothermia III - $0 degrees";
		case THERMOMETER_LORE:
			return "&eRight click to get temperature reading.";
		case THERMOMETER_NORMAL:
			return "&aRoom Temperature - $0 degrees";
		case THERMOMETER_SHIVERING:
			return "&bShivering - $0 degrees";
		case THERMOMETER_SWEATING:
			return "&4Sweating - $0 degrees";
		case HEALING_ALREADY:
			return "&4You are unable to heal that player at this time.";
		case HEAL_NOT_COMPLETE:
			return "&4You were not healed in time.";
		case HEAL_NOT_COMPLETED:
			return "&4You did not finish healing in time and must start over.";
		case HEAL_COOLING_DOWN:
			return "&4You must wait $0 seconds.";
		case HEALED:
			return "&eYou were healed by $0.";
		case HEALED_PLAYER:
			return "&eYou healed $0.";
		case HEAL_CHECK:
			return "&eBleeding: $0\n&eHealth: &6$1";
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
		case CHAT_PREFIX_MANAGER_0:
			return "&eEnter a chat prefix (ie. [VIP]), using ampersands (&) for chat formatting.";
		case CHAT_PREFIX_MANAGER_1:
			return "&eEnter a rank number (0-100), or a player name to apply this prefix to.";
		case CHAT_PREFIX_MANAGER_2:
			return "&aThe chat prefix has been created.";
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
		}
		return message.getKey();
	}
}
