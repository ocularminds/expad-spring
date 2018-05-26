/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.common;

import com.ocularminds.expad.error.XInputParameterError;
import com.ocularminds.expad.error.XSoftwareFailure;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Vector;

public final class Convert {
    private static final short[] ebcdic_to_ascii = new short[256];
    private static final short[] ascii_to_ebcdic = new short[256];
    private static final int positive_byte_mask = 255;
    private static final long positive_int_mask = -1;

    public static final int fromMsgTypeToInt(String str) {
        return Convert.fromMsgTypeDataToInt(Convert.getData(str));
    }

    public static final void convertEbcdicToAscii(byte[] data, int offset, int len) {
        int end = offset + len;
        for (int i = offset; i < end; ++i) {
            data[i] = data[i] < 0 ? (byte)ebcdic_to_ascii[256 + data[i]] : (byte)ebcdic_to_ascii[data[i]];
        }
    }

    public static void getBinFromHexInAscii(String ascii_string, byte[] bin, int offset) {
        int nb = ascii_string.length();
        char[] ascii = new char[nb];
        ascii_string.getChars(0, nb, ascii, 0);
        for (int i = 0; i < nb; ++i) {
            byte nibble = ascii[i] <= '9' ? (byte)((byte)ascii[i] - 48 & 15) : (byte)((byte)ascii[i] - 65 + 10 & 15);
            if (i % 2 == 0) {
                bin[offset + i / 2] = (byte)(nibble << 4);
                continue;
            }
            byte[] arrby = bin;
            int n = offset + i / 2;
            arrby[n] = (byte)(arrby[n] + nibble);
        }
    }

    public static final int toInt(byte[] buf, int offset, int len) {
        int val = 0;
        while (len > 0) {
            val = val * 10 + (buf[offset++] & 15);
            --len;
        }
        return val;
    }

    public static final int toInt(byte[] buf) {
        return Convert.toInt(buf, 0, buf.length);
    }

    public static long fromStringToLong(String value) {
        return Long.parseLong(value);
    }

    public static final long fromMsgTypeToLong(String str) {
        return Convert.fromMsgTypeDataToLong(Convert.getData(str));
    }

    public static final void convertAsciiToEbcdic(byte[] data, int offset, int len) {
        int end = offset + len;
        for (int i = offset; i < end; ++i) {
            data[i] = data[i] < 0 ? (byte)ascii_to_ebcdic[256 + data[i]] : (byte)ascii_to_ebcdic[data[i]];
        }
    }

    public static void main(String[] args) throws Exception{
        System.out.println("--------- Convert --------");
        String ans_test = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+;'\\,.:\"|<>";
        String msg_type_test = "09AF";
        System.out.println(Convert.fromDoubleToString(100.0, 5));
        System.out.println(Convert.fromDoubleToString(-100.0, 5));
        if (!(ans_test.equals(Convert.fromAsciiToEbcdic(Convert.fromEbcdicToAscii(ans_test))) && ans_test.equals(Convert.fromHexToBin(Convert.fromBinToHex(ans_test))) && ans_test.equals(Convert.fromPexToBin(Convert.fromBinToPex(ans_test))) && msg_type_test.equals(Convert.fromLongToMsgType(Convert.fromMsgTypeToLong(msg_type_test))) && "".equals(Convert.strip("", '0', false, 0)) && "".equals(Convert.strip("00", '0', false, 0)) && "12".equals(Convert.strip("12", '0', false, 0)) && "12".equals(Convert.strip("0012", '0', false, 0)) && "00".equals(Convert.strip("", '0', false, 2)) && "00".equals(Convert.strip("00", '0', false, 2)) && "00".equals(Convert.strip("000", '0', false, 2)) && "01".equals(Convert.strip("1", '0', false, 2)) && "01".equals(Convert.strip("01", '0', false, 2)) && "01".equals(Convert.strip("001", '0', false, 2)) && "123".equals(Convert.strip("123", '0', false, 2)) && "123".equals(Convert.strip("00123", '0', false, 2)) && "0000".equals(Convert.resize("", 4, '0', false)) && "0000".equals(Convert.resize("00", 4, '0', false)) && "0000".equals(Convert.resize("0000", 4, '0', false)) && "0012".equals(Convert.resize("12", 4, '0', false)) && "0012".equals(Convert.resize("012", 4, '0', false)) && "0012".equals(Convert.resize("0012", 4, '0', false)) && "1234".equals(Convert.resize("1234", 4, '0', false)) && "1234".equals(Convert.resize("0001234", 4, '0', false)) && "00100".equals(Convert.fromDoubleToString(100.0, 5)) && "00100".equals(Convert.fromDoubleToString(-100.0, 5)))) {
            throw new RuntimeException();
        }
        try {
            System.in.read();
        }
        catch (Throwable e) {
            System.out.println(e.toString());
        }
        try {
            System.in.read();
        }
        catch (Throwable e) {
            System.out.println(e.toString());
        }
    }

    public static final long fromMsgTypeDataToLong(byte[] data) {
        long val = 0;
        int len = data.length;
        for (int i = 0; i < len; ++i) {
            val = val << 4 | (long)Convert.getHexNibble(data[i]);
        }
        return val;
    }

    public static final byte[] toDataHex(byte[] value, int offset, int length) {
        length += length;
        byte[] buf = new byte[length];
        for (int i = 0; i < length; ++i) {
            buf[i] = (value[offset] & 128) == 0 ? (byte)((value[offset] & 112) >> 4) : (byte)((value[offset] & 112) >> 4 | 8);
            if (buf[i] < 10) {
                byte[] arrby = buf;
                int n = i;
                arrby[n] = (byte)(arrby[n] + 48);
            } else {
                byte[] arrby = buf;
                int n = i;
                arrby[n] = (byte)(arrby[n] + 55);
            }
            buf[++i] = (byte)(value[offset] & 15);
            if (buf[i] < 10) {
                byte[] arrby = buf;
                int n = i;
                arrby[n] = (byte)(arrby[n] + 48);
            } else {
                byte[] arrby = buf;
                int n = i;
                arrby[n] = (byte)(arrby[n] + 55);
            }
            ++offset;
        }
        return buf;
    }

    public static final void toDataHex(long value, byte[] data, int offset, int len) {
        offset += len;
        while (len > 0) {
            data[--offset] = (byte)(value & 15 | 48);
            if (data[offset] > 57) {
                byte[] arrby = data;
                int n = offset;
                arrby[n] = (byte)(arrby[n] + 7);
            }
            value >>= 4;
            --len;
        }
    }

    public static final String toStringHex(long value, int length) {
        byte[] buf = new byte[length];
        while (length > 0) {
            long temp = value & 15;
            buf[--length] = temp < 10 ? (byte)(temp + 48) : (byte)(temp + 55);
            value >>= 4;
        }
        return new String(buf, 0, 0, buf.length);
    }

    public static double fromStringToDouble(String value) {
        return Convert.fromStringToLong(value);
    }

    private static final byte getPexNibble(byte data) {
        if (data >= 48 && data <= 63) {
            return (byte)(data & 15);
        }
        return 0;
    }

    public static final String toStringHex(long value) {
        int len = (int)Math.round(Math.floor(Math.log(value) / Math.log(16.0))) + 1;
        return Convert.toStringHex(value, len);
    }

    public static final String toStringHex(String value) {
        byte[] temp = Convert.toData(value);
        return Convert.toStringHex(temp, 0, temp.length);
    }

    public static final String toStringHex(byte[] value, int offset, int length) {
        byte[] temp = Convert.toDataHex(value, offset, length);
        return new String(temp, 0, 0, temp.length);
    }

    public static final String toStringHex(byte[] value) {
        return Convert.toStringHex(value, 0, value.length);
    }

    private static final int getPexChar(int nibble) {
        return nibble + 48;
    }

    public static final String setLength(String str, int len, boolean left_justify) {
        return "";//Convert.setLength(str, len, 32, left_justify);
    }

    public static final String setLength(String str, int len, byte pad_char, boolean left_justify) {
        int str_len = str.length();
        if (str_len == len) {
            return str;
        }
        if (str_len < len) {
            byte[] data = new byte[str_len];
            str.getBytes(0, str_len, data, 0);
            data = Convert.toDataPadded(data, len, pad_char, left_justify);
            return new String(data, 0, 0, len);
        }
        if (left_justify) {
            return str.substring(0, len);
        }
        return str.substring(str_len - len);
    }

    public static Vector splitString(String str, int sep) {
        int offset;
        Vector<String> entries = new Vector<String>();
        int old_offset = offset = 0;
        do {
            if ((offset = str.indexOf(sep, old_offset)) == -1) {
                if (str.length() <= 0) break;
                entries.addElement(str.substring(old_offset));
                break;
            }
            entries.addElement(str.substring(old_offset, offset));
            old_offset = offset + 1;
        } while (true);
        return entries;
    }

    public static final boolean isEqual(byte[] arg1, byte[] arg2) {
        int len = arg1.length;
        if (len != arg2.length) {
            return false;
        }
        for (int i = 0; i < len; ++i) {
            if (arg1[i] == arg2[i]) continue;
            return false;
        }
        return true;
    }

    public static String fromDoubleToString(double value, int length) throws XSoftwareFailure{
        return Convert.fromDoubleToUnsignedLongString(value, length);
    }

    public static final int fromMsgTypeDataToInt(byte[] data) {
        int val = 0;
        int len = data.length;
        for (int i = 0; i < len; ++i) {
            val = val << 4 | Convert.getHexNibble(data[i]);
        }
        return val;
    }

    public static final boolean isEqual(Object arg1, Object arg2) {
        if (arg1 == null ^ arg2 == null) {
            return false;
        }
        if (arg1 == null) {
            return true;
        }
        return arg1.equals(arg2);
    }

    public static final String toString(byte[] data) {
        return new String(data, 0, 0, data.length);
    }

    public static final String toString(long value, int length) {
        byte[] buf = new byte[length];
        while (length > 0) {
            buf[--length] = (byte)(value % 10 + 48);
            value /= 10;
        }
        return Convert.toString(buf);
    }

    public static final boolean isEqualData(byte[] arg1, byte[] arg2) {
        if (arg1 == null ^ arg2 == null) {
            return false;
        }
        if (arg1 == null) {
            return true;
        }
        int len = arg1.length;
        if (len != arg2.length) {
            return false;
        }
        for (int i = 0; i < len; ++i) {
            if (arg1[i] == arg2[i]) continue;
            return false;
        }
        return true;
    }

    public static final byte[] fromPexDataToBinData(byte[] pex_data) {
        int len = pex_data.length / 2;
        byte[] data = new byte[len];
        int offset = 0;
        for (int i = 0; i < len; ++i) {
            data[i] = (byte)(Convert.getPexNibble(pex_data[offset++]) << 4 | Convert.getPexNibble(pex_data[offset++]));
        }
        return data;
    }

    public static final byte[] fromBinDataToPexData(byte[] bin_data) {
        int len = bin_data.length;
        byte[] buf = new byte[len * 2];
        int offset = 0;
        for (int i = 0; i < len; ++i) {
            int val = bin_data[i] & 255;
            buf[offset++] = (byte)Convert.getPexChar(val >> 4);
            buf[offset++] = (byte)Convert.getPexChar(val & 15);
        }
        return buf;
    }

    public static final byte[] stripData(byte[] data, byte pad_byte, boolean left_justify, int min_length) {
        int pos;
        int len = data.length;
        if (left_justify) {
            --len;
            while (len >= min_length && data[len] == pad_byte) {
                --len;
            }
            if (len < min_length) {
                byte[] temp = new byte[min_length];
                System.arraycopy(data, 0, temp, 0, ++len);
                while (len < min_length) {
                    temp[len] = pad_byte;
                    ++len;
                }
                return temp;
            }
            byte[] temp = new byte[++len];
            System.arraycopy(data, 0, temp, 0, len);
            return temp;
        }
        int end = len - min_length;
        for (pos = 0; pos < end && data[pos] == pad_byte; ++pos) {
        }
        if (pos > end) {
            byte[] temp = new byte[min_length];
            System.arraycopy(data, pos, temp, min_length - len, len -= pos);
            min_length -= len;
            --min_length;
            while (min_length >= 0) {
                temp[min_length] = pad_byte;
                --min_length;
            }
            return temp;
        }
        byte[] temp = new byte[len -= pos];
        System.arraycopy(data, pos, temp, 0, len);
        return temp;
    }

    public static final String strip(String str, char pad_char, boolean left_justify, int min_length) {
        return Convert.getString(Convert.stripData(Convert.getData(str), (byte)pad_char, left_justify, min_length));
    }

    public static final byte[] toData(String val) {
        int len = val.length();
        byte[] data = new byte[len];
        val.getBytes(0, len, data, 0);
        return data;
    }

    public static final String fromPexToBin(String pex_str) {
        return Convert.getString(Convert.fromPexDataToBinData(Convert.getData(pex_str)));
    }

    public static final void toData(long val, byte[] buf, int offset, int len) {
        offset += len;
        while (len > 0) {
            buf[--offset] = (byte)(val % 10 | 48);
            val /= 10;
            --len;
        }
    }

    public static final byte[] toData(long val) {
        int len = (int)Math.round(Math.floor(Math.log(val) / Math.log(10.0))) + 1;
        byte[] buf = new byte[len];
        Convert.toData(val, buf, 0, len);
        return buf;
    }

    public static String[] splitParams(String params) {
        if (params == null) {
            return null;
        }
        int len = params.length();
        char[] sep = new char[]{' ', '\t'};
        char[] special_sep = new char[]{'\"', "'".charAt(0)};
        Vector<String> v = new Vector<String>();
        int i = 0;
        while (i < len) {
            int next_sep = len;
            for (int j = 0; j < sep.length; ++j) {
                int temp = params.indexOf(sep[j], i);
                if (temp == -1 || temp >= next_sep) continue;
                next_sep = temp;
            }
            int next_special_sep = len;
            for (int j2 = 0; j2 < special_sep.length; ++j2) {
                int temp = params.indexOf(special_sep[j2], i);
                if (temp == -1 || temp >= next_special_sep) continue;
                next_special_sep = temp;
            }
            if (next_special_sep == i) {
                char c = params.charAt(i);
                int end = params.indexOf(c, i + 1);
                if (end == -1) {
                    end = len;
                }
                v.addElement(params.substring(i + 1, end));
                i = end + 1;
                continue;
            }
            if (next_sep == i) {
                ++i;
                continue;
            }
            v.addElement(params.substring(i, next_sep));
            i = next_sep;
        }
        String[] temp = new String[v.size()];
        for (int j = 0; j < v.size(); ++j) {
            temp[j] = (String)v.elementAt(j);
        }
        return temp;
    }

    public static final String fromBinToPex(String bin_str) {
        return Convert.getString(Convert.fromBinDataToPexData(Convert.getData(bin_str)));
    }

    public static final byte[] fromDataPadded(byte[] data, byte pad_char, boolean right_padded, int min_length) {
        int pos;
        int len = data.length;
        if (right_padded) {
            --len;
            while (len >= min_length && data[len] == pad_char) {
                --len;
            }
            if (len < min_length) {
                byte[] temp = new byte[min_length];
                System.arraycopy(data, 0, temp, 0, ++len);
                while (len < min_length) {
                    temp[len] = pad_char;
                    ++len;
                }
                return temp;
            }
            byte[] temp = new byte[++len];
            System.arraycopy(data, 0, temp, 0, len);
            return temp;
        }
        int end = len - min_length;
        for (pos = 0; pos < end && data[pos] == pad_char; ++pos) {
        }
        if (pos > end) {
            byte[] temp = new byte[min_length];
            System.arraycopy(data, pos, temp, min_length - len, len -= pos);
            min_length -= pos;
            --min_length;
            while (min_length >= 0) {
                temp[min_length] = pad_char;
                --min_length;
            }
            return temp;
        }
        byte[] temp = new byte[len -= pos];
        System.arraycopy(data, pos, temp, 0, len);
        return temp;
    }

    public static final String fromStringPadded(String str, byte pad_char, boolean right_padded, int min_length) {
        byte[] data = Convert.toData(str);
        return Convert.toString(Convert.fromDataPadded(data, pad_char, right_padded, min_length));
    }

    public static final byte[] toDataPadded(String str, int len, byte pad_char) {
        byte[] data = new byte[len];
        int str_len = str.length();
        if (str_len >= len) {
            str.getBytes(0, len, data, 0);
        } else {
            str.getBytes(0, str_len, data, 0);
            while (str_len < len) {
                data[str_len] = pad_char;
                ++str_len;
            }
        }
        return data;
    }

    public static final byte[] toDataPadded(byte[] data, int len, byte pad_char, boolean left_justify) {
        int data_len = 0;
        byte[] pad_data = new byte[len];
        if (left_justify) {
            if (data_len >= len) {
                System.arraycopy(data, 0, pad_data, 0, len);
            } else {
                System.arraycopy(data, 0, pad_data, 0, data_len);
                for (data_len = data.length; data_len < len; ++data_len) {
                    pad_data[data_len] = pad_char;
                }
            }
        } else if (data_len >= len) {
            System.arraycopy(data, data_len - len, pad_data, 0, len);
        } else {
            data_len = len - data_len;
            System.arraycopy(data, 0, pad_data, data_len, data.length);
            --data_len;
            while (data_len >= 0) {
                pad_data[data_len] = pad_char;
                --data_len;
            }
        }
        return pad_data;
    }

    public static final String toStringPadded(String str, int len, byte pad_char, boolean left_justify) {
        return Convert.toString(Convert.toDataPadded(Convert.toData(str), len, pad_char, left_justify));
    }

    public static final String getString(byte[] data) {
        return new String(data, 0, 0, data.length);
    }

    public static String fromLongToString(long value, int length) throws XSoftwareFailure{
        String result;
        if (value < 0) {
            value = - value;
        }
        if ((result = String.valueOf(value)).length() > length) {
            throw new XSoftwareFailure("Method Convert.fromLongToString was incorrectly called with invalid parameters:(value = " + value + ", length = " + length + ". For " + value + ", length must be " + result.length() + " or more.");
        }
        return Convert.resize(result, length, '0', false);
    }

    public static final String fromDataToStringHexDump(byte[] value, String pre_str) {
        int offset = 0;
        int length = value.length;
        StringBuffer out = new StringBuffer(length * 4);
        String three_spaces = "   ";
        int old_offset = 0;
        int end_offset = offset + length;
        while (offset < end_offset) {
            out.append(pre_str + Convert.toString(offset, 4) + "(" + Convert.toStringHex(offset, 4) + ")  ");
            old_offset = offset;
            int i = 0;
            while (i < 16) {
                if (offset < end_offset) {
                    if (value[offset] > 0) {
                        out.append(Convert.toStringHex(value[offset], 2) + " ");
                    } else {
                        out.append(Convert.toStringHex(256 + value[offset], 2) + " ");
                    }
                } else {
                    out.append(three_spaces);
                }
                if (i == 7) {
                    out.append(" ");
                }
                ++i;
                ++offset;
            }
            offset = old_offset;
            out.append("  ");
            for (i = 0; i < 16 && offset < end_offset; ++i, ++offset) {
                if (value[offset] < 32) {
                    out.append(".");
                    continue;
                }
                out.append(new String(value, offset, 1));
            }
            out.append("\n");
        }
        return new String(out);
    }

    public static final String toStringHexDump(byte[] value, int offset, int length, String pre_str) {
        StringBuffer out = new StringBuffer(value.length * 4);
        String three_spaces = "   ";
        int old_offset = 0;
        int end_offset = offset + length;
        while (offset < end_offset) {
            out.append(pre_str + Convert.toString(offset, 4) + "(" + Convert.toStringHex(offset, 4) + ")  ");
            old_offset = offset;
            int i = 0;
            while (i < 16) {
                if (offset < end_offset) {
                    if (value[offset] > 0) {
                        out.append(Convert.toStringHex(value[offset], 2) + " ");
                    } else {
                        out.append(Convert.toStringHex(256 + value[offset], 2) + " ");
                    }
                } else {
                    out.append(three_spaces);
                }
                if (i == 7) {
                    out.append(" ");
                }
                ++i;
                ++offset;
            }
            offset = old_offset;
            out.append("  ");
            for (i = 0; i < 16 && offset < end_offset; ++i, ++offset) {
                if (value[offset] < 32) {
                    out.append(".");
                    continue;
                }
                out.append(new String(value, offset, 1));
            }
            out.append("\n");
        }
        return new String(out);
    }

    public static final String toStringHexDump(byte[] value, int offset, int length) {
        return Convert.toStringHexDump(value, offset, length, "");
    }

    public static final String toStringHexDump(byte[] value) {
        return Convert.toStringHexDump(value, 0, value.length, "");
    }

    public static final String toStringPadded(String str, int len, char pad_char, boolean left_justify) {
        char[] pad_data = new char[len];
        int data_len = 0;
        if (str != null && str.length() > len) {
            if (left_justify) {
                return str.substring(0, len);
            }
            return str.substring(str.length() - len, str.length());
        }
        if (str != null) {
            data_len = str.length();
            if (left_justify) {
                str.getChars(0, data_len, pad_data, 0);
                for (int i = data_len; i < len; ++i) {
                    pad_data[i] = pad_char;
                }
            } else {
                str.getChars(0, data_len, pad_data, len - data_len);
                for (int i = 0; i < len - data_len; ++i) {
                    pad_data[i] = pad_char;
                }
            }
        } else {
            for (int i = 0; i < len; ++i) {
                pad_data[i] = pad_char;
            }
        }
        return new String(pad_data);
    }

    public static final String fromIntToMsgType(int value) {
        return Convert.getString(Convert.fromIntToMsgTypeData(value));
    }

    public static final void putData(byte[] source, byte[] destination, int offset) {
        System.arraycopy(source, 0, destination, offset, source.length);
    }

    public static final byte[] getData(byte[] data, int offset, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(data, offset, temp, 0, length);
        return temp;
    }

    public static final byte[] getData(String str) {
        int len = str.length();
        byte[] data = new byte[len];
        str.getBytes(0, len, data, 0);
        return data;
    }

    public static final byte[] fromAsciiDataToEbcdicData(byte[] ascii_data) {
        int len = ascii_data.length;
        byte[] ret = new byte[len];
        for (int i = 0; i < len; ++i) {
            ret[i] = (byte)ascii_to_ebcdic[ascii_data[i] & 255];
        }
        return ret;
    }

    public static final byte[] fromEbcdicDataToAsciiData(byte[] ebcdic_data) {
        int len = ebcdic_data.length;
        byte[] ret = new byte[len];
        for (int i = 0; i < len; ++i) {
            ret[i] = (byte)ebcdic_to_ascii[ebcdic_data[i] & 255];
        }
        return ret;
    }

    public static final byte[] resizeData(byte[] data, int length, byte pad_byte, boolean left_justify) {
        int data_len = 0;
        byte[] pad_data = new byte[length];
        if (left_justify) {
            if (data_len >= length) {
                System.arraycopy(data, 0, pad_data, 0, length);
            } else {
                System.arraycopy(data, 0, pad_data, 0, data_len);
                for (data_len = data.length; data_len < length; ++data_len) {
                    pad_data[data_len] = pad_byte;
                }
            }
        } else if (data_len >= length) {
            System.arraycopy(data, data_len - length, pad_data, 0, length);
        } else {
            data_len = length - data_len;
            System.arraycopy(data, 0, pad_data, data_len, data.length);
            --data_len;
            while (data_len >= 0) {
                pad_data[data_len] = pad_byte;
                --data_len;
            }
        }
        return pad_data;
    }

    public static Vector split(String str, char sep) {
        int offset;
        Vector<String> entries = new Vector<String>();
        int old_offset = offset = 0;
        do {
            if ((offset = str.indexOf(sep, old_offset)) == -1) {
                if (str.length() <= 0) break;
                entries.addElement(str.substring(old_offset));
                break;
            }
            entries.addElement(str.substring(old_offset, offset));
            old_offset = offset + 1;
        } while (true);
        return entries;
    }

    public static final byte[] fromIntToMsgTypeData(int value) {
        byte[] data = new byte[4];
        for (int i = 3; i >= 0; --i) {
            data[i] = (byte)Convert.getHexChar(value & 15);
            value >>= 4;
        }
        return data;
    }

    public static final String fromLongToMsgType(long value) {
        return Convert.getString(Convert.fromLongToMsgTypeData(value));
    }

    public static final String fromEbcdicToAscii(String ebcdic_str) {
        return Convert.getString(Convert.fromEbcdicDataToAsciiData(Convert.getData(ebcdic_str)));
    }

    private static final byte getHexNibble(byte data) {
        if (data >= 48 && data <= 57) {
            return (byte)(data & 15);
        }
        if (data >= 65 && data <= 70) {
            return (byte)(data - 55);
        }
        if (data >= 97 && data <= 102) {
            return (byte)(data - 87);
        }
        return 0;
    }

    public static final String fromHexToBin(String hex_str) {
        return Convert.getString(Convert.fromHexDataToBinData(Convert.getData(hex_str)));
    }

    public static String getHexInAsciiFromBin(byte[] bin, int offset, int nb) {
        StringBuffer buf = new StringBuffer(nb * 2);
        int upper_limit = offset + nb;
        while (offset < upper_limit) {
            byte nibble = (byte)(bin[offset] >> 4 & 15);
            if (nibble <= 9) {
                buf.append((char)(nibble + 48));
            } else {
                buf.append((char)(nibble - 10 + 65));
            }
            nibble = (byte)(bin[offset] & 15);
            if (nibble <= 9) {
                buf.append((char)(nibble + 48));
            } else {
                buf.append((char)(nibble - 10 + 65));
            }
            ++offset;
        }
        return buf.toString();
    }

    public static final String fromAsciiToEbcdic(String ascii_str) {
        return Convert.getString(Convert.fromAsciiDataToEbcdicData(Convert.getData(ascii_str)));
    }

    private Convert() {
    }

    public static final String fromBinToHex(String bin_str) {
        return Convert.getString(Convert.fromBinDataToHexData(Convert.getData(bin_str)));
    }

    public static final long fromDataHex(byte[] data, int offset, int len) {
        long val = 0;
        while (len > 0) {
            if (data[offset] >= 48 && data[offset] <= 57) {
                val = val << 4 | (long)(data[offset] & 15);
            } else if (data[offset] >= 65 && data[offset] <= 70) {
                val = val << 4 | (long)(data[offset] - 55);
            } else if (data[offset] >= 97 && data[offset] <= 102) {
                val = val << 4 | (long)(data[offset] - 87);
            } else {
                return -1;
            }
            ++offset;
            --len;
        }
        return val;
    }

    public static final String fromStringHex(String value) {
        byte[] hex_data = Convert.toData(value);
        int len = value.length() / 2;
        byte[] data = new byte[len];
        int offset = 0;
        for (int i = 0; i < len; ++i) {
            if (hex_data[offset] >= 48 && hex_data[offset] <= 57) {
                data[i] = (byte)((hex_data[offset] & 15) << 4);
            } else if (hex_data[offset] >= 65 && hex_data[offset] <= 70) {
                data[i] = (byte)(hex_data[offset] - 55 << 4);
            } else if (hex_data[offset] >= 97 && hex_data[offset] <= 102) {
                data[i] = (byte)(hex_data[offset] - 87 << 4);
            } else {
                return null;
            }
            if (hex_data[++offset] >= 48 && hex_data[offset] <= 57) {
                byte[] arrby = data;
                int n = i;
                arrby[n] = (byte)(arrby[n] | hex_data[offset] & 15);
            } else if (hex_data[offset] >= 65 && hex_data[offset] <= 70) {
                byte[] arrby = data;
                int n = i;
                arrby[n] = (byte)(arrby[n] | hex_data[offset] - 55);
            } else if (hex_data[offset] >= 97 && hex_data[offset] <= 102) {
                byte[] arrby = data;
                int n = i;
                arrby[n] = (byte)(arrby[n] | hex_data[offset] - 87);
            } else {
                return null;
            }
            ++offset;
        }
        return new String(data, 0, 0, len);
    }

    private static final int getHexChar(int nibble) {
        if (nibble < 10) {
            return nibble + 48;
        }
        return nibble + 55;
    }

    public static final String resize(String str, int length, char pad_char, boolean left_justify) {
        return Convert.getString(Convert.resizeData(Convert.getData(str), length, (byte)pad_char, left_justify));
    }

    public static String fromDoubleToUnsignedLongString(double value, int length) throws XSoftwareFailure{
        try {
            String integer_result;
            if (value < 0.0) {
                value = Math.abs(value);
            }
            if ((integer_result = String.valueOf((long)value)).length() > length) {
                throw new XInputParameterError("fromDoubleToString", "length", String.valueOf(length));
            }
            return Convert.resize(integer_result, length, '0', false);
        }catch (XInputParameterError e) {
            throw new XSoftwareFailure((Throwable)((Object)e));
        }
    }

    public static final byte[] fromHexDataToBinData(byte[] hex_data) {
        int len = hex_data.length / 2;
        byte[] data = new byte[len];
        int offset = 0;
        for (int i = 0; i < len; ++i) {
            data[i] = (byte)(Convert.getHexNibble(hex_data[offset++]) << 4 | Convert.getHexNibble(hex_data[offset++]));
        }
        return data;
    }

    public static final byte[] fromBinDataToHexData(byte[] bin_data) {
        int len = bin_data.length;
        byte[] buf = new byte[len * 2];
        int offset = 0;
        for (int i = 0; i < len; ++i) {
            int val = bin_data[i] & 255;
            buf[offset++] = (byte)Convert.getHexChar(val >> 4);
            buf[offset++] = (byte)Convert.getHexChar(val & 15);
        }
        return buf;
    }

    public static final byte[] fromLongToMsgTypeData(long value) {
        byte[] data = new byte[4];
        for (int i = 3; i >= 0; --i) {
            data[i] = (byte)Convert.getHexChar((int)value & 15);
            value >>= 4;
        }
        return data;
    }

    static {
        Convert.ascii_to_ebcdic[0] = 0;
        Convert.ascii_to_ebcdic[1] = 1;
        Convert.ascii_to_ebcdic[2] = 2;
        Convert.ascii_to_ebcdic[3] = 3;
        Convert.ascii_to_ebcdic[4] = 55;
        Convert.ascii_to_ebcdic[5] = 45;
        Convert.ascii_to_ebcdic[6] = 46;
        Convert.ascii_to_ebcdic[7] = 47;
        Convert.ascii_to_ebcdic[8] = 22;
        Convert.ascii_to_ebcdic[9] = 5;
        Convert.ascii_to_ebcdic[10] = 37;
        Convert.ascii_to_ebcdic[11] = 11;
        Convert.ascii_to_ebcdic[12] = 12;
        Convert.ascii_to_ebcdic[13] = 13;
        Convert.ascii_to_ebcdic[14] = 14;
        Convert.ascii_to_ebcdic[15] = 15;
        Convert.ascii_to_ebcdic[16] = 16;
        Convert.ascii_to_ebcdic[17] = 17;
        Convert.ascii_to_ebcdic[18] = 18;
        Convert.ascii_to_ebcdic[19] = 19;
        Convert.ascii_to_ebcdic[20] = 60;
        Convert.ascii_to_ebcdic[21] = 61;
        Convert.ascii_to_ebcdic[22] = 50;
        Convert.ascii_to_ebcdic[23] = 38;
        Convert.ascii_to_ebcdic[24] = 24;
        Convert.ascii_to_ebcdic[25] = 25;
        Convert.ascii_to_ebcdic[26] = 63;
        Convert.ascii_to_ebcdic[27] = 39;
        Convert.ascii_to_ebcdic[28] = 28;
        Convert.ascii_to_ebcdic[29] = 29;
        Convert.ascii_to_ebcdic[30] = 30;
        Convert.ascii_to_ebcdic[31] = 31;
        Convert.ascii_to_ebcdic[32] = 64;
        Convert.ascii_to_ebcdic[33] = 90;
        Convert.ascii_to_ebcdic[34] = 127;
        Convert.ascii_to_ebcdic[35] = 123;
        Convert.ascii_to_ebcdic[36] = 91;
        Convert.ascii_to_ebcdic[37] = 108;
        Convert.ascii_to_ebcdic[38] = 80;
        Convert.ascii_to_ebcdic[39] = 125;
        Convert.ascii_to_ebcdic[40] = 77;
        Convert.ascii_to_ebcdic[41] = 93;
        Convert.ascii_to_ebcdic[42] = 92;
        Convert.ascii_to_ebcdic[43] = 78;
        Convert.ascii_to_ebcdic[44] = 107;
        Convert.ascii_to_ebcdic[45] = 96;
        Convert.ascii_to_ebcdic[46] = 75;
        Convert.ascii_to_ebcdic[47] = 97;
        Convert.ascii_to_ebcdic[48] = 240;
        Convert.ascii_to_ebcdic[49] = 241;
        Convert.ascii_to_ebcdic[50] = 242;
        Convert.ascii_to_ebcdic[51] = 243;
        Convert.ascii_to_ebcdic[52] = 244;
        Convert.ascii_to_ebcdic[53] = 245;
        Convert.ascii_to_ebcdic[54] = 246;
        Convert.ascii_to_ebcdic[55] = 247;
        Convert.ascii_to_ebcdic[56] = 248;
        Convert.ascii_to_ebcdic[57] = 249;
        Convert.ascii_to_ebcdic[58] = 122;
        Convert.ascii_to_ebcdic[59] = 94;
        Convert.ascii_to_ebcdic[60] = 76;
        Convert.ascii_to_ebcdic[61] = 126;
        Convert.ascii_to_ebcdic[62] = 110;
        Convert.ascii_to_ebcdic[63] = 111;
        Convert.ascii_to_ebcdic[64] = 124;
        Convert.ascii_to_ebcdic[65] = 193;
        Convert.ascii_to_ebcdic[66] = 194;
        Convert.ascii_to_ebcdic[67] = 195;
        Convert.ascii_to_ebcdic[68] = 196;
        Convert.ascii_to_ebcdic[69] = 197;
        Convert.ascii_to_ebcdic[70] = 198;
        Convert.ascii_to_ebcdic[71] = 199;
        Convert.ascii_to_ebcdic[72] = 200;
        Convert.ascii_to_ebcdic[73] = 201;
        Convert.ascii_to_ebcdic[74] = 209;
        Convert.ascii_to_ebcdic[75] = 210;
        Convert.ascii_to_ebcdic[76] = 211;
        Convert.ascii_to_ebcdic[77] = 212;
        Convert.ascii_to_ebcdic[78] = 213;
        Convert.ascii_to_ebcdic[79] = 214;
        Convert.ascii_to_ebcdic[80] = 215;
        Convert.ascii_to_ebcdic[81] = 216;
        Convert.ascii_to_ebcdic[82] = 217;
        Convert.ascii_to_ebcdic[83] = 226;
        Convert.ascii_to_ebcdic[84] = 227;
        Convert.ascii_to_ebcdic[85] = 228;
        Convert.ascii_to_ebcdic[86] = 229;
        Convert.ascii_to_ebcdic[87] = 230;
        Convert.ascii_to_ebcdic[88] = 231;
        Convert.ascii_to_ebcdic[89] = 232;
        Convert.ascii_to_ebcdic[90] = 233;
        Convert.ascii_to_ebcdic[91] = 173;
        Convert.ascii_to_ebcdic[92] = 224;
        Convert.ascii_to_ebcdic[93] = 189;
        Convert.ascii_to_ebcdic[94] = 95;
        Convert.ascii_to_ebcdic[95] = 109;
        Convert.ascii_to_ebcdic[96] = 121;
        Convert.ascii_to_ebcdic[97] = 129;
        Convert.ascii_to_ebcdic[98] = 130;
        Convert.ascii_to_ebcdic[99] = 131;
        Convert.ascii_to_ebcdic[100] = 132;
        Convert.ascii_to_ebcdic[101] = 133;
        Convert.ascii_to_ebcdic[102] = 134;
        Convert.ascii_to_ebcdic[103] = 135;
        Convert.ascii_to_ebcdic[104] = 136;
        Convert.ascii_to_ebcdic[105] = 137;
        Convert.ascii_to_ebcdic[106] = 145;
        Convert.ascii_to_ebcdic[107] = 146;
        Convert.ascii_to_ebcdic[108] = 147;
        Convert.ascii_to_ebcdic[109] = 148;
        Convert.ascii_to_ebcdic[110] = 149;
        Convert.ascii_to_ebcdic[111] = 150;
        Convert.ascii_to_ebcdic[112] = 151;
        Convert.ascii_to_ebcdic[113] = 152;
        Convert.ascii_to_ebcdic[114] = 153;
        Convert.ascii_to_ebcdic[115] = 162;
        Convert.ascii_to_ebcdic[116] = 163;
        Convert.ascii_to_ebcdic[117] = 164;
        Convert.ascii_to_ebcdic[118] = 165;
        Convert.ascii_to_ebcdic[119] = 166;
        Convert.ascii_to_ebcdic[120] = 167;
        Convert.ascii_to_ebcdic[121] = 168;
        Convert.ascii_to_ebcdic[122] = 169;
        Convert.ascii_to_ebcdic[123] = 192;
        Convert.ascii_to_ebcdic[124] = 79;
        Convert.ascii_to_ebcdic[125] = 208;
        Convert.ascii_to_ebcdic[126] = 161;
        Convert.ascii_to_ebcdic[127] = 7;
        Convert.ascii_to_ebcdic[128] = 32;
        Convert.ascii_to_ebcdic[129] = 33;
        Convert.ascii_to_ebcdic[130] = 34;
        Convert.ascii_to_ebcdic[131] = 35;
        Convert.ascii_to_ebcdic[132] = 36;
        Convert.ascii_to_ebcdic[133] = 21;
        Convert.ascii_to_ebcdic[134] = 6;
        Convert.ascii_to_ebcdic[135] = 23;
        Convert.ascii_to_ebcdic[136] = 40;
        Convert.ascii_to_ebcdic[137] = 41;
        Convert.ascii_to_ebcdic[138] = 42;
        Convert.ascii_to_ebcdic[139] = 43;
        Convert.ascii_to_ebcdic[140] = 44;
        Convert.ascii_to_ebcdic[141] = 9;
        Convert.ascii_to_ebcdic[142] = 10;
        Convert.ascii_to_ebcdic[143] = 27;
        Convert.ascii_to_ebcdic[144] = 48;
        Convert.ascii_to_ebcdic[145] = 49;
        Convert.ascii_to_ebcdic[146] = 26;
        Convert.ascii_to_ebcdic[147] = 51;
        Convert.ascii_to_ebcdic[148] = 52;
        Convert.ascii_to_ebcdic[149] = 53;
        Convert.ascii_to_ebcdic[150] = 54;
        Convert.ascii_to_ebcdic[151] = 8;
        Convert.ascii_to_ebcdic[152] = 56;
        Convert.ascii_to_ebcdic[153] = 57;
        Convert.ascii_to_ebcdic[154] = 58;
        Convert.ascii_to_ebcdic[155] = 59;
        Convert.ascii_to_ebcdic[156] = 4;
        Convert.ascii_to_ebcdic[157] = 20;
        Convert.ascii_to_ebcdic[158] = 62;
        Convert.ascii_to_ebcdic[159] = 255;
        Convert.ascii_to_ebcdic[160] = 65;
        Convert.ascii_to_ebcdic[161] = 170;
        Convert.ascii_to_ebcdic[162] = 74;
        Convert.ascii_to_ebcdic[163] = 177;
        Convert.ascii_to_ebcdic[164] = 159;
        Convert.ascii_to_ebcdic[165] = 178;
        Convert.ascii_to_ebcdic[166] = 106;
        Convert.ascii_to_ebcdic[167] = 181;
        Convert.ascii_to_ebcdic[168] = 187;
        Convert.ascii_to_ebcdic[169] = 180;
        Convert.ascii_to_ebcdic[170] = 154;
        Convert.ascii_to_ebcdic[171] = 138;
        Convert.ascii_to_ebcdic[172] = 176;
        Convert.ascii_to_ebcdic[173] = 202;
        Convert.ascii_to_ebcdic[174] = 175;
        Convert.ascii_to_ebcdic[175] = 188;
        Convert.ascii_to_ebcdic[176] = 144;
        Convert.ascii_to_ebcdic[177] = 143;
        Convert.ascii_to_ebcdic[178] = 234;
        Convert.ascii_to_ebcdic[179] = 250;
        Convert.ascii_to_ebcdic[180] = 190;
        Convert.ascii_to_ebcdic[181] = 160;
        Convert.ascii_to_ebcdic[182] = 182;
        Convert.ascii_to_ebcdic[183] = 179;
        Convert.ascii_to_ebcdic[184] = 157;
        Convert.ascii_to_ebcdic[185] = 218;
        Convert.ascii_to_ebcdic[186] = 155;
        Convert.ascii_to_ebcdic[187] = 139;
        Convert.ascii_to_ebcdic[188] = 183;
        Convert.ascii_to_ebcdic[189] = 184;
        Convert.ascii_to_ebcdic[190] = 185;
        Convert.ascii_to_ebcdic[191] = 171;
        Convert.ascii_to_ebcdic[192] = 100;
        Convert.ascii_to_ebcdic[193] = 101;
        Convert.ascii_to_ebcdic[194] = 98;
        Convert.ascii_to_ebcdic[195] = 102;
        Convert.ascii_to_ebcdic[196] = 99;
        Convert.ascii_to_ebcdic[197] = 103;
        Convert.ascii_to_ebcdic[198] = 158;
        Convert.ascii_to_ebcdic[199] = 104;
        Convert.ascii_to_ebcdic[200] = 116;
        Convert.ascii_to_ebcdic[201] = 113;
        Convert.ascii_to_ebcdic[202] = 114;
        Convert.ascii_to_ebcdic[203] = 115;
        Convert.ascii_to_ebcdic[204] = 120;
        Convert.ascii_to_ebcdic[205] = 117;
        Convert.ascii_to_ebcdic[206] = 118;
        Convert.ascii_to_ebcdic[207] = 119;
        Convert.ascii_to_ebcdic[208] = 172;
        Convert.ascii_to_ebcdic[209] = 105;
        Convert.ascii_to_ebcdic[210] = 237;
        Convert.ascii_to_ebcdic[211] = 238;
        Convert.ascii_to_ebcdic[212] = 235;
        Convert.ascii_to_ebcdic[213] = 239;
        Convert.ascii_to_ebcdic[214] = 236;
        Convert.ascii_to_ebcdic[215] = 191;
        Convert.ascii_to_ebcdic[216] = 128;
        Convert.ascii_to_ebcdic[217] = 253;
        Convert.ascii_to_ebcdic[218] = 254;
        Convert.ascii_to_ebcdic[219] = 251;
        Convert.ascii_to_ebcdic[220] = 252;
        Convert.ascii_to_ebcdic[221] = 186;
        Convert.ascii_to_ebcdic[222] = 174;
        Convert.ascii_to_ebcdic[223] = 89;
        Convert.ascii_to_ebcdic[224] = 68;
        Convert.ascii_to_ebcdic[225] = 69;
        Convert.ascii_to_ebcdic[226] = 66;
        Convert.ascii_to_ebcdic[227] = 70;
        Convert.ascii_to_ebcdic[228] = 67;
        Convert.ascii_to_ebcdic[229] = 71;
        Convert.ascii_to_ebcdic[230] = 156;
        Convert.ascii_to_ebcdic[231] = 72;
        Convert.ascii_to_ebcdic[232] = 84;
        Convert.ascii_to_ebcdic[233] = 81;
        Convert.ascii_to_ebcdic[234] = 82;
        Convert.ascii_to_ebcdic[235] = 83;
        Convert.ascii_to_ebcdic[236] = 88;
        Convert.ascii_to_ebcdic[237] = 85;
        Convert.ascii_to_ebcdic[238] = 86;
        Convert.ascii_to_ebcdic[239] = 87;
        Convert.ascii_to_ebcdic[240] = 140;
        Convert.ascii_to_ebcdic[241] = 73;
        Convert.ascii_to_ebcdic[242] = 205;
        Convert.ascii_to_ebcdic[243] = 206;
        Convert.ascii_to_ebcdic[244] = 203;
        Convert.ascii_to_ebcdic[245] = 207;
        Convert.ascii_to_ebcdic[246] = 204;
        Convert.ascii_to_ebcdic[247] = 225;
        Convert.ascii_to_ebcdic[248] = 112;
        Convert.ascii_to_ebcdic[249] = 221;
        Convert.ascii_to_ebcdic[250] = 222;
        Convert.ascii_to_ebcdic[251] = 219;
        Convert.ascii_to_ebcdic[252] = 220;
        Convert.ascii_to_ebcdic[253] = 141;
        Convert.ascii_to_ebcdic[254] = 142;
        Convert.ascii_to_ebcdic[255] = 223;
        Convert.ebcdic_to_ascii[0] = 0;
        Convert.ebcdic_to_ascii[1] = 1;
        Convert.ebcdic_to_ascii[2] = 2;
        Convert.ebcdic_to_ascii[3] = 3;
        Convert.ebcdic_to_ascii[4] = 156;
        Convert.ebcdic_to_ascii[5] = 9;
        Convert.ebcdic_to_ascii[6] = 134;
        Convert.ebcdic_to_ascii[7] = 127;
        Convert.ebcdic_to_ascii[8] = 151;
        Convert.ebcdic_to_ascii[9] = 141;
        Convert.ebcdic_to_ascii[10] = 142;
        Convert.ebcdic_to_ascii[11] = 11;
        Convert.ebcdic_to_ascii[12] = 12;
        Convert.ebcdic_to_ascii[13] = 13;
        Convert.ebcdic_to_ascii[14] = 14;
        Convert.ebcdic_to_ascii[15] = 15;
        Convert.ebcdic_to_ascii[16] = 16;
        Convert.ebcdic_to_ascii[17] = 17;
        Convert.ebcdic_to_ascii[18] = 18;
        Convert.ebcdic_to_ascii[19] = 19;
        Convert.ebcdic_to_ascii[20] = 157;
        Convert.ebcdic_to_ascii[21] = 133;
        Convert.ebcdic_to_ascii[22] = 8;
        Convert.ebcdic_to_ascii[23] = 135;
        Convert.ebcdic_to_ascii[24] = 24;
        Convert.ebcdic_to_ascii[25] = 25;
        Convert.ebcdic_to_ascii[26] = 146;
        Convert.ebcdic_to_ascii[27] = 143;
        Convert.ebcdic_to_ascii[28] = 28;
        Convert.ebcdic_to_ascii[29] = 29;
        Convert.ebcdic_to_ascii[30] = 30;
        Convert.ebcdic_to_ascii[31] = 31;
        Convert.ebcdic_to_ascii[32] = 128;
        Convert.ebcdic_to_ascii[33] = 129;
        Convert.ebcdic_to_ascii[34] = 130;
        Convert.ebcdic_to_ascii[35] = 131;
        Convert.ebcdic_to_ascii[36] = 132;
        Convert.ebcdic_to_ascii[37] = 10;
        Convert.ebcdic_to_ascii[38] = 23;
        Convert.ebcdic_to_ascii[39] = 27;
        Convert.ebcdic_to_ascii[40] = 136;
        Convert.ebcdic_to_ascii[41] = 137;
        Convert.ebcdic_to_ascii[42] = 138;
        Convert.ebcdic_to_ascii[43] = 139;
        Convert.ebcdic_to_ascii[44] = 140;
        Convert.ebcdic_to_ascii[45] = 5;
        Convert.ebcdic_to_ascii[46] = 6;
        Convert.ebcdic_to_ascii[47] = 7;
        Convert.ebcdic_to_ascii[48] = 144;
        Convert.ebcdic_to_ascii[49] = 145;
        Convert.ebcdic_to_ascii[50] = 22;
        Convert.ebcdic_to_ascii[51] = 147;
        Convert.ebcdic_to_ascii[52] = 148;
        Convert.ebcdic_to_ascii[53] = 149;
        Convert.ebcdic_to_ascii[54] = 150;
        Convert.ebcdic_to_ascii[55] = 4;
        Convert.ebcdic_to_ascii[56] = 152;
        Convert.ebcdic_to_ascii[57] = 153;
        Convert.ebcdic_to_ascii[58] = 154;
        Convert.ebcdic_to_ascii[59] = 155;
        Convert.ebcdic_to_ascii[60] = 20;
        Convert.ebcdic_to_ascii[61] = 21;
        Convert.ebcdic_to_ascii[62] = 158;
        Convert.ebcdic_to_ascii[63] = 26;
        Convert.ebcdic_to_ascii[64] = 32;
        Convert.ebcdic_to_ascii[65] = 160;
        Convert.ebcdic_to_ascii[66] = 226;
        Convert.ebcdic_to_ascii[67] = 228;
        Convert.ebcdic_to_ascii[68] = 224;
        Convert.ebcdic_to_ascii[69] = 225;
        Convert.ebcdic_to_ascii[70] = 227;
        Convert.ebcdic_to_ascii[71] = 229;
        Convert.ebcdic_to_ascii[72] = 231;
        Convert.ebcdic_to_ascii[73] = 241;
        Convert.ebcdic_to_ascii[74] = 162;
        Convert.ebcdic_to_ascii[75] = 46;
        Convert.ebcdic_to_ascii[76] = 60;
        Convert.ebcdic_to_ascii[77] = 40;
        Convert.ebcdic_to_ascii[78] = 43;
        Convert.ebcdic_to_ascii[79] = 124;
        Convert.ebcdic_to_ascii[80] = 38;
        Convert.ebcdic_to_ascii[81] = 233;
        Convert.ebcdic_to_ascii[82] = 234;
        Convert.ebcdic_to_ascii[83] = 235;
        Convert.ebcdic_to_ascii[84] = 232;
        Convert.ebcdic_to_ascii[85] = 237;
        Convert.ebcdic_to_ascii[86] = 238;
        Convert.ebcdic_to_ascii[87] = 239;
        Convert.ebcdic_to_ascii[88] = 236;
        Convert.ebcdic_to_ascii[89] = 223;
        Convert.ebcdic_to_ascii[90] = 33;
        Convert.ebcdic_to_ascii[91] = 36;
        Convert.ebcdic_to_ascii[92] = 42;
        Convert.ebcdic_to_ascii[93] = 41;
        Convert.ebcdic_to_ascii[94] = 59;
        Convert.ebcdic_to_ascii[95] = 94;
        Convert.ebcdic_to_ascii[96] = 45;
        Convert.ebcdic_to_ascii[97] = 47;
        Convert.ebcdic_to_ascii[98] = 194;
        Convert.ebcdic_to_ascii[99] = 196;
        Convert.ebcdic_to_ascii[100] = 192;
        Convert.ebcdic_to_ascii[101] = 193;
        Convert.ebcdic_to_ascii[102] = 195;
        Convert.ebcdic_to_ascii[103] = 197;
        Convert.ebcdic_to_ascii[104] = 199;
        Convert.ebcdic_to_ascii[105] = 209;
        Convert.ebcdic_to_ascii[106] = 166;
        Convert.ebcdic_to_ascii[107] = 44;
        Convert.ebcdic_to_ascii[108] = 37;
        Convert.ebcdic_to_ascii[109] = 95;
        Convert.ebcdic_to_ascii[110] = 62;
        Convert.ebcdic_to_ascii[111] = 63;
        Convert.ebcdic_to_ascii[112] = 248;
        Convert.ebcdic_to_ascii[113] = 201;
        Convert.ebcdic_to_ascii[114] = 202;
        Convert.ebcdic_to_ascii[115] = 203;
        Convert.ebcdic_to_ascii[116] = 200;
        Convert.ebcdic_to_ascii[117] = 205;
        Convert.ebcdic_to_ascii[118] = 206;
        Convert.ebcdic_to_ascii[119] = 207;
        Convert.ebcdic_to_ascii[120] = 204;
        Convert.ebcdic_to_ascii[121] = 96;
        Convert.ebcdic_to_ascii[122] = 58;
        Convert.ebcdic_to_ascii[123] = 35;
        Convert.ebcdic_to_ascii[124] = 64;
        Convert.ebcdic_to_ascii[125] = 39;
        Convert.ebcdic_to_ascii[126] = 61;
        Convert.ebcdic_to_ascii[127] = 34;
        Convert.ebcdic_to_ascii[128] = 216;
        Convert.ebcdic_to_ascii[129] = 97;
        Convert.ebcdic_to_ascii[130] = 98;
        Convert.ebcdic_to_ascii[131] = 99;
        Convert.ebcdic_to_ascii[132] = 100;
        Convert.ebcdic_to_ascii[133] = 101;
        Convert.ebcdic_to_ascii[134] = 102;
        Convert.ebcdic_to_ascii[135] = 103;
        Convert.ebcdic_to_ascii[136] = 104;
        Convert.ebcdic_to_ascii[137] = 105;
        Convert.ebcdic_to_ascii[138] = 171;
        Convert.ebcdic_to_ascii[139] = 187;
        Convert.ebcdic_to_ascii[140] = 240;
        Convert.ebcdic_to_ascii[141] = 253;
        Convert.ebcdic_to_ascii[142] = 254;
        Convert.ebcdic_to_ascii[143] = 177;
        Convert.ebcdic_to_ascii[144] = 176;
        Convert.ebcdic_to_ascii[145] = 106;
        Convert.ebcdic_to_ascii[146] = 107;
        Convert.ebcdic_to_ascii[147] = 108;
        Convert.ebcdic_to_ascii[148] = 109;
        Convert.ebcdic_to_ascii[149] = 110;
        Convert.ebcdic_to_ascii[150] = 111;
        Convert.ebcdic_to_ascii[151] = 112;
        Convert.ebcdic_to_ascii[152] = 113;
        Convert.ebcdic_to_ascii[153] = 114;
        Convert.ebcdic_to_ascii[154] = 170;
        Convert.ebcdic_to_ascii[155] = 186;
        Convert.ebcdic_to_ascii[156] = 230;
        Convert.ebcdic_to_ascii[157] = 184;
        Convert.ebcdic_to_ascii[158] = 198;
        Convert.ebcdic_to_ascii[159] = 164;
        Convert.ebcdic_to_ascii[160] = 181;
        Convert.ebcdic_to_ascii[161] = 126;
        Convert.ebcdic_to_ascii[162] = 115;
        Convert.ebcdic_to_ascii[163] = 116;
        Convert.ebcdic_to_ascii[164] = 117;
        Convert.ebcdic_to_ascii[165] = 118;
        Convert.ebcdic_to_ascii[166] = 119;
        Convert.ebcdic_to_ascii[167] = 120;
        Convert.ebcdic_to_ascii[168] = 121;
        Convert.ebcdic_to_ascii[169] = 122;
        Convert.ebcdic_to_ascii[170] = 161;
        Convert.ebcdic_to_ascii[171] = 191;
        Convert.ebcdic_to_ascii[172] = 208;
        Convert.ebcdic_to_ascii[173] = 91;
        Convert.ebcdic_to_ascii[174] = 222;
        Convert.ebcdic_to_ascii[175] = 174;
        Convert.ebcdic_to_ascii[176] = 172;
        Convert.ebcdic_to_ascii[177] = 163;
        Convert.ebcdic_to_ascii[178] = 165;
        Convert.ebcdic_to_ascii[179] = 183;
        Convert.ebcdic_to_ascii[180] = 169;
        Convert.ebcdic_to_ascii[181] = 167;
        Convert.ebcdic_to_ascii[182] = 182;
        Convert.ebcdic_to_ascii[183] = 188;
        Convert.ebcdic_to_ascii[184] = 189;
        Convert.ebcdic_to_ascii[185] = 190;
        Convert.ebcdic_to_ascii[186] = 221;
        Convert.ebcdic_to_ascii[187] = 168;
        Convert.ebcdic_to_ascii[188] = 175;
        Convert.ebcdic_to_ascii[189] = 93;
        Convert.ebcdic_to_ascii[190] = 180;
        Convert.ebcdic_to_ascii[191] = 215;
        Convert.ebcdic_to_ascii[192] = 123;
        Convert.ebcdic_to_ascii[193] = 65;
        Convert.ebcdic_to_ascii[194] = 66;
        Convert.ebcdic_to_ascii[195] = 67;
        Convert.ebcdic_to_ascii[196] = 68;
        Convert.ebcdic_to_ascii[197] = 69;
        Convert.ebcdic_to_ascii[198] = 70;
        Convert.ebcdic_to_ascii[199] = 71;
        Convert.ebcdic_to_ascii[200] = 72;
        Convert.ebcdic_to_ascii[201] = 73;
        Convert.ebcdic_to_ascii[202] = 173;
        Convert.ebcdic_to_ascii[203] = 244;
        Convert.ebcdic_to_ascii[204] = 246;
        Convert.ebcdic_to_ascii[205] = 242;
        Convert.ebcdic_to_ascii[206] = 243;
        Convert.ebcdic_to_ascii[207] = 245;
        Convert.ebcdic_to_ascii[208] = 125;
        Convert.ebcdic_to_ascii[209] = 74;
        Convert.ebcdic_to_ascii[210] = 75;
        Convert.ebcdic_to_ascii[211] = 76;
        Convert.ebcdic_to_ascii[212] = 77;
        Convert.ebcdic_to_ascii[213] = 78;
        Convert.ebcdic_to_ascii[214] = 79;
        Convert.ebcdic_to_ascii[215] = 80;
        Convert.ebcdic_to_ascii[216] = 81;
        Convert.ebcdic_to_ascii[217] = 82;
        Convert.ebcdic_to_ascii[218] = 185;
        Convert.ebcdic_to_ascii[219] = 251;
        Convert.ebcdic_to_ascii[220] = 252;
        Convert.ebcdic_to_ascii[221] = 249;
        Convert.ebcdic_to_ascii[222] = 250;
        Convert.ebcdic_to_ascii[223] = 255;
        Convert.ebcdic_to_ascii[224] = 92;
        Convert.ebcdic_to_ascii[225] = 247;
        Convert.ebcdic_to_ascii[226] = 83;
        Convert.ebcdic_to_ascii[227] = 84;
        Convert.ebcdic_to_ascii[228] = 85;
        Convert.ebcdic_to_ascii[229] = 86;
        Convert.ebcdic_to_ascii[230] = 87;
        Convert.ebcdic_to_ascii[231] = 88;
        Convert.ebcdic_to_ascii[232] = 89;
        Convert.ebcdic_to_ascii[233] = 90;
        Convert.ebcdic_to_ascii[234] = 178;
        Convert.ebcdic_to_ascii[235] = 212;
        Convert.ebcdic_to_ascii[236] = 214;
        Convert.ebcdic_to_ascii[237] = 210;
        Convert.ebcdic_to_ascii[238] = 211;
        Convert.ebcdic_to_ascii[239] = 213;
        Convert.ebcdic_to_ascii[240] = 48;
        Convert.ebcdic_to_ascii[241] = 49;
        Convert.ebcdic_to_ascii[242] = 50;
        Convert.ebcdic_to_ascii[243] = 51;
        Convert.ebcdic_to_ascii[244] = 52;
        Convert.ebcdic_to_ascii[245] = 53;
        Convert.ebcdic_to_ascii[246] = 54;
        Convert.ebcdic_to_ascii[247] = 55;
        Convert.ebcdic_to_ascii[248] = 56;
        Convert.ebcdic_to_ascii[249] = 57;
        Convert.ebcdic_to_ascii[250] = 179;
        Convert.ebcdic_to_ascii[251] = 219;
        Convert.ebcdic_to_ascii[252] = 220;
        Convert.ebcdic_to_ascii[253] = 217;
        Convert.ebcdic_to_ascii[254] = 218;
        Convert.ebcdic_to_ascii[255] = 0;
    }
}

