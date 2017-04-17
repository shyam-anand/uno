package co.unobot.uno.businesses.models;

/**
 * Created by shyam on 17/04/17.
 */
public enum BusinessCategory {
    RETAIL_STORE(1), HOTEL(2), RESTAURANT(3);

    private int value;

    BusinessCategory(int v) {
        this.value = v;
    }

    public int value() {
        return value;
    }
}
