package com.ocularminds.expad.app.view;

import com.ocularminds.expad.Fault;
import com.ocularminds.expad.app.service.AdminService;
import com.ocularminds.expad.app.service.BulkCardService;
import com.ocularminds.expad.app.service.CardService;
import com.ocularminds.expad.model.Account;
import com.ocularminds.expad.model.AccountError;
import com.ocularminds.expad.model.Card;
import com.ocularminds.expad.model.User;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.Product;
import java.io.BufferedOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/bulk")
public class Bulk implements Page {

    @Value("{expad.key.dir}")
    String uploadDirectory;

    @Value("{expad.archive.dir}")
    String archiveDirectory;

    static final Logger LOG = LoggerFactory.getLogger(Bulk.class);
    final AdminService admin;
    final BulkCardService service;
    final CardService cs;

    @Autowired
    public Bulk(AdminService adminService, CardService cardService, BulkCardService bulkCardService) {
        this.admin = adminService;
        this.service = bulkCardService;
        this.cs = cardService;
    }

    @RequestMapping(value = "/process", method = GET)
    public String pan(HttpSession session, ModelMap model) {
        Company c = admin.config();
        c.setAccountSize(10);
        model.addAttribute("config", c);
        model.addAttribute("merchants", cs.merchants());
        model.addAttribute("products", cs.products());
        return getPage(session, "process", model, new Fault());
    }

    @RequestMapping(value = "/upload", method = GET)
    public String upload(HttpSession session, ModelMap model) {
        List<Product> products = cs.products();
        model.addAttribute("products", products);
        return getPage(session, "upload", model, new Fault());
    }

    @RequestMapping(value = "/upload", method = POST)
    public Fault uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productCode") String productCode,
            @RequestParam("type") String type,
            @RequestParam("network") String network,
            HttpSession session, ModelMap model) {
        if (!new File(uploadDirectory).exists()) {
            new File(uploadDirectory).mkdirs();
        }
        Fault fault;
        String uploaded = uploadDirectory + File.separator + file.getName();
        if (!file.isEmpty()) {
            fault = toFile(file, uploaded);
            if (fault.isFailed()) {
                return fault;
            }
        }
        return accept(session, productCode, network, type, uploaded);

    }

    @RequestMapping(value = "/approval", method = GET)
    public String approval(HttpSession session, ModelMap model) {

        if (session == null) {
            return index(model, new Fault("00", ""));
        }
        User user = (User) session.getAttribute("user");
        try {
            List<Card> cards = cs.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "review", model, new Fault());
    }

    private Fault toFile(MultipartFile file, String output) {
        LOG.info("uploading file...");
        try {
            FileCopyUtils.copy(
                    file.getInputStream(),
                    new BufferedOutputStream(new FileOutputStream(output))
            );
            //return "redirect:/publisher/browse";
            return new Fault("00", "Ok");
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Sorry, file could not be uploaded:");
            sb.append(file.getName());
            sb.append(" => ".concat(e.getMessage()));
            return new Fault("90", sb.toString());
            //redirectAttributes.addFlashAttribute("message","You failed to publish " + name + " => " + e.getMessage());
        }
    }

    private Fault accept(HttpSession session, String productCode, String network, String type, String uploaded) {
        Fault fault;
        String error = "Bulk upload Failed! Please use the valid Excel or XML format.";
        if (type != null && type.equals("xml")) {
            List records = service.acceptXml(uploaded);
            service.processXmlInfo(records);
            List<AccountError> errors = service.getFailedAccounts();
            String success = "Bulk upload completed successfully.";
            if (records.size() > 0) {
                fault = new Fault("00", success);
            } else {
                fault = new Fault("31", error);
            }
            fault.setData(errors);
        } else {
            try {
                fault = process(session, uploaded, productCode, network, error);
            } catch (Exception ex) {
                fault = new Fault("90", "Sorry, bulk upload could not be processed completely. ");
                LOG.error("bulk upload error ", ex);
            }
        }
        return fault;
    }

    private Fault process(HttpSession session, String uploaded,
            String productCode, String network, String error) throws Exception {
        Fault fault;
        List records = service.acceptExcel(uploaded);
        service.process(records);
        List<Account> accounts = service.getCorrectAccounts();
        System.out.println("tottal valid accounts - " + accounts.size());
        User user = (User) session.getAttribute("user");
        String userid = (String) session.getAttribute("userId");
        String branchCode = (user != null) ? user.getBranch() : "101";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String zipOutput = "PIN-" + sdf.format(new java.util.Date()) + ".zip";
        archiveDirectory = archiveDirectory + zipOutput;
        String downloadUrl = "dz.zip?.url=" + zipOutput + "&.src=pin";
        service.processBulkCard(records, userid, productCode, branchCode, network);
        service.processCardFiles(records, archiveDirectory);
        String success = "Bulk upload completed successfully. Batch Ref: "
                + service.getBatchReference()
                + "<br><a href='#' onClick=window.open('" + downloadUrl
                + "')>Download Pin File here..</a>";
        if (records.size() > 0) {
            fault = new Fault("00", success);
        } else {
            fault = new Fault("31", error);
        }

        List<AccountError> errors = service.getFailedAccounts();
        fault.setData(errors);
        return fault;
    }
}
