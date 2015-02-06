/**
 * 
 */
package jordan.sicherman.locales;

/**
 * @author Jordan
 * 
 */
public class LocalePirate {

	public static String getMessage(LocaleMessage message) {
		switch (message) {
		case NO_SQL:
			return "MySQL has walked te' plank.";
		case SQL_MODIFICATION:
			return "Yar MySQL cannot be changed when 'tis off.";
		case SQL_CONNECT:
			return "Yar connected.";
		case SQL_NO_CONNECT:
			return "Ya couldn't connect.";
		case SQL_DISCONNECT:
			return "MySQL has set sail.";
		case SQL_CONNECTING:
			return "MySQL, ho!";
		case SQL_NO_DISCONNECT:
			return "MySQL has joined our crew, captain.";
		case SQL_FAIL:
			return "Couldn't run MySQL, capt'n.";
		case MURKY_WATER:
			return "Murky Bottle o' Grog";
		case INFECTED:
			return "&4Arrrr!";
		case RADIO_DISPLAY:
			return "Pearl o' Talkin'";
		case RADIO_LORE:
			return "&eHold to chat with ''yer shipmates";
		case RADIO_CHAT:
			return "%s: &aPearl o' Talkin' $0 - %s";
		case PRIVATE_CHAT_TO:
			return "&8To %s: %s";
		case PRIVATE_CHAT_FROM:
			return "&8Crewmate %s: %s";
		case NO_USER:
			return "&4Unable to speak to shipmate.";
		case INCOMPATIBLE:
			return "MyZ cannot set sail on this server version.";
		case DID_POKE:
			return "&8You signalled $0.";
		case POKED:
			return "&8$0 wants 'yer attention.";
		case LOCAL_CHAT:
			return "%s: %s";
		case REVIVER:
			return "&eYou revived $0.";
		case REVIVED:
			return "&aYou were revived by $0. Davy Jones is near for the next &efive minutes&a.";
		case REVIVER_STARTED:
			return "&eYou have begun revivin' $0.";
		case REVIVED_STARTED:
			return "&eAhoy! $0Has begun to revive you.";
		case REVIVER_CANCELLED:
			return "&4You failed to revive $0.";
		case REVIVED_CANCELLED:
			return "&4$0 was unable to revive you.";
		case BECAME_GHOST:
			return "&e'Yer in &fDavy Jones' Locker&e! If you aren't revived within $0 seconds, you will perish.";
		case BECAME_ZOMBIE:
			return "&e'Yer one of &2Davy Jones' undead&e! Hold out for $0 seconds to escape the Locker.";
		case DEATH_ARROW:
			return "&4$0 took a {cannonball} to the knee.";
		case DEATH_CACTUS:
			return "&4$0 brawled with a {cactus}.";
		case DEATH_DROWNED:
			return "&4$0 {went swimmin' with the fishes}.";
		case DEATH_EXPLOSION:
			return "&4$0 {blew up}.";
		case DEATH_FALL:
			return "&4$0 tried to be a seagull.";
		case DEATH_FIRE:
			return "&4$0 is a {BBQ}.";
		case DEATH_GIANT:
			return "&4$0 took on an {Undead Boss}. And lost.";
		case DEATH_LAVA:
			return "&4$0 went for a swim. In {lava}.";
		case DEATH_MAGIC:
			return "&4$0 got {Harry Potter'd}.";
		case DEATH_PIGMAN:
			return "&4$0 thought {pigmen} were friendly.";
		case DEATH_PLAYER:
			return "&4$0 was SWASHBUCKLED by $1.";
		case DEATH_POISON:
			return "&4$0 mistook {poison} for rum.";
		case DEATH_SUFFOCATION:
			return "&4$0 thought he could hide from Davy Jones {in a wall}.";
		case DEATH_UNKNOWN:
			return "&4$0 walked the plank.";
		case DEATH_ZOMBIE:
			return "&4$0 {joined Davy Jones}.";
		case DEATH_STARVED:
			return "&4$0 got scurvy.";
		case DEATH_VOID:
			return "&4$0 fell overboard.";
		case DEATH_AS_GHOST:
			return "&4$0 was abandoned by Davy Jones.";
		case CANNOT_COMMAND:
			return "You cannot execute this command unless 'yer a deckhand.";
		case MANAGING_SPAWNS:
			return "&e'Yer now managin' spawnpoints.\n&9o&f Right click to cycle spawnpoints.\n&9o&f Left click a spawnpoint to remove it\n&9o&f Left click to add a spawnpoint\n&9o&f Left click while crouchin' to set the home point\n&9o&f Drop wand to end";
		case MANAGING_OVER:
			return "&eYou have finished managin'.";
		case ADDED_SPAWN:
			return "&eYou have added a spawn at the current location.";
		case NO_SPAWNS:
			return "&eThere are no spawns.";
		case REMOVED_SPAWN:
			return "&eYou have removed a spawn at the current location.";
		case VIEWING_SPAWN:
			return "&eNow viewin' spawnpoint #$0.";
		case HOME_SET:
			return "&eSet the home spawnpoint to the current location.";
		case SPAWNED:
			return "&eYou have spawned in the world.";
		case STARTER_SWORD_DISPLAY:
			return "Pirate Scimitar";
		case STARTER_SWORD_LORE:
			return "Get to slashin'.";
		case STARTER_TUNIC_DISPLAY:
			return "Dusty Chestpiece";
		case STARTER_TUNIC_LORE:
			return "It's decomposin'...";
		case BANDAGE_DISPLAY:
			return "Old rag";
		case BLEEDING:
			return "&4Yer blood is spillin' on the deck!";
		case BLEEDING_ENDED:
			return "&eYou stop bleedin'.";
		case INFECTION_ENDED:
			return "&eYer fever drops.";
		case ANTISEPTIC_DISPLAY:
			return "&aAntiseptic";
		case ANTISEPTIC_LORE:
			return "Keeps ya in tip-top shape.~~&6Immunity X";
		case BANDAGE_LORE:
			return "Stops bleedin'.~~&eRight-click to use~&eLeft-click crewmate to use";
		case OINTMENT_DISPLAY:
			return "&4Ointment";
		case OINTMENT_LORE:
			return "Basic medicine.~~&6Heal X~Absorption I";
		case SALT_WATER:
			return "Bottle of Seawater";
		case WARM_WATER:
			return "Warm Bottle of Water";
		case COLD_WATER:
			return "Cold Bottle of Water";
		case SCISSORS_DISPLAY:
			return "Scissors";
		case SCISSORS_LORE:
			return "Used to cut old rags.~~&eLeft-click friend~&eto cut bandage";
		case ALREADY_SPAWNED:
			return "&4'Yer already land 'ho.";
		case BROKEN:
			return "&4Rottin'~$0% Effective";
		case DEVASTATED:
			return "&4Decomposed~$0% Effective";
		case DULL:
			return "&4Dull~$0% Effective";
		case REINFORCED:
			return "&6Reinforced~$0% Effective";
		case SHATTERED:
			return "&4Shattered~$0% Effective";
		case SHARPENED:
			return "&2Pointy~$0% Effective";
		case FORTIFIED:
			return "&2Fortified~$0% Effective";
		case ORNATE:
			return "&6Fancy~$0% Effective";
		case TEMPERED:
			return "&2Tempered~$0% Effective";
		case WEAKENED:
			return "&4Weakened~$0% Effective";
		case CRACKED:
			return "&4Cracked~$0% Effective";
		case BOW_SHARPENED:
			return "&2Sharpened~$0% Effective~&9+$1 Attack Damage";
		case PRECISE:
			return "&2Accurate~$0% Effective~&9+$1 Headshot Damage";
		case HEADSHOT:
			return "&eHeadshot! &4+$0 Damage";
		case SLACK:
			return "&4Slack~$0% Effective";
		case UNABLE_TO_ACCESS_INVENTORY:
			return "&4You can't open that while a crewmate is usin' it.";
		case CHAIN:
			return "Links o' Chain";
		case GRAPPLE_DISPLAY:
			return "Hook o' Grapplin'";
		case GRAPPLE_LORE:
			return "&eRight click to use";
		case WEAK:
			return "&4Weak";
		case LIGHTWEIGHT_GRAPPLE:
			return "&6Lightweight~&9Negates Fall Damage";
		case MANAGING_ENGINEER:
			return "&e'Yer now managin' engineerin' recipes.\n&9o&f Right click to open the interface\n&9o&f Left click to flip through existin' recipes\n&9o&f Place 'yer input items in the left slots\n&9o&f Place 'yer output item in the right slot\n&9o&f Close the interface to create the recipe\n&9o&f Drop wand to end";
		case ENGINEER_RECIPE_NOT_CREATED:
			return "&4The recipe was not created.";
		case ENGINEER_RECIPE_CREATED:
			return "&eA new engineerin' recipe has been created.";
		case ENGINEER_RECIPE_REMOVED:
			return "&eThe engineerin' recipe has been removed.";
		case ENGINEER_RECIPE_MODIFIED:
			return "&eThe engineerin' recipe has been modified.";
		case NO_RECIPES:
			return "&4There are no recipes to view.";
		case VIEWING_RECIPE:
			return "&eNow viewin' recipe #$0.";
		case MEDICINE_DISPLAY:
			return "&eMedicine";
		case MEDICINE_LORE:
			return "Proper medicine.~~&6Heal II~&6Absorption II~&6Immunity X";
		case ACHIEVEMENT_EARNED:
			return "&eAhoy! You got the achievement &6$0 &8($1)&r.";
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
			return "&eRight click to check 'yer temperature.";
		case THERMOMETER_NORMAL:
			return "&aSeafarin' - $0 degrees";
		case THERMOMETER_SHIVERING:
			return "&bShiverin' - $0 degrees";
		case THERMOMETER_SWEATING:
			return "&4Sweatin' - $0 degrees";
		case HEALING_ALREADY:
			return "&4'Yer unable to heal that crewmate.";
		case HEAL_NOT_COMPLETE:
			return "&4You were not healed in time.";
		case HEAL_NOT_COMPLETED:
			return "&4You did not finish healin' in time and must start over.";
		case HEAL_COOLING_DOWN:
			return "&4You must wait $0 seconds.";
		case HEALED:
			return "&eYou were healed by $0.";
		case HEALED_PLAYER:
			return "&eYou healed $0.";
		case HEAL_CHECK:
			return "&eBleedin': $0\n&eHealth: &6$1";
		case NO:
			return "&4neigh";
		case YES:
			return "&aaye";
		case CHEST_SET:
			return "Thar treasure trove now spawns &6$0.";
		case NOTHING:
			return "nothin'";
		case CREATE_CHEST_INSTRUCTIONS:
			return "&ePlease enter a name for 'yer new chest type. Once you enter a name, an inventory will open where you may add a spawn item.";
		case ADD_ITEM_INSTRUCTIONS:
			return "&ePlease enter a minimum and maximum amount for this item, followed by a probability.\n&9ie. 1, 5, 50 &e(Between 1 and 5, 50% spawn chance)";
		case ADD_ITEM_PARSE_ERROR:
			return "&4Unable to parse format. &ePlease enter a minimum and maximum amount for this item, followed by a probability.\n&9ie. 1, 5, 50 &e(Between 1 and 5, 50% spawn chance)";
		case CHEST_MANAGER:
			return "&eYer now managin' chests.\n&9o&f Click a treasure trove to assign it a lootset\n&9o&f Left click air to create a lootset\n&9o&f Right click air to remove a lootset\n&9o&f Drop wand to end";
		case CHEST_MANAGER_COMPLETE:
			return "&eThe lootset has been completed.";
		case CHEST_MANAGER_REMOVED:
			return "&eThe lootset has been removed.";
		case CHEST_MANAGER_REMOVE:
			return "&ePlease enter the name of the lootset 'yer removin'. Options include:";
		case CHAT_PREFIX_MANAGER_0:
			return "&eEnter a chat prefix (ie. [PIRATE]), using ampersands (&) fer chat formatting.";
		case CHAT_PREFIX_MANAGER_1:
			return "&eEnter a rank number (0-100), or a crewmate's name to apply this prefix to.";
		case CHAT_PREFIX_MANAGER_2:
			return "&aThe chat prefix has been created.";
		case MANAGING_SPAWN_KIT:
			return "&eYer now managing spawn kits.\n&9o&f Enter a rank number (0-100) or a crewmate's name to save yer current inventory and gear contents to.";
		case MANAGING_SPAWN_KIT_CREATED:
			return "&aThe spawn kit has been created.";
		}
		return message.getKey();
	}
}
