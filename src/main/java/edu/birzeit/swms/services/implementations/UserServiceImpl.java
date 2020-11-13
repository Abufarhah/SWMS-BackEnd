package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.configurations.Constants;
import edu.birzeit.swms.exceptions.DatabaseException;
import edu.birzeit.swms.models.User;
import edu.birzeit.swms.repositories.UserRepository;
import edu.birzeit.swms.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    User admin;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("loadUserByUsername --- loading user username to authenticate");
            if (username.equals(Constants.ADMIN_USERNAME)) {
                log.warn("loadUserByUsername --- trying to login as an administrator");
                return admin;
            } else {
                User user = userRepository.findByUsername(username).orElseThrow(() -> {
                    log.error(String.format("loadUserByUsername --- user with username: '%s' not found", username));
                    return new UsernameNotFoundException(String.format("User with username: '%s' not found", username));
                });
                log.debug(String.format("loadUserByUsername --- user was found: '%s'", user));
                return user;
            }
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(String.format("loadUserByUsername --- database persisting error accessing user table\n%s", ex));
            throw new DatabaseException(ex);
        }
    }
}
