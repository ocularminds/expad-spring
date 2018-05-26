/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.repository;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.Outcome;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public interface Repo { 
    
    final static Logger LOG = LoggerFactory.getLogger(Repo.class);

    default String select(String q, Object[] params, DataSource ds) {
        try {
            JdbcSession session = new JdbcSession(ds);
            session.sql(q);
            for (Object o : params) {
                session.set(o);
            }
            return session.select((final ResultSet rs, java.sql.Statement s) -> {
                if (rs.next()) {
                    return rs.getString(1);
                } else {
                    return "";
                }
            });
        } catch (SQLException ex) {
            LOG.error("could not execute query " + q, ex);
            return "";
        }
    }

    default void execute(String q, Object[] params, DataSource ds) {
        try {
            JdbcSession session = new JdbcSession(ds);
            session.sql(q);
            for (Object o : params) {
                session.set(o);
            }
            if (q.toLowerCase().contains("update")) {
                session.update(Outcome.VOID);
            } else {
                session.insert(Outcome.VOID);
            }
        } catch (SQLException ex) {
            LOG.error("could not execute query " + q, ex);
        }
    }

    default boolean exists(String query, Object[] params, DataSource ds) {
        return !select(query, params, ds).trim().isEmpty();
    }
}
