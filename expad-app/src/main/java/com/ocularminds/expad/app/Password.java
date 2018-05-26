package com.ocularminds.expad.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private static final Pattern WORDS_PATTERN = Pattern.compile("\\w+\\.");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("(?=.*[^\\w]).+$");
    private static final Pattern ONLY_DIGITS_PATTERN = Pattern.compile("(?=.*\\d).+$");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile("(?=.*[A-Z]).+$");
    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile("(?=.*[a-z]).+$");

    private static boolean isMatched(Pattern p, String input) {
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public static boolean containsSpecialCharacter(String password) {
        return Password.isMatched(SPECIAL_CHARACTER_PATTERN, password);
    }

    public static boolean containsUpperCase(String password) {
        return Password.isMatched(UPPER_CASE_PATTERN, password);
    }

    public static boolean containsLowerCase(String password) {
        return Password.isMatched(LOWER_CASE_PATTERN, password);
    }

    public static boolean containsNumber(String password) {
        return Password.isMatched(ONLY_DIGITS_PATTERN, password);
    }

    public static boolean isAlphaNumeric(String password) {
        return (Password.containsLowerCase(password) || Password.containsUpperCase(password)) && Password.containsNumber(password);
    }

    public static void main(String[] args) {
        String password1 = "rtX**_";
        String password2 = "1234";
        String password3 = "rtwest8345";
        String password4 = "rtwest8345$";
        String password5 = "rtwest8345'";
        String password6 = "rtwest8345+";
        System.out.println("** Unit Test for Password Policy **");
        System.out.println("**************************************");
        System.out.println(" ");
        System.out.println("Is " + password1 + " alphanumeric ?" + Password.isAlphaNumeric(password1));
        System.out.println("Is " + password2 + " numeric char?" + Password.containsNumber(password3));
        System.out.println("Is " + password2 + " alphanumeric?" + Password.isAlphaNumeric(password2));
        System.out.println("Is " + password3 + " alphanumeric?" + Password.isAlphaNumeric(password3));
        System.out.println("Is " + password3 + " special char?" + Password.containsSpecialCharacter(password3));
        System.out.println("Is " + password4 + " special char?" + Password.containsSpecialCharacter(password4));
        System.out.println("Is " + password5 + " special char?" + Password.containsSpecialCharacter(password5));
        System.out.println("Is " + password6 + " special char?" + Password.containsSpecialCharacter(password6));
        System.out.println("Is " + password1 + " special char?" + Password.containsSpecialCharacter(password1));
        System.out.println("Is " + password1 + " upperca char?" + Password.containsUpperCase(password1));
    }
}
