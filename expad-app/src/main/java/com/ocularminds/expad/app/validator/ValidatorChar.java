/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.IValidator
 */
package com.ocularminds.expad.app.validator;

final class ValidatorChar implements IValidator {
    private char val_char;

    public final boolean isValid(Field field) {
        return ValidatorChar.isValid(this.val_char, field.data, 0, field.data.length);
    }

    public static final boolean isValid(char val_char, byte[] data, int offset, int len) {
        byte val_byte = (byte)val_char;
        for (int i = 0; i < len; ++i) {
            if (data[offset++] == val_byte) continue;
            return false;
        }
        return true;
    }

    public ValidatorChar(char val_char) {
        this.val_char = val_char;
    }

    public final String describe() {
        return "'" + this.val_char + "' ";
    }
}

