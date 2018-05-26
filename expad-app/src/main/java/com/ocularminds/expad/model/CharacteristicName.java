/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;

public class CharacteristicName
implements Serializable {
    private String id;
    private String name;
    private String code;

    public CharacteristicName(String id, String code, String name) {
        this.setId(id);
        this.setCode(code);
        this.setName(name);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }
}

