package edu.birzeit.swms.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailSenderService {

    @Async
    void sendEmail(SimpleMailMessage email);

}
