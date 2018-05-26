package com.ocularminds.expad.app.repository;

import com.ocularminds.expad.app.dao.Db;
import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.ListOutcome;
import com.ocularminds.expad.app.CardGenerators;
import com.ocularminds.expad.app.Queries;
import com.ocularminds.expad.common.Oid;
import com.ocularminds.expad.common.CardUtil;
import com.ocularminds.expad.common.Dates;
import com.ocularminds.expad.model.BranchDistribution;
import com.ocularminds.expad.model.Card;
import com.ocularminds.expad.model.CardSummary;
import com.ocularminds.expad.model.CustomizedCard;
import com.ocularminds.expad.vao.LinkedAccount;
import com.ocularminds.expad.model.MailExtractor;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.Pan;
import com.ocularminds.expad.vao.Product;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cards implements Repo {

    final static String SELECT_CARD_QUERY = "SELECT ID,BATCH_ID,CUSTOMER_ID,CUSTOMER_NAME,PREF_NAME,SOL,PHONE,EMAIL,COLLECTING_SOL,STAFF_ID,PAN,EXPIRY_DT,OFFSET,CREATE_DT,STATUS,PRODUCT_CODE,SERIAL_NO,CUST_TYPE,SCHOOL,DOB  FROM CARD ";
    final static String SELECT_REMP_QUERY = "SELECT ID,BATCH_ID,CUSTOMER_ID,CUSTOMER_NAME,PREF_NAME,SOL,PHONE,EMAIL,COLLECTING_SOL,STAFF_ID,PAN,EXPIRY_DT,OFFSET,CREATE_DT,STATUS,PRODUCT_CODE,SERIAL_NO,CUST_TYPE,SCHOOL,DOB  FROM TEMP_CARD ";

    public Dates dateFormat = new Dates();
    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    public Company company;
    final Pans pans;
    final Products products;
    final Db db;

    @Autowired
    public Cards(final Db db, final Pans pans, final Products products) {
        this.db = db;
        this.pans = pans;
        this.products = products;
    }

    public boolean exists(String accountno) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("select a.pref_name from card a,linkaccount b ");
        sb.append("where  a.status not in('X','R','J','D') and a.id = b.card_id ");
        sb.append("and b.account_no = ? ");
        Object[] params = {accountno};
        return this.exists(sb.toString(), params, db.get(Db.EXPAD));
    }

    public void add(CustomizedCard customizedCard) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(customizedCard);
                oos.flush();
            }
            byte[] bArray = baos.toByteArray();
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .sql("INSERT INTO STD(SID,DATA_OBJ) VALUES(?,?) ")
                    .set(customizedCard.getId())
                    .set(bArray);
            session.insert(Outcome.VOID);
        } catch (IOException | SQLException ex) {
            LOG.warn(null, ex);
        }
    }

    public void notifyBatchDownload(List cards) throws Exception {
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .autocommit(false);
        for (int x = 0; x < cards.size(); ++x) {
            String id = (String) cards.get(x);
            session.sql("update card set status='D' where id = ?")
                    .set(id);
            session.execute();
        }
        session.commit();
    }

    public void notifyDownloadedCardByPan(List cards) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE CARD SET STATUS = 'D',LAST_UPDATE_DT = getdate(),");
        sb.append("LAST_UPDATE_USR = 'AUTO'  WHERE PAN = ?");
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .autocommit(false);
        for (int x = 0; x < cards.size(); ++x) {
            String pan = (String) cards.get(x);
            session.sql(sb.toString())
                    .set(pan);
            session.execute();
        }
        session.commit();
    }

    public void add(Card card) {
        StringBuilder sb = new StringBuilder("INSERT INTO CARD(");
        sb.append("ID,BATCH_ID,CUSTOMER_ID,CUSTOMER_NAME,PREF_NAME,SOL,PHONE,EMAIL,");
        sb.append("PAN,EXPIRY_DT,OFFSET,SERIAL_NO,PRODUCT_CODE,STAFF_ID,CREATE_DT,");
        sb.append("STATUS,COLLECTING_SOL,CUST_TYPE,SCHOOL,DOB) VALUES(");
        sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
        try {
            String photoid = card.getPhotoId();
            Pan pan = null;
            if (card.getPan() != null) {
                pan = new Pan(card.getSerialNo(), card.getPan(), card.getExpiryDate(), card.getProductCode());
            }
            String oid = photoid == null || photoid.trim().equals("") ? Oid.next().get(Oid.Type.MAX) : photoid;
            LOG.info("Oid max " + oid);
            java.sql.Timestamp timeofbirth = null;//card.getDob() != null ? this.getDateTime(card.getDob()) : null;
            if (pan == null) {
                Product product = products.get(card.getProductCode());
                pan = CardGenerators.valueOf(card.getNetwork().toUpperCase())
                        .generate(company, product, card.getCollectingSol());
            }
            Object[] params = {oid, card.getBatchId(), card.getCustomerId(), card.getCustomerName(),
                card.getPreferedName(), card.getSol(), card.getPhone(), card.getEmail(),
                CardUtil.mask(pan.getNo()), pan.getExpiry(), pan.getOffset(), pan.getId(),
                card.getProductCode(), card.getUserId(), new Dates().toTime(),
                "O", card.getCollectingSol(), card.getCustType(), card.getMerchant(), timeofbirth
            };
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .autocommit(false)
                    .sql(sb.toString())
                    .set(oid)
                    .set(card.getBatchId())
                    .set(card.getCustomerId())
                    .set(card.getCustomerName())
                    .set(card.getPreferedName())
                    .set(card.getSol())
                    .set(card.getPhone())
                    .set(card.getEmail())
                    .set(CardUtil.mask(pan.getNo()))
                    .set(pan.getExpiry())
                    .set(pan.getOffset())
                    .set(pan.getId())
                    .set(card.getProductCode())
                    .set(card.getUserId())
                    .set(new Dates().toTime())
                    .set("O")
                    .set(card.getCollectingSol())
                    .set(card.getCustType())
                    .set(card.getMerchant())
                    .set(timeofbirth);
            session.insert(Outcome.VOID);
            session.sql("INSERT INTO ACCOUNT(ACID,ACCT) VALUES(?,?)")
                    .set(oid)
                    .set(CardUtil.toMask(pan.getNo()));
            session.insert(Outcome.VOID);
            List<String> accounts = card.getAccounts();
            for (int x = 0; x < card.getAccounts().size(); x++) {
                session.sql("INSERT INTO LinkAccount(ID,CARD_ID,ACCOUNT_NO) VALUES(?,?,?)")
                        .set(accounts.get(x) + Integer.toString((int) (Math.random() * 899 + 100)))
                        .set(oid)
                        .set(accounts.get(x));
                session.insert(Outcome.VOID);
            }
            session.commit();
            pans.add(pan);
        } catch (Exception ex) {
            LOG.error("[EXPAD] Error creating Card ->", ex);
        }
    }

    public void updateDebitCard(String id, String holder, String phone, String email, String branch) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE CARD  SET PREF_NAME = ?, PHONE = ?,EMAIL = ? WHERE ID = ? ");
        Object[] params = {holder, phone, email, id};
        this.execute(sb.toString(), params, db.get(Db.EXPAD));
    }

    public void addLinkedAccount(String id, String accountno, String pan) {
        try {
            String oid = Oid.next().get(Oid.Type.MAX);
            String q = "INSERT INTO LinkAccount(ID,CARD_ID,ACCOUNT_NO) VALUES(?,?,?) ";
            Object[] params = {oid, id, accountno};
            this.execute(q, params, db.get(Db.EXPAD));
        } catch (Exception ex) {
            LOG.warn("error linking account", ex);
        }
    }

    public void approveCard(String[] ids, String userid) throws Exception {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.approveCard(ids[x], userid);
            }
        }
    }

    public void rejectCard(String[] ids, String userid) throws Exception {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.rejectCard(ids[x], userid);
            }
        }
    }

    public void updateCard(String id, String status, String custname, String email, String phone, String userid) throws Exception {
        String query = "UPDATE CARD SET STATUS = ?,PREF_NAME=?,CUSTOMER_NAME=?,PHONE=?,EMAIL=?,LAST_UPDATE_DT = ?,LAST_UPDATE_USR = ? WHERE ID = ? ";
        Object[] params = {status, custname, custname, phone, email, new java.sql.Date(new java.util.Date().getTime()), userid, id};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    public void finalizeCard(String no, String custid) throws Exception {
        String query = "UPDATE CARD SET STATUS = 'V' WHERE PAN = ? and SERIAL_NO=?";
        Object[] params = {no, custid};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    public void updateCard(String id, String userid, String status) throws Exception {
        String query = "UPDATE CARD SET STATUS = ?," + "LAST_UPDATE_DT = getdate(),LAST_UPDATE_USR = ? WHERE ID = ? ";
        Object[] params = {status, userid, id};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    public void blockCard(String pan, String userid) throws Exception {
        String query = "UPDATE CARD SET STATUS = 'B',LAST_UPDATE_DT = getdate(),LAST_UPDATE_USR =? WHERE PAN = ? ";
        Object[] params = {userid, pan};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    private void approveCard(String id, String userid) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE CARD SET STATUS = 'V',LAST_UPDATE_DT = getdate()");
        sb.append("LAST_UPDATE_USR = ? WHERE ID = ? AND STATUS !='D'");
        Object[] params = {userid, id};
        this.execute(sb.toString(), params, db.get(Db.EXPAD));
    }

    public void notifyPrintedCard(String pan, String userid) throws Exception {
        String query = "UPDATE CARD SET STATUS = 'P',LAST_UPDATE_DT = getdate(),LAST_UPDATE_USR = ? WHERE PAN = ? AND STATUS != 'D'";
        Object[] params = {userid, pan};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    private void rejectCard(String id, String userid) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE CARD SET STATUS = 'R',LAST_UPDATE_DT = getdate(),");
        sb.append("LAST_UPDATE_USR = ?  WHERE ID = ? ");
        Object[] params = {userid, id};
        this.execute(sb.toString(), params, db.get(Db.EXPAD));
    }

    public void removeProcessedPan(String pan) throws Exception {
        String query = "DELETE FROM PAN WHERE NO = ? ";
        Object[] params = {pan};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    public void approveReviewedCard(String id, String userid) throws Exception {
        String query = "UPDATE CARD SET STATUS = 'V',LAST_UPDATE_DT = getdate(),LAST_UPDATE_USR = ?  WHERE ID = ? AND STATUS != 'D'";
        Object[] params = {userid, id};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    public void approveBulkCard(String id, String userid) throws Exception {
        String query = "UPDATE CARD SET STATUS = 'V',LAST_UPDATE_DT = getdate(),LAST_UPDATE_USR = '" + userid + "'  WHERE ID = '" + id + "' AND STATUS != 'D' ";
        Object[] params = {userid, id};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    public void approveBulkCard(String[] ids, String userid) throws Exception {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.approveBulkCard(ids[x], userid);
            }
        }
    }

    public void logPanInformation(String pan, String exp, String branchCode) throws Exception {
        String query = "INSERT INTO PAN_INFO VALUES(?,?,?)";
        Object[] params = {pan, exp, branchCode};
        this.execute(query, params, db.get(Db.EXPAD));
    }

    public void deleteDebitCardCard(String[] ids) throws Exception {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.deleteDebitCard(ids[x]);
            }
        }
    }

    public void notifyPinMailer(String batchid, String expiry) {
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .sql("UPDATE CARD SET STATUS ='F',EXPIRY_DT = ? WHERE BATCH_ID = ?")
                    .set(new Dates().toSQL(expiry))
                    .set(batchid);
            session.insert(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.warn(null, ex);
        }
    }

    public void reversePostedTransaction(String pan) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO PAN(OID,NO,EXPIRY_DT,OFFSET,STATUS,BRANCH_CODE)  ");
            sb.append("SELECT '").append(Oid.next().get(Oid.Type.LONG))
                    .append("' AS OID,PAN AS NO,EXPIRY_DT,");
            sb.append("OFFSET,'D' AS STATUS,SOL as BRANCH_CODE ");
            sb.append("FROM CARD WHERE PAN = ?");
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .autocommit(false)
                    .sql(sb.toString())
                    .set(pan);
            session.insert(Outcome.VOID);
            session.sql("DELETE FROM CARD WHERE PAN  = ?")
                    .set(pan);
            session.update(Outcome.VOID);
            session.commit();
        } catch (Exception ex) {
            LOG.warn("[EXPAD] Error reversing created card ->", ex);
        }
    }

    public void deleteDebitCard(String id) throws Exception {
        String q = "DELETE FROM CARD  WHERE ID = ? AND STATUS = 'O' ";
        Object[] params = {id};
        this.execute(q, params, db.get(Db.EXPAD));
    }

    public void notifyDownloadedDebitCardByPan(String pan) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE CARD SET STATUS = 'D',LAST_UPDATE_DT = getdate(),");
        sb.append("LAST_UPDATE_USR = 'AUTO'  WHERE PAN = ?");
        Object[] params = {pan};
        this.execute(sb.toString(), params, db.get(Db.EXPAD));
    }

    public void notifyDownloadedDebitCard(String id) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE CARD SET STATUS = 'D',LAST_UPDATE_DT = getdate(),");
        sb.append("LAST_UPDATE_USR = 'AUTO'  WHERE ID =?");
        Object[] params = {id};
        this.execute(sb.toString(), params, db.get(Db.EXPAD));
    }

    public void sendBulkMessage(List groupMails, String messageContent) {
        for (int x = 0; x < groupMails.size(); ++x) {
            MailExtractor me = (MailExtractor) groupMails.get(x);
            String[] to = new String[]{me.getEmail()};
            String subject = "EXPAD NOTIFICATIONS !";
            String from = "info@ocular-minds.com";
            this.sendMessage(to, from, subject, messageContent);
        }
    }

    public void sendMessage(String[] to, String from, String subject, String messageContent) {
        //this.mailer.sendMail(from, to, this.company.getMailServer(), subject, messageContent);
    }

    public void notifyBatchStatus(String batchid, String status) throws Exception {
        String q = "UPDATE BatchRecord SET PIN_STATUS = ? WHERE ID = ? ";
        Object[] params = {status, batchid};
        this.execute(q, params, db.get(Db.EXPAD));
    }

    /*
     *
    public void logCardIssue(String id, String type, String name) {
        Connection con = null;
        PreparedStatement ps = null;
        String endpointFunction = "";
        String msg1 = "";
        String q = "INSERT INTO CARDTRACKS(ID,TRACK_TYPE,STATUS,LOG_DATE) VALUES(?,?,?,?) ";
        try {
            con = this.getConnection("expad");
            ps = con.prepareStatement(q);
            ps.setString(1, id);
            ps.setString(2, type);
            ps.setString(3, "O");
            ps.setDate(4, this.dateConvert(new java.util.Date()));
            ps.execute();
            if (type.equalsIgnoreCase("P")) {
                endpointFunction = "pinsForAuthorization";
                msg1 = "Card by Customer:" + name + " is to be HOTLISTED \n" + " Kindly Log in to Expad\n" + "and complete the requested operation.";
            } else {
                endpointFunction = "cardsForAuthorization";
                msg1 = "New Pin Re-Issuance by Customer:" + name + "\n" + "is available for your approval. Kindly Log in to Expad\n" + "and complete the requested operation.";
            }
            ArrayList mailGroup1 = this.extractEmailByFunction(endpointFunction);
            this.sendBulkMessage(mailGroup1, msg1);
        }
        catch (Exception e) {
            System.out.println("[EXPAD] Error logging CardIssue -> " + e.getMessage());
        }
        finally {
            this.closeConnection(con, ps);
        }
    }*/

 /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void createBatch(String id, String userid) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO BatchRecord(ID,DOWNLOAD_DATE,USER_ID,PIN_STATUS,CAD_STATUS) ");
        sb.append("VALUES(?,?,?,?,?) ");

        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .sql(sb.toString())
                    .set(id)
                    .set(new Dates().toTime())
                    .set(userid)
                    .set("O")
                    .set("D");
            session.insert(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.warn("[EXPAD] Error creating Batch ID -> ", ex);
        }
    }

    public List<Card> expiring() throws Exception {
        String froDate = this.dateFormat.getFirstDateOfMonth();
        String toDate = this.dateFormat.getLastDateOfMonth();
        String dFromDate = froDate.substring(6, 10) + "-" + froDate.substring(3, 5) + "-" + froDate.substring(0, 2);
        String dToDate = toDate.substring(6, 10) + "-" + toDate.substring(3, 5) + "-" + toDate.substring(0, 2);
        String filter = " WHERE EXPIRY_DT BETWEEN '" + dFromDate + "' AND '" + dToDate + " 23:59:59.997'";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter)
                .set(dFromDate)
                .set(dToDate);
        return records(session);
    }

    public boolean isCardProcessed(String pan) throws Exception {
        return this.find(pan) != null;
    }

    public Card find(String no) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + " WHERE PAN = ?")
                .set(no)
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next() ? read(rs) : null);
    }

    public List<Card> findCardByAccountNoRetList(String accountno) throws Exception {
        String filter = " WHERE ID in (select card_id from linkaccount where account_no = ?') ";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter)
                .set(accountno);
        return records(session);
    }

    public Card findCardByAccountNo(String accountno) throws Exception {
        String filter = " WHERE ID in (select card_id from linkaccount where account_no = ?)";
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter)
                .set(accountno)
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next() ? read(rs) : null);
    }

    public Card get(String id) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + " WHERE ID = ?")
                .set(id)
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next() ? read(rs) : null);
    }

    public Card findCardBySerialNo(String id) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + " WHERE SERIAL_NO = ?")
                .set(id)
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next() ? read(rs) : null);
    }

    public List<CustomizedCard> findCustomizedCardRecordForDownload(List ids) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SID,DATA_OBJ FROM STD WHERE SID IN");
        sql.append(Queries.fromCards(ids));
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(sql.toString())
                .select(new ListOutcome<>((final ResultSet rs) -> rs.next() ? customized(rs) : null));
    }

    public CustomizedCard findCustomizedCardById(String id) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT SID,DATA_OBJ FROM STD where SID = ?")
                .set(id)
                .select((final ResultSet rs, java.sql.Statement s) -> (rs.next()) ? customized(rs) : null);
    }

    public List<Card> findCardByCustomer(String custid) throws Exception {
        String filter = " WHERE CUSTOMER_ID = ? ORDER BY COLLECTING_SOL,PREF_NAME ";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter)
                .set(custid);
        return records(session);
    }

    public List<Card> findCardByCustomerName(String name) throws Exception {
        String filter = " WHERE CUSTOMER_NAME LIKE ? ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter)
                .set("%" + name + "%");
        return records(session);
    }

    public List<Card> findCardForDownload() throws Exception {
        String filter = " WHERE STATUS = 'V' ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter);
        return records(session);
    }

    public List<Card> findBranchCardForApproval(String branchCode) throws Exception {
        String filter = " WHERE STATUS = 'O' AND COLLECTING_SOL=? ORDER BY PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter).set(branchCode);
        return records(session);
    }

    public List<Card> findViaCardForDownload() throws Exception {
        String filter = " WHERE STATUS = 'V' AND PRODUCT_CODE='0000006' ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter);
        return records(session);
    }

    public List<Card> findNormalCardForDownload() throws Exception {
        String filter = " WHERE STATUS = 'V' AND CUST_TYPE='C' ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter);
        return records(session);
    }

    public List<Card> findStudentCardForDownload() throws Exception {
        String filter = " WHERE STATUS = 'V' AND CUST_TYPE='S' ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter);
        return records(session);
    }

    public List<Card> findStudentCardForDownload(String merchant) throws Exception {
        String filter = " WHERE STATUS = 'V' AND CUST_TYPE='S' AND lower(trim(MERCH_ID))= ? ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter)
                .set(merchant.trim().toLowerCase());
        return records(session);
    }

    public List<Card> findNyscCardForDownload() throws Exception {
        String filter = " WHERE STATUS = 'V' AND CUST_TYPE='N' ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter);
        return records(session);
    }

    public List<Card> findValCardForDownload() throws Exception {
        String filter = " WHERE STATUS = 'V' AND CUST_TYPE='V' ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter);
        return records(session);
    }

    public List<Card> findBatchCardForApproval() throws Exception {
        String filter = " WHERE BATCH_ID IS NOT NULL AND STATUS='O' ORDER BY COLLECTING_SOL,PREF_NAME";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter);
        return records(session);
    }

    public List<Card> findCardForIssue(String branchCode) throws Exception {
        String filter = " WHERE STATUS = 'A' AND COLLECTION_SOL = ? ORDER BY COLLECTING_SOL,PREF_NAME";
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter)
                .set(branchCode)
                .select(new ListOutcome<>(this::read));
    }

    public List<Card> findCardByBatch(String batchid) throws Exception {
        String filter = " WHERE BATCH_ID = ? ORDER BY COLLECTING_SOL,PREF_NAME";
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter)
                .set(batchid)
                .select(new ListOutcome<>(this::read));
    }

    public List<Card> findPanForDownload() throws Exception {
        String filter = " WHERE STATUS = 'O' ";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter);
        return records(session);
    }

    public List<Card> findPanForDownload(String prod) throws Exception {
        String filter = " WHERE STATUS = 'O' AND PRODUCT_CODE = ? ";
        JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                .sql(Cards.SELECT_CARD_QUERY + filter).set(prod);
        return records(session);
    }

    public List<LinkedAccount> accounts(String requestid) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("select ID,CARD_ID,ACCOUNT_NO  FROM LinkAccount WHERE CARD_ID = ?")
                .set(requestid)
                .select(new ListOutcome<>((final ResultSet rs) -> {
                    String id = rs.getString("ID");
                    String accountNo = rs.getString("ACCOUNT_NO");
                    String requestId = rs.getString("CARD_ID");
                    return new LinkedAccount(id, requestId, accountNo);
                }));
    }

    public List<Card> findTemporaryCardByQuery(String filter) throws Exception {
        String q = "SELECT ID,BATCH_ID,CUSTOMER_ID,CUSTOMER_NAME,PREF_NAME,SOL,PHONE,EMAIL,COLLECTING_SOL,STAFF_ID,PAN,EXPIRY_DT,OFFSET,CREATE_DT,STATUS,PRODUCT_CODE,SERIAL_NO,CUST_TYPE,MERCH_ID,DOB  FROM TEMP_CARD " + filter;
        return this.findThisCardByQuery(q);
    }

    public List<Card> findThisCardByQuery(String query) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(query)
                .select(new ListOutcome<>(this::read));
    }

    public String status(String status) {
        if (status != null) {
            status = status.equalsIgnoreCase("O") ? "OPEN FOR APPROVAL" : (status.equalsIgnoreCase("I") ? "ACTIVATED" : (status.equalsIgnoreCase("D") ? "DOWNLOADED FOR UPLOAD" : (status.equalsIgnoreCase("P") ? "AUTHORIZED FOR CALLOVER" : (status.equalsIgnoreCase("A") ? "APRROVED" : (status.equalsIgnoreCase("V") ? "VERIFIED" : "REJECTED")))));
        }
        return status;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<CardSummary> summary(String filter) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT distinct(COLLECTING_SOL),COUNT(PAN)AS TOTAL_CARDS FROM CARD ");
        sb.append("where UPPER(PREF_NAME) NOT LIKE '%PAYDIRECT%'  ".concat(filter));
        sb.append(" GROUP BY COLLECTING_SOL ORDER BY 2 DESC");
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(sb.toString())
                .select(new ListOutcome<>((final ResultSet rs) -> {
                    String branchCode = rs.getString("COLLECTING_SOL");
                    String userId = "STAFF";
                    int totalCards = rs.getInt("TOTAL_CARDS");
                    return new CardSummary(branchCode, userId, totalCards);
                }));
    }

    public List<BranchDistribution> distribution() throws Exception {
        StringBuilder sb = new StringBuilder("SELECT BRANCH_CODE, MIN(OID) AS START_OID,");
        sb.append("MAX(OID) AS END_OID FROM PAN WHERE BRANCH_CODE IS NOT NULL ");
        sb.append("AND STATUS = 'O' GROUP BY BRANCH_CODE ORDER BY 2");
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(sb.toString())
                .select(new ListOutcome<>((final ResultSet rs) -> {
                    String code = rs.getString("BRANCH_CODE");
                    String from = rs.getString("START_OID");
                    String to = rs.getString("END_OID");
                    return new BranchDistribution(code, from, to);
                }));
    }

    private Card read(final ResultSet rs) throws java.sql.SQLException {
        String id = rs.getString("ID");
        String batchId = rs.getString("BATCH_ID");
        String customerId = rs.getString("CUSTOMER_ID");
        String name = rs.getString("CUSTOMER_NAME");
        String holder = rs.getString("PREF_NAME");
        String sol = rs.getString("SOL");
        String phone = rs.getString("PHONE");
        String email = rs.getString("EMAIL");
        String branch = rs.getString("COLLECTING_SOL");
        String pan = rs.getString("PAN");
        String exp = rs.getString("EXPIRY_DT");
        String offset = rs.getString("OFFSET");
        String userId = rs.getString("STAFF_ID");
        String date = new Dates(rs.getDate("CREATE_DT")).format();
        String status = rs.getString("STATUS");
        String prod = rs.getString("PRODUCT_CODE");
        String serialNo = rs.getString("SERIAL_NO");
        String custType = rs.getString("CUST_TYPE");
        String merchant = rs.getString("SCHOOL");
        String dob = rs.getString("DOB");
        return new Card(id, batchId, customerId, name, holder, sol, phone, email, pan, exp, offset, userId, date, branch, status, prod, serialNo, custType, merchant, dob);
    }

    private CustomizedCard customized(final ResultSet rs) throws java.sql.SQLException {
        CustomizedCard s = null;
        try {
            String id = rs.getString("SID");
            InputStream is = rs.getBlob("DATA_OBJ").getBinaryStream();
            ObjectInputStream oip = new ObjectInputStream(is);
            s = (CustomizedCard) oip.readObject();
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            LOG.warn(null, ex);
        }
        return s;
    }

    private List<Card> records(JdbcSession session) throws Exception {
        return session.select(new ListOutcome<>((final ResultSet rs) -> read(rs)));
    }
}
