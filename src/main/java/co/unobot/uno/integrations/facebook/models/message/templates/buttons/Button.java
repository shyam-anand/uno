package co.unobot.uno.integrations.facebook.models.message.templates.buttons;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public abstract class Button {

    private final ButtonType type;

    public Button(ButtonType type) {
        this.type = type;
    }

    public ButtonType getType() {
        return type;
    }
}
