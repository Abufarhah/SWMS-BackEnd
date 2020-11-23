package edu.birzeit.swms.services;

import edu.birzeit.swms.models.ConfirmationToken;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken confirmationToken);

    void deleteConfirmationToken(Integer id);

}
