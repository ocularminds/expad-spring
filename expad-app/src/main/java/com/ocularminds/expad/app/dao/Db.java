package com.ocularminds.expad.app.dao;

import java.util.HashMap;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Db {

    public static final String BANKS = "bank";
    public static final String EXPAD = "expad";
    HashMap<String, DataSource> datasources = new HashMap<>();

    DataSource datasource;
    final static Logger LOG = LoggerFactory.getLogger(Db.class);

    public Db(final DataSource datasource) {
        this.datasource = datasource;
        datasources.put(Db.EXPAD, datasource);
    }

    public Db(final DataSource expad, final DataSource banking) {
        this.datasource = expad;
        datasources.put(Db.EXPAD, datasource);
        datasources.put(Db.BANKS, banking);
    }

    public DataSource get(String jndi) {
        return datasources.get(jndi);
    }

    public DataSource get() {
        return datasource;
    }
}
