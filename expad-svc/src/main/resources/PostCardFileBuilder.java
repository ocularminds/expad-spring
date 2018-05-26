/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  com.ocularminds.util.DatetimeFormat
 *  com.ocularminds.util.FileFormat
 */
package com.ocularminds.expad.svc.builder;

import com.ocularminds.expad.svc.FileNames;
import com.ocularminds.expad.service.AdminService;
import com.ocularminds.expad.service.CardService;
import com.ocularminds.expad.service.ServiceLocator;
import com.ocularminds.util.FileFormat;
import com.ocularminds.expad.vao.Card;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.LinkedAccount;
import com.ocularminds.expad.vao.Product;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class PostCardFileBuilder extends FileNames {
    
    static final Logger LOG = Logger.getLogger(PostCardFileBuilder.class.getName());

    public static Map buildFile(List list) throws Exception {
        AdminService as = (AdminService) ServiceLocator.locate(AdminService.class);
        CardService cs = (CardService) ServiceLocator.locate(CardService.class);
        Map<String, String> branches = as.mappedBranches();
        Company company = as.config();
        String processDateTime = ddf.format(new Date());
        StringBuilder mailQuery = new StringBuilder();
        StringBuilder carddata = new StringBuilder();
        StringBuilder cardaccount = new StringBuilder();
        StringBuilder balances = new StringBuilder();
        StringBuilder accountdata = new StringBuilder();
        for (int index = 0; index < list.size(); ++index) {
            Card request = (Card) list.get(index);
            String id = request.getId();
            String pan = request.getPan();
            String productCode = request.getProductCode();
            Product product = cs.product(productCode);
            String cardProgram = product != null ? product.getName() : " ";
            String currencyCode = product != null ? product.getCurrencyCode() : "NGN";
            String trailingText = product != null ? product.getTrailingText() : "pppp";
            expiryDate = request.getExpiryDate().replaceAll("/", "");
            revExpiry = expiryDate.substring(2, 4) + expiryDate.substring(0, 2);
            List<LinkedAccount> accounts = Arrays.asList(cs.findDebitCardLinkAccounts(id));
            if (accounts == null || accounts.size() < 1) {
                System.out.println("INFO : Card Not Linked ");
                continue;
            }
            int totalAccount = accounts.size();
            String custid = request.getCustomerId();
            if (custid == null) {
                custid = " ";
            }
            String holder = request.getPreferedName();
            holder = holder.replaceAll(",", "");
            holder = holder.replaceAll(";", "");
            holder = holder.replaceAll(",", "");
            String sol = request.getCollectingSol();
            if (sol == null) {
                sol = "230";
            }
            String accnum = accounts.get(0).getAccountNumber();
            custid = FileFormat.formatText((int) 9, (String) custid);
            holder = FileFormat.formatText((int) 26, (String) holder);
            currencyCode = FileFormat.formatText((int) 3, (String) currencyCode);
            PIN_OFFSET = request.getOffset();
            pan = FileFormat.formatText((int) company.getPanSize(), (String) pan);
            holder = FileFormat.formatText((int) 26, (String) holder);
            custid = FileFormat.formatText((int) 9, (String) custid);
            String custAccount;
            sol = FileFormat.formatText((int) 3, (String) sol);
            String branch = branches.get(sol);
            branch = FileFormat.formatText((int) 26, (String) branch);
            mailQuery.append(holder);
            mailQuery.append(comma);
            mailQuery.append(custid);
            mailQuery.append(comma);
            mailQuery.append(sol);
            mailQuery.append("\r\n");
            String firstName = "";
            String secondName = "";
            String type = "10";
            String accounttypex = accnum.substring(company.getAccountStartPosition(), company.getAccountEndPosition());
            if (company.getCurrentMapCode().lastIndexOf(accounttypex) > -1) {
                type = "20";
            }
            if (company.isPrefixedWithU()) {
                carddata.append("U");
                carddata.append(comma);
            }
            carddata.append(pan);
            carddata.append(comma);
            carddata.append("001");
            carddata.append(comma);
            carddata.append(cardProgram);
            carddata.append(comma);
            carddata.append(type);
            carddata.append(comma);
            carddata.append("0");
            carddata.append(comma);
            carddata.append(comma);
            String xx = expiryDate.substring(0, 2);
            int pos = Integer.parseInt(xx);
            if (pos < 10) {
                carddata.append(revExpiry);
            } else {
                carddata.append(expiryDate);
            }
            carddata.append(comma).append(comma).append(comma).append(comma);
            carddata.append("4");
            carddata.append(comma);
            carddata.append(PIN_OFFSET);
            carddata.append(comma);
            carddata.append(firstName);
            carddata.append(comma);
            carddata.append(secondName);
            carddata.append(comma);
            carddata.append(comma);
            carddata.append(comma);
            carddata.append(comma).append(comma).append(comma).append(comma);
            carddata.append(holder);
            carddata.append(comma);
            carddata.append(branch).append(comma).append(comma).append(comma).append(comma).append(comma).append(currencyCode);
            carddata.append(comma).append(comma);
            carddata.append("1");
            carddata.append(comma).append(comma);
            carddata.append("0");
            carddata.append(comma).append(comma);
            carddata.append(trailingText);
            carddata.append(comma);
            carddata.append(processDateTime);
            carddata.append(comma);
            carddata.append("1900-01-01 00:00:00.000");
            carddata.append(comma).append(comma).append(comma);
            carddata.append(pan.substring(pan.length() - 7, pan.length()));
            carddata.append("\r\n");
            for (int x = 0; x < totalAccount; ++x) {
                custAccount = accounts.get(x).getAccountNumber();
                String typex = "10";
                String accounttype = custAccount.substring(company.getAccountStartPosition(), company.getAccountEndPosition());
                if (company.getCurrentMapCode().lastIndexOf(accounttype) > -1) {
                    typex = "20";
                }
                custid = FileFormat.formatText((int) 9, (String) custid);
                holder = FileFormat.formatText((int) 26, (String) holder);
                sol = FileFormat.formatText((int) 4, (String) sol);
                branch = FileFormat.formatText((int) 26, (String) branch);
                pan = FileFormat.formatText((int) company.getPanSize(), (String) pan);
                if (company.isPrefixedWithU()) {
                    accountdata.append("U");
                    accountdata.append(comma);
                }
                accountdata.append(custAccount);
                accountdata.append(comma);
                accountdata.append(typex);
                accountdata.append(comma);
                accountdata.append("566");
                accountdata.append(comma);
                accountdata.append(pan.substring(pan.length() - 7, pan.length()));
                if (company.isPrefixedWithU()) {
                    balances.append("U");
                    balances.append(comma);
                }
                balances.append(custAccount);
                balances.append(comma);
                balances.append("0");
                balances.append(comma);
                balances.append("0");
                balances.append(comma);
                balances.append(typex);
                if (company.isPrefixedWithU()) {
                    cardaccount.append("U");
                    cardaccount.append(comma);
                }
                cardaccount.append(pan);
                cardaccount.append(comma);
                cardaccount.append("001");
                cardaccount.append(comma);
                cardaccount.append(custAccount);
                cardaccount.append(comma);
                cardaccount.append(typex);
                cardaccount.append(comma);
                cardaccount.append("1");
                cardaccount.append(comma);
                cardaccount.append(typex);
                accountdata.append("\r\n");
                balances.append("\r\n");
                cardaccount.append("\r\n");
            }
        }
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("ACCOUNT", accountdata.toString());
        m.put("BALANCE", balances.toString());
        m.put("CARD", carddata.toString());
        m.put("CARD_ACCOUNT", cardaccount.toString());
        return m;
    }
}
