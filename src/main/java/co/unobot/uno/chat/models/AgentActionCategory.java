package co.unobot.uno.chat.models;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public enum AgentActionCategory {
    SMALLTALK("smalltalk"),
    DELIVERY("delivery");

    private String name;

    AgentActionCategory(String category) {
        name = category;
    }

    @Override
    public String toString() {
        return name;
    }

    public static AgentActionCategory fromString(String value) {
        for (AgentActionCategory aac : values()) {
            if (aac.name.equalsIgnoreCase(value)) {
                return aac;
            }
        }

        throw new IllegalArgumentException("No constant with value " + value);
    }
}
