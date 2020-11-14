package edu.birzeit.swms.security;

import edu.birzeit.swms.enums.UserRole;
import edu.birzeit.swms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static edu.birzeit.swms.configurations.Constants.*;

@Configuration
public class AdminConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public User admin() {
        User user = new User();
        user.setId(ADMIN_ID);
        user.setFirstName(ADMIN_FIRSTNAME);
        user.setLastName(ADMIN_LASTNAME);
        user.setPhone(ADMIN_PHONE);
        user.setAddress(ADMIN_ADDRESS);
        user.setUsername(ADMIN_USERNAME);
        user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        user.setRole(UserRole.ADMIN);
        return user;
    }

}
