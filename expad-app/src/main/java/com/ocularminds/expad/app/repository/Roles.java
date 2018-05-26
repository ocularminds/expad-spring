package com.ocularminds.expad.app.repository;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.jcabi.jdbc.Outcome;
import com.ocularminds.expad.common.Oid;
import com.ocularminds.expad.app.dao.Db;
import com.ocularminds.expad.model.Function;
import com.ocularminds.expad.model.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Festus B. Jejelowo
 */
@Component
public class Roles implements Repo {

    Db db;

    @Autowired
    public Roles(final Db db) {
        this.db = db;
    }

    public Role get(String id) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT ID,ROLE_NAME,DESCRIPTION,DISABLED FROM ROLES WHERE ID  = ?").set(id)
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next() ? read(rs) : null);
    }

    public void add(Role role) {
        String sql = "INSERT INTO ROLES(ID,ROLE_NAME,DESCRIPTION,DISABLED) VALUES(?,?,?,?)";
        try {
            String oid = Oid.next().get(Oid.Type.MAX);
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD)).autocommit(false);
            session.sql(sql).set(oid)
                    .set(role.getName())
                    .set(role.getDescription())
                    .set("N");
            session.insert(Outcome.VOID);
            if (role.getFunctions() != null) {
                for (String f : role.getFunctions()) {
                    session.sql("INSERT INTO RoleFunction(ROLE_ID,FUNC_UUID) VALUES(?,?)")
                            .set(oid).set(f).insert(Outcome.VOID);
                }
            }
            session.commit();
        } catch (Exception e) {
            LOG.error("Error creating role ->", e);
        }
    }

    public void update(Role role) {
        String sql = "UPDATE ROLES SET ROLE_NAME = ?,DESCRIPTION = ? WHERE ID = ?";
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.sql(sql).set(role.getName())
                    .set(role.getDescription())
                    .set(role.getId())
                    .update(Outcome.VOID);
            if (role.getFunctions() != null) {
                session
                        .sql("DELETE RoleFunction WHERE ROLE_ID = ?")
                        .set(role.getId())
                        .update(Outcome.VOID);
                for (String f : role.getFunctions()) {
                    session.sql("INSERT INTO RoleFunction(ROLE_ID,FUNC_UUID) VALUES(?,?)")
                            .set(role.getId())
                            .set(f)
                            .insert(Outcome.VOID);
                }
            }

        } catch (SQLException e) {
            LOG.error("Error updating role ->", e);
        }
    }

    public void delete(String id) {
        String Q1 = "DELETE FROM ROLES  WHERE ID = ?";
        String Q2 = "DELETE FROM USERS  WHERE ROLE = ?";
        String Q3 = "DELETE FROM RoleFunction WHERE ROLE_ID = ?";
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.autocommit(false);
            session.sql(Q1).set(id).update(Outcome.VOID);
            session.sql(Q2).set(id).update(Outcome.VOID);
            session.sql(Q2).set(id).update(Outcome.VOID);
            session.commit();
        } catch (SQLException e) {
            LOG.info("Error delete role ->" + e.getMessage());
        }
    }

    public void addFunction(String rid, String[] functions) {
        String Q = "INSERT INTO RoleFunction(ROLE_ID,FUNC_UUID) VALUES(?,?)";
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.autocommit(false);
            session.insert(Outcome.VOID);
            for (String function : functions) {
                session.sql(Q).set(rid).set(function);
                session.insert(Outcome.VOID);
            }
            session.commit();
        } catch (SQLException e) {
            LOG.error("Error adding  role function->", e);
        }
    }

    public void deleteFunction(String rid, String fid) {
        String Q = "DELETE FROM RoleFunction WHERE ROLE_ID = ?  AND FUNC_UUID = ?";
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.sql(Q).set(rid).set(fid).update(Outcome.VOID);
        } catch (SQLException e) {
            LOG.error("Error deleting role function ->", e);
        }
    }

    public void deleteFunction(String rid, String[] fid) {
        String Q = "DELETE FROM RoleFunction WHERE ROLE_ID = ?  AND FUNC_UUID = ?";
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD));
            session.autocommit(false);
            for (String fid1 : fid) {
                session.sql(Q).set(rid).set(fid1).update(Outcome.VOID);
            }
            session.commit();

        } catch (SQLException e) {
            LOG.error("Error deleting role functions ->", e);
        }
    }

    public void activate(String id) {
        String query = "UPDATE ROLES SET DISABLED = ?, WHERE ID = ?";
        try {
            new JdbcSession(db.get(Db.EXPAD))
                    .sql(query)
                    .set("N").set(id)
                    .update(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.error("Error activating role ->", ex);
        }
    }

    public void deActivate(String id) {
        String query = "UPDATE ROLES SET DISABLED = 'Y' WHERE ID = ?";
        try {
            new JdbcSession(db.get(Db.EXPAD))
                    .sql(query).set("Y")
                    .set(id)
                    .update(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.error("Error deactivating role ->", ex);
        }
    }

    public List<Role> iterate() throws Exception {
        String q = "SELECT ID,ROLE_NAME,DESCRIPTION,DISABLED FROM  ROLES ";
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(q)
                .select(new ListOutcome<>(this::read));
    }

    private Role read(final ResultSet rs) {
        Role role;
        try {
            String id = rs.getString("ID");
            String name = rs.getString("ROLE_NAME");
            String description = rs.getString("DESCRIPTION");
            String strDisabled = rs.getString("DISABLED");
            boolean isDisabled = strDisabled != null && strDisabled.equalsIgnoreCase("Y");
            role = new Role(id, name, description, isDisabled);
        } catch (SQLException ex) {
            role = new Role();
            LOG.warn("Error loading roles", ex);
        }
        return role;
    }

}
