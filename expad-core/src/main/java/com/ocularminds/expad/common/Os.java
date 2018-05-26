
package com.ocularminds.expad.common;

import java.util.Map;

public class Os {
    private static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return Os.getOsName().startsWith("Windows");
    }

    public static boolean isUnix() {
        return Os.getOsName().startsWith("Linux");
    }

    public static boolean isMacX() {
        return Os.getOsName().startsWith("Mac");
    }

    public static boolean isSolarisPlatform() {
        return Os.getOsName().startsWith("Solaris");
    }

    public static String keyFilePath() {
        String cryptoUrl = System.getenv("OCULAR_HOME");
        return cryptoUrl + Os.osSlash() + "security" + Os.osSlash() + "keystore.properties";
    }

    public static String osSlash() {
        String slash = "";
        if (Os.isWindows()) {
            return "\\";
        }
        if (Os.isUnix()) {
            return "/";
        }
        if (Os.isMacX()) {
            return "/";
        }
        return slash;
    }

    public static void main(String[] args) {
        System.out.println("Os Name = " + Os.getOsName());
        System.out.println("PATH = " + System.getenv("PATH"));
        System.out.println("JAVA_HOME = " + System.getenv("JAVA_HOME"));
        System.out.println("JBOSS_HOME = " + System.getenv("JBOSS_HOME"));
        System.out.println("OCULAR_HOME = " + System.getenv("OCULAR_HOME"));
        Map<String, String> env = System.getenv();
        for (Map.Entry<String, String> entry : env.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}

