/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class Pan implements Serializable {

    private String id;
    private String no;
    private String expiry;
    private String offset;
    private String status;
    private String branchCode;
    private String productCode;
    private String serviceCode;

    public Pan() {
        this.id = "";
        this.no = "";
        this.offset = "";
    }

    public Pan(String id, String no, String exp, String code) {
        this.id = id;
        this.no = no;
        this.expiry = exp;
        this.productCode = code;
    }

    public Pan(String id, String no, String expiry, String offset, String status, String branchCode, String productCode) {
        this.setId(id);
        this.setNo(no);
        this.setExpiry(expiry);
        this.setOffset(offset);
        this.setStatus(status);
        this.setBranchCode(branchCode);
        this.setProductCode(productCode);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getId() {
        return this.id;
    }

    public String getNo() {
        return this.no;
    }

    public String getExpiry() {
        return this.expiry;
    }

    public String getOffset() {
        return this.offset;
    }

    public String getStatus() {
        return this.status;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public String getServiceCode() {
        return this.serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
