/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.util.ExcelFile
 */
package com.ocularminds.expad.svc.builder;

import com.ocularminds.expad.svc.FileNames;
import com.ocularminds.util.ExcelFile;
import java.io.PrintStream;
import java.util.ArrayList;

public final class PrePaidFileBuilder extends FileNames {
    public static ArrayList getUploadedAccounts(String uploadedFile) {
        ExcelFile records = null;
        try {
            records = new ExcelFile(uploadedFile);
        } catch (Exception e) {
            System.out.println("[EXPAD]: [EXPAD]: Error reading excel file." + e);
        }
        return records;
    }
}

