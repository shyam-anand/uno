package co.unobot.uno.integrations.zomato;

import org.springframework.http.HttpStatus;

/**
 * Created by shyam on 08/04/17.
 */
public class ZomatoRequestFailedException extends Exception {

    private HttpStatus httpStatus;
    private String message;

    public ZomatoRequestFailedException(HttpStatus httpStatus, String message) {
        super("Zomato API failed with " + httpStatus.value() + " " + httpStatus.getReasonPhrase() + " - " + message);
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
