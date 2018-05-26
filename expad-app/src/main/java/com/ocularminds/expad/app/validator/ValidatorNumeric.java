/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.IValidator
 */
package com.ocularminds.expad.app.validator;

final class ValidatorNumeric implements IValidator {

    private static final boolean[] invalid;

    @Override
    public boolean isValid(Field field) {
        return ValidatorNumeric.isValid(field.data, 0, field.data.length);
    }

    public static final boolean isValid(byte[] data, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            int val;
            if ((val = data[offset++]) < 0) {
                val += 256;
            }
            if (!invalid[val]) continue;
            return false;
        }
        return true;
    }

    public ValidatorNumeric() {
    }

    public final String describe() {
        return "n   ";
    }

    static {
        int i;
        invalid = new boolean[256];
        for (i = 0; i < 48; ++i) {
            ValidatorNumeric.invalid[i] = true;
        }
        for (i = 58; i < 256; ++i) {
            ValidatorNumeric.invalid[i] = true;
        }
        for (i = 48; i < 58; ++i) {
            ValidatorNumeric.invalid[i] = false;
        }
    }
}

