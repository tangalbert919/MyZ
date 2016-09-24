package jordan.sicherman.json;

import java.util.Iterator;

public class XML {

    public static final Character AMP = new Character('&');
    public static final Character APOS = new Character('\'');
    public static final Character BANG = new Character('!');
    public static final Character EQ = new Character('=');
    public static final Character GT = new Character('>');
    public static final Character LT = new Character('<');
    public static final Character QUEST = new Character('?');
    public static final Character QUOT = new Character('\"');
    public static final Character SLASH = new Character('/');

    public static String escape(String s) {
        StringBuffer stringbuffer = new StringBuffer();
        int i = 0;

        for (int j = s.length(); i < j; ++i) {
            char c0 = s.charAt(i);

            switch (c0) {
            case '\"':
                stringbuffer.append("&quot;");
                break;

            case '&':
                stringbuffer.append("&amp;");
                break;

            case '<':
                stringbuffer.append("&lt;");
                break;

            case '>':
                stringbuffer.append("&gt;");
                break;

            default:
                stringbuffer.append(c0);
            }
        }

        return stringbuffer.toString();
    }

    public static void noSpace(String s) throws JSONException {
        int i = s.length();

        if (i == 0) {
            throw new JSONException("Empty string.");
        } else {
            for (int j = 0; j < i; ++j) {
                if (Character.isWhitespace(s.charAt(j))) {
                    throw new JSONException("\'" + s + "\' contains a space character.");
                }
            }

        }
    }

    private static boolean parse(XMLTokener xmltokener, JSONObject jsonobject, String s) throws JSONException {
        JSONObject jsonobject1 = null;
        Object object = xmltokener.nextToken();
        String s1;

        if (object == XML.BANG) {
            char c0 = xmltokener.next();

            if (c0 == 45) {
                if (xmltokener.next() == 45) {
                    xmltokener.skipPast("-->");
                    return false;
                }

                xmltokener.back();
            } else if (c0 == 91) {
                object = xmltokener.nextToken();
                if (object.equals("CDATA") && xmltokener.next() == 91) {
                    s1 = xmltokener.nextCDATA();
                    if (s1.length() > 0) {
                        jsonobject.accumulate("content", s1);
                    }

                    return false;
                }

                throw xmltokener.syntaxError("Expected \'CDATA[\'");
            }

            int i = 1;

            do {
                object = xmltokener.nextMeta();
                if (object == null) {
                    throw xmltokener.syntaxError("Missing \'>\' after \'<!\'.");
                }

                if (object == XML.LT) {
                    ++i;
                } else if (object == XML.GT) {
                    --i;
                }
            } while (i > 0);

            return false;
        } else if (object == XML.QUEST) {
            xmltokener.skipPast("?>");
            return false;
        } else if (object == XML.SLASH) {
            object = xmltokener.nextToken();
            if (s == null) {
                throw xmltokener.syntaxError("Mismatched close tag" + object);
            } else if (!object.equals(s)) {
                throw xmltokener.syntaxError("Mismatched " + s + " and " + object);
            } else if (xmltokener.nextToken() != XML.GT) {
                throw xmltokener.syntaxError("Misshaped close tag");
            } else {
                return true;
            }
        } else if (object instanceof Character) {
            throw xmltokener.syntaxError("Misshaped tag");
        } else {
            String s2 = (String) object;

            object = null;
            jsonobject1 = new JSONObject();

            while (true) {
                if (object == null) {
                    object = xmltokener.nextToken();
                }

                if (!(object instanceof String)) {
                    if (object == XML.SLASH) {
                        if (xmltokener.nextToken() != XML.GT) {
                            throw xmltokener.syntaxError("Misshaped tag");
                        }

                        jsonobject.accumulate(s2, jsonobject1);
                        return false;
                    }

                    if (object != XML.GT) {
                        throw xmltokener.syntaxError("Misshaped tag");
                    }

                    while (true) {
                        object = xmltokener.nextContent();
                        if (object == null) {
                            if (s2 != null) {
                                throw xmltokener.syntaxError("Unclosed tag " + s2);
                            }

                            return false;
                        }

                        if (object instanceof String) {
                            s1 = (String) object;
                            if (s1.length() > 0) {
                                jsonobject1.accumulate("content", JSONObject.stringToValue(s1));
                            }
                        } else if (object == XML.LT && parse(xmltokener, jsonobject1, s2)) {
                            if (jsonobject1.length() == 0) {
                                jsonobject.accumulate(s2, "");
                            } else if (jsonobject1.length() == 1 && jsonobject1.opt("content") != null) {
                                jsonobject.accumulate(s2, jsonobject1.opt("content"));
                            } else {
                                jsonobject.accumulate(s2, jsonobject1);
                            }

                            return false;
                        }
                    }
                }

                s1 = (String) object;
                object = xmltokener.nextToken();
                if (object == XML.EQ) {
                    object = xmltokener.nextToken();
                    if (!(object instanceof String)) {
                        throw xmltokener.syntaxError("Missing value");
                    }

                    jsonobject1.accumulate(s1, JSONObject.stringToValue((String) object));
                    object = null;
                } else {
                    jsonobject1.accumulate(s1, "");
                }
            }
        }
    }

    public static JSONObject toJSONObject(String s) throws JSONException {
        JSONObject jsonobject = new JSONObject();
        XMLTokener xmltokener = new XMLTokener(s);

        while (xmltokener.more() && xmltokener.skipPast("<")) {
            parse(xmltokener, jsonobject, (String) null);
        }

        return jsonobject;
    }

    public static String toString(Object object) throws JSONException {
        return toString(object, (String) null);
    }

    public static String toString(Object object, String s) throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();
        Object object1;
        String s1;
        JSONArray jsonarray;
        int i;
        int j;

        if (!(object instanceof JSONObject)) {
            if (object instanceof JSONArray) {
                jsonarray = (JSONArray) object;
                i = jsonarray.length();

                for (j = 0; j < i; ++j) {
                    object1 = jsonarray.opt(j);
                    stringbuffer.append(toString(object1, s == null ? "array" : s));
                }

                return stringbuffer.toString();
            } else {
                s1 = object == null ? "null" : escape(object.toString());
                return s == null ? "\"" + s1 + "\"" : (s1.length() == 0 ? "<" + s + "/>" : "<" + s + ">" + s1 + "</" + s + ">");
            }
        } else {
            if (s != null) {
                stringbuffer.append('<');
                stringbuffer.append(s);
                stringbuffer.append('>');
            }

            JSONObject jsonobject = (JSONObject) object;
            Iterator iterator = jsonobject.keys();

            while (iterator.hasNext()) {
                String s2 = iterator.next().toString();

                object1 = jsonobject.opt(s2);
                if (object1 == null) {
                    object1 = "";
                }

                if (object1 instanceof String) {
                    s1 = (String) object1;
                } else {
                    s1 = null;
                }

                if (s2.equals("content")) {
                    if (object1 instanceof JSONArray) {
                        jsonarray = (JSONArray) object1;
                        i = jsonarray.length();

                        for (j = 0; j < i; ++j) {
                            if (j > 0) {
                                stringbuffer.append('\n');
                            }

                            stringbuffer.append(escape(jsonarray.get(j).toString()));
                        }
                    } else {
                        stringbuffer.append(escape(object1.toString()));
                    }
                } else if (object1 instanceof JSONArray) {
                    jsonarray = (JSONArray) object1;
                    i = jsonarray.length();

                    for (j = 0; j < i; ++j) {
                        object1 = jsonarray.get(j);
                        if (object1 instanceof JSONArray) {
                            stringbuffer.append('<');
                            stringbuffer.append(s2);
                            stringbuffer.append('>');
                            stringbuffer.append(toString(object1));
                            stringbuffer.append("</");
                            stringbuffer.append(s2);
                            stringbuffer.append('>');
                        } else {
                            stringbuffer.append(toString(object1, s2));
                        }
                    }
                } else if (object1.equals("")) {
                    stringbuffer.append('<');
                    stringbuffer.append(s2);
                    stringbuffer.append("/>");
                } else {
                    stringbuffer.append(toString(object1, s2));
                }
            }

            if (s != null) {
                stringbuffer.append("</");
                stringbuffer.append(s);
                stringbuffer.append('>');
            }

            return stringbuffer.toString();
        }
    }
}
