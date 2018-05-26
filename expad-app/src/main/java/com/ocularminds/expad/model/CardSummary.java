/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class CardSummary
implements Serializable {
    private String branchCode;
    private String userId;
    private int totalCard;

    public CardSummary(String branchCode, String userId, int totalCard) {
        this.setBranchCode(branchCode);
        this.setUserId(userId);
        this.setTotalCard(totalCard);
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTotalCard(int totalCard) {
        this.totalCard = totalCard;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public String getUserId() {
        return this.userId;
    }

    public int getTotalCard() {
        return this.totalCard;
    }
}

