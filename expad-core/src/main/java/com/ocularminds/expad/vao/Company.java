/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class Company implements Serializable {

    private String name;
    private String shortName;
    private String address;
    private int maximumRequestPerCustomer;
    private int maximumAccountPerRequest;
    private String excludeAccountType;
    private String resetFlag;
    private String lastModification;
    private int minimumPassword;
    private int passwordExpiry;
    private int sessionIdleTime;
    private String mailServer;
    private String downloadType;
    private String downloadFrequency;
    private String restrictionCode;
    private String sequenceNo;
    private String pinOffset;
    private String cardProgram;
    private String inActiveCardCode;
    private int attemptLimit;
    private int accountSize;
    private int accountStartPosition;
    private int accountEndPosition;
    private String currentMapCode;
    private String savingsMapCode;
    private int panSize;
    private boolean prefixedWithU;
    private String postCardDirectory;

    public Company(String name, String shortName, String address, int maximumRequestPerCustomer, int maximumAccountPerRequest, String excludeAccountType, String resetFlag, String lastModification, int minimumPassword, int passwordExpiry, int sessionIdleTime, String mailServer, String downloadType, String downloadFrequency, String restrictionCode, String sequenceNo, String pinOffset, String cardProgram, String inActiveCode, int attemptLimit, int accountSize, int accountStartPosition, int accountEndPosition, String currentMapCode, String savingsMapCode, int panSize, boolean prefixedWithU, String postCardDirectory) {
        this.name = name;
        this.shortName = shortName;
        this.address = address;
        this.maximumRequestPerCustomer = maximumRequestPerCustomer;
        this.maximumAccountPerRequest = maximumAccountPerRequest;
        this.excludeAccountType = excludeAccountType;
        this.resetFlag = resetFlag;
        this.lastModification = lastModification;
        this.minimumPassword = minimumPassword;
        this.passwordExpiry = passwordExpiry;
        this.sessionIdleTime = sessionIdleTime;
        this.mailServer = mailServer;
        this.downloadType = downloadType;
        this.downloadFrequency = downloadFrequency;
        this.restrictionCode = restrictionCode;
        this.sequenceNo = sequenceNo;
        this.pinOffset = pinOffset;
        this.cardProgram = cardProgram;
        this.inActiveCardCode = inActiveCode;
        this.attemptLimit = attemptLimit;
        this.accountSize = accountSize;
        this.accountStartPosition = accountStartPosition;
        this.accountEndPosition = accountEndPosition;
        this.currentMapCode = currentMapCode;
        this.savingsMapCode = savingsMapCode;
        this.panSize = panSize;
        this.prefixedWithU = prefixedWithU;
        this.postCardDirectory = postCardDirectory;
    }

    public Company() {
        //To change body of generated methods, choose Tools | Templates.
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMaximumRequestPerCustomer(int maximumRequestPerCustomer) {
        this.maximumRequestPerCustomer = maximumRequestPerCustomer;
    }

    public void setMaximumAccountPerRequest(int maximumAccountPerRequest) {
        this.maximumAccountPerRequest = maximumAccountPerRequest;
    }

    public void setExcludeAccountType(String excludeAccountType) {
        this.excludeAccountType = excludeAccountType;
    }

    public void setResetFlag(String resetFlag) {
        this.resetFlag = resetFlag;
    }

    public void setLastModification(String lastModification) {
        this.lastModification = lastModification;
    }

    public void setMinimumPassword(int minimPassword) {
        this.minimumPassword = minimPassword;
    }

    public void setPasswordExpiry(int passwordExpiry) {
        this.passwordExpiry = passwordExpiry;
    }

    public void setSessionIdleTime(int sessionIdleTime) {
        this.sessionIdleTime = sessionIdleTime;
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }

    public void setDownloadFrequency(String downloadFrequency) {
        this.downloadFrequency = downloadFrequency;
    }

    public void setRestrictionCode(String restrictionCode) {
        this.restrictionCode = restrictionCode;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setPinOffset(String pinOffset) {
        this.pinOffset = pinOffset;
    }

    public void setCardProgram(String cardProgram) {
        this.cardProgram = cardProgram;
    }

    public void setInActiveCardCode(String inActiveCode) {
        this.inActiveCardCode = inActiveCode;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getAddress() {
        return this.address;
    }

    public int getMaximumRequestPerCustomer() {
        return this.maximumRequestPerCustomer;
    }

    public int getMaximumAccountPerRequest() {
        return this.maximumAccountPerRequest;
    }

    public String getExcludeAccountType() {
        return this.excludeAccountType;
    }

    public String getResetFlag() {
        return this.resetFlag;
    }

    public String getLastModification() {
        return this.lastModification;
    }

    public int getMinimumPassword() {
        return this.minimumPassword;
    }

    public int getPasswordExpiry() {
        return this.passwordExpiry;
    }

    public int getSessionIdleTime() {
        return this.sessionIdleTime;
    }

    public String getMailServer() {
        return this.mailServer;
    }

    public String getDownloadType() {
        return this.downloadType;
    }

    public String getDownloadFrequency() {
        return this.downloadFrequency;
    }

    public String getRestrictionCode() {
        return this.restrictionCode;
    }

    public String getSequenceNo() {
        return this.sequenceNo;
    }

    public String getPinOffset() {
        return this.pinOffset;
    }

    public String getCardProgram() {
        return this.cardProgram;
    }

    public String getInActiveCardCode() {
        return this.inActiveCardCode;
    }

    public void setAttemptLimit(int accessAttemptLimit) {
        this.attemptLimit = accessAttemptLimit;
    }

    public int getAttemptLimit() {
        return this.attemptLimit;
    }

    public int getAccountSize() {
        return this.accountSize;
    }

    public int getPanSize() {
        return this.panSize;
    }

    public int getAccountStartPosition() {
        return this.accountStartPosition;
    }

    public int getAccountEndPosition() {
        return this.accountEndPosition;
    }

    public String getCurrentMapCode() {
        return this.currentMapCode;
    }

    public String getSavingsMapCode() {
        return this.savingsMapCode;
    }

    public boolean isPrefixedWithU() {
        return this.prefixedWithU;
    }

    public String getPostCardDirectory() {
        return this.postCardDirectory;
    }

    /**
     * @param accountStartPosition the accountStartPosition to set
     */
    public void setAccountStartPosition(int accountStartPosition) {
        this.accountStartPosition = accountStartPosition;
    }

    /**
     * @param accountEndPosition the accountEndPosition to set
     */
    public void setAccountEndPosition(int accountEndPosition) {
        this.accountEndPosition = accountEndPosition;
    }

    /**
     * @param currentMapCode the currentMapCode to set
     */
    public void setCurrentMapCode(String currentMapCode) {
        this.currentMapCode = currentMapCode;
    }

    /**
     * @param savingsMapCode the savingsMapCode to set
     */
    public void setSavingsMapCode(String savingsMapCode) {
        this.savingsMapCode = savingsMapCode;
    }

    /**
     * @param panSize the panSize to set
     */
    public void setPanSize(int panSize) {
        this.panSize = panSize;
    }

    /**
     * @param prefixedWithU the prefixedWithU to set
     */
    public void setPrefixedWithU(boolean prefixedWithU) {
        this.prefixedWithU = prefixedWithU;
    }

    /**
     * @param postCardDirectory the postCardDirectory to set
     */
    public void setPostCardDirectory(String postCardDirectory) {
        this.postCardDirectory = postCardDirectory;
    }

    /**
     * @param accountSize the accountSize to set
     */
    public void setAccountSize(int accountSize) {
        this.accountSize = accountSize;
    }
}
