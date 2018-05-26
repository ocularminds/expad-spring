package com.ocularminds.expad.common;

import com.ocularminds.expad.vao.Company;
import java.security.SecureRandom;
import java.util.Date;

public class CardUtil {

    public static final String NORMAL_CARD = "0";
    public static final String TEMPORARY_CARD = "1";
    public static final String ANONYMOUS_CARD = "2";
    public static final int BRANCH_PRODUCT_RANDOM_FORMAT = 0;
    public static final int BRANCH_RANDOM_PRODUCT_FORMAT = 1;
    public static final int PRODUCT_BRANCH_RANDOM_FORMAT = 2;
    public static final int PRODUCT_RANDOM_BRANCH_FORMAT = 3;
    public static final int RANDOM_BRANCH_PRODUCT_FORMAT = 4;
    public static final int RANDOM_PRODUCT_BRANCH_FORMAT = 5;

    public static String mask(String pan) {
        int stars = 0;
        StringBuilder sb = new StringBuilder();
        char[] characters = pan.toCharArray();
        for (int x = 0; x < characters.length; x++) {
            if (x < 4 || x > pan.length() - 5) {
                sb.append(characters[x]);
                if (x == 3) {
                    sb.append(" ");
                }
            } else {
                stars = stars + 1;
                sb.append("X");
                if (stars % 4 == 0 || x == pan.length() - 5) {
                    sb.append(" ");
                }
            }
        }
        System.out.println("masked pan " + sb.toString());
        return sb.toString();
    }

    public static String toMask(String pan) {
        return pan.substring(4, pan.length() - 4);
    }

    public static String offset() {
        int rand = (int) (Math.random() * 9999);
        return String.format("%04d", rand);
    }

    public static String random(SecureRandom sd, int len) {
        len = len < 0 ? len * -1 : len;
        long d = new Date().getTime() + (long) sd.nextInt(5567878);
        String strRand = Long.toString(d);
        return strRand.substring(strRand.length() - len, strRand.length());
    }

    public static String[] newPan(SecureRandom sd, Company info, String bin, String branchCode, String code, String network, int format, boolean includeCode) {
        String data[] = new String[2];
        String OFFSET = random(sd, 4);
        branchCode = branchCode == null ? "" : branchCode;
        String num = "";
        String rand = random(sd, info.getPanSize() - 1 - bin.trim().length() - branchCode.trim().length() - code.trim().length());
        rand = includeCode ? rand : random(sd, info.getPanSize() - 1 - bin.trim().length() - branchCode.trim().length());
        switch (format) {
            case 0:
                num = includeCode ? bin + branchCode + code + rand : bin + branchCode + rand;
                break;
            case 1:
                num = includeCode ? bin + branchCode + rand + code : bin + branchCode + rand;
                break;

            case 2:
                num = includeCode ? bin + code + branchCode + rand : bin + branchCode + rand;
                break;
            case 3:
                num = includeCode ? bin + code + rand + branchCode : bin + rand + branchCode;
                break;
            case 4:
                num = includeCode ? bin + rand + branchCode + code : bin + rand + branchCode;
                break;

            case 5:
                num = includeCode ? bin + rand + code + branchCode : bin + rand + branchCode;
                break;
            default:
                if (num.length() > info.getPanSize() - 1) {
                    num = num.substring(1, num.length());
                }
        }

        int checkDigit = CardUtil.checkdigit(num);
        data[0] = num.concat(Integer.toString(checkDigit));
        data[1] = OFFSET;
        return data;
    }

    public static int checkdigit(String num) {
        String validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZ_";
        num = num.trim().toUpperCase();
        int sum = 0;
        for (int i = 0; i < num.length(); ++i) {
            char ch = num.charAt(num.length() - i - 1);
            if (validChars.indexOf(ch) == -1) {
                System.out.println("INVALID IDENTIFICATION NO: \"" + ch + "\" is an invalid character");
            }
            int digit = ch - 48;
            int weight = i % 2 == 0 ? 2 * digit - digit / 5 * 9 : digit;
            sum += weight;
        }
        sum = Math.abs(sum) + 10;
        int calculatedCheckDigit = (10 - sum % 10) % 10;
        return calculatedCheckDigit;
    }

    public static void main(String[] args) {
        String pan = "5120000005678912817";
        System.out.println("Mask " + pan + " -> " + CardUtil.mask(pan));
        System.out.println("Acct " + pan + " -> " + CardUtil.toMask(pan));
    }
}
