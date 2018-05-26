
package com.ocularminds.expad.model;

import java.io.Serializable;

public class Script implements Serializable {
    private String db;
    private String account;
    private String customer;
    private String branch;
    private String details;

    public Script(String db, String acct, String cust, String sol, String det) {
        this.db = db;
        this.account = acct;
        this.customer = cust;
        this.details = det;
        this.branch = sol;
    }

    public Script() {}

    public void setDb(String db) {
        this.db = db;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDb() {
        return this.db;
    }

    public String getAccount() {
        return this.account;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getCustomer() {
        return this.customer;
    }

    public String getDetails() {
        return this.details;
    }
}

