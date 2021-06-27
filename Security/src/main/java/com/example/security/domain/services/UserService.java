package com.example.security.domain.services;

import com.example.security.auth.utils.JwtUtils;
import com.example.security.base.ServiceBase;
import com.example.security.domain.dao.UserRepository;
import com.example.security.domain.entities.User;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class UserService extends ServiceBase<User,Long> implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        super(repository);
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    @Override
    public ResponseEntity<User> save(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.save(entity);
    }

    @Transactional
    @Override
    public ResponseEntity<User> update(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.update(entity);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return jwtUtils.getCurrentUsername().flatMap(userRepository::findOneWithRolesByUsername);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {

        LOGGER.debug("Authenticating user '{}'", username);

        if (new EmailValidator().isValid(username, null)) {
            return userRepository.findOneWithRolesByEmailIgnoreCase(username)
                    .map(user -> createSpringSecurityUser(username, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " was not found in the database"));
        }

        String lowercaseLogin = username.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneWithRolesByUsername(lowercaseLogin)
                .map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        /*if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }*/
        /*List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        List<GrantedAuthority> permissions = user.getAuthorities().stream()
                .flatMap(role -> ((Role)role).getPermissions().stream())
                .map(privilege -> new SimpleGrantedAuthority(privilege.getPermission()))
                .collect(Collectors.toList());


        grantedAuthorities.addAll(permissions);*/

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());
    }

    public Optional<User> findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

}
