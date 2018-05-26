/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.error;

public class XInvalidInput extends XSoftwareFailure {
    public XInvalidInput(String input_description) {
        super(-1073741813, new String[]{input_description});
    }

    protected XInvalidInput(int event_id, String[] msgs) {
        super(event_id, msgs);
    }
}

