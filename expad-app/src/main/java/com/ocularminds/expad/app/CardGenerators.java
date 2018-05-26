/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app;

import com.ocularminds.expad.app.generator.EtranzactCardGenerator;
import com.ocularminds.expad.app.generator.InterswitchCardGenerator;
import com.ocularminds.expad.vao.Company;
import com.ocularminds.expad.vao.Pan;
import com.ocularminds.expad.vao.Product;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public enum CardGenerators {
    ETRANZACT {
        @Override
        public Pan generate(Company company, Product prod, String branch) {
            return new EtranzactCardGenerator(company, prod, branch).generate();
        }
    }, INTERSWITCH {

        @Override
        public Pan generate(Company company, Product prod, String branch) {
            return new InterswitchCardGenerator(company, prod, branch).generate();
        }
    };

    public abstract Pan generate(Company company, Product prod, String branch);
}
