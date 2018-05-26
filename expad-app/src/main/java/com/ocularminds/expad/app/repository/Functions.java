/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.repository;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.ocularminds.expad.app.dao.Db;
import com.ocularminds.expad.model.Function;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jejelowo B. Festus <festus.jejelowo@ocularminds.com>
 */
@Component
public class Functions implements Repo {

    Db db;

    @Autowired
    public Functions(final Db db) {
        this.db = db;
    }

    public String getParentPage(String url) {
        try {
            return new JdbcSession(db.get(Db.EXPAD))
                    .sql("SELECT PARENT FROM PAGES WHERE  PAGE = ?")
                    .set(url).select((ResultSet rs, Statement s) -> {
                if (rs.next()) {
                    return rs.getString("PARENT");
                } else {
                    return null;
                }
            });
        } catch (SQLException ex) {
            LOG.error(null, ex);
            return null;
        }
    }

    public List<Function> iterate() {
        String Q = "SELECT UUID,'000' as CODE, URL,DESCRIPTION,MENU_TYPE  FROM FUNCTIONS ";
        try {
            return new JdbcSession(db.get(Db.EXPAD)).sql(Q)
                    .select(new ListOutcome<>(this::read));
        } catch (SQLException er) {
            LOG.error("[EXPAD] Error connecting to DB ", er);
            return new ArrayList<>();
        }
    }

    public List<Function> of(String rid) {
        String SQL = new StringBuilder()
                .append("SELECT A.UUID,'000' AS CODE,A.URL,A.DESCRIPTION,A.MENU_TYPE ")
                .append("FROM RoleFunction B,FUNCTIONS A ")
                .append("WHERE B.FUNC_UUID = A.UUID  AND B.ROLE_ID = ?").toString();
        try {
            return new JdbcSession(db.get(Db.EXPAD)).sql(SQL).set(rid)
                    .select(new ListOutcome<>(this::read));
        } catch (SQLException ex) {
            String message = "Can not select user's available\n functions due to the following:\n";
            LOG.error(message, ex);
            return new ArrayList<>();
        }
    }

    public List<Function> notFor(String rid) {
        try {
            return new JdbcSession(db.get(Db.EXPAD)).sql(
                    new StringBuilder("SELECT UUID,'000' as CODE,URL,DESCRIPTION,")
                            .append("MENU_TYPE  FROM FUNCTIONS  WHERE UUID NOT IN")
                            .append("(SELECT FUNC_UUID FROM  ROLEFUNCTION  ")
                            .append("WHERE ROLE_ID=?)")
                            .toString()).set(rid)
                    .select(new ListOutcome<>(this::read));
        } catch (SQLException ex) {
            String message = "Can not select user's available\n functions due to the following:\n";
            LOG.error(message, ex);
            return new ArrayList<>();
        }
    }

    private Function read(ResultSet rs) {
        try {
            String id = rs.getString(1);
            String code = rs.getString(2);
            String url = rs.getString(3);
            String description = rs.getString(4);
            String type = rs.getString(5);
            return new Function(id, code, url, description, type);
        } catch (SQLException ex) {
            LOG.error("error reading function table ->", ex);
            return null;
        }
    }
}
