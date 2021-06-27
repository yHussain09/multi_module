package com.example.security.auth.filters;

import com.example.security.auth.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    protected static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);
    private final JwtUtils jwtUtils;

    public JwtRequestFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        String token = jwtUtils.getTokenFromRequest(request);
        LOGGER.info("Get token from Request.");
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = jwtUtils.getAuthentication(token);
        LOGGER.info("Get Authentication from JWT.");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LOGGER.info("Set Authentication in Spring Security Context.");
        filterChain.doFilter(request,response);
    }
}
