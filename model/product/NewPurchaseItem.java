/*
 * This class uses the StringProperty String representation in order to
 * link to the parents that could sense any changes to these Strings
 */
package model.product;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Wilson
 */
public class NewPurchaseItem {
    SimpleStringProperty model;
    SimpleStringProperty quantity;
    SimpleStringProperty description;
    SimpleStringProperty listPrice;
    SimpleStringProperty unitPrice;
    SimpleStringProperty total;
    boolean empty = false;
    SimpleStringProperty dateCode;
    SimpleStringProperty notes;
    
    public NewPurchaseItem() {
        model = new SimpleStringProperty("");
        quantity = new SimpleStringProperty("");
        description = new SimpleStringProperty("");
        listPrice = new SimpleStringProperty("");
        unitPrice = new SimpleStringProperty("");
        total = new SimpleStringProperty("");
        dateCode = new SimpleStringProperty("");
        notes = new SimpleStringProperty("");
        empty = true;
    }
    
    public NewPurchaseItem(Product product) {
        model = new SimpleStringProperty(product.getModel());
        quantity = new SimpleStringProperty("1");
        description = new SimpleStringProperty(product.getName());
        String price = String.format("%.2f", product.getPrice());
        listPrice = new SimpleStringProperty(price);
        unitPrice = new SimpleStringProperty(price);
        total = new SimpleStringProperty(price);
        dateCode = new SimpleStringProperty(" ");
        notes = new SimpleStringProperty(" ");
    }
    
    /**
     *
     * @param product
     */
    public final void setItem(Product product) {
        model.setValue(product.getModel());
        quantity.setValue("1");
        description.setValue(product.getName());
        String price = String.format("%.2f", product.getPrice());
        listPrice.setValue(price);
        unitPrice.setValue(price);
        total.setValue(price);
    }

    public final SimpleStringProperty getModel() {
        return model;
    }

    public final SimpleStringProperty getQuantity() {
        return quantity;
    }

    public final SimpleStringProperty getDescription() {
        return description;
    }

    public final SimpleStringProperty getListPrice() {
        return listPrice;
    }

    public final SimpleStringProperty getUnitPrice() {
        return unitPrice;
    }

    public final SimpleStringProperty getTotal() {
        return total;
    }

    public final SimpleStringProperty getDateCode() {
        return dateCode;
    }

    public final SimpleStringProperty getNotes() {
        return notes;
    }
    
    public final boolean isEmpty() {
        return empty;
    }

    public final void setModel(String model) {
        this.model.setValue(model);
    }

    public final void setQuantity(String quantity) {
        this.quantity.setValue(quantity);
    }

    public final void setDescription(String description) {
        this.description.setValue(description);
    }

    public final void setListPrice(String listPrice) {
        this.listPrice.setValue(listPrice);
    }

    public final void setUnitPrice(String unitPrice) {
        this.unitPrice.setValue(unitPrice);
    }

    public final void setTotal(String total) {
        this.total.setValue(total);
    }

    public final void setDateCode(String dateCode) {
        this.dateCode.setValue(dateCode);
    }

    public final void setNotes(String notes) {
        this.notes.setValue(notes);
    }
    
}
