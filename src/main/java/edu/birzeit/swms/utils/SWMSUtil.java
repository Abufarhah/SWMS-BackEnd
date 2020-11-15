package edu.birzeit.swms.utils;

import edu.birzeit.swms.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SWMSUtil {

    public boolean isAdmin() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN);
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(simpleGrantedAuthority);
    }

}
