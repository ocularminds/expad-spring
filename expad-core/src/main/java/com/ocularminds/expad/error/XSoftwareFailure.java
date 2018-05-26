/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.XChainedRuntimeException
 */
package com.ocularminds.expad.error;

import java.util.Arrays;

public class XSoftwareFailure extends XChainedRuntimeException {
    private int event_id;
    private String[] msgs = new String[]{"com.ocularminds.expad.util.XSoftwareFailure"};

    public final int getId() {
        return this.event_id;
    }

    @Override
    public final String toString() {
        StringBuffer sb = new StringBuffer(1000);
        sb.append("Ocular-Minds XSoftwareFailure: ");
        sb.append(this.getClass().getName());
        sb.append("\r\n  Description: ");
        sb.append(this.getMessage());
        sb.append("\r\n  ID: 0x");
        sb.append(this.event_id);
        sb.append("\r\n  Strings: ");
        if (this.msgs == null) {
            sb.append("<none>");
        } else {
            for (int i = 0; i < this.msgs.length; ++i) {
                sb.append(String.format("'%s'",this.msgs[i]));
                if (i >= this.msgs.length - 1) continue;
                sb.append(" ; ");
            }
        }
        return new String(sb);
    }

    public XSoftwareFailure() {
        this.event_id = -1073741824;
    }

    public XSoftwareFailure(Throwable e) {
        super(e);
        this.event_id = -1073741822;
    }

    public XSoftwareFailure(String str) {
        super(str);
        this.event_id = -1073741823;
    }

    public XSoftwareFailure(String str, Throwable cause) {
        super(str, cause);
        this.event_id = -1073741823;
    }

    public XSoftwareFailure(int event_id, String[] msgs) {
        this.event_id = event_id;
        this.msgs = msgs;
    }

    @Override
    public String getMessage() {
        return Integer.toString(this.event_id) + ":" + Arrays.toString(this.msgs);
    }

    public final String[] getMsgs() {
        return this.msgs;
    }
}

