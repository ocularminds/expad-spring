/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class AccountError
implements Serializable {
    private String id;
    private String accountNumber;
    private String errorType;

    public AccountError(String id, String accountNumber, String errorType) {
        this.setId(id);
        this.setAccountNumber(accountNumber);
        this.setErrorType(errorType);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getId() {
        return this.id;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getErrorType() {
        return this.errorType;
    }
}

