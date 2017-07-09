package jordan.sicherman.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class JSONArray {

    private ArrayList myArrayList;

    public JSONArray() {
        this.myArrayList = new ArrayList();
    }

    public JSONArray(JSONTokener jsontokener) throws JSONException {
        this();
        char c0 = jsontokener.nextClean();
        char c1;

        if (c0 == 91) {
            c1 = 93;
        } else {
            if (c0 != 40) {
                throw jsontokener.syntaxError("A JSONArray text must start with \'[\'");
            }

            c1 = 41;
        }

        if (jsontokener.nextClean() != 93) {
            jsontokener.back();

            while (true) {
                if (jsontokener.nextClean() == 44) {
                    jsontokener.back();
                    this.myArrayList.add((Object) null);
                } else {
                    jsontokener.back();
                    this.myArrayList.add(jsontokener.nextValue());
                }

                c0 = jsontokener.nextClean();
                switch (c0) {
                case ')':
                case ']':
                    if (c1 != c0) {
                        throw jsontokener.syntaxError("Expected a \'" + new Character(c1) + "\'");
                    }

                    return;

                case ',':
                case ';':
                    if (jsontokener.nextClean() == 93) {
                        return;
                    }

                    jsontokener.back();
                    break;

                default:
                    throw jsontokener.syntaxError("Expected a \',\' or \']\'");
                }
            }
        }
    }

    public JSONArray(String s) throws JSONException {
        this(new JSONTokener(s));
    }

    public JSONArray(Collection collection) {
        this.myArrayList = collection == null ? new ArrayList() : new ArrayList(collection);
    }

    public JSONArray(Collection collection, boolean flag) {
        this.myArrayList = new ArrayList();
        if (collection != null) {
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                this.myArrayList.add(new JSONObject(iterator.next(), flag));
            }
        }

    }

    public JSONArray(Object object) throws JSONException {
        this();
        if (!object.getClass().isArray()) {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        } else {
            int i = Array.getLength(object);

            for (int j = 0; j < i; ++j) {
                this.put(Array.get(object, j));
            }

        }
    }

    public JSONArray(Object object, boolean flag) throws JSONException {
        this();
        if (!object.getClass().isArray()) {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        } else {
            int i = Array.getLength(object);

            for (int j = 0; j < i; ++j) {
                this.put((Object) (new JSONObject(Array.get(object, j), flag)));
            }

        }
    }

    public Object get(int i) throws JSONException {
        Object object = this.opt(i);

        if (object == null) {
            throw new JSONException("JSONArray[" + i + "] not found.");
        } else {
            return object;
        }
    }

    public boolean getBoolean(int i) throws JSONException {
        Object object = this.get(i);

        if (!object.equals(Boolean.FALSE) && (!(object instanceof String) || !((String) object).equalsIgnoreCase("false"))) {
            if (!object.equals(Boolean.TRUE) && (!(object instanceof String) || !((String) object).equalsIgnoreCase("true"))) {
                throw new JSONException("JSONArray[" + i + "] is not a Boolean.");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public double getDouble(int i) throws JSONException {
        Object object = this.get(i);

        try {
            return object instanceof Number ? ((Number) object).doubleValue() : Double.valueOf((String) object).doubleValue();
        } catch (Exception exception) {
            throw new JSONException("JSONArray[" + i + "] is not a number.");
        }
    }

    public int getInt(int i) throws JSONException {
        Object object = this.get(i);

        return object instanceof Number ? ((Number) object).intValue() : (int) this.getDouble(i);
    }

    public JSONArray getJSONArray(int i) throws JSONException {
        Object object = this.get(i);

        if (object instanceof JSONArray) {
            return (JSONArray) object;
        } else {
            throw new JSONException("JSONArray[" + i + "] is not a JSONArray.");
        }
    }

    public JSONObject getJSONObject(int i) throws JSONException {
        Object object = this.get(i);

        if (object instanceof JSONObject) {
            return (JSONObject) object;
        } else {
            throw new JSONException("JSONArray[" + i + "] is not a JSONObject.");
        }
    }

    public long getLong(int i) throws JSONException {
        Object object = this.get(i);

        return object instanceof Number ? ((Number) object).longValue() : (long) this.getDouble(i);
    }

    public String getString(int i) throws JSONException {
        return this.get(i).toString();
    }

    public boolean isNull(int i) {
        return JSONObject.NULL.equals(this.opt(i));
    }

    public String join(String s) throws JSONException {
        int i = this.length();
        StringBuffer stringbuffer = new StringBuffer();

        for (int j = 0; j < i; ++j) {
            if (j > 0) {
                stringbuffer.append(s);
            }

            stringbuffer.append(JSONObject.valueToString(this.myArrayList.get(j)));
        }

        return stringbuffer.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public Object opt(int i) {
        return i >= 0 && i < this.length() ? this.myArrayList.get(i) : null;
    }

    public boolean optBoolean(int i) {
        return this.optBoolean(i, false);
    }

    public boolean optBoolean(int i, boolean flag) {
        try {
            return this.getBoolean(i);
        } catch (Exception exception) {
            return flag;
        }
    }

    public double optDouble(int i) {
        return this.optDouble(i, Double.NaN);
    }

    public double optDouble(int i, double d0) {
        try {
            return this.getDouble(i);
        } catch (Exception exception) {
            return d0;
        }
    }

    public int optInt(int i) {
        return this.optInt(i, 0);
    }

    public int optInt(int i, int j) {
        try {
            return this.getInt(i);
        } catch (Exception exception) {
            return j;
        }
    }

    public JSONArray optJSONArray(int i) {
        Object object = this.opt(i);

        return object instanceof JSONArray ? (JSONArray) object : null;
    }

    public JSONObject optJSONObject(int i) {
        Object object = this.opt(i);

        return object instanceof JSONObject ? (JSONObject) object : null;
    }

    public long optLong(int i) {
        return this.optLong(i, 0L);
    }

    public long optLong(int i, long j) {
        try {
            return this.getLong(i);
        } catch (Exception exception) {
            return j;
        }
    }

    public String optString(int i) {
        return this.optString(i, "");
    }

    public String optString(int i, String s) {
        Object object = this.opt(i);

        return object != null ? object.toString() : s;
    }

    public JSONArray put(boolean flag) {
        this.put((Object) (flag ? Boolean.TRUE : Boolean.FALSE));
        return this;
    }

    public JSONArray put(Collection collection) {
        this.put((Object) (new JSONArray(collection)));
        return this;
    }

    public JSONArray put(double d0) throws JSONException {
        double Double = new Double(d0);

        JSONObject.testValidity(double.class);
        this.put((Object) double.class);
        return this;
    }

    public JSONArray put(int i) {
        this.put((Object) (new Integer(i)));
        return this;
    }

    public JSONArray put(long i) {
        this.put((Object) (new Long(i)));
        return this;
    }

    public JSONArray put(Map map) {
        this.put((Object) (new JSONObject(map)));
        return this;
    }

    public JSONArray put(Object object) {
        this.myArrayList.add(object);
        return this;
    }

    public JSONArray put(int i, boolean flag) throws JSONException {
        this.put(i, (Object) (flag ? Boolean.TRUE : Boolean.FALSE));
        return this;
    }

    public JSONArray put(int i, Collection collection) throws JSONException {
        this.put(i, (Object) (new JSONArray(collection)));
        return this;
    }

    public JSONArray put(int i, double d0) throws JSONException {
        this.put(i, (Object) (new Double(d0)));
        return this;
    }

    public JSONArray put(int i, int j) throws JSONException {
        this.put(i, (Object) (new Integer(j)));
        return this;
    }

    public JSONArray put(int i, long j) throws JSONException {
        this.put(i, (Object) (new Long(j)));
        return this;
    }

    public JSONArray put(int i, Map map) throws JSONException {
        this.put(i, (Object) (new JSONObject(map)));
        return this;
    }

    public JSONArray put(int i, Object object) throws JSONException {
        JSONObject.testValidity(object);
        if (i < 0) {
            throw new JSONException("JSONArray[" + i + "] not found.");
        } else {
            if (i < this.length()) {
                this.myArrayList.set(i, object);
            } else {
                while (i != this.length()) {
                    this.put(JSONObject.NULL);
                }

                this.put(object);
            }

            return this;
        }
    }

    public JSONObject toJSONObject(JSONArray jsonarray) throws JSONException {
        if (jsonarray != null && jsonarray.length() != 0 && this.length() != 0) {
            JSONObject jsonobject = new JSONObject();

            for (int i = 0; i < jsonarray.length(); ++i) {
                jsonobject.put(jsonarray.getString(i), this.opt(i));
            }

            return jsonobject;
        } else {
            return null;
        }
    }

    public String toString() {
        try {
            return '[' + this.join(",") + ']';
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
            return "[]";
        } else {
            StringBuffer stringbuffer = new StringBuffer("[");

            if (k == 1) {
                stringbuffer.append(JSONObject.valueToString(this.myArrayList.get(0), i, j));
            } else {
                int l = j + i;

                stringbuffer.append('\n');

                int i1;

                for (i1 = 0; i1 < k; ++i1) {
                    if (i1 > 0) {
                        stringbuffer.append(",\n");
                    }

                    for (int j1 = 0; j1 < l; ++j1) {
                        stringbuffer.append(' ');
                    }

                    stringbuffer.append(JSONObject.valueToString(this.myArrayList.get(i1), i, l));
                }

                stringbuffer.append('\n');

                for (i1 = 0; i1 < j; ++i1) {
                    stringbuffer.append(' ');
                }
            }

            stringbuffer.append(']');
            return stringbuffer.toString();
        }
    }

    public Writer write(Writer writer) throws JSONException {
        try {
            boolean flag = false;
            int i = this.length();

            writer.write(91);

            for (int j = 0; j < i; ++j) {
                if (flag) {
                    writer.write(44);
                }

                Object object = this.myArrayList.get(j);

                if (object instanceof JSONObject) {
                    ((JSONObject) object).write(writer);
                } else if (object instanceof JSONArray) {
                    ((JSONArray) object).write(writer);
                } else {
                    writer.write(JSONObject.valueToString(object));
                }

                flag = true;
            }

            writer.write(93);
            return writer;
        } catch (IOException ioexception) {
            throw new JSONException(ioexception);
        }
    }
}
