/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.service;

import com.ocularminds.expad.Fault;
import com.ocularminds.expad.app.Credentials;
import com.ocularminds.expad.app.Password;
import com.ocularminds.expad.crypto.EncryptionException;
import com.ocularminds.expad.crypto.StringEncrypter;
import com.ocularminds.expad.model.User;
import com.ocularminds.expad.vao.Company;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@Service
public class PasswordService {

    final UserService service;
    final AdminService admin;
    static final Logger LOG = LoggerFactory.getLogger(PasswordService.class);

    @Autowired
    public PasswordService(final UserService userService, final AdminService adminService) {
        this.service = userService;
        this.admin = adminService;
    }

    public Fault execute(HttpSession session, Credentials credentials) {
        Fault fault;
        User user = (User) session.getAttribute("user");
        fault = verify(user);
        if (fault.isFailed()) {
            return fault;
        }

        String userid = user.getStaffCode();
        String hiddenPassword = user.getPassword();//form.getFirst("hiddenPass");
        String oldPassword = credentials.getPassword();
        String password = credentials.getPassword1();
        String password2 = credentials.getPassword2();

        fault = validate(password, password2);
        if (fault.isFailed()) {
            return fault;
        }
        fault = authenticate(userid, user, password, oldPassword, hiddenPassword);
        return fault;
    }

    private Fault verify(User user) {
        try {
            String code = user.getStaffCode();
            if (this.service.role(user.getRole()).isDisabled()) {
                return new Fault("10", "Your account has been disabled.");
            }
            if (this.service.isUserDisabled(code)) {
                return new Fault("10", "Your account has been disabled!");
            }
        } catch (Exception ex) {
            return new Fault("10", "Your session has expied! Please login again.");
        }
        return new Fault("00", "Ok");
    }

    private Fault validate(String password, String password2) {

        try {
            Company company = admin.getCompany();
            if (password.equals("") && password2.equals("")) {
                return new Fault("10", "Password fields cannot be empty.");
            } else if (!password.equals(password2)) {
                return new Fault("10", "Password and confirm password are not the same.");
            } else if (password.length() < (company.getMinimumPassword())) {
                return new Fault("10", "Password doest not meet " + company.getMinimumPassword()
                        + " characters minimum password! ");
            } else if (!Password.isAlphaNumeric(password)) {
                return new Fault("10", "Password must be alpha-numeric!");
            } else if (!Password.containsUpperCase(password)) {
                return new Fault("10", "Password must contain at least one Upper Case letter.");
            } else if (!Password.containsLowerCase(password)) {
                return new Fault("10", "Password must contain at least one Lower Case letter.");
            } else if (!Password.containsSpecialCharacter(password)) {
                return new Fault("10", "Password must contain at 1 special character!");
            } else {
                return new Fault("00", "Ok");
            }
        } catch (Exception er) {
            LOG.error("WARN : Error encrypting password >>", er);
            return new Fault("10", "Error verifying password");
        }
    }

    private Fault authenticate(String userid, User user, String pass, String oldPass, String hiddenPass) {
        try {
            StringEncrypter se = new StringEncrypter();
            oldPass = se.encrypt(oldPass);
            pass = se.encrypt(pass);
            if (!oldPass.equals(hiddenPass)) {
                return new Fault("10", "Incorrect Old Password!");
            } else if (oldPass.equals(pass)) {
                return new Fault("10", "Both Old and New password cannot be the same.");
            } else if (service.isUserPasswordExist(user.getId(), pass)) {
                return new Fault("10", "Password exists already. Please use different password.");
            } else {
                service.changePassword(userid, pass);
                // service.notifyFirstLogin(userid);
                return new Fault("00", "Password Change completed sucessfully");
            }
        } catch (EncryptionException er) {
            LOG.error("Error encrypting password >>", er);
            return new Fault("10", "Error verifying password");
        }
    }
}
