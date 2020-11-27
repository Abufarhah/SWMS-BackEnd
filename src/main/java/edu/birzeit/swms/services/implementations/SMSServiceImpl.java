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

    private String twilioAccountSID="ACce17252e794a9a9bc8b1d0b35676a517";

    private String twilioAuthToken="b04d8801c5345127579cde5c2f21a54c";

    private String twilioPhoneNumber="+12312725350";

    public void send(SMS sms) {
        Twilio.init(twilioAccountSID, twilioAuthToken);

        Message message = Message.creator(new PhoneNumber("+970597035694"), new PhoneNumber(twilioPhoneNumber), sms.getMessage())
                .create();
        System.out.println("here is my id:" + message.getSid());// Unique resource ID created to manage this transaction

    }

    public void receive(MultiValueMap<String, String> smscallback) {
    }
}
