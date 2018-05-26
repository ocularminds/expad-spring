/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import com.ocularminds.expad.model.Customer;
import java.io.Serializable;
import java.util.Date;

public class CreditCardCustomer
extends Customer
implements Serializable {
    private String id;
    private String name;
    private int totalAccounts;
    private String sol;
    private String solDescription;
    private String address;
    private String city;
    private String residence;
    private String phone;
    private String email;
    private String age;
    private String industry;

    public CreditCardCustomer(String id, String name, int totalAccounts, String sol, String address, String city, String residence, String phone, String email, String age, String industry) {
        super(id, name, totalAccounts, sol, null);
        this.setAddress(address);
        this.setCity(city);
        this.setResidence(residence);
        this.setPhone(phone);
        this.setEmail(email);
        this.setAge(age);
        this.setIndustry(industry);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getResidence() {
        return this.residence;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAge() {
        return this.age;
    }

    public String getIndustry() {
        return this.industry;
    }
}

