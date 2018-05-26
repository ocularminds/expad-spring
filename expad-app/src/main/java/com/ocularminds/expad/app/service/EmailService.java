/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.service;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public class EmailService {
    

    /*
    public List extractEmailByFunction(String functionURL) {
        List<MailExtractor> emails;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        emails = new List<MailExtractor>();
        StringBuilder sb = new StringBuilder("SELECT DISTINCT(A.STAFF_ID),A.STAFF_NAME,A.EMAIL ");
        sb.append("FROM USERS A, ROLEFUNCTION R,FUNCTIONS F WHERE A.ROLE = R.ROLE_ID    ");
        sb.append("AND R.FUNC_UUID = F.UUID AND F.URL ='" + functionURL + "' ORDER BY 1");
        try {
            con = this.getConnection("expad");
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                String staffCode = rs.getString("STAFF_ID");
                String staffName = rs.getString("STAFF_NAME");
                String email = rs.getString("EMAIL");
                MailExtractor me = new MailExtractor(staffCode, staffName, email);
                emails.add(me);
            }
        }
        catch (Exception e) {
            LOG.info("[EXPAD] Error extracting Email By Function-> " + e.getMessage());
        }
        finally {
            this.closeConnection(con, ps);
        }
        return emails;
    }*/
}
