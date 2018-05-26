/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.IValidator
 */
package com.ocularminds.expad.app.validator;

final class ValidatorNone implements IValidator {
    public final boolean isValid(Field field) {
        return true;
    }

    ValidatorNone() {
    }

    public final String describe() {
        return "b   ";
    }
}

