/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class MailExtractor
implements Serializable {
    private String staffCode;
    private String staffName;
    private String email;

    public MailExtractor(String staffCode, String staffName, String email) {
        this.setStaffCode(staffCode);
        this.setStaffName(staffName);
        this.setEmail(email);
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffCode() {
        return this.staffCode;
    }

    public String getStaffName() {
        return this.staffName;
    }

    public String getEmail() {
        return this.email;
    }
}

