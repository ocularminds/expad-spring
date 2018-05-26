/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;
import java.util.Date;

public class Customer
        implements Serializable {

    private String id;
    private String requestId;
    private String name;
    private int totalAccounts;
    private String sol;
    private Date dob;
    private String dateOfBirth;
    private String solDescription;

    public Customer() {
        this.id = "";
        this.requestId = "";
        this.name = "";
        this.sol = "";
        this.dob = new Date();
    }

    public Customer(String id, String name, int totalAccounts, String sol, Date dob) {
        this.id = id;
        this.name = name;
        this.totalAccounts = totalAccounts;
        this.sol = sol;
        this.dob = dob;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String id) {
        this.requestId = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalAccounts() {
        return this.totalAccounts;
    }

    public void setTotalAccounts(int totalAccounts) {
        this.totalAccounts = totalAccounts;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dob) {
        this.dateOfBirth = dob;
    }

    public String getSol() {
        return this.sol;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }

    public String getSolDescription() {
        return this.solDescription;
    }

    public void setSolDescription(String solDescription) {
        this.solDescription = solDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Customer) {
            return ((Customer) o).getId().equals(this.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() * 17 * this.name.hashCode();
    }
}
