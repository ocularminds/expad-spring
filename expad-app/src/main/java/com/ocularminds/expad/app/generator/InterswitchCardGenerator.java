package com.ocularminds.expad.app.generator;

import com.ocularminds.expad.common.CardUtil;
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

public final class InterswitchCardGenerator implements CardGenerator {

    final Product product;
    final String branchCode;
    final Company company;
    private SecureRandom rand;
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    static final Logger LOG = LoggerFactory.getLogger(InterswitchCardGenerator.class);

    public InterswitchCardGenerator(Company company, Product prod, String branch) {
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
        String date = this.sdf.format(new java.util.Date());
        String strDate = sdf.format(new Dates().months(36));
        String expiry = strDate.substring(8, 10) + strDate.substring(3, 5);
        boolean paydirect = !product.getName().toLowerCase().contains("direct");
        String xbin = product.getBin();
        String[] data = CardUtil.newPan(this.rand, company, xbin, branchCode, product.getCode(), "", product.getFormat(), paydirect);
        String pan = data[0];
        String offset = data[1];
//        if (new Pans().exists(pan)) {
//            data = CardUtil.newPan(this.rand, company, xbin, product.getCode(), branchCode, network, product.getFormat(), paydirect);
//            pan = data[0];
//            offset = data[1];
//        }

        Pan p = new Pan(serial(rand), pan, expiry, offset, "D", branchCode, product.getId());
        return p;
    }
}
