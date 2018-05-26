package com.ocularminds.expad.app.repository;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.ocularminds.expad.common.Oid;
import com.ocularminds.expad.app.dao.Db;
import com.ocularminds.expad.common.Dates;
import com.ocularminds.expad.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Festus B. Jejelowo
 */
@Component
public class Users implements Repo {

    Db db;

    @Autowired
    public Users(final Db db) {
        this.db = db;
    }

    public void add(User user) {
        try {
            String id = Oid.next().get(null);
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.autocommit(false);
            session.sql(new StringBuilder("INSERT INTO USERS(ID,STAFF_ID,STAFF_NAME,")
                    .append("ROLE,BRANCH,PASSWORD,EXPIRY,CREATE_DATE,TITLE,EMAIL,")
                    .append("LAST_PASS_CHG,READ_ONLY,LAST_CHG_USER,LAST_CHG_DT) VALUES(")
                    .append("?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
                    .toString()
            ).set(id)
                    .set(user.getStaffCode())
                    .set(user.getFullName())
                    .set(user.getRole())
                    .set(user.getBranch())
                    .set(user.getPassword())
                    .set(user.getExpiry())
                    .set(new java.sql.Date(new Date().getTime()))
                    .set(user.getTitle())
                    .set(user.getEMail())
                    .set(new java.sql.Date(new Date().getTime()))
                    .set(user.isReadOnly())
                    .set(user.getStaffCode())
                    .set(new java.sql.Date(new Date().getTime()));
            session.insert(Outcome.VOID);
            session.sql(new StringBuilder("INSERT INTO PASSWORD_HISTORY(")
                    .append("USER_ID,PASSWORD) values('".concat(user.getStaffCode()))
                    .append("','".concat(user.getPassword()).concat("')")).append("")
                    .toString());
            session.commit();
        } catch (Exception e) {
            System.out.println("[EXPAD] Error creating user ->" + e.getMessage());
        }
    }

    public void update(User user) {
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.sql(new StringBuilder("UPDATE USERS SET ROLE = ?,EMAIL = ?,")
                    .append("BRANCH = ?,PASSWORD = ?,EXPIRY = ?,  READ_ONLY = ?,")
                    .append("DISABLED = ?,STAFF_NAME = ?,TITLE = ?,LAST_CHG_USER = ?,")
                    .append("LAST_CHG_DT=? WHERE ID = ?")
                    .toString())
                    .set(user.getRole())
                    .set(user.getEMail())
                    .set(user.getBranch())
                    .set(user.getBranch())
                    .set(user.getExpiry())
                    .set(user.isReadOnly())
                    .set(user.isDisabled())
                    .set(user.getFullName())
                    .set(user.getTitle())
                    .set(new java.sql.Date(new java.util.Date().getTime()))
                    .set(user.getStaffCode())
                    .set(user.getId());
            session.update(Outcome.VOID);
            session.sql(new StringBuilder("INSERT INTO PASSWORD_HISTORY(")
                    .append("USER_ID,PASSWORD) values('".concat(user.getId()))
                    .append("','".concat(user.getPassword()))
                    .append("')").toString());
            session.insert(Outcome.VOID);
        } catch (SQLException e) {
            LOG.error("Error updating user ", e);
        }
    }

    public void notifyFirstLogin(String userid) {
        String q = "UPDATE USERS SET FIRST_LOG = 'N' WHERE STAFF_ID = ?";
        try {
            Object[] params = {"N", userid};
            this.execute(q, params, db.get(Db.EXPAD));
        } catch (Exception ex) {
            LOG.error("Error updating user login ", ex);
        }
    }

    public void activate(String id) throws Exception {
        String q = "UPDATE USERS SET DISABLED = ? WHERE ID = ?";
        Object[] params = {"N", id};
        this.execute(q, params, db.get(Db.EXPAD));
    }

    public void deactivate(String id, String userid) {
        String q = "UPDATE USERS SET DISABLED = ?,LAST_CHG_USER=? ,LAST_CHG_DT=? WHERE ID = ?";
        try {
            Object[] params = {"Y", userid, new java.sql.Date(new java.util.Date().getTime()), id};
            this.execute(q, params, db.get(Db.EXPAD));
        } catch (Exception ex) {
            LOG.error("Error deactivating user ", ex);
        }
    }

    public void deactivate(String userid) {
        String q = "UPDATE USERS SET DISABLED = ?,LAST_CHG_USER=?, LAST_CHG_DT=? WHERE STAFF_ID = ?";
        try {
            Object[] params = {"Y", userid, new java.sql.Date(new java.util.Date().getTime()), userid};
            this.execute(q, params, db.get(Db.EXPAD));
        } catch (Exception ex) {
            LOG.error("Error deactivating user ", ex);
        }
    }

    public void reset(String userid) {
        try {
            String Q = "UPDATE USERS SET LOGIN_STATUS = 0 where STAFF_ID = ?";
            new JdbcSession(db.get(Db.EXPAD)).sql(Q).set(userid).update(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.error("Error resetting using login ", ex);
        }
    }

    public void remove(String id) {
        try {
            String Q = "DELETE FROM USERS WHERE ID = ?";
            new JdbcSession(db.get(Db.EXPAD)).sql(Q).set(id).update(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.error("Error deleting user ", ex);
        }
    }

    public void password(String userid, String password) {
        String q = "UPDATE USERS SET PASSWORD = ?, LAST_PASS_CHG = ? WHERE STAFF_ID =?";
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.autocommit(false);
            session.sql(q).set(password)
                    .set(new java.sql.Date(new java.util.Date().getTime()))
                    .set(userid).update(Outcome.VOID);
            session.sql("insert into password_history(user_id,password) values(?,?)")
                    .set(userid)
                    .set(password)
                    .update(Outcome.VOID);
            session.commit();
        } catch (SQLException ex) {
            LOG.error("Error deactivating user ", ex);
        }
    }

    public User get(String id) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT * from users WHERE ID  = ?").set(id)
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next() ? read(rs) : null);
    }

    public User of(String userid) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT * from users WHERE STAFF_ID  = ?").set(userid)
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next() ? read(rs) : null);
    }

    public boolean exists(String userid) throws Exception {
        String q = "SELECT STAFF_ID from users WHERE STAFF_ID  = ?";
        Object[] params = {userid};
        return this.exists(q, params, db.get(Db.EXPAD));
    }

    public boolean hasPassword(String userid, String password) throws Exception {
        String Q = "SELECT PASSWORD FROM  PASSWORD_HISTORY WHERE USER_ID = ? AND PASSWORD = ?";
        Object[] params = {userid, password};
        return this.exists(Q, params, db.get(Db.EXPAD));
    }

    public List<User> iterate() {
        try {
            return new JdbcSession(db.get(Db.EXPAD))
                    .sql("select * from users")
                    .select(new ListOutcome<>(this::read));
        } catch (SQLException ex) {
            return new ArrayList();
        }
    }

    private User read(final ResultSet rs) {
        User user;
        try {
            String id = rs.getString("ID");
            String staffCode = rs.getString("STAFF_ID");
            String title = rs.getString("TITLE");
            String password = rs.getString("PASSWORD");
            String fullName = rs.getString("STAFF_NAME");
            String branch = rs.getString("BRANCH");
            String role = rs.getString("ROLE");
            String dateCreated = new Dates(rs.getDate("CREATE_DATE")).format();
            int expiry = rs.getInt("EXPIRY");
            String firstLog = rs.getString("FIRST_LOG");
            int statusLog = rs.getInt("LOGIN_STATUS");
            String lastPasswordChange = new Dates(rs.getDate("LAST_PASS_CHG")).format();
            String strRead = rs.getString("READ_ONLY");
            String strDisabled = rs.getString("DISABLED");
            String strSuper = rs.getString("SUSR");
            boolean readOnly = strRead != null && strRead.equalsIgnoreCase("Y");
            boolean disabled = strDisabled != null && strDisabled.equalsIgnoreCase("Y");
            boolean superUser = strSuper != null && strSuper.equalsIgnoreCase("Y");
            user = new User(staffCode, role, fullName, "");
            user.setTitle(title);
            user.setPassword(password);
            user.setBranch(branch);
            user.setDateCreated(dateCreated);
            user.setExpiry(expiry);
            user.setFirstLog(firstLog);
            user.setLastPasswordChange(lastPasswordChange);
            user.setReadOnly(readOnly);
            user.setDisabled(disabled);
            user.setSuperUser(superUser);
            user.setId(id);

        } catch (SQLException ex) {
            user = new User();
            LOG.warn("Error loading users", ex);
        }
        return user;
    }
}
