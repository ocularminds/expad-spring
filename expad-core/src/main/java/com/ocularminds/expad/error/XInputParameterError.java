/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.error;

public class XInputParameterError extends XPostilion {
    public XInputParameterError(String method_name, String parameter_name, String parameter_value) {
        String[] log_parameters = new String[]{method_name, parameter_name, parameter_value};
        this.init(-1073741820, log_parameters, null);
    }
}

