/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class ScoreAttribute
implements Serializable {
    private String id;
    private String name;
    private String code;
    private String attribute;
    private double point;

    public ScoreAttribute(String id, String code, String name, String attribute, double point) {
        this.setId(id);
        this.setCode(code);
        this.setName(name);
        this.setAttribute(attribute);
        this.setPoint(point);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public double getPoint() {
        return this.point;
    }
}

