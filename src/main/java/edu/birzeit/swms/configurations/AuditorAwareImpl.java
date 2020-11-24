package edu.birzeit.swms.configurations;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String email;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            email = principal.toString();
        } catch (Exception e) {
            return Optional.of("signing up");
        }
        return Optional.of(email);
    }

}
