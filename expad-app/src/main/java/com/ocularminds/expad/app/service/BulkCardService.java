package com.ocularminds.expad.app.service;

import com.ocularminds.expad.common.Oid;
import com.ocularminds.expad.app.repository.Branches;
import com.ocularminds.expad.common.CardUtil;
import com.ocularminds.expad.common.Compressor;
import com.ocularminds.expad.common.Dates;
import com.ocularminds.expad.common.ExcelFile;
import com.ocularminds.expad.common.Strings;
import com.ocularminds.expad.common.XmlFile;
import com.ocularminds.expad.model.Account;
import com.ocularminds.expad.model.AccountError;
import com.ocularminds.expad.model.Card;
import com.ocularminds.expad.model.CustomizedCard;
import com.ocularminds.expad.app.repository.Pans;
import com.ocularminds.expad.common.Commons;
import com.ocularminds.expad.vao.Product;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.Pan;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

@Service
public class BulkCardService {

    @Value("${expad.download.directory}")
    private String downloadDirectory;

    static final Logger LOG = Logger.getLogger(BulkCardService.class.getName());
    private boolean doneDownload;
    private final Dates dateFormat = new Dates();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private List accountRecords;
    private List<AccountError> failedAccounts;
    private List<Account> correctAccounts;
    private Map panRecords;
    private final Map solRecords;
    private String batchReference;
    Company company;
    private final SecureRandom secureRandom;
    CardService service;
    AdminService asb;
    CustomerService cms;
    final Pans pans;
    String expiryDate = "";
    String revExpiry = "";
    StringBuilder pins = null;
    StringBuilder data = null;
    String pinFile = "PinFile.txt";
    String persoFile = "PersoFile.txt";

    @Autowired
    public BulkCardService(final CardService service, final AdminService adminService,
            final CustomerService customerService, final Branches branches, final Pans pans) {
        this.solRecords = branches.mapped();
        this.asb = adminService;
        this.company = adminService.config();
        this.service = service;
        this.cms = customerService;
        this.pans = pans;
        this.failedAccounts = new ArrayList();
        this.secureRandom = Commons.random();
    }

    public Account getAccount(String accountno, List accounts) {
        Account account = null;
        for (int x = 0; x < accounts.size(); ++x) {
            Account tempAccount = (Account) accounts.get(x);
            if (!tempAccount.getAccountNumber().trim().equals(accountno.trim())) {
                continue;
            }
            account = tempAccount;
            break;
        }
        return account;
    }

    public List<Account> getCorrectAccounts() {
        return this.correctAccounts;
    }

    public void setCorrectAccounts(List<Account> correctAccounts) {
        this.correctAccounts = correctAccounts;
    }

    public void setFailedAccounts(List<AccountError> failedAccounts) {
        this.failedAccounts = failedAccounts;
    }

    public List<AccountError> getFailedAccounts() {
        return this.failedAccounts;
    }

    public void setBatchReference(String batchReference) {
        this.batchReference = batchReference;
    }

    public String getBatchReference() {
        return this.batchReference;
    }

    public List getAccountRecords() {
        return this.accountRecords;
    }

    public void setDoneDownload(boolean doneDownload) {
        this.doneDownload = doneDownload;
    }

    public boolean isDoneDownload() {
        return this.doneDownload;
    }

    public String branchPart(ArrayList records, String accountno) {
        String sol = null;
        for (int x = 0; x < records.size(); ++x) {
            String line = (String) records.get(x);
            String[] lines = line.split("-");
            if (lines.length <= 1 || !lines[1].equals(accountno)) {
                continue;
            }
            sol = lines[0];
            break;
        }
        return sol;
    }

    public List acceptExcel(String file) throws Exception {
        return new ExcelFile(file);
    }

    public List acceptXml(String file) {
        LOG.log(Level.INFO, "Accepting upload file name {0}", file);
        if (file.toLowerCase().contains("association")) {
            return XmlFile.read((String) file, (String) "Association", (int) 0);
        }
        return XmlFile.read((String) file, (String) "Students", (int) 0);
    }

    public String buildQuery(List accounts) {
        StringBuilder sb = new StringBuilder();
        sb.append("'");
        for (int x = 0; x < accounts.size(); ++x) {
            String[] a = ((String) accounts.get(x)).split(",");
            sb.append(a[1]);
            if (x >= accounts.size() - 1) {
                continue;
            }
            sb.append("','");
        }
        sb.append("'");
        return sb.toString();
    }

    private boolean isAccountFound(String accountno, List accounts) {
        boolean isFound = this.getAccount(accountno, accounts) != null;
        return isFound;
    }

    public void processXmlInfo(List records) {
        for (int x = 0; x < records.size(); ++x) {
            Element e = (Element) records.get(x);
            try {
                this.insertFile(e);
            } catch (Exception i) {
                LOG.log(Level.SEVERE, "failed processing xml record ", i);
            }
        }
    }

    private void insertFile(Element e) throws Exception {
        String id = XmlFile.tagValue((String) "RegistrationID", (Element) e);
        String fname = XmlFile.tagValue((String) "FirstName", (Element) e);
        String mname = XmlFile.tagValue((String) "MiddleName", (Element) e);
        String lname = XmlFile.tagValue((String) "LastName", (Element) e);
        String sex = XmlFile.tagValue((String) "Gender", (Element) e);
        String type = XmlFile.tagValue((String) "RegistrationType", (Element) e);
        String date = XmlFile.tagValue((String) "RegistrationDate", (Element) e);
        String post = XmlFile.tagValue((String) "Position", (Element) e);
        String no = XmlFile.tagValue((String) "MatricNo", (Element) e);
        String institute;
        String faculty = "";
        String dept = "";
        String session = "";
        if (type != null && type.equalsIgnoreCase("Student")) {
            institute = XmlFile.tagValue((String) "SchoolName", (Element) e);
            faculty = XmlFile.tagValue((String) "Faculty", (Element) e);
            dept = XmlFile.tagValue((String) "Department", (Element) e);
            session = XmlFile.tagValue((String) "Session", (Element) e);
        } else {
            try {
                institute = XmlFile.tagValue((String) "AssociationName", (Element) e);
            } catch (Exception ex) {
                institute = "";
            }
        }
        byte[] photo = Base64.getDecoder().decode((String) XmlFile.tagValue((String) "Passport", (Element) e));
        byte[] signature = Base64.getDecoder().decode((String) XmlFile.tagValue((String) "Signatures", (Element) e));
        String dob = null;
        this.service.create(new CustomizedCard(id, fname, mname, lname, sex, type, institute, date, post, dept, faculty, session, dob, no, photo, signature));
    }

    public void process(List uploaded) {
        LOG.info("Processing Account Info..");
        String filter = " WHERE FORACID IN(" + buildQuery(uploaded) + ") ";
        String extension = "UNION SELECT G.CUST_ID AS CUSTOMER_NO,O.ACCT_NUM AS ACCT_NO,G.ACCT_CLS_FLG,G.ACCT_NAME,G.EMP_ID,G.SCHM_CODE AS ACCT_TYPE,G.SOL_ID AS BRANCH_CODE,G.CLR_BAL_AMT FROM GAM G,OLDACT O WHERE G.FORACID = O.ACCT_NUM AND O.OLD_ACCT_NUM IN(" + buildQuery(uploaded) + ")";
        String bankName = this.company.getName();
        if (bankName.toLowerCase().lastIndexOf("inland") > -1) {
            filter = filter + extension;
        }
        List<AccountError> errors = new ArrayList<>();
        List<Account> found = new ArrayList<>();
        List<Account> accounts = cms.search("ACCT", filter);
        for (int x = 0; x < uploaded.size(); ++x) {
            String[] columns = ((String) uploaded.get(x)).split(",");
            String id = columns[0];
            String accountno = columns[1];
            if (this.isAccountFound(accountno, accounts)) {
                Account a = this.getAccount(accountno, accounts);
                if (columns.length == 4) {
                    a.setBranch(columns[3]);
                }
                found.add(a);
                continue;
            }
            errors.add(new AccountError(id, accountno, "Invalid Account"));
        }
        this.setFailedAccounts(errors);
        this.setCorrectAccounts(found);
        LOG.info("Done processing Account Info.");
    }

    public void processBulkCard(List records, String userid, String productid, String branchCode, String network) throws Exception {
        LOG.info("Processing Bulk Card.");
        this.panRecords = new HashMap();
        String br = Oid.next().get(Oid.Type.SHORT);
        Product product = service.product(productid);
        this.setBatchReference(br);
        for (int x = 0; x < records.size(); ++x) {
            Account account = (Account) records.get(x);
            Pan p = this.getGeneratedPan(Integer.toString(x), account, product, account.getBranch(), network);
            this.createCardInformation(account, userid, this.batchReference, p);
            this.service.finalizeCard(p.getNo(), p.getId());
            LOG.log(Level.INFO, "a/c {0},pan:{1},sn:{2}", new Object[]{account.getAccountNumber(), p.getNo(), p.getId()});
        }
        LOG.info("Done processing Bulk Card.");
    }

    public void processCardFiles(List correctAccountsRecords, String repository) {
        LOG.info("Processing Card Files...");
        this.pins = new StringBuilder();
        this.data = new StringBuilder();
        for (int x = 0; x < correctAccountsRecords.size(); ++x) {
            Account account = (Account) correctAccountsRecords.get(x);
            String blankName = Strings.strpad(account.getAccountName(), 26);
            String blankBranch = (String) this.solRecords.get(account.getBranch());
            Pan pan = (Pan) this.panRecords.get(account.getAccountNumber() + "-" + Integer.toString(x));
            blankBranch = blankBranch != null ? Strings.strpad(blankBranch, 26) : Strings.strpad(account.getBranch(), 26);
            this.pins.append(pan.getId());
            this.pins.append(",");
            this.pins.append(blankName);
            this.pins.append(",");
            this.pins.append(blankBranch);
            this.pins.append(",");
            String scrambledPan = pan.getNo().substring(0, 10) + pan.getNo().substring(pan.getNo().length() - 6, pan.getNo().length());
            this.pins.append(scrambledPan);
            this.pins.append(",");
            this.pins.append(pan.getOffset());
            this.pins.append("\r\n");
            this.data.append(pan.getId());
            this.data.append(",");
            this.data.append(pan.getNo());
            this.data.append(",,,");
            this.data.append(pan.getExpiry());
            this.data.append(",");
            this.data.append(blankName);
            this.data.append(",");
            this.data.append(pan.getNo());
            this.data.append("=");
            this.data.append(pan.getExpiry());
            this.data.append("101");
            this.data.append(pan.getOffset());
            this.data.append(",");
            this.data.append(blankBranch);
            this.data.append("\r\n");
        }
        String pinData = this.pins.toString();
        this.writeFileData(pinData, this.data.toString(), repository);
        LOG.info("Done processing card files.");
    }

    private void writeFileData(String pinData, String persoData, String repository) {
        LOG.info("Writing data to file..");
        try {
            this.prepareFileDirectory(downloadDirectory);
            File toSendFile = new File(downloadDirectory);
            this.printToFile(toSendFile, this.pinFile, pinData);
            this.printToFile(toSendFile, this.persoFile, persoData);
            if (pinData.length() > 5) {
                Compressor.compressFolder(downloadDirectory, repository);
            }
        } catch (Exception e) {
            String errorMessage = "[EXPAD] Error occured while writing to batchFile:\n";
            LOG.log(Level.SEVERE, errorMessage, e);
            this.setDoneDownload(false);
        }
        LOG.info("Done Writing data to file..");
    }

    private void prepareFileDirectory(String folder) throws Exception {
        File toSendFile = new File(folder);
        if (!toSendFile.exists()) {
            LOG.log(Level.INFO, "Directory - @{0}@ does not exist. creating it..", folder);
            toSendFile.mkdirs();
        }
        if (toSendFile.isDirectory()) {
            LOG.log(Level.INFO, "Deleting old files from :{0}", downloadDirectory);
            String[] filenames = toSendFile.list();
            LOG.log(Level.INFO, "INFO::Total Files Found -->{0} Files", filenames.length);
            if (filenames.length > 1) {
                for (int i = 0; i < filenames.length; ++i) {
                    String fileName1 = downloadDirectory + "\\" + filenames[i];
                    File temp = new File(fileName1);
                    temp.delete();
                }
            }
        }
    }

    private void printToFile(File parentFolder, String fileName, String data) throws Exception {
        File file = new File(parentFolder, fileName);
        FileOutputStream fdout = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fdout, 1024);
        try (PrintStream ps = new PrintStream(bos, false)) {
            System.setOut(ps);
            System.out.print(data);
            ps.flush();
        }
    }

    private void doWait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException x) {
            LOG.log(Level.SEVERE, null, x);
        }
    }

    private Pan getGeneratedPan(String index, Account account, Product product, String branchCode, String network) throws Exception {
        String processDate = this.sdf.format(new Date());
        String strDate = this.dateFormat.addMonthToDate(processDate, 24);
        String expiry = strDate.substring(8, 10) + strDate.substring(3, 5);
        boolean isPayDirect = !product.getName().toLowerCase().contains("direct");
        String xbin = product.getBin();
        String[] panData = CardUtil.newPan(this.secureRandom, this.company, xbin, branchCode, product.getCode(), network, product.getFormat(), isPayDirect);
        String pan = panData[0];
        String offset = panData[1];
        if (this.service.isPanExist(pan)) {
            this.doWait(100);
            panData = CardUtil.newPan(this.secureRandom, this.company, xbin, branchCode, product.getCode(), network, product.getFormat(), isPayDirect);
            pan = panData[0];
            offset = panData[1];
        }
        this.doWait(100);
        String serialno = Oid.next().get(Oid.Type.MAX);
        Pan p = new Pan(serialno, pan, expiry, offset, "D", branchCode, product.getId());
        this.panRecords.put(account.getAccountNumber() + "-" + index, p);
        pans.add(pan, expiry, offset, account.getBranch(), product.getId());
        return p;
    }

    public void createCardInformation(Account account, String userid, String batchid, Pan p) {
        List<String> accounts = new ArrayList<>();
        accounts.add(account.getAccountNumber());
        Card card = new Card();
        card.setBatchId(batchid);
        card.setCustomerId(account.getCustomerId());
        card.setCustomerName(account.getAccountName());
        card.setPreferedName(account.getAccountName());
        card.setSol(account.getBranch());
        card.setCollectingSol(account.getBranch());
        card.setUserId(userid);
        card.setPan(p.getNo());
        card.setExpiryDate(p.getExpiry());
        card.setOffset(p.getOffset());
        card.setSerialNo(p.getId());
        card.setProductCode(p.getProductCode());
        card.setAccounts(accounts);
        service.create(card);
    }

}
