
package com.ocularminds.expad.svc.jobs;

import com.ocularminds.expad.svc.CardDownloadService;
import com.ocularminds.expad.svc.builder.IssuedCardFileProducer;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NyscCardFilePushJob  {
    
    String folder = "c:\\postcard\\nysc";
    String repository = "c:\\postcard\\archive\\";
    CardDownloadService downloader;
    final static Logger LOG = LoggerFactory.getLogger(NyscCardFilePushJob.class);

    @Autowired
    public NyscCardFilePushJob(final CardDownloadService cardDownloadService) {
        this.downloader = cardDownloadService;
    }
    
    @Scheduled(fixedRate = 5000)
    public void push(){
        try {
            List data = downloader.download("N");
            new IssuedCardFileProducer(repository, this.folder, "N", data).produce();
        } catch (Exception ex) {
            LOG.error("Error pushing file ", ex);
        }
    }
}

