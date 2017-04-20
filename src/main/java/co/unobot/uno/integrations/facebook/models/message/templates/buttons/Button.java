package co.unobot.uno.integrations.facebook.models.message.templates.buttons;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public abstract class Button {

    private ButtonType type;

    public ButtonType getType() {
        return type;
    }

    public void setType(ButtonType type) {
        this.type = type;
    }
}
