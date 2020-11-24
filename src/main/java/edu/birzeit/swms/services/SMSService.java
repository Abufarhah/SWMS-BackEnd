package edu.birzeit.swms.services;

import edu.birzeit.swms.models.SMS;
import org.springframework.util.MultiValueMap;

public interface SMSService {

    void send(SMS sms);

    void receive(MultiValueMap<String, String> smscallback);
}
