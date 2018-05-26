
package com.ocularminds.expad.app.generator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class HostAuth {
    public static String url;
    public static String username;
    public static String password;
    public static Properties properties;

    public static void init(String network) {
        username = (String)properties.get(network + "_USR");
        password = (String)properties.get(network + "_PAS");
        url = (String)properties.get(network + "_URL");
    }

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("c:\\expad\\ocularminds.network.properties"));
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not find the property file: stream.network.properties");
        }
        catch (IOException ioe) {
            System.out.println("Error reading network configuration -" + ioe);
        }
    }
}

