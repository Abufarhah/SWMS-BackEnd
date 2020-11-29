package edu.birzeit.swms.utils;

import edu.birzeit.swms.enums.UserRole;
import edu.birzeit.swms.exceptions.CustomException;
import edu.birzeit.swms.models.User;
import edu.birzeit.swms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static edu.birzeit.swms.configurations.Constants.ADMIN_USERNAME;

@Component
public class SWMSUtil {

    @Autowired
    UserRepository userRepository;

    @Autowired
    User admin;

    public boolean isAdmin() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN);
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(simpleGrantedAuthority);
    }

    public User getLoggedInUser() {
        String username;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = principal.toString();
        } catch (Exception e) {
            throw new CustomException("you are not logged in user", HttpStatus.UNAUTHORIZED);
        }
        User user;
        if (username.equals(ADMIN_USERNAME)) {
            user = admin;
        } else {
            user = userRepository.findByUsername(username).orElseThrow(() ->
                    new CustomException("User Not Found", HttpStatus.NOT_FOUND)
            );
        }
        return user;
    }

}
