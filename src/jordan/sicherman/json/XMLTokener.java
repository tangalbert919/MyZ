package jordan.sicherman.json;

import java.util.HashMap;

public class XMLTokener extends JSONTokener {

    public static final HashMap entity = new HashMap(8);

    public XMLTokener(String s) {
        super(s);
    }

    public String nextCDATA() throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();

        int i;

        do {
            char c0 = this.next();

            if (c0 == 0) {
                throw this.syntaxError("Unclosed CDATA");
            }

            stringbuffer.append(c0);
            i = stringbuffer.length() - 3;
        } while (i < 0 || stringbuffer.charAt(i) != 93 || stringbuffer.charAt(i + 1) != 93 || stringbuffer.charAt(i + 2) != 62);

        stringbuffer.setLength(i);
        return stringbuffer.toString();
    }

    public Object nextContent() throws JSONException {
        char c0;

        do {
            c0 = this.next();
        } while (Character.isWhitespace(c0));

        if (c0 == 0) {
            return null;
        } else if (c0 == 60) {
            return XML.LT;
        } else {
            StringBuffer stringbuffer;

            for (stringbuffer = new StringBuffer(); c0 != 60 && c0 != 0; c0 = this.next()) {
                if (c0 == 38) {
                    stringbuffer.append(this.nextEntity(c0));
                } else {
                    stringbuffer.append(c0);
                }
            }

            this.back();
            return stringbuffer.toString().trim();
        }
    }

    public Object nextEntity(char c0) throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();

        while (true) {
            char c1 = this.next();

            if (!Character.isLetterOrDigit(c1) && c1 != 35) {
                if (c1 == 59) {
                    String s = stringbuffer.toString();
                    Object object = XMLTokener.entity.get(s);

                    return object != null ? object : c0 + s + ";";
                }

                throw this.syntaxError("Missing \';\' in XML entity: &" + stringbuffer);
            }

            stringbuffer.append(Character.toLowerCase(c1));
        }
    }

    public Object nextMeta() throws JSONException {
        char c0;

        do {
            c0 = this.next();
        } while (Character.isWhitespace(c0));

        switch (c0) {
        case '\u0000':
            throw this.syntaxError("Misshaped meta tag");

        case '!':
            return XML.BANG;

        case '\"':
        case '\'':
            char c1 = c0;

            do {
                c0 = this.next();
                if (c0 == 0) {
                    throw this.syntaxError("Unterminated string");
                }
            } while (c0 != c1);

            return Boolean.TRUE;

        case '/':
            return XML.SLASH;

        case '<':
            return XML.LT;

        case '=':
            return XML.EQ;

        case '>':
            return XML.GT;

        case '?':
            return XML.QUEST;

        default:
            while (true) {
                c0 = this.next();
                if (Character.isWhitespace(c0)) {
                    return Boolean.TRUE;
                }

                switch (c0) {
                case '\u0000':
                case '!':
                case '\"':
                case '\'':
                case '/':
                case '<':
                case '=':
                case '>':
                case '?':
                    this.back();
                    return Boolean.TRUE;
                }
            }
        }
    }

    public Object nextToken() throws JSONException {
        char c0;

        do {
            c0 = this.next();
        } while (Character.isWhitespace(c0));

        StringBuffer stringbuffer;

        switch (c0) {
        case '\u0000':
            throw this.syntaxError("Misshaped element");

        case '!':
            return XML.BANG;

        case '\"':
        case '\'':
            char c1 = c0;

            stringbuffer = new StringBuffer();

            while (true) {
                c0 = this.next();
                if (c0 == 0) {
                    throw this.syntaxError("Unterminated string");
                }

                if (c0 == c1) {
                    return stringbuffer.toString();
                }

                if (c0 == 38) {
                    stringbuffer.append(this.nextEntity(c0));
                } else {
                    stringbuffer.append(c0);
                }
            }

        case '/':
            return XML.SLASH;

        case '<':
            throw this.syntaxError("Misplaced \'<\'");

        case '=':
            return XML.EQ;

        case '>':
            return XML.GT;

        case '?':
            return XML.QUEST;

        default:
            stringbuffer = new StringBuffer();

            while (true) {
                stringbuffer.append(c0);
                c0 = this.next();
                if (Character.isWhitespace(c0)) {
                    return stringbuffer.toString();
                }

                switch (c0) {
                case '\u0000':
                    return stringbuffer.toString();

                case '!':
                case '/':
                case '=':
                case '>':
                case '?':
                case '[':
                case ']':
                    this.back();
                    return stringbuffer.toString();

                case '\"':
                case '\'':
                case '<':
                    throw this.syntaxError("Bad character in a name");
                }
            }
        }
    }

    public boolean skipPast(String s) throws JSONException {
        int i = 0;
        int j = s.length();
        char[] achar = new char[j];

        int k;
        char c0;

        for (k = 0; k < j; ++k) {
            c0 = this.next();
            if (c0 == 0) {
                return false;
            }

            achar[k] = c0;
        }

        while (true) {
            int l = i;
            boolean flag = true;

            k = 0;

            while (true) {
                if (k < j) {
                    if (achar[l] == s.charAt(k)) {
                        ++l;
                        if (l >= j) {
                            l -= j;
                        }

                        ++k;
                        continue;
                    }

                    flag = false;
                }

                if (flag) {
                    return true;
                }

                c0 = this.next();
                if (c0 == 0) {
                    return false;
                }

                achar[i] = c0;
                ++i;
                if (i >= j) {
                    i -= j;
                }
                break;
            }
        }
    }

    static {
        XMLTokener.entity.put("amp", XML.AMP);
        XMLTokener.entity.put("apos", XML.APOS);
        XMLTokener.entity.put("gt", XML.GT);
        XMLTokener.entity.put("lt", XML.LT);
        XMLTokener.entity.put("quot", XML.QUOT);
    }
}
