package edu.birzeit.swms.services.implementations;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.birzeit.swms.models.SMS;
import edu.birzeit.swms.services.SMSService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@ConfigurationProperties(prefix = "application.sms")
@Service
public class SMSServiceImpl implements SMSService {

    private String twilioAccountSID="AC980062100b28b9f21f0fa45d3d436602";

    private String twilioAuthToken="46fb842f6c3b06e87debeba8ad685c5c";

    private String twilioPhoneNumber="+12056515403";

    public void send(SMS sms) {
        Twilio.init(twilioAccountSID, twilioAuthToken);

        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(twilioPhoneNumber), sms.getMessage())
                .create();
        System.out.println("here is my id:" + message.getSid());// Unique resource ID created to manage this transaction

    }

    public void receive(MultiValueMap<String, String> smscallback) {
    }
}
