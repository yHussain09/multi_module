package com.example.security.auth.filters;


import com.example.security.rest.dto.UserLoginDto;
import com.example.security.auth.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

//@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        setFilterProcessesUrl(jwtUtils.getAuthEndpoint());
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        UserLoginDto userLoginDto;
        try {
            userLoginDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(),
                            userLoginDto.getPassword(),
                            new ArrayList<>());

            LOGGER.info("Attempt Authentication for User '{}'.",userLoginDto.getUsername());
            return authenticationManager.authenticate(authenticationToken);
        }
        catch(IOException | AuthenticationException e) {
            LOGGER.error("Authentication Failed!: '{}'.",e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        LOGGER.info("Authentication Successful! for User '{}'.",((User)authentication.getPrincipal()).getUsername());
//        response.addHeader("Authorization","Bearer " + token);
        response.addHeader(jwtUtils.getTokenHeader(), jwtUtils.getTokenPrefix() + " " + jwtUtils.generateToken(authentication));
//        String body = ((User) authentication.getPrincipal()).getUsername() + " " + generateToken(authentication);
//        response.getWriter().write(body);
//        response.getWriter().flush();

    }




}
