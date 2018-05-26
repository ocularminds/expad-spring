package com.ocularminds.expad.svc.jobs;

import com.ocularminds.expad.svc.CardDownloadService;
import com.ocularminds.expad.svc.builder.IssuedCardFileProducer;
import com.ocularminds.expad.vao.Merchant;
import java.util.List;
import java.util.Properties;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StudentCardFilePushJob {

    Properties properties = new Properties();
    String folder = "c:\\postcard\\student\\";
    String repository = "c:\\postcard\\student\\archive\\";
    CardDownloadService downloader;
    final static org.slf4j.Logger LOG = LoggerFactory.getLogger(StudentCardFilePushJob.class);

    @Autowired
    public StudentCardFilePushJob(final CardDownloadService cardDownloadService) {
        this.downloader = cardDownloadService;
    }

    @Scheduled(fixedRate = 5000)
    public void push() {
        try {
            List data = downloader.download("S");
            //new IssuedCardFileProducer(repository, this.folder, "S", data).produce();
            List<Merchant> merchants;
            try {
                merchants = downloader.merchants();
                for (int x = 0; x < merchants.size(); ++x) {
                    Merchant s = (Merchant) merchants.get(x);
                    String f = this.folder + "-" + s.getAcronymn().trim();
                    new IssuedCardFileProducer(repository, f, "S", data).produce();   
                }
            } catch (Exception ex) {
                LOG.error("error pushing file ...", ex);
            }
        } catch (Exception ex) {   
            LOG.error("Error pushing file ", ex);
        }
    }
}
