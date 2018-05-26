/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

public class AddressData {
    public String address_line_1 = null;
    public String address_line_2 = null;
    public String city = null;
    public String region = null;
    public String postal_code = null;
    public String country = null;

    public AddressData(String address_line_1, String address_line_2, String city, String region, String postal_code, String country) {
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.city = city;
        this.region = region;
        this.postal_code = postal_code;
        this.country = country;
    }
}

