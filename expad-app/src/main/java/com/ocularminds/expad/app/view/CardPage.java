package com.ocularminds.expad.app.view;

import com.ocularminds.expad.Fault;
import com.ocularminds.expad.app.service.CardService;
import com.ocularminds.expad.app.service.AdminService;
import com.ocularminds.expad.model.Card;
import com.ocularminds.expad.model.User;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/cards")
public class CardPage implements Page {

    static final Logger LOG = LoggerFactory.getLogger(CardPage.class);
    AdminService admin;
    CardService service;

    @Autowired
    public CardPage(AdminService adminService, CardService cardService) {
        this.admin = adminService;
        this.service = cardService;
    }

    @RequestMapping(value = "/product", method = GET)
    public String products(HttpSession session, ModelMap model) {
        model.addAttribute("products", service.products());
        return getPage(session, "products", model, new Fault());
    }

    @RequestMapping(value = "/product", method = POST)
    public String products(HttpSession session, ModelMap model, Product product) {
        service.save(product);
        return products(session, model);
    }

    @RequestMapping(value = "/product/{productId}", method = GET)
    public String products(HttpSession session, ModelMap model, @PathVariable String productId) {
        Product product = productId.equals("0") ? new Product() : service.product(productId);
        model.addAttribute("product", product);
        return getPage(session, "product", model, new Fault());
    }

    @RequestMapping(value = "/order")
    public String order(HttpSession session, ModelMap model) {

        Company c = admin.config();
        c.setAccountSize(10);
        model.addAttribute("config", c);
        model.addAttribute("merchants", service.merchants());
        model.addAttribute("products", service.products());
        return getPage(session, "order", model, new Fault());
    }

    @RequestMapping(value = "/file")
    public String file(HttpSession session, ModelMap model) {
        Company c = admin.config();
        c.setAccountSize(10);
        model.addAttribute("config", c);
        model.addAttribute("merchants", service.merchants());
        model.addAttribute("products", service.products());
        return getPage(session, "order", model, new Fault());
    }

    @RequestMapping(value = "/pan", method = GET)
    public String pan(HttpSession session, ModelMap model) {

        Company c = admin.config();
        c.setAccountSize(10);
        model.addAttribute("config", c);
        model.addAttribute("merchants", service.merchants());
        model.addAttribute("products", service.products());
        return getPage(session, "order", model, new Fault());
    }

    @RequestMapping(value = "/request", method = GET)
    public String request(HttpSession session, ModelMap model) {

        Company c = admin.config();
        c.setAccountSize(10);
        model.addAttribute("config", c);
        model.addAttribute("merchants", service.merchants());
        model.addAttribute("products", service.products());
        return getPage(session, "order", model, new Fault());
    }

    @RequestMapping(value = "/query")
    public String query(HttpSession session, ModelMap model) {

        if (session == null) {
            return index(model, new Fault("00", ""));
        }
        User user = (User) session.getAttribute("UserInSession");
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "approval", model, new Fault());
    }

    @RequestMapping(value = "/issuance")
    public String issue(HttpSession session, ModelMap model) {

        Company c = admin.config();
        c.setAccountSize(10);
        model.addAttribute("config", c);
        model.addAttribute("merchants", service.merchants());
        model.addAttribute("products", service.products());
        return getPage(session, "order", model, new Fault());
    }

    @RequestMapping(value = "/approval", method = GET)
    public String approval(HttpSession session, ModelMap model) {

        if (session == null) {
            return index(model, new Fault("00", ""));
        }
        User user = (User) session.getAttribute("UserInSession");
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "approval", model, new Fault());
    }

    @RequestMapping(value = "/review")
    public String review(HttpSession session, ModelMap model) {

        if (session == null) {
            return index(model, new Fault("00", ""));
        }
        User user = (User) session.getAttribute("UserInSession");
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "review", model, new Fault());
    }

    @RequestMapping(value = "/print", method = GET)
    public String print(HttpSession session, ModelMap model) {

        if (session == null) {
            return index(model, new Fault("00", ""));
        }
        User user = (User) session.getAttribute("user");
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "printables", model, new Fault());
    }

    @RequestMapping(value = "/search")
    public String search(HttpSession session, ModelMap model) {

        if (session == null) {
            return index(model, new Fault("00", ""));
        }
        User user = (User) session.getAttribute("UserInSession");
        try {
            List<Card> cards = service.findBranchCardForApproval(user.getBranch());
            model.addAttribute("cards", cards);
        } catch (Exception ex) {
            model.addAttribute("cards", new ArrayList());
            LOG.error("failed retriving cards for branch ", ex);
        }
        return getPage(session, "print", model, new Fault());
    }
}
