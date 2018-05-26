/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.app.generator;

import static com.ocularminds.expad.app.generator.HostAuth.url;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public final class Etranzact {

    public static final String SUCCESSFUL = "00";
    public static final String INVALID_ACCESS_PARAMETER = "01";
    public static final String CARD_NOT_EXIST = "03";
    public static final String INAVLID_PARAMETER_VALUES = "04";
    public static final String DUPLICATE_ENTRY = "05";
    public static final String FAILURE = "91";
    public static final String CARD_CREATION = "ccard";
    public static final String CARD_ACCT_UPD = "ucard";
    public static final String NETWORK_NAME = "ETRANZACT";

    public HostRes createCard(String branchCode) {
        HostReq req = new HostReq("ccard", "ETRANZACT");
        req.setField("BRANCH_CODE", branchCode);
        return send(req);
        
    }

    public HostRes update(String pan, String accountno, String firstName, String lastName, String mobile, String email, String address) {
        HostReq req = new HostReq("ucard", "ETRANZACT");
        req.setField("PAN", pan);
        req.setField("CARD_ACCOUNT", accountno);
        req.setField("FNAME", firstName);
        req.setField("LNAME", lastName);
        req.setField("MOBILE", mobile);
        req.setField("EMAIL", email);
        req.setField("ADDRESS", address);
        return send(req);
    }

    private HostRes send(HostReq req) {
        StringBuilder sb = new StringBuilder();
        try {
            String s;
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setAllowUserInteraction(false);
            try (DataOutputStream server = new DataOutputStream(connection.getOutputStream())) {
                server.writeBytes(req.toString());
            }
            try (DataInputStream in = new DataInputStream(connection.getInputStream())) {
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return new HostRes(sb.toString());
    }
}
