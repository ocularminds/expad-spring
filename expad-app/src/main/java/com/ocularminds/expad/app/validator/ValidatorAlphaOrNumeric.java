/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.IValidator
 */
package com.ocularminds.expad.app.validator;

final class ValidatorAlphaOrNumeric implements IValidator {
    public final boolean isValid(Field field) {
        return ValidatorAlphaOrNumeric.isValid(field.data, 0, field.data.length);
    }

    public static final boolean isValid(byte[] data, int offset, int len) {
        return ValidatorNumeric.isValid(data, offset, len) || ValidatorAlpha.isValid(data, offset, len);
    }

    ValidatorAlphaOrNumeric() {
    }

    public final String describe() {
        return "a/n ";
    }
}

