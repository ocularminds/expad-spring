/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.service;

import com.ocularminds.expad.Fault;
import com.ocularminds.expad.app.Module;
import com.ocularminds.expad.app.Credentials;
import com.ocularminds.expad.common.Dates;
import com.ocularminds.expad.crypto.EncryptionException;
import com.ocularminds.expad.crypto.StringEncrypter;
import com.ocularminds.expad.app.repository.Branches;
import com.ocularminds.expad.model.Function;
import com.ocularminds.expad.model.Role;
import com.ocularminds.expad.model.User;
import com.ocularminds.expad.vao.Company;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jejelowo B. Festus <festus.jejelowo@ocularminds.com>
 */
@Service
public final class Authenticator {

    String userid;
    String password;
    AuditService audits;
    AdminService admin;
    UserService users;
    Branches branches;
    static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
    static final Dates DTF = new Dates();
    static final Logger LOG = LoggerFactory.getLogger(Authenticator.class);
    ConcurrentHashMap<String, Integer> logins;

    @Autowired
    public Authenticator(Branches branches, AuditService auditService, AdminService adminService, UserService userService) {
        audits = auditService;
        admin = adminService;
        users = userService;
        this.branches = branches;
    }

    public Fault authenticate(HttpSession session, Credentials credentials) {
        Fault fault;
        try {
            this.userid = credentials.getLogin();
            this.password = credentials.getPassword();
            String ip = "";//req.getRemoteAddr();
            session.isNew();
            if (userid == null || password == null || userid.equals("") || password.equals("")) {
                fault = new Fault("ERR", "Error verifying credentials");
            } else {
                password = this.encryptData(password);
                Integer counter = (Integer) session.getAttribute("ATEMPTS");
                int attempts = counter != null ? counter + 1 : 1;
                session.setAttribute("ATEMPTS", attempts);
                Company company = admin.config();
                if (company == null) {
                    fault = new Fault("ENV", "Application configuration not found.");
                } else {
                    fault = validate(session, company, attempts, ip);
                }
            }
        } catch (Exception exr) {
            LOG.error("System error ", exr);
            fault = new Fault("XDB", "Error connecting to datasource! Please contact the administrator");
        }
        return fault;
    }

    private Fault validate(HttpSession session, Company company, int attempts, String ip) {
        Fault fault;
        if (company.getAttemptLimit() != 0 && company.getAttemptLimit() < attempts) {
            if (userid.equalsIgnoreCase("superadmin")) {
                fault = new Fault("IVD", "Your login credentials cannot be authenticated");
            } else {
                audits.login(userid, "login", null, null, attempts, "FAILED", ip);
                users.deactivate(userid);
                fault = new Fault("EXC", "Maximum number of attempts exceeded");
            }
        } else if (users.isValidUser(userid, password)) {
            User staff = users.getUser();
            if (staff.getStatusLog() == 1) {
                fault = new Fault("ON", "You are aready signed-on on another machine");
            } else if (staff.isDisabled()) {
                fault = new Fault("DIS", "This account has been disabled");
            } else {
                if (!session.isNew()) {
                    session.invalidate();
                    //session = req.getSession(true);
                }
                fault = authorize(session, staff, company, attempts, ip);
            }
        } else {
            fault = new Fault("IVD", "Your login information cannot be authenticated!");
        }
        return fault;
    }

    private Fault authorize(HttpSession session, User staff, Company company, int attempts, String ip) {
        try {
            Fault fault;
            String branch = branches.describe(staff.getBranch());
            List<Function> priviledges = this.users.roleFunctions(staff.getRole());
            String traceid = audits.login(staff.getStaffCode(), "login", null, staff.getBranch(), attempts, "ACTIVE", ip);
            if (traceid == null) {
                traceid = Long.toString(System.currentTimeMillis());
            }
            session.setAttribute("traceid", traceid);
            session.setAttribute("ATEMPTS", null);
            session.setMaxInactiveInterval(company.getSessionIdleTime() * 60);
            session.setAttribute("user", staff);
            session.setAttribute("userId", staff.getStaffCode());
            session.setAttribute("permissions", group(priviledges));
            session.setAttribute("branch", branch);
            String today = SDF.format(new java.util.Date());
            int passwordLife = DTF.getDayDifference(today, staff.getLastPasswordChange());
            Role role = users.role(staff.getRole());
            String direction;
//            if (role.isDisabled()) {
//                session.setAttribute("permissions", new ArrayList<>());
//                direction = "disabledRoleArea.jsp";
//                fault = new Fault("00", "Your current User group has been disabled");
//            } else {
            direction = staff.getFirstLog() == null || passwordLife > staff.getExpiry() ? "first" : "dashboard";
            fault = new Fault("00", "Success");
            // }
            fault.setData(direction);
            return fault;
        } catch(IllegalStateException ise){
            return new Fault("40", "This session is no longer valid or has expired! Please login again.");
        }catch (Exception ex) {
            LOG.error("error validating user credentials -> ", ex);
            return new Fault("45", "Each of the logon fields must not be ommitted!");
        }
    }

    private String decryptData(String data) {
        StringEncrypter se;
        String enciphered = "";
        try {
            se = new StringEncrypter();
            enciphered = se.decrypt(data);
        } catch (EncryptionException xx) {
            LOG.info("WebSecurity :{0}", xx);
        }
        return enciphered;
    }

    private String encryptData(String data) {
        StringEncrypter se;
        String ciphered;
        try {
            se = new StringEncrypter();
            ciphered = se.encrypt(data);
        } catch (EncryptionException xx) {
            ciphered = "";
            LOG.error("WebSecurity :{0}", xx);
        }
        return ciphered;
    }

    private List<Module> group(List<Function> functions) {
        Module admins = new Module("admin", "Admin", "icon-graphic_eq");
        Module cards = new Module("card", "Cards", "icon-credit-card");
        Module files = new Module("file", "Files", "icon-pie_chart");
        Module operations = new Module("process", "Operation", "icon-person_pin");
        Module reports = new Module("report", "Reports", "icon-chart");
        List<Module> modules = new ArrayList<>();
        functions.forEach((Function f) -> {
            if (null == f.getType()) {
                cards.add(f);
            } else {
                if (f.getType().equals("admin")) {
                    admins.add(f);
                } else if (f.getType().equals("file")) {
                    files.add(f);
                } else if (f.getType().equals("process")) {
                    operations.add(f);
                } else if (f.getType().equals("report")) {
                    reports.add(f);
                } else {
                    cards.add(f);
                }
            }
        });
        modules.add(admins);
        modules.add(cards);
        modules.add(files);
        modules.add(operations);
        modules.add(reports);
        return modules;
    }
}
