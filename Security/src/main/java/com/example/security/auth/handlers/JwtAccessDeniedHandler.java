package com.example.security.auth.handlers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Slf4j
//@Component
//@ConditionalOnProperty(name = "app.security.enable", havingValue = "true")
/*public class JwtAccessDeniedHandler implements AccessDeniedHandler {

//    protected static final Logger LOGGER = LoggerFactory.getLogger(JwtAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            log.warn("User: " + authentication.getName()
                    + " attempted to access the protected URL: "
                    + request.getRequestURI());

            // This is invoked when user tries to access a secured REST resource without the necessary authorization
            // We should just send a 403 Forbidden response because there is no 'error' page to redirect to
            // Here you can place any message you want
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, accessDeniedException.getMessage());

            // redirect to access denied page (for web application)
//             response.sendRedirect(request.getContextPath() + "/accessDenied");
        }




    }
}*/
