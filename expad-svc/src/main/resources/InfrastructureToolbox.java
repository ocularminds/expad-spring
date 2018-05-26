/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.svc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class InfrastructureToolbox {

    public static final String CONNECTION_NAME = "postilion_postcard";
    public static Hashtable online_issuer_loads = new Hashtable();

    public static CallableStatement getCallToStoreCardRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_cards_update", 42);
    }

    public static CallableStatement getCallToStoreCardRecord42(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_cards_update", 35);
    }

    public static CallableStatement getCallToStoreCustomerRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_customers_update", 42);
    }

    public static CallableStatement getCallToRemoveCardRecord(Connection cn) throws SQLException {
        return cn.prepareCall("{call pcb_cards_delete (?,?,?,?)}");
    }

    public static CallableStatement getCallToStoreCardAccountRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_card_accounts_update", 9);
    }

    public static CallableStatement getCallToRemoveCardAccountRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_card_accounts_delete", 6);
    }

    public static CallableStatement getCallToStoreStatementRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_statements_update", 9);
    }

    public static CallableStatement getCallToStoreStatementRecord42(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_statements_update", 14);
    }

    public static CallableStatement getCallToRemoveStatementRecord(Connection cn) throws SQLException {
        return cn.prepareCall("{call pcb_statements_delete (?,?,?,?)}");
    }

    public static CallableStatement getCallToAdjustAccountBalanceRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_adjust_accountbalances", 8);
    }

    public static CallableStatement getCallToStoreAccountRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_accounts_update", 7);
    }

    public static CallableStatement getCallToRemoveAccountRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_accounts_delete", 3);
    }

    public static CallableStatement getCallToStoreAccountBalanceRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_account_balances_update", 7);
    }

    public static CallableStatement getCallToRemoveAccountBalanceRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_account_balances_delete", 3);
    }

    public static CallableStatement getCallToStoreEmvScriptRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_insert_emv_script_command", 11);
    }

    public static CallableStatement getCallToStoreAccountOverrideLimitRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_account_override_limits_update", 39);
    }

    public static CallableStatement getCallToRemoveAccountOverrideLimitRecord(Connection cn) throws SQLException {
        return cn.prepareCall("{call pcb_account_override_limits_delete (?,?,?)}");
    }

    public static CallableStatement getCallToStoreCardOverrideLimitRecord(Connection cn) throws SQLException {
        return InfrastructureToolbox.prepareStoredProc(cn, "pcb_card_override_limits_update", 39);
    }

    public static CallableStatement getCallToRemoveCardOverrideLimitRecord(Connection cn) throws SQLException {
        return cn.prepareCall("{call pcb_card_override_limits_delete (?,?,?)}");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getIssuerName(int issuer_number) {
        String sqlquery = "SELECT issuer_name FROM pc_issuers WHERE (issuer_nr = ?)";
        Connection cn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String issuer_name = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareStatement(sqlquery);
            stmt.setInt(1, issuer_number);
            if (!stmt.execute()) {
                String s;
                String string = s = null;
                return string;
            }
            rs = stmt.getResultSet();
            while (rs.next()) {
                issuer_name = rs.getString(1);
            }
            InfrastructureToolbox.commitAndClose(cn, stmt, rs);
        } catch (SQLException sqle) {
            String s1;
            String string = s1 = null;
            return string;
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt, rs);
        }
        return issuer_name;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void swapFullAccountsLoadTableGroupStart(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_swap_fullaccountsload_tablegroup_start (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static String getInactiveCardsTablename(int issuer_nr) throws SQLException {
        char current_tableset = InfrastructureToolbox.getTablesetIndicator(issuer_nr, TableSetIndicator.CARDS_TABLE_SET);
        if (current_tableset == '\u0000') {
            return null;
        }
        if (current_tableset == 'A') {
            return "pc_cards_" + String.valueOf(issuer_nr) + "_B";
        }
        if (current_tableset == 'B') {
            return "pc_cards_" + String.valueOf(issuer_nr) + "_A";
        }
        return null;
    }

    public static String getInactiveAccountsTablename(int issuer_nr) throws SQLException {
        char current_tableset = InfrastructureToolbox.getTablesetIndicator(issuer_nr, TableSetIndicator.ACCOUNTS_TABLE_SET);
        if (current_tableset == '\u0000') {
            return null;
        }
        if (current_tableset == 'A') {
            return "pc_accounts_" + String.valueOf(issuer_nr) + "_B";
        }
        if (current_tableset == 'B') {
            return "pc_accounts_" + String.valueOf(issuer_nr) + "_A";
        }
        return null;
    }

    public static String getInactiveAccountBalancesTablename(int issuer_nr) throws SQLException {
        char current_tableset = InfrastructureToolbox.getTablesetIndicator(issuer_nr, TableSetIndicator.ACCOUNTBALANCES_TABLE_SET);
        if (current_tableset == '\u0000') {
            return null;
        }
        if (current_tableset == 'A') {
            return "pc_account_balances_" + String.valueOf(issuer_nr) + "_B";
        }
        if (current_tableset == 'B') {
            return "pc_account_balances_" + String.valueOf(issuer_nr) + "_A";
        }
        return null;
    }

    public static String getInactiveCardAccountsTablename(int issuer_nr) throws SQLException {
        char current_tableset = InfrastructureToolbox.getTablesetIndicator(issuer_nr, TableSetIndicator.CARDACCOUNTS_TABLE_SET);
        if (current_tableset == '\u0000') {
            return null;
        }
        if (current_tableset == 'A') {
            return "pc_card_accounts_" + String.valueOf(issuer_nr) + "_B";
        }
        if (current_tableset == 'B') {
            return "pc_card_accounts_" + String.valueOf(issuer_nr) + "_A";
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createStatementsTableConstraintsExceptIndexesForAll(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_statementstable_constraints_except_indexes_for_all (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void blockCard(String pan, String expirydt, String messageReason) throws SQLException {
        Connection cn = null;
        PreparedStatement stmt = null;
        String query = "UPDATE pc_cards_1_A SET hold_rsp_code = ?,last_updated_date =getdate(),\tlast_updated_user=? WHERE pan = ? and expiry_date = ?";
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareStatement(query);
            stmt.setString(1, messageReason);
            stmt.setString(2, "EXPAD");
            stmt.setString(3, pan);
            stmt.setString(4, expirydt);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IssuerInformation getIssuerInfo(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        IssuerInformation iei = new IssuerInformation();
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = InfrastructureToolbox.prepareStoredProc(cn, "pcb_get_issuer_info", 12);
            stmt.setInt(1, issuer_nr);
            stmt.registerOutParameter(2, 4);
            stmt.registerOutParameter(3, 4);
            stmt.registerOutParameter(4, 4);
            stmt.registerOutParameter(5, 4);
            stmt.registerOutParameter(6, 4);
            stmt.registerOutParameter(7, 4);
            stmt.registerOutParameter(8, 4);
            stmt.registerOutParameter(9, 4);
            stmt.registerOutParameter(10, 4);
            stmt.registerOutParameter(11, 4);
            stmt.registerOutParameter(12, 4);
            stmt.execute();
            iei.high_extract_nr = stmt.getInt(2);
            iei.low_extract_nr = stmt.getInt(3);
            iei.current_velocity_nr = stmt.getInt(4);
            iei.current_monthly_velocity_nr = stmt.getInt(8);
            iei.current_weekly_velocity_nr = stmt.getInt(7);
            iei.current_auth_mode = stmt.getInt(5);
            InfrastructureToolbox.commitAndClose(cn, stmt, rs);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt, rs);
        }
        return iei;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CardInfo getCardInfo(String pan, String expiryDate) throws SQLException {
        Connection cn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CardInfo iei = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareStatement("SELECT pan, expiry_date, card_program from pc_cards_1_A where pan=? and expiry_date=?");
            stmt.setString(1, pan);
            stmt.setString(2, expiryDate);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String pan1 = rs.getString("pan");
                String expiryDate1 = rs.getString("expiry_date");
                String name = rs.getString("card_program");
                iei = new CardInfo(pan1, expiryDate1, name);
            }
            InfrastructureToolbox.commitAndClose(cn, stmt, rs);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt, rs);
        }
        return iei;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createStatementsTableIndexes(int issuer_nr) throws SQLException {
        boolean deadlock;
        Connection cn = null;
        CallableStatement stmt = null;
        do {
            deadlock = false;
            try {
                cn = InfrastructureToolbox.getConnection("postilion_postcard");
                stmt = cn.prepareCall("{CALL pcb_add_inactive_statementstable_indexes (?)}");
                stmt.setInt(1, issuer_nr);
                stmt.execute();
                InfrastructureToolbox.commitAndClose(cn, stmt);
            } catch (SQLException e) {
                if (InfrastructureToolbox.isDeadlockException(e)) {
                    deadlock = true;
                    continue;
                }
                throw e;
            } finally {
                InfrastructureToolbox.cleanup(cn, stmt);
            }
        } while (deadlock);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createAccountBalancesTableConstraintsExceptIndexesForAll(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_accountbalancestable_constraints_except_indexes_for_all (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createCardsTableIndexes(int issuer_nr) throws SQLException {
        boolean deadlock;
        Connection cn = null;
        CallableStatement stmt = null;
        do {
            deadlock = false;
            try {
                try {
                    cn = InfrastructureToolbox.getConnection("postilion_postcard");
                    stmt = cn.prepareCall("{CALL pcb_add_inactive_cardstable_indexes (?)}");
                    stmt.setInt(1, issuer_nr);
                    stmt.execute();
                    InfrastructureToolbox.commitAndClose(cn, stmt);
                } finally {
                    InfrastructureToolbox.cleanup(cn, stmt);
                }
            } catch (SQLException e) {
                if (InfrastructureToolbox.isDeadlockException(e)) {
                    deadlock = true;
                    continue;
                }
                throw e;
            }
        } while (deadlock);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createAccountsTableIndexes(int issuer_nr) throws SQLException {
        boolean deadlock;
        Connection cn = null;
        CallableStatement stmt = null;
        do {
            deadlock = false;
            try {
                try {
                    cn = InfrastructureToolbox.getConnection("postilion_postcard");
                    stmt = cn.prepareCall("{CALL pcb_add_inactive_accountstable_indexes (?)}");
                    stmt.setInt(1, issuer_nr);
                    stmt.execute();
                    InfrastructureToolbox.commitAndClose(cn, stmt);
                } finally {
                    InfrastructureToolbox.cleanup(cn, stmt);
                }
            } catch (SQLException e) {
                if (InfrastructureToolbox.isDeadlockException(e)) {
                    deadlock = true;
                    continue;
                }
                throw e;
            }
        } while (deadlock);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createCardAccountsTableIndexes(int issuer_nr) throws SQLException {
        boolean deadlock;
        Connection cn = null;
        CallableStatement stmt = null;
        do {
            deadlock = false;
            try {
                try {
                    cn = InfrastructureToolbox.getConnection("postilion_postcard");
                    stmt = cn.prepareCall("{CALL pcb_add_inactive_cardaccountstable_indexes (?)}");
                    stmt.setInt(1, issuer_nr);
                    stmt.execute();
                    InfrastructureToolbox.commitAndClose(cn, stmt);
                } finally {
                    InfrastructureToolbox.cleanup(cn, stmt);
                }
            } catch (SQLException e) {
                if (InfrastructureToolbox.isDeadlockException(e)) {
                    deadlock = true;
                    continue;
                }
                throw e;
            }
        } while (deadlock);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createAccountBalancesTableIndexes(int issuer_nr) throws SQLException {
        boolean deadlock;
        Connection cn = null;
        CallableStatement stmt = null;
        do {
            deadlock = false;
            try {
                try {
                    cn = InfrastructureToolbox.getConnection("postilion_postcard");
                    stmt = cn.prepareCall("{CALL pcb_add_inactive_accountbalancestable_indexes (?)}");
                    stmt.setInt(1, issuer_nr);
                    stmt.execute();
                    InfrastructureToolbox.commitAndClose(cn, stmt);
                } finally {
                    InfrastructureToolbox.cleanup(cn, stmt);
                }
            } catch (SQLException e) {
                if (InfrastructureToolbox.isDeadlockException(e)) {
                    deadlock = true;
                    continue;
                }
                throw e;
            }
        } while (deadlock);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createStatementsTableConstraintsExceptIndexesForBalances(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_statementstable_constraints_except_indexes_for_balances (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static void removeAccountBalanceRecord(CallableStatement stmt, int issuer_nr, String account_id, String account_type) throws SQLException {
        if (InfrastructureToolbox.isIssuerOnlineLoadInProgress(issuer_nr)) {
            throw new SQLException("Deletion of existing account balance (issuer nr = " + Integer.toString(issuer_nr) + ", account id = " + account_id + ", account_type = " + account_type + ") is not permitted during an online update load.");
        }
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, account_id);
        stmt.setString(3, account_type);
        stmt.execute();
    }

    public static void removeCardAccountRecord(CallableStatement stmt, int issuer_nr, String pan, String seq_nr, String account_id, String account_type_nominated, String account_type) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, pan);
        stmt.setString(3, seq_nr);
        stmt.setString(4, account_id);
        stmt.setString(5, account_type_nominated);
        stmt.setString(6, account_type);
        stmt.execute();
    }

    public static void removeCardRecord(CallableStatement stmt, int issuer_nr, String pan, String seq_nr, String expiry_date) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, pan);
        stmt.setString(3, seq_nr);
        stmt.setString(4, expiry_date);
        stmt.execute();
    }

    public static void storeStatementRecord(CallableStatement stmt, int issuer_nr, String account_id, String tran_type, String tran_amount, String tran_local_datetime, String tran_posted_datetime, String last_updated_date, String account_type) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, account_id);
        stmt.setString(3, tran_type);
        stmt.setString(4, tran_amount);
        stmt.setString(5, tran_local_datetime);
        stmt.setString(6, tran_posted_datetime);
        stmt.setString(7, last_updated_date);
        stmt.setString(8, account_type);
        if (InfrastructureToolbox.isIssuerOnlineLoadInProgress(issuer_nr)) {
            stmt.setInt(9, 1);
        } else {
            stmt.setInt(9, 0);
        }
        stmt.execute();
    }

    public static void storeStatementRecord42(CallableStatement stmt, int issuer_nr, String account_id, String tran_type, String tran_amount, String tran_local_datetime, String tran_posted_datetime, String last_updated_date, String account_type) throws SQLException {
        String extended_tran_type = "";
        String other_account_type = "";
        String other_account_id = "";
        String check_nr = "";
        String card_acceptor_term_id = "";
        String card_acceptor_id_code = "";
        String card_acceptor_name_location = "";
        String description = "";
        String extended_fields = "";
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, account_id);
        stmt.setString(3, tran_type);
        stmt.setString(4, tran_amount);
        stmt.setString(5, tran_local_datetime);
        stmt.setString(6, tran_posted_datetime);
        stmt.setString(7, last_updated_date);
        stmt.setString(8, account_type);
        if (InfrastructureToolbox.isIssuerOnlineLoadInProgress(issuer_nr)) {
            stmt.setInt(9, 1);
        } else {
            stmt.setInt(9, 0);
        }
        stmt.setString(10, extended_tran_type);
        stmt.setString(11, other_account_type);
        stmt.setString(12, other_account_id);
        stmt.setString(13, check_nr);
        stmt.setString(14, card_acceptor_term_id);
        stmt.setString(11, card_acceptor_id_code);
        stmt.setString(12, card_acceptor_name_location);
        stmt.setString(13, description);
        stmt.setString(14, extended_fields);
        stmt.execute();
    }

    public static void removeAccountRecord(CallableStatement stmt, int issuer_nr, String account_id, String account_type) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, account_id);
        stmt.setString(3, account_type);
        stmt.execute();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createAccountBalancesTableConstraintsExceptIndexesForBalances(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_accountbalancestable_constraints_except_indexes_for_balances (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void swapAllTableGroupsStart(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_swap_all_tablegroups_start (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static String getInactiveAccountOverrideLimitsTablename(int issuer_nr) throws SQLException {
        char current_tableset = InfrastructureToolbox.getTablesetIndicator(issuer_nr, TableSetIndicator.ACCOUNTOVERRIDELIMITS_TABLE_SET);
        if (current_tableset == '\u0000') {
            return null;
        }
        if (current_tableset == 'A') {
            return "pc_account_override_limits_" + String.valueOf(issuer_nr) + "_B";
        }
        if (current_tableset == 'B') {
            return "pc_account_override_limits_" + String.valueOf(issuer_nr) + "_A";
        }
        return null;
    }

    public static String getInactiveCardOverrideLimitsTablename(int issuer_nr) throws SQLException {
        char current_tableset = InfrastructureToolbox.getTablesetIndicator(issuer_nr, TableSetIndicator.CARDOVERRIDELIMITS_TABLE_SET);
        if (current_tableset == '\u0000') {
            return null;
        }
        if (current_tableset == 'A') {
            return "pc_card_override_limits_" + String.valueOf(issuer_nr) + "_B";
        }
        if (current_tableset == 'B') {
            return "pc_card_override_limits_" + String.valueOf(issuer_nr) + "_A";
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setIssuerVelocityNumber(int issuer_nr, int velocity_nr) throws SQLException {
        Connection cn = null;
        PreparedStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareStatement("UPDATE\tpc_issuers SET current_velocity_nr = ? WHERE (issuer_id = ?)");
            stmt.setInt(1, velocity_nr);
            stmt.setInt(2, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createAccountOverrideLimitsTableIndexes(int issuer_nr) throws SQLException {
        boolean deadlock;
        Connection cn = null;
        CallableStatement stmt = null;
        do {
            deadlock = false;
            try {
                try {
                    cn = InfrastructureToolbox.getConnection("postilion_postcard");
                    stmt = cn.prepareCall("{CALL pcb_add_inactive_accountoverridelimitstable_indexes (?)}");
                    stmt.setInt(1, issuer_nr);
                    stmt.execute();
                    InfrastructureToolbox.commitAndClose(cn, stmt);
                } finally {
                    InfrastructureToolbox.cleanup(cn, stmt);
                }
            } catch (SQLException e) {
                if (InfrastructureToolbox.isDeadlockException(e)) {
                    deadlock = true;
                    continue;
                }
                throw e;
            }
        } while (deadlock);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createCardOverrideLimitsTableIndexes(int issuer_nr) throws SQLException {
        boolean deadlock;
        Connection cn = null;
        CallableStatement stmt = null;
        do {
            deadlock = false;
            try {
                try {
                    cn = InfrastructureToolbox.getConnection("postilion_postcard");
                    stmt = cn.prepareCall("{CALL pcb_add_inactive_cardoverridelimitstable_indexes (?)}");
                    stmt.setInt(1, issuer_nr);
                    stmt.execute();
                    InfrastructureToolbox.commitAndClose(cn, stmt);
                } finally {
                    InfrastructureToolbox.cleanup(cn, stmt);
                }
            } catch (SQLException e) {
                if (InfrastructureToolbox.isDeadlockException(e)) {
                    deadlock = true;
                    continue;
                }
                throw e;
            }
        } while (deadlock);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createCardsTableConstraintsExceptIndexes(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_cardstable_constraints_except_indexes (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createAccountsTableConstraintsExceptIndexes(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_accountstable_constraints_except_indexes (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createCardAccountsTableConstraintsExceptIndexes(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_cardaccountstable_constraints_except_indexes (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void swapFullAccountsLoadTableGroupFinish(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_swap_fullaccountsload_tablegroup_finish (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static void commitCallableStatement(Connection cn, CallableStatement stmt) throws SQLException {
        InfrastructureToolbox.commitAndClose(cn, stmt);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void IncrementExtractsSinceLoad(int issuer_nr, int which_group) throws SQLException {
        Connection cn = null;
        PreparedStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareStatement("UPDATE\tpc_issuers SET extracts_since_load = extracts_since_load+1 WHERE (issuer_id = ?)");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static void storeAccountRecord(CallableStatement stmt, int issuer_nr, String account_id, String account_type, String currency_code, String last_updated_date, String last_updated_user, String customer_id) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, account_id);
        stmt.setString(3, account_type);
        stmt.setString(4, currency_code);
        stmt.setString(5, last_updated_date);
        stmt.setString(6, last_updated_user);
        stmt.setString(7, customer_id);
        stmt.addBatch();
    }

    public static void storeAccountBalanceRecord(CallableStatement stmt, int issuer_nr, String account_id, String ledger_balance, String available_balance, String last_updated_date, String account_type) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, account_id);
        stmt.setString(3, ledger_balance);
        stmt.setString(4, available_balance);
        stmt.setString(5, last_updated_date);
        stmt.setString(6, account_type);
        if (InfrastructureToolbox.isIssuerOnlineLoadInProgress(issuer_nr)) {
            stmt.setInt(7, 1);
        } else {
            stmt.setInt(7, 0);
        }
        stmt.addBatch();
    }

    public static void removeAccountOverrideLimitRecord(CallableStatement stmt, int issuer_nr, String account_id, String account_type) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, account_id);
        stmt.setString(3, account_type);
        stmt.execute();
    }

    public static void storeCardAccountRecord(CallableStatement stmt, int issuer_nr, String pan, String seq_nr, String account_id, String account_type_nominated, String account_type_qualifier, String last_updated_date, String last_updated_user, String account_type) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, pan);
        stmt.setString(3, seq_nr);
        stmt.setString(4, account_id);
        stmt.setString(5, account_type_nominated);
        stmt.setString(6, account_type_qualifier);
        stmt.setString(7, last_updated_date);
        stmt.setString(8, last_updated_user);
        stmt.setString(9, account_type);
        stmt.addBatch();
    }

    public static void removeCardOverrideLimitRecord(CallableStatement stmt, int issuer_nr, String pan, String seq_nr) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, pan);
        stmt.setString(3, seq_nr);
        stmt.execute();
    }

    public static void storeCardRecord(CallableStatement stmt, int issuer_nr, CardRecordInfo cri, String batch_nr, String last_updated_date, String last_updated_user) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, cri.pan);
        stmt.setString(3, cri.seq_nr);
        stmt.setString(4, cri.card_program);
        stmt.setString(5, cri.default_account_type);
        stmt.setString(6, cri.card_status);
        stmt.setString(7, cri.expiry_date);
        stmt.setString(8, cri.hold_rsp_code);
        stmt.setString(9, cri.track2_value);
        stmt.setString(10, cri.track2_value_offset);
        stmt.setString(11, cri.pvki_or_pin_length);
        stmt.setString(12, cri.pvv_or_pin_offset_secure);
        stmt.setString(13, cri.pvv_or_pin_offset_insecure);
        stmt.setString(14, cri.validation_data_question);
        stmt.setString(15, cri.validation_data);
        stmt.setString(16, cri.cardholder_rsp_info);
        stmt.setString(17, cri.cardholder_title);
        stmt.setString(18, cri.cardholder_first_name);
        stmt.setString(19, cri.cardholder_middle_initials);
        stmt.setString(20, cri.cardholder_last_name);
        stmt.setString(21, cri.cardholder_name_on_card);
        stmt.setString(22, cri.cardholder_address_1);
        stmt.setString(23, cri.cardholder_address_2);
        stmt.setString(24, cri.cardholder_city);
        stmt.setString(25, cri.cardholder_region);
        stmt.setString(26, cri.cardholder_postal_code);
        stmt.setString(27, cri.cardholder_country);
        stmt.setString(28, cri.cardholder_date_of_birth);
        stmt.setString(29, cri.mailer_destination);
        stmt.setString(30, cri.company_name);
        stmt.setString(31, cri.vip);
        stmt.setString(32, cri.vip_lapse_date);
        stmt.setString(33, cri.discretionary_data);
        stmt.setString(34, cri.date_issued);
        stmt.setString(35, cri.date_activated);
        stmt.setString(36, cri.issuer_reference);
        stmt.setString(37, last_updated_date);
        stmt.setString(38, last_updated_user);
        stmt.setString(39, cri.card_custom_state);
        stmt.setString(40, cri.branch_code);
        stmt.setString(41, cri.customer_id);
        stmt.setString(42, batch_nr);
        stmt.addBatch();
    }

    public static void storeCardRecord42(CallableStatement stmt, int issuer_nr, CardRecordInfo cri, String batch_nr, String last_updated_date, String last_updated_user) throws SQLException {
        String extended_fields = "";
        String expiry_day = "";
        String from_date = "";
        String from_day = "";
        String pvki2_or_pin2_length = "";
        String contactless_discretionary_data = "";
        String dcvv_key_index = "";
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, cri.pan);
        stmt.setString(3, cri.seq_nr);
        stmt.setString(4, cri.card_program);
        stmt.setString(5, cri.default_account_type);
        stmt.setString(6, cri.card_status);
        stmt.setString(7, cri.expiry_date);
        stmt.setString(8, null);
        stmt.setString(9, cri.track2_value);
        stmt.setString(10, cri.track2_value_offset);
        stmt.setString(11, cri.pvki_or_pin_length);
        stmt.setString(12, cri.pvv_or_pin_offset_secure);
        stmt.setString(13, cri.pvv_or_pin_offset_insecure);
        stmt.setString(14, cri.validation_data_question);
        stmt.setString(15, cri.validation_data);
        stmt.setString(16, cri.cardholder_rsp_info);
        stmt.setString(17, cri.mailer_destination);
        stmt.setString(18, cri.discretionary_data);
        stmt.setString(19, cri.date_issued);
        stmt.setString(20, cri.date_activated);
        stmt.setString(21, cri.issuer_reference);
        stmt.setString(22, last_updated_date);
        stmt.setString(23, last_updated_user);
        stmt.setString(24, cri.card_custom_state);
        stmt.setString(25, cri.branch_code);
        stmt.setString(26, cri.customer_id);
        stmt.setString(27, batch_nr);
        stmt.setString(28, cri.company_name);
        stmt.setString(29, pvki2_or_pin2_length);
        stmt.setString(30, extended_fields);
        stmt.setString(31, expiry_day);
        stmt.setString(32, from_date);
        stmt.setString(33, from_day);
        stmt.setString(34, contactless_discretionary_data);
        stmt.setString(35, dcvv_key_index);
        stmt.addBatch();
    }

    public static void storeCustomerRecord(CallableStatement stmt, int issuer_nr, CardRecordInfo cri, String batch_nr, String last_updated_date, String last_updated_user) throws SQLException {
        String extended_fields = "";
        String preferred_lang = "EN";
        String national_id_nr = "234";
        String vip_lapse_date = "";
        String vip = "";
        String tel_nr = "";
        String mobile_nr = "";
        String fax_nr = "";
        String c2_title = "";
        String c2_first_name = "";
        String c2_initials = "";
        String c2_last_name = "";
        String c2_name_on_card = "";
        String c3_title = "";
        String c3_first_name = "";
        String c3_initials = "";
        String c3_last_name = "";
        String c3_name_on_card = "";
        String email_address = "";
        String other_address_1 = "";
        String other_address_2 = "";
        String other_city = "";
        String other_region = "";
        String other_postal_code = "";
        String other_country = "";
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, cri.customer_id);
        stmt.setString(3, national_id_nr);
        stmt.setString(4, cri.cardholder_title);
        stmt.setString(5, cri.cardholder_first_name);
        stmt.setString(6, cri.cardholder_middle_initials);
        stmt.setString(7, cri.cardholder_last_name);
        stmt.setString(8, cri.cardholder_name_on_card);
        stmt.setString(9, c2_title);
        stmt.setString(10, c2_first_name);
        stmt.setString(11, c2_initials);
        stmt.setString(12, c2_last_name);
        stmt.setString(13, c2_name_on_card);
        stmt.setString(14, c3_title);
        stmt.setString(15, c3_first_name);
        stmt.setString(16, c3_initials);
        stmt.setString(17, c3_last_name);
        stmt.setString(18, c3_name_on_card);
        stmt.setString(19, tel_nr);
        stmt.setString(20, mobile_nr);
        stmt.setString(21, fax_nr);
        stmt.setString(22, email_address);
        stmt.setString(23, cri.cardholder_address_1);
        stmt.setString(24, cri.cardholder_address_2);
        stmt.setString(25, cri.cardholder_city);
        stmt.setString(26, cri.cardholder_region);
        stmt.setString(27, cri.cardholder_postal_code);
        stmt.setString(28, cri.cardholder_country);
        stmt.setString(29, other_address_1);
        stmt.setString(30, other_address_2);
        stmt.setString(31, other_city);
        stmt.setString(32, other_region);
        stmt.setString(33, other_postal_code);
        stmt.setString(34, other_country);
        stmt.setString(35, cri.cardholder_date_of_birth);
        stmt.setString(36, cri.company_name);
        stmt.setString(37, preferred_lang);
        stmt.setString(38, vip);
        stmt.setString(39, vip_lapse_date);
        stmt.setString(40, last_updated_date);
        stmt.setString(41, last_updated_user);
        stmt.setString(42, extended_fields);
        stmt.addBatch();
    }

    public static boolean adjustAccountBalanceRecord(CallableStatement stmt, int issuer_nr, String account_id, String account_type, String ledger_balance_delta, String available_balance_delta, String last_updated_date, boolean only_check_that_account_exists) throws SQLException {
        int i = 0;
        stmt.setInt(++i, issuer_nr);
        stmt.setString(++i, account_id);
        stmt.setString(++i, account_type);
        stmt.setString(++i, ledger_balance_delta);
        stmt.setString(++i, available_balance_delta);
        stmt.setString(++i, last_updated_date);
        stmt.setInt(++i, only_check_that_account_exists ? 1 : 0);
        stmt.registerOutParameter(++i, 4);
        stmt.execute();
        int result = stmt.getInt(i);
        return result == 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateCardProductionAfterFullLoad(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_card_production_update_after_load (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static void commenceOnlineLoad(int issuer_nr) throws Exception {
        Integer issuer = issuer_nr;
        Integer entry = (Integer) online_issuer_loads.put(issuer, issuer);
        if (entry != null) {
            throw new Exception("InfrastructureToolbox.commenceOnlineLoad(): issuer online load already in progress for issuer " + issuer.toString());
        }
    }

    public static boolean isIssuerOnlineLoadInProgress(int issuer_nr) {
        Integer issuer = issuer_nr;
        Object entry = online_issuer_loads.get(issuer);
        return entry != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void DecrementExtractsSinceLoad(int issuer_nr) throws SQLException {
        Connection cn = null;
        PreparedStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareStatement("UPDATE\tpc_issuers SET extracts_since_load = extracts_since_load-1 WHERE (issuer_id = ?)");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }
    
    public static char getTablesetIndicator(int issuer_nr, int which_table_set) {
        String sqlquery = null;
        sqlquery = "{call pcb_get_tableset_indicator (?, ?, ?)}";
        Connection cn = null;
        CallableStatement stmt = null;
        char current_tableset = '\u0000';
        boolean error = false;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall(sqlquery);
            stmt.setInt(1, issuer_nr);
            stmt.setInt(2, which_table_set);
            stmt.registerOutParameter(3, 1);
            stmt.execute();
            current_tableset = stmt.getString(3).charAt(0);
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } catch (SQLException sqle) {
            error = true;
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
        if (error) {
            return '\u0000';
        }
        return current_tableset;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void swapBalancesTableGroupStart(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_swap_balances_tablegroup_start (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean createIssuer(int issuer_nr, String issuer_name, String institution_id, String card_production_required, String velocity_currency_code, String card_info_managed_by_issuer, String last_updated_user) {
        Connection cn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        boolean error = false;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = InfrastructureToolbox.prepareStoredProc(cn, "pcb_add_issuer", 7);
            stmt.setInt(1, issuer_nr);
            stmt.setString(2, issuer_name);
            stmt.setString(3, institution_id);
            stmt.setString(4, card_production_required);
            stmt.setString(5, velocity_currency_code);
            stmt.setString(6, card_info_managed_by_issuer);
            stmt.setString(7, last_updated_user);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt, rs);
        } catch (SQLException sqle) {
            error = true;
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt, rs);
        }
        return !error;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean removeIssuer(int issuer_nr) {
        Connection cn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        boolean error = false;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{call pcb_delete_issuer (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt, rs);
        } catch (SQLException sqle) {
            error = true;
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt, rs);
        }
        return !error;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean grabApplicationLock(int issuer_nr, String application_name) throws SQLException {
        boolean ok_to_proceed = false;
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_grab_application_lock (?,?,?)}");
            stmt.setInt(1, issuer_nr);
            stmt.setString(2, application_name);
            stmt.registerOutParameter(3, 4);
            stmt.execute();
            int ok_to_proceed_int = stmt.getInt(3);
            if (ok_to_proceed_int == 1) {
                ok_to_proceed = true;
            }
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
        return ok_to_proceed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void releaseApplicationLock(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_release_application_lock (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static CallableStatement prepareStoredProc(Connection cn, String procname, int nparams) throws SQLException {
        String PREFIX = "{call ";
        String SUFFIX = ")}";
        String PROCNAME_SUFFIX = "(";
        StringBuilder stmt = new StringBuilder("{call ".length() + procname.length() + "(".length() + nparams * 2 + ")}".length());
        stmt.append("{call ");
        stmt.append(procname);
        stmt.append("(");
        stmt.append("?");
        for (int j = 1; j < nparams; ++j) {
            stmt.append(",?");
        }
        stmt.append(")}");
        return cn.prepareCall(stmt.toString());
    }

    public InfrastructureToolbox() {
        System.out.println("Using InfrastructureToolbox...");
    }

    public static void storeAccountOverrideLimitRecord(CallableStatement stmt, int issuer_nr, AccountOverrideLimitRecordInfo aolri, String last_updated_date, String last_updated_user) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, aolri.account_id);
        stmt.setString(3, aolri.goods_nr_trans_limit);
        stmt.setString(4, aolri.goods_limit);
        stmt.setString(5, aolri.goods_offline_limit);
        stmt.setString(6, aolri.cash_nr_trans_limit);
        stmt.setString(7, aolri.cash_limit);
        stmt.setString(8, aolri.cash_offline_limit);
        stmt.setString(9, aolri.card_not_present_limit);
        stmt.setString(10, aolri.card_not_present_offline_limit);
        stmt.setString(11, aolri.deposit_credit_limit);
        stmt.setString(12, last_updated_date);
        stmt.setString(13, last_updated_user);
        stmt.setString(14, aolri.account_type);
        stmt.setString(15, aolri.weekly_goods_nr_trans_limit);
        stmt.setString(16, aolri.weekly_goods_limit);
        stmt.setString(17, aolri.weekly_goods_offline_limit);
        stmt.setString(18, aolri.weekly_cash_nr_trans_limit);
        stmt.setString(19, aolri.weekly_cash_limit);
        stmt.setString(20, aolri.weekly_cash_offline_limit);
        stmt.setString(21, aolri.weekly_card_not_present_limit);
        stmt.setString(22, aolri.weekly_card_not_present_offline_limit);
        stmt.setString(23, aolri.weekly_deposit_credit_limit);
        stmt.setString(24, aolri.monthly_goods_nr_trans_limit);
        stmt.setString(25, aolri.monthly_goods_limit);
        stmt.setString(26, aolri.monthly_goods_offline_limit);
        stmt.setString(27, aolri.monthly_cash_nr_trans_limit);
        stmt.setString(28, aolri.monthly_cash_limit);
        stmt.setString(29, aolri.monthly_cash_offline_limit);
        stmt.setString(30, aolri.monthly_card_not_present_limit);
        stmt.setString(31, aolri.monthly_card_not_present_offline_limit);
        stmt.setString(32, aolri.monthly_deposit_credit_limit);
        stmt.setString(33, aolri.tran_goods_limit);
        stmt.setString(34, aolri.tran_goods_offline_limit);
        stmt.setString(35, aolri.tran_cash_limit);
        stmt.setString(36, aolri.tran_cash_offline_limit);
        stmt.setString(37, aolri.tran_card_not_present_limit);
        stmt.setString(38, aolri.tran_card_not_present_offline_limit);
        stmt.setString(39, aolri.tran_deposit_credit_limit);
        stmt.execute();
    }

    public static void storeCardOverrideLimitRecord(CallableStatement stmt, int issuer_nr, CardOverrideLimitRecordInfo colri, String last_updated_date, String last_updated_user) throws SQLException {
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, colri.pan);
        stmt.setString(3, colri.seq_nr);
        stmt.setString(4, colri.goods_nr_trans_limit);
        stmt.setString(5, colri.goods_limit);
        stmt.setString(6, colri.goods_offline_limit);
        stmt.setString(7, colri.cash_nr_trans_limit);
        stmt.setString(8, colri.cash_limit);
        stmt.setString(9, colri.cash_offline_limit);
        stmt.setString(10, colri.card_not_present_limit);
        stmt.setString(11, colri.card_not_present_offline_limit);
        stmt.setString(12, colri.deposit_credit_limit);
        stmt.setString(13, last_updated_date);
        stmt.setString(14, last_updated_user);
        stmt.setString(15, colri.weekly_goods_nr_trans_limit);
        stmt.setString(16, colri.weekly_goods_limit);
        stmt.setString(17, colri.weekly_goods_offline_limit);
        stmt.setString(18, colri.weekly_cash_nr_trans_limit);
        stmt.setString(19, colri.weekly_cash_limit);
        stmt.setString(20, colri.weekly_cash_offline_limit);
        stmt.setString(21, colri.weekly_card_not_present_limit);
        stmt.setString(22, colri.weekly_card_not_present_offline_limit);
        stmt.setString(23, colri.weekly_deposit_credit_limit);
        stmt.setString(24, colri.monthly_goods_nr_trans_limit);
        stmt.setString(25, colri.monthly_goods_limit);
        stmt.setString(26, colri.monthly_goods_offline_limit);
        stmt.setString(27, colri.monthly_cash_nr_trans_limit);
        stmt.setString(28, colri.monthly_cash_limit);
        stmt.setString(29, colri.monthly_cash_offline_limit);
        stmt.setString(30, colri.monthly_card_not_present_limit);
        stmt.setString(31, colri.monthly_card_not_present_offline_limit);
        stmt.setString(32, colri.monthly_deposit_credit_limit);
        stmt.setString(33, colri.tran_goods_limit);
        stmt.setString(34, colri.tran_goods_offline_limit);
        stmt.setString(35, colri.tran_cash_limit);
        stmt.setString(36, colri.tran_cash_offline_limit);
        stmt.setString(37, colri.tran_card_not_present_limit);
        stmt.setString(38, colri.tran_card_not_present_offline_limit);
        stmt.setString(39, colri.tran_deposit_credit_limit);
        stmt.execute();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createAccountOverrideLimitsTableConstraintsExceptIndexes(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_accountoverridelimitstable_constraints_except_indexes (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createCardOverrideLimitsTableConstraintsExceptIndexes(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_add_inactive_cardoverridelimitstable_constraints_except_indexes (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void swapAllTableGroupsFinish(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_swap_all_tablegroups_finish (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static void removeStatementRecord(CallableStatement stmt, int issuer_nr, String account_id, String tran_posted_datetime, String account_type) throws SQLException {
        if (InfrastructureToolbox.isIssuerOnlineLoadInProgress(issuer_nr)) {
            throw new SQLException("Deletion of existing statement for (issuer nr = " + Integer.toString(issuer_nr) + ", account id = " + account_id + ", account_type = " + account_type + ") is not permitted during an online update load.");
        }
        stmt.setInt(1, issuer_nr);
        stmt.setString(2, account_id);
        stmt.setString(3, tran_posted_datetime);
        stmt.setString(4, account_type);
        stmt.execute();
    }

    public static void storeEmvScriptRecord(CallableStatement stmt, int issuer_nr, String pan, String seq_nr, String expiry_date, String application_id, String script_command_type, String script_command_body, String generated_date, String script_expiry_date, String max_send_tries, String last_updated_user) throws SQLException {
        int i = 0;
        stmt.setInt(++i, issuer_nr);
        stmt.setString(++i, pan);
        stmt.setString(++i, seq_nr);
        stmt.setString(++i, expiry_date);
        stmt.setString(++i, application_id);
        stmt.setString(++i, script_command_type);
        stmt.setString(++i, script_command_body);
        stmt.setString(++i, generated_date);
        stmt.setString(++i, script_expiry_date);
        stmt.setString(++i, max_send_tries);
        stmt.setString(++i, last_updated_user);
        stmt.execute();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void swapBalancesTableGroupFinish(int issuer_nr) throws SQLException {
        Connection cn = null;
        CallableStatement stmt = null;
        try {
            cn = InfrastructureToolbox.getConnection("postilion_postcard");
            stmt = cn.prepareCall("{CALL pcb_swap_balances_tablegroup_finish (?)}");
            stmt.setInt(1, issuer_nr);
            stmt.execute();
            InfrastructureToolbox.commitAndClose(cn, stmt);
        } finally {
            InfrastructureToolbox.cleanup(cn, stmt);
        }
    }

    public static void endOnlineLoad(int issuer_nr) throws Exception {
        Integer issuer = new Integer(issuer_nr);
        Object entry = online_issuer_loads.remove(issuer);
        if (entry == null) {
            throw new Exception("InfrastructureToolbox.endOnlineLoad(): no issuer online load in progress for issuer " + issuer.toString());
        }
    }

    public static String getInactiveStatementsTablename(int issuer_nr) throws SQLException {
        char current_tableset = InfrastructureToolbox.getTablesetIndicator(issuer_nr, TableSetIndicator.STATEMENTS_TABLE_SET);
        if (current_tableset == '\u0000') {
            return null;
        }
        if (current_tableset == 'A') {
            return "pc_statements_" + String.valueOf(issuer_nr) + "_B";
        }
        if (current_tableset == 'B') {
            return "pc_statements_" + String.valueOf(issuer_nr) + "_A";
        }
        return null;
    }

    public static Connection getConnection(String jndiName) {
        Connection con = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/" + jndiName);
            con = ds.getConnection();
        } catch (SQLException | NamingException x) {
            System.out.println("Error getting connection ->" + x);
        }
        return con;
    }

    public static final void commitAndClose(Connection cn, Statement stmt, ResultSet rs) {
        try {
            if (cn == null) {
                return;
            }
            cn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error commitAndClose - >" + e);
        }
    }

    public static final void commitAndClose(Connection cn, Statement stmt) {
        try {
            InfrastructureToolbox.commitAndClose(cn, stmt, null);
        } catch (Exception e) {
            System.out.println("Error commitAndClose - >" + e);
        }
    }

    public static final void cleanup(Connection cn, Statement stmt, ResultSet rs) {
        try {
            if (cn == null) {
                return;
            }
            if (cn.isClosed()) {
                return;
            }
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (cn != null) {
                cn.close();
            }
        } catch (Exception e) {
            System.out.println("Error cleaning up ->" + e);
        }
    }

    public static final void cleanup(Connection cn, Statement stmt) {
        try {
            InfrastructureToolbox.cleanup(cn, stmt, null);
        } catch (Exception x) {
            System.out.println("Error cleaning up ->" + x);
        }
    }

    public static final void cleanup(Connection cn) throws SQLException {
        InfrastructureToolbox.cleanup(cn, null, null);
    }

    public static final void commitAndClose(Connection cn) throws SQLException {
        InfrastructureToolbox.commitAndClose(cn, null, null);
    }

    public static final boolean isQueryTimeoutException(SQLException sql_exception) {
        for (SQLException cur_sql_exception = sql_exception; cur_sql_exception != null; cur_sql_exception = cur_sql_exception.getNextException()) {
            if (!SqlState.isQueryTimeoutException(cur_sql_exception.getSQLState())) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static final boolean isDeadlockException(SQLException sql_exception) {
        for (SQLException current_sql_exception = sql_exception; current_sql_exception != null; current_sql_exception = current_sql_exception.getNextException()) {
            String sql_state = current_sql_exception.getSQLState();
            if (!SqlState.isSerializationFailure(sql_state)) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static final boolean isIntegrityConstraintException(SQLException sql_exception) {
        for (SQLException current_sql_exception = sql_exception; current_sql_exception != null; current_sql_exception = current_sql_exception.getNextException()) {
            String sql_state = current_sql_exception.getSQLState();
            if (!SqlState.isIntegrityConstraintViolation(sql_state)) {
                continue;
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void clearHoldResponseCode() {
        String query = "UPDATE PC_ACCOUNTS_1_A SET HOLD_RSP_CODE = NULL WHERE LAST_UPDATED_USER = 'expad' AND HOLD_RSP_CODE = '00'";
        Connection con = null;
        Statement s = null;
        try {
            con = InfrastructureToolbox.getConnection("postilion_postcard");
            s = con.createStatement();
            s.execute(query);
        } catch (SQLException ex) {
            System.out.println("InfrastructureToolBox:clearHoldResponseCode:error->" + ex);
        } finally {
            InfrastructureToolbox.cleanup(con, s);
        }
    }

    public static class IssuerInformation {

        public int high_extract_nr;
        public int low_extract_nr;
        public int current_velocity_nr;
        public int current_weekly_velocity_nr;
        public int current_monthly_velocity_nr;
        public int current_auth_mode;
        public int check_weekly_velocities;
        public int check_monthly_velocities;
        public int check_per_transaction_velocities;
    }

    public static class TableSetIndicator {

        public static int CARDS_TABLE_SET = 1;
        public static int ACCOUNTS_TABLE_SET = 2;
        public static int CARDACCOUNTS_TABLE_SET = 3;
        public static int ACCOUNTBALANCES_TABLE_SET = 4;
        public static int STATEMENTS_TABLE_SET = 5;
        public static int ACCOUNTOVERRIDELIMITS_TABLE_SET = 6;
        public static int CARDOVERRIDELIMITS_TABLE_SET = 7;
    }

    public static final class SqlState {

        public static final String _00000_SUCCESS = "00000";
        public static final String _01000_GENERAL_WARNING = "01000";
        public static final String _01002_DISCONNECT_ERROR = "01002";
        public static final String _01004_DATA_TRUNCATED = "01004";
        public static final String _01006_PRIVILEGE_NOT_REVOKED = "01006";
        public static final String _01S00_INVALID_CONNECTION_STRING_ATTRIBUTE = "01S00";
        public static final String _01S01_ERROR_IN_ROW = "01S01";
        public static final String _01S02_OPTION_VALUE_CHANGED = "01S02";
        public static final String _01S03_NO_ROWS_UPDATED_OR_DELETED = "01S03";
        public static final String _01S04_MORE_THAN_ONE_ROW_UPDATED_OR_DELETED = "01S04";
        public static final String _01S05_CANCEL_TREATED_AS_FREESTMT_OR_CLOSE = "01S05";
        public static final String _01S06_ATTEMPT_TO_FETCH_BEFORE_RESULT_RETURNED_THE_FIRST_ROW = "01S06";
        public static final String _07001_WRONG_NUMBER_OF_PARAMETERS = "07001";
        public static final String _07006_RESTRICTED_DATA_TYPE_ATTRIBUTE_VIOLATION = "07006";
        public static final String _07S01_INVALID_USE_OF_DEFAULT_PARAMETER = "07S01";
        public static final String _08001_UNABLE_TO_CONNECT_TO_DATA_SOURCE = "08001";
        public static final String _08002_CONNECTION_IN_USE = "08002";
        public static final String _08003_CONNECTION_NOT_OPENED = "08003";
        public static final String _08004_DATA_SOURCE_REJECTED_ESTABLISHMENT_OF_CONNECTION = "08004";
        public static final String _08007_CONNECTION_FAILURE_DURING_TRANSACTION = "08007";
        public static final String _08S01_COMMUNICATION_LINK_FAILURE = "08S01";
        public static final String _22003_NUMERIC_VALUE_OUT_OF_RANGE = "22003";
        public static final String _23000_INTEGRITY_CONSTRAINT_VIOLATION = "23000";
        public static final String _40001_SERIALIZATION_FAILURE = "40001";
        public static final String _40002_SYNTAX_ERROR_OR_ACCESS_VIOLATION = "40002";
        public static final String _S1T00_QUERY_TIMEOUT_EXPIRED = "S1T00";

        public static boolean isConnectionException(String state) {
            if (state == null) {
                return false;
            }
            if (state.length() < 2) {
                return false;
            }
            return state.startsWith("08");
        }

        public static boolean isQueryTimeoutException(String state) {
            return "S1T00".equals(state);
        }

        public static boolean isSerializationFailure(String code) {
            return "40001".equals(code);
        }

        public static boolean isIntegrityConstraintViolation(String code) {
            return "23000".equals(code);
        }
    }

}
