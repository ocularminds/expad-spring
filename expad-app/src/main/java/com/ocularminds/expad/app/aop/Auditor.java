/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Auditor is an implementation class to audit any class marked for audit for
 * audit log. It captures the methods annotated with
 *
 * @ShouldBeAudited read the details to processing audit log.
 *
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@Aspect
@Component
public class Auditor {

    static final Logger LOG = LoggerFactory.getLogger(Auditor.class);

    @Around("@annotation(ShouldBeAudited)")
    public Object audit(ProceedingJoinPoint proceedingJoinPoint) {
        LOG.info("entered Audit.audit method");
        Object proceed;
        CapturedEntity previous, current;
        try {
            Object[] parameters = proceedingJoinPoint.getArgs();
            LOG.info("found parameters with size - " + parameters.length);
            if (parameters.length > 0) {
                Class<?> clazz = parameters[0].getClass();
                LOG.info("auditing for class {}", clazz.getSimpleName());
                //Field f = clazz.getDeclaredField("name");
                //Method method = clazz.getDeclaredMethod("getName");
                //if (method. != null) {
                previous = new CapturedEntity();//previous(f.get(f.getName()).toString());
                current = current(parameters[0]);
                //}
                proceed = proceedingJoinPoint.proceed();
            } else {
                proceed = proceedingJoinPoint.proceed();
            }
            Signature signature = proceedingJoinPoint.getSignature();
        } catch (Throwable ex) {
            proceed = null;
            LOG.error("error auditing method: {}", ex.getMessage(), ex);
        }
        return proceed;
    }

    private void log(Object[] parameters) {
        Object object = parameters[0];
        //objectain declare fields and compare
        //create Audit object and insert into database
        if (object instanceof Serializable) {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            try {
                Field f = clazz.getField("id");
                LOG.info("Primary key {} - {}", clazz.getName(), f.get(f.getName()));
                for (Field field : fields) {

                    Object name = field.getName();
                    Object value = field.get(name);
                    // previous = 
                    LOG.info("class {} field:{} - {}", clazz.getName(), name, value);
                }

            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }

//        Table tableAnnotation = entityClass.getAnnotation(Table.class);
//        Audit audit = new Audit();
//        audit.setTableName(tableAnnotation.name());
//        audit.setOperation("INSERT");
//        audit.setNewValue(((Auditable) entity).getDetails());
//        audit.setRowIdentifier(id.toString());
//        auditables.add(audit);
    }

    private CapturedEntity previous(String identity) {
        CapturedEntity v = new CapturedEntity();
        return v;
    }

    private CapturedEntity current(Object parameter) {
        CapturedEntity v = new CapturedEntity();
        Class<?> clazz = parameter.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            Object name = field.getName();
            Object value;
            try {
                if (Modifier.isPrivate(field.getModifiers())) {
                    field.setAccessible(true);
                }
                value = field.get(parameter);
                LOG.info("class {} field:{} - {}", clazz.getSimpleName(), name, value);
                Optional val = Optional.ofNullable(value);
                String actual = val.isPresent() ? val.get().toString() : null;
                v.addField(name.toString(), actual);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                LOG.error(ex.getMessage(), ex);
            }

        }

        v.clazz(clazz.getName());
        return v;
    }

}
