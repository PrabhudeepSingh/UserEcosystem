package com.prabhudeepsingh.userecosystem.model;

/**
 * Created by prabhudeepsingh on 22/03/18.
 */

public class Address {
    public String adrsLine1;
    public String adrsLine2;
    public String city;
    public String state;
    public int zipCode;

    public Address() {
    }

    public Address(String adrsLine1, String adrsLine2, String city, String state, int zipCode) {
        this.adrsLine1 = adrsLine1;
        this.adrsLine2 = adrsLine2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
