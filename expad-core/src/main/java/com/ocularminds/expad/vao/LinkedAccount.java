/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class LinkedAccount implements Serializable {

    private String id;
    private String requestId;
    private String accountNumber;

    public LinkedAccount() {
    }

    public LinkedAccount(String id, String requestId, String accountNumber) {
        this.setId(id);
        this.setRequestId(requestId);
        this.setAccountNumber(accountNumber);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return this.id;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }
}
