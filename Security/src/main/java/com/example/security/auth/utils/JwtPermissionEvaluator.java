package com.example.security.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class JwtPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication != null && permission instanceof String) {
            log.debug("Validating Access Permission for User '{}' and Permission '{}'.",authentication.getPrincipal().toString(), permission);
            boolean permissionValidate = validatePermission(authentication ,String.valueOf(permission));
            if (permissionValidate) {
                log.debug("Permission '{}' Granted for User '{}'",permission, authentication.getPrincipal().toString());
            } else {
                log.debug("Failed to validate Permission '{}' for User '{}'",permission, authentication.getPrincipal().toString());
            }
            return permissionValidate;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    private boolean validatePermission(Authentication authentication, String permission) {
        HashMap<String, Object> userDetailsMap = (HashMap<String, Object>) authentication.getDetails();
        List<String> permissionList = (List<String>) userDetailsMap.get("permissions");
        return permissionList.contains(permission);
    }
}
