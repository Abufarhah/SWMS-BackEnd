package edu.birzeit.swms.controllers;

import com.twilio.exception.ApiException;
import edu.birzeit.swms.models.SMS;
import edu.birzeit.swms.services.SMSService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log
@RestController
public class SMSController {

    @Autowired
    SMSService smsservice;

    @Autowired
    SimpMessagingTemplate webSocket;

    private final String TOPIC_DESTINATION = "/swms-topic/sms";

    @PostMapping("/sms")
    public void smsSubmit(@RequestBody SMS sms) {
        try {
            smsservice.send(sms);
        } catch (ApiException e) {
            webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error sending the SMS: " + e.getMessage());
            throw e;
        }
        webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent!: " + sms.getTo());
    }

    @PostMapping(value = "/smscallback",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void smsCallback(@RequestBody MultiValueMap<String, String> map) {
        smsservice.receive(map);
        webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Twilio has made a callback request! Here are the contents: " + map.toString());
    }

    private String getTimeStamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }


}

