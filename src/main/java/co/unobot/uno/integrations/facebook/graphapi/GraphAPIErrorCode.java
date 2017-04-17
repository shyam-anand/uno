package co.unobot.uno.integrations.facebook.graphapi;

/**
 * Created by shyam on 17/04/17.
 */
public enum GraphAPIErrorCode {

    CODE_INVALID_ACCESS_TOKEN(190),
    SUBCODE_SESSION_EXPIRED(463);

    private int code;

    GraphAPIErrorCode(int c) {
        this.code = c;
    }

    public int code() {
        return code;
    }

    @Override
    public String toString() {
        switch (this) {
            case CODE_INVALID_ACCESS_TOKEN:
                return "Invalid Access Token";
            case SUBCODE_SESSION_EXPIRED:
                return "Session expired";
            default:
                return "undefined";
        }
    }
}
