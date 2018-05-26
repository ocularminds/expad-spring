/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class AuditElement
implements Serializable {
    private String id;
    private String name;
    private String oldValue;
    private String newValue;
    private String actionPerformed;
    private String user;
    private String effectiveDate;
    private String branchCode;
    private String time;

    public AuditElement(String id, String name, String oldValue, String newValue, String actionPerformed, String user, String branchCode, String effectiveDate, String time) {
        this.setId(id);
        this.setName(name);
        this.setOldValue(oldValue);
        this.setNewValue(newValue);
        this.setActionPerformed(actionPerformed);
        this.setUser(user);
        this.setBranchCode(branchCode);
        this.setEffectiveDate(effectiveDate);
        this.setTime(time);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(String val) {
        this.oldValue = val;
    }

    public String getNewValue() {
        return this.newValue;
    }

    public void setNewValue(String value) {
        this.newValue = value;
    }

    public String getActionPerformed() {
        return this.actionPerformed;
    }

    public void setActionPerformed(String action) {
        this.actionPerformed = action;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String currentValue) {
        this.effectiveDate = currentValue;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

