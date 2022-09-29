package com.example.security.auth.filters;


import com.example.security.rest.dto.UserLoginDto;
import com.example.security.auth.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@Component
@Slf4j
public class RestApiAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public RestApiAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
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
            log.info("Attempt Authentication for User '{}'.", userLoginDto.getUsername());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException | AuthenticationException e) {
            log.error("Authentication Failed!: '{}'.", e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("Authentication Successful! for User '{}'.", ((User) authentication.getPrincipal()).getUsername());
//        response.addHeader("Authorization","Bearer " + token);
        response.addHeader(AUTHORIZATION, jwtUtils.getTokenPrefix() + " " + jwtUtils.getAccessToken(request, authentication));
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("access_token", jwtUtils.getAccessToken(request, authentication));
        responseMap.put("token_type", "Bearer");
        responseMap.put("expires_in", jwtUtils.getAccessTokenExpirationTime());
        responseMap.put("refresh_token", jwtUtils.getRefreshToken(request, authentication));
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
