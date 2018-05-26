package com.ocularminds.expad.app.view;

import com.ocularminds.expad.app.Credentials;
import com.ocularminds.expad.Fault;
import com.ocularminds.expad.app.NotFoundException;
import com.ocularminds.expad.app.service.Authenticator;
import com.ocularminds.expad.app.service.PasswordService;
import com.ocularminds.expad.model.User;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author Jejelowo B. Festus <festus.jejelowo@ocularminds.com>
 */
@Controller
@RequestMapping(value = "/")
public class Index implements Page {

    final Authenticator authenticator;
    final PasswordService passwords;
    final static Logger LOG = LoggerFactory.getLogger(Index.class);

    @Autowired
    public Index(Authenticator authenticator, PasswordService passwordService) {
        this.authenticator = authenticator;
        this.passwords = passwordService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpSession session, ModelMap model) {
        return index(model, new Fault("00", ""));
    }

    @RequestMapping(value = "/authenticate", method = POST)
    public String index(HttpSession session, ModelMap model, Credentials credentials, BindingResult bindingResult) {
        Fault fault = authenticator.authenticate(session, credentials);
        if (fault.isFailed()) {
            return index(model, fault);
        } else {
            return dashboard(session, model);
        }
    }

    @RequestMapping(value = "/dashboard", method = GET)
    public String dashboard(HttpSession session, ModelMap model) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("modules", session.getAttribute("permissions"));
        return getPage(session, "dashboard", model, new Fault());
    }

    @RequestMapping(value = "/signout", method = GET)
    public String signout(HttpSession session, ModelMap model) {
        session.invalidate();
        return index(model, new Fault("00", ""));
    }

    @RequestMapping(value = "/password", method = GET)
    public String password(HttpSession session, ModelMap model) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return signout(session, model);
            }
            model.addAttribute("userId", user.getId());
            model.addAttribute("password1", "");
            model.addAttribute("password2", "");
        } catch (Exception ex) {
            LOG.error("error sestting up password ", ex);
            return signout(session, model);
        }
        return getPage(session, "roles", model, new Fault());
    }

    @RequestMapping(value = "/password", method = POST)
    public String roles(HttpSession session, ModelMap model, Credentials credentials) {
        try {
            Fault fault = passwords.execute(session, credentials);
            if (fault.isSuccess()) {
                return getPage(session, "success", model, new Fault());
            } else {
                return getPage(session, "password", model, fault);
            }
        } catch (Exception ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

}
