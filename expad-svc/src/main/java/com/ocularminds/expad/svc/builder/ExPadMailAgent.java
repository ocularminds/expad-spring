
package com.ocularminds.expad.svc.builder;

import java.io.PrintStream;
import java.util.Properties;

public class ExPadMailAgent {
    public static void sendMail(Properties properties, String subject, String msg) {
//        try {
//            Properties props = new Properties();
//            props.put("mail.host", (String)properties.get("com.ocularminds.expad.email.host"));
//            Session mailConnection = Session.getInstance((Properties)props, (Authenticator)null);
//            MimeMessage message = new MimeMessage(mailConnection);
//            InternetAddress from = new InternetAddress((String)properties.get("com.ocularminds.expad.email.mailFrom"));
//            InternetAddress to = new InternetAddress((String)properties.get("com.ocularminds.expad.email.mailTo"));
//            message.setContent((Object)msg, "text/plain");
//            message.setFrom((Address)from);
//            message.setRecipient(Message.RecipientType.TO, (Address)to);
//            message.setSubject(subject);
//            Transport.send((Message)message);
//        }
//        catch (Exception ex) {
//            System.out.println("EXPAD: Error sending mail");
//            ex.printStackTrace();
//        }
    }
}

