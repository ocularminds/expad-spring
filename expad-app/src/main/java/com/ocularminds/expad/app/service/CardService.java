package com.ocularminds.expad.app.service;

import com.ocularminds.expad.app.CardGenerators;
import com.ocularminds.expad.app.aop.ShouldBeAudited;
import com.ocularminds.expad.common.Dates;

import com.ocularminds.expad.model.Card;
import com.ocularminds.expad.app.repository.Cards;
import com.ocularminds.expad.app.repository.Configurator;
import com.ocularminds.expad.model.CustomizedCard;
import com.ocularminds.expad.vao.LinkedAccount;
import com.ocularminds.expad.model.MailExtractor;
import com.ocularminds.expad.model.Merchant;
import com.ocularminds.expad.app.repository.Merchants;
import com.ocularminds.expad.app.repository.Pans;
import com.ocularminds.expad.app.repository.Products;
import com.ocularminds.expad.vao.Pan;
import com.ocularminds.expad.vao.Product;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final Dates DF = new Dates();
    private final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
    private final Cards cards;
    private final Merchants merchants;
    private final Pans pans;
    private final Products products;
    private final Configurator configurator;
    final static Logger LOG = LoggerFactory.getLogger(CardService.class);

    @Autowired
    public CardService(Cards cards, Pans pans, Merchants merchants, Products products, Configurator configurator) {
        this.cards = cards;
        this.pans = pans;
        this.merchants = merchants;
        this.products = products;
        this.configurator = configurator;
    }

    @ShouldBeAudited
    public void save(Product product) {
        if (product != null) {
            if (product.getId() == null || product.getId().isEmpty()) {
                products.add(product);
            } else {
                products.update(product);
            }
        }
    }

    public List<Product> products() {
        try {
            return products.findAll();
        } catch (Exception ex) {
            LOG.error("error listing products ", ex);
            return new ArrayList();
        }
    }

    public Map<String, Product> mappedProducts() {
        try {
            return products.mapped();
        } catch (Exception ex) {
            return new HashMap<>();
        }
    }

    public Product product(final String id) {
        try {
            return products.get(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean isCardExisting(String account) throws Exception {
        return this.cards.exists(account);
    }

    public void create(Card card) {
        this.cards.add(card);
    }

    public void create(CustomizedCard customizedCard) {
        try {
            this.cards.add(customizedCard);
        } catch (Exception e) {
            LOG.error("[EXPAD] Error creating customizedCard record ->", e);
        }
    }

    public void notifyBatchDownload(List cards) {
        try {
            this.cards.notifyBatchDownload(cards);
        } catch (Exception e) {
            LOG.info("error notifying batch dowload " + e.getMessage());
        }
    }

    public void updateDebitCard(String id, String preferedName, String phone, String email, String collectingSol) throws Exception {
        cards.updateCard(id, email, phone, email, phone, id);
    }

    public void addLinkedAccount(String id, String accountno, String pan) {
        this.cards.addLinkedAccount(id, accountno, pan);
    }

    public void approveCard(String[] ids, String userid) throws Exception {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.approveCard(ids[x], userid);
            }
        }
    }

    public void rejectCard(String[] ids, String userid) throws Exception {
        cards.rejectCard(ids, userid);
    }

    public void updateCard(String id, String status, String custname, String email, String phone, String userid) throws Exception {
        cards.updateCard(id, status, custname, email, phone, userid);
    }

    public void finalizeCard(String no, String custid) throws Exception {
        cards.finalizeCard(no, custid);
    }

    public void updateCard(String id, String userid, String status) throws Exception {
        cards.updateCard(id, userid, status);
    }

    public void blockCard(String pan, String userid) throws Exception {
        cards.blockCard(pan, userid);
    }

    private void approveCard(String id, String userid) throws Exception {
        cards.approveCard(new String[]{id}, userid);
    }

    public void notifyPrintedCard(String pan, String userid) throws Exception {
        cards.notifyPrintedCard(pan, userid);
    }

    private void rejectCard(String id, String userid) throws Exception {
        cards.rejectCard(new String[]{id}, userid);
    }

    public void removeProcessedPan(String pan) throws Exception {
        cards.removeProcessedPan(pan);
    }

    public void approveReviewedCard(String id, String userid) throws Exception {
        cards.approveReviewedCard(id, userid);
    }

    public void approveBulkCard(String id, String userid) throws Exception {
        cards.approveBulkCard(id, userid);
    }

    public void approveBulkCard(String[] ids, String userid) throws Exception {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.approveBulkCard(ids[x], userid);
            }
        }
    }

    public void deleteDebitCardCard(String[] ids) throws Exception {
        if (ids != null) {
            for (int x = 0; x < ids.length; ++x) {
                this.deleteDebitCard(ids[x]);
            }
        }
    }

    public void notifyPinMailer(String batchid, String expiry) {
        this.cards.notifyPinMailer(batchid, expiry);
    }

    public void deleteDebitCard(String id) throws Exception {
        cards.deleteDebitCard(id);
    }

    public void notifyDownloadedDebitCardByPan(String pan) throws Exception {
        cards.notifyDownloadedDebitCardByPan(pan);
    }

    public void notifyDownloadedDebitCard(List<String> cardIds) {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(processors);
        cardIds.forEach(id -> service.execute(() -> {
            try {
                cards.notifyDownloadedDebitCard(id);
            } catch (Exception ex) {
                LOG.error("error updating card with id:{} ", id, ex);
            }
        }));
        service.shutdown();
    }

    public void notifyDownloadedDebitCard(String id) throws Exception {
        cards.notifyDownloadedDebitCard(id);
    }

    public void sendBulkMessage(List groupMails, String messageContent) {
        for (int x = 0; x < groupMails.size(); ++x) {
            MailExtractor me = (MailExtractor) groupMails.get(x);
            String[] to = new String[]{me.getEmail()};
            String subject = "EXPAD NOTIFICATIONS !";
            String from = "info@ocular-minds.com";
            this.sendMessage(to, from, subject, messageContent);
        }
    }

    public void sendMessage(String[] to, String from, String subject, String messageContent) {
        //this.mailer.sendMail(from, to, this.company.getMailServer(), subject, messageContent);
    }

    public void notifyBatchStatus(String batchid, String status) throws Exception {
        cards.notifyBatchStatus(batchid, status);
    }

    public void createBatch(String id, String userid) {
        this.cards.createBatch(id, userid);
    }

    public List<Card> findCardExpiring() throws Exception {
        return this.cards.expiring();
    }

    public boolean isCardProcessed(String pan) throws Exception {
        return this.pans.exists(pan);
    }

    public Card findCardByNo(String no) throws Exception {
        return this.cards.find(no);
    }

    public List findCardByAccountNoRetList(String accountno) throws Exception {
        return this.cards.findCardByAccountNoRetList(accountno);
    }

    public Card findCardByAccountNo(String accountno) throws Exception {
        return this.cards.findCardByAccountNo(accountno);
    }

    public Card findCardById(String id) throws Exception {
        return this.cards.get(id);
    }

    public Card findCardBySerialNo(String id) throws Exception {
        return this.cards.findCardBySerialNo(id);
    }

    public List findCustomizedCardRecordForDownload(List ids) throws Exception {
        return this.cards.findCustomizedCardRecordForDownload(ids);
    }

    public CustomizedCard findCustomizedCardById(String id) throws Exception {
        return this.cards.findCustomizedCardById(id);
    }

    public List findCardByCustomer(String custid) throws Exception {
        return this.cards.findCardByCustomer(custid);
    }

    public List findCardByCustomerName(String name) throws Exception {
        return this.cards.findCardByCustomerName(name);
    }

    public List findCardForDownload() throws Exception {
        return this.cards.findCardForDownload();
    }

    public List findCardForDownload(String type) throws Exception {
        if (type != null) {
            if (type.equalsIgnoreCase("V")) {
                return findViaCardForDownload();
            } else if (type.equalsIgnoreCase("S")) {
                return findNyscCardForDownload();
            } else if (type.equalsIgnoreCase("N")) {
                return findNyscCardForDownload();
            } else {
                return findNormalCardForDownload();
            }
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public List findViaCardForDownload() throws Exception {
        return this.cards.findViaCardForDownload();
    }

    public List findNormalCardForDownload() throws Exception {
        return this.cards.findNormalCardForDownload();
    }

    public List findStudentCardForDownload() throws Exception {
        return this.cards.findStudentCardForDownload();
    }

    public List findStudentCardForDownload(String school) throws Exception {
        return this.cards.findStudentCardForDownload(school);
    }

    public List findNyscCardForDownload() throws Exception {
        return this.cards.findNyscCardForDownload();
    }

    public List findValCardForDownload() throws Exception {
        return this.cards.findValCardForDownload();
    }

    public List findBatchCardForApproval() throws Exception {
        return this.cards.findBatchCardForApproval();
    }

    public List<Card> findBranchCardForApproval(String branchCode) throws Exception {
        return this.cards.findBranchCardForApproval(branchCode);
    }

    public List findCardForIssue(String branchCode) throws Exception {
        return this.cards.findCardForIssue(branchCode);
    }

    public List findCardByBatch(String batchid) throws Exception {
        return this.cards.findCardByBatch(batchid);
    }

    public List findPanForDownload() throws Exception {
        return this.cards.findPanForDownload();
    }

    public List findPanForDownload(String productCode) throws Exception {
        return this.cards.findPanForDownload(productCode);
    }

    public Pan findPanByNo(String no) throws Exception {
        return this.pans.find(no);
    }

    public LinkedAccount[] findDebitCardLinkAccounts(String requestid) throws Exception {
        List<LinkedAccount> list = this.cards.accounts(requestid);
        return list.toArray(new LinkedAccount[list.size()]);
    }

    public List findTemporaryCardByQuery(String filter) throws Exception {
        return this.cards.findTemporaryCardByQuery(filter);
    }

    public List merchants() {
        try {
            return this.merchants.findAll();
        } catch (Exception ex) {
            return new ArrayList();
        }
    }

    public Merchant merchant(String id) throws Exception {
        try {
            return this.merchants.get(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean create(Merchant merchant) {
        this.merchants.add(merchant.getName(), merchant.getAcronymn(), merchant.getLocation());
        return true;
    }

    @ShouldBeAudited
    public void update(Merchant merchant) {
        this.merchants.update(merchant.getId(), merchant.getName(), merchant.getAcronymn(), merchant.getLocation());
    }

    public boolean isDuplicateMerchant(String shortName) throws Exception {
        return this.merchants.exists(shortName);
    }

    public String describeStatus(String status) throws Exception {
        return this.cards.status(status);
    }

    public void generatePanForCards(int quantity, String[] branches, String productCode, String network) throws Exception {
        int distributions = quantity / branches.length;
        Product product = products.find(productCode);
        ExecutorService service = Executors.newFixedThreadPool(4);
        if (quantity > 0) {
            for (int x = 0; x < quantity; ++x) {
                int sequence = x / distributions;
                service.submit(createTask(sequence, branches, product, network));
            }
            service.shutdown();
        }
    }

    private Runnable createTask(final int sequence, final String[] branches, final Product product, final String network) {
        return () -> {
            String branchCode = sequence > branches.length - 1 ? branches[branches.length - 1] : branches[sequence];
            try {
                Pan pan = CardGenerators.valueOf(network.toUpperCase())
                        .generate(configurator.get(), product, branchCode);
                pans.add(pan);
                Thread.sleep(100);
            } catch (Exception ex) {
                LOG.info("Thread interrupted:" + ex);
            }
        };
    }

    public void notifyDownloadRecords(List list) throws Exception {
        for (int j = 0; j < list.size(); ++j) {
            this.notifyDownloadedDebitCard(((Card) list.get(j)).getId());
        }
    }

    public void notifyProcessedPan(String pan) {
        // cards.notifyp
    }

    public boolean isPanExist(String pan) throws Exception {
        return this.pans.exists(pan);
    }

    public List showCardSummary(String filter) throws Exception {
        return this.cards.summary(filter);
    }

    public List showBranchDistribution() throws Exception {
        return this.cards.distribution();
    }
}
