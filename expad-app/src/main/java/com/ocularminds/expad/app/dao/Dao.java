package com.ocularminds.expad.app.dao;

import com.ocularminds.expad.common.Dates;
import com.ocularminds.expad.crypto.Crypto;
import com.ocularminds.expad.crypto.EncryptionException;
import com.ocularminds.expad.crypto.StringEncrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import javax.sql.DataSource;

public class Dao {

    final Logger logger = LoggerFactory.getLogger(Dao.class);
    Dates dateFormat = new Dates();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    static DataSource ds = null;
    static final Logger LOG = LoggerFactory.getLogger(Crypto.class);

    public String decryptData(String data) {
        try {
            return new StringEncrypter().decrypt(data);
        } catch (EncryptionException ex) {
            java.util.logging.Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String encryptData(String data) throws Exception{
        return new StringEncrypter().encrypt(data);
    }

    public Properties loadDataSource() throws Exception {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("c:\\expad\\ocularminds-ds.xml");
        prop.loadFromXML(fis);
        return prop;
    }

    public void storeDataStore(String host, String con, String userid, String pass, String appsDB, String appsdburl, String appsdbuser, String appsdbpass) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("host-driver-class", this.encryptData(host));
        prop.setProperty("host-connection-url", this.encryptData(con));
        prop.setProperty("host-username", userid);
        prop.setProperty("host-password", this.encryptData(pass));
        prop.setProperty("apps-driver-class", this.encryptData(appsDB));
        prop.setProperty("apps-connection-url", this.encryptData(appsdburl));
        prop.setProperty("apps-username", appsdbuser);
        prop.setProperty("apps-password", this.encryptData(appsdbpass));
        try (FileOutputStream fos = new FileOutputStream("c:\\expad\\ocularminds-ds.xml")) {
            prop.storeToXML(fos, "Ocular-Minds Application Datasource");
        }
    }
    
    public Timestamp getDateTime(String inputDate) {
        Timestamp inputTime = null;
        try {
            if (inputDate == null) {
                inputDate = this.sdf.format(new java.util.Date());
            }
            inputDate = inputDate.replaceAll("/", "-");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
            String strDate = formatter.format(new java.util.Date());
            String transInputDate = inputDate + strDate.substring(10, strDate.length());
            inputTime = new Timestamp(formatter.parse(transInputDate).getTime());
        } catch (ParseException er) {
            logger.error("Error getting datetime ->", er);
        }
        return inputTime;
    }

    public Timestamp getDateTime(java.util.Date inputDate) {
        String strDate = null;
        try {
            strDate = inputDate == null ? this.sdf.format(new java.util.Date()) : this.sdf.format(inputDate);
        } catch (Exception er) {
            logger.info("WARN : Error getting datetime ->" + er);
        }
        return this.getDateTime(strDate);
    }
}
