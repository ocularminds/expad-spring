/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.IValidator
 */
package com.ocularminds.expad.app.validator;

final class ValidatorAlphaNumeric implements IValidator {
    private static final boolean[] invalid;

    public final boolean isValid(Field field) {
        return ValidatorAlphaNumeric.isValid(field.data, 0, field.data.length);
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

    ValidatorAlphaNumeric() {
    }

    public final String describe() {
        return "an  ";
    }

    static {
        int i;
        invalid = new boolean[256];
        for (i = 0; i < 48; ++i) {
            ValidatorAlphaNumeric.invalid[i] = true;
        }
        for (i = 58; i < 65; ++i) {
            ValidatorAlphaNumeric.invalid[i] = true;
        }
        for (i = 91; i < 97; ++i) {
            ValidatorAlphaNumeric.invalid[i] = true;
        }
        for (i = 123; i < 256; ++i) {
            ValidatorAlphaNumeric.invalid[i] = true;
        }
        for (i = 48; i < 58; ++i) {
            ValidatorAlphaNumeric.invalid[i] = false;
        }
        for (i = 65; i < 91; ++i) {
            ValidatorAlphaNumeric.invalid[i] = false;
        }
        for (i = 97; i < 123; ++i) {
            ValidatorAlphaNumeric.invalid[i] = false;
        }
    }
}

