/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class Role implements Serializable {
    private String id;
    private String name;
    private String description;
    boolean disabled;

    public Role(String id, String name, String desc, boolean disabled) {
        this.id = id;
        this.name = name;
        this.description =  desc;
        this.disabled = disabled;
    }

    public Role() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDisabled() {
        return this.disabled;
    }
}

