/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class Account
implements Serializable {
    private String accountName;
    private String accountNumber;
    private String schemeCode;
    private String customerId;
    private String flag;
    private String employeeId;
    private String branch;
    private double balance;

    public Account(String accountNumber, String accountName, String branch, String flag, String schemeCode, String customerId, String employeeId, double balance) {
        this.setAccountName(accountName);
        this.setAccountNumber(accountNumber);
        this.setSchemeCode(schemeCode);
        this.setCustomerId(customerId);
        this.setBranch(branch);
        this.setBalance(balance);
        this.setFlag(flag);
        this.setEmployeeId(employeeId);
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public String getFlag() {
        return this.flag;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getBranch() {
        return this.branch;
    }

    public double getBalance() {
        return this.balance;
    }
}

