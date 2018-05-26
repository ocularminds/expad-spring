package com.ocularminds.expad.app.repository;

import com.ocularminds.expad.app.dao.Db;
import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.ListOutcome;
import com.ocularminds.expad.common.Oid;
import com.ocularminds.expad.model.Merchant;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Merchants implements Repo {

    Db db;

    @Autowired
    public Merchants(final Db db) {
        this.db = db;
    }

    public void add(String sname, String shortName, String location) {

        String q = "INSERT INTO MERCHANTS(MERCH_ID,SNAME,SHORT_NAME,LOCATION) VALUES(?,?,?,?)";
        try {
            if (this.exists(shortName)) {
                return;
            }
            String oid = Oid.next().get(Oid.Type.SHORT);
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .autocommit(false)
                    .sql(q)
                    .set(oid)
                    .set(sname)
                    .set(shortName)
                    .set(location);
            session.insert(Outcome.VOID);
            session.commit();
        } catch (Exception ex) {
            LOG.warn(null, ex);
        }
    }

    public void update(String id, String sname, String shortName, String location) {
        String q = "UPDATE MERCHANTS SET SNAME=?,SHORT_NAME=?,LOCATION=? WHERE MERCH_ID=?";
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .autocommit(false).sql(q)
                    .set(sname).set(shortName).set(location).set(id);
            session.update(Outcome.VOID);
            session.commit();
        } catch (SQLException ex) {
            LOG.warn(null, ex);
        }
    }

    public Merchant get(String id) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT MERCH_ID,SNAME, SHORT_NAME, LOCATION FROM MERCHANTS where MERCH_ID = ?")
                .set(id)
                .select((final ResultSet rs, java.sql.Statement s) -> {
                    return rs.next() ? read(rs) : null;
                });
    }

    public boolean exists(String shortName) throws Exception {
        String q = "SELECT MERCH_ID FROM  MERCHANTS where SHORT_NAME = ?";
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(q).set(shortName)
                .select((final ResultSet rs, java.sql.Statement s) -> rs.next());
    }

    public List<Merchant> findAll() throws Exception {
        String q = "SELECT MERCH_ID,SNAME, SHORT_NAME, LOCATION FROM MERCHANTS ";
        return new JdbcSession(db.get(Db.EXPAD)).sql(q)
                .select(new ListOutcome<>(this::read));
    }

    private Merchant read(final ResultSet rs) {
        try {
            String id = rs.getString("MERCH_ID");
            String sname = rs.getString("SNAME");
            String shortName = rs.getString("SHORT_NAME");
            String location = rs.getString("LOCATION");
            return new Merchant(id, sname, shortName, location);
        } catch (SQLException ex) {
            LOG.warn("Error loading merchants", ex);
            return new Merchant();
        }
    }
}
