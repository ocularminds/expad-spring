package com.ocularminds.expad.app.route;

import com.ocularminds.expad.app.DuplicateNameException;
import com.ocularminds.expad.app.EmptyDataException;
import com.ocularminds.expad.app.NotFoundException;
import com.ocularminds.expad.app.ValidationError;
import com.ocularminds.expad.app.ValidationErrors;
import com.ocularminds.expad.app.service.AdminService;
import com.ocularminds.expad.app.service.UserService;
import com.ocularminds.expad.model.Role;
import com.ocularminds.expad.model.User;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@RestController
@RequestMapping(value="/api/admin")
public class UserRoute implements Route{

    UserService service;
    AdminService admin;
    private final MessageSource messageSource;

    static final Logger LOG = LoggerFactory.getLogger(Admin.class);

    @Autowired
    public UserRoute(AdminService adminService, UserService userService, MessageSource messageSource) {
        this.service = userService;
        this.admin = adminService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/users", method=GET)    
    public List<User> users() {
        try {
            return service.users();
        } catch (Exception ex) {
            LOG.error("error cannot log request ", ex);
            return new ArrayList();
        }
    }

    @RequestMapping(value = "/users/{userId}", method=GET)
    @ResponseStatus(HttpStatus.OK)
    public User users(@PathVariable("userId") String userId) {
        User user;
        try {
            user = service.user(userId);
        } catch (Exception ex) {
            user = new User();
            LOG.error("error cannot log request ", ex);
        }
        return user;
    }
     
//    @RequestMapping(value = "/api/password", method=POST)
//    @ResponseStatus(HttpStatus.OK)
//    public String password(Password password, HttpSession session) {
//        return new PasswordAction(form,request).execute();
//    }

    @RequestMapping(value = "/roles", method=GET)
    public List<Role> roles() {
        try {
            return service.roles();
        } catch (Exception ex) {
            LOG.error("", ex);
            return new ArrayList();
        }
    }
    
    @RequestMapping(value = "/roles", method=POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(Role role) {
         //service.save(role);
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
