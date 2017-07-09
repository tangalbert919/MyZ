package jordan.sicherman.json;

import java.io.IOException;
import java.io.Writer;

public class JSONWriter {

    private static final int maxdepth = 20;
    private boolean comma = false;
    protected char mode = 105;
    private JSONObject[] stack = new JSONObject[20];
    private int top = 0;
    protected Writer writer;

    public JSONWriter(Writer writer) {
        this.writer = writer;
    }

    private JSONWriter append(String s) throws JSONException {
        if (s == null) {
            throw new JSONException("Null pointer");
        } else if (this.mode != 111 && this.mode != 97) {
            throw new JSONException("Value out of sequence.");
        } else {
            try {
                if (this.comma && this.mode == 97) {
                    this.writer.write(44);
                }

                this.writer.write(s);
            } catch (IOException ioexception) {
                throw new JSONException(ioexception);
            }

            if (this.mode == 111) {
                this.mode = 107;
            }

            this.comma = true;
            return this;
        }
    }

    public JSONWriter array() throws JSONException {
        if (this.mode != 105 && this.mode != 111 && this.mode != 97) {
            throw new JSONException("Misplaced array.");
        } else {
            this.push((JSONObject) null);
            this.append("[");
            this.comma = false;
            return this;
        }
    }

    private JSONWriter end(char c0, char c1) throws JSONException {
        if (this.mode != c0) {
            throw new JSONException(c0 == 111 ? "Misplaced endObject." : "Misplaced endArray.");
        } else {
            this.pop(c0);

            try {
                this.writer.write(c1);
            } catch (IOException ioexception) {
                throw new JSONException(ioexception);
            }

            this.comma = true;
            return this;
        }
    }

    public JSONWriter endArray() throws JSONException {
        return this.end('a', ']');
    }

    public JSONWriter endObject() throws JSONException {
        return this.end('k', '}');
    }

    public JSONWriter key(String s) throws JSONException {
        if (s == null) {
            throw new JSONException("Null key.");
        } else if (this.mode == 107) {
            try {
                if (this.comma) {
                    this.writer.write(44);
                }

                this.stack[this.top - 1].putOnce(s, Boolean.TRUE);
                this.writer.write(JSONObject.quote(s));
                this.writer.write(58);
                this.comma = false;
                this.mode = 111;
                return this;
            } catch (IOException ioexception) {
                throw new JSONException(ioexception);
            }
        } else {
            throw new JSONException("Misplaced key.");
        }
    }

    public JSONWriter object() throws JSONException {
        if (this.mode == 105) {
            this.mode = 111;
        }

        if (this.mode != 111 && this.mode != 97) {
            throw new JSONException("Misplaced object.");
        } else {
            this.append("{");
            this.push(new JSONObject());
            this.comma = false;
            return this;
        }
    }

    private void pop(char c0) throws JSONException {
        if (this.top <= 0) {
            throw new JSONException("Nesting error.");
        } else {
            int i = this.stack[this.top - 1] == null ? 97 : 107;

            if (i != c0) {
                throw new JSONException("Nesting error.");
            } else {
                --this.top;
                this.mode = (char) (this.top == 0 ? 100 : (this.stack[this.top - 1] == null ? 97 : 107));
            }
        }
    }

    private void push(JSONObject jsonobject) throws JSONException {
        if (this.top >= 20) {
            throw new JSONException("Nesting too deep.");
        } else {
            this.stack[this.top] = jsonobject;
            this.mode = (char) (jsonobject == null ? 97 : 107);
            ++this.top;
        }
    }

    public JSONWriter value(boolean flag) throws JSONException {
        return this.append(flag ? "true" : "false");
    }

    public JSONWriter value(double d0) throws JSONException {
        return this.value(new Double(d0));
    }

    public JSONWriter value(long i) throws JSONException {
        return this.append(Long.toString(i));
    }

    public JSONWriter value(Object object) throws JSONException {
        return this.append(JSONObject.valueToString(object));
    }
}
