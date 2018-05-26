/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class User
implements Serializable {
    private String id;
    private String staffCode;
    private String title;
    private String branch;
    private String dateCreated;
    private String password;
    private String fullName;
    private String role;
    private int expiry;
    private String eMail;
    private String firstLog;
    private int statusLog;
    private String lastPasswordChange;
    private boolean readOnly;
    private boolean disabled;
    private boolean superUser;

    public User() {
        this.expiry = 0;
        this.statusLog = 0;
        this.readOnly = false;
    }

    public User(String userid, String role, String name) {
        this.staffCode = userid;
        this.setFullName(name);
        this.setRole(role);
    }

    public User(String id, String staffCode, String title, String password, String fullName, String branch, String role, String dateCreated, int expiry, String firstLog, int statusLog, String lastPasswordChange, boolean readOnly, boolean disabled, boolean superUser) {
        this.setId(id);
        this.setStaffCode(staffCode);
        this.setTitle(title);
        this.setPassword(password);
        this.setFullName(fullName);
        this.setRole(role);
        this.setBranch(branch);
        this.setDateCreated(dateCreated);
        this.setExpiry(expiry);
        this.setFirstLog(firstLog);
        this.setStatusLog(statusLog);
        this.setLastPasswordChange(lastPasswordChange);
        this.setReadOnly(readOnly);
        this.setDisabled(disabled);
        this.setSuperUser(superUser);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String departmentCode) {
        this.role = departmentCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBranch(String designation) {
        this.branch = designation;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setFirstLog(String firstLog) {
        this.firstLog = firstLog;
    }

    public void setStatusLog(int statusLog) {
        this.statusLog = statusLog;
    }

    public void setLastPasswordChange(String lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }

    public String getId() {
        return this.id;
    }

    public String getStaffCode() {
        return this.staffCode;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getRole() {
        return this.role;
    }

    public String getPassword() {
        return this.password;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }

    public int getExpiry() {
        return this.expiry;
    }

    public String getEMail() {
        return this.eMail;
    }

    public String getFirstLog() {
        return this.firstLog;
    }

    public int getStatusLog() {
        return this.statusLog;
    }

    public String getLastPasswordChange() {
        return this.lastPasswordChange;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public boolean isSuperUser() {
        return this.superUser;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof User) {
            return ((User)o).getStaffCode().equals(this.getStaffCode());
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode() * 17 * this.staffCode.hashCode();
    }
}

