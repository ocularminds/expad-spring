
package com.ocularminds.expad.model;

import java.io.Serializable;

public class Branch implements Serializable {

    private String id;
    private String name;
    private String code;

    public Branch(String id, String code, String name) {
        this.setId(id);
        this.setName(name);
        this.setCode(code);
    }

    public Branch(String code, String name) {
        this.setName(name);
        this.setCode(code);
    }

    public Branch() {
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
