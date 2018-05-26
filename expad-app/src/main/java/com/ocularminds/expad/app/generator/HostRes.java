/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.app.generator;

import java.util.HashMap;
import java.util.Map;

public class HostRes {
    private Map m = new HashMap();

    public HostRes(String response) {
        String[] res = response.split(",");
        for (int x = 0; x < res.length; ++x) {
            String a = res[x];
            String[] b = a.split("=");
            this.m.put(b[0], b[1]);
        }
    }

    public String getParameter(String name) {
        return (String)this.m.get(name);
    }
}

