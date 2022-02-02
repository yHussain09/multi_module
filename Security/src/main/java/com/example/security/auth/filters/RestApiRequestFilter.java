package com.example.security.auth.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.corejava.dto.RestResponse;
import com.example.security.auth.exceptions.JwtTokenMissingException;
import com.example.security.auth.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
//@Component
@RequiredArgsConstructor
public class RestApiRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        if (!request.getServletPath().equals(jwtUtils.getAuthEndpoint())) {
            try {
                String token = jwtUtils.getTokenFromRequest(request);
                UsernamePasswordAuthenticationToken authentication = jwtUtils.getAuthentication(token);
                if (authentication != null) {
//                    log.info("Get Authentication from JWT.");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Set Authentication in Spring Security Context.");
                    filterChain.doFilter(request, response);
                }
            } catch (JwtTokenMissingException e) {
                log.error(e.getMessage());
//                response.setHeader("error", e.getMessage());
//                response.sendError(FORBIDDEN.value());
//                Map<String, Object> responseMap = new HashMap<>();
//                responseMap.put("error", e.getMessage());
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
//                ObjectMapper mapper = mapperBuilder.build();
//
//                new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
                /*new ObjectMapper().writeValue(response.getOutputStream(),
                        RestResponse.builder()
//                                .timeStamp(LocalDateTime.now())
                                .data(responseMap)
                                .message("")
                                .status(HttpStatus.FORBIDDEN)
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .build());*/
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}
