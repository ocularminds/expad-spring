/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class LoginAudit
implements Serializable {
    private String id;
    private String userid;
    private String logDate;
    private String loginTime;
    private String logoutTime;
    private String branchCode;
    private int attemptCount;
    private String status;
    private String workstation;
    static final long serialVersionUID = System.currentTimeMillis();

    public LoginAudit(String id, String userid, String logDate, String loginTime, String logoutTime, String branchCode, int attemptCount, String status, String workstation) {
        this.setId(id);
        this.setUserId(userid);
        this.setLogDate(logDate);
        this.setLoginTime(loginTime);
        this.setLogoutTime(logoutTime);
        this.setBranchCode(branchCode);
        this.setAttemptCount(attemptCount);
        this.setStatus(status);
        this.setWorkstation(workstation);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

    public void setLogDate(String logdate) {
        this.logDate = logdate;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userid;
    }

    public String getLogDate() {
        return this.logDate;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public String getLogoutTime() {
        return this.logoutTime;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public String getStatus() {
        return this.status;
    }

    public int getAttemptCount() {
        return this.attemptCount;
    }

    public String getWorkstation() {
        return this.workstation;
    }
}

