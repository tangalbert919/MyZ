package jordan.sicherman.json;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class Test {

    public static void main(String[] astring) {
        class Obj implements JSONString {

            public String aString;
            public double aNumber;
            public boolean aBoolean;

            public Obj(String s, double d0, boolean flag) {
                this.aString = s;
                this.aNumber = d0;
                this.aBoolean = flag;
            }

            public double getNumber() {
                return this.aNumber;
            }

            public String getString() {
                return this.aString;
            }

            public boolean isBoolean() {
                return this.aBoolean;
            }

            public String getBENT() {
                return "All uppercase key";
            }

            public String getX() {
                return "x";
            }

            public String toJSONString() {
                return "{" + JSONObject.quote(this.aString) + ":" + JSONObject.doubleToString(this.aNumber) + "}";
            }

            public String toString() {
                return this.getString() + " " + this.getNumber() + " " + this.isBoolean() + "." + this.getBENT() + " " + this.getX();
            }
        }

        Obj obj = new Obj("A beany object", 42.0D, true);

        try {
            JSONObject jsonobject = XML.toJSONObject("<![CDATA[This is a collection of test patterns and examples for org.json.]]>  Ignore the stuff past the end.  ");

            System.out.println(jsonobject.toString());
            String s = "{     \"list of lists\" : [         [1, 2, 3],         [4, 5, 6],     ] }";

            jsonobject = new JSONObject(s);
            System.out.println(jsonobject.toString(4));
            System.out.println(XML.toString(jsonobject));
            s = "<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ";
            jsonobject = XML.toJSONObject(s);
            System.out.println(jsonobject.toString(4));
            System.out.println();
            jsonobject = JSONML.toJSONObject(s);
            System.out.println(jsonobject.toString());
            System.out.println(JSONML.toString(jsonobject));
            System.out.println();
            JSONArray jsonarray = JSONML.toJSONArray(s);

            System.out.println(jsonarray.toString(4));
            System.out.println(JSONML.toString(jsonarray));
            System.out.println();
            s = "<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between <b>JSON</b> and <b>XML</b> that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>";
            jsonobject = JSONML.toJSONObject(s);
            System.out.println(jsonobject.toString(4));
            System.out.println(JSONML.toString(jsonobject));
            System.out.println();
            jsonarray = JSONML.toJSONArray(s);
            System.out.println(jsonarray.toString(4));
            System.out.println(JSONML.toString(jsonarray));
            System.out.println();
            s = "<person created=\"2006-11-11T19:23\" modified=\"2006-12-31T23:59\">\n <firstName>Robert</firstName>\n <lastName>Smith</lastName>\n <address type=\"home\">\n <street>12345 Sixth Ave</street>\n <city>Anytown</city>\n <state>CA</state>\n <postalCode>98765-4321</postalCode>\n </address>\n </person>";
            jsonobject = XML.toJSONObject(s);
            System.out.println(jsonobject.toString(4));
            jsonobject = new JSONObject(obj);
            System.out.println(jsonobject.toString());
            s = "{ \"entity\": { \"imageURL\": \"\", \"name\": \"IXXXXXXXXXXXXX\", \"id\": 12336, \"ratingCount\": null, \"averageRating\": null } }";
            jsonobject = new JSONObject(s);
            System.out.println(jsonobject.toString(2));
            JSONStringer jsonstringer = new JSONStringer();

            s = jsonstringer.object().key("single").value("MARIE HAA\'S").key("Johnny").value("MARIE HAA\\\'S").key("foo").value("bar").key("baz").array().object().key("quux").value("Thanks, Josh!").endObject().endArray().key("obj keys").value(JSONObject.getNames((Object) obj)).endObject().toString();
            System.out.println(s);
            System.out.println((new JSONStringer()).object().key("a").array().array().array().value("b").endArray().endArray().endArray().endObject().toString());
            jsonstringer = new JSONStringer();
            jsonstringer.array();
            jsonstringer.value(1L);
            jsonstringer.array();
            jsonstringer.value((Object) null);
            jsonstringer.array();
            jsonstringer.object();
            jsonstringer.key("empty-array").array().endArray();
            jsonstringer.key("answer").value(42L);
            jsonstringer.key("null").value((Object) null);
            jsonstringer.key("false").value(false);
            jsonstringer.key("true").value(true);
            jsonstringer.key("big").value(1.23456789E96D);
            jsonstringer.key("small").value(1.23456789E-80D);
            jsonstringer.key("empty-object").object().endObject();
            jsonstringer.key("long");
            jsonstringer.value(Long.MAX_VALUE);
            jsonstringer.endObject();
            jsonstringer.value("two");
            jsonstringer.endArray();
            jsonstringer.value(true);
            jsonstringer.endArray();
            jsonstringer.value(98.6D);
            jsonstringer.value(-100.0D);
            jsonstringer.object();
            jsonstringer.endObject();
            jsonstringer.object();
            jsonstringer.key("one");
            jsonstringer.value(1.0D);
            jsonstringer.endObject();
            jsonstringer.value(obj);
            jsonstringer.endArray();
            System.out.println(jsonstringer.toString());
            System.out.println((new JSONArray(jsonstringer.toString())).toString(4));
            int[] aint = new int[] { 1, 2, 3};
            JSONArray jsonarray1 = new JSONArray(aint);

            System.out.println(jsonarray1.toString());
            String[] astring1 = new String[] { "aString", "aNumber", "aBoolean"};

            jsonobject = new JSONObject(obj, astring1);
            jsonobject.put("Testing JSONString interface", (Object) obj);
            System.out.println(jsonobject.toString(4));
            jsonobject = new JSONObject("{slashes: \'///\', closetag: \'</script>\', backslash:\'\\\\\', ei: {quotes: \'\"\\\'\'},eo: {a: \'\"quoted\"\', b:\"don\'t\"}, quotes: [\"\'\", \'\"\']}");
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonobject = new JSONObject("{foo: [true, false,9876543210,    0.0, 1.00000001,  1.000000000001, 1.00000000000000001, .00000000000000001, 2.00, 0.1, 2e100, -32,[],{}, \"string\"],   to   : null, op : \'Good\',ten:10} postfix comment");
            jsonobject.put("String", (Object) "98.6");
            jsonobject.put("JSONObject", (Object) (new JSONObject()));
            jsonobject.put("JSONArray", (Object) (new JSONArray()));
            jsonobject.put("int", 57);
            jsonobject.put("double", 1.2345678901234568E29D);
            jsonobject.put("true", true);
            jsonobject.put("false", false);
            jsonobject.put("null", JSONObject.NULL);
            jsonobject.put("bool", (Object) "true");
            jsonobject.put("zero", -0.0D);
            jsonobject.put("\\u2028", (Object) "\u2028");
            jsonobject.put("\\u2029", (Object) "\u2029");
            jsonarray = jsonobject.getJSONArray("foo");
            jsonarray.put(666);
            jsonarray.put(2001.99D);
            jsonarray.put((Object) "so \"fine\".");
            jsonarray.put((Object) "so <fine>.");
            jsonarray.put(true);
            jsonarray.put(false);
            jsonarray.put((Object) (new JSONArray()));
            jsonarray.put((Object) (new JSONObject()));
            jsonobject.put("keys", (Object) JSONObject.getNames(jsonobject));
            System.out.println(jsonobject.toString(4));
            System.out.println(XML.toString(jsonobject));
            System.out.println("String: " + jsonobject.getDouble("String"));
            System.out.println("  bool: " + jsonobject.getBoolean("bool"));
            System.out.println("    to: " + jsonobject.getString("to"));
            System.out.println("  true: " + jsonobject.getString("true"));
            System.out.println("   foo: " + jsonobject.getJSONArray("foo"));
            System.out.println("    op: " + jsonobject.getString("op"));
            System.out.println("   ten: " + jsonobject.getInt("ten"));
            System.out.println("  oops: " + jsonobject.optBoolean("oops"));
            s = "<xml one = 1 two=\' \"2\" \'><five></five>First \t&lt;content&gt;<five></five> This is \"content\". <three>  3  </three>JSON does not preserve the sequencing of elements and contents.<three>  III  </three>  <three>  T H R E E</three><four/>Content text is an implied structure in XML. <six content=\"6\"/>JSON does not have implied structure:<seven>7</seven>everything is explicit.<![CDATA[CDATA blocks<are><supported>!]]></xml>";
            jsonobject = XML.toJSONObject(s);
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonarray1 = JSONML.toJSONArray(s);
            System.out.println(jsonarray1.toString(4));
            System.out.println(JSONML.toString(jsonarray1));
            System.out.println("");
            s = "<xml do=\'0\'>uno<a re=\'1\' mi=\'2\'>dos<b fa=\'3\'/>tres<c>true</c>quatro</a>cinqo<d>seis<e/></d></xml>";
            jsonarray1 = JSONML.toJSONArray(s);
            System.out.println(jsonarray1.toString(4));
            System.out.println(JSONML.toString(jsonarray1));
            System.out.println("");
            s = "<mapping><empty/>   <class name = \"Customer\">      <field name = \"ID\" type = \"string\">         <bind-xml name=\"ID\" node=\"attribute\"/>      </field>      <field name = \"FirstName\" type = \"FirstName\"/>      <field name = \"MI\" type = \"MI\"/>      <field name = \"LastName\" type = \"LastName\"/>   </class>   <class name = \"FirstName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"MI\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"LastName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class></mapping>";
            jsonobject = XML.toJSONObject(s);
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonarray1 = JSONML.toJSONArray(s);
            System.out.println(jsonarray1.toString(4));
            System.out.println(JSONML.toString(jsonarray1));
            System.out.println("");
            jsonobject = XML.toJSONObject("<?xml version=\"1.0\" ?><Book Author=\"Anonymous\"><Title>Sample Book</Title><Chapter id=\"1\">This is chapter 1. It is not very long or interesting.</Chapter><Chapter id=\"2\">This is chapter 2. Although it is longer than chapter 1, it is not any more interesting.</Chapter></Book>");
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonobject = XML.toJSONObject("<!DOCTYPE bCard \'http://www.cs.caltech.edu/~adam/schemas/bCard\'><bCard><?xml default bCard        firstname = \'\'        lastname  = \'\' company   = \'\' email = \'\' homepage  = \'\'?><bCard        firstname = \'Rohit\'        lastname  = \'Khare\'        company   = \'MCI\'        email     = \'khare@mci.net\'        homepage  = \'http://pest.w3.org/\'/><bCard        firstname = \'Adam\'        lastname  = \'Rifkin\'        company   = \'Caltech Infospheres Project\'        email     = \'adam@cs.caltech.edu\'        homepage  = \'http://www.cs.caltech.edu/~adam/\'/></bCard>");
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonobject = XML.toJSONObject("<?xml version=\"1.0\"?><customer>    <firstName>        <text>Fred</text>    </firstName>    <ID>fbs0001</ID>    <lastName> <text>Scerbo</text>    </lastName>    <MI>        <text>B</text>    </MI></customer>");
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonobject = XML.toJSONObject("<!ENTITY tp-address PUBLIC \'-//ABC University::Special Collections Library//TEXT (titlepage: name and address)//EN\' \'tpspcoll.sgm\'><list type=\'simple\'><head>Repository Address </head><item>Special Collections Library</item><item>ABC University</item><item>Main Library, 40 Circle Drive</item><item>Ourtown, Pennsylvania</item><item>17654 USA</item></list>");
            System.out.println(jsonobject.toString());
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonobject = XML.toJSONObject("<test intertag status=ok><empty/>deluxe<blip sweet=true>&amp;&quot;toot&quot;&toot;&#x41;</blip><x>eks</x><w>bonus</w><w>bonus2</w></test>");
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonobject = HTTP.toJSONObject("GET / HTTP/1.0\nAccept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*\nAccept-Language: en-us\nUser-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows 98; Win 9x 4.90; T312461; Q312461)\nHost: www.nokko.com\nConnection: keep-alive\nAccept-encoding: gzip, deflate\n");
            System.out.println(jsonobject.toString(2));
            System.out.println(HTTP.toString(jsonobject));
            System.out.println("");
            jsonobject = HTTP.toJSONObject("HTTP/1.1 200 Oki Doki\nDate: Sun, 26 May 2002 17:38:52 GMT\nServer: Apache/1.3.23 (Unix) mod_perl/1.26\nKeep-Alive: timeout=15, max=100\nConnection: Keep-Alive\nTransfer-Encoding: chunked\nContent-Type: text/html\n");
            System.out.println(jsonobject.toString(2));
            System.out.println(HTTP.toString(jsonobject));
            System.out.println("");
            jsonobject = new JSONObject("{nix: null, nux: false, null: \'null\', \'Request-URI\': \'/\', Method: \'GET\', \'HTTP-Version\': \'HTTP/1.0\'}");
            System.out.println(jsonobject.toString(2));
            System.out.println("isNull: " + jsonobject.isNull("nix"));
            System.out.println("   has: " + jsonobject.has("nix"));
            System.out.println(XML.toString(jsonobject));
            System.out.println(HTTP.toString(jsonobject));
            System.out.println("");
            jsonobject = XML.toJSONObject("<?xml version=\'1.0\' encoding=\'UTF-8\'?>\n\n<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"><SOAP-ENV:Body><ns1:doGoogleSearch xmlns:ns1=\"urn:GoogleSearch\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><key xsi:type=\"xsd:string\">GOOGLEKEY</key> <q xsi:type=\"xsd:string\">\'+search+\'</q> <start xsi:type=\"xsd:int\">0</start> <maxResults xsi:type=\"xsd:int\">10</maxResults> <filter xsi:type=\"xsd:boolean\">true</filter> <restrict xsi:type=\"xsd:string\"></restrict> <safeSearch xsi:type=\"xsd:boolean\">false</safeSearch> <lr xsi:type=\"xsd:string\"></lr> <ie xsi:type=\"xsd:string\">latin1</ie> <oe xsi:type=\"xsd:string\">latin1</oe></ns1:doGoogleSearch></SOAP-ENV:Body></SOAP-ENV:Envelope>");
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonobject = new JSONObject("{Envelope: {Body: {\"ns1:doGoogleSearch\": {oe: \"latin1\", filter: true, q: \"\'+search+\'\", key: \"GOOGLEKEY\", maxResults: 10, \"SOAP-ENV:encodingStyle\": \"http://schemas.xmlsoap.org/soap/encoding/\", start: 0, ie: \"latin1\", safeSearch:false, \"xmlns:ns1\": \"urn:GoogleSearch\"}}}}");
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonobject = CookieList.toJSONObject("  f%oo = b+l=ah  ; o;n%40e = t.wo ");
            System.out.println(jsonobject.toString(2));
            System.out.println(CookieList.toString(jsonobject));
            System.out.println("");
            jsonobject = Cookie.toJSONObject("f%oo=blah; secure ;expires = April 24, 2002");
            System.out.println(jsonobject.toString(2));
            System.out.println(Cookie.toString(jsonobject));
            System.out.println("");
            jsonobject = new JSONObject("{script: \'It is not allowed in HTML to send a close script tag in a string<script>because it confuses browsers</script>so we insert a backslash before the /\'}");
            System.out.println(jsonobject.toString());
            System.out.println("");
            JSONTokener jsontokener = new JSONTokener("{op:\'test\', to:\'session\', pre:1}{op:\'test\', to:\'session\', pre:2}");

            jsonobject = new JSONObject(jsontokener);
            System.out.println(jsonobject.toString());
            System.out.println("pre: " + jsonobject.optInt("pre"));
            char c0 = jsontokener.skipTo('{');

            System.out.println(c0);
            jsonobject = new JSONObject(jsontokener);
            System.out.println(jsonobject.toString());
            System.out.println("");
            jsonarray = CDL.toJSONArray("No quotes, \'Single Quotes\', \"Double Quotes\"\n1,\'2\',\"3\"\n,\'It is \"good,\"\', \"It works.\"\n\n");
            System.out.println(CDL.toString(jsonarray));
            System.out.println("");
            System.out.println(jsonarray.toString(4));
            System.out.println("");
            jsonarray = new JSONArray(" [\"<escape>\", next is an implied null , , ok,] ");
            System.out.println(jsonarray.toString());
            System.out.println("");
            System.out.println(XML.toString(jsonarray));
            System.out.println("");
            jsonobject = new JSONObject("{ fun => with non-standard forms ; forgiving => This package can be used to parse formats that are similar to but not stricting conforming to JSON; why=To make it easier to migrate existing data to JSON,one = [[1.00]]; uno=[[{1=>1}]];\'+\':+6e66 ;pluses=+++;empty = \'\' , \'double\':0.666,true: TRUE, false: FALSE, null=NULL;[true] = [[!,@;*]]; string=>  o. k. ; \r oct=0666; hex=0x666; dec=666; o=0999; noh=0x0x}");
            System.out.println(jsonobject.toString(4));
            System.out.println("");
            if (jsonobject.getBoolean("true") && !jsonobject.getBoolean("false")) {
                System.out.println("It\'s all good");
            }

            System.out.println("");
            jsonobject = new JSONObject(jsonobject, new String[] { "dec", "oct", "hex", "missing"});
            System.out.println(jsonobject.toString(4));
            System.out.println("");
            System.out.println((new JSONStringer()).array().value(jsonarray).value(jsonobject).endArray());
            jsonobject = new JSONObject("{string: \"98.6\", long: 2147483648, int: 2147483647, longer: 9223372036854775807, double: 9223372036854775808}");
            System.out.println(jsonobject.toString(4));
            System.out.println("\ngetInt");
            System.out.println("int    " + jsonobject.getInt("int"));
            System.out.println("long   " + jsonobject.getInt("long"));
            System.out.println("longer " + jsonobject.getInt("longer"));
            System.out.println("double " + jsonobject.getInt("double"));
            System.out.println("string " + jsonobject.getInt("string"));
            System.out.println("\ngetLong");
            System.out.println("int    " + jsonobject.getLong("int"));
            System.out.println("long   " + jsonobject.getLong("long"));
            System.out.println("longer " + jsonobject.getLong("longer"));
            System.out.println("double " + jsonobject.getLong("double"));
            System.out.println("string " + jsonobject.getLong("string"));
            System.out.println("\ngetDouble");
            System.out.println("int    " + jsonobject.getDouble("int"));
            System.out.println("long   " + jsonobject.getDouble("long"));
            System.out.println("longer " + jsonobject.getDouble("longer"));
            System.out.println("double " + jsonobject.getDouble("double"));
            System.out.println("string " + jsonobject.getDouble("string"));
            jsonobject.put("good sized", Long.MAX_VALUE);
            System.out.println(jsonobject.toString(4));
            jsonarray = new JSONArray("[2147483647, 2147483648, 9223372036854775807, 9223372036854775808]");
            System.out.println(jsonarray.toString(4));
            System.out.println("\nKeys: ");
            Iterator iterator = jsonobject.keys();

            while (iterator.hasNext()) {
                s = (String) iterator.next();
                System.out.println(s + ": " + jsonobject.getString(s));
            }

            System.out.println("\naccumulate: ");
            jsonobject = new JSONObject();
            jsonobject.accumulate("stooge", "Curly");
            jsonobject.accumulate("stooge", "Larry");
            jsonobject.accumulate("stooge", "Moe");
            jsonarray = jsonobject.getJSONArray("stooge");
            jsonarray.put(5, (Object) "Shemp");
            System.out.println(jsonobject.toString(4));
            System.out.println("\nwrite:");
            System.out.println(jsonobject.write(new StringWriter()));
            s = "<xml empty><a></a><a>1</a><a>22</a><a>333</a></xml>";
            jsonobject = XML.toJSONObject(s);
            System.out.println(jsonobject.toString(4));
            System.out.println(XML.toString(jsonobject));
            s = "<book><chapter>Content of the first chapter</chapter><chapter>Content of the second chapter      <chapter>Content of the first subchapter</chapter>      <chapter>Content of the second subchapter</chapter></chapter><chapter>Third Chapter</chapter></book>";
            jsonobject = XML.toJSONObject(s);
            System.out.println(jsonobject.toString(4));
            System.out.println(XML.toString(jsonobject));
            jsonarray = JSONML.toJSONArray(s);
            System.out.println(jsonarray.toString(4));
            System.out.println(JSONML.toString(jsonarray));
            Object object = null;
            Object object1 = null;

            jsonobject = new JSONObject((Map) object1);
            jsonarray = new JSONArray((Collection) object);
            jsonobject.append("stooge", "Joe DeRita");
            jsonobject.append("stooge", "Shemp");
            jsonobject.accumulate("stooges", "Curly");
            jsonobject.accumulate("stooges", "Larry");
            jsonobject.accumulate("stooges", "Moe");
            jsonobject.accumulate("stoogearray", jsonobject.get("stooges"));
            jsonobject.put("map", (Map) object1);
            jsonobject.put("collection", (Collection) object);
            jsonobject.put("array", (Object) jsonarray);
            jsonarray.put((Map) object1);
            jsonarray.put((Collection) object);
            System.out.println(jsonobject.toString(4));
            s = "{plist=Apple; AnimalSmells = { pig = piggish; lamb = lambish; worm = wormy; }; AnimalSounds = { pig = oink; lamb = baa; worm = baa;  Lisa = \"Why is the worm talking like a lamb?\" } ; AnimalColors = { pig = pink; lamb = black; worm = pink; } } ";
            jsonobject = new JSONObject(s);
            System.out.println(jsonobject.toString(4));
            s = " (\"San Francisco\", \"New York\", \"Seoul\", \"London\", \"Seattle\", \"Shanghai\")";
            jsonarray = new JSONArray(s);
            System.out.println(jsonarray.toString());
            s = "<a ichi=\'1\' ni=\'2\'><b>The content of b</b> and <c san=\'3\'>The content of c</c><d>do</d><e></e><d>re</d><f/><d>mi</d></a>";
            jsonobject = XML.toJSONObject(s);
            System.out.println(jsonobject.toString(2));
            System.out.println(XML.toString(jsonobject));
            System.out.println("");
            jsonarray1 = JSONML.toJSONArray(s);
            System.out.println(jsonarray1.toString(4));
            System.out.println(JSONML.toString(jsonarray1));
            System.out.println("");
            System.out.println("\nTesting Exceptions: ");
            System.out.print("Exception: ");

            try {
                jsonarray = new JSONArray();
                jsonarray.put(Double.NEGATIVE_INFINITY);
                jsonarray.put(Double.NaN);
                System.out.println(jsonarray.toString());
            } catch (Exception exception) {
                System.out.println(exception);
            }

            System.out.print("Exception: ");

            try {
                System.out.println(jsonobject.getDouble("stooge"));
            } catch (Exception exception1) {
                System.out.println(exception1);
            }

            System.out.print("Exception: ");

            try {
                System.out.println(jsonobject.getDouble("howard"));
            } catch (Exception exception2) {
                System.out.println(exception2);
            }

            System.out.print("Exception: ");

            try {
                System.out.println(jsonobject.put((String) null, (Object) "howard"));
            } catch (Exception exception3) {
                System.out.println(exception3);
            }

            System.out.print("Exception: ");

            try {
                System.out.println(jsonarray.getDouble(0));
            } catch (Exception exception4) {
                System.out.println(exception4);
            }

            System.out.print("Exception: ");

            try {
                System.out.println(jsonarray.get(-1));
            } catch (Exception exception5) {
                System.out.println(exception5);
            }

            System.out.print("Exception: ");

            try {
                System.out.println(jsonarray.put(Double.NaN));
            } catch (Exception exception6) {
                System.out.println(exception6);
            }

            System.out.print("Exception: ");

            try {
                jsonobject = XML.toJSONObject("<a><b>    ");
            } catch (Exception exception7) {
                System.out.println(exception7);
            }

            System.out.print("Exception: ");

            try {
                jsonobject = XML.toJSONObject("<a></b>    ");
            } catch (Exception exception8) {
                System.out.println(exception8);
            }

            System.out.print("Exception: ");

            try {
                jsonobject = XML.toJSONObject("<a></a    ");
            } catch (Exception exception9) {
                System.out.println(exception9);
            }

            System.out.print("Exception: ");

            try {
                jsonarray1 = new JSONArray(new Object());
                System.out.println(jsonarray1.toString());
            } catch (Exception exception10) {
                System.out.println(exception10);
            }

            System.out.print("Exception: ");

            try {
                s = "[)";
                jsonarray = new JSONArray(s);
                System.out.println(jsonarray.toString());
            } catch (Exception exception11) {
                System.out.println(exception11);
            }

            System.out.print("Exception: ");

            try {
                s = "<xml";
                jsonarray1 = JSONML.toJSONArray(s);
                System.out.println(jsonarray1.toString(4));
            } catch (Exception exception12) {
                System.out.println(exception12);
            }

            System.out.print("Exception: ");

            try {
                s = "<right></wrong>";
                jsonarray1 = JSONML.toJSONArray(s);
                System.out.println(jsonarray1.toString(4));
            } catch (Exception exception13) {
                System.out.println(exception13);
            }

            System.out.print("Exception: ");

            try {
                s = "{\"koda\": true, \"koda\": true}";
                jsonobject = new JSONObject(s);
                System.out.println(jsonobject.toString(4));
            } catch (Exception exception14) {
                System.out.println(exception14);
            }

            System.out.print("Exception: ");

            try {
                jsonstringer = new JSONStringer();
                s = jsonstringer.object().key("bosanda").value("MARIE HAA\'S").key("bosanda").value("MARIE HAA\\\'S").endObject().toString();
                System.out.println(jsonobject.toString(4));
            } catch (Exception exception15) {
                System.out.println(exception15);
            }
        } catch (Exception exception16) {
            System.out.println(exception16.toString());
        }

    }
}
