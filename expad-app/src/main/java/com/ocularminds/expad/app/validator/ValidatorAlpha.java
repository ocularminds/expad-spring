/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.IValidator
 */
package com.ocularminds.expad.app.validator;

final class ValidatorAlpha implements IValidator {
    private static final boolean[] invalid;

    public final boolean isValid(Field field) {
        return ValidatorAlpha.isValid(field.data, 0, field.data.length);
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

    ValidatorAlpha() {
    }

    public final String describe() {
        return "a   ";
    }

    static {
        int i;
        invalid = new boolean[256];
        for (i = 0; i < 65; ++i) {
            ValidatorAlpha.invalid[i] = true;
        }
        for (i = 91; i < 97; ++i) {
            ValidatorAlpha.invalid[i] = true;
        }
        for (i = 123; i < 256; ++i) {
            ValidatorAlpha.invalid[i] = true;
        }
        for (i = 65; i < 91; ++i) {
            ValidatorAlpha.invalid[i] = false;
        }
        for (i = 97; i < 123; ++i) {
            ValidatorAlpha.invalid[i] = false;
        }
    }
}

