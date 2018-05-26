package com.ocularminds.expad.app.repository;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import com.ocularminds.expad.app.dao.Db;
import com.ocularminds.expad.model.Account;
import com.ocularminds.expad.model.CreditCardCustomer;
import com.ocularminds.expad.model.Customer;
import com.ocularminds.expad.model.Script;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class Customers {

    private final Configurator config;
    private static final Logger LOG = LoggerFactory.getLogger(Customers.class);
    Db db;

    @Autowired
    public Customers(final Db db, final Configurator configurator) {
        this.db = db;
        this.config = configurator;
    }

    public Account accountWithId(String id) {
        Account account;
        String filter = "";
        try {
            LOG.info("this.config.script().getDb().trim() ->"+this.config.script().getDb().trim());
            if (this.config.script().getDb().trim().equalsIgnoreCase("finacle")) {
                filter = " WHERE FORACID = '" + id + "'";
            } else {
                filter = " WHERE COD_ACCT_NO = '" + id + "'";
            }
        } catch (Exception ex) {
            LOG.error("error fetching account details from core banking ", ex);
        }
        LOG.info("filter - "+filter);
        List<Account> accounts = this.search("ACCT", filter);
        account = accounts.size() > 0 ? (Account) accounts.get(0) : null;
        return account;
    }

    public List<Account> accounts(String custid) {
        return this.search("CUST", custid);
    }

    public List<Account> search(String type, String filter) {
        String q;
        try {
            q = this.config.script().getAccount();
            JdbcSession session = new JdbcSession(db.get(Db.BANKS));
            if (type != null && type.equalsIgnoreCase("ACCT")) {
                q = q.substring(0, q.indexOf("WHERE")) + " " + filter;
                session = session.sql(q);
            } else if (this.config.script().getDb().trim().equalsIgnoreCase("finacle")) {
                session = session.sql(q).set(filter);
            } else {
                q = q + filter;
                session = session.sql(q);
            }
            return session.select(new ListOutcome<>(this::account));
        } catch (Exception e) {
            LOG.error("Error Finding accounts By CustId:", e);
            return new ArrayList<>();
        }
    }

    public Customer get(String customerid) {
        try {
            Script script = this.config.script();
            String Q = script.getCustomer();
            JdbcSession session = new JdbcSession(db.get(Db.BANKS));
            if (script.getDb().trim().equalsIgnoreCase("oracle")) {
                session = session.sql(Q).set(customerid);
            } else {
                Q = Q.concat("'").concat(customerid).concat("'");
                session = session.sql(Q);
            }
            return session.select((ResultSet rs, Statement s) -> {
                if (rs.next()) {
                    return customer(rs);
                } else {
                    return null;
                }
            });
        } catch (Exception e) {
            LOG.info("Error fetching customer by id:", e);
            return null;
        }
    }

    public Customer ofAccountId(String foracid) throws Exception {
        String bankName = this.config.get().getName();
        try {
            Script script = this.config.script();
            String Q = script.getCustomer();
            JdbcSession session = new JdbcSession(db.get(Db.BANKS));
            if (script.getDb().trim().equalsIgnoreCase("finacle")) {
                LOG.info(Q);
                session = session.sql(Q).set(foracid);
                if (bankName.toLowerCase().lastIndexOf("inland") > -1) {
                    session = session.set(foracid);
                }
            } else {
                Q = Q.concat("'").concat(foracid).concat("'");
                LOG.info(Q);
                session = session.sql(Q);
            }
            return session.select((rs, s) -> customer(rs));
        } catch (Exception e) {
            LOG.error("error fetching customer by foracid:", e);
            return null;
        }
    }

    public CreditCardCustomer creditCardCustomerWithAccountId(String foracid) {
        String Q = new StringBuilder()
                .append("SELECT C.CUST_ID,C.CUST_NAME,C.PRIMARY_SOL_ID,")
                .append("C.NUM_OF_ACCOUNTS,C.CREATE_DT,C.RESIDENCE,C.ADDRESS,")
                .append("C.CITY,C.STATE,C.PHONE,C.EMAIL,C.INDUSTRY,")
                .append("(getDate()-DATE_OF_BIRTH) as DOB  ")
                .append("FROM CMG C,GAM G WHERE C.CUST_ID = G.CUST_ID ")
                .append("AND G.FORACID = ?").toString();
        try {
            return new JdbcSession(db.get(Db.BANKS)).sql(Q).set(foracid)
                    .select((ResultSet rs, Statement s) -> {
                        String id = rs.getString("CUST_ID");
                        String name = rs.getString("Cust_Name");
                        String sol = rs.getString("Primary_Sol_Id");
                        int totalAccount = rs.getInt("Num_Of_Accounts");
                        String address = rs.getString("ADDRESS");
                        String city = rs.getString("CITY");
                        String residence = rs.getString("RESIDENCE");
                        String phone = rs.getString("PHONE");
                        String email = rs.getString("EMAIL");
                        String age = rs.getString("DOB");
                        String industry = rs.getString("INDUSTRY");
                        return new CreditCardCustomer(id, name, totalAccount, sol, address, city, residence, phone, email, age, industry);
                    });
        } catch (SQLException e) {
            LOG.error("Error fetching CardCustomer by foracid:", e);
            return null;
        }
    }

    private Customer customer(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return null;
        }
        String id = rs.getString("CUSTOMER_NO");
        String name = rs.getString("CUSTOMER_NAME");
        String sol = rs.getString("BRANCH_CODE");
        int totalAccount = rs.getInt("NO_OF_ACCTS");
        java.sql.Date dob = rs.getDate("DOB");
        return new Customer(id, name, totalAccount, sol, dob);
    }

    private Account account(ResultSet rs) throws SQLException {
        String customerId = rs.getString("CUSTOMER_NO");
        String accountno = rs.getString("ACCT_NO");
        String flag = rs.getString("ACCT_CLS_FLG");
        String name = rs.getString("ACCT_NAME");
        String empid = rs.getString("EMP_ID");
        String code = rs.getString("ACCT_TYPE");
        String branch = rs.getString("BRANCH_CODE");
        double balance = rs.getDouble("CLR_BAL_AMT");
        return new Account(accountno, name, branch, flag, code, customerId, empid, balance);
    }

    private String filter(String id) throws Exception {
        if (this.config.get().getName().toLowerCase().lastIndexOf("inland") > -1) {
            return new StringBuffer()
                    .append("UNION SELECT G.CUST_ID AS CUSTOMER_NO,")
                    .append("O.ACCT_NUM AS ACCT_NO,G.ACCT_CLS_FLG,G.ACCT_NAME,")
                    .append("G.EMP_ID,G.SCHM_CODE AS ACCT_TYPE,G.SOL_ID AS BRANCH_CODE,")
                    .append("G.CLR_BAL_AMT FROM GAM G,OLDACT O ")
                    .append("WHERE G.FORACID = O.ACCT_NUM AND ")
                    .append("O.OLD_ACCT_NUM = '".concat(id).concat("'")).toString();
        } else {
            return "";
        }
    }
}
