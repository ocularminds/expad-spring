package com.ocularminds.expad.app.view;

import com.ocularminds.expad.Fault;
import com.ocularminds.expad.app.service.CardService;
import com.ocularminds.expad.model.Card;
import com.ocularminds.expad.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/reports")
public class Reports implements Page {

    static final Logger LOG = LoggerFactory.getLogger(Reports.class);
    CardService service;

    public Reports(CardService cardService) {
        service = cardService;
    }

    @RequestMapping(value = "/audit", method = GET)
    public String audit(HttpSession session, ModelMap model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return index(model, new Fault("00", "Session has expired."));
        }
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "approval", model, new Fault());
    }

    @RequestMapping(value = "/card", method = GET)
    public String card(HttpSession session, ModelMap model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return index(model, new Fault("00", "Session has expired."));
        }
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "approval", model, new Fault());
    }

    @RequestMapping(value = "/module", method = GET)
    public String module(HttpSession session, ModelMap model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return index(model, new Fault("00", "Session has expired."));
        }
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "approval", model, new Fault());
    }

    @RequestMapping(value = "/login", method = GET)
    public String login(HttpSession session, ModelMap model) {        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return index( model, new Fault("00","Session has expired."));
        }
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "approval", model, new Fault());
    }
}
