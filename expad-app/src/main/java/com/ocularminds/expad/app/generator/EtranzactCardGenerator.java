package com.ocularminds.expad.app.generator;

import com.ocularminds.expad.common.Dates;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ocularminds.expad.CardGenerator;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.Pan;
import com.ocularminds.expad.vao.Product;

public final class EtranzactCardGenerator implements CardGenerator {

    final Product product;
    final String branchCode;
    final Company company;
    private SecureRandom rand;
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    static final Logger LOG = LoggerFactory.getLogger(EtranzactCardGenerator.class);

    public EtranzactCardGenerator(Company company, Product prod, String branch) {
        this.product = prod;
        this.branchCode = branch;
        this.company = company;
        try {
            this.rand = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException nsae) {
            LOG.error(nsae.toString(), nsae);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Pan generate() {
        String strDate = sdf.format(new Dates().months(36));
        String expiry = strDate.substring(8, 10) + strDate.substring(3, 5);
        String[] data = new String[2];
        HostRes response = new Etranzact().createCard(branchCode);
        String error = response.getParameter("error");
        if (error.equals("00")) {
            data[0] = response.getParameter("PAN");
            data[1] = response.getParameter("EXPIRY");
        }

        Pan p = new Pan(serial(rand), data[0], expiry, data[1], "D", branchCode, product.getId());
        return p;
    }
}
