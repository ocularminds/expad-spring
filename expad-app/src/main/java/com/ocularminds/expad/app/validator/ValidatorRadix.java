
package com.ocularminds.expad.app.validator;

import com.ocularminds.expad.common.Convert;

final class ValidatorRadix implements IValidator {
    private final int radix;
    private final boolean[] invalid = new boolean[256];

    private void initValidDigits(int radix) {
        for (int i = 0; i < this.invalid.length; ++i) {
            this.invalid[i] = Character.digit((char)i, radix) == -1;
            this.invalid[i] = 48 <= i && i <= 57 ? false : (65 <= i && i <= 70 ? false : 97 > i || i > 102);
        }
    }

    @Override
    public final boolean isValid(Field field) {
        return this.isValid(field.data, 0, field.data.length);
    }

    public final boolean isValid(byte[] data, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            int val;
            if ((val = data[offset++]) < 0) {
                val += 256;
            }
            if (!this.invalid[val]) continue;
            return false;
        }
        return true;
    }

    public ValidatorRadix(int radix) {
        this.radix = radix;
        this.initValidDigits(radix);
    }

    public final String describe() {
        switch (this.radix) {
            case 8: {
                return "Oct ";
            }
            case 10: {
                return "Dec ";
            }
            case 16: {
                return "Hex ";
            }
        }
        return Convert.resize("B" + this.radix, 4, ' ', true);
    }
}

