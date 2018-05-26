package com.ocularminds.expad.app.repository;

import com.ocularminds.expad.app.dao.Db;
import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import com.jcabi.jdbc.ListOutcome;
import com.ocularminds.expad.common.Oid;
import com.ocularminds.expad.vao.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Products implements Repo {

    Db db;

    @Autowired
    public Products(final Db db) {
        this.db = db;
    }

    public void add(Product product) {

        StringBuilder sb = new StringBuilder("INSERT INTO PRODUCT");
        sb.append("(OID,BIN,CODE,PROD_NAME,INACTIVE_CODE,CRNY_CODE,TRAIL_TEXT,FORMAT) ");
        sb.append("VALUES(?,?,?,?,?,?,?,?)");
        try {
            String oid = Oid.next().get(Oid.Type.LONG);
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .sql(sb.toString()).set(oid).set(product.getBin())
                    .set(product.getCode())
                    .set(product.getName())
                    .set(product.getInActiveCardCode())
                    .set(product.getCurrencyCode())
                    .set(product.getTrailingText())
                    .set(product.getFormat());
            session.insert(Outcome.VOID);
        } catch (Exception ex) {
            LOG.warn(null, ex);
        }
    }

    public void update(Product product) {
        String SQL = "UPDATE PRODUCT SET BIN = ?,CODE = ?,PROD_NAME = ?,"+
                "INACTIVE_CODE = ?,CRNY_CODE = ?, TRAIL_TEXT = ?,FORMAT = ?  "+
                "where OID = ?";
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .autocommit(false)
                    .sql(SQL)
                    .set(product.getBin())
                    .set(product.getCode())
                    .set(product.getName())
                    .set(product.getInActiveCardCode())
                    .set(product.getCurrencyCode())
                    .set(product.getTrailingText())
                    .set(product.getFormat())
                    .set(product.getId());
            session.update(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.warn(null, ex);
        }
    }

    public void delete(String oid) {
        try {
            JdbcSession session = new JdbcSession(db.get(Db.EXPAD))
                    .sql("delete from product where oid = ?").set(oid);
            session.update(Outcome.VOID);
        } catch (SQLException ex) {
            LOG.warn(null, ex);
        }
    }

    public Map<String, Product> mapped() throws Exception {
        List<Product> records = this.findAll();
        HashMap<String, Product> p = new HashMap<>();
        for (int x = 0; x < records.size(); ++x) {
            Product pp = records.get(x);
            p.put(pp.getId(), pp);
        }
        return p;
    }

    public Product find(String code) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT OID,BIN,CODE,PROD_NAME,INACTIVE_CODE,CRNY_CODE,TRAIL_TEXT,FORMAT FROM PRODUCT WHERE CODE = ? ORDER BY 3")
                .set(code)
                .select((final ResultSet rs, java.sql.Statement s) -> {
                    rs.next();
                    return read(rs);
        });
    }

    public Product get(String id) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT OID,BIN,CODE,PROD_NAME,INACTIVE_CODE,CRNY_CODE,TRAIL_TEXT,FORMAT FROM PRODUCT WHERE OID = ? ORDER BY 3")
                .set(id)
                .select((final ResultSet rs, java.sql.Statement s) -> {
                    rs.next();
                    return read(rs);
        });
    }

    public List<Product> findAll() throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT OID,BIN,CODE,PROD_NAME,INACTIVE_CODE,CRNY_CODE,TRAIL_TEXT,FORMAT FROM PRODUCT ORDER BY 3")
                .select(new ListOutcome<>(this::read));
    }

    private Product read(java.sql.ResultSet rs) throws java.sql.SQLException {
        String id = rs.getString("OID");
        String bin = rs.getString("BIN");
        String code = rs.getString("CODE");
        String name = rs.getString("PROD_NAME");
        String inActiveCardCode = rs.getString("INACTIVE_CODE");
        String currencyCode = rs.getString("CRNY_CODE");
        String trailingTex = rs.getString("TRAIL_TEXT");
        int format = rs.getInt("FORMAT");
        return new Product(id, bin, code, name, inActiveCardCode, currencyCode, trailingTex, format);
    }
}
