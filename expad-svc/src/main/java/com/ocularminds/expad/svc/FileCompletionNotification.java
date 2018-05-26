/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.svc;

import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@Aspect
@Component
public class FileCompletionNotification {
    
    static final Logger LOG = LoggerFactory.getLogger(FileCompletionNotification.class);
    
    @Around("@annotation(OnFileCompletion)")
    public void update(ProceedingJoinPoint proceedingJoinPoint){         
        try {
            Signature signature = proceedingJoinPoint.getSignature();
            LOG.info("signature type: "+signature.getDeclaringTypeName());
            LOG.info("signature name: "+signature.getName());
            LOG.info("signature class: "+signature.getClass());
            proceedingJoinPoint.proceed();
        } catch (Throwable ex) {
            LOG.error("error capturing job completion status", ex);
        }
    }
    
    private void update(){
        List records = new ArrayList();
//        for (int j = 0; j < records.size(); ++j) {
//            try {
//                esb.notifyDownloadedDebitCardByPan((String) records.get(j));
//            } catch (Exception ex) {
//                LOG.error("", ex);
//            }
//        }
    }
    
}
