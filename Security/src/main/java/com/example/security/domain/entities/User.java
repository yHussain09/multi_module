package com.example.security.domain.entities;

import com.example.corejava.domain.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "user")
public class User extends EntityBase implements UserDetails, Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    @NotEmpty(message = "Username cannot be empty or null!")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password cannot be empty or null!")
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "activated", length = 1)
    private String activated;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "enabled", length = 1)
    private String enabled;

    @Column(name = "last_password_reset_date", length = 6)
    private Timestamp lastPasswordResetDate;

    @Column(name = "account_non_expired", length = 1)
    private String accountNonExpired;


    @Column(name = "account_non_locked", length = 1)
    private String accountNonLocked;

    @Column(name = "credentials_non_expired", length = 1)
    private String credentialsNonExpired;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public User() { }

    public User(@NotEmpty(message = "Username cannot be empty or null!") String username, @NotEmpty(message = "Password cannot be empty or null!") String password, String firstName, String lastName, String email, String activated, String phoneNumber, String enabled, Timestamp lastPasswordResetDate, String accountNonExpired, String accountNonLocked, String credentialsNonExpired, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    @Override
    public boolean isEnabled() {
        return "Y".equals(this.enabled);
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return "Y".equals(this.accountNonExpired);
    }

    public void setAccountNonLocked(String accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return "Y".equals(this.accountNonLocked);
    }

    public void setAccountNonExpired(String accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return "Y".equals(this.credentialsNonExpired);
    }

    public void setCredentialsNonExpired(String credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : this.roles) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
            role.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                    .forEach(authorities::add);
        }

        return authorities;
    }
}
