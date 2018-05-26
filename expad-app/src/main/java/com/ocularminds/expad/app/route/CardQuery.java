/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.route;

import com.ocularminds.expad.Fault;
import com.ocularminds.expad.app.DuplicateNameException;
import com.ocularminds.expad.app.EmptyDataException;
import com.ocularminds.expad.app.NotFoundException;
import com.ocularminds.expad.app.ValidationError;
import com.ocularminds.expad.app.ValidationErrors;
import com.ocularminds.expad.app.service.AdminService;
import com.ocularminds.expad.app.service.CardService;
import com.ocularminds.expad.crypto.Crypto;
import com.ocularminds.expad.model.Card;
import com.ocularminds.expad.vao.LinkedAccount;
import com.ocularminds.expad.vao.Pan;
import com.ocularminds.expad.vao.Product;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@RestController
@RequestMapping(value = "/api/cards")
public class CardQuery implements Route {

    @Value("{expad.key.dir}")
    String keyDir;

    AdminService admin;
    CardService service;
    private final MessageSource messageSource;

    @Autowired
    public CardQuery(AdminService adminService, CardService cardService, MessageSource messageSource) {
        this.admin = adminService;
        this.service = cardService;
        this.messageSource = messageSource;
    }
    
    @RequestMapping(value = "/{num}", method = GET)
    public Pan getPan(@PathVariable("num") String num) {
        try {
            return service.findPanByNo(num);
        } catch (Exception ex) {
            Pan p = new Pan();
            p.setOffset("Not found");
            return p;
        }
    }

    @RequestMapping(value = "/{pan}/{branch}", method = GET)
    public Pan blankCard(@PathVariable("pan") String pan, @PathVariable("branch") String branch) {
        try {
            Pan p = service.findPanByNo(pan);
            if (p != null) {
                if (!branch.equalsIgnoreCase(p.getBranchCode())) {
                    p.setOffset("CARD NOT FOR THIS BRANCH");
                }
            } else {
                p = new Pan();
                p.setExpiry("NA");
                p.setOffset("INVALID CARD");
            }
            return p;
        } catch (Exception ex) {
            LOG.error("error in find by pan ", ex);
            return new Pan();
        }
    }

    @RequestMapping(value = "/products", method = GET)
    public List<Product> products() {
        return service.products();
    }

    @RequestMapping(value = "/comboProducts", method = GET)
    public String selectProducts() {
        List<Product> products = service.products();
        StringBuilder sb = new StringBuilder();
        sb.append("<option value=''>--select merchant--</option>");
        products.forEach((product) -> {
            sb.append("<option value=\"").append(product.getId()).append("\">").append(product.getName()).append("</option>");
        });
        return sb.toString();
    }

    @RequestMapping(value = "/products", method = POST)
    public void product(Product product) {
        service.save(product);
    }

    @RequestMapping(value = "/products/{id}", method = GET)
    public Product product(@PathVariable String id) {
        return service.product(id);
    }

    @RequestMapping(value = "/download/{type}", method = GET)
    public List<Card> downloadableCards(@PathVariable String type) {
        try {
            LOG.info("Fetching cards....of type {}", type);
            List<Card> records = service.findCardForDownload(type);
            LOG.info("Fetching cards....result  {}", records);
            return records == null ? Collections.EMPTY_LIST : records;
        } catch (Exception ex) {
            LOG.error("Error fetching cards for download ", ex);
            throw new NotFoundException("Error fetching cards for download ");
        }
    }

    @RequestMapping(value = "/download", method = PUT)
    public void product(List processed) {
        service.notifyDownloadedDebitCard(processed);
    }

    @RequestMapping(value = "/order", method = POST)
    public Fault add(Card card) throws Exception {
        Fault fault = new Fault("00", "Completed Successfully");
        if (card.getPan() != null && service.isCardProcessed(card.getPan())) {
            fault = new Fault("10", "Card Information has already been processed!");
            return fault;
        }
        List<String> accounts = card.getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            fault = new Fault("12", "Please select at least 1 account to link!");
            return fault;
        }
        if (card.getCustType().equals("S")) {
            if (card.getMerchant() == null || card.getMerchant().equalsIgnoreCase("NA")) {
                fault = new Fault("39", "Process not Completed. Please select School or Association");
                return fault;
            }
        }
        service.create(card);
        return fault;
    }

    @RequestMapping(value = "/approve", method = POST)
    public Fault approve(HashMap form) {
        String userid = (String) form.get("userid");
        String operation = (String) form.get("operation");
        List<String> id = (List) form.get("id");
        String[] ids = id.toArray(new String[id.size()]);
        try {
            if (operation.equalsIgnoreCase("Approve")) {
                service.approveCard(ids, userid);
            } else {
                service.rejectCard(ids, userid);
            }
        } catch (Exception ex) {
            LOG.error("", ex);
            return new Fault("90", "Unable to complete approval process.");
        }
        return new Fault("00", "Completed Successfully");
    }

    @RequestMapping(value = "/approval/{id}", method = GET)
    public Fault approval(@PathVariable String id) {
        try {
            LinkedAccount[] accounts = service.findDebitCardLinkAccounts(id);
            Fault fault = new Fault("00", "Completed Successfully", service.findCardById(id));
            StringBuilder sb = new StringBuilder();
            for (LinkedAccount account : accounts) {
                sb.append(String.format("<li>%s</li>", account.getAccountNumber()));
            }
            fault.setValue(sb.toString());
            return fault;
        } catch (Exception ex) {
            LOG.error("", ex);
            return new Fault("90", "Unable to complete approval process.");
        }
    }

    @RequestMapping(value = "/review", method = POST)
    public Fault review(HashMap form) {
        String userid = (String) form.get("userid");
        String operation = (String) form.get("operation");
        List<String> id = (List) form.get("id");
        String[] ids = id.toArray(new String[id.size()]);
        try {
            if (operation.equalsIgnoreCase("Approve")) {
                service.approveCard(ids, userid);
            } else {
                service.rejectCard(ids, userid);
            }
        } catch (Exception ex) {
            LOG.error("", ex);
            return new Fault("90", "Unable to complete approval process.");
        }
        return new Fault("00", "Completed Successfully");
    }

    @RequestMapping(value = "/crypto/{pan}")
    public Fault crypto(@PathVariable("pan") String pan) throws Exception {
        Crypto crypto = new Crypto();
        crypto.generate("RSA", 512);
        PrivateKey privateKey = crypto.getPrivate(keyDir + File.separator + "duk_private.key");
        PublicKey publicKey = crypto.getPublic(keyDir + File.separator + "duk_public.key");

        String encrypted_msg = crypto.encryptText(pan, privateKey);
        String decrypted_msg = crypto.decryptText(encrypted_msg, publicKey);

        LOG.info("pan:{} Encrypted :{} Decrypted : ", pan, encrypted_msg, decrypted_msg);
        return new Fault("00", "Success", encrypted_msg);
    }

    @ExceptionHandler(EmptyDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound() {
        // No-op, return empty 404
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFoundException() {
        // No-op, return empty 404
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Override
    public ValidationErrors processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return validate(fieldErrors, messageSource);
    }

    /**
     *
     * @param e Duplicate Exception
     * @return
     */
    @ExceptionHandler(DuplicateNameException.class)
    @Override
    public ResponseEntity<ValidationError> duplicateException(final DuplicateNameException e) {
        return error(e, HttpStatus.TOO_MANY_REQUESTS, e.getMessage());
    }

}
