/**
 * 
 */
package jordan.sicherman.nms.v1_7_R4;

import java.util.ArrayList;
import java.util.List;

import jordan.sicherman.nms.utilities.MessagePart;
import jordan.sicherman.nms.utilities.NMS;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.JSONException;
import org.json.JSONStringer;

/**
 * @author mkremins
 * 
 */
public class JSONMessage {

	private final List<MessagePart> messageParts;

	public JSONMessage(final String firstPartText) {
		messageParts = new ArrayList<MessagePart>();
		messageParts.add(new MessagePart(firstPartText));
	}

	private MessagePart latest() {
		return messageParts.get(messageParts.size() - 1);
	}

	private void onClick(final String name, final String data) {
		final MessagePart latest = latest();
		latest.clickActionName = name;
		latest.clickActionData = data;
	}

	private void onHover(final String name, final String data) {
		final MessagePart latest = latest();
		latest.hoverActionName = name;
		latest.hoverActionData = data;
	}

	public JSONMessage achievementTooltip(final String name) {
		onHover("show_achievement", "achievement." + name);
		return this;
	}

	public JSONMessage color(final ChatColor color) {
		if (color == null) { return this; }

		if (!color.isColor()) { throw new IllegalArgumentException(color.name() + " is not a color"); }
		latest().color = color;
		return this;
	}

	public JSONMessage command(final String command) {
		onClick("run_command", command);
		return this;
	}

	public JSONMessage file(final String path) {
		onClick("open_file", path);
		return this;
	}

	public JSONMessage itemTooltip(final ItemStack itemStack) {
		if (itemStack == null || itemStack.getType() == Material.AIR) { return this; }
		return itemTooltip(CraftItemStack.asNMSCopy(itemStack).save(new NBTTagCompound()).toString());
	}

	public JSONMessage itemTooltip(final String itemJSON) {
		onHover("show_item", itemJSON);
		return this;
	}

	public JSONMessage link(final String url) {
		onClick("open_url", url);
		return this;
	}

	public boolean send(Player player) {
		try {
			NMS.sendPacket(new PacketPlayOutChat(ChatSerializer.a(toJSONString())), player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public JSONMessage style(final ChatColor... styles) {
		for (final ChatColor style : styles) {
			if (!style.isFormat()) { throw new IllegalArgumentException(style.name() + " is not a style"); }
		}
		latest().styles = styles;
		return this;
	}

	public JSONMessage suggest(final String command) {
		onClick("suggest_command", command);
		return this;
	}

	public JSONMessage then(final Object obj) {
		messageParts.add(new MessagePart(obj.toString()));
		return this;
	}

	public String toJSONString() {
		final JSONStringer json = new JSONStringer();
		try {
			if (messageParts.size() == 1) {
				latest().writeJson(json);
			} else {
				json.object().key("text").value("").key("extra").array();
				for (final MessagePart part : messageParts) {
					part.writeJson(json);
				}
				json.endArray().endObject();
			}
		} catch (final JSONException e) {
			throw new RuntimeException("Invalid message");
		}
		return json.toString();
	}

	public JSONMessage tooltip(final String text) {
		onHover("show_text", text);
		return this;
	}

}
