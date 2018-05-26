
package com.ocularminds.expad.svc.builder;

import com.ocularminds.expad.svc.FileNames;
import com.ocularminds.expad.service.AdminService;
import com.ocularminds.expad.service.CardService;
import com.ocularminds.expad.service.PostCardService;
import com.ocularminds.expad.service.ServiceLocator;
import com.ocularminds.util.FileFormat;
import com.ocularminds.util.JavaCompress;
import com.ocularminds.expad.repository.Branches;
import com.ocularminds.expad.repository.Cards;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.repository.Configurator;
import com.ocularminds.expad.vao.Pan;
import com.ocularminds.expad.vao.Product;
import com.ocularminds.expad.repository.Products;
import static com.ocularminds.expad.svc.FileNames.expiryDate;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PersoFileBuilder extends FileNames {
    
    static final Logger LOG = Logger.getLogger(PersoFileBuilder.class.getName());

    public static String buildCardFile(List list) throws Exception {
        AdminService as = (AdminService) ServiceLocator.locate(AdminService.class);
        CardService cs = (CardService) ServiceLocator.locate(CardService.class);
        Map<String, String> branches = as.mappedBranches();
        Company company = as.config();
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date());
        StringBuilder data = new StringBuilder();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        LinkedList<Future<StringBuilder>> tasks = new LinkedList<Future<StringBuilder>>();
        for (int x = 0; x < list.size(); x++) {
            tasks.add(executor.submit(createTask((Pan) list.get(x), cs, company, date, branches)));
        }
        for (Future<StringBuilder> f : tasks) {
            try {
                data.append(f.get());
            } catch (InterruptedException | ExecutionException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        return data.toString();
    }

    public static void buildFile(List list, String repository) throws Exception {
        Company company = new Configurator().get();
        Branches branches = new Branches();
        Cards cards = new Cards();
        Products products = new Products();
        String downloadFolder = "C:\\ExPad\\Downloads\\Pan";
        String blankName = FileFormat.formatText((int) 26, (String) " ");
        String blankBranch = FileFormat.formatText((int) 26, (String) " ");
        StringBuilder data = new StringBuilder();
        StringBuilder pins = new StringBuilder();
        for (int index = 0; index < list.size(); ++index) {
            Pan cardPan = (Pan) list.get(index);
            String pan = cardPan.getNo();
            expiryDate = cardPan.getExpiry();
            blankBranch = cardPan.getBranchCode();
            Product product = products.get(cardPan.getProductCode());
            String PIN_OFFSET = cardPan.getOffset();
            revExpiry = expiryDate.substring(2, 4) + "/" + expiryDate.substring(0, 2);
            expiryDate = expiryDate.replaceAll("/", "");
            String serialno = cardPan.getId();
            pins.append(serialno);
            pins.append(",");
            pins.append(blankName);
            pins.append(",");
            pins.append(blankBranch);
            pins.append(",");
            String scrambledPan = pan.substring(0, 10) + pan.substring(pan.length() - 6, pan.length());
            pins.append(scrambledPan);
            pins.append(",");
            pins.append(PIN_OFFSET);
            pins.append("\r\n");
            data.append(serialno);
            data.append(",");
            data.append(pan);
            data.append(",,,");
            data.append(revExpiry);
            data.append(",");
            data.append(blankName);
            data.append(",");
            data.append(pan);
            data.append("=");
            data.append(expiryDate);
            data.append(product == null ? "101" : product.getInActiveCardCode());
            data.append(PIN_OFFSET);
            data.append(",");
            data.append(blankBranch);
            data.append("\r\n");
        }
        String persoData = data.toString();
        String pinData = pins.toString();
        try {
            String cardData = PersoFileBuilder.buildCardFile(list);
            FileNames.createDirectory(downloadFolder, true);
            FileNames.writeFile(downloadFolder, FileNames.PERSO_FILE, persoData);
            FileNames.writeFile(downloadFolder, FileNames.PIN_FILE, pinData);
            FileNames.writeFile(downloadFolder, FileNames.CARDS_FILE, cardData);
            FileNames.writeFile(downloadFolder, FileNames.ACCOUNTS_FILE, "");
            FileNames.writeFile(downloadFolder, FileNames.ACCOUNT_OVERRIDES_FILE, "");
            FileNames.writeFile(downloadFolder, FileNames.CARD_OVERRIDES_FILE, "");
            FileNames.writeFile(downloadFolder, FileNames.ACCOUNT_BALANCES_FILE, "");
            FileNames.writeFile(downloadFolder, FileNames.CARD_ACCOUNTS_FILE, "");
            FileNames.writeFile(downloadFolder, FileNames.STATEMENTS_FILE, "");
            if (list.size() > 0) {
                JavaCompress.zipDirectory(downloadFolder, repository);
            }
        } catch (Exception e) {
            String errorMessage = "WARN: Error occured while writing to batchFile:\n";
            System.out.println(errorMessage + e);
        }
    }

    private static Callable<StringBuilder> createTask(final Pan request, final CardService cs, final Company config, final String date, final Map<String, String> branches) {
        return new Callable<StringBuilder>() {
            @Override
            public StringBuilder call() {
                StringBuilder data = new StringBuilder();
                String pan = request.getNo();
                String productCode = request.getProductCode();
                Product product = cs.product(productCode);
                String PIN_OFFSET = request.getOffset();
                String preferedName = request.getId();
                String cardProgram = product != null ? product.getName() : " ";
                String currencyCode = product != null ? product.getCurrencyCode() : "NGN";
                String trailingText = product != null ? product.getTrailingText() : "pppp";
                String serialno = request.getId();
                expiryDate = request.getExpiry().replaceAll("/", "");
                revExpiry = expiryDate.substring(2, 4) + expiryDate.substring(0, 2);
                String sol = request.getBranchCode();
                preferedName = FileFormat.formatText((int) 26, (String) preferedName);
                currencyCode = FileFormat.formatText((int) 3, (String) currencyCode);
                pan = FileFormat.formatText((int) config.getPanSize(), (String) pan);
                preferedName = FileFormat.formatText((int) 26, (String) preferedName);
                sol = FileFormat.formatText((int) 3, (String) sol);
                String branch = branches.get(sol);
                branch = FileFormat.formatText((int) 26, (String) branch);
                String firstName = "";
                String secondName = "";
                String type = "10";
                data.append("U");
                data.append(",");
                data.append(pan);
                data.append(",");
                data.append("001");
                data.append(",");
                data.append(cardProgram);
                data.append(",");
                data.append(type);
                data.append(",");
                data.append("6");
                data.append(",");
                data.append(",");
                data.append(expiryDate);
                data.append(",,,,");
                data.append("4");
                data.append(",");
                data.append(PIN_OFFSET);
                data.append(",");
                data.append(firstName);
                data.append(",");
                data.append(secondName);
                data.append(",");
                data.append(",");
                data.append(",");
                data.append(",,,,");
                data.append(preferedName);
                data.append(",");
                data.append(branch.concat(",,"));
                data.append(",,,".concat(currencyCode));
                data.append(",,");
                data.append("1");
                data.append(",,");
                data.append("0");
                data.append(",,");
                data.append(trailingText);
                data.append(",");
                data.append(date);
                data.append(",");
                data.append("1900-01-01 00:00:00.000");
                data.append(",,");
                data.append(",");
                data.append(sol);
                data.append(",");
                data.append(serialno);
                data.append("\r\n");
                return data;
            }
        };
    }
}
