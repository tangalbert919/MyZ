package jordan.sicherman.jnbt;

public final class EndTag extends Tag {

    public EndTag() {
        super("");
    }

    public Object getValue() {
        return null;
    }

    public String toString() {
        return "TAG_End";
    }
}
