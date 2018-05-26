package com.ocularminds.expad.app.repository;

import com.ocularminds.expad.app.dao.Db;
import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.ocularminds.expad.common.Dates;
import com.ocularminds.expad.model.Script;
import com.ocularminds.expad.vao.Company;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Configurator implements Repo {

    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Db db;

    @Autowired
    public Configurator(final Db db) {
        this.db = db;
    }

    public void update(Company company) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE CompanyProfile SET COMPANY_NAME = ? ,SHORT_NAME = ?   ,");
        sb.append("ADDRESS = ?,MAX_REQUEST = ?,MAX_ACCOUNT = ?,EXCLUDE_ACCOUNT = ? ,");
        sb.append("RESET_FLAG = ?,LAST_MODIFICATION = ?,MIN_PASSWORD = ?,PAS_EXPIRY = ?,");
        sb.append("SES_IDLE_TIME = ?,MAIL_SERVER = ?,DOWN_TYPE = ?,FREQ = ?,RES_CODE = ?,");
        sb.append("SEQ_NO = ?, PIN_OFFSET = ?, CAD_PROG = ?,INA_CARD = ?,");
        sb.append("ATTEMPT_LIMIT = ?,ACCT_SIZE = ?,ACCT_START_POS = ?,");
        sb.append("ACCT_END_POS = ?,SAVINGS_MAP_CODE = ?,CURRENT_MAP_CODE = ?,");
        sb.append("PAN_SIZE = ?,PREFIX_WITH_U = ?,DOWNLOAD_DIR = ?");
        try {
            new JdbcSession(db.get(Db.EXPAD))
                    .sql(sb.toString())
                    .set(company.getName())
                    .set(company.getShortName())
                    .set(company.getAddress())
                    .set(company.getMaximumRequestPerCustomer())
                    .set(company.getMaximumAccountPerRequest())
                    .set(company.getExcludeAccountType())
                    .set(company.getResetFlag())
                    .set(new Dates().toSQL(new Date()))
                    .set(company.getMinimumPassword())
                    .set(company.getPasswordExpiry())
                    .set(company.getSessionIdleTime())
                    .set(company.getMailServer())
                    .set(company.getDownloadType())
                    .set(company.getDownloadFrequency())
                    .set(company.getRestrictionCode())
                    .set(company.getSequenceNo())
                    .set(company.getPinOffset())
                    .set(company.getCardProgram())
                    .set(company.getInActiveCardCode())
                    .set(company.getAttemptLimit())
                    .set(company.getAccountSize())
                    .set(company.getAccountStartPosition())
                    .set(company.getAccountEndPosition())
                    .set(company.getCurrentMapCode())
                    .set(company.getSavingsMapCode())
                    .set(company.getPanSize())
                    .set(company.isPrefixedWithU())
                    .set(company.getPostCardDirectory())
                    .update(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.error("Error updating company profile -> ", ex);
        }
    }

    public Company get() throws Exception {
        StringBuilder sb = new StringBuilder("SELECT COMPANY_NAME ,SHORT_NAME,ADDRESS ,");
        sb.append("MAX_REQUEST,MAX_ACCOUNT,EXCLUDE_ACCOUNT,RESET_FLAG,LAST_MODIFICATION,");
        sb.append("MIN_PASSWORD,PAS_EXPIRY ,ATTEMPT_LIMIT, SES_IDLE_TIME,MAIL_SERVER,");
        sb.append("DOWN_TYPE,FREQ,RES_CODE,SEQ_NO,PIN_OFFSET,CAD_PROG,INA_CARD,ACCT_SIZE,");
        sb.append("ACCT_START_POS,ACCT_END_POS,SAVINGS_MAP_CODE,CURRENT_MAP_CODE,");
        sb.append("PAN_SIZE,PREFIX_WITH_U,DOWNLOAD_DIR FROM CompanyProfile");
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(sb.toString())
                .select((final ResultSet rs, java.sql.Statement s) -> {
                    rs.next();
                    String name = rs.getString("COMPANY_NAME");
                    String shortName = rs.getString("SHORT_NAME");
                    String address = rs.getString("ADDRESS");
                    int maximumRequestPerCustomer = rs.getInt("MAX_REQUEST");
                    int maximumAccountPerRequest = rs.getInt("MAX_ACCOUNT");
                    String excludeAccountType = rs.getString("EXCLUDE_ACCOUNT");
                    String resetFlag = rs.getString("RESET_FLAG");
                    java.util.Date lastMod = rs.getDate("LAST_MODIFICATION");
                    String lastModification = new Dates(lastMod).format();
                    int minimumPassword = rs.getInt("MIN_PASSWORD");
                    int passwordExpiry = rs.getInt("PAS_EXPIRY");
                    int sessionIdleTime = rs.getInt("SES_IDLE_TIME");
                    String mailServer = rs.getString("MAIL_SERVER");
                    String downloadType = rs.getString("DOWN_TYPE");
                    String downloadFrequency = rs.getString("FREQ");
                    String restrictionCode = rs.getString("RES_CODE");
                    String sequenceNo = rs.getString("SEQ_NO");
                    String pinOffset = rs.getString("PIN_OFFSET");
                    String cardProgram = rs.getString("CAD_PROG");
                    String inActiveCardCode = rs.getString("INA_CARD");
                    int attemptLimit = rs.getInt("ATTEMPT_LIMIT");
                    int accountSize = rs.getInt("ACCT_SIZE");
                    int accountStartPosition = rs.getInt("ACCT_START_POS");
                    int accountEndPosition = rs.getInt("ACCT_END_POS");
                    String currentMapCode = rs.getString("SAVINGS_MAP_CODE");
                    String savingsMapCode = rs.getString("CURRENT_MAP_CODE");
                    int panSize = rs.getInt("PAN_SIZE");
                    String strPrefixWithU = rs.getString("PREFIX_WITH_U");
                    String postCardDirectory = rs.getString("DOWNLOAD_DIR");
                    boolean prefixedWithU = strPrefixWithU != null && strPrefixWithU.equalsIgnoreCase("Y");
                    return new Company(name, shortName, address, maximumRequestPerCustomer, maximumAccountPerRequest, excludeAccountType, resetFlag, lastModification, minimumPassword, passwordExpiry, sessionIdleTime, mailServer, downloadType, downloadFrequency, restrictionCode, sequenceNo, pinOffset, cardProgram, inActiveCardCode, attemptLimit, accountSize, accountStartPosition, accountEndPosition, currentMapCode, savingsMapCode, panSize, prefixedWithU, postCardDirectory);

                });
    }

    public boolean exists() throws Exception {
        String q = "SELECT COMPANY_NAME FROM CompanyProfile";
        Object[] params = {};
        return this.exists(q, params, db.get(Db.EXPAD));
    }

    public Script script() throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT HOST_DB,ACCT_SCRIPT,CUSTOMER_SCRIPT,BRANCH_SCRIPT FROM APPS_CONFIG")
                .select((final ResultSet rs, java.sql.Statement s) -> script(rs));
    }

    public void save(Script script) {
        try {
            final String VALIDATE = "select customer_script from apps_config";
            final String INSERT = "INSERT INTO APPS_CONFIG("
                    + "HOST_DB,ACCT_SCRIPT,CUSTOMER_SCRIPT,BRANCH_SCRIPT"
                    + ") VALUES(?, ?, ? ,?)";
            final String UPDATE = "UPDATE APPS_CONFIG SET HOST_DB = ? ,ACCT_SCRIPT = ? ,"
                    + "CUSTOMER_SCRIPT = ?,BRANCH_SCRIPT = ?";
            Object[] params = {
                script.getDb(), script.getAccount(), script.getCustomer(), script.getBranch()
            };
            if (exists(VALIDATE, new Object[]{}, db.get(Db.EXPAD))) {
                this.execute(UPDATE, params, db.get(Db.EXPAD));
            } else {
                this.execute(INSERT, params, db.get(Db.EXPAD));
            }
        } catch (Exception ex) {
            LOG.warn(null, ex);
        }
    }

    private Script script(ResultSet rs) {
        try {
            if (rs.next()) {
                String hostDb = rs.getString("HOST_DB");
                String account = rs.getString("ACCT_SCRIPT");
                String customer = rs.getString("CUSTOMER_SCRIPT");
                String branch = rs.getString("BRANCH_SCRIPT");
                String details = null;//rs.getString("CUSTOMER_DETAIL");
                return new Script(hostDb, account, customer, branch, details);
            } else {
                return new Script();
            }
        } catch (SQLException ex) {
            LOG.info("error loading scripts -> ", ex);
            return new Script();
        }
    }
}
