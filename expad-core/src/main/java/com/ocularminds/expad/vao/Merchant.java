/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class Merchant implements Serializable {
    private String name;
    private String acronymn;
    private String location;
    private String id;

    public Merchant(){}

    public Merchant(String id, String sname, String shortName, String location) {
        this.id = id;
        this.name = sname;
        this.acronymn = shortName;
        this.location = location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Merchant) {
            return ((Merchant)o).getAcronymn().equals(this.getAcronymn());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode() * 17 * this.getAcronymn().hashCode();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the acronymn
     */
    public String getAcronymn() {
        return acronymn;
    }

    /**
     * @param acronymn the acronymn to set
     */
    public void setAcronymn(String acronymn) {
        this.acronymn = acronymn;
    }
}

