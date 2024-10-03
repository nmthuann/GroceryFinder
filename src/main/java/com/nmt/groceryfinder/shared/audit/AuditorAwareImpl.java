package com.nmt.groceryfinder.shared.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class  AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        if (SecurityContextHolder.getContext().getAuthentication() == null) {
//            return Optional.of("SERVER");
//        }
//        UserDetails userDetails =
//                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return Optional.ofNullable(userDetails.getUsername());

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return Optional.of("SERVER");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return Optional.ofNullable(userDetails.getUsername());
        } else if (principal instanceof String username) {
            return Optional.of(username);
        }

        return Optional.of("SERVER");
    }
    //        return Optional.empty();
}