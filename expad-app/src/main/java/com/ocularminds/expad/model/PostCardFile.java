/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.model;

import java.io.Serializable;
import java.util.Map;

public class PostCardFile implements Serializable {
    private String downloadFolder;
    private Map bufferedData;

    public PostCardFile(String downloadFolder, Map bufferedData) {
        this.setDownloadFolder(downloadFolder);
        this.setBufferedData(bufferedData);
    }

    public String getDownloadFolder() {
        return this.downloadFolder;
    }

    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    public Map getBufferedData() {
        return this.bufferedData;
    }

    public void setBufferedData(Map bufferedData) {
        this.bufferedData = bufferedData;
    }
}

