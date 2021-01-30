package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void signUpUser(UserDto userDto);

    void sendConfirmationSMS(String userMail, String token);

    void confirmUser(String token);

    UserDto getUser();

    void setPassword(String token, String password);

    void sendPasswordSettingSMS(String username, String userMail, String token);

}
