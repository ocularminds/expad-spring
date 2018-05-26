/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.route;

import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@RestController
@RequestMapping("/api")
public class Health {

    @RequestMapping(value = "/ping", method = GET)
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "/health", method = GET)
    public String heath() {
        return "Healthy. Ready";
    }
}
