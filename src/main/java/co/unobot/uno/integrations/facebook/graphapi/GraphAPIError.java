package co.unobot.uno.integrations.facebook.graphapi;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 16/04/17.
 */
public class GraphAPIError extends Exception {
    private String message;
    private String type;
    private int code;
    @JsonProperty("sub_code")
    private int subCode;
    @JsonProperty("fbtrace_id")
    private String fbTraceId;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSubCode() {
        return subCode;
    }

    public void setSubCode(int subCode) {
        this.subCode = subCode;
    }

    public String getFbTraceId() {
        return fbTraceId;
    }

    public void setFbTraceId(String fbTraceId) {
        this.fbTraceId = fbTraceId;
    }
}
