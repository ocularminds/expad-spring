/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.sql.Timestamp;

public class RecordInfo {
    protected String failure_reason_description = null;
    public String source_data_string = null;
    public static final String EMPTY_FIELD = "";

    static boolean isValidDATETIME(String st) {
        if (st == null) {
            return false;
        }
        boolean error = false;
        try {
            Timestamp ts = Timestamp.valueOf(st);
        }
        catch (IllegalArgumentException iae) {
            error = true;
        }
        return !error;
    }

    public String getDescriptionOfFailure() {
        return this.failure_reason_description;
    }

    static boolean isValidINT(String st) {
        if (st == null) {
            return false;
        }
        long a = 0;
        boolean error = false;
        try {
            a = Long.parseLong(st);
        }
        catch (NumberFormatException nfe) {
            error = true;
        }
        return !error;
    }

    static boolean isValidVARCHAR(int varcharlength, String st) {
        if (st == null) {
            return false;
        }
        return st.length() <= varcharlength;
    }

    static boolean isValidCHAR(int charlength, String st) {
        if (st == null) {
            return false;
        }
        return st.length() == charlength;
    }

    static boolean isValidNUMERIC(int numericlength, String st) {
        if (RecordInfo.isValidINT(st) && (st.charAt(0) == '-' ? RecordInfo.isValidVARCHAR(numericlength + 1, st) : RecordInfo.isValidVARCHAR(numericlength, st))) {
            return true;
        }
        return false;
    }

    static boolean isEMPTY(String st) {
        return "".equals(st);
    }

    static boolean isNull(String st) {
        return st == null;
    }

    static boolean isValidFLOAT(String st) {
        if (st == null) {
            return false;
        }
        boolean error = false;
        try {
            Float a = Float.valueOf(st);
        }
        catch (NumberFormatException nfe) {
            error = true;
        }
        return !error;
    }

    static boolean isValidOperationIndicator(String st) {
        if (st == null) {
            return false;
        }
        return st.charAt(0) == 'U' || st.charAt(0) == 'D';
    }
}

