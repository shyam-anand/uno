package co.unobot.uno.integrations.zomato;

import org.springframework.http.HttpStatus;

/**
 * Created by shyam on 08/04/17.
 */
public class ZomatoRequestFailedException extends Exception {

    private HttpStatus httpStatus;

    public ZomatoRequestFailedException(HttpStatus httpStatus) {
        super("Zomato API responded with " + httpStatus.name() + " " + httpStatus.getReasonPhrase());
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
