/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.app.generator;

import com.ocularminds.expad.app.generator.HostAuth;
import java.net.URLEncoder;

public class HostReq
extends HostAuth {
    private StringBuffer sb;

    public HostReq(String operation, String network) {
        HostAuth.init(network);
        this.sb = new StringBuffer();
        this.sb.append("uname");
        this.sb.append("=");
        this.sb.append(URLEncoder.encode(username));
        this.setField("pwd", URLEncoder.encode(password));
        this.setField("op", URLEncoder.encode(operation));
    }

    public void setField(String name, String value) {
        this.sb.append("&");
        this.sb.append(name);
        this.sb.append("=");
        this.sb.append(URLEncoder.encode(value));
    }

    public String toString() {
        return this.sb.toString();
    }
}

