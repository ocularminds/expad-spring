
package com.ocularminds.expad.svc;

import com.ocularminds.expad.vao.AccountBalanceRecordInfo;
import com.ocularminds.expad.vao.AccountOverrideLimitRecordInfo;
import com.ocularminds.expad.vao.AccountRecordInfo;
import com.ocularminds.expad.vao.Card;
import com.ocularminds.expad.vao.CardAccountRecordInfo;
import com.ocularminds.expad.vao.CardOverrideLimitRecordInfo;
import com.ocularminds.expad.vao.CardRecordInfo;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.LinkedAccount;
import com.ocularminds.expad.vao.Product;
import com.ocularminds.expad.vao.StatementRecordInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostCardService  {

    Company company;
    CardService service;
    final SimpleDateFormat sdf;
    static final Logger LOG = Logger.getLogger(PostCardService.class.getName());

    public PostCardService(CardService svc, AdminService as) {
        this.service = svc;
        this.company = as.config();
        this.sdf  = new SimpleDateFormat("dd-MM-yyyy");
    }
    
    public CardService getCardService(){
        return this.service;
    }

    public Map getGeneratedPostCardData(ArrayList list) {
        DatetimeFormat dateFormat = new DatetimeFormat();
        HashMap<String, Object> postCardData = new HashMap<String, Object>();
        LocalCardFileBuilder lcf = new LocalCardFileBuilder();
        String PIN_OFFSET = "";
        String SEQ_NO = this.company.getSequenceNo();
        FileFormat ff = new FileFormat();
        java.util.Date date = new java.util.Date();
        String processDate = this.sdf.format(date);
        SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        String processDateTime = ddf.format(new java.util.Date());
        ArrayList<CardRecordInfo> cardRecords = new ArrayList<CardRecordInfo>();
        ArrayList<CardAccountRecordInfo> cardAccountRecords = new ArrayList<CardAccountRecordInfo>();
        ArrayList<AccountRecordInfo> accountRecords = new ArrayList<AccountRecordInfo>();
        ArrayList<AccountBalanceRecordInfo> accountBalanceRecords = new ArrayList<AccountBalanceRecordInfo>();
        ArrayList<StatementRecordInfo> statementRecords = new ArrayList<StatementRecordInfo>();
        String revExpiry;
        String prefix = this.company.isPrefixedWithU() ? "U" : "";
        for (int index = 0; index < list.size(); ++index) {
            try {
                Card request = (Card) list.get(index);
                String id = request.getId();
                String pan = request.getPan();
                String productCode = request.getProductCode();
                Product product = service.product(productCode);
                String cardProgram = product != null ? product.getName() : " ";
                String currencyCode = product != null ? product.getCurrencyCode() : "NGN";
                //String inActiveCardCode = product != null ? product.getInActiveCardCode() : "0";
                String trailingText = product != null ? product.getTrailingText() : "pppp";
                String branch = request.getCollectingSol();
                String expiryDate = request.getExpiryDate() != null ? request.getExpiryDate().replaceAll("/", "") : "";
                LinkedAccount[] links = service.findDebitCardLinkAccounts(id);
                if (expiryDate.length() != 4) {
                    LOG.log(Level.INFO, "INFO : Invalid offset or expiry for card:{0}", pan);
                    continue;
                }
                if (links == null || links.length < 1) {
                    LOG.info("INFO : Card Not Linked ");
                    continue;
                }
                int totalAccount = links.length;
                revExpiry = expiryDate.substring(2, 4) + expiryDate.substring(0, 2);
                String custid = request.getCustomerId() != null ? request.getCustomerId() : " ";
                String preferedName = request.getPreferedName();
                preferedName = preferedName.replaceAll(",", "");
                preferedName = preferedName.replaceAll(";", "");
                preferedName = preferedName.replaceAll(",", "");
                String sol = request.getCollectingSol() != null ? request.getCollectingSol() : "230";
                String accnum = links[0].getAccountNumber();
                custid = FileFormat.formatText((int) 9, (String) custid);
                preferedName = FileFormat.formatText((int) 26, (String) preferedName);
                currencyCode = FileFormat.formatText((int) 3, (String) currencyCode);
                PIN_OFFSET = request.getOffset();
                pan = FileFormat.formatText((int) this.company.getPanSize(), (String) pan);
                String firstName = "";
                String secondName = "";
                String accounttypex = accnum.substring(this.company.getAccountStartPosition(), this.company.getAccountEndPosition());
                String type = this.company.getCurrentMapCode().lastIndexOf(accounttypex) > -1 ? "20" : "10";
                CardRecordInfo cardRecord = new CardRecordInfo();
                cardRecord.firstfield = prefix;
                cardRecord.pan = pan;
                cardRecord.seq_nr = "001";
                cardRecord.card_program = cardProgram;
                cardRecord.default_account_type = type;
                cardRecord.card_status = "0";
                cardRecord.expiry_date = expiryDate;
                cardRecord.pvki_or_pin_length = "4";
                cardRecord.pvv_or_pin_offset_secure = PIN_OFFSET;
                cardRecord.cardholder_first_name = firstName;
                cardRecord.cardholder_last_name = secondName;
                cardRecord.cardholder_name_on_card = preferedName;
                cardRecord.cardholder_country = currencyCode;
                cardRecord.mailer_destination = "1";
                cardRecord.vip = "0";
                cardRecord.discretionary_data = trailingText;
                cardRecord.date_issued = processDateTime;
                cardRecord.date_activated = processDateTime;
                cardRecord.branch_code = branch;
                cardRecord.customer_id = custid;
                cardRecord.hold_rsp_code = null;
                cardRecords.add(cardRecord);
                for (int x = 0; x < totalAccount; ++x) {
                    String custAccount = links[x].getAccountNumber();
                    String accounttype = custAccount.substring(this.company.getAccountStartPosition(), this.company.getAccountEndPosition());
                    String typex = this.company.getCurrentMapCode().lastIndexOf(accounttype) > -1 ? "20" : "10";
                    custid = FileFormat.formatText((int) 9, (String) custid);
                    preferedName = FileFormat.formatText((int) 26, (String) preferedName);
                    sol = FileFormat.formatText((int) 4, (String) sol);
                    branch = FileFormat.formatText((int) 26, (String) branch);
                    pan = FileFormat.formatText((int) this.company.getPanSize(), (String) pan);
                    AccountRecordInfo accountRecord = new AccountRecordInfo();
                    accountRecord.firstfield = prefix;
                    accountRecord.account_id = custAccount;
                    accountRecord.account_type = typex;
                    accountRecord.currency_code = "566";
                    accountRecord.customer_id = custid;
                    accountRecords.add(accountRecord);
                    AccountBalanceRecordInfo accountBalanceRecord = new AccountBalanceRecordInfo();
                    accountBalanceRecord.firstfield = prefix;
                    accountBalanceRecord.account_id = custAccount;
                    accountBalanceRecord.ledger_balance = "0";
                    accountBalanceRecord.available_balance = "0";
                    accountBalanceRecord.account_type = typex;
                    accountBalanceRecords.add(accountBalanceRecord);
                    CardAccountRecordInfo cardAccountRecord = new CardAccountRecordInfo();
                    cardAccountRecord.firstfield = prefix;
                    cardAccountRecord.pan = pan;
                    cardAccountRecord.seq_nr = "001";
                    cardAccountRecord.account_id = custAccount;
                    cardAccountRecord.account_type_nominated = typex;
                    cardAccountRecord.account_type_qualifier = "1";
                    cardAccountRecord.account_type = typex;
                    cardAccountRecords.add(cardAccountRecord);
                    StatementRecordInfo statementRecord = new StatementRecordInfo();
                    statementRecord.firstfield = prefix;
                    statementRecord.account_id = custAccount;
                    statementRecord.tran_type = "01";
                    statementRecord.tran_amount = "0";
                    statementRecord.tran_local_datetime = dateFormat.dateConvert(date).toString();
                    statementRecord.tran_posted_datetime = dateFormat.dateConvert(date).toString();
                    statementRecord.account_type = typex;
                    statementRecords.add(statementRecord);
                }
            } catch (Exception ex) {
                Logger.getLogger(PostCardService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        postCardData.put("CARDS", cardRecords);
        postCardData.put("ACCOUNTS", accountRecords);
        postCardData.put("ACCOUNTBALANCES", accountBalanceRecords);
        postCardData.put("CARDACCOUNTS", cardAccountRecords);
        postCardData.put("STATEMENT", statementRecords);
        postCardData.put("CARDOVERRIDELIMITS", new CardOverrideLimitRecordInfo());
        postCardData.put("ACCOUNTOVERRIDELIMITS", new AccountOverrideLimitRecordInfo());
        return postCardData;
    }
}
