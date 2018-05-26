/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.view;

import com.ocularminds.expad.Fault;
import com.ocularminds.expad.app.Credentials;
import com.ocularminds.expad.app.NotFoundException;
import com.ocularminds.expad.app.Password;
import com.ocularminds.expad.app.route.Admin;
import com.ocularminds.expad.app.service.AdminService;
import com.ocularminds.expad.app.service.CardService;
import com.ocularminds.expad.app.service.PasswordService;
import com.ocularminds.expad.app.service.UserService;
import com.ocularminds.expad.crypto.EncryptionException;
import com.ocularminds.expad.crypto.StringEncrypter;
import com.ocularminds.expad.model.Branch;
import com.ocularminds.expad.model.Function;
import com.ocularminds.expad.model.Merchant;
import com.ocularminds.expad.model.Role;
import com.ocularminds.expad.model.Script;
import com.ocularminds.expad.model.User;
import com.ocularminds.expad.vao.Company;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminPage implements Page {

    UserService service;
    AdminService admin;
    CardService cs;
    PasswordService passwords;

    static final Logger LOG = LoggerFactory.getLogger(Admin.class);

    @Autowired
    public AdminPage(AdminService adminService, UserService userService,
            CardService cardService, PasswordService passwordService) {
        this.service = userService;
        this.admin = adminService;
        this.cs = cardService;
        this.passwords = passwordService;
    }

    @RequestMapping(value = "/config", method = POST)
    public String config(HttpSession session, ModelMap model, Company company) {
        this.admin.update(company);
        model.addAttribute("config", this.admin.config());
        return config(session, model);
    }

    @RequestMapping(value = "/config", method = GET)
    public String config(HttpSession session, ModelMap model) {
        model.addAttribute("config", this.admin.config());
        return getPage(session, "config", model, new Fault());
    }

    @RequestMapping(value = "/scripts", method = GET)
    public String scripts(HttpSession session, ModelMap model) {
        try {
            model.addAttribute("script", admin.getScript());
            return getPage(session, "script", model, new Fault());
        } catch (Exception ex) {
            LOG.error("error loading scripts ", ex);
            model.addAttribute("script", new Script());
            return getPage(session, "script", model, new Fault());
        }
    }

    @RequestMapping(value = "/scripts", method = POST)
    public String scripts(HttpSession session, ModelMap model, Script script) {
        try {
            admin.update(script);
            model.addAttribute("script", admin.getScript());
            return scripts(session, model);
        } catch (Exception ex) {
            LOG.error("error loading scripts ", ex);
            model.addAttribute("script", new Script());
            return getPage(session, "script", model, new Fault("67", "error updating scripts "));
        }
    }

    @RequestMapping(value = "/users", method = GET)
    public String users(HttpSession session, ModelMap model) {
        try {
            model.addAttribute("users", service.users());
        } catch (Exception ex) {
            model.addAttribute("users", new ArrayList());
            LOG.error("error cannot load users ", ex);
        }
        return getPage(session, "users", model, new Fault());
    }

    @RequestMapping(value = "/users/{userId}", method = GET)
    public String users(HttpSession session, ModelMap model, @PathVariable String userId) {
        try {
            User user = userId.equals("0") ? new User() : service.user(userId);
            model.addAttribute("roles", service.roles());
            model.addAttribute("editableUser", user);
            model.addAttribute("branches", admin.branches());
        } catch (Exception ex) {
            LOG.error("error cannot load users ", ex);
            throw new NotFoundException("user not found");
        }
        return getPage(session, "user", model, new Fault());
    }

    @RequestMapping(value = "/users", method = POST)
    public String users(HttpSession session, ModelMap model, User user) {
        Fault fault;
        try {
            String password = user.getPassword();
            if (!Password.isAlphaNumeric(password)) {
                fault = new Fault("56", "Password must be alpha-numeric!");
            } else if (!Password.containsUpperCase(password)) {
                fault = new Fault("56", "Password must contain at least one Upper Case letter.");
            } else if (!Password.containsLowerCase(password)) {
                fault = new Fault("56", "Password must contain at least one Lower Case letter.");
            } else if (!Password.isAlphaNumeric(password)) {
                fault = new Fault("56", "Password must be alpha-numeric.");
            } else if (!Password.containsSpecialCharacter(password)) {
                fault = new Fault("56", "Password must contain at 1 special character!");
            } else {
                password = new StringEncrypter().encrypt(password);
                user.setPassword(password);
                if (service.isUserPasswordExist(user.getStaffCode(), password)) {
                    fault = new Fault("56", "Password exists already. Please use different password.");
                } else {
                    if (user.getId() != null) {
//                        audit.select(1, "SELECT * FROM  USERS  WHERE ID = '" + id + "'");
//                        service.save(usr);
//                        audit.select(2, "SELECT * FROM  USERS  WHERE ID = '" + id + "'");
//                        audit.logAuditTrail("UPDATE ON USER ", user.getBranch(), user.getStaffCode(), id);
                        fault = new Fault("00", "User Account has been updated sucessfully.");
                    } else {
                        service.save(user);
                        fault = new Fault("00", "User records sucessfully created.");
                    }

                }
            }

        } catch (EncryptionException ex) {
            fault = new Fault("56", "Password expiry field must be an integer!");
            LOG.error("Password expiry field must be an integer!", ex);
            throw new NotFoundException("user not found");
        }
        service.save(user);
        return users(session, model);
    }

    @RequestMapping(value = "/reset", method = GET)
    public String reset(HttpSession session, ModelMap model) {
        try {
            model.addAttribute("roles", service.roles());
            model.addAttribute("users", service.users());
        } catch (Exception ex) {
            model.addAttribute("users", new ArrayList());
            LOG.error("error cannot load users ", ex);
        }
        return getPage(session, "users", model, new Fault());
    }

    @RequestMapping(value = "/roles", method = POST)
    public String save(HttpSession session, ModelMap model, Role role) {
        service.save(role);
        return roles(session, model);
    }

    @RequestMapping(value = "/roles", method = GET)
    public String roles(HttpSession session, ModelMap model) {
        try {
            model.addAttribute("roles", service.roles());
        } catch (Exception ex) {
            model.addAttribute("roles", new ArrayList());
            LOG.error("error cannot load user groups ", ex);
        }
        return getPage(session, "roles", model, new Fault());
    }

    @RequestMapping(value = "/roles/{roleId}", method = GET)
    public String roles(HttpSession session, ModelMap model, @PathVariable("roleId") String roleId) {
        try {
            Role role = roleId.equals("0") ? new Role() : service.role(roleId);
            List<Function> picked = service.roleFunctions(roleId);
            List<Function> available;
            if (roleId.equals("0")) {
                available = service.functions();
            } else {
                available = service.roleFunctionsAvailable(roleId);
            }
            model.addAttribute("role", role);
            model.addAttribute("picked", picked);
            model.addAttribute("available", available);
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
        return getPage(session, "role", model, new Fault());
    }

    @RequestMapping(value = "/merchant", method = GET)
    public String merchants(HttpSession session, ModelMap model) {
        model.addAttribute("merchants", cs.merchants());
        return getPage(session, "merchants", model, new Fault());
    }

    @RequestMapping(value = "/merchant/{merchantId}", method = GET)
    public String merchant(@PathVariable String merchantId, HttpSession session, ModelMap model) {
        try {
            Merchant merchant = merchantId.equals("0") ? new Merchant() : cs.merchant(merchantId);
            model.addAttribute("merchant", merchant);
        } catch (Exception ex) {
            throw new NotFoundException("error fetching merchant details");
        }
        return getPage(session, "merchant", model, new Fault());
    }

    @RequestMapping(value = "/merchant", method = POST)
    public String merchant(HttpSession session, ModelMap model, Merchant merchant, BindingResult binding) {
        if (isNew(merchant.getId())) {
            cs.create(merchant);
        } else {
            cs.update(merchant);
        }
        return merchants(session, model);
    }

    @RequestMapping(value = "/branch", method = GET)
    public String branches(HttpSession session, ModelMap model) {
        model.addAttribute("branches", admin.branches());
        return getPage(session, "branches", model, new Fault());
    }

    @RequestMapping(value = "/branch/{branchId}", method = GET)
    public String branches(HttpSession session, ModelMap model, @PathVariable String branchId) {
        Branch branch = branchId.equals("0") ? new Branch() : admin.branch(branchId);
        model.addAttribute("branch", branch);
        return getPage(session, "branch", model, new Fault());
    }

    @RequestMapping(value = "/branch", method = POST)
    public String branches(HttpSession session, ModelMap model, Branch branch) {
        try {
            admin.createBranch(branch.getCode(), branch.getName());
            return branches(session, model);
        } catch (Exception ex) {
            throw new NotFoundException("error creating branch");
        }
    }

    @RequestMapping(value = "/password", method = GET)
    public String password(HttpSession session, ModelMap model) {
        return getPage(session, "password", model, new Fault());
    }

    @RequestMapping(value = "/password", method = POST)
    public String password(HttpSession session, ModelMap model, Credentials credentials) {
        try {
            Fault fault = passwords.execute(session, credentials);
            if(fault.isSuccess()){
                return index(model, new Fault());
            }else{
                model.addAttribute("fault", fault);
                return password(session, model);
            }
        } catch (Exception ex) {
            throw new NotFoundException("error changing password");
        }
    }

}
