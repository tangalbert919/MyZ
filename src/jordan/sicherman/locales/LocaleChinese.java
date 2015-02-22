/**
 *
 */
package jordan.sicherman.locales;

/**
 * @author Jordan
 * 
 */
public class LocaleChinese {

	public static String getMessage(LocaleMessage message) {
		switch (message) {
		case NO_USER:
			return "&4æ‰¾ä¸åˆ°æŒ‡å®šç”¨æˆ·.";
		case CANNOT_COMMAND:
			return "å½“å‰æŒ‡ä»¤æ— æ³•åœ¨æŽ§åˆ¶å°æ‰§è¡Œ.";
		case MANAGING_OVER:
			return "&eä½ å®Œæˆäº†ç®¡ç†æ“ä½œ.";
		case MANAGING_ENGINEER:
			return "&eä½ çŽ°åœ¨æ­£åœ¨ç®¡ç†å·¥ç¨‹é…æ–¹.\n&9o&f å³é”®æ‰“å¼€ç•Œé¢\n&9o&f å·¦é”®æµè§ˆçŽ°æœ‰çš„é£Ÿè°±\n&9o&f å°†æ‰€éœ€ææ–™æ”¾å…¥åå·¦è¾¹æ§½ä½\n&9o&f å°†èŽ·å¾—ç‰©å“æ”¾å…¥å³è¾¹æ§½ä½\n&9o&f å…³é—­èœå•æ¥åˆ›å»ºé…æ–¹\n&9o&f ä¸¢å¼ƒæ”»å‡»æ¥ç»“æŸè®¾ç½®";
		case NO_RECIPES:
			return "&4æ²¡æœ‰é…æ–¹å¯ä»¥æŸ¥çœ‹.";
		case ENGINEER_RECIPE_CREATED:
			return "&eä¸€ä¸ªæ–°çš„å·¥ç¨‹é…æ–¹è¢«åˆ›å»ºå‡ºæ¥äº†.";
		case ENGINEER_RECIPE_MODIFIED:
			return "&eå·¥ç¨‹é…æ–¹å·²ä¿®æ”¹.";
		case ENGINEER_RECIPE_NOT_CREATED:
			return "&4é…æ–¹æœªåˆ›å»º.";
		case ENGINEER_RECIPE_REMOVED:
			return "&eå·¥ç¨‹é…æ–¹è¢«ç§»é™¤.";
		case VIEWING_RECIPE:
			return "&eæ­£åœ¨æŸ¥çœ‹ #$0 é…æ–¹.";
		case ADDED_SPAWN:
			return "&eä½ åœ¨å½“å‰ä½ç½®æ·»åŠ äº†å‡ºç”Ÿç‚¹.";
		case MANAGING_SPAWNS:
			return "&eä½ çŽ°åœ¨æ­£åœ¨ç®¡ç†å‡ºç”Ÿç‚¹.\n&9o&f å³é”®é€‰æ‹©å‡ºç”Ÿç‚¹è®¾ç½®æ¨¡å¼.\n&9o&f å·¦é”®ç§»é™¤ä¸€ä¸ªå‡ºç”Ÿç‚¹\n&9o&f å·¦é”®æ·»åŠ ä¸€ä¸ªå‡ºç”Ÿç‚¹\n&9o&f è¹²ä¼çŠ¶æ€ä¸‹å·¦é”®è®¾ç½®å®¶\n&9o&f ä¸¢å¼ƒå·¥å…·æ¥ç»“æŸè®¾ç½®";
		case HOME_SET:
			return "&eåœ¨å½“å‰ä½ç½®è®¾ç½®äº†å®¶çš„åæ ‡.";
		case NO_SPAWNS:
			return "&eæ²¡æœ‰å‡ºç”Ÿç‚¹.";
		case REMOVED_SPAWN:
			return "&eä½ ç§»é™¤äº†å½“å‰ä½ç½®çš„å‡ºç”Ÿç‚¹.";
		case VIEWING_SPAWN:
			return "&eçŽ°åœ¨å‰å¾€çš„æ˜¯#$0 å·å‡ºç”Ÿç‚¹.";
		case ALREADY_SPAWNED:
			return "&4ä½ æ—©å°±åŠ å…¥äº†å½“å‰ä¸–ç•Œ.";
		case SPAWNED:
			return "&eä½ åŠ å…¥äº†å½“å‰ä¸–ç•Œ.";
		case DEATH_AS_GHOST:
			return "&4$0 é¢ä¸´å®¡åˆ¤.";
		case DEATH_SUFFOCATION:
			return "&4$0 è®¤ä¸ºä»–èº²åœ¨ {in a wall} é‡Œå°±å¯ä»¥èº²é¿åƒµå°¸.";
		case DEATH_CACTUS:
			return "&4$0 è®¤ä¸º {cactus} æ˜¯å‹å¥½çš„.";
		case DEATH_DROWNED:
			return "&4$0 æƒ³è¦æˆä¸ºä¸€æ¡ {fish}.";
		case DEATH_EXPLOSION:
			return "&4$0 {blew up}.";
		case DEATH_FALL:
			return "&4$0 æƒ³è¦æˆä¸ºä¸€åªå¿«ä¹çš„å°é¸Ÿ.";
		case DEATH_FIRE:
			return "&4$0 æˆä¸ºäº† {BBQ}.";
		case DEATH_GIANT:
			return "&4$0 æƒ³åŽ»å’Œ{Giant} æ¯”åŠ›æ°”. å¤±è´¥äº†.";
		case DEATH_LAVA:
			return "&4$0 æƒ³è¦æ´—ä¸ªçƒ­æ°´æ¾¡.äºŽæ˜¯è·³å…¥äº† {lava}.";
		case DEATH_MAGIC:
			return "&4$0 æ˜Žç™½äº† {Harry Potter'd}.";
		case DEATH_PIGMAN:
			return "&4$0 è®¤ä¸º {pigmen} æ˜¯å‹å¥½çš„.";
		case DEATH_PLAYER:
			return "&4$0 è¢« $1 è°‹æ€äº†.";
		case DEATH_POISON:
			return "&4$0 æƒ³è¦è¯•ä¸€è¯• {poison} çš„ç¾Žå‘³.";
		case DEATH_ARROW:
			return "&4$0 è†ç›–ä¸­äº†ä¸€ {arrow} .";
		case DEATH_STARVED:
			return "&4$0 æ— æ³•æ‰¾åˆ°æœ€è¿‘çš„é‡‘è‰²æ‹±é—¨.";
		case DEATH_UNKNOWN:
			return "&4$0 æ­»äº¡äº†.";
		case DEATH_VOID:
			return "&4$0 æŽ‰å‡ºäº†è¿™ä¸ªä¸–ç•Œ.";
		case DEATH_ZOMBIE:
			return "&4$0 {joined the horde}.";
		case STARTER_TUNIC_DISPLAY:
			return "åŸ¹è®­æŸè…°å¤–è¡£";
		case STARTER_TUNIC_LORE:
			return "æ€»æ¯”æ²¡æœ‰è¦å¥½!";
		case STARTER_SWORD_DISPLAY:
			return "ä¿®ç»ƒç”¨çŸ­å‰‘";
		case STARTER_SWORD_LORE:
			return "ç !åˆº!å•Š,æ–­äº†!";
		case BOW_SHARPENED:
			return "&2å°–é”çš„~$0% æœ‰æ•ˆçš„~~&9+$1 æ”»å‡»ä¼¤å®³";
		case BROKEN:
			return "&4ç ´ç¢Žçš„~$0% æœ‰æ•ˆçš„";
		case CRACKED:
			return "&4ç ´ç¢Žçš„~$0% æœ‰æ•ˆçš„";
		case DEVASTATED:
			return "&4æ¯åçš„~$0% æœ‰æ•ˆçš„";
		case DULL:
			return "&4è¿Ÿé’çš„~$0% æœ‰æ•ˆçš„";
		case FORTIFIED:
			return "&2å¼ºåŒ–çš„~$0% æœ‰æ•ˆçš„";
		case LIGHTWEIGHT_GRAPPLE:
			return "&6è½»ç›ˆçš„~&9å…ç–«æŽ‰è½ä¼¤å®³";
		case ORNATE:
			return "&6åŽä¸½çš„~$0% æœ‰æ•ˆçš„";
		case PRECISE:
			return "&2ç²¾ç¡®çš„~$0% æœ‰æ•ˆçš„~~&9+$1 çˆ†å¤´ä¼¤å®³";
		case REINFORCED:
			return "&6å¼ºåŒ–çš„~$0% æœ‰æ•ˆçš„";
		case SHARPENED:
			return "&2å°–é”çš„~$0% æœ‰æ•ˆçš„";
		case SHATTERED:
			return "&ç²‰ç¢Žçš„~$0% æœ‰æ•ˆçš„";
		case SLACK:
			return "&4æ¾å¼›çš„~$0% æœ‰æ•ˆçš„";
		case TEMPERED:
			return "&2æ¸©å’Œçš„~$0% æœ‰æ•ˆçš„";
		case WEAK:
			return "&4è™šå¼±";
		case WEAKENED:
			return "&4è™šå¼±çš„~$0% æœ‰æ•ˆçš„";
		case INCOMPATIBLE:
			return "MyZä¸Žå½“å‰æœåŠ¡ç«¯ç‰ˆæœ¬ä¸å…¼å®¹.";
		case BLEEDING:
			return "&4ä½ è¢«å‰ªåˆ°äº†,æ­£åœ¨æµè¡€!";
		case ACHIEVEMENT_EARNED:
			return "&eä½ èŽ·å¾—æˆå°± &6$0 &8($1)&r.";
		case HEADSHOT:
			return "&eçˆ†å¤´ä¸€å‡»! &4ä¼¤å®³å¢žåŠ +$0 ";
		case INFECTED:
			return "&4ä½ å·²ç»è¢«æ„ŸæŸ“äº†.";
		case UNABLE_TO_ACCESS_INVENTORY:
			return "&4ä½ æ— æ³•æ‰“å¼€åˆ«äººæ­£åœ¨ä½¿ç”¨çš„ç®±å­.";
		case BLEEDING_ENDED:
			return "&eä¼¤å£æ­¢è¡€äº†.";
		case INFECTION_ENDED:
			return "&eä½ æ­£åœ¨å‘çƒ§,æ„Ÿè§‰èº«ä½“å°±åƒä¸æ˜¯è‡ªå·±çš„ä¸€æ ·.";
		case CHAIN:
			return "é“¾æ¡";
		case COLD_WATER:
			return "ç“¶è£…å†°æ°´";
		case GRAPPLE_DISPLAY:
			return "çˆªé’©";
		case GRAPPLE_LORE:
			return "&eå³é”®ä½¿ç”¨";
		case BANDAGE_DISPLAY:
			return "ç»·å¸¦";
		case MURKY_WATER:
			return "æ±¡æ°´ç“¶";
		case SALT_WATER:
			return "ç“¶è£…ç›æ°´";
		case WARM_WATER:
			return "ç“¶è£…æ¸©æ°´";
		case NO:
			return "&4å¦";
		case YES:
			return "&aæ˜¯";
		default:
			break;
		}
		return message.getKey();
	}
}
