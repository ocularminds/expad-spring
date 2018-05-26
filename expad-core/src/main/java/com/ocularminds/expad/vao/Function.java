/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class Function
implements Serializable {
    private String id;
    private String code;
    private String url;
    private String description;
    private String type;

    public Function(String id, String code, String url, String description, String type) {
        this.setId(id);
        this.setCode(code);
        this.setUrl(url);
        this.setDescription(description);
        this.setType(type);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDescription() {
        return this.description;
    }

    public String getType() {
        return this.type;
    }
}

