package co.unobot.uno.integrations.facebook.models.message.templates;

import co.unobot.uno.integrations.facebook.models.message.templates.buttons.Button;

import java.util.List;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public class ButtonTemplate<T extends Button> extends Template {
    private String text;
    private List<T> buttons;

    public ButtonTemplate() {
        super(TemplateType.BUTTON);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<T> getButtons() {
        return buttons;
    }

    public void setButtons(List<T> buttons) {
        this.buttons = buttons;
    }
}
