
package com.ocularminds.expad;

public final class Field {
    public byte[] data = null;

    public Field() {
    }

    public Field(int length) {
        this.data = new byte[length];
    }

    public Field(byte[] data) {
        this.data = data;
    }
}

