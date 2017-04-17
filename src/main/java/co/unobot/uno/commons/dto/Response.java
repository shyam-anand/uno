package co.unobot.uno.commons.dto;

/**
 * Created by shyam on 16/04/17.
 */
public class Response {

    private boolean success;
    private Object data;

    public Response(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
