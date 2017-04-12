package co.unobot.uno.integrations.facebook.graphapi;

import org.springframework.http.HttpStatus;

/**
 * Created by shyam on 07/04/17.
 */
public class GraphApiFailureException extends Exception {

    private HttpStatus httpStatus;
    private String message;

    public GraphApiFailureException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
