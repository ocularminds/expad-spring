package com.ocularminds.expad.app.repository;

import com.ocularminds.expad.app.dao.Db;
import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.ocularminds.expad.model.Branch;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Branches implements Repo {

    Db db;

    @Autowired
    public Branches(final Db db) {
        this.db = db;
    }

    public boolean add(String code, String name) {
        try {
            if (this.exists(code)) {
                return false;
            }
            String q = "INSERT INTO SOL(SOL_ID, SOL_DESC) VALUES(?,?)";
            Object[] params = {code, name};
            this.execute(q, params, db.get(Db.EXPAD));
        } catch (Exception ex) {
            LOG.warn(null, ex);
        }
        return true;
    }

    public void update(String code, String desc) {
        try {
            String q = "UPDATE SOL SET SOL_DESC = ?  WHERE SOL_ID = ?";
            Object[] params = {desc, code};
            this.execute(q, params, db.get(Db.EXPAD));
        } catch (Exception ex) {
            LOG.warn(null, ex);
        }
    }

    public Branch find(String code) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT '00' as ID,SOL_ID as CODE,SOL_DESC as NAME FROM  SOL where SOL_ID = ?")
                .set(code)
                .select((final ResultSet rs, java.sql.Statement s) -> {
                    if (rs.next()) {
                        return read(rs);
                    } else {
                        return null;
                    }
                });
    }

    public List<Branch> findBySearchName(String searchName) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql(new StringBuilder("SELECT '00' as ID,SOL_ID as CODE,SOL_DESC as NAME FROM  SOL ")
                        .append("WHERE lower(SOL_DESC) LIKE %?% ORDER BY NAME ").toString())
                .set(searchName.toLowerCase())
                .select(new ListOutcome<>(this::read));
    }

    public List<Branch> findAll() throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT '00' as ID,SOL_ID as CODE,SOL_DESC as NAME FROM  SOL ORDER BY NAME ")
                .select(new ListOutcome<>(this::read));
    }

    public Map<String, String> mapped() {
        HashMap<String, String> m = new HashMap<>();
        try {
            List<Branch> sols = this.findAll();
            for (int x = 0; x < sols.size(); ++x) {
                Branch b = (Branch) sols.get(x);
                m.put(b.getCode(), b.getName());
            }
        } catch (Exception ex) {
            LOG.warn("error loading mapped bracnhes ", ex);
        }
        return m;
    }

    public boolean exists(String branchCode) throws Exception {
        return find(branchCode) != null;
    }

    public String describe(String code) throws Exception {
        return new JdbcSession(db.get(Db.EXPAD))
                .sql("SELECT SOL_DESC FROM  SOL where SOL_ID = ?").set(code)
                .select((final ResultSet rs, java.sql.Statement s) -> {
                    if (rs.next()) {
                        return rs.getString(1);
                    } else {
                        return "N/A";
                    }
                });
    }

    private Branch read(final ResultSet rs) throws java.sql.SQLException {
        String id = rs.getString("ID");
        String code = rs.getString("CODE");
        String name = rs.getString("NAME");
        return new Branch(id, code, name);
    }

}
