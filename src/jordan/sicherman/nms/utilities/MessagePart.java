package jordan.sicherman.nms.utilities;

import jordan.sicherman.json.JSONException;
import jordan.sicherman.json.JSONWriter;
import org.bukkit.ChatColor;

public final class MessagePart {

    public ChatColor color;
    public ChatColor[] styles;
    public String clickActionName;
    public String clickActionData;
    public String hoverActionName;
    public String hoverActionData;
    public String text;

    public MessagePart(String text) {
        this.text = text;
    }

    public JSONWriter writeJson(JSONWriter json) throws JSONException {
        json.object().key("text").value(this.text);
        if (this.color != null) {
            json.key("color").value(this.color.name().toLowerCase());
        }

        if (this.styles != null) {
            ChatColor[] achatcolor = this.styles;
            int i = achatcolor.length;

            for (int j = 0; j < i; ++j) {
                ChatColor style = achatcolor[j];

                json.key(style.name().toLowerCase()).value(true);
            }
        }

        if (this.clickActionName != null && this.clickActionData != null) {
            json.key("clickEvent").object().key("action").value(this.clickActionName).key("value").value(this.clickActionData).endObject();
        }

        if (this.hoverActionName != null && this.hoverActionData != null) {
            json.key("hoverEvent").object().key("action").value(this.hoverActionName).key("value").value(this.hoverActionData).endObject();
        }

        return json.endObject();
    }
}
