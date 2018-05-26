package com.ocularminds.expad.vao;

import java.io.Serializable;

public class Product implements Serializable {

    private String id;
    private String bin;
    private String code;
    private String name;
    private String inActiveCardCode;
    private String currencyCode;
    private String trailingText;
    private int format;
    private String issuerName;
    
    public Product(){}

    public Product(String id, String bin, String code, String name, String inActiveCardCode, String currencyCode, String trailingText, int format) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.bin = bin;
        this.inActiveCardCode = inActiveCardCode;
        this.currencyCode = currencyCode;
        this.format = format;
        this.trailingText = trailingText;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInActiveCardCode(String inActiveCardCode) {
        this.inActiveCardCode = inActiveCardCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setTrailingText(String trailingText) {
        this.trailingText = trailingText;
    }

    public String getId() {
        return this.id;
    }

    public String getBin() {
        return this.bin;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getInActiveCardCode() {
        return this.inActiveCardCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public String getTrailingText() {
        return this.trailingText;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getFormat() {
        return this.format;
    }
}
