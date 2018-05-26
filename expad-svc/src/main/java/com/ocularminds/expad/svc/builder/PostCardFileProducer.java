package com.ocularminds.expad.svc.builder;

import com.ocularminds.expad.vao.PostCardFiles;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.LinkedAccount;
import com.ocularminds.expad.vao.Product;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import com.ocularminds.expad.FileProducer;
import com.ocularminds.expad.common.Compressor;
import com.ocularminds.expad.common.Constants;
import com.ocularminds.expad.common.Dates;
import com.ocularminds.expad.common.Strings;
import com.ocularminds.expad.vao.Card;
import java.util.HashMap;

public final class PostCardFileProducer implements FileProducer {

    public static List processedCards;
    List records;
    String repository;
    String downloadFolder;
    Company company;
    Product product;
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public PostCardFileProducer(Company company, Product product, List list, String repository, String downloadFolder) {
        this.records = list;
        this.repository = repository;
        this.downloadFolder = downloadFolder;
        this.company = company;
        this.product = product;
    }

    private PostCardFiles toPostCard(List list) throws Exception {
       // Branches branches = new Branches();
       // Cards cards = new Cards();
        String processDate = sdf.format(new Date());
        SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        String processDateTime = ddf.format(new Date());
       // Map mb = branches.mapped();
        Date strDate = new Dates(new Date()).months(36);
        StringBuilder cb = new StringBuilder();
        StringBuilder cab = new StringBuilder();
        StringBuilder bb = new StringBuilder();
        StringBuilder ab = new StringBuilder();
        StringBuilder eb = new StringBuilder();
        processedCards = new java.util.ArrayList();
        for (int index = 0; index < list.size(); ++index) {
            Card request = (Card) list.get(index);
            String id = request.getId();
            String pan = request.getPan();
            String serialno = request.getSerialNo();
            //Product product = products.get(productCode);
            String offset = request.getOffset();
            String preferedName = request.getPreferedName();
            String cardProgram = product != null ? product.getName() : " ";
            String currencyCode = product != null ? product.getCurrencyCode() : "NGN";
            String trailingText = product != null ? product.getTrailingText() : "pppp";
            List<LinkedAccount> accounts = request.getLinkedAccounts();//cards.accounts(id);
            String expiryDate = request.getExpiryDate() != null ? request.getExpiryDate().replaceAll("/", "") : "";
            if (isFailedValidation(eb, accounts, id, pan, preferedName, expiryDate, offset)) {
                continue;
            }
            int totalAccount = accounts.size();
            processedCards.add(pan);
            String custid = request.getCustomerId();
            if (custid == null) {
                custid = " ";
            }
            String revExpiry = expiryDate.substring(2, 4) + expiryDate.substring(0, 2);
            preferedName = preferedName.replaceAll(",", "");
            preferedName = preferedName.replaceAll(";", "");
            preferedName = preferedName.replaceAll(",", "");
            String sol = request.getCollectingSol();
            if (sol == null) {
                sol = "230";
            }
            String accnum = accounts.get(0).getAccountNumber();
            custid = Strings.strpad(custid, 9);
            preferedName = Strings.strpad(preferedName, 26);
            currencyCode = Strings.strpad(currencyCode, 3);
            pan = Strings.strpad(pan, company.getPanSize());
            preferedName = Strings.strpad(preferedName, 26);
            custid = Strings.strpad(custid, 9);
            String custAccount = accnum;
            sol = Strings.strpad(sol, 3);
            String branch = sol;//branches.describe(sol);
            branch = Strings.strpad(branch, 3);
            String firstName = "";
            String secondName = "";
            String type = "10";
            String accounttypex = accnum.substring(company.getAccountStartPosition(), company.getAccountEndPosition());
            if (company.getCurrentMapCode().lastIndexOf(accounttypex) > -1) {
                type = "20";
            }
            if (company.isPrefixedWithU()) {
                cb.append("U");
                cb.append(",");
            }
            HashMap<String, String> record = new HashMap<>();
            record.put(Constants.PAN, pan);
            record.put(Constants.CARD_PROGRAM, cardProgram.substring(0, 8));
            record.put(Constants.TYPE, type);
            record.put(Constants.EXPIRY, expiryDate);
            record.put(Constants.OFFSET, offset);
            record.put(Constants.FIRST_NAME, firstName);
            record.put(Constants.SECOND_NAME, secondName);
            record.put(Constants.PREFERED_NAME, preferedName);
            record.put(Constants.BRANCH, branch);
            record.put(Constants.CURRENCY_CODE, currencyCode);
            record.put(Constants.DISCRETIONARY_DATA, trailingText);
            record.put(Constants.PROCESS_TIME, processDateTime);
            record.put(Constants.WORKING_BRANCH, sol);
            record.put(Constants.SERIAL_NO, serialno);
            this.addCard(cb, record);
            for (int x = 0; x < totalAccount; ++x) {
                custAccount = accounts.get(x).getAccountNumber();
                String typex = "10";
                String accounttype = custAccount.substring(company.getAccountStartPosition(), company.getAccountEndPosition());
                if (company.getCurrentMapCode().lastIndexOf(accounttype) > -1) {
                    typex = "20";
                }
                custid = Strings.strpad(custid, 9);
                preferedName = Strings.strpad(preferedName, 26);
                sol = Strings.strpad(sol, 4);
                branch = Strings.strpad(branch, 26);
                pan = Strings.strpad(pan, company.getPanSize());
                addBalance(ab, custAccount, typex, pan);
                addBookBalance(bb, custAccount, typex);
                addCardAccountBalance(cab, pan, custAccount, typex);
            }
        }
        String xid = Long.toString(System.currentTimeMillis());
        String accounts = ab.toString();
        String accountBalances = bb.toString();
        String data = cb.toString();
        String cardAccounts = cab.toString();
        String cardOverrideLimits = "";
        String accountOverrideLimits = "";
        String statements = "";
        String bads = eb.toString();
        PostCardFiles pcf = new PostCardFiles(xid, data, accounts, cardAccounts, accountBalances, cardOverrideLimits, accountOverrideLimits, statements);
        pcf.setBads(bads);
        return pcf;
    }

    //@SendJobCompletionNotification
    @Override
    public void produce() {
        String accountFile = "accounts.txt";
        String cardAccountFile = "cardaccounts.txt";
        String cardsFile = "cards.txt";
        String accountBalanceFile = "accountbalances.txt";
        String cardLimitFile = "cardoverridelimits.txt";
        String accountLimitFile = "accountoverridelimits.txt";
        String statementFile = "statements.txt";
        String badFile = "bad.txt";   
        try {
            PostCardFiles pcf = toPostCard(records);
            createDirectory(downloadFolder, true);
            write(downloadFolder, accountFile, pcf.getAccounts());
            write(downloadFolder, accountBalanceFile, pcf.getAccountBalances());
            write(downloadFolder, cardsFile, pcf.getCards());
            write(downloadFolder, cardAccountFile, pcf.getCardAccounts());
            write(downloadFolder, cardLimitFile, pcf.getCardOverrideLimits());
            write(downloadFolder, accountLimitFile, pcf.getAccountOverrideLimits());
            write(downloadFolder, statementFile, pcf.getStatements());
            write(downloadFolder, badFile, pcf.getBads());
            if (records.size() > 0) {
                Compressor.compressFolder(downloadFolder, repository);
            }
            //new Cards().notifyDownloadedCardByPan(processedCards);
        } catch (Exception x) {
            // empty catch block
        }
    }

    private boolean isFailedValidation(StringBuilder eb, List accounts, String id, String pan, String name, String expiry, String offset) {
        if (accounts == null || accounts.size() < 1) {
            LOG.info("INFO : Card {} Not Linked ", pan);
            eb.append("card with id ").append(id).append(" not linked");
            return true;
        }
        if (name == null || name.length() < 2) {
            LOG.info("INFO : Card {} has no name ", pan);
           eb.append(String.format("%s %s\n",pan,Strings.strpad(" has no name", 26)));
            return true;
        }
        if (expiry.length() != 4) {
            LOG.info("INFO : Card {} has invalid expiry date", pan);
           eb.append(String.format("%s %s\n",pan,Strings.strpad(" has invalid date", 26)));
            return true;
        }
        if (offset.length() != 4) {
            LOG.info("INFO : Card " + pan + " has invalid pin offset");
            eb.append(String.format("%s %s\n",pan,Strings.strpad("invalid pin offset", 26)));
            return true;
        }
        return false;
    }

    private void addCard(StringBuilder cb, HashMap<String, String> data) {
        if (company.isPrefixedWithU()) {
            cb.append("U,");
        }
        cb.append(data.getOrDefault(Constants.PAN, ""));
        cb.append(",001,");
        cb.append(data.getOrDefault(Constants.CARD_PROGRAM.substring(0, 8), ""));
        cb.append(",");
        cb.append(data.getOrDefault(Constants.TYPE, ""));
        cb.append(",0,,");
        cb.append(data.getOrDefault(Constants.EXPIRY, ""));
        cb.append(",,,,4,");
        cb.append(data.getOrDefault(Constants.OFFSET, ""));
        cb.append(",");
        cb.append(data.getOrDefault(Constants.FIRST_NAME, ""));
        cb.append(",");
        cb.append(data.getOrDefault(Constants.SECOND_NAME, ""));
        cb.append(",,,,,,,");
        cb.append(data.getOrDefault(Constants.PREFERED_NAME, ""));
        cb.append(",");
        cb.append(data.getOrDefault(Constants.BRANCH, ""));
        cb.append(",,,,,");
        cb.append(data.getOrDefault(Constants.CURRENCY_CODE, ""));
        cb.append(",,1,,0,,");
        cb.append(data.getOrDefault(Constants.DISCRETIONARY_DATA, ""));
        cb.append(",");
        cb.append(data.getOrDefault(Constants.PROCESS_TIME, ""));
        cb.append(",1900-01-01 00:00:00.000,,,");
        cb.append(data.getOrDefault(Constants.WORKING_BRANCH, ""));
        cb.append(",");
        cb.append(data.getOrDefault(Constants.SERIAL_NO, ""));
        cb.append("\r\n");
    }

    private void addBalance(StringBuilder ab, String account, String type, String pan) {
        if (company.isPrefixedWithU()) {
            ab.append("U,");
        }
        ab.append(account);
        ab.append(",");
        ab.append(type);
        ab.append(",566,");
        ab.append(pan.substring(pan.length() - 7, pan.length()));
        ab.append("\r\n");
    }

    private void addBookBalance(StringBuilder bb, String account, String type) {
        if (company.isPrefixedWithU()) {
            bb.append("U,");
        }
        bb.append(account);
        bb.append(",0,0,");
        bb.append(type);
        bb.append("\r\n");
    }

    private void addCardAccountBalance(StringBuilder cab, String pan, String account, String type) {

        if (company.isPrefixedWithU()) {
            cab.append("U,");
        }
        cab.append(pan);
        cab.append(",001,");
        cab.append(account);
        cab.append(",");
        cab.append(type);
        cab.append(",1,");
        cab.append(type);
        cab.append("\r\n");
    }
}
