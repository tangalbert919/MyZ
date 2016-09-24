package jordan.sicherman.jnbt;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class CompoundTag extends Tag {

    private final Map value;

    public CompoundTag(String name, Map value) {
        super(name);
        this.value = Collections.unmodifiableMap(value);
    }

    public Map getValue() {
        return this.value;
    }

    public String toString() {
        String name = this.getName();
        String append = "";

        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }

        StringBuilder bldr = new StringBuilder();

        bldr.append("TAG_Compound" + append + ": " + this.value.size() + " entries\r\n{\r\n");
        Iterator iterator = this.value.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            bldr.append("   " + ((Tag) entry.getValue()).toString().replaceAll("\r\n", "\r\n   ") + "\r\n");
        }

        bldr.append("}");
        return bldr.toString();
    }
}
