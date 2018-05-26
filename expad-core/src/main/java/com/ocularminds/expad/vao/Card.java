/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {

    private String id;
    private String photoId;
    private String batchId;
    private String customerId;
    private String customerName;
    private String preferedName;
    private String sol;
    private String phone;
    private String email;
    private String pan;
    private String expiryDate;
    private String offset;
    private String userId;
    private String createDate;
    private String status;
    private String collectingSol;
    private String productCode;
    private String serialNo;
    private String custType;
    private String merchant;
    private String network;
    private String dob;
    private List<String> accounts;
    private List<LinkedAccount> linkedAccounts;
    
    public Card(){}    

    public Card(String name) {
        this.preferedName = name;
        this.customerName = name;
    }

    public Card(String id, String batch, String custId, String name, String pName, String sol,
            String phone, String mail, String pan, String exp, String ofset, String usr,
            String date, String branch, String status, String product,
            String serial, String custType, String schl, String dob) {
        this.id = id;
        this.batchId = batch;
        this.customerId = custId;
        this.serialNo = serial;
        this.customerName = name;
        this.preferedName = pName;
        this.sol = sol;
        this.phone = phone;
        this.email = mail;
        this.pan = pan;
        this.userId = usr;
        this.createDate = date;
        this.status = status;
        this.expiryDate = exp;
        this.offset = ofset;
        this.productCode = product;
        this.collectingSol = branch;
        this.custType = custType;
        this.merchant = schl;
        this.dob = dob;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPreferedName(String preferedName) {
        this.preferedName = preferedName;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEMail(String eMail) {
        this.setEmail(eMail);
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCollectingSol(String collectingSol) {
        this.collectingSol = collectingSol;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public void setMerchant(String school) {
        this.merchant = school;
    }

    public String getId() {
        return this.id;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getPreferedName() {
        return this.preferedName;
    }

    public String getSol() {
        return this.sol;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEMail() {
        return this.getEmail();
    }

    public String getPan() {
        return this.pan;
    }

    public String getOffset() {
        return this.offset;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public String getStatus() {
        return this.status;
    }

    public String getCollectingSol() {
        return this.collectingSol;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public String getCustType() {
        return this.custType;
    }

    public String getMerchant() {
        return this.merchant;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return this.dob;
    }

    /**
     * @return the eMail
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param eMail the eMail to set
     */
    public void setEmail(String eMail) {
        this.email = eMail;
    }

    /**
     * @return the accounts
     */
    public List<String> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the photoId
     */
    public String getPhotoId() {
        return photoId;
    }

    /**
     * @param photoId the photoId to set
     */
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    /**
     * @return the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * @return the linkedAccounts
     */
    public List<LinkedAccount> getLinkedAccounts() {
        return linkedAccounts;
    }

    /**
     * @param linkedAccounts the linkedAccounts to set
     */
    public void setLinkedAccounts(List<LinkedAccount> linkedAccounts) {
        this.linkedAccounts = linkedAccounts;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Card) {
            return ((Card) o).getPan().equals(this.getPan());
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return this.getId().hashCode() * 17 * this.getPan().hashCode();
    }
}
