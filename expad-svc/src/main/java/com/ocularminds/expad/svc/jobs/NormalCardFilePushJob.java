
package com.ocularminds.expad.svc.jobs;

import com.ocularminds.expad.svc.CardDownloadService;
import com.ocularminds.expad.svc.builder.IssuedCardFileProducer;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NormalCardFilePushJob {

    Properties properties;
    CardDownloadService downloader;
    String folder = "c:\\postcard\\customer";
    String repository = "c:\\postcard\\archive\\";
    final static Logger LOG = LoggerFactory.getLogger(NormalCardFilePushJob.class);

    @Autowired
    public NormalCardFilePushJob(final CardDownloadService cardDownloadService) {
        this.downloader = cardDownloadService;
    }
    
    @Scheduled(fixedRate = 5000)
    public void push(){
        try {
            List data = downloader.download("C");
            new IssuedCardFileProducer(repository, this.folder, "C", data).produce();
        } catch (Exception ex) {
            LOG.error("Error pushing file ", ex);
        }
    }
}
