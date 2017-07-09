package jordan.sicherman.json;

public class CDL {

    private static String getValue(JSONTokener jsontokener) throws JSONException {
        char c0;

        do {
            c0 = jsontokener.next();
        } while (c0 == 32 || c0 == 9);

        switch (c0) {
        case '\u0000':
            return null;

        case '\"':
        case '\'':
            return jsontokener.nextString(c0);

        case ',':
            jsontokener.back();
            return "";

        default:
            jsontokener.back();
            return jsontokener.nextTo(',');
        }
    }

    public static JSONArray rowToJSONArray(JSONTokener jsontokener) throws JSONException {
        JSONArray jsonarray = new JSONArray();

        label33:
        while (true) {
            String s = getValue(jsontokener);

            if (s == null || jsonarray.length() == 0 && s.length() == 0) {
                return null;
            }

            jsonarray.put((Object) s);

            char c0;

            do {
                c0 = jsontokener.next();
                if (c0 == 44) {
                    continue label33;
                }
            } while (c0 == 32);

            if (c0 != 10 && c0 != 13 && c0 != 0) {
                throw jsontokener.syntaxError("Bad character \'" + c0 + "\' (" + c0 + ").");
            }

            return jsonarray;
        }
    }

    public static JSONObject rowToJSONObject(JSONArray jsonarray, JSONTokener jsontokener) throws JSONException {
        JSONArray jsonarray1 = rowToJSONArray(jsontokener);

        return jsonarray1 != null ? jsonarray1.toJSONObject(jsonarray) : null;
    }

    public static JSONArray toJSONArray(String s) throws JSONException {
        return toJSONArray(new JSONTokener(s));
    }

    public static JSONArray toJSONArray(JSONTokener jsontokener) throws JSONException {
        return toJSONArray(rowToJSONArray(jsontokener), jsontokener);
    }

    public static JSONArray toJSONArray(JSONArray jsonarray, String s) throws JSONException {
        return toJSONArray(jsonarray, new JSONTokener(s));
    }

    public static JSONArray toJSONArray(JSONArray jsonarray, JSONTokener jsontokener) throws JSONException {
        if (jsonarray != null && jsonarray.length() != 0) {
            JSONArray jsonarray1 = new JSONArray();

            while (true) {
                JSONObject jsonobject = rowToJSONObject(jsonarray, jsontokener);

                if (jsonobject == null) {
                    return jsonarray1.length() == 0 ? null : jsonarray1;
                }

                jsonarray1.put((Object) jsonobject);
            }
        } else {
            return null;
        }
    }

    public static String rowToString(JSONArray jsonarray) {
        StringBuffer stringbuffer = new StringBuffer();

        for (int i = 0; i < jsonarray.length(); ++i) {
            if (i > 0) {
                stringbuffer.append(',');
            }

            Object object = jsonarray.opt(i);

            if (object != null) {
                String s = object.toString();

                if (s.indexOf(44) >= 0) {
                    if (s.indexOf(34) >= 0) {
                        stringbuffer.append('\'');
                        stringbuffer.append(s);
                        stringbuffer.append('\'');
                    } else {
                        stringbuffer.append('\"');
                        stringbuffer.append(s);
                        stringbuffer.append('\"');
                    }
                } else {
                    stringbuffer.append(s);
                }
            }
        }

        stringbuffer.append('\n');
        return stringbuffer.toString();
    }

    public static String toString(JSONArray jsonarray) throws JSONException {
        JSONObject jsonobject = jsonarray.optJSONObject(0);

        if (jsonobject != null) {
            JSONArray jsonarray1 = jsonobject.names();

            if (jsonarray1 != null) {
                return rowToString(jsonarray1) + toString(jsonarray1, jsonarray);
            }
        }

        return null;
    }

    public static String toString(JSONArray jsonarray, JSONArray jsonarray1) throws JSONException {
        if (jsonarray != null && jsonarray.length() != 0) {
            StringBuffer stringbuffer = new StringBuffer();

            for (int i = 0; i < jsonarray1.length(); ++i) {
                JSONObject jsonobject = jsonarray1.optJSONObject(i);

                if (jsonobject != null) {
                    stringbuffer.append(rowToString(jsonobject.toJSONArray(jsonarray)));
                }
            }

            return stringbuffer.toString();
        } else {
            return null;
        }
    }
}
