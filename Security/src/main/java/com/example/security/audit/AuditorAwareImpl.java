package com.example.security.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String auditor = "";
        if (securityContext.getAuthentication() != null) {
            auditor = securityContext.getAuthentication().getName();
            log.debug("Auditor: {} found from Spring Security Context.", auditor);
        } else {
            log.debug("Spring Security Context not found.");
        }
        return Optional.of(auditor);
    }
}
