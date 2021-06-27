package com.example.security.auth.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

   private final String key;
   private final String tokenHeader;
   private final String tokenPrefix;
   private final Long tokenValidity;
   private final String authEndpoint;


    public JwtUtils(
            @Value("${jwt.base64-secret}") String key,
            @Value("${jwt.header}") String tokenHeader,
            @Value("${jwt.prefix}") String tokenPrefix,
            @Value("${jwt.token.validity}") Long tokenValidity,
            @Value("${jwt.user.authentication.endpoint}") String authEndpoint
            ) {
        this.key = key;
        this.tokenHeader = tokenHeader;
        this.tokenPrefix = tokenPrefix;
        this.tokenValidity = tokenValidity;
        this.authEndpoint = authEndpoint;
    }


    public String getKey() {
        return key;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public Long getTokenValidity() {
        return tokenValidity;
    }

    public String getAuthEndpoint() {
        return authEndpoint;
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public   Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            LOGGER.debug("no authentication in security context found");
            return Optional.empty();
        }

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        LOGGER.debug("found username '{}' in security context", username);

        return Optional.ofNullable(username);
    }

    private boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.key).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            LOGGER.info("Invalid JWT signature.");
            LOGGER.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            LOGGER.info("Expired JWT token.");
            LOGGER.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            LOGGER.info("Unsupported JWT token.");
            LOGGER.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            LOGGER.info("JWT token compact of handler are invalid.");
            LOGGER.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        if(token != null && validateToken(token)) {

            Claims claims = parseToken(token);

            String username = getUsername(claims);

            String roles = getRoles(claims);

            String permissions = getPermissions(claims);

            if(username != null) {
                return getUsernamePasswordAuthenticationTokenFromClaims(username, roles, permissions);
            }
            return null;
        }
        else {
            LOGGER.debug("no valid JWT token found!");
        }
        return null;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(this.tokenHeader);
        if(token != null && token.startsWith(this.tokenPrefix)) {
            token = token.replace(this.tokenPrefix + " ", "");
            LOGGER.debug("Get token: {} from Request: {}", token, request.getRequestURI());
            return token;
        } else {
//            throw new JwtTokenMissingException("JWT not found in request header.");
            return null;
        }
    }

    public Claims parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.key)
                .parseClaimsJws(token)
                .getBody();
        LOGGER.debug("Get Claims from JWT: {}", claims);
        return claims;
    }

    public String getUsername(Claims claims) {
        String username = claims.getSubject();
        LOGGER.debug("Get Username '{}' from JWT Claims.",username);
        return username;
    }

    public String getRoles(Claims claims) {
        String roles = claims.get("roles", String.class);
        LOGGER.debug("Get Roles '[{}]' from JWT Claims.",roles);
        return roles;
    }

    public String getPermissions(Claims claims) {
        String permissions = claims.get("permissions", String.class);
        LOGGER.debug("Get Permissions '[{}]' from JWT Claims.",permissions);
        return permissions;
    }

    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationTokenFromClaims(String username, String roles, String permissions) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Arrays.asList(roles.split(",")).stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList()));
        Map<String, Object> userDetailsMap = new HashMap<>();
        userDetailsMap.put("roles", Arrays.asList(roles.split(",")));
        userDetailsMap.put("permissions", Arrays.asList(permissions.split(",")));
        authenticationToken.setDetails(userDetailsMap);
        LOGGER.debug("Username Password Authentication Token Generated '[{}]' from JWT Claims.",authenticationToken);
        return authenticationToken;
    }

    public String getRoles(Authentication authentication) {
        String roles = authentication.getAuthorities()
                .stream()
                .filter(a -> a.getAuthority().startsWith("ROLE_"))
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        LOGGER.debug("Getting Roles: [{}] for User '{}'.",roles, ((User) authentication.getPrincipal()).getUsername());
        return roles;
    }

    public String getPermissions(Authentication authentication) {
        String permissions = authentication.getAuthorities()
                .stream()
                .filter(a -> !(a.getAuthority().startsWith("ROLE_")))
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        LOGGER.debug("Getting Permissions: [{}] for User '{}'.",permissions, ((User) authentication.getPrincipal()).getUsername());
        return permissions;
    }

    public String generateToken(Authentication authentication) {
        String jwtToken = Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .claim("roles", getRoles(authentication))
                .claim("permissions", getPermissions(authentication))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.tokenValidity))
                .signWith(SignatureAlgorithm.HS256, this.key)
                .compact();
        LOGGER.info("JWT Generated.");
        return jwtToken;
    }
}
