/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app;

import com.ocularminds.expad.model.Function;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jejelowo B. Festus <festus.jejelowo@ocularminds.com>
 */
public class Module implements Serializable{

    private String id;
    private String name;
    private String icon;
    private Set<Function> permissions;

    public Module(String pk, String nam,String ico) {
        this.id = pk;
        this.name = nam;
        this.icon = ico;
        this.permissions = new HashSet<>();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the permissions
     */
    public Set<Function> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(Set<Function> permissions) {
        this.permissions = permissions;
    }
    
    public void add(Function f){
        this.permissions.add(f);
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
        if (icon != null) {
            sb.append(icon);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Module) {
            return ((Module) o).getName().equals(this.getName());
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
