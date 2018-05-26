/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class CardInfo implements Serializable {
    private String pan;
    private String expiryDate;
    private String name;

    public CardInfo(String pan, String expiryDate, String name) {
        this.setPan(pan);
        this.setExpiryDate(expiryDate);
        this.setName(name);
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPan() {
        return this.pan;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getName() {
        return this.name;
    }
}

