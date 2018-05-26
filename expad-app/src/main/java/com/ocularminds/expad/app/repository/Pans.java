package com.ocularminds.expad.app.repository;

import com.ocularminds.expad.app.dao.Db;
import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.ListOutcome;
import com.ocularminds.expad.common.Oid;
import com.ocularminds.expad.vao.Pan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Pans implements Repo {

    Db db;

    @Autowired
    public Pans(final Db db) {
        this.db = db;
    }

    public void add(Pan pan) {
        add(pan.getNo(), pan.getExpiry(), pan.getOffset(), pan.getBranchCode(), pan.getProductCode());
    }

    public void add(String pan, String exp, String offset, String branch, String prod) {
        String q = "INSERT INTO PAN(OID,NO,EXPIRY_DT,OFFSET,STATUS,BRANCH_CODE,PRODUCT_CODE) VALUES(?,?,?,?,?,?,?)";
        try {
            String oid = Oid.next().get(Oid.Type.MAX);
            Object[] params = {oid, pan, exp, offset, "O", branch, prod};
            this.execute(q, params, db.get(Db.EXPAD));
            String Q = "INSERT INTO PAN_INFO(PAN,EXPIRY_DT,BRANCH_CODE) VALUES(?,?,?)";
            Object[] params2 = {pan, exp, branch};
            this.execute(Q, params2, db.get(Db.EXPAD));
        } catch (Exception ex) {
            LOG.warn(null, ex);
        }
    }

    public void notifyProcessedPan(String pan) {
        try {
            new JdbcSession(db.get(Db.EXPAD))
                    .sql("UPDATE PAN SET STATUS ='D' WHERE NO = ?")
                    .set(pan)
                    .update(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.warn(null, ex);
        }
    }

    public boolean exists(String pan) throws Exception {
        return find(pan) != null;
    }

    public Pan find(String pan) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT OID,NO,EXPIRY_DT,OFFSET,STATUS,BRANCH_CODE,PRODUCT_CODE FROM PAN where NO = ?")
                .set(pan)
                .select((final ResultSet rs, java.sql.Statement s) -> {
                    if (rs.next()) {
                        return read(rs);
                    } else {
                        return null;
                    }
                });
    }

    public List<Pan> query(String filter) throws Exception {
        String q = "SELECT OID,NO,EXPIRY_DT,OFFSET,STATUS,BRANCH_CODE,PRODUCT_CODE FROM PAN " + filter;
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(q)
                .select(new ListOutcome<>(
                        (final ResultSet rs) -> read(rs))
                );
    }

    private Pan read(final ResultSet rs) {
        Pan pan = new Pan();
        try {
            String id = rs.getString("OID");
            String no = rs.getString("NO");
            String expiry = rs.getString("EXPIRY_DT");
            String offset = rs.getString("OFFSET");
            String status = rs.getString("STATUS");
            String branchCode = rs.getString("BRANCH_CODE");
            String productCode = rs.getString("PRODUCT_CODE");
            pan = new Pan(id, no, expiry, offset, status, branchCode, productCode);
        } catch (SQLException ex) {
            LOG.warn("Error loading transactions", ex);
        }
        return pan;
    }
}
