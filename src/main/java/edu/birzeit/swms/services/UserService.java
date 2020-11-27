package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.UserDto;
import edu.birzeit.swms.models.ConfirmationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void signUpUser(UserDto userDto);

    void sendConfirmationMail(String userMail, String token);

    void confirmUser(String token);

    UserDto getUser();

}
