package jordan.sicherman.json;

public class JSONException extends Exception {

    private Throwable cause;

    public JSONException(String s) {
        super(s);
    }

    public JSONException(Throwable throwable) {
        super(throwable.getMessage());
        this.cause = throwable;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
