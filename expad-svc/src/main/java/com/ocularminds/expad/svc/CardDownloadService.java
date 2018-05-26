/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.svc;

import com.ocularminds.expad.vao.Card;
import com.ocularminds.expad.vao.Merchant;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jejelowo .B. Festus <festus.jejelowo@ocularminds.com>
 */
@Service
public class CardDownloadService implements CardDownload {

    @Value("${expad.server.port}")
    int port;

    @Override
    public List<Card> download(String type) {
        RestTemplate resource = new RestTemplate();
        ResponseEntity<String> response = resource.getForEntity(
                createURLWithPort("/api/cards/download/".concat(type), port), null, String.class
        );
        if (response.getBody() == null) {
            return Collections.EMPTY_LIST;
        }
        return (List) asJsonObject(response.getBody(), List.class);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Merchant> merchants() {
        RestTemplate resource = new RestTemplate();
        ResponseEntity<String> response = resource.getForEntity(
                createURLWithPort("/api/admin/merchant", port), null, String.class
        );        
        if (response.getBody() == null) {
            return Collections.EMPTY_LIST;
        }
        return (List) asJsonObject(response.getBody(), List.class);
    }
}
