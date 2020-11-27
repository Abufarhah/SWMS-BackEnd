package edu.birzeit.swms.configurations;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String username;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = principal.toString();
        } catch (Exception e) {
            return Optional.of("signing up");
        }
        return Optional.of(username);
    }

}
