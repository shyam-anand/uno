package co.unobot.uno.chat.models;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         20/04/17
 */
public enum AgentAction {

    /**
     * SmallTalk Agent
     */
    SMALLTALK_WELCOME("smalltalk", "welcome"),
    SMALLTALK_GREETINGS("smalltalk", "greetings"),
    /**
     * Food-Delivery Agent
     */
    DELIVERY_ORDER_ADD("delivery", "order.add"),
    DELIVERY_ORDER_CHECK("delivery", "order.check"),
    DELIVERY_ORDER_CHECK_STATUS("delivery.order", "check_status"),
    DELIVERY_ORDER_REMOVE("delivery", "order.remove"),
    DELIVERY_PRODUCT_ADD("delivery", "product.add"),
    DELIVERY_PRODUCT_REMOVE("delivery", "product.remove"),
    DELIVERY_PRODUCT_SWAP("delivery", "product.swap"),
    DELIVERY_SEARCH("delivery", "search");

    private AgentActionCategory category;
    private String action;
    private String name;

    AgentAction(String category, String action) {
        this.category = AgentActionCategory.valueOf(category);
        this.action = action;
        this.name = String.format("%s.%s", category, action);
    }

    public AgentActionCategory category() {
        return category;
    }

    public String action() {
        return action;
    }

    @Override
    public String toString() {
        return name;
    }
}
