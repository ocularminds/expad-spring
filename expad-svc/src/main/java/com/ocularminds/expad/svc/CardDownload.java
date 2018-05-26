/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.svc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocularminds.expad.vao.Card;
import com.ocularminds.expad.vao.Merchant;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
public interface CardDownload {
    
    static final Logger LOG = LoggerFactory.getLogger(CardDownload.class);

    /**
     *
     * @param type
     * @return
     */
    List<Card> download(String type);
    
    List<Merchant> merchants();

    default Object asJsonObject(final String json, Class clazz) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    default String createURLWithPort(String uri, int port) {
        return String.format("http://localhost:%s%s", port, uri);
    }

}
