/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.expad.util.XChainedException
 */
package com.ocularminds.expad.error;

import java.util.Arrays;

public class XPostilion extends XChainedException {

    private int id = -1073741824;
    private String[] msgs = null;
    private byte[] data = null;

    public final int getID() {
        return this.id;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final String toString() {
        StringBuffer str = new StringBuffer(1000);
        String event_msg = null;
        try {
            event_msg = this.getMessage();
        } catch (Throwable e) {
            event_msg = "[Could not be retrieved:" + e.getClass().getName();
        } finally {
            event_msg = event_msg + "]";
        }
        str.append(String.format("Postilion exception: %s", this.getClass().getName()));
        str.append(String.format("\r\n  Description: %s" ,event_msg ));
        str.append(String.format("\r\n  ID: %0xd \r\n  Strings: ",this.id));
        if (this.msgs == null) {
            str.append("<none>");
        } else {
            for (int i = 0; i < this.msgs.length; ++i) {
                str.append(String.format("'%s'", this.msgs[i]));
                if (i >= this.msgs.length - 1) {
                    continue;
                }
                str.append(" ; ");
            }
        }
        str.append("\r\n  Data: ");
        if (this.data == null) {
            str.append("<none>\r\n");
        } else {
            str.append(String.format("\r\n%s", Arrays.toString(this.data)));
        }
        return new String(str);
    }

    public XPostilion() {
    }

    public XPostilion(int id, String[] msgs, byte[] data) {
        this.id = id;
        this.msgs = msgs;
        this.data = data;
    }

    public XPostilion(int id, String msg, byte[] data) {
        this.id = id;
        this.msgs = new String[1];
        this.msgs[0] = msg;
        this.data = data;
    }

    public XPostilion(int id, String[] msgs) {
        this.id = id;
        this.msgs = msgs;
    }

    public XPostilion(int id, String msg) {
        this.id = id;
        this.msgs = new String[1];
        this.msgs[0] = msg;
    }

    public XPostilion(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public XPostilion(int id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s",Integer.toString(this.id),Arrays.toString(this.msgs));
    }

    public final byte[] getData() {
        return this.data;
    }

    public final void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof XPostilion)) {
            return this.equals(o);
        }
        XPostilion e = (XPostilion) ((Object) o);
        if (e == null) {
            return false;
        }
        if (this.id != e.id) {
            return false;
        }
        if (this.msgs == null ^ e.msgs == null) {
            return false;
        }
        if (this.msgs != null) {
            if (this.msgs.length != e.msgs.length) {
                return false;
            }
            for (int i = 0; i < this.msgs.length; ++i) {
                if (this.msgs[i] == null ^ e.msgs[i] == null) {
                    return false;
                }
                if (this.msgs[i] == null || this.msgs[i].equals(e.msgs[i])) {
                    continue;
                }
                return false;
            }
        }
        return Arrays.equals(this.data, e.data);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Arrays.deepHashCode(this.msgs);
        hash = 97 * hash + Arrays.hashCode(this.data);
        return hash;
    }

    public final String[] getMsgs() {
        return this.msgs;
    }

    public final void init(int id, String[] msgs, byte[] data) {
        this.id = id;
        this.msgs = msgs;
        this.data = data;
    }
}
