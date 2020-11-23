package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.models.ConfirmationToken;
import edu.birzeit.swms.repositories.ConfirmationTokenRepository;
import edu.birzeit.swms.services.ConfirmationTokenService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.save(confirmationToken);

    }

    public void deleteConfirmationToken(Integer id) {

        confirmationTokenRepository.deleteById(id);

    }

}
