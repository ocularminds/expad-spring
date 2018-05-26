package com.ocularminds.expad.app.route;

import com.ocularminds.expad.app.DuplicateNameException;
import com.ocularminds.expad.app.EmptyDataException;
import com.ocularminds.expad.app.NotFoundException;
import com.ocularminds.expad.app.ValidationError;
import com.ocularminds.expad.app.ValidationErrors;
import com.ocularminds.expad.app.service.CardService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class FileProcess implements Route {

    @Value("{expad.file.directory}")
    String directory;

    SimpleDateFormat sdf;
    String serverZipRepository;
    String zipOutput;
    String downloadUrl;
    CardService service;
    private final MessageSource messageSource;
    static final Logger LOG = Logger.getLogger(FileProcess.class.getName());

    @Autowired
    public FileProcess(CardService cardService, final MessageSource messageSource) {
        this.service = cardService;
        this.sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-sss");
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/pan", method = GET, produces = "text/xml")
    public String pin(Map<String, String> request) throws IOException {
        String strQuantity = request.get("COUNT");
        String distribution = request.get("DIST");
        String productCode = request.get("PRODUCT");
        String network = request.get("network");
        StringTokenizer tokenizer = new StringTokenizer(distribution, "-");
        String totalSelections = tokenizer.nextToken();
        String branches = tokenizer.nextToken();
        String[] selectedBranches = branches.split(",");
        StringBuilder sb = new StringBuilder();
        String msg = "Pan generator completed Successfully.";
        if (strQuantity == null) {
            msg = "Pan generator failed. Try again later.";
        } else {
            try {
                int quantity = Integer.parseInt(strQuantity);
                service.generatePanForCards(quantity, selectedBranches, productCode, network);
            } catch (Exception e) {
                e.printStackTrace();
                msg = "Process aborted. " + e.getMessage();
            }
        }
        sb.append("<PinReport>\n");
        sb.append("<ResponseMessage>").append(msg).append("</ResponseMessage>\n");
        sb.append("</PinReport>");
        return sb.toString();
    }

    @RequestMapping(value = "/card", method = GET, produces = "text/xml")
    public String perso(Map<String, String> request) throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String productCode = request.get("PRODUCT");
            List list = service.findPanForDownload(productCode);
            this.zipOutput = "PAN-" + this.sdf.format(new Date()) + ".zip";
            this.serverZipRepository = this.serverZipRepository + this.zipOutput;
            this.downloadUrl = "ZipFileDownloadServlet.do?url=" + this.zipOutput + "&amp;src=pan";
            String msg = "File Processing completed Successful.[" + list.size() + "] records affected";
            if (list.isEmpty()) {
                msg = "Record not available for download!";
                this.downloadUrl = "#";
            }
            sb.append("<PinReport>\n");
            sb.append("<ResponseMessage>").append(msg).append("</ResponseMessage>\n");
            sb.append("<DownloadUrl>").append(this.downloadUrl).append("</DownloadUrl>");
            sb.append("</PinReport>");
            return sb.toString();
        } catch (Exception ex) {
            Logger.getLogger(FileProcess.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    @RequestMapping(value = "/prp", method = GET, produces = "text/xml")
    public String reverseCardTransaction(Map<String, String> request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String pan = request.get("PAN");
        //service..reversePostedTransaction(pan);
        String msg = "Transaction Successfully reversed";
        sb.append("<PinReport>\n");
        sb.append("<ResponseMessage>").append(msg).append("</ResponseMessage>\n");
        sb.append("</PinReport>");
        return sb.toString();
    }

    @RequestMapping(value = "/batch", method = GET, produces = "text/xml")
    public String download(Map<String, String> request) throws IOException {
        try {
            this.zipOutput = "CARD-" + this.sdf.format(new Date()) + ".zip";
            this.serverZipRepository = this.serverZipRepository + this.zipOutput;
            this.downloadUrl = "ZipFileDownloadServlet.do?url=" + this.zipOutput + "&amp;src=card";
            StringBuilder sb = new StringBuilder();
            String process = request.get("BATCHID");
            List list = null;
            list = service.findViaCardForDownload();
            //service.generateCardFiles(list, this.serverZipRepository);
            String msg = "File Processing completed Successful.[" + list.size() + "] records affected. Please check the contents of bad.txt file for any exceptions.";
            if (list.isEmpty()) {
                msg = "Record not available for download!";
                this.downloadUrl = "#";
            }
            sb.append("<PinReport>\n");
            sb.append("<ResponseMessage>").append(msg).append("</ResponseMessage>\n");
            sb.append("<DownloadUrl>").append(this.downloadUrl).append("</DownloadUrl>");
            sb.append("</PinReport>");
            return sb.toString();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            return "";
        }
    }

    @ExceptionHandler(EmptyDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound() {
        // No-op, return empty 404
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFoundException() {
        // No-op, return empty 404
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Override
    public ValidationErrors processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return validate(fieldErrors, messageSource);
    }

    /**
     *
     * @param e Duplicate Exception
     * @return
     */
    @ExceptionHandler(DuplicateNameException.class)
    @Override
    public ResponseEntity<ValidationError> duplicateException(final DuplicateNameException e) {
        return error(e, HttpStatus.TOO_MANY_REQUESTS, e.getMessage());
    }
}
