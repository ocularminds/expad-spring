/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.app;

import com.ocularminds.expad.app.dao.Db;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@Configuration
@PropertySource(value = {"file:config/application.properties"})
public class Configurations {
    
    private static final String PROPERTY_DB_DRIVER_CLASS = "expad.db.class";
    private static final String PROPERTY_DB_PASSWORD = "expad.db.pass";
    private static final String PROPERTY_NAMED_INSTANCE = "expad.db.namedinstance";
    private static final String PROPERTY_DB_INSTANCE = "expad.db.instance";
    private static final String PROPERTY_DB_NAME = "expad.db.name";
    private static final String PROPERTY_DB_ADDR = "expad.db.addr";
    private static final String PROPERTY_DB_PORT = "expad.db.port";
   // private static final String PROPERTY_DB_URL = "expad.db.url";
    private static final String PROPERTY_DB_USER = "expad.db.user";   
    
    private static final String BANK_DB_DRIVER_CLASS = "bank.db.class";
    private static final String BANK_DB_PASSWORD = "bank.db.pass";
    private static final String BANK_NAMED_INSTANCE = "bank.db.namedinstance";
    private static final String BANK_DB_INSTANCE = "bank.db.instance";
    private static final String BANK_DB_NAME = "bank.db.name";
    private static final String BANK_DB_ADDR = "bank.db.addr";
    private static final String BANK_DB_PORT = "bank.db.port";
    private static final String BANK_DB_USER = "bank.db.user";   
    
    @Autowired
    private Environment env;
    
    @Bean
    public Db db() {
        return new Db(dataSource(), bankDataSource());
    }
    
    public void addFilter(){
        //res.addHeader("X-FRAME-OPTIONS", "DENY");
    }
    
    /**
     * Creates and configures the HikariCP data source bean.
     *
     * @param env The runtime environment of our application.
     * @return
     */
    @Bean(destroyMethod = "close") 
    DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(env.getRequiredProperty(PROPERTY_DB_DRIVER_CLASS));
        config.addDataSourceProperty("serverName", env.getRequiredProperty(PROPERTY_DB_ADDR));
        if (env.getRequiredProperty(PROPERTY_DB_DRIVER_CLASS).contains("microsoft")) {
            config.addDataSourceProperty("portNumber", env.getRequiredProperty(PROPERTY_DB_PORT));
        } else {
            config.addDataSourceProperty("port", env.getRequiredProperty(PROPERTY_DB_PORT));
        }
        if (Boolean.valueOf(env.getRequiredProperty(PROPERTY_NAMED_INSTANCE))) {
            config.addDataSourceProperty("instanceName",
                    env.getRequiredProperty(PROPERTY_DB_INSTANCE));
        }
        config.addDataSourceProperty("databaseName", env.getRequiredProperty(PROPERTY_DB_NAME));
        config.addDataSourceProperty("user", env.getRequiredProperty(PROPERTY_DB_USER));
        config.addDataSourceProperty("password", env.getRequiredProperty(PROPERTY_DB_PASSWORD));
        return new HikariDataSource(config);
    }
    
    /**
     * Creates and configures the HikariCP data source bean.
     *
     * @param env The runtime environment of our application.
     * @return
     */
    @Bean(destroyMethod = "close")
    DataSource bankDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(env.getRequiredProperty(BANK_DB_DRIVER_CLASS));
        config.addDataSourceProperty("serverName", env.getRequiredProperty(BANK_DB_ADDR));
        if (env.getRequiredProperty(BANK_DB_DRIVER_CLASS).contains("microsoft")) {
            config.addDataSourceProperty("portNumber", env.getRequiredProperty(BANK_DB_PORT));
        } else {
            config.addDataSourceProperty("port", env.getRequiredProperty(BANK_DB_PORT));
        }
        if (Boolean.valueOf(env.getRequiredProperty(BANK_NAMED_INSTANCE))) {
            config.addDataSourceProperty("instanceName",
                    env.getRequiredProperty(BANK_DB_INSTANCE));
        }
        config.addDataSourceProperty("databaseName", env.getRequiredProperty(BANK_DB_NAME));
        config.addDataSourceProperty("user", env.getRequiredProperty(BANK_DB_USER));
        config.addDataSourceProperty("password", env.getRequiredProperty(BANK_DB_PASSWORD));
        return new HikariDataSource(config);
    }

//    @Bean
//    public FreeMarkerViewResolver freemarkerViewResolver() {
//        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
//        resolver.setCache(true);
//        resolver.setPrefix("");
//        resolver.setSuffix(".html");
//        return resolver;
//    }
//
//    @Bean
//    public FreeMarkerConfigurer freemarkerConfig() {
//        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//        freeMarkerConfigurer.setTemplateLoaderPath("/templates/");
//        return freeMarkerConfigurer;
//    }
}
