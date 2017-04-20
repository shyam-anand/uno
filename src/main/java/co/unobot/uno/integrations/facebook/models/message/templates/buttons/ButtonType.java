package co.unobot.uno.integrations.facebook.models.message.templates.buttons;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public enum ButtonType {
    URL("web_url"),
    POSTBACK("postback");

    private String name;

    ButtonType(String s) {
        this.name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
