/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.view;

import com.google.gson.Gson;
import com.ocularminds.expad.Fault;
import javax.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;

/**
 *
 * @author Jejelowo B. Festus <festus.jejelowo@ocularminds.com>
 */
public interface Page {

    default String index(ModelMap model, Fault fault) {
        model.addAttribute("error", fault.getFault());
        model.addAttribute("locked", "");
        model.addAttribute("LICENSE_INFO", "");
        return "login";
    }

    default String getPage(HttpSession session, String page, ModelMap model, Fault fault) {
        if (session != null && session.getAttribute("user") == null) {
            session.invalidate();
            return index(model, new Fault("30", "This session is no longer valid or has expired! Please login again."));
        }
        model.addAttribute("fault", fault);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("modules", session.getAttribute("permissions"));
        return page;
    }

    default String toJson(Object o) {
        return new Gson().toJson(o);
    }

    default Object fromJson(String o, Class clazz) {
        return new Gson().fromJson(o, clazz);
    }

    default boolean isNew(String identity) {
        return identity == null || identity.trim().isEmpty();
    }
}
