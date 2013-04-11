/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.user;

/**
 *
 * @author Zhengyi
 */
public class Address {
    private String street;
    private String city;
    private String province;
    private String country;
    private String postal;
    
    public Address () {
        street = "";
        city = "";
        province = "";
        country = "";
        postal = "";
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostal() {
        return postal;
    }

    public String getProvince() {
        return province;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    
}
