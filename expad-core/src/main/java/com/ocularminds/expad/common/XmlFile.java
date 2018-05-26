package com.ocularminds.expad.common;

import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlFile {

    public static final int FILE_SOURCE = 0;
    public static final int URL_SOURCE = 1;
    public static final int SBUF_SOURCE = 2;
    private static final Logger LOG = LoggerFactory.getLogger(XmlFile.class);

    public static Document getDocument(String url, int source) throws Exception {
        Document doc;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        switch (source) {
            case 1: {
                URL soapURI = new URL(url);
                InputStream is = soapURI.openStream();
                doc = builder.parse(is);
                break;
            }
            case 0: {
                File f = new File(url);
                doc = builder.parse(f);
                break;
            }
            case 2: {
                StringBufferInputStream sbis = new StringBufferInputStream(url);
                doc = builder.parse(sbis);
                break;
            }
            default: {
                File f0 = new File(url);
                doc = builder.parse(f0);
            }
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static ArrayList read(String url, String obj, int source) {
        ArrayList<Element> records = new ArrayList<>();
        try {
            Document doc = XmlFile.getDocument(url, source);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName(obj);
            for (int x = 0; x < nodes.getLength(); ++x) {
                Node node = nodes.item(x);
                if (node.getNodeType() != 1) {
                    continue;
                }
                Element data = (Element) node;
                records.add(data);
            }
        } catch (Exception e) {
           LOG.error("error reading xls file ",e);
        }
        return records;
    }

    private void print(Element data) {
        LOG.info("First Name : " + XmlFile.tagValue("firstname", data));
        LOG.info("Last Name : " + XmlFile.tagValue("lastname", data));
        LOG.info("Nick Name : " + XmlFile.tagValue("nickname", data));
        LOG.info("Salary : " + XmlFile.tagValue("salary", data));
    }

    public static String tagValue(String tag, Element data) {
        NodeList nlList = data.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        return nValue == null ? null : nValue.getNodeValue();
    }
}
