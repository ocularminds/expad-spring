/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class ModuleAudit
implements Serializable {
    private String id;
    private String userid;
    private String accessDate;
    private String accessTime;
    private String branchCode;
    private String module;
    static final long serialVersionUID = System.currentTimeMillis();

    public ModuleAudit(String id, String userid, String accessDate, String accessTime, String branchCode, String module) {
        this.setId(id);
        this.setUserId(userid);
        this.setAccessDate(accessDate);
        this.setAccessTime(accessTime);
        this.setBranchCode(branchCode);
        this.setModule(module);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

    public void setAccessDate(String accessdate) {
        this.accessDate = accessdate;
    }

    public void setAccessTime(String loginTime) {
        this.accessTime = loginTime;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userid;
    }

    public String getAccessDate() {
        return this.accessDate;
    }

    public String getAccessTime() {
        return this.accessTime;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public String getModule() {
        return this.module;
    }
}

