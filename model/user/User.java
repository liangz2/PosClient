/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.user;

import java.io.Serializable;
import model.order.Order;

/**
 *
 * @author Zhengyi
 */
public class User implements Serializable {
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNum;
    private Address address;
    private Order purchaseHistory;
    private int rank;
    
    public User () {
        firstName = "";
        lastName = "";
        email = "";
        address = null;
        rank = -1;
    }
    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public void setPurchaseHistory(Order history) {
        purchaseHistory = history;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
    
    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public Order getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return firstName;
    }
}
