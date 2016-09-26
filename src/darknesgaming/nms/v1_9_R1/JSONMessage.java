package darknesgaming.nms.v1_9_R1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jordan.sicherman.json.JSONException;
import jordan.sicherman.json.JSONStringer;
import jordan.sicherman.nms.utilities.MessagePart;
import jordan.sicherman.nms.utilities.NMS;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JSONMessage {

	    private final List messageParts = new ArrayList();

	    public JSONMessage(String firstPartText) {
	        this.messageParts.add(new MessagePart(firstPartText));
	    }

	    private MessagePart latest() {
	        return (MessagePart) this.messageParts.get(this.messageParts.size() - 1);
	    }

	    private void onClick(String name, String data) {
	        MessagePart latest = this.latest();

	        latest.clickActionName = name;
	        latest.clickActionData = data;
	    }

	    private void onHover(String name, String data) {
	        MessagePart latest = this.latest();

	        latest.hoverActionName = name;
	        latest.hoverActionData = data;
	    }

	    public JSONMessage achievementTooltip(String name) {
	        this.onHover("show_achievement", "achievement." + name);
	        return this;
	    }

	    public JSONMessage color(ChatColor color) {
	        if (color == null) {
	            return this;
	        } else if (!color.isColor()) {
	            throw new IllegalArgumentException(color.name() + " is not a color");
	        } else {
	            this.latest().color = color;
	            return this;
	        }
	    }

	    public JSONMessage command(String command) {
	        this.onClick("run_command", command);
	        return this;
	    }

	    public JSONMessage file(String path) {
	        this.onClick("open_file", path);
	        return this;
	    }

	    public JSONMessage itemTooltip(ItemStack itemStack) {
	        return itemStack != null && itemStack.getType() != Material.AIR ? this.itemTooltip(CraftItemStack.asNMSCopy(itemStack).save(new NBTTagCompound()).toString()) : this;
	    }

	    public JSONMessage itemTooltip(String itemJSON) {
	        this.onHover("show_item", itemJSON);
	        return this;
	    }

	    public JSONMessage link(String url) {
	        this.onClick("open_url", url);
	        return this;
	    }

	    public boolean send(Player player) {
	        try {
	            NMS.sendPacket(new PacketPlayOutChat(ChatModifier(this.toJSONString())), player);
	            return true;
	        } catch (Exception exception) {
	            exception.printStackTrace();
	            return false;
	        }
	    }

	    private IChatBaseComponent ChatModifier(String jsonString) {
			return null;
		}

		public JSONMessage style(ChatColor... styles) {
	        ChatColor[] achatcolor = styles;
	        int i = styles.length;

	        for (int j = 0; j < i; ++j) {
	            ChatColor style = achatcolor[j];

	            if (!style.isFormat()) {
	                throw new IllegalArgumentException(style.name() + " is not a style");
	            }
	        }

	        this.latest().styles = styles;
	        return this;
	    }

	    public JSONMessage suggest(String command) {
	        this.onClick("suggest_command", command);
	        return this;
	    }

	    public JSONMessage then(Object obj) {
	        this.messageParts.add(new MessagePart(obj.toString()));
	        return this;
	    }

	    public String toJSONString() {
	        JSONStringer json = new JSONStringer();

	        try {
	            if (this.messageParts.size() == 1) {
	                this.latest().writeJson(json);
	            } else {
	                json.object().key("text").value("").key("extra").array();
	                Iterator e = this.messageParts.iterator();

	                while (e.hasNext()) {
	                    MessagePart part = (MessagePart) e.next();

	                    part.writeJson(json);
	                }

	                json.endArray().endObject();
	            }
	        } catch (JSONException jsonexception) {
	            throw new RuntimeException("Invalid message");
	        }

	        return json.toString();
	    }

	    public JSONMessage tooltip(String text) {
	        this.onHover("show_text", text);
	        return this;
	    }
	}
