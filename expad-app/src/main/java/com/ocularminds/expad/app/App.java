/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app;

import com.ocularminds.expad.app.service.AdminService;
import com.ocularminds.expad.vao.Company;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@SpringBootApplication
public class App {

    @Autowired
    AdminService adminService;
    Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }  

    /**
     *
     */
    @PostConstruct
    public void testShouldBeAudited() {
        LOG.info("Processing test audit...");
        Company c = adminService.config();
        c.setAddress("1, Kofo Abayomi, VI, Lagos");
        //adminService.update(c);
    }                
}                  