package com.ocularminds.expad.app.route;

import com.ocularminds.expad.Fault;
import com.ocularminds.expad.model.CardInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/postcard")
public class PostCard {

    @RequestMapping(value="/{pan}/{expiry}", method=GET)
    public CardInfo find(@PathVariable("pan") String pan, @PathVariable("expiry") String expiry) {
        CardInfo info;
        info = null;//InfrastructureToolbox.getCardInfo(pan, expiry);
        if (info == null) {
            info = new CardInfo("", "", "");
            info.setPan("NA");
        }
        return info;
    }

    @RequestMapping(value="/block", method = POST)
    public Fault block(@RequestParam("userid") String userid, @RequestParam("pan") String pan, @RequestParam("expiry") String expiry, @RequestParam("reason") String reason) {
        Fault fault;
        try {
            if (reason == null || reason.equalsIgnoreCase("NA")) {
                fault = new Fault("56", "Process not Completed. Please select Message");
            } else if (expiry == null) {
                fault = new Fault("58", "Expiry Date must not be left blank");
            } else {
                //InfrastructureToolbox.blockCard(pan, expiry, reason);
                //CardService.instance().blockCard(pan, userid);
                fault = new Fault("error", "00", "Completed Successfully");
            }
        } catch (Exception e) {
            System.out.println("Error blocking card -> " + pan + " " + e);
            fault = new Fault("91", "Failed Blocking Card");
        }
        return fault;
    }
}
