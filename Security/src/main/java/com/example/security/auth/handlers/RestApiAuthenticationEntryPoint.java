package com.example.security.auth.handlers;

import com.example.corejava.dto.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

//@Component
//@ControllerAdvice
/*
public class RestApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 401
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        */
/*OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(out,
                RestResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED)
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message(authException.getMessage())
                        .path(request.getServletPath())
                        .build()
        );
        out.flush();*//*

    }

    @ExceptionHandler (value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AccessDeniedException accessDeniedException) throws IOException {
        // 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Failed : " + accessDeniedException.getMessage());
    }

    @ExceptionHandler (value = {IllegalStateException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         IllegalStateException illegalStateException) throws IOException {
        // 404
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Failed : " + illegalStateException.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         Exception exception) throws IOException {
        // 500
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error : " + exception.getMessage());
    }
}
*/
