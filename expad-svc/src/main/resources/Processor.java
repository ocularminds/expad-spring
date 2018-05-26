/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.svc.jobs;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public class Processor {
    
    public PostCardFile getPostCardFiles(List list) throws Exception {
        String downloadFolder = "c:\\expad\\downloads\\card";
        Map m = PostCardFileBuilder.buildFile(list);
        PostCardFile postcard = new PostCardFile(downloadFolder, m);
        return postcard;
    }

    public PostCardFiles toPostCard(List list) throws Exception {
        return CardFileBuilder.toPostCard(list);
    }

    public void generateCardFiles(List list, String repository) throws Exception {
        Company company = new Configurator().get();
        String downloadFolder = company.getPostCardDirectory();
        try {
            CardFileBuilder.buildFile(list, repository, downloadFolder);
        } catch (Exception ex) {
            String errorMessage = "[EXPAD] Error occured while writing to batchFile:\n";
            LOG.error("error creating card files ", ex);
        }
    }

    public void generatePersonlizationCardFiles(List list, String repository) {
        try {
            PersoFileBuilder.buildFile(list, repository);
        } catch (Exception ex) {
            LOG.error("error creating personalization files ", ex);
        }
        for (int j = 0; j < list.size(); ++j) {
            this.notifyProcessedPan(((Pan) list.get(j)).getNo());
        }
    }
}
