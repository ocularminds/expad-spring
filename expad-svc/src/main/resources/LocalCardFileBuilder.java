

import com.ocularminds.expad.FileProducer;
import com.ocularminds.expad.vao.Card;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.CustomizedCard;
import com.ocularminds.expad.vao.LinkedAccount;
import com.ocularminds.expad.vao.Product;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LocalCardFileBuilder implements FileProducer {
    Map prods;
    Map m;
    Company info;
    Logger LOG = LoggerFactory.getLogger(LocalCardFileBuilder.class);
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddhhmm");
    SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final String SUBJECT = "ExPad-FEP Automatic Download Failure!";
    public static final String MESSAGE = "Service Jam  : Files on postcard are yet to be processed.\nPlease check the update load job on postcard";

    public LocalCardFileBuilder() {
        try {
            this.prods = esb.mappedProducts();
            this.info = this.config.get();
            this.m = ssb.mappedBranches();
        } catch (Exception ex) {
            m = new HashMap<String, String>();
            LOG.error("", ex);
        }
    }

    private boolean checkOrCreateFolder(String folder) {
        int onQueue = 0;
        try {
            onQueue = FileBuilder.createDirectory(folder, false);
        } catch (Exception fbe) {
            System.out.println("error building file LocalFileBuilder.build - " + fbe.getMessage());
        }
        if (onQueue > 2) {
            System.out.println("ExPad FEP-PUSH: Card files not sent!\nService Jam  : Files in " + folder + " folder are yet to be processed.");
            return true;
        }
        return false;
    }

    public void updateCardAfterDownload(ArrayList records, CardService esb) {
        for (int j = 0; j < records.size(); ++j) {
            try {
                esb.notifyDownloadedDebitCardByPan((String) records.get(j));
            } catch (Exception ex) {
                LOG.error("", ex);
            }
        }
    }

    private String buildPhotoData(List records) {
        StringBuilder sb = new StringBuilder();
        sb.append(BioBuilder.HEADER);
        for (int x = 0; x < records.size(); ++x) {
            CustomizedCard s = (CustomizedCard) records.get(x);
            sb.append(new BioDataBuilder(s).build());
        }
        sb.append(BioBuilder.FOOTER);
        return sb.toString();
    }

    public void buildCardData(StringBuilder ccsb, Company info, String pan, String cardProgram, String type, String ed, String PIN_OFFSET, String cardholder, String branch, String currencyCode, String discretionData, String processDateTime, String ATMDateTime, String sol, String custid) {
        String firstName = "";
        String secondName = "";
        if (info.isPrefixedWithU()) {
            ccsb.append("U");
            ccsb.append(",");
        }
        ccsb.append(pan);
        ccsb.append(",");
        ccsb.append("001");
        ccsb.append(",");
        ccsb.append(cardProgram);
        ccsb.append(",");
        ccsb.append(type);
        ccsb.append(",");
        ccsb.append("6");
        ccsb.append(",");
        ccsb.append(",");
        ccsb.append(ed);
        ccsb.append(",,,,");
        ccsb.append("4");
        ccsb.append(",");
        ccsb.append(PIN_OFFSET);
        ccsb.append(",");
        ccsb.append(firstName);
        ccsb.append(",");
        ccsb.append(secondName);
        ccsb.append(",");
        ccsb.append(",");
        ccsb.append(",");
        ccsb.append(",,,,");
        ccsb.append(cardholder);
        ccsb.append(",");
        ccsb.append(branch);
        ccsb.append(", , , ,234,");
        ccsb.append(currencyCode);
        ccsb.append(",,");
        ccsb.append("1");
        ccsb.append(",,");
        ccsb.append("0");
        ccsb.append(",,");
        ccsb.append(discretionData);
        ccsb.append(",");
        ccsb.append(processDateTime);
        ccsb.append(",");
        ccsb.append(ATMDateTime);
        ccsb.append(",," + sol + ",");
        ccsb.append(custid);
        ccsb.append("\r\n");
    }

    private void buildAccountData(StringBuilder acsb, Company info, String custAccount, String typex, String custid) {
        if (info.isPrefixedWithU()) {
            acsb.append("U");
            acsb.append(",");
        }
        acsb.append(custAccount);
        acsb.append(",");
        acsb.append(typex);
        acsb.append(",");
        acsb.append("566");
        acsb.append(",");
        acsb.append(custid);
        acsb.append("\r\n");
    }

    private void buildBalanceData(StringBuilder basb, Company info, String custAccount, String typex) {
        if (info.isPrefixedWithU()) {
            basb.append("U,");
        }
        basb.append(custAccount);
        basb.append(",0,0,");
        basb.append(typex);
        basb.append("\r\n");
    }         

    private void buildCardAccountData(StringBuilder casb, Company info, String pan, String custAccount, String typex) {
        if (info.isPrefixedWithU()) {
            casb.append("U");
            casb.append(",");
        }
        casb.append(pan);
        casb.append(",");
        casb.append("001");
        casb.append(",");
        casb.append(custAccount);
        casb.append(",");
        casb.append(typex);
        casb.append(",");
        casb.append("1");
        casb.append(",");
        casb.append(typex);
        casb.append("\r\n");
    }

    private List load(String ctype, String folder) {
        List records = new ArrayList();
        switch (ctype) {
            case "S":
                try {
                    System.out.println("[EXPAD]: loading student card files...");
                    String[] ff = folder.split("-");
                    String acronymn = ff[1];
                    folder = ff[0] + "\\" + acronymn;
                    records = this.esb.findStudentCardForDownload(acronymn);
                    System.out.println("" + records.size() + " " + acronymn + " cards found");
                } catch (Exception ex) {
                    LOG.error("", ex);
                }
                break;
            case "C":
                try {
                    System.out.println("[EXPAD]: loading Normal VERVE card data...");
                    records = this.esb.findNormalCardForDownload();
                    System.out.println("" + records.size() + " normal cards found.");
                } catch (Exception ex) {
                    LOG.error("", ex);
                }
                break;
            default:
                try {
                    System.out.println("[EXPAD]: loading NYSC card data...");
                    records = this.esb.findNyscCardForDownload();
                    System.out.println("" + records.size() + " Nysc cards found.");
                } catch (Exception ex) {
                    LOG.error("", ex);
                }
                break;
        }
        return records;
    }
    
    private void persistAll(ArrayList processed, String acdata, String badata, String ccdata, String cadata, String photodata, String folder, String ctype) {
        try {
            if (processed.size() > 0 && !ccdata.equals("")) {
                FileBuilder.writeFile(folder, FileBuilder.ACCOUNTS_FILE, acdata);
                FileBuilder.writeFile(folder, FileBuilder.ACCOUNT_BALANCES_FILE, badata);
                FileBuilder.writeFile(folder, FileBuilder.CARDS_FILE, ccdata);
                FileBuilder.writeFile(folder, FileBuilder.CARD_ACCOUNTS_FILE, cadata);
                FileBuilder.writeFile(folder, FileBuilder.CARD_OVERRIDES_FILE, "");
                FileBuilder.writeFile(folder, FileBuilder.ACCOUNT_OVERRIDES_FILE, "");
                FileBuilder.writeFile(folder, FileBuilder.STATEMENTS_FILE, "");
                if (ctype.equals("S")) {
                    FileBuilder.writeFile(folder, FileBuilder.PHOTO_FILE, photodata);
                }
                this.esb.notifyBatchDownload(processed);
            }
        } catch (Exception x) {
            System.out.println("[EXPAD]: LocalCardFileBuilder: Error producing card files::" + x);
            x.printStackTrace();
        }
    }

    private boolean isCorrectData(LinkedAccount[] links, String expiry, String pan, String cardholder, String PIN_OFFSET) {
        boolean valid = false;
        if (links == null || links.length < 1) {
            System.out.println("[EXPAD]: INFO : Card " + pan + " Not Linked ");
        } else if (cardholder == null || cardholder.length() < 4) {
            System.out.println("[EXPAD]: INFO : Card " + pan + " has no name \n");
        } else if (expiry == null || expiry.length() < 4) {
            System.out.println("[EXPAD]: INFO : Card " + pan + " has invalid expiry date\n");
        } else if (PIN_OFFSET == null || PIN_OFFSET.length() < 4) {
            System.out.println("[EXPAD]: INFO : Card " + pan + " has invalid pin offset\n");
        } else {
            valid = true;
        }
        return valid;
    }

    public void buildFile(String repository, String folder, String ctype) {
        try {
            StringBuilder error = new StringBuilder();
            if (this.checkOrCreateFolder(folder)) {
                return;
            }
            List list = this.load(ctype, folder);
            if (list.size() == 0) {
                System.out.println("No records available for download.");
                return;
            }
            System.out.println("[EXPAD]: processing data...");
            String processDateTime = this.ddf.format(new Date());
            String ATMDateTime = this.ddf.format(new Date());
            String PIN_OFFSET = "";
            String RES_CODE = this.info.getRestrictionCode();
            String SEQ_NO = this.info.getSequenceNo();
            String INACTIVE_CODE = this.info.getInActiveCardCode();
            StringBuilder ccsb = new StringBuilder();
            StringBuilder casb = new StringBuilder();
            StringBuilder basb = new StringBuilder();
            StringBuilder acsb = new StringBuilder();
            StringBuilder exsb = new StringBuilder();
            ArrayList<String> processed = new ArrayList<String>();
            for (int j = 0; j < list.size(); ++j) {
                try {
                    Card request = (Card) list.get(j);
                    String id = request.getId();
                    String pan = request.getPan();
                    String pCode = request.getProductCode();
                    Product p = (Product) this.prods.get(pCode);
                    PIN_OFFSET = request.getOffset();
                    String cardholder = request.getPreferedName();
                    String custType = request.getCustType();
                    String cardProgram = p != null ? p.getName() : " ";
                    String currencyCode = p != null ? p.getCurrencyCode() : "NGN";
                    String svc = p != null ? p.getInActiveCardCode() : "0";
                    String discretionData = p != null ? p.getTrailingText() : "pppp";
                    LinkedAccount[] links = this.esb.findDebitCardLinkAccounts(id);
                    try {
                        String ed;
                        if (!this.isCorrectData(links, request.getExpiryDate(), pan, cardholder, PIN_OFFSET)) {
                            continue;
                        }
                        int totalAccount = links.length;
                        String custid = request.getCustomerId();
                        if (custid == null) {
                            custid = " ";
                        }
                        revExpiry = (ed = request.getExpiryDate().replaceAll("/", "")) != null && ed.length() == 5 ? ed.substring(3, 5) + "/" + ed.substring(0, 2) : ed.substring(2, 4) + "/" + ed.substring(0, 2);
                        cardholder = cardholder.replaceAll(",", "");
                        cardholder = cardholder.replaceAll(";", "");
                        cardholder = cardholder.replaceAll(",", "");
                        String sol = request.getCollectingSol();
                        if (sol == null) {
                            sol = "230";
                        }
                        String accnum = links[0].getAccountNumber();
                        custid = FileFormat.formatText((int) 9, (String) custid);
                        cardholder = FileFormat.formatText((int) 25, (String) cardholder);
                        currencyCode = FileFormat.formatText((int) 3, (String) currencyCode);
                        pan = FileFormat.formatText((int) this.info.getPanSize(), (String) pan);
                        cardholder = FileFormat.formatText((int) 25, (String) cardholder);
                        custid = FileFormat.formatText((int) 9, (String) custid);
                        String custAccount = accnum;
                        String branch = (String) this.m.get(sol = FileFormat.formatText((int) 3, (String) sol));
                        branch = branch == null ? "" : branch;
                        branch = FileFormat.formatText((int) 26, (String) branch);
                        String type = "10";
                        String accounttypex = accnum.substring(this.info.getAccountStartPosition(), this.info.getAccountEndPosition());
                        if (this.info.getCurrentMapCode().lastIndexOf(accounttypex) > -1) {
                            type = "20";
                        }
                        this.buildCardData(ccsb, this.info, pan, cardProgram, type, ed, PIN_OFFSET, cardholder, branch, currencyCode, discretionData, processDateTime, ATMDateTime, sol, custid);
                        for (int x = 0; x < totalAccount; ++x) {
                            custAccount = links[x].getAccountNumber();
                            String typex = "10";
                            String accounttype = custAccount.substring(this.info.getAccountStartPosition(), this.info.getAccountEndPosition());
                            if (this.info.getCurrentMapCode().lastIndexOf(accounttype) > -1) {
                                typex = "20";
                            }
                            custid = FileFormat.formatText((int) 9, (String) custid);
                            cardholder = FileFormat.formatText((int) 26, (String) cardholder);
                            sol = FileFormat.formatText((int) 4, (String) sol);
                            branch = FileFormat.formatText((int) 26, (String) branch);
                            pan = FileFormat.formatText((int) this.info.getPanSize(), (String) pan);
                            this.buildAccountData(acsb, this.info, custAccount, typex, custid);
                            this.buildBalanceData(basb, this.info, custAccount, typex);
                            this.buildCardAccountData(casb, this.info, pan, custAccount, typex);
                        }
                        processed.add(id);
                        continue;
                    } catch (Exception io) {
                        io.printStackTrace();
                    }
                } catch (Exception ex) {
                    LOG.error("", ex);
                }
            }
            String acdata = acsb.toString();
            String badata = basb.toString();
            String ccdata = ccsb.toString();
            String cadata = casb.toString();
            String exdata = exsb.toString();
            List photos = ctype.equals("S") ? this.esb.findCustomizedCardRecordForDownload(processed) : new ArrayList();
            String photodata = this.buildPhotoData(photos);
            this.persistAll(processed, acdata, badata, ccdata, exdata, photodata, folder, ctype);
            System.out.println("[EXPAD]: Building files completed.");
        } catch (Exception ex) {
            LOG.error("", ex);
        }
    }

    @Override
    public void produce() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
