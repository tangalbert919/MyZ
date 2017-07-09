package jordan.sicherman.jnbt;

public final class ShortTag extends Tag {

    private final short value;

    public ShortTag(String name, short value) {
        super(name);
        this.value = value;
    }

    public Short getValue() {
        return Short.valueOf(this.value);
    }

    public String toString() {
        String name = this.getName();
        String append = "";

        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }

        return "TAG_Short" + append + ": " + this.value;
    }
}
