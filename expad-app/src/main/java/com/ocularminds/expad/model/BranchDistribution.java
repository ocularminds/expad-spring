/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class BranchDistribution
implements Serializable {
    private String code;
    private String fromSequence;
    private String toSequence;

    public BranchDistribution(String code, String fromSequence, String toSequence) {
        this.setCode(code);
        this.setFromSequence(fromSequence);
        this.setToSequence(toSequence);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFromSequence(String fromSequence) {
        this.fromSequence = fromSequence;
    }

    public void setToSequence(String toSequence) {
        this.toSequence = toSequence;
    }

    public String getCode() {
        return this.code;
    }

    public String getFromSequence() {
        return this.fromSequence;
    }

    public String getToSequence() {
        return this.toSequence;
    }
}

