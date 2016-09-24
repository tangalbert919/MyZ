package jordan.sicherman.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

public class JSONObject {

    private Map map;
    public static final Object NULL = new JSONObject.Null((JSONObject.SyntheticClass_1) null);

    public JSONObject() {
        this.map = new HashMap();
    }

    public JSONObject(JSONObject jsonobject, String[] astring) throws JSONException {
        this();

        for (int i = 0; i < astring.length; ++i) {
            this.putOnce(astring[i], jsonobject.opt(astring[i]));
        }

    }

    public JSONObject(JSONTokener jsontokener) throws JSONException {
        this();
        if (jsontokener.nextClean() != 123) {
            throw jsontokener.syntaxError("A JSONObject text must begin with \'{\'");
        } else {
            while (true) {
                char c0 = jsontokener.nextClean();

                switch (c0) {
                case '\u0000':
                    throw jsontokener.syntaxError("A JSONObject text must end with \'}\'");

                case '}':
                    return;

                default:
                    jsontokener.back();
                    String s = jsontokener.nextValue().toString();

                    c0 = jsontokener.nextClean();
                    if (c0 == 61) {
                        if (jsontokener.next() != 62) {
                            jsontokener.back();
                        }
                    } else if (c0 != 58) {
                        throw jsontokener.syntaxError("Expected a \':\' after a key");
                    }

                    this.putOnce(s, jsontokener.nextValue());
                    switch (jsontokener.nextClean()) {
                    case ',':
                    case ';':
                        if (jsontokener.nextClean() == 125) {
                            return;
                        }

                        jsontokener.back();
                        break;

                    case '}':
                        return;

                    default:
                        throw jsontokener.syntaxError("Expected a \',\' or \'}\'");
                    }
                }
            }
        }
    }

    public JSONObject(Map map) {
        this.map = (Map) (map == null ? new HashMap() : map);
    }

    public JSONObject(Map map, boolean flag) {
        this.map = new HashMap();
        if (map != null) {
            Iterator iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();

                this.map.put(entry.getKey(), new JSONObject(entry.getValue(), flag));
            }
        }

    }

    public JSONObject(Object object) {
        this();
        this.populateInternalMap(object, false);
    }

    public JSONObject(Object object, boolean flag) {
        this();
        this.populateInternalMap(object, flag);
    }

    private void populateInternalMap(Object object, boolean flag) {
        Class oclass = object.getClass();

        if (oclass.getClassLoader() == null) {
            flag = false;
        }

        Method[] amethod = flag ? oclass.getMethods() : oclass.getDeclaredMethods();

        for (int i = 0; i < amethod.length; ++i) {
            try {
                Method method = amethod[i];
                String s = method.getName();
                String s1 = "";

                if (s.startsWith("get")) {
                    s1 = s.substring(3);
                } else if (s.startsWith("is")) {
                    s1 = s.substring(2);
                }

                if (s1.length() > 0 && Character.isUpperCase(s1.charAt(0)) && method.getParameterTypes().length == 0) {
                    if (s1.length() == 1) {
                        s1 = s1.toLowerCase();
                    } else if (!Character.isUpperCase(s1.charAt(1))) {
                        s1 = s1.substring(0, 1).toLowerCase() + s1.substring(1);
                    }

                    Object object1 = method.invoke(object, (Object[]) null);

                    if (object1 == null) {
                        this.map.put(s1, JSONObject.NULL);
                    } else if (object1.getClass().isArray()) {
                        this.map.put(s1, new JSONArray(object1, flag));
                    } else if (object1 instanceof Collection) {
                        this.map.put(s1, new JSONArray((Collection) object1, flag));
                    } else if (object1 instanceof Map) {
                        this.map.put(s1, new JSONObject((Map) object1, flag));
                    } else if (this.isStandardProperty(object1.getClass())) {
                        this.map.put(s1, object1);
                    } else if (!object1.getClass().getPackage().getName().startsWith("java") && object1.getClass().getClassLoader() != null) {
                        this.map.put(s1, new JSONObject(object1, flag));
                    } else {
                        this.map.put(s1, object1.toString());
                    }
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

    }

    private boolean isStandardProperty(Class oclass) {
        return oclass.isPrimitive() || oclass.isAssignableFrom(Byte.class) || oclass.isAssignableFrom(Short.class) || oclass.isAssignableFrom(Integer.class) || oclass.isAssignableFrom(Long.class) || oclass.isAssignableFrom(Float.class) || oclass.isAssignableFrom(Double.class) || oclass.isAssignableFrom(Character.class) || oclass.isAssignableFrom(String.class) || oclass.isAssignableFrom(Boolean.class);
    }

    public JSONObject(Object object, String[] astring) {
        this();
        Class oclass = object.getClass();

        for (int i = 0; i < astring.length; ++i) {
            String s = astring[i];

            try {
                this.putOpt(s, oclass.getField(s).get(object));
            } catch (Exception exception) {
                ;
            }
        }

    }

    public JSONObject(String s) throws JSONException {
        this(new JSONTokener(s));
    }

    public JSONObject accumulate(String s, Object object) throws JSONException {
        testValidity(object);
        Object object1 = this.opt(s);

        if (object1 == null) {
            this.put(s, object instanceof JSONArray ? (new JSONArray()).put(object) : object);
        } else if (object1 instanceof JSONArray) {
            ((JSONArray) object1).put(object);
        } else {
            this.put(s, (Object) (new JSONArray()).put(object1).put(object));
        }

        return this;
    }

    public JSONObject append(String s, Object object) throws JSONException {
        testValidity(object);
        Object object1 = this.opt(s);

        if (object1 == null) {
            this.put(s, (Object) (new JSONArray()).put(object));
        } else {
            if (!(object1 instanceof JSONArray)) {
                throw new JSONException("JSONObject[" + s + "] is not a JSONArray.");
            }

            this.put(s, (Object) ((JSONArray) object1).put(object));
        }

        return this;
    }

    public static String doubleToString(double d0) {
        if (!Double.isInfinite(d0) && !Double.isNaN(d0)) {
            String s = Double.toString(d0);

            if (s.indexOf(46) > 0 && s.indexOf(101) < 0 && s.indexOf(69) < 0) {
                while (s.endsWith("0")) {
                    s = s.substring(0, s.length() - 1);
                }

                if (s.endsWith(".")) {
                    s = s.substring(0, s.length() - 1);
                }
            }

            return s;
        } else {
            return "null";
        }
    }

    public Object get(String s) throws JSONException {
        Object object = this.opt(s);

        if (object == null) {
            throw new JSONException("JSONObject[" + quote(s) + "] not found.");
        } else {
            return object;
        }
    }

    public boolean getBoolean(String s) throws JSONException {
        Object object = this.get(s);

        if (!object.equals(Boolean.FALSE) && (!(object instanceof String) || !((String) object).equalsIgnoreCase("false"))) {
            if (!object.equals(Boolean.TRUE) && (!(object instanceof String) || !((String) object).equalsIgnoreCase("true"))) {
                throw new JSONException("JSONObject[" + quote(s) + "] is not a Boolean.");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public double getDouble(String s) throws JSONException {
        Object object = this.get(s);

        try {
            return object instanceof Number ? ((Number) object).doubleValue() : Double.valueOf((String) object).doubleValue();
        } catch (Exception exception) {
            throw new JSONException("JSONObject[" + quote(s) + "] is not a number.");
        }
    }

    public int getInt(String s) throws JSONException {
        Object object = this.get(s);

        return object instanceof Number ? ((Number) object).intValue() : (int) this.getDouble(s);
    }

    public JSONArray getJSONArray(String s) throws JSONException {
        Object object = this.get(s);

        if (object instanceof JSONArray) {
            return (JSONArray) object;
        } else {
            throw new JSONException("JSONObject[" + quote(s) + "] is not a JSONArray.");
        }
    }

    public JSONObject getJSONObject(String s) throws JSONException {
        Object object = this.get(s);

        if (object instanceof JSONObject) {
            return (JSONObject) object;
        } else {
            throw new JSONException("JSONObject[" + quote(s) + "] is not a JSONObject.");
        }
    }

    public long getLong(String s) throws JSONException {
        Object object = this.get(s);

        return object instanceof Number ? ((Number) object).longValue() : (long) this.getDouble(s);
    }

    public static String[] getNames(JSONObject jsonobject) {
        int i = jsonobject.length();

        if (i == 0) {
            return null;
        } else {
            Iterator iterator = jsonobject.keys();
            String[] astring = new String[i];

            for (int j = 0; iterator.hasNext(); ++j) {
                astring[j] = (String) iterator.next();
            }

            return astring;
        }
    }

    public static String[] getNames(Object object) {
        if (object == null) {
            return null;
        } else {
            Class oclass = object.getClass();
            Field[] afield = oclass.getFields();
            int i = afield.length;

            if (i == 0) {
                return null;
            } else {
                String[] astring = new String[i];

                for (int j = 0; j < i; ++j) {
                    astring[j] = afield[j].getName();
                }

                return astring;
            }
        }
    }

    public String getString(String s) throws JSONException {
        return this.get(s).toString();
    }

    public boolean has(String s) {
        return this.map.containsKey(s);
    }

    public boolean isNull(String s) {
        return JSONObject.NULL.equals(this.opt(s));
    }

    public Iterator keys() {
        return this.map.keySet().iterator();
    }

    public int length() {
        return this.map.size();
    }

    public JSONArray names() {
        JSONArray jsonarray = new JSONArray();
        Iterator iterator = this.keys();

        while (iterator.hasNext()) {
            jsonarray.put(iterator.next());
        }

        return jsonarray.length() == 0 ? null : jsonarray;
    }

    public static String numberToString(Number number) throws JSONException {
        if (number == null) {
            throw new JSONException("Null pointer");
        } else {
            testValidity(number);
            String s = number.toString();

            if (s.indexOf(46) > 0 && s.indexOf(101) < 0 && s.indexOf(69) < 0) {
                while (s.endsWith("0")) {
                    s = s.substring(0, s.length() - 1);
                }

                if (s.endsWith(".")) {
                    s = s.substring(0, s.length() - 1);
                }
            }

            return s;
        }
    }

    public Object opt(String s) {
        return s == null ? null : this.map.get(s);
    }

    public boolean optBoolean(String s) {
        return this.optBoolean(s, false);
    }

    public boolean optBoolean(String s, boolean flag) {
        try {
            return this.getBoolean(s);
        } catch (Exception exception) {
            return flag;
        }
    }

    public JSONObject put(String s, Collection collection) throws JSONException {
        this.put(s, (Object) (new JSONArray(collection)));
        return this;
    }

    public double optDouble(String s) {
        return this.optDouble(s, Double.NaN);
    }

    public double optDouble(String s, double d0) {
        try {
            Object object = this.opt(s);

            return object instanceof Number ? ((Number) object).doubleValue() : (new Double((String) object)).doubleValue();
        } catch (Exception exception) {
            return d0;
        }
    }

    public int optInt(String s) {
        return this.optInt(s, 0);
    }

    public int optInt(String s, int i) {
        try {
            return this.getInt(s);
        } catch (Exception exception) {
            return i;
        }
    }

    public JSONArray optJSONArray(String s) {
        Object object = this.opt(s);

        return object instanceof JSONArray ? (JSONArray) object : null;
    }

    public JSONObject optJSONObject(String s) {
        Object object = this.opt(s);

        return object instanceof JSONObject ? (JSONObject) object : null;
    }

    public long optLong(String s) {
        return this.optLong(s, 0L);
    }

    public long optLong(String s, long i) {
        try {
            return this.getLong(s);
        } catch (Exception exception) {
            return i;
        }
    }

    public String optString(String s) {
        return this.optString(s, "");
    }

    public String optString(String s, String s1) {
        Object object = this.opt(s);

        return object != null ? object.toString() : s1;
    }

    public JSONObject put(String s, boolean flag) throws JSONException {
        this.put(s, (Object) (flag ? Boolean.TRUE : Boolean.FALSE));
        return this;
    }

    public JSONObject put(String s, double d0) throws JSONException {
        this.put(s, (Object) (new Double(d0)));
        return this;
    }

    public JSONObject put(String s, int i) throws JSONException {
        this.put(s, (Object) (new Integer(i)));
        return this;
    }

    public JSONObject put(String s, long i) throws JSONException {
        this.put(s, (Object) (new Long(i)));
        return this;
    }

    public JSONObject put(String s, Map map) throws JSONException {
        this.put(s, (Object) (new JSONObject(map)));
        return this;
    }

    public JSONObject put(String s, Object object) throws JSONException {
        if (s == null) {
            throw new JSONException("Null key.");
        } else {
            if (object != null) {
                testValidity(object);
                this.map.put(s, object);
            } else {
                this.remove(s);
            }

            return this;
        }
    }

    public JSONObject putOnce(String s, Object object) throws JSONException {
        if (s != null && object != null) {
            if (this.opt(s) != null) {
                throw new JSONException("Duplicate key \"" + s + "\"");
            }

            this.put(s, object);
        }

        return this;
    }

    public JSONObject putOpt(String s, Object object) throws JSONException {
        if (s != null && object != null) {
            this.put(s, object);
        }

        return this;
    }

    public static String quote(String s) {
        if (s != null && s.length() != 0) {
            char c0 = 0;
            int i = s.length();
            StringBuffer stringbuffer = new StringBuffer(i + 4);

            stringbuffer.append('\"');

            for (int j = 0; j < i; ++j) {
                char c1 = c0;

                c0 = s.charAt(j);
                switch (c0) {
                case '\b':
                    stringbuffer.append("\\b");
                    break;

                case '\t':
                    stringbuffer.append("\\t");
                    break;

                case '\n':
                    stringbuffer.append("\\n");
                    break;

                case '\f':
                    stringbuffer.append("\\f");
                    break;

                case '\r':
                    stringbuffer.append("\\r");
                    break;

                case '\"':
                case '\\':
                    stringbuffer.append('\\');
                    stringbuffer.append(c0);
                    break;

                case '/':
                    if (c1 == 60) {
                        stringbuffer.append('\\');
                    }

                    stringbuffer.append(c0);
                    break;

                default:
                    if (c0 >= 32 && (c0 < 128 || c0 >= 160) && (c0 < 8192 || c0 >= 8448)) {
                        stringbuffer.append(c0);
                    } else {
                        String s1 = "000" + Integer.toHexString(c0);

                        stringbuffer.append("\\u" + s1.substring(s1.length() - 4));
                    }
                }
            }

            stringbuffer.append('\"');
            return stringbuffer.toString();
        } else {
            return "\"\"";
        }
    }

    public Object remove(String s) {
        return this.map.remove(s);
    }

    public Iterator sortedKeys() {
        return (new TreeSet(this.map.keySet())).iterator();
    }

    public static Object stringToValue(String s) {
        if (s.equals("")) {
            return s;
        } else if (s.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if (s.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        } else if (s.equalsIgnoreCase("null")) {
            return JSONObject.NULL;
        } else {
            char c0 = s.charAt(0);

            if (c0 >= 48 && c0 <= 57 || c0 == 46 || c0 == 45 || c0 == 43) {
                if (c0 == 48) {
                    if (s.length() > 2 && (s.charAt(1) == 120 || s.charAt(1) == 88)) {
                        try {
                            return new Integer(Integer.parseInt(s.substring(2), 16));
                        } catch (Exception exception) {
                            ;
                        }
                    } else {
                        try {
                            return new Integer(Integer.parseInt(s, 8));
                        } catch (Exception exception1) {
                            ;
                        }
                    }
                }

                try {
                    return new Integer(s);
                } catch (Exception exception2) {
                    try {
                        return new Long(s);
                    } catch (Exception exception3) {
                        try {
                            return new Double(s);
                        } catch (Exception exception4) {
                            ;
                        }
                    }
                }
            }

            return s;
        }
    }

    static void testValidity(Object object) throws JSONException {
        if (object != null) {
            if (object instanceof Double) {
                if (((Double) object).isInfinite() || ((Double) object).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            } else if (object instanceof Float && (((Float) object).isInfinite() || ((Float) object).isNaN())) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }

    }

    public JSONArray toJSONArray(JSONArray jsonarray) throws JSONException {
        if (jsonarray != null && jsonarray.length() != 0) {
            JSONArray jsonarray1 = new JSONArray();

            for (int i = 0; i < jsonarray.length(); ++i) {
                jsonarray1.put(this.opt(jsonarray.getString(i)));
            }

            return jsonarray1;
        } else {
            return null;
        }
    }

    public String toString() {
        try {
            Iterator iterator = this.keys();
            StringBuffer stringbuffer = new StringBuffer("{");

            while (iterator.hasNext()) {
                if (stringbuffer.length() > 1) {
                    stringbuffer.append(',');
                }

                Object object = iterator.next();

                stringbuffer.append(quote(object.toString()));
                stringbuffer.append(':');
                stringbuffer.append(valueToString(this.map.get(object)));
            }

            stringbuffer.append('}');
            return stringbuffer.toString();
        } catch (Exception exception) {
            return null;
        }
    }

    public String toString(int i) throws JSONException {
        return this.toString(i, 0);
    }

    String toString(int i, int j) throws JSONException {
        int k = this.length();

        if (k == 0) {
            return "{}";
        } else {
            Iterator iterator = this.sortedKeys();
            StringBuffer stringbuffer = new StringBuffer("{");
            int l = j + i;
            Object object;

            if (k == 1) {
                object = iterator.next();
                stringbuffer.append(quote(object.toString()));
                stringbuffer.append(": ");
                stringbuffer.append(valueToString(this.map.get(object), i, j));
            } else {
                int i1;

                while (iterator.hasNext()) {
                    object = iterator.next();
                    if (stringbuffer.length() > 1) {
                        stringbuffer.append(",\n");
                    } else {
                        stringbuffer.append('\n');
                    }

                    for (i1 = 0; i1 < l; ++i1) {
                        stringbuffer.append(' ');
                    }

                    stringbuffer.append(quote(object.toString()));
                    stringbuffer.append(": ");
                    stringbuffer.append(valueToString(this.map.get(object), i, l));
                }

                if (stringbuffer.length() > 1) {
                    stringbuffer.append('\n');

                    for (i1 = 0; i1 < j; ++i1) {
                        stringbuffer.append(' ');
                    }
                }
            }

            stringbuffer.append('}');
            return stringbuffer.toString();
        }
    }

    static String valueToString(Object object) throws JSONException {
        if (object != null && !object.equals((Object) null)) {
            if (object instanceof JSONString) {
                String s;

                try {
                    s = ((JSONString) object).toJSONString();
                } catch (Exception exception) {
                    throw new JSONException(exception);
                }

                if (s instanceof String) {
                    return (String) s;
                } else {
                    throw new JSONException("Bad value from toJSONString: " + s);
                }
            } else {
                return object instanceof Number ? numberToString((Number) object) : (!(object instanceof Boolean) && !(object instanceof JSONObject) && !(object instanceof JSONArray) ? (object instanceof Map ? (new JSONObject((Map) object)).toString() : (object instanceof Collection ? (new JSONArray((Collection) object)).toString() : (object.getClass().isArray() ? (new JSONArray(object)).toString() : quote(object.toString())))) : object.toString());
            }
        } else {
            return "null";
        }
    }

    static String valueToString(Object object, int i, int j) throws JSONException {
        if (object != null && !object.equals((Object) null)) {
            try {
                if (object instanceof JSONString) {
                    String s = ((JSONString) object).toJSONString();

                    if (s instanceof String) {
                        return (String) s;
                    }
                }
            } catch (Exception exception) {
                ;
            }

            return object instanceof Number ? numberToString((Number) object) : (object instanceof Boolean ? object.toString() : (object instanceof JSONObject ? ((JSONObject) object).toString(i, j) : (object instanceof JSONArray ? ((JSONArray) object).toString(i, j) : (object instanceof Map ? (new JSONObject((Map) object)).toString(i, j) : (object instanceof Collection ? (new JSONArray((Collection) object)).toString(i, j) : (object.getClass().isArray() ? (new JSONArray(object)).toString(i, j) : quote(object.toString())))))));
        } else {
            return "null";
        }
    }

    public Writer write(Writer writer) throws JSONException {
        try {
            boolean flag = false;
            Iterator iterator = this.keys();

            writer.write(123);

            for (; iterator.hasNext(); flag = true) {
                if (flag) {
                    writer.write(44);
                }

                Object object = iterator.next();

                writer.write(quote(object.toString()));
                writer.write(58);
                Object object1 = this.map.get(object);

                if (object1 instanceof JSONObject) {
                    ((JSONObject) object1).write(writer);
                } else if (object1 instanceof JSONArray) {
                    ((JSONArray) object1).write(writer);
                } else {
                    writer.write(valueToString(object1));
                }
            }

            writer.write(125);
            return writer;
        } catch (IOException ioexception) {
            throw new JSONException(ioexception);
        }
    }

    static class SyntheticClass_1 {    }

    private static final class Null {

        private Null() {}

        protected final Object clone() {
            return this;
        }

        public boolean equals(Object object) {
            return object == null || object == this;
        }

        public String toString() {
            return "null";
        }

        Null(JSONObject.SyntheticClass_1 jsonobject_syntheticclass_1) {
            this();
        }
    }
}
