package co.unobot.uno.integrations.zomato;

/**
 * Created by shyam on 09/04/17.
 */
public enum City {
    HYDERABAD("6");

    private String name;

    City(String s) {
        this.name = s;
    }

    public String value() {
        switch (this) {
            case HYDERABAD:
                return "6";
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
