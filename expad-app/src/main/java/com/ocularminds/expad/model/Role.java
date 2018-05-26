/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {

    private String id;
    private String name;
    private String description;
    boolean disabled;
    private List<String> functions;

    public Role(String id, String name, String desc, boolean disabled) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.disabled = disabled;
    }

    public Role() {
    }

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

    /**
     * @return the functions
     */
    public List<String> getFunctions() {
        return functions;
    }

    /**
     * @param functions the functions to set
     */
    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null) {
            sb.append(id.concat(","));
        }
        if (name != null) {
            sb.append(name.concat(","));
        }
        if (description != null) {
            sb.append(description);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Role) {
            return ((Role) o).getName().equals(this.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int prime = 17;
        int hashcode = id != null ? id.hashCode() * prime : prime;
        hashcode = name != null ? name.hashCode() * hashcode : hashcode;
        return hashcode;
    }
}
