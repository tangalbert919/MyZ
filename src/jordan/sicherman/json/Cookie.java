package jordan.sicherman.json;

public class Cookie {

    public static String escape(String s) {
        String s1 = s.trim();
        StringBuffer stringbuffer = new StringBuffer();
        int i = s1.length();

        for (int j = 0; j < i; ++j) {
            char c0 = s1.charAt(j);

            if (c0 >= 32 && c0 != 43 && c0 != 37 && c0 != 61 && c0 != 59) {
                stringbuffer.append(c0);
            } else {
                stringbuffer.append('%');
                stringbuffer.append(Character.forDigit((char) (c0 >>> 4 & 15), 16));
                stringbuffer.append(Character.forDigit((char) (c0 & 15), 16));
            }
        }

        return stringbuffer.toString();
    }

    public static JSONObject toJSONObject(String s) throws JSONException {
        JSONObject jsonobject = new JSONObject();
        JSONTokener jsontokener = new JSONTokener(s);

        jsonobject.put("name", (Object) jsontokener.nextTo('='));
        jsontokener.next('=');
        jsonobject.put("value", (Object) jsontokener.nextTo(';'));
        jsontokener.next();

        String s1;
        Object object;

        for (; jsontokener.more(); jsonobject.put(s1, object)) {
            s1 = unescape(jsontokener.nextTo("=;"));
            if (jsontokener.next() != 61) {
                if (!s1.equals("secure")) {
                    throw jsontokener.syntaxError("Missing \'=\' in cookie parameter.");
                }

                object = Boolean.TRUE;
            } else {
                object = unescape(jsontokener.nextTo(';'));
                jsontokener.next();
            }
        }

        return jsonobject;
    }

    public static String toString(JSONObject jsonobject) throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();

        stringbuffer.append(escape(jsonobject.getString("name")));
        stringbuffer.append("=");
        stringbuffer.append(escape(jsonobject.getString("value")));
        if (jsonobject.has("expires")) {
            stringbuffer.append(";expires=");
            stringbuffer.append(jsonobject.getString("expires"));
        }

        if (jsonobject.has("domain")) {
            stringbuffer.append(";domain=");
            stringbuffer.append(escape(jsonobject.getString("domain")));
        }

        if (jsonobject.has("path")) {
            stringbuffer.append(";path=");
            stringbuffer.append(escape(jsonobject.getString("path")));
        }

        if (jsonobject.optBoolean("secure")) {
            stringbuffer.append(";secure");
        }

        return stringbuffer.toString();
    }

    public static String unescape(String s) {
        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer();

        for (int j = 0; j < i; ++j) {
            char c0 = s.charAt(j);

            if (c0 == 43) {
                c0 = 32;
            } else if (c0 == 37 && j + 2 < i) {
                int k = JSONTokener.dehexchar(s.charAt(j + 1));
                int l = JSONTokener.dehexchar(s.charAt(j + 2));

                if (k >= 0 && l >= 0) {
                    c0 = (char) (k * 16 + l);
                    j += 2;
                }
            }

            stringbuffer.append(c0);
        }

        return stringbuffer.toString();
    }
}
