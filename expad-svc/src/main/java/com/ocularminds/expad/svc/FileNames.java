
package com.ocularminds.expad.svc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileNames {
    public static final boolean DELETABLE = true;
    public static final boolean NON_DELETABLE = false;
    public static final String comma = ",";
    public static final String space = " ";
    public static final String empty = "";
    static String expiryDate = "";
    static String revExpiry = "";
    static String PIN_OFFSET = "";
    public static String BAD_FILE = "bad.txt";
    public static String PHOTO_FILE = "expad.photo.xml";
    public static String PERSO_FILE = "perso.txt";
    public static String PIN_FILE = "pin.txt";
    public static String OUTPUT_FILE = "output.txt";
    public static String ACCOUNTS_FILE = "accounts.txt";
    public static String ACCOUNT_OVERRIDES_FILE = "accountoverridelimits.txt";
    public static String ACCOUNT_BALANCES_FILE = "accountbalances.txt";
    public static String CARDS_FILE = "cards.txt";
    public static String CARD_ACCOUNTS_FILE = "cardaccounts.txt";
    public static String CARD_OVERRIDES_FILE = "cardoverridelimits.txt";
    public static String STATEMENTS_FILE = "statements.txt";
    public static final String PIN_DIRECTORY = System.getenv("OCULAR_HOME") + File.separator + "expad" + File.separator + "downloads" + File.separator + "pin";
    public static final String CARD_DIRECTORY = System.getenv("OCULAR_HOME") + File.separator + "expad" + File.separator + "downloads" + File.separator + "card";
    public static final String PAN_DIRECTORY = System.getenv("OCULAR_HOME") + File.separator + "expad" + File.separator + "downloads" + File.separator + "pan";
    public static final String ZIP_DIRECTORY = System.getenv("OCULAR_HOME") + File.separator + "expad" + File.separator + "downloads" + File.separator + "zip";
    public static final String DOWNLOAD_DIR = System.getenv("OCULAR_HOME") + File.separator + "expad" + File.separator + "downloads" + File.separator + "pin";
    public static final String UPLOAD_DIR = System.getenv("OCULAR_HOME") + File.separator + "expad" + File.separator + "uploads";
    
    static SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    static SimpleDateFormat timeFormat = new SimpleDateFormat("hhmmSSS");
}
