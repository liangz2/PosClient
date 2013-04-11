/*
 * The PurchaseItem class on the client end takes a NewPurchaseItem and convert
 * every piece of info into premitive type for pass onto the server
 */
package model.product;

import java.io.Serializable;

/**
 *
 * @author Wilson
 */
public class PurchasedItem implements Serializable {
    String model;
    int quantity;
    String description;
    float listPrice;
    float unitPrice;
    float total;
    String dateCode;
    String notes;
    public PurchasedItem(NewPurchaseItem item) {
        model = item.getModel().getValue();
        quantity = Integer.parseInt(item.getQuantity().getValue());
        description = item.getDescription().getValue();
        listPrice = Float.parseFloat(item.getListPrice().getValue());
        unitPrice = Float.parseFloat(item.getUnitPrice().getValue());
        total = Float.parseFloat(item.getTotal().getValue());
        dateCode = item.getDateCode().getValue();
        notes = item.getNotes().getValue();
    }

    public String getModel() {
        return model;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public float getListPrice() {
        return listPrice;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public float getTotal() {
        return total;
    }

    public String getDateCode() {
        return dateCode;
    }

    public String getNotes() {
        return notes;
    }
    
}
