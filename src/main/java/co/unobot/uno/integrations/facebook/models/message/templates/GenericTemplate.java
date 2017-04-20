package co.unobot.uno.integrations.facebook.models.message.templates;

import co.unobot.uno.integrations.facebook.models.message.templates.buttons.Button;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericTemplate {

    @JsonProperty("template_type")
    private final TemplateType templateType = TemplateType.GENERIC;
    private String text;
    private List<Button> buttons;


}
