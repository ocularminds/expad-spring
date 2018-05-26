package com.ocularminds.expad.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compressor {

    public static void compress(String from, String to) throws IOException {
        int bytesRead;
        GZIPOutputStream out;
        try (FileInputStream in = new FileInputStream(from)) {
            out = new GZIPOutputStream(new FileOutputStream(to));
            byte[] buffer = new byte[4096];
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        out.close();
    }

    public static void compressFolder(String dir, String zipfile) throws IOException, IllegalArgumentException {
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

    public static void main(String[] args) throws IOException {
        String from = ".";
        File f = new File(from);
        boolean directory = f.isDirectory();
        Compressor.compressFolder(from, from + ".zip");
        Compressor.compress(from, from + ".gz");
    }
}
