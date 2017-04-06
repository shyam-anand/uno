package co.unobot.uno.chat.models;

import java.util.List;
import java.util.Map;

/**
 * Created by shyam on 01/04/17
 */
public class UnoResponseParameters {

    private List<Map<String, String>> number;
    private List<Map<String, String>> product;
    private List<Map<String, String>> business;

    public List<Map<String, String>> getNumber() {
        return number;
    }

    public void setNumber(List<Map<String, String>> number) {
        this.number = number;
    }

    public List<Map<String, String>> getProduct() {
        return product;
    }

    public void setProduct(List<Map<String, String>> product) {
        this.product = product;
    }

    public List<Map<String, String>> getBusiness() {
        return business;
    }

    public void setBusiness(List<Map<String, String>> business) {
        this.business = business;
    }
}
