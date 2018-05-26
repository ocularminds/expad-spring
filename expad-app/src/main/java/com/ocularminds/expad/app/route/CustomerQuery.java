package com.ocularminds.expad.app.route;

import com.ocularminds.expad.app.DuplicateNameException;
import com.ocularminds.expad.app.EmptyDataException;
import com.ocularminds.expad.app.NotFoundException;
import com.ocularminds.expad.app.ValidationError;
import com.ocularminds.expad.app.ValidationErrors;
import com.ocularminds.expad.app.service.AdminService;
import com.ocularminds.expad.app.service.CardService;
import com.ocularminds.expad.app.service.CustomerService;
import com.ocularminds.expad.common.Images;
import com.ocularminds.expad.common.XmlFile;
import com.ocularminds.expad.model.Account;
import com.ocularminds.expad.model.Card;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.model.Customer;
import com.ocularminds.expad.model.CustomizedCard;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Element;

@RestController
@RequestMapping("/api/customer")
public class CustomerQuery implements Route {

    final CardService service;
    final AdminService admin;
    final CustomerService cs;
    private final MessageSource messageSource;

    @Autowired
    public CustomerQuery(CardService cardService, AdminService adminService,
            CustomerService customerService, MessageSource messageSource) {
        this.service = cardService;
        this.admin = adminService;
        this.cs = customerService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/{customerId}", method = GET)
    public Customer customer(@PathVariable("customerId") String customerId) {
        Customer customer = cs.customer(customerId);
        return Optional.ofNullable(customer)
                .orElseThrow(() -> new NotFoundException("customer with id not found"));
    }

    @RequestMapping(value = "/account/{accountNumber}", method = GET)
    public Customer accountCustomer(@PathVariable String accountNumber) {
        try {
            Company company = admin.config();
            Customer customer = new Customer();
            if (company != null) {
                if (accountNumber.length() > company.getAccountSize()
                        || accountNumber.length() < company.getAccountSize()) {
                    customer.setName("INVALID ACCOUNT SIZE!");
                } else {
                    try {
                        Account account = cs.findAccountById(accountNumber);
                        if (account == null) {
                            throw new NotFoundException("cannot get account customer. ");
                        }
                        customer = cs.customer(account.getCustomerId());
                    } catch (NotFoundException ex) {
                        LOG.error("cannot get account customer. ", ex);
                        throw new NotFoundException("cannot get account customer. ");
                    }
                }
            }
            return Optional.ofNullable(customer).orElseThrow(
                            () -> new NotFoundException("cannot get account customer. ")
                    );

        } catch (NotFoundException ex) {
            LOG.error("cannot get account customer. ", ex);
            throw new NotFoundException("cannot get account customer. ");
        }
    }

    @RequestMapping(value = "/{customerId}/accounts", method = GET)
    public List<Account> account(@PathVariable String customerId) {
        return cs.accounts(customerId);
    }

    @RequestMapping(value = "/{customerId}/cards", method = GET)
    public List<Card> cards(@PathVariable("customerId") String customerId) {
        try {
            return service.findCardByCustomer(customerId);
        } catch (Exception ex) {
            LOG.error("cards not found for customer", ex);
            throw new NotFoundException("cards not found for customer");
        }
    }

    @RequestMapping(value = "/photo/{photoId}", method = RequestMethod.GET)
    public void getPhotoAsByteArray(HttpSession session, HttpServletResponse response,
            @PathVariable String photoId) throws IOException {
        writeImage(session, response, "photo", photoId);
    }

    @RequestMapping(value = "/signature/{photoId}", method = RequestMethod.GET)
    public void getSignatureAsByteArray(HttpSession session, HttpServletResponse response,
            @PathVariable String photoId) throws IOException {
        writeImage(session, response, "signature", photoId);
    }

    @RequestMapping(value = "/bio", method = RequestMethod.GET)
    public void bio(HttpSession session, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", -1);
        response.setContentType(MediaType.TEXT_XML_VALUE);
        StringBuffer sb = (StringBuffer) session.getAttribute("bio");
        byte[] image = Base64.getDecoder().decode(sb.toString().getBytes());
        ServletOutputStream sos = response.getOutputStream();
        sos.write(image);
    }

    @RequestMapping(value = "/bio/{id}", method = GET)
    public Map<String, String> photo(@PathVariable("id") String id) throws Exception {
        String identity = "----";
        String dob = "";
        String name = "STUDENT RECORD NOT FOUND";
        String passport = "";
        String signature = "";
        Map<String, String> record = new HashMap<>();
        CustomizedCard cc = service.findCustomizedCardById(id);
        if (cc != null) {
            name = cc.getName().replaceAll("&", " AND ");
            dob = cc.getDob();
            dob = dob == null ? "-" : dob;
            identity = cc.getNo().trim().equals("") ? "." : identity;
            passport = Base64.getEncoder().encodeToString(cc.getPhoto());
            signature = Base64.getEncoder().encodeToString(cc.getSignature());
        }
        record.put("name", name);
        record.put("dob", dob);
        record.put("identity", identity);
        record.put("passport", passport);
        record.put("signature", signature);
        record.put("photoId", id);
        return record;
    }

    private void writeImage(HttpSession session, HttpServletResponse response, String id, String typ) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", -1);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        byte[] image;
        String sb = typ.equals("photo") ? (String) session.getAttribute("passport") : (String) session.getAttribute("signature");
        String xml = new String(Base64.getDecoder().decode(sb));
        if (xml.contains("SOAP-ENV")) {
            ArrayList records = XmlFile.read(xml, "SOAP-ENV:Body", 2);
            Element e = (Element) records.get(0);
            image = Base64.getDecoder().decode(XmlFile.tagValue("SOAP-ENC:Array", e).getBytes());
        } else {
            image = Base64.getDecoder().decode((String) sb);
        }
        ServletOutputStream stream = response.getOutputStream();
        image = Optional.ofNullable(image).orElse(defaultImage(typ));
        stream.write(image);
        stream.flush();
    }

    private byte[] defaultImage(String typ) {
        String path = typ.equals("photo") ? "blank-img.jpg" : "send.gif";
        path = "resources" + File.separator + path;
        return Images.fromFile(path);
    }

    private Customer format(Customer customer) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat sdfs = new SimpleDateFormat("dd-MM-yyyy");
        String customerName = "ACCOUNT NOT FOUND";
        String requestid = "";
        String sol = "";
        String dob = "";
        String custid = "";
        if (customer != null) {
            requestid = customer.getId() + "-" + sdf.format(new Date());
            custid = customer.getId();
            customerName = customer.getName().replaceAll("&", " AND ");
            sol = customer.getSol();
            dob = customer.getDob() != null ? sdfs.format(customer.getDob()) : "";
        } else {
            customer = new Customer();
        }
        customer.setName(customerName);
        customer.setSol(sol);
        customer.setDateOfBirth(dob);
        customer.setRequestId(requestid);
        customer.setId(custid);
        return customer;
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
