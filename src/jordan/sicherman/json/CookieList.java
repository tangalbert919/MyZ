package jordan.sicherman.json;

import java.util.Iterator;

public class CookieList {

    public static JSONObject toJSONObject(String s) throws JSONException {
        JSONObject jsonobject = new JSONObject();
        JSONTokener jsontokener = new JSONTokener(s);

        while (jsontokener.more()) {
            String s1 = Cookie.unescape(jsontokener.nextTo('='));

            jsontokener.next('=');
            jsonobject.put(s1, (Object) Cookie.unescape(jsontokener.nextTo(';')));
            jsontokener.next();
        }

        return jsonobject;
    }

    public static String toString(JSONObject jsonobject) throws JSONException {
        boolean flag = false;
        Iterator iterator = jsonobject.keys();
        StringBuffer stringbuffer = new StringBuffer();

        while (iterator.hasNext()) {
            String s = iterator.next().toString();

            if (!jsonobject.isNull(s)) {
                if (flag) {
                    stringbuffer.append(';');
                }

                stringbuffer.append(Cookie.escape(s));
                stringbuffer.append("=");
                stringbuffer.append(Cookie.escape(jsonobject.getString(s)));
                flag = true;
            }
        }

        return stringbuffer.toString();
    }
}
