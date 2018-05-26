package com.ocularminds.expad.app.repository;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.ocularminds.expad.common.Oid;
import com.ocularminds.expad.app.dao.Dao;
import com.ocularminds.expad.app.dao.Db;
import com.ocularminds.expad.common.Dates;
import com.ocularminds.expad.model.AuditElement;
import com.ocularminds.expad.model.ModuleAudit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jejelowo B. Festus <festus.jejelowo@ocularminds.com>
 */
@Component
public class Audits extends Dao {

    static final Logger LOG = LoggerFactory.getLogger(Audits.class);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Db db;
    Dates dates;

    @Autowired
    public Audits(Db db) {
        this.db = db;
        this.dates = new Dates();
    }

    public void add(AuditElement audit) {
        String SQL = "INSERT INTO AUDITS(AUDIT_INDEX,ACT_PERFMD,"
                + "OBJ_NAME,PRE_VALUE,CUR_VALUE,EFF_DATE,USER_ID)  "
                + "values(?,?,?,?,?,?,?)";

        try {
            String index = Oid.next().get(Oid.Type.MAX);
            new JdbcSession(db.get(Db.EXPAD)).sql(SQL)
                    .set(index).set(audit.getActionPerformed())
                    .set(audit.getName())
                    .set(audit.getOldValue())
                    .set(audit.getNewValue())
                    .set(new java.sql.Date(new Date().getTime()))
                    .set(audit.getUser()).insert(Outcome.VOID);
        } catch (Exception ex) {
            LOG.error(":Error logging audit info -->", ex);
        }
    }

    public String audit(String userid, String event, String loginid, String branch, int attempts, String status, String ip) {
        String id;
        String OTQ = "UPDATE LOGINS SET LOGOUT_TM = ?,STATUS = ? WHERE OID = ?";
        String AUT = "INSERT INTO LOGINS(OID,USER_ID,LOGIN_DT,LOGIN_TM,BRANCH_CODE,ATTEMPT_COUNT,STATUS,WORKSTATION) VALUES(?,?,?,?,?,?,?,?)";
        String INS = "UPDATE USERS SET LOGIN_STATUS = 1,LAST_LOG_DT = ? where STAFF_ID = ?";
        String OTS = "UPDATE USERS SET LOGIN_STATUS = 0 where STAFF_ID = ?";
        SimpleDateFormat timer = new SimpleDateFormat("kk:mm:ss");
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.autocommit(false);
            if (event.equalsIgnoreCase("login")) {
                id = Oid.next().get(Oid.Type.MAX);
                session.sql(INS).set(getDateTime(new Date())).set(userid).update(Outcome.VOID);
                session.sql(AUT).set(id).set(userid)
                        .set(getDateTime(new Date()))
                        .set(timer.format(new Date()))
                        .set(branch).set(attempts).set(status)
                        .set(ip).insert(Outcome.VOID);
            } else {
                id = loginid;
                session.sql(OTQ).set(timer.format(new Date()))
                        .set(status).set(id).update(Outcome.VOID);
                session.sql(OTS).set(userid).update(Outcome.VOID);
            }
            session.commit();
        } catch (Exception er) {
            id = "";
            LOG.error("Error notifying LoginStatus ->", er);
        }
        return id;
    }

    public void module(String module, String userid, String branchCode) {
        String id;
        SimpleDateFormat timer = new SimpleDateFormat("kk:mm:ss");
        String Q = "INSERT INTO MODULE_AUDIT(OID,USER_ID,ACCESS_DT,ACCESS_TM,BRANCH_CODE,MODULE) VALUES(?,?,?,?,?,?)";
        try {
            id = Oid.next().get(Oid.Type.MAX);
            new JdbcSession(db.get(Db.EXPAD)).sql(Q)
                    .set(id).set(userid).set(dates.toSQL(new java.util.Date()))
                    .set(timer.format(new Date()))
                    .set(branchCode).set(module).insert(Outcome.VOID);
        } catch (Exception er) {
            LOG.error("Error notifying Module Accessed ->", er);
        }
    }

    public boolean isVisited(String module, String userid) throws Exception {
        String Q = "SELECT OID FROM MODULE_AUDIT WHERE USER_ID = ? AND MODULE = ? AND (ACCESS_DT BETWEEN ? AND ?)";
        return new JdbcSession(db.get(Db.EXPAD)).sql(Q)
                .set(userid).set(module)
                .set(dates.toSQL(new Date()))
                .set(dates.toSQL(new Date()))
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next());
    }

    public List<ModuleAudit> searchModule(String filter) throws Exception {
        String SQL = "SELECT m.OID,m.USER_ID,m.ACCESS_DT,m.ACCESS_TM,m.BRANCH_CODE, f.DESCRIPTION FROM MODULE_AUDIT m,FUNCTIONS f WHERE m.MODULE = f.UUID " + filter + " ORDER BY 3,2,1,5";
        return new JdbcSession(db.get(Db.EXPAD)).sql(SQL).
                select(new ListOutcome<>(this::module));
    }

    public List<AuditElement> searchAudit(String filter) throws Exception {
        String SQL = "SELECT AUDIT_INDEX,ACT_PERFMD,OBJ_NAME,PRE_VALUE,CUR_VALUE,EFF_DT, ACT_DT,USER_ID,BRANCH_CODE,EFF_TM FROM AUDITS " + filter;
        return new JdbcSession(db.get(Db.EXPAD)).sql(SQL)
                .select(new ListOutcome<>(this::audit));
    }

    private AuditElement audit(ResultSet rs) {
        try {
            String id = rs.getString("AUDIT_INDEX");
            String actionPerformed = rs.getString("ACT_PERFMD");
            String name = rs.getString("OBJ_NAME");
            String userid = rs.getString("USER_ID");
            String effectiveDate = this.sdf.format(rs.getDate("EFF_DT"));
            String oldValue = rs.getString("PRE_VALUE");
            String newValue = rs.getString("CUR_VALUE");
            String branchCode = rs.getString("BRANCH_CODE");
            String time = rs.getString("EFF_TM");
            return new AuditElement(id, name, oldValue, newValue, actionPerformed, userid, branchCode, effectiveDate, time);
        } catch (SQLException ex) {
            LOG.error("error reading audits ", ex);
            return null;
        }
    }

    private ModuleAudit module(ResultSet rs) {
        try {
            String id = rs.getString("OID");
            String userid = rs.getString("USER_ID");
            String accessDate = this.sdf.format(rs.getDate("ACCESS_DT"));
            String accessTime = rs.getString("ACCESS_TM");
            String branchCode = rs.getString("BRANCH_CODE");
            String module = rs.getString("DESCRIPTION");
            return new ModuleAudit(id, userid, accessDate, accessTime, branchCode, module);
        } catch (SQLException ex) {
            LOG.error("Error reading module audit ->", ex);
            return null;
        }
    }
}
