/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public interface FileProducer {
    
    static final Logger LOG = LoggerFactory.getLogger(FileProducer.class);

    void produce();  
    
    default void write(String directory, String file, String data) throws Exception {
        FileWriter fw = new FileWriter(new File(directory, file));
        try (PrintWriter out = new PrintWriter(new BufferedWriter(fw))) {
            out.print(data);
            out.flush();
        }
    }

    default int createDirectory(String folder, boolean isDeletable) throws Exception {
        File toSendFile = new File(folder);
        int total = 0;
        if (!toSendFile.exists()) {
            try {
                toSendFile.mkdirs();
            } catch (Exception e) {
                LOG.info("Could not create directory.Service Jam  : Files in {} folder might not have been processed.", folder, e);
            }
        }
        if (toSendFile.isDirectory()) {
            String[] filenames = toSendFile.list();
            total = filenames.length;
            if (isDeletable && filenames.length > 1) {
                for (int i = 0; i < filenames.length; ++i) {
                    String fileName1 = folder + "\\" + filenames[i];
                    File temp = new File(fileName1);
                    temp.delete();
                }
            }
        }
        return total;
    }

    default void compress(String dir, String zipfile) throws IOException, IllegalArgumentException {
        File d = new File(dir);
        if (!d.isDirectory()) {
            throw new IllegalArgumentException("Not a directory:  " + dir);
        }
        String[] entries = d.list();
        byte[] buffer = new byte[4096];
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile))) {
            for (int i = 0; i < entries.length; ++i) {
                int bytesRead;
                File f = new File(d, entries[i]);
                if (f.isDirectory()) {
                    continue;
                }
                try (FileInputStream in = new FileInputStream(f)) {
                    ZipEntry entry = new ZipEntry(f.getPath());
                    out.putNextEntry(entry);
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }
}
