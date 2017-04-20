package co.unobot.uno.integrations.facebook.models.message.templates.buttons;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public class PostbackButton extends Button {

    private String title;
    private Object payload;

    public PostbackButton() {
        super(ButtonType.POSTBACK);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
