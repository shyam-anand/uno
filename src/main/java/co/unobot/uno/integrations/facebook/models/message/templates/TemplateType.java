package co.unobot.uno.integrations.facebook.models.message.templates;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public enum TemplateType {
    GENERIC("generic"),
    BUTTON("button");

    private String name;

    TemplateType(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }
}
