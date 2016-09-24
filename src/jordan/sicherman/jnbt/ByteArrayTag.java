package jordan.sicherman.jnbt;

public final class ByteArrayTag extends Tag {

    private final byte[] value;

    public ByteArrayTag(String name, byte[] value) {
        super(name);
        this.value = value;
    }

    public byte[] getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder hex = new StringBuilder();
        byte[] abyte = this.value;
        int i = this.value.length;

        for (int append = 0; append < i; ++append) {
            byte name = abyte[append];
            String hexDigits = Integer.toHexString(name).toUpperCase();

            if (hexDigits.length() == 1) {
                hex.append("0");
            }

            hex.append(hexDigits).append(" ");
        }

        String s = this.getName();
        String s1 = "";

        if (s != null && !s.equals("")) {
            s1 = "(\"" + this.getName() + "\")";
        }

        return "TAG_Byte_Array" + s1 + ": " + hex.toString();
    }
}
