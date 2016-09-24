package jordan.sicherman.json;

import java.util.Iterator;

public class HTTP {

    public static final String CRLF = "\r\n";

    public static JSONObject toJSONObject(String s) throws JSONException {
        JSONObject jsonobject = new JSONObject();
        HTTPTokener httptokener = new HTTPTokener(s);
        String s1 = httptokener.nextToken();

        if (s1.toUpperCase().startsWith("HTTP")) {
            jsonobject.put("HTTP-Version", (Object) s1);
            jsonobject.put("Status-Code", (Object) httptokener.nextToken());
            jsonobject.put("Reason-Phrase", (Object) httptokener.nextTo('\u0000'));
            httptokener.next();
        } else {
            jsonobject.put("Method", (Object) s1);
            jsonobject.put("Request-URI", (Object) httptokener.nextToken());
            jsonobject.put("HTTP-Version", (Object) httptokener.nextToken());
        }

        while (httptokener.more()) {
            String s2 = httptokener.nextTo(':');

            httptokener.next(':');
            jsonobject.put(s2, (Object) httptokener.nextTo('\u0000'));
            httptokener.next();
        }

        return jsonobject;
    }

    public static String toString(JSONObject jsonobject) throws JSONException {
        Iterator iterator = jsonobject.keys();
        StringBuffer stringbuffer = new StringBuffer();

        if (jsonobject.has("Status-Code") && jsonobject.has("Reason-Phrase")) {
            stringbuffer.append(jsonobject.getString("HTTP-Version"));
            stringbuffer.append(' ');
            stringbuffer.append(jsonobject.getString("Status-Code"));
            stringbuffer.append(' ');
            stringbuffer.append(jsonobject.getString("Reason-Phrase"));
        } else {
            if (!jsonobject.has("Method") || !jsonobject.has("Request-URI")) {
                throw new JSONException("Not enough material for an HTTP header.");
            }

            stringbuffer.append(jsonobject.getString("Method"));
            stringbuffer.append(' ');
            stringbuffer.append('\"');
            stringbuffer.append(jsonobject.getString("Request-URI"));
            stringbuffer.append('\"');
            stringbuffer.append(' ');
            stringbuffer.append(jsonobject.getString("HTTP-Version"));
        }

        stringbuffer.append("\r\n");

        while (iterator.hasNext()) {
            String s = iterator.next().toString();

            if (!s.equals("HTTP-Version") && !s.equals("Status-Code") && !s.equals("Reason-Phrase") && !s.equals("Method") && !s.equals("Request-URI") && !jsonobject.isNull(s)) {
                stringbuffer.append(s);
                stringbuffer.append(": ");
                stringbuffer.append(jsonobject.getString(s));
                stringbuffer.append("\r\n");
            }
        }

        stringbuffer.append("\r\n");
        return stringbuffer.toString();
    }
}
