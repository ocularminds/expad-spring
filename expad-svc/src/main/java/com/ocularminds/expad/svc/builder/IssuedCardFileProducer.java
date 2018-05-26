/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.svc.builder;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
import com.ocularminds.expad.svc.FileNames;
import com.ocularminds.expad.FileProducer;
import com.ocularminds.expad.common.Strings;
import com.ocularminds.expad.svc.OnFileCompletion;
import com.ocularminds.expad.vao.Card;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.LinkedAccount;
import com.ocularminds.expad.vao.Product;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class IssuedCardFileProducer implements FileProducer {

    private final String repository;
    private final String folder;
    private final List<Card> cards;
    private final String type;
    List<String> processed;
    Map prods;
    Map m;
    Company info;
    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddhhmm");
    SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final String SUBJECT = "ExPad-FEP Automatic Download Failure!";
    public static final String MESSAGE = "Service Jam  : Files on postcard are yet to be processed.\nPlease check the update load job on postcard";

    public IssuedCardFileProducer(String repository, String folder, String type, List<Card> records) {
        this.type = type;
        this.repository = repository;
        this.folder = folder;
        this.cards = records;
    }

    @Override
    @OnFileCompletion
    public void produce() {
        processed = new ArrayList<>();   
        try {
            if (cards == null || cards.isEmpty()) {
                LOG.info("No records available for download.");
                return;
            }
            LOG.info("[EXPAD]: processing data...");
            String processDateTime = this.ddf.format(new Date());
            String ATMDateTime = this.ddf.format(new Date());
            String offset;
            StringBuilder ccsb = new StringBuilder();
            StringBuilder casb = new StringBuilder();
            StringBuilder basb = new StringBuilder();
            StringBuilder acsb = new StringBuilder();
            StringBuilder exsb = new StringBuilder();

            for (int j = 0; j < cards.size(); ++j) {
                try {
                    Card card = (Card) cards.get(j);
                    String id = card.getId();
                    String pan = card.getPan();
                    String pCode = card.getProductCode();
                    Product p = (Product) this.prods.get(pCode);
                    offset = card.getOffset();
                    String cardholder = card.getPreferedName();
                    String custType = card.getCustType();
                    String cardProgram = p != null ? p.getName() : " ";
                    String currencyCode = p != null ? p.getCurrencyCode() : "NGN";
                    String svc = p != null ? p.getInActiveCardCode() : "0";
                    String discretionData = p != null ? p.getTrailingText() : "pppp";
                    LinkedAccount[] links = new LinkedAccount[0];//this.esb.findDebitCardLinkAccounts(id);
                    try {
                        String ed;
                        if (!this.isCorrectData(links, card.getExpiryDate(), pan, cardholder, offset)) {
                            continue;
                        }
                        int totalAccount = links.length;
                        String custid = card.getCustomerId();
                        if (custid == null) {
                            custid = " ";
                        }
                        ed = card.getExpiryDate();
                        //ed = .replaceAll("/", "") != null && ed.length() == 5 ? ed.substring(3, 5) + "/" + ed.substring(0, 2) : ed.substring(2, 4) + "/" + ed.substring(0, 2);
                        cardholder = cardholder.replaceAll(",", "");
                        cardholder = cardholder.replaceAll(";", "");
                        cardholder = cardholder.replaceAll(",", "");
                        String sol = card.getCollectingSol();
                        if (sol == null) {
                            sol = "230";
                        }
                        String accnum = links[0].getAccountNumber();
                        custid = Strings.strpad(custid, 9);
                        cardholder = Strings.strpad(cardholder, 25);
                        currencyCode = Strings.strpad(currencyCode, 3);
                        pan = Strings.strpad(pan, this.info.getPanSize());
                        cardholder = Strings.strpad(cardholder, 25);
                        custid = Strings.strpad(custid, 9);
                        String custAccount = accnum;
                        String branch = Strings.strpad(sol, 3);
                        branch = branch == null ? "" : branch;
                        branch = Strings.strpad(branch, 26);
                        String typ = "10";
                        String accounttypex = accnum.substring(this.info.getAccountStartPosition(), this.info.getAccountEndPosition());
                        if (this.info.getCurrentMapCode().lastIndexOf(accounttypex) > -1) {
                            typ = "20";
                        }
                        this.buildCardData(ccsb, this.info, pan, cardProgram, type, ed, offset, cardholder, branch, currencyCode, discretionData, processDateTime, ATMDateTime, sol, custid);
                        for (int x = 0; x < totalAccount; ++x) {
                            custAccount = links[x].getAccountNumber();
                            String typex = "10";
                            String accounttype = custAccount.substring(this.info.getAccountStartPosition(), this.info.getAccountEndPosition());
                            if (this.info.getCurrentMapCode().lastIndexOf(accounttype) > -1) {
                                typex = "20";
                            }
                            custid = Strings.strpad(custid, 9);
                            cardholder = Strings.strpad(cardholder, 26);
                            sol = Strings.strpad(sol, 4);
                            branch = Strings.strpad(branch, 26);
                            pan = Strings.strpad(pan, this.info.getPanSize());
                            account(acsb, this.info, custAccount, typex, custid);
                            balance(basb, this.info, custAccount, typex);
                            cardAccount(casb, this.info, pan, custAccount, typex);
                        }
                        processed.add(id);
                    } catch (Exception io) {
                        LOG.error("error building card file. ", io);
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
            List photos = new ArrayList();//type.equals("S") ? this.esb.findCustomizedCardRecordForDownload(processed) : new ArrayList();
            String photodata = this.buildPhotoData(photos);
            produce(acdata, badata, ccdata, exdata, photodata, folder, type);
            LOG.info("[EXPAD]: Building files completed.");
        } catch (Exception ex) {
            LOG.error("", ex);
        }
    }

    private String buildPhotoData(List records) {
        StringBuilder sb = new StringBuilder();
////        sb.append(BioBuilder.HEADER);
////        for (int x = 0; x < records.size(); ++x) {
////            CustomizedCard s = (CustomizedCard) records.get(x);
////            sb.append(new BioDataBuilder(s).build());
////        }
////        sb.append(BioBuilder.FOOTER);
        return sb.toString();
    }

    public void buildCardData(StringBuilder ccsb, Company info, String pan, String cardProgram, String type, String ed, String offset, String cardholder, String branch, String currencyCode, String discretionData, String processDateTime, String ATMDateTime, String sol, String custid) {
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
        ccsb.append(offset);
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

    private void account(StringBuilder acsb, Company info, String account, String typ, String custid) {
        if (info.isPrefixedWithU()) {
            acsb.append("U");
            acsb.append(",");
        }
        acsb.append(account);
        acsb.append(",");
        acsb.append(typ);
        acsb.append(",");
        acsb.append("566");
        acsb.append(",");
        acsb.append(custid);
        acsb.append("\r\n");
    }

    private void balance(StringBuilder basb, Company info, String account, String typ) {
        if (info.isPrefixedWithU()) {
            basb.append("U,");
        }
        basb.append(account);
        basb.append(",0,0,");
        basb.append(typ);
        basb.append("\r\n");
    }

    private void cardAccount(StringBuilder casb, Company info, String pan, String account, String typ) {
        if (info.isPrefixedWithU()) {
            casb.append("U");
            casb.append(",");
        }
        casb.append(pan);
        casb.append(",");
        casb.append("001");
        casb.append(",");
        casb.append(account);
        casb.append(",");
        casb.append(typ);
        casb.append(",");
        casb.append("1");
        casb.append(",");
        casb.append(typ);
        casb.append("\r\n");
    }

    private List load(String ctype, String folder) {
        List records = new ArrayList();
        switch (ctype) {
            case "S":
                try {
                    LOG.info("[EXPAD]: loading student card files...");
                    String[] ff = folder.split("-");
                    String acronymn = ff[1];
                    folder = ff[0] + "\\" + acronymn;
                    //records = this.esb.findStudentCardForDownload(acronymn);
                    LOG.info("" + records.size() + " " + acronymn + " cards found");
                } catch (Exception ex) {
                    LOG.error("", ex);
                }
                break;
            case "C":
                try {
                    LOG.info("[EXPAD]: loading Normal VERVE card data...");
                    //records = this.esb.findNormalCardForDownload();
                    LOG.info("" + records.size() + " normal cards found.");
                } catch (Exception ex) {
                    LOG.error("", ex);
                }
                break;
            default:
                try {
                    LOG.info("[EXPAD]: loading NYSC card data...");
                    //records = this.esb.findNyscCardForDownload();
                    LOG.info("" + records.size() + " Nysc cards found.");
                } catch (Exception ex) {
                    LOG.error("", ex);
                }
                break;
        }
        return records;
    }

    private void produce(String acdata, String badata, String ccdata, String cadata, String photodata, String folder, String ctype) {
        try {
            if (processed.size() > 0 && !ccdata.equals("")) {
                write(folder, FileNames.ACCOUNTS_FILE, acdata);
                write(folder, FileNames.ACCOUNT_BALANCES_FILE, badata);
                write(folder, FileNames.CARDS_FILE, ccdata);
                write(folder, FileNames.CARD_ACCOUNTS_FILE, cadata);
                write(folder, FileNames.CARD_OVERRIDES_FILE, "");
                write(folder, FileNames.ACCOUNT_OVERRIDES_FILE, "");
                write(folder, FileNames.STATEMENTS_FILE, "");
                if (ctype.equals("S")) {
                    write(folder, FileNames.PHOTO_FILE, photodata);
                }
                //this.esb.notifyBatchDownload(processed);
            }
        } catch (Exception x) {
            LOG.error("Error producing card files::", x);
        }
    }

    private boolean isCorrectData(LinkedAccount[] links, String expiry, String pan, String cardholder, String offset) {
        boolean valid = false;
        if (links == null || links.length < 1) {
            LOG.info("[EXPAD]: INFO : Card " + pan + " Not Linked ");
        } else if (cardholder == null || cardholder.length() < 4) {
            LOG.info("[EXPAD]: INFO : Card " + pan + " has no name \n");
        } else if (expiry == null || expiry.length() < 4) {
            LOG.info("[EXPAD]: INFO : Card " + pan + " has invalid expiry date\n");
        } else if (offset == null || offset.length() < 4) {
            LOG.info("[EXPAD]: INFO : Card " + pan + " has invalid pin offset\n");
        } else {
            valid = true;
        }
        return valid;
    }
}
