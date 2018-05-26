package com.ocularminds.expad.svc;

import com.ocularminds.util.DatetimeFormat;
import com.ocularminds.expad.vao.AccountBalanceRecordInfo;
import com.ocularminds.expad.vao.AccountOverrideLimitRecordInfo;
import com.ocularminds.expad.vao.AccountRecordInfo;
import com.ocularminds.expad.vao.CardAccountRecordInfo;
import com.ocularminds.expad.vao.CardOverrideLimitRecordInfo;
import com.ocularminds.expad.vao.CardRecordInfo;
import com.ocularminds.expad.vao.StatementRecordInfo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostilionService {

    private final DatetimeFormat dateFormat;
    private final PostCardService service;
    final int CARD_LOAD = 1;
    final int CARD_UPDATE = 2;
    public int DEBIT_CARD = 0;
    public int CREDIT_CARD = 1;
    boolean sucessfull;
    Connection con = null;
    CallableStatement cs = null;
    static final Logger LOG = LoggerFactory.getLogger(PostilionService.class.getName());

    public PostilionService(final PostCardService svc) {
        LOG.info("Using PostilionService..");
        this.dateFormat = new DatetimeFormat();
        this.service = svc;
    }

    public void setSuccessfull(boolean sucessfull) {
        this.sucessfull = sucessfull;
    }

    public boolean isSucessfull() {
        return this.sucessfull;
    }

    public void execute() {
        List list;
        try {
            list = this.service.getCardService().findCardForDownload();
            Map cardRecords = this.service.getGeneratedPostCardData((ArrayList) list);
            this.uploadCardInformation(cardRecords);
            InfrastructureToolbox.clearHoldResponseCode();
        } catch (Exception ex) {
            LOG.error("", ex);
        }
    }

    public void buildHOTCARDStructureData(String pan, String expiryDate, String actionCode) {
        HashMap<String, String> isoXmlData = new HashMap<String, String>();
        isoXmlData.put("PAN", pan);
        isoXmlData.put("EXPIRY_DT", expiryDate);
        isoXmlData.put("ACTION_CODE", actionCode);
    }

    public void uploadCardInformation(Map dataFile) {
        ArrayList cardRecords = (ArrayList) dataFile.get("CARDS");
        ArrayList cardAccountRecords = (ArrayList) dataFile.get("CARDACCOUNTS");
        ArrayList accountRecords = (ArrayList) dataFile.get("ACCOUNTS");
        ArrayList accountBalanceRecords = (ArrayList) dataFile.get("ACCOUNTBALANCES");
        ArrayList statementRecords = (ArrayList) dataFile.get("STATEMENT");
        ArrayList processedCards = new ArrayList();
        java.util.Date date = new java.util.Date();
        String last_updated_date = this.dateFormat.dateConvert(date).toString();
        String last_updated_user = "expad";
        int issuer_nr = 1;
        this.uploadCardInfo(processedCards, cardRecords, issuer_nr, last_updated_date, last_updated_user, date);
        this.uploadCardAccountInfo(cardAccountRecords, issuer_nr, last_updated_date, last_updated_user);
        this.uploadAccountInfo(accountRecords, issuer_nr, last_updated_date, last_updated_user);
        this.uploadAccountBalanceInfo(accountBalanceRecords, issuer_nr, last_updated_date, last_updated_user);
        this.uploadStatementInfo(statementRecords, issuer_nr, last_updated_date, last_updated_user);
        this.updateCardAfterDownload(processedCards);
        try {
            InfrastructureToolbox.updateCardProductionAfterFullLoad(issuer_nr);
        } catch (SQLException x) {
            LOG.info("Error updating CardProductionAfterFullLoad {0}", x);
        }
    }

    public void updateCardAfterDownload(ArrayList records) {
        for (int j = 0; j < records.size(); ++j) {
            try {
                this.service.getCardService().notifyDownloadedDebitCardByPan((String) records.get(j));
            } catch (Exception ex) {
                LOG.error("", ex);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void uploadCardInfo(ArrayList processedCards, ArrayList cardRecords, int issuer_nr, String last_updated_date, String last_updated_user, java.util.Date date) {
        LOG.info("uploading card records...");
        String xtime = Long.toString(date.getTime());
        String batch_nr = xtime.substring(xtime.length() - 7, xtime.length());
        this.setSuccessfull(true);
        try {
            this.con = InfrastructureToolbox.getConnection("postilion_postcard");
            this.cs = InfrastructureToolbox.getCallToStoreCardRecord42(this.con);
            for (int x = 0; x < cardRecords.size(); ++x) {
                CardRecordInfo cardRecordInfo = (CardRecordInfo) cardRecords.get(x);
                InfrastructureToolbox.storeCardRecord42(this.cs, issuer_nr, cardRecordInfo, batch_nr, last_updated_date, last_updated_user);
            }
            int[] affectedRows = this.cs.executeBatch();
            LOG.info("storeCardRecord ->{0} rows affected", affectedRows.length);
            this.cs = InfrastructureToolbox.getCallToStoreCustomerRecord(this.con);
            for (int x2 = 0; x2 < cardRecords.size(); ++x2) {
                CardRecordInfo cardRecordInfo = (CardRecordInfo) cardRecords.get(x2);
                InfrastructureToolbox.storeCustomerRecord(this.cs, issuer_nr, cardRecordInfo, batch_nr, last_updated_date, last_updated_user);
                processedCards.add(cardRecordInfo.pan);
            }
            affectedRows = this.cs.executeBatch();
            LOG.info("storeCustomerRecord ->{0} rows affected", affectedRows.length);
            InfrastructureToolbox.commitAndClose(this.con);
        } catch (SQLException e) {
            LOG.info("Error uploading card records :{0}", e);
            this.setSuccessfull(false);
        } finally {
            InfrastructureToolbox.cleanup(this.con, this.cs);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void uploadCardAccountInfo(ArrayList cardAccountRecords, int issuer_nr, String last_updated_date, String last_updated_user) {
        LOG.info("Uploading CardAccountInfo...");
        String batch_nr = "0001";
        this.setSuccessfull(true);
        try {
            this.con = InfrastructureToolbox.getConnection("postilion_postcard");
            this.cs = InfrastructureToolbox.getCallToStoreCardAccountRecord(this.con);
            for (int x = 0; x < cardAccountRecords.size(); ++x) {
                CardAccountRecordInfo cardAccountRecord = (CardAccountRecordInfo) cardAccountRecords.get(x);
                InfrastructureToolbox.storeCardAccountRecord(this.cs, issuer_nr, cardAccountRecord.pan, cardAccountRecord.seq_nr, cardAccountRecord.account_id, cardAccountRecord.account_type_nominated, cardAccountRecord.account_type_qualifier, last_updated_date, last_updated_user, cardAccountRecord.account_type);
            }
            int[] affectedRows = this.cs.executeBatch();
            LOG.info("storeCardAccountRecord ->{0} rows affected", affectedRows.length);
            InfrastructureToolbox.commitAndClose(this.con);
        } catch (SQLException e) {
            LOG.info("Error uploading CardAccountInfo:{0}", e);
            this.setSuccessfull(false);
        } finally {
            InfrastructureToolbox.cleanup(this.con, this.cs);
        }
    }

    public void uploadAccountInfo(ArrayList accountRecords, int issuer_nr, String last_updated_date, String last_updated_user) {
        LOG.info("uploading AccountInfo...");
        this.setSuccessfull(true);
        try {
            this.con = InfrastructureToolbox.getConnection("postilion_postcard");
            this.cs = InfrastructureToolbox.getCallToStoreAccountRecord(this.con);
            for (int x = 0; x < accountRecords.size(); ++x) {
                AccountRecordInfo accountRecordInfo = (AccountRecordInfo) accountRecords.get(x);
                InfrastructureToolbox.storeAccountRecord(this.cs, issuer_nr, accountRecordInfo.account_id, accountRecordInfo.account_type, accountRecordInfo.currency_code, last_updated_date, last_updated_user, accountRecordInfo.customer_id);
            }
            int[] affectedRows = this.cs.executeBatch();
            LOG.info("storeAccountRecord ->{0} rows affected", affectedRows.length);
            InfrastructureToolbox.commitAndClose(this.con);
        } catch (SQLException e) {
            LOG.info("Error uploading AccountInfo records :{0}", e);
            this.setSuccessfull(false);
        } finally {
            InfrastructureToolbox.cleanup(this.con, this.cs);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void uploadAccountBalanceInfo(ArrayList accountBalanceRecords, int issuer_nr, String last_updated_date, String last_updated_user) {
        LOG.info("uploading AccountBalanceInfo...");
        this.setSuccessfull(true);
        try {
            this.con = InfrastructureToolbox.getConnection("postilion_postcard");
            this.cs = InfrastructureToolbox.getCallToStoreAccountBalanceRecord(this.con);
            for (int x = 0; x < accountBalanceRecords.size(); ++x) {
                AccountBalanceRecordInfo accountBalanceRecord = (AccountBalanceRecordInfo) accountBalanceRecords.get(x);
                InfrastructureToolbox.storeAccountBalanceRecord(this.cs, issuer_nr, accountBalanceRecord.account_id, accountBalanceRecord.ledger_balance, accountBalanceRecord.available_balance, last_updated_date, accountBalanceRecord.account_type);
            }
            int[] affectedRows = this.cs.executeBatch();
            LOG.info("storeAccountBalanceRecord ->{0} rows affected", affectedRows.length);
            InfrastructureToolbox.commitAndClose(this.con);
        } catch (SQLException e) {
            LOG.info("Error uploading AccountBalanceInfo records :{0}", e);
            this.setSuccessfull(false);
        } finally {
            InfrastructureToolbox.cleanup(this.con, this.cs);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void uploadCardOverrrideInfo(CardOverrideLimitRecordInfo cardOverrideInfo, int issuer_nr, String last_updated_date, String last_updated_user) {
        LOG.info("uploading CardOverrrideInfo...");
        String batch_nr = "0001";
        this.setSuccessfull(true);
        try {
            this.con = InfrastructureToolbox.getConnection("postilion_postcard");
            this.cs = InfrastructureToolbox.getCallToStoreCardOverrideLimitRecord(this.con);
            InfrastructureToolbox.storeCardOverrideLimitRecord(this.cs, issuer_nr, cardOverrideInfo, last_updated_date, last_updated_user);
            int[] affectedRows = this.cs.executeBatch();
            LOG.info("storeCardOverrideLimitRecord ->{0} rows affected", affectedRows.length);
            InfrastructureToolbox.commitAndClose(this.con);
        } catch (SQLException e) {
            LOG.info("Error uploading CardOverrrideInfo :{0}", e);
            this.setSuccessfull(false);
        } finally {
            InfrastructureToolbox.cleanup(this.con, this.cs);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void uploadAccountOverrideInfo(AccountOverrideLimitRecordInfo accountOverrrideRecordInfo, int issuer_nr, String last_updated_date, String last_updated_user) {
        LOG.info("uploading AccountOverrideInfo records...");
        String batch_nr = "0001";
        this.setSuccessfull(true);
        try {
            this.con = InfrastructureToolbox.getConnection("postilion_postcard");
            this.cs = InfrastructureToolbox.getCallToStoreAccountOverrideLimitRecord(this.con);
            InfrastructureToolbox.storeAccountOverrideLimitRecord(this.cs, issuer_nr, accountOverrrideRecordInfo, last_updated_date, last_updated_user);
            int[] affectedRows = this.cs.executeBatch();
            LOG.info("storeAccountOverrideLimitRecord ->{0} rows affected", affectedRows.length);
            InfrastructureToolbox.commitAndClose(this.con);
        } catch (SQLException e) {
            LOG.info("Error uploading AccountOverrideInfo records :{0}", e);
            this.setSuccessfull(false);
        } finally {
            InfrastructureToolbox.cleanup(this.con, this.cs);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void uploadStatementInfo(ArrayList statementRecordInfos, int issuer_nr, String last_updated_date, String last_updated_user) {
        LOG.info("Uploading StatementRecordInfo records...");
        String batch_nr = "0001";
        this.setSuccessfull(true);
        try {
            this.con = InfrastructureToolbox.getConnection("postilion_postcard");
            this.cs = InfrastructureToolbox.getCallToStoreStatementRecord42(this.con);
            for (int x = 0; x < statementRecordInfos.size(); ++x) {
                StatementRecordInfo statementRecordInfo = (StatementRecordInfo) statementRecordInfos.get(x);
                InfrastructureToolbox.storeStatementRecord42(this.cs, issuer_nr, statementRecordInfo.account_id, statementRecordInfo.tran_type, statementRecordInfo.tran_amount, statementRecordInfo.tran_local_datetime, statementRecordInfo.tran_posted_datetime, last_updated_date, statementRecordInfo.account_type);
            }
            int[] affectedRows = this.cs.executeBatch();
            LOG.info("StatementRecordInfo ->{0} rows affected", affectedRows.length);
            InfrastructureToolbox.commitAndClose(this.con);
        } catch (SQLException e) {
            LOG.info("Error uploading StatementRecordInfo records :{0}", e);
            this.setSuccessfull(false);
        } finally {
            InfrastructureToolbox.cleanup(this.con, this.cs);
        }
    }

    public static void main(String[] args) {
    }
}
