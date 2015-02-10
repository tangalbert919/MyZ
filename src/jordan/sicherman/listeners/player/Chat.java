/**
 * 
 */
package jordan.sicherman.listeners.player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jordan.sicherman.MyZ;
import jordan.sicherman.items.ItemTag;
import jordan.sicherman.items.ItemUtilities;
import jordan.sicherman.locales.LocaleMessage;
import jordan.sicherman.utilities.ChestType;
import jordan.sicherman.utilities.DataWrapper;
import jordan.sicherman.utilities.ManagerManager;
import jordan.sicherman.utilities.ManagerManager.ManagerType;
import jordan.sicherman.utilities.MyZRank;
import jordan.sicherman.utilities.StartingKitManager.EquipmentPiece;
import jordan.sicherman.utilities.Utilities;
import jordan.sicherman.utilities.VisibilityManager;
import jordan.sicherman.utilities.VisibilityManager.VisibilityCause;
import jordan.sicherman.utilities.configuration.ConfigEntries;
import jordan.sicherman.utilities.configuration.UserEntries;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * @author Jordan
 * 
 */
public class Chat implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onChat(AsyncPlayerChatEvent e) {
		if (!Utilities.inWorld(e.getPlayer())) { return; }

		Player from = e.getPlayer();

		if (ManagerManager.isManager(from, ManagerType.CHESTS)) {
			String meta = from.getMetadata(ManagerType.CHESTS.getID()).get(0).asString();
			if (meta != null && meta.isEmpty()) {
				e.setCancelled(true);
				ChestType.create(e.getMessage());
				from.setMetadata(ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, e.getMessage()));
				from.openInventory(Bukkit.createInventory(null, 9, "Create Lootset"));
				return;
			} else if (meta != null && !meta.isEmpty()) {
				e.setCancelled(true);

				if ("a769bH3".equals(meta)) {
					ChestType type = ChestType.fromName(e.getMessage());
					if (type != null) {
						type.remove();
						from.sendMessage(LocaleMessage.CHEST_MANAGER_REMOVED.toString(from));
						return;
					}
					from.setMetadata(ManagerType.CHESTS.getID(), new FixedMetadataValue(MyZ.instance, null));
					return;
				}

				String[] format = e.getMessage().split(", ");
				try {
					int min = Integer.parseInt(format[0]);
					int max = Integer.parseInt(format[1]);
					int prob = Integer.parseInt(format[2]);
					ChestType.fromName(meta).setRecentProperties(min, max, prob);
					from.openInventory(Bukkit.createInventory(null, 9, "Create Lootset"));
				} catch (Exception exc) {
					from.sendMessage(LocaleMessage.ADD_ITEM_PARSE_ERROR.toString(from));
				}
				return;
			}
		} else if (ManagerManager.isManager(from, ManagerType.CHAT_PREFIX)) {
			e.setCancelled(true);

			String meta = from.getMetadata(ManagerType.CHAT_PREFIX.getID()).get(0).asString();
			if (meta == null || meta.isEmpty()) {
				from.setMetadata(ManagerType.CHAT_PREFIX.getID(), new FixedMetadataValue(MyZ.instance, e.getMessage()));
				from.sendMessage(LocaleMessage.CHAT_PREFIX_MANAGER_1.toString(from));
			} else {
				try {
					MyZRank.forInt(Integer.parseInt(e.getMessage())).setPrefix(meta);
				} catch (Exception exc) {
					OfflinePlayer player = Bukkit.getOfflinePlayer(e.getMessage());
					if (player == null) {
						from.sendMessage(LocaleMessage.NO_USER.toString(from));
						return;
					}
					DataWrapper.set(player, UserEntries.PREFIX, meta);
				}
				from.sendMessage(LocaleMessage.CHAT_PREFIX_MANAGER_2.toString(from));
				ManagerManager.setManager(from, false, ManagerType.CHAT_PREFIX);
			}

			return;
		} else if (ManagerManager.isManager(from, ManagerType.SPAWN_KIT)) {
			e.setCancelled(true);

			try {
				MyZRank rank = MyZRank.forInt(Integer.parseInt(e.getMessage()), true);
				rank.setEquipment(EquipmentPiece.BOOTS, from.getEquipment().getBoots() != null ? from.getEquipment().getBoots().clone()
						: null);
				rank.setEquipment(EquipmentPiece.LEGGINGS, from.getEquipment().getLeggings() != null ? from.getEquipment().getLeggings()
						.clone() : null);
				rank.setEquipment(EquipmentPiece.CHESTPLATE, from.getEquipment().getChestplate() != null ? from.getEquipment()
						.getChestplate().clone() : null);
				rank.setEquipment(EquipmentPiece.HELMET, from.getEquipment().getHelmet() != null ? from.getEquipment().getHelmet().clone()
						: null);
				rank.setInventory(Arrays.asList(from.getInventory().getContents() != null ? from.getInventory().getContents().clone()
						: null));
			} catch (Exception exc) {
				OfflinePlayer player = Bukkit.getOfflinePlayer(e.getMessage());
				if (player == null) {
					from.sendMessage(LocaleMessage.NO_USER.toString(from));
					return;
				}
				DataWrapper.set(player, UserEntries.KIT_BOOTS, from.getEquipment().getBoots() != null ? from.getEquipment().getBoots()
						.clone() : null);
				DataWrapper.set(player, UserEntries.KIT_LEGGINGS, from.getEquipment().getLeggings() != null ? from.getEquipment()
						.getLeggings().clone() : null);
				DataWrapper.set(player, UserEntries.KIT_CHESTPLATE, from.getEquipment().getChestplate() != null ? from.getEquipment()
						.getChestplate().clone() : null);
				DataWrapper.set(player, UserEntries.KIT_HELMET, from.getEquipment().getHelmet() != null ? from.getEquipment().getHelmet()
						.clone() : null);
				DataWrapper.set(player, UserEntries.KIT_INVENTORY,
						Arrays.asList(from.getInventory().getContents() != null ? from.getInventory().getContents().clone() : null));
			}
			from.sendMessage(LocaleMessage.MANAGING_SPAWN_KIT_CREATED.toString(from));
			ManagerManager.setManager(from, false, ManagerType.SPAWN_KIT);
		}

		VisibilityManager.getInstance().overloadXPBarVisibility(from, VisibilityCause.CHAT);

		if (!MyZ.isPremium()) { return; }

		if (MyZ.ghostFactory.isGhost(from) || MyZ.zombieFactory.isZombie(from)) {
			e.getRecipients().clear();
			for (Player player : Utilities.getPlayersInRange(from.getLocation(), ConfigEntries.CHAT_RADIUS.<Integer> getValue())) {
				e.getRecipients().add(player);
			}
			e.setFormat(LocaleMessage.LOCAL_CHAT.toString().replaceFirst("%s", Utilities.getPrefixFor(from) + "%s"));
			e.setMessage(ChatColor.MAGIC + e.getMessage());
			return;
		}

		if (ConfigEntries.PRIVATE_CHAT.<Boolean> getValue() && e.getMessage().startsWith("@")) {
			Player to = Bukkit.getPlayer(e.getMessage().split(" ")[0].substring(1));
			boolean poke = !e.getMessage().contains(" ");
			e.setCancelled(true);

			if (to != null && to.isOnline()) {
				if (!poke) {
					String message = format(e.getMessage().substring(e.getMessage().indexOf(" ") + 1));
					from.sendMessage(LocaleMessage.PRIVATE_CHAT_TO.toString(from)
							.replaceFirst("%s", Utilities.getPrefixFor(to) + to.getName()).replaceFirst("%s", message));
					to.sendMessage(LocaleMessage.PRIVATE_CHAT_FROM.toString(to)
							.replaceFirst("%s", Utilities.getPrefixFor(from) + from.getName()).replaceFirst("%s", message));
				} else {
					from.sendMessage(LocaleMessage.DID_POKE.filter(Utilities.getPrefixFor(to) + to.getName()).toString(from));
					to.sendMessage(LocaleMessage.POKED.filter(Utilities.getPrefixFor(from) + from.getName()).toString(to));
				}
			} else {
				from.sendMessage(LocaleMessage.NO_USER.toString(from));
			}
		} else if (ConfigEntries.RADIO_CHAT.<Boolean> getValue()
				&& ItemUtilities.getInstance().hasTag(e.getPlayer().getItemInHand(), ItemTag.RADIO)) {
			int frequency = e.getPlayer().getInventory().getHeldItemSlot();
			Set<Player> recipients = new HashSet<Player>(e.getRecipients());
			e.getRecipients().clear();
			e.getRecipients().add(from);
			for (Player recipient : recipients) {
				if (Utilities.inWorld(recipient) && recipient.getInventory().getItem(frequency) != null
						&& ItemUtilities.getInstance().hasTag(recipient.getInventory().getItem(frequency), ItemTag.RADIO)) {
					e.getRecipients().add(recipient);
				}
			}
			e.setMessage(format(e.getMessage()));
			e.setFormat(LocaleMessage.RADIO_CHAT.filter(frequency).toString().replaceFirst("%s", Utilities.getPrefixFor(from) + "%s"));
		} else if (ConfigEntries.LOCAL_CHAT.<Boolean> getValue()) {
			e.getRecipients().clear();
			for (Player player : Utilities.getPlayersInRange(from.getLocation(), ConfigEntries.CHAT_RADIUS.<Integer> getValue())) {
				e.getRecipients().add(player);
			}
			e.setMessage(format(e.getMessage()));
			e.setFormat(LocaleMessage.LOCAL_CHAT.toString().replaceFirst("%s", Utilities.getPrefixFor(from) + "%s"));
		}
	}

	private static enum LazyWords {
		i("i", "I"), dont("dont", "don't"), cant("cant", "can't"), wont("wont", "won't"), thats("thats", "that's"), lets("lets", "let's"), im(
				"im", "I'm"), youre("youre", "you're"), didnt("didnt", "didn't"), doesnt("doesnt", "doesn't"), havent("havent", "haven't"), wouldnt(
				"wouldnt", "wouldn't"), theyre("theyre", "they're"), ass("ass", "art"), tit("tit", "tot"), tits("tits", "tots"), gay("gay",
				"guy"), whats("whats", "what's"), howre("howre", "how're");

		private final String lazy, contracted;

		private LazyWords(String lazy, String contracted) {
			this.lazy = lazy;
			this.contracted = contracted;
		}

		public String matchCase(String toMatch) {
			if ("i".equals(toMatch)) { return "I"; }

			char[] array = contracted.toCharArray().clone();
			char[] newarray = new char[array.length];
			char[] other = toMatch.toCharArray();
			for (int i = 0; i < array.length; i++) {
				try {
					if (Character.toLowerCase(other[i]) == Character.toLowerCase(array[i])) {
						newarray[i] = other[i];
					} else {
						newarray[i] = Character.isUpperCase(other[i]) ? Character.toUpperCase(array[i]) : array[i];
					}
				} catch (Exception e) {
					newarray[i] = array[i];
				}
			}

			return new String(newarray);
		}

		public static String cleanup(String string) {
			String[] parts = string.split(" ");
			StringBuilder builder = new StringBuilder();
			for (String s : parts) {
				boolean b = false;
				for (LazyWords word : LazyWords.values()) {
					if (word.lazy.equalsIgnoreCase(s)) {
						builder.append(word.matchCase(s));
						builder.append(" ");
						b = true;
					}
				}
				if (!b) {
					builder.append(s);
					builder.append(" ");
				}
			}
			return builder.toString().trim();
		}
	}

	private static enum SwearFragments {
		fuck("fuck", "duck"), shit("shit", "shot"), nigger("nigger", "number"), bitch("bitch", "fetch"), dick("dick", "duck"), sex("sex",
				"sox"), cunt("cunt", "cent"), penis("penis", "paris"), vagina("vagina", "regina"), asshole("asshole", "anthole"), faggot(
				"faggot", "finger");

		private final String swear, replacement;

		private SwearFragments(String swear, String replacement) {
			this.swear = swear;
			this.replacement = replacement;
		}

		public String matchCase(String toMatch) {
			char[] array = replacement.toCharArray().clone();
			char[] newarray = new char[array.length];
			char[] other = toMatch.toCharArray();
			for (int i = 0; i < array.length; i++) {
				newarray[i] = Character.isUpperCase(other[i]) ? Character.toUpperCase(array[i]) : array[i];
			}

			return new String(newarray);
		}

		public static String cleanup(String string) {
			for (SwearFragments frag : SwearFragments.values()) {
				while (string.toLowerCase().contains(frag.swear)) {
					int index = string.toLowerCase().indexOf(frag.swear);
					String bad = string.substring(index, index + frag.swear.length());
					string = string.replaceAll(bad, frag.matchCase(bad));
				}
			}
			return string;
		}
	}

	private String format(String message) {
		if (!ConfigEntries.CHAT_FORMATTING.<Boolean> getValue()) { return message; }

		boolean doCapitalize = true;
		StringBuilder builder = new StringBuilder(message);
		int i = 0;
		while (i < builder.length()) {
			if (builder.charAt(i) == '.') {
				doCapitalize = true;
			} else if (doCapitalize && !Character.isWhitespace(builder.charAt(i))) {
				doCapitalize = false;
				builder.setCharAt(i, Character.toUpperCase(builder.charAt(i)));
			}
			i++;
		}
		return SwearFragments.cleanup(LazyWords.cleanup(builder.toString()));
	}
}
