/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.aop;

import java.util.HashMap;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public class CapturedEntity {

    String clazz;
    String id;
    HashMap fields;

    public CapturedEntity() {
        fields = new HashMap<>();
    }
    
    public void clazz(String clazz){
        this.clazz = clazz;
    }
    
    public void addField(final String name, String value){
        fields.put(name, value);
    }
    
    public HashMap<String,String> getFields(){
        return this.fields; 
    }
    
    @Override
    public String toString(){
        return clazz+" "+fields.toString();
    }
}
