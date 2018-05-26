/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String staffCode;
    private String title;
    private String branch;
    private String dateCreated;
    private String password;
    private String password2;
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

    public User(String userid, String role, String name, String email) {
        this.staffCode = userid;
        this.fullName = name;
        this.role = role;
        this.eMail = email;
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

    /**
     * @return the password2
     */
    public String getPassword2() {
        return password2;
    }

    /**
     * @param password2 the password2 to set
     */
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id.concat(","));
        }
        if (staffCode != null) {
            sb.append(staffCode.concat(","));
        }
        if (eMail != null) {
            sb.append(eMail);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof User) {
            return ((User) o).getStaffCode().equals(this.getStaffCode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() * 17 * this.staffCode.hashCode();
    }
}
