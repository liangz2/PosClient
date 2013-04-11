/*
 * The order class conatins all PurchasedItem in an array in order to be transferred
 * in between server and client
 */
package model.order;

import java.io.Serializable;
import model.product.PurchasedItem;
import model.product.NewPurchaseItem;
import model.user.User;

/**
 *
 * @author Wilson
 */
public class Order implements Serializable {
    private int orderId;
    private User user;
    private String placeTime;
    private PurchasedItem[] purchasedItems;
    private float total;

    public User getUser() {
        return user;
    }

    public float getTotal() {
        return total;
    }

    public void setPurchasedItems(PurchasedItem[] purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public User getCustomer() {
        return user;
    }

    public String getPlaceTime() {
        return placeTime;
    }

    public PurchasedItem[] getPurchasedItems() {
        return purchasedItems;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlaceTime(String placeTime) {
        this.placeTime = placeTime;
    }

    public void setPurchasedItems(NewPurchaseItem[] purchasedItems) {
        this.purchasedItems = new PurchasedItem[purchasedItems.length];
        // create PurchasedItem array
        for (int i = 0; i < purchasedItems.length; i++) {
            this.purchasedItems[i] = new PurchasedItem(purchasedItems[i]);
        }
    }
}
