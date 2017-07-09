package jordan.sicherman.json;

public class HTTPTokener extends JSONTokener {

    public HTTPTokener(String s) {
        super(s);
    }

    public String nextToken() throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();

        char c0;

        do {
            c0 = this.next();
        } while (Character.isWhitespace(c0));

        if (c0 != 34 && c0 != 39) {
            while (c0 != 0 && !Character.isWhitespace(c0)) {
                stringbuffer.append(c0);
                c0 = this.next();
            }

            return stringbuffer.toString();
        } else {
            char c1 = c0;

            while (true) {
                c0 = this.next();
                if (c0 < 32) {
                    throw this.syntaxError("Unterminated string.");
                }

                if (c0 == c1) {
                    return stringbuffer.toString();
                }

                stringbuffer.append(c0);
            }
        }
    }
}
