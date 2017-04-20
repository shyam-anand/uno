package co.unobot.uno.integrations.facebook.models.message.templates;

import co.unobot.uno.integrations.facebook.models.message.AttachmentPayload;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public abstract class Template extends AttachmentPayload {
    @JsonProperty("template_type")
    private final TemplateType type;

    public Template(TemplateType templateType) {
        this.type = templateType;
    }

    public TemplateType getType() {
        return type;
    }
}
