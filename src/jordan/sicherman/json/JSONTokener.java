package jordan.sicherman.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JSONTokener {

    private int index;
    private Reader reader;
    private char lastChar;
    private boolean useLastChar;

    public JSONTokener(Reader reader) {
        this.reader = (Reader) (reader.markSupported() ? reader : new BufferedReader(reader));
        this.useLastChar = false;
        this.index = 0;
    }

    public JSONTokener(String s) {
        this((Reader) (new StringReader(s)));
    }

    public void back() throws JSONException {
        if (!this.useLastChar && this.index > 0) {
            --this.index;
            this.useLastChar = true;
        } else {
            throw new JSONException("Stepping back two steps is not supported");
        }
    }

    public static int dehexchar(char c0) {
        return c0 >= 48 && c0 <= 57 ? c0 - 48 : (c0 >= 65 && c0 <= 70 ? c0 - 55 : (c0 >= 97 && c0 <= 102 ? c0 - 87 : -1));
    }

    public boolean more() throws JSONException {
        char c0 = this.next();

        if (c0 == 0) {
            return false;
        } else {
            this.back();
            return true;
        }
    }

    public char next() throws JSONException {
        if (this.useLastChar) {
            this.useLastChar = false;
            if (this.lastChar != 0) {
                ++this.index;
            }

            return this.lastChar;
        } else {
            int i;

            try {
                i = this.reader.read();
            } catch (IOException ioexception) {
                throw new JSONException(ioexception);
            }

            if (i <= 0) {
                this.lastChar = 0;
                return '\u0000';
            } else {
                ++this.index;
                this.lastChar = (char) i;
                return this.lastChar;
            }
        }
    }

    public char next(char c0) throws JSONException {
        char c1 = this.next();

        if (c1 != c0) {
            throw this.syntaxError("Expected \'" + c0 + "\' and instead saw \'" + c1 + "\'");
        } else {
            return c1;
        }
    }

    public String next(int i) throws JSONException {
        if (i == 0) {
            return "";
        } else {
            char[] achar = new char[i];
            int j = 0;

            if (this.useLastChar) {
                this.useLastChar = false;
                achar[0] = this.lastChar;
                j = 1;
            }

            int k;

            try {
                while (j < i && (k = this.reader.read(achar, j, i - j)) != -1) {
                    j += k;
                }
            } catch (IOException ioexception) {
                throw new JSONException(ioexception);
            }

            this.index += j;
            if (j < i) {
                throw this.syntaxError("Substring bounds error");
            } else {
                this.lastChar = achar[i - 1];
                return new String(achar);
            }
        }
    }

    public char nextClean() throws JSONException {
        char c0;

        do {
            c0 = this.next();
        } while (c0 != 0 && c0 <= 32);

        return c0;
    }

    public String nextString(char c0) throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();

        while (true) {
            char c1 = this.next();

            switch (c1) {
            case '\u0000':
            case '\n':
            case '\r':
                throw this.syntaxError("Unterminated string");

            case '\\':
                c1 = this.next();
                switch (c1) {
                case 'b':
                    stringbuffer.append('\b');
                    continue;

                case 'c':
                case 'd':
                case 'e':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'o':
                case 'p':
                case 'q':
                case 's':
                case 'v':
                case 'w':
                default:
                    stringbuffer.append(c1);
                    continue;

                case 'f':
                    stringbuffer.append('\f');
                    continue;

                case 'n':
                    stringbuffer.append('\n');
                    continue;

                case 'r':
                    stringbuffer.append('\r');
                    continue;

                case 't':
                    stringbuffer.append('\t');
                    continue;

                case 'u':
                    stringbuffer.append((char) Integer.parseInt(this.next((int) 4), 16));
                    continue;

                case 'x':
                    stringbuffer.append((char) Integer.parseInt(this.next((int) 2), 16));
                    continue;
                }

            default:
                if (c1 == c0) {
                    return stringbuffer.toString();
                }

                stringbuffer.append(c1);
            }
        }
    }

    public String nextTo(char c0) throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();

        while (true) {
            char c1 = this.next();

            if (c1 == c0 || c1 == 0 || c1 == 10 || c1 == 13) {
                if (c1 != 0) {
                    this.back();
                }

                return stringbuffer.toString().trim();
            }

            stringbuffer.append(c1);
        }
    }

    public String nextTo(String s) throws JSONException {
        StringBuffer stringbuffer = new StringBuffer();

        while (true) {
            char c0 = this.next();

            if (s.indexOf(c0) >= 0 || c0 == 0 || c0 == 10 || c0 == 13) {
                if (c0 != 0) {
                    this.back();
                }

                return stringbuffer.toString().trim();
            }

            stringbuffer.append(c0);
        }
    }

    public Object nextValue() throws JSONException {
        char c0 = this.nextClean();

        switch (c0) {
        case '\"':
        case '\'':
            return this.nextString(c0);

        case '(':
        case '[':
            this.back();
            return new JSONArray(this);

        case '{':
            this.back();
            return new JSONObject(this);

        default:
            StringBuffer stringbuffer;

            for (stringbuffer = new StringBuffer(); c0 >= 32 && ",:]}/\\\"[{;=#".indexOf(c0) < 0; c0 = this.next()) {
                stringbuffer.append(c0);
            }

            this.back();
            String s = stringbuffer.toString().trim();

            if (s.equals("")) {
                throw this.syntaxError("Missing value");
            } else {
                return JSONObject.stringToValue(s);
            }
        }
    }

    public char skipTo(char c0) throws JSONException {
        char c1;

        try {
            int i = this.index;

            this.reader.mark(Integer.MAX_VALUE);

            do {
                c1 = this.next();
                if (c1 == 0) {
                    this.reader.reset();
                    this.index = i;
                    return c1;
                }
            } while (c1 != c0);
        } catch (IOException ioexception) {
            throw new JSONException(ioexception);
        }

        this.back();
        return c1;
    }

    public JSONException syntaxError(String s) {
        return new JSONException(s + this.toString());
    }

    public String toString() {
        return " at character " + this.index;
    }
}
