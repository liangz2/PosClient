/*
 * Product class that ccontains basic info obtained from database, plus extra
 * info controls the button display options
 */
package model.product;

import java.io.Serializable;

/**
 *
 * @author Zhengyi
 */
public class Product implements Serializable {
    private int id;
    private short quantity;
    private float price;
    private float weight;
    private String catagory;
    private String image;
    private String model;
    private String dateAdded;
    private boolean status;
    private String name;
    private boolean remove;
    private Object removeButton;
    private Object updateButton;
    private boolean changed;


    public Product() {
        this.model = "";
        this.name = "";
        this.catagory = null;
        this.id = -1;
        this.quantity = -1;
        this.image = "";
        this.price = 0.00f;
        this.dateAdded = "Now()";
        this.weight = 0.00f;
        this.status = true;
        this.remove = false;
        this.changed = false;
    }
    
    public Product(Product product) {
        this.model = product.getModel();
        this.name = product.getName();
        this.catagory = null;
        this.id = product.getId();
        this.quantity = product.quantity;
        this.image = "";
        this.price = product.getPrice();
        this.dateAdded = "Now()";
        this.weight = product.getWeight();
        this.status = product.getStatus();
        this.remove = false;
        this.changed = false;
    }

    public String getName() {
        return name;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getCatagory() {
        return catagory;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public float getPrice() {
        return price;
    }

    public short getQuantity() {
        return quantity;
    }

    public boolean getStatus() {
        return status;
    }

    public float getWeight() {
        return weight;
    }

    public boolean getRemove() {
        return remove;
    }
    
    public Object getRemoveButton() {
        return removeButton;
    }
    
    public Object getUpdateButton() {
        return updateButton;
    }
    
    public boolean getChanged() {
        return changed;
    }
    
    public void setModel(String model) {
        this.model = model.toUpperCase();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
    
    public void setRemove(boolean remove) {
        this.remove = remove;
    }
    
    public void setRemoveButton(Object removeButton) {
        this.removeButton = removeButton;
    }
    
    public void setUpdateButton(Object updateButton) {
        this.updateButton = updateButton;
    }
    
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
