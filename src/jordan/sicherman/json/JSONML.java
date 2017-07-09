package jordan.sicherman.json;

import java.util.Iterator;

public class JSONML {

    private static Object parse(XMLTokener xmltokener, boolean flag, JSONArray jsonarray) throws JSONException {
        String s = null;
        JSONArray jsonarray1 = null;
        JSONObject jsonobject = null;
        String s1 = null;

        while (true) {
            while (true) {
                Object object = xmltokener.nextContent();

                if (object == XML.LT) {
                    object = xmltokener.nextToken();
                    if (object instanceof Character) {
                        if (object == XML.SLASH) {
                            object = xmltokener.nextToken();
                            if (!(object instanceof String)) {
                                throw new JSONException("Expected a closing name instead of \'" + object + "\'.");
                            }

                            if (xmltokener.nextToken() != XML.GT) {
                                throw xmltokener.syntaxError("Misshaped close tag");
                            }

                            return object;
                        }

                        if (object != XML.BANG) {
                            if (object != XML.QUEST) {
                                throw xmltokener.syntaxError("Misshaped tag");
                            }

                            xmltokener.skipPast("?>");
                        } else {
                            char c0 = xmltokener.next();

                            if (c0 == 45) {
                                if (xmltokener.next() == 45) {
                                    xmltokener.skipPast("-->");
                                }

                                xmltokener.back();
                            } else if (c0 == 91) {
                                object = xmltokener.nextToken();
                                if (!object.equals("CDATA") || xmltokener.next() != 91) {
                                    throw xmltokener.syntaxError("Expected \'CDATA[\'");
                                }

                                if (jsonarray != null) {
                                    jsonarray.put((Object) xmltokener.nextCDATA());
                                }
                            } else {
                                int i = 1;

                                while (true) {
                                    object = xmltokener.nextMeta();
                                    if (object == null) {
                                        throw xmltokener.syntaxError("Missing \'>\' after \'<!\'.");
                                    }

                                    if (object == XML.LT) {
                                        ++i;
                                    } else if (object == XML.GT) {
                                        --i;
                                    }

                                    if (i <= 0) {
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        if (!(object instanceof String)) {
                            throw xmltokener.syntaxError("Bad tagName \'" + object + "\'.");
                        }

                        s1 = (String) object;
                        jsonarray1 = new JSONArray();
                        jsonobject = new JSONObject();
                        if (flag) {
                            jsonarray1.put((Object) s1);
                            if (jsonarray != null) {
                                jsonarray.put((Object) jsonarray1);
                            }
                        } else {
                            jsonobject.put("tagName", (Object) s1);
                            if (jsonarray != null) {
                                jsonarray.put((Object) jsonobject);
                            }
                        }

                        object = null;

                        while (true) {
                            if (object == null) {
                                object = xmltokener.nextToken();
                            }

                            if (object == null) {
                                throw xmltokener.syntaxError("Misshaped tag");
                            }

                            if (!(object instanceof String)) {
                                if (flag && jsonobject.length() > 0) {
                                    jsonarray1.put((Object) jsonobject);
                                }

                                if (object == XML.SLASH) {
                                    if (xmltokener.nextToken() != XML.GT) {
                                        throw xmltokener.syntaxError("Misshaped tag");
                                    }

                                    if (jsonarray == null) {
                                        if (flag) {
                                            return jsonarray1;
                                        }

                                        return jsonobject;
                                    }
                                } else {
                                    if (object != XML.GT) {
                                        throw xmltokener.syntaxError("Misshaped tag");
                                    }

                                    s = (String) parse(xmltokener, flag, jsonarray1);
                                    if (s != null) {
                                        if (!s.equals(s1)) {
                                            throw xmltokener.syntaxError("Mismatched \'" + s1 + "\' and \'" + s + "\'");
                                        }

                                        s1 = null;
                                        if (!flag && jsonarray1.length() > 0) {
                                            jsonobject.put("childNodes", (Object) jsonarray1);
                                        }

                                        if (jsonarray == null) {
                                            if (flag) {
                                                return jsonarray1;
                                            }

                                            return jsonobject;
                                        }
                                    }
                                }
                                break;
                            }

                            String s2 = (String) object;

                            if (!flag && (s2 == "tagName" || s2 == "childNode")) {
                                throw xmltokener.syntaxError("Reserved attribute.");
                            }

                            object = xmltokener.nextToken();
                            if (object == XML.EQ) {
                                object = xmltokener.nextToken();
                                if (!(object instanceof String)) {
                                    throw xmltokener.syntaxError("Missing value");
                                }

                                jsonobject.accumulate(s2, JSONObject.stringToValue((String) object));
                                object = null;
                            } else {
                                jsonobject.accumulate(s2, "");
                            }
                        }
                    }
                } else if (jsonarray != null) {
                    jsonarray.put(object instanceof String ? JSONObject.stringToValue((String) object) : object);
                }
            }
        }
    }

    public static JSONArray toJSONArray(String s) throws JSONException {
        return toJSONArray(new XMLTokener(s));
    }

    public static JSONArray toJSONArray(XMLTokener xmltokener) throws JSONException {
        return (JSONArray) parse(xmltokener, true, (JSONArray) null);
    }

    public static JSONObject toJSONObject(XMLTokener xmltokener) throws JSONException {
        return (JSONObject) parse(xmltokener, false, (JSONArray) null);
    }

    public static JSONObject toJSONObject(String s) throws JSONException {
        return toJSONObject(new XMLTokener(s));
    }

    public static String toString(JSONArray jsonarray) throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();
        String s = jsonarray.getString(0);

        XML.noSpace(s);
        s = XML.escape(s);
        stringbuffer.append('<');
        stringbuffer.append(s);
        Object object = jsonarray.opt(1);
        int i;

        if (object instanceof JSONObject) {
            i = 2;
            JSONObject jsonobject = (JSONObject) object;
            Iterator iterator = jsonobject.keys();

            while (iterator.hasNext()) {
                String s1 = iterator.next().toString();

                XML.noSpace(s1);
                String s2 = jsonobject.optString(s1);

                if (s2 != null) {
                    stringbuffer.append(' ');
                    stringbuffer.append(XML.escape(s1));
                    stringbuffer.append('=');
                    stringbuffer.append('\"');
                    stringbuffer.append(XML.escape(s2));
                    stringbuffer.append('\"');
                }
            }
        } else {
            i = 1;
        }

        int j = jsonarray.length();

        if (i >= j) {
            stringbuffer.append('/');
            stringbuffer.append('>');
        } else {
            stringbuffer.append('>');

            do {
                object = jsonarray.get(i);
                ++i;
                if (object != null) {
                    if (object instanceof String) {
                        stringbuffer.append(XML.escape(object.toString()));
                    } else if (object instanceof JSONObject) {
                        stringbuffer.append(toString((JSONObject) object));
                    } else if (object instanceof JSONArray) {
                        stringbuffer.append(toString((JSONArray) object));
                    }
                }
            } while (i < j);

            stringbuffer.append('<');
            stringbuffer.append('/');
            stringbuffer.append(s);
            stringbuffer.append('>');
        }

        return stringbuffer.toString();
    }

    public static String toString(JSONObject jsonobject) throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();
        String s = jsonobject.optString("tagName");

        if (s == null) {
            return XML.escape(jsonobject.toString());
        } else {
            XML.noSpace(s);
            s = XML.escape(s);
            stringbuffer.append('<');
            stringbuffer.append(s);
            Iterator iterator = jsonobject.keys();

            while (iterator.hasNext()) {
                String s1 = iterator.next().toString();

                if (!s1.equals("tagName") && !s1.equals("childNodes")) {
                    XML.noSpace(s1);
                    String s2 = jsonobject.optString(s1);

                    if (s2 != null) {
                        stringbuffer.append(' ');
                        stringbuffer.append(XML.escape(s1));
                        stringbuffer.append('=');
                        stringbuffer.append('\"');
                        stringbuffer.append(XML.escape(s2));
                        stringbuffer.append('\"');
                    }
                }
            }

            JSONArray jsonarray = jsonobject.optJSONArray("childNodes");

            if (jsonarray == null) {
                stringbuffer.append('/');
                stringbuffer.append('>');
            } else {
                stringbuffer.append('>');
                int i = jsonarray.length();

                for (int j = 0; j < i; ++j) {
                    Object object = jsonarray.get(j);

                    if (object != null) {
                        if (object instanceof String) {
                            stringbuffer.append(XML.escape(object.toString()));
                        } else if (object instanceof JSONObject) {
                            stringbuffer.append(toString((JSONObject) object));
                        } else if (object instanceof JSONArray) {
                            stringbuffer.append(toString((JSONArray) object));
                        }
                    }
                }

                stringbuffer.append('<');
                stringbuffer.append('/');
                stringbuffer.append(s);
                stringbuffer.append('>');
            }

            return stringbuffer.toString();
        }
    }
}
