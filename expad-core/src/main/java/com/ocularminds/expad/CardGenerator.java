/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad;

import com.ocularminds.expad.common.CardUtil;
import com.ocularminds.expad.vao.Pan;
import java.security.SecureRandom;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public interface CardGenerator {

    Pan generate();     

    default String serial(SecureRandom rand) {
        return new StringBuffer(Long.toString(System.currentTimeMillis()))
                .reverse().substring(0, 5) + CardUtil.random(rand, 3);
    }
}
