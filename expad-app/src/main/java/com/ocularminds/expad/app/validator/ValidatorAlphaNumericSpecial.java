/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.IValidator
 */
package com.ocularminds.expad.app.validator;

final class ValidatorAlphaNumericSpecial implements IValidator {
    private static final boolean[] invalid;

    public final boolean isValid(Field field) {
        return ValidatorAlphaNumericSpecial.isValid(field.data, 0, field.data.length);
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

    ValidatorAlphaNumericSpecial() {
    }

    public final String describe() {
        return "ans ";
    }

    static {
        int i;
        invalid = new boolean[256];
        for (i = 0; i < 32; ++i) {
            ValidatorAlphaNumericSpecial.invalid[i] = true;
        }
        for (i = 32; i < 256; ++i) {
            ValidatorAlphaNumericSpecial.invalid[i] = false;
        }
    }
}

