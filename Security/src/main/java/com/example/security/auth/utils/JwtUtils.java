package com.example.security.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.security.auth.exceptions.JwtTokenMissingException;
import lombok.extern.slf4j.Slf4j;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class JwtUtils {

   private final String secretKey;
//   private final String tokenHeader;
   private final String tokenPrefix;
   private final Long accessTokenValidity;
   private final Long refreshTokenValidity;
   private final String authEndpoint;



    public JwtUtils(
            @Value("${jwt.base64-secret}") String secretKey,
//            @Value("${jwt.header}") String tokenHeader,
            @Value("${jwt.prefix}") String tokenPrefix,
            @Value("${jwt.access.token.validity}") Long accessTokenValidity,
            @Value("${jwt.refresh.token.validity}") Long refreshTokenValidity,
            @Value("${jwt.user.authentication.endpoint}") String authEndpoint
    ) {
        this.secretKey = secretKey;
//        this.tokenHeader = tokenHeader;
        this.tokenPrefix = tokenPrefix;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.authEndpoint = authEndpoint;
    }


    public String getSecretKey() {
        return this.secretKey;
    }

//    public String getTokenHeader() {
//        return tokenHeader;
//    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public Long getAccessTokenValidity() {
        return accessTokenValidity;
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
            log.debug("no authentication in security context found");
            return Optional.empty();
        }
        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }
        log.debug("found username '{}' in security context", username);
        return Optional.ofNullable(username);
    }

    private DecodedJWT validateToken(String authToken) {
        try {
            JWTVerifier jwtVerifier = JWT.require(this.getAlgorithm()).build();
            return jwtVerifier.verify(authToken);
        }
        catch (SignatureVerificationException | TokenExpiredException e) {
            log.error(e.getMessage());
            return null;
        }
        /*catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }*/
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        if(decodedJWT != null) {
            String username = getUsername(decodedJWT);
            String roles = getRoles(decodedJWT);
            String permissions = getPermissions(decodedJWT);
            if(username != null) {
                return getUsernamePasswordAuthenticationTokenFromClaims(username, roles, permissions);
            }
            return null;
        }
        else {
            log.error("JWT Validation failed!");
        }
        return null;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith(this.tokenPrefix)) {
            String token = authorizationHeader.substring(this.tokenPrefix.length() + 1);
            log.debug("Get token: {} from Request.", token);
            return token;
        }
        else {
            log.debug("Authorization Header not found in the request header.");
            throw new JwtTokenMissingException("Authorization Header not found in the request header.");
        }
    }

    /*public Claims parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.key)
                .parseClaimsJws(token)
                .getBody();
        log.debug("Get Claims from JWT: {}", claims);
        return claims;
    }*/

    public String getUsername(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        log.debug("Get Username '{}' from JWT.",username);
        return username;
    }

    public String getRoles(DecodedJWT decodedJWT) {
        String roles = decodedJWT.getClaim("roles").asString();
        log.debug("Get Roles '[{}]' from JWT.",roles);
        return roles;
    }

    public String getPermissions(DecodedJWT decodedJWT) {
        String permissions = decodedJWT.getClaim("permissions").asString();
        log.debug("Get Permissions '[{}]' from JWT.",permissions);
        return permissions;
    }

    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationTokenFromClaims(String username, String roles, String permissions) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Arrays.asList(roles).stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList()));
        Map<String, Object> userDetailsMap = new HashMap<>();
        userDetailsMap.put("roles", Arrays.asList(roles.split(",")));
        userDetailsMap.put("permissions", Arrays.asList(permissions.split(",")));
        authenticationToken.setDetails(userDetailsMap);
        log.debug("Username Password Authentication Token Generated '[{}]' from JWT Claims.",authenticationToken);
        return authenticationToken;
    }

    /*public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationTokenFromClaims(String username, String roles, String permissions) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Arrays.asList(roles.split(",")).stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList()));
        Map<String, Object> userDetailsMap = new HashMap<>();
        userDetailsMap.put("roles", Arrays.asList(roles.split(",")));
        userDetailsMap.put("permissions", Arrays.asList(permissions.split(",")));
        authenticationToken.setDetails(userDetailsMap);
        log.debug("Username Password Authentication Token Generated '[{}]' from JWT Claims.",authenticationToken);
        return authenticationToken;
    }*/

    public String getRoles(Authentication authentication) {
        String roles = authentication.getAuthorities()
                .stream()
                .filter(a -> a.getAuthority().startsWith("ROLE_"))
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        log.debug("Getting Roles: [{}] for User '{}'.",roles, ((User) authentication.getPrincipal()).getUsername());
        return roles;
    }

    public String getPermissions(Authentication authentication) {
        String permissions = authentication.getAuthorities()
                .stream()
                .filter(a -> !(a.getAuthority().startsWith("ROLE_")))
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        log.debug("Getting Permissions: [{}] for User '{}'.",permissions, ((User) authentication.getPrincipal()).getUsername());
        return permissions;
    }

    public Date getAccessTokenExpirationTime() {
        return new Date(System.currentTimeMillis() + this.accessTokenValidity);
    }

    public Date getRefreshTokenExpirationTime() {
        return new Date(System.currentTimeMillis() + this.refreshTokenValidity);
    }

    /*public String generateToken(Authentication authentication) {
        String jwtToken = Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .claim("roles", getRoles(authentication))
                .claim("permissions", getPermissions(authentication))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.tokenValidity))
                .signWith(SignatureAlgorithm.HS256, this.key)
                .compact();
        log.info("JWT Generated.");
        return jwtToken;
    }*/

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.getSecretKey().getBytes());
    }

    public String getAccessToken(HttpServletRequest request, Authentication authentication) {
        return JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .withExpiresAt(this.getAccessTokenExpirationTime())
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", this.getRoles(authentication))
                .withClaim("permissions", this.getPermissions(authentication))
                .sign(this.getAlgorithm());

    }
    public String getRefreshToken(HttpServletRequest request, Authentication authentication) {
        return JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .withExpiresAt(this.getRefreshTokenExpirationTime())
                .withIssuer(request.getRequestURL().toString())
//                .withClaim("roles", jwtUtils.getRoles(authentication))
//                .withClaim("permissions", jwtUtils.getPermissions(authentication))
                .sign(getAlgorithm());
    }

}
