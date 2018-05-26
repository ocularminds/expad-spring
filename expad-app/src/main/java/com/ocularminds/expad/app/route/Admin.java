package com.ocularminds.expad.app.route;

import com.ocularminds.expad.app.DuplicateNameException;
import com.ocularminds.expad.app.EmptyDataException;
import com.ocularminds.expad.app.NotFoundException;
import com.ocularminds.expad.app.ValidationError;
import com.ocularminds.expad.app.ValidationErrors;
import com.ocularminds.expad.app.service.AdminService;
import com.ocularminds.expad.app.service.CardService;
import com.ocularminds.expad.model.Branch;
import com.ocularminds.expad.model.Merchant;
import com.ocularminds.expad.vao.Company;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value="/api/admin")
public class Admin implements Route {

    CardService service;
    AdminService admin;
    private final MessageSource messageSource;

    static final Logger LOG = LoggerFactory.getLogger(Admin.class);

    @Autowired
    public Admin(AdminService adminService, CardService cardService, MessageSource messageSource) {
        this.service = cardService;
        this.admin = adminService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/config", method=GET)
    public Company config() {
        return admin.config();
    }

   
    @RequestMapping(value = "/config", method=POST)
    public void update(Company company) {
        admin.update(company);
    }
    
    @RequestMapping(value = "/api/branch", method=GET)
    public List<Branch> branches() {
        try {
            return admin.branches();
        } catch (Exception ex) {
            LOG.error("error cannot log request ", ex);
            return new ArrayList();
        }
    }

    @RequestMapping(value = "/branch/{code}", method=GET)
    public Branch branch(@PathVariable String code) {
        Branch branch;
        try {
            branch = admin.branch(code);
        } catch (Exception ex) {
            branch = new Branch();
            LOG.error("error cannot log request ", ex);
        }
        return branch;
    }

    @RequestMapping(value = "/branch", method=POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(Branch branch) {
        try {
            //admin.save(branch);
        } catch (Exception ex) {
            branch = new Branch();
            LOG.error("error cannot log request ", ex);
        }
    }

    @RequestMapping(value = "/branch/{branchId}", method=PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(Branch branch) {
        try {
            //admin.save(branch);
        } catch (Exception ex) {
            branch = new Branch();
            LOG.error("error cannot log request ", ex);
        }
    }

    @RequestMapping(value = "/merchant",method=GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Merchant> merchants() {
        try {
            return service.merchants();
        } catch (Exception ex) {
            LOG.error("", ex);
            return new ArrayList();
        }
    }

    @RequestMapping(value = "/merchant/{id}", method=GET)
    @ResponseStatus(HttpStatus.OK)
    public Merchant merchants(@PathVariable String id) {
        try {           
            return service.merchant(id);
        } catch (Exception ex) {
            LOG.error("", ex);
            return new Merchant();
        }
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
