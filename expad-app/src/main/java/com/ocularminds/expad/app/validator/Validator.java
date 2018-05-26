/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.IValidator
 */
package com.ocularminds.expad.app.validator;

public class Validator {
    private static IValidator VAL_A = new ValidatorAlpha();
    private static IValidator VAL_AN = new ValidatorAlphaNumeric();
    private static IValidator VAL_ANS = new ValidatorAlphaNumericSpecial();
    private static IValidator VAL_A_OR_N = new ValidatorAlphaOrNumeric();
    private static IValidator VAL_N = new ValidatorNumeric();
    private static IValidator VAL_NONE = new ValidatorNone();
    private static IValidator VAL_NS = new ValidatorNumericSpecial();
    private static IValidator VAL_HEX = new ValidatorRadix(16);

    public static IValidator getChar(char valid_char) {
        return new ValidatorChar(valid_char);
    }

    public static boolean isValidAns(String str) {
        return Validator.isValid(VAL_ANS, str);
    }

    public static boolean isValidAns(byte[] data, int offset, int len) {
        return ValidatorAlphaNumericSpecial.isValid(data, offset, len);
    }

    public static IValidator getNone() {
        return VAL_NONE;
    }

    public static IValidator getAOrN() {
        return VAL_A_OR_N;
    }

    public static boolean isValid(IValidator val, String str) {
        //Field field = new Field(Convert.toData(str));
        //return val.isValid(field);
        return true;
    }

    public static boolean isValidChar(char valid_char, String str) {
        return Validator.isValid(new ValidatorChar(valid_char), str);
    }

    public static boolean isValidChar(char valid_char, byte[] data, int offset, int len) {
        return ValidatorChar.isValid(valid_char, data, offset, len);
    }

    public static IValidator getNs() {
        return VAL_NS;
    }

    public static IValidator getAn() {
        return VAL_AN;
    }

    public static boolean isValidAOrN(String str) {
        return Validator.isValid(VAL_N, str);
    }

    public static boolean isValidAOrN(byte[] data, int offset, int len) {
        return ValidatorAlphaOrNumeric.isValid(data, offset, len);
    }

    public static IValidator getA() {
        return VAL_A;
    }

    public static IValidator getHex() {
        return VAL_HEX;
    }

    public static boolean isValidA(String str) {
        return Validator.isValid(VAL_A, str);
    }

    public static IValidator getN() {
        return VAL_N;
    }

    public static boolean isValidA(byte[] data, int offset, int len) {
        return ValidatorAlpha.isValid(data, offset, len);
    }

    public static boolean isValidHex(String str) {
        return Validator.isValid(VAL_HEX, str);
    }

    public static boolean isValidN(String str) {
        return Validator.isValid(VAL_N, str);
    }

    public static boolean isValidN(byte[] data, int offset, int len) {
        return ValidatorNumeric.isValid(data, offset, len);
    }

    public static IValidator getAns() {
        return VAL_ANS;
    }

    public static boolean isValidNs(String str) {
        return Validator.isValid(VAL_NS, str);
    }

    public static boolean isValidAn(String str) {
        return Validator.isValid(VAL_AN, str);
    }

    public static boolean isValidAn(byte[] data, int offset, int len) {
        return ValidatorAlphaNumeric.isValid(data, offset, len);
    }

    public static boolean isValidNs(byte[] data, int offset, int len) {
        return ValidatorNumericSpecial.isValid(data, offset, len);
    }
}

