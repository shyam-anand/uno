package co.unobot.uno.integrations.facebook.models.message.templates;

import co.unobot.uno.integrations.facebook.models.message.AttachmentPayload;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public abstract class Template extends AttachmentPayload {
    private final TemplateType type;

    public Template(TemplateType templateType) {
        this.type = templateType;
    }

    public TemplateType getType() {
        return type;
    }
}
