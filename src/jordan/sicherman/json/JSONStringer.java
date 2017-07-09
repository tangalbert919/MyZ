package jordan.sicherman.json;

import java.io.StringWriter;

public class JSONStringer extends JSONWriter {

    public JSONStringer() {
        super(new StringWriter());
    }

    public String toString() {
        return this.mode == 100 ? this.writer.toString() : null;
    }
}
