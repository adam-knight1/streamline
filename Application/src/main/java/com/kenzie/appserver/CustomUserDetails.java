
package com.kenzie.appserver;

import com.kenzie.appserver.repositories.model.UserRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * CustomUserDetails is an implementation of Spring Security's UserDetails interface,
 * providing core user information to the framework. This class is tailored to use
 * UserRecord, a custom application domain class, to populate the user details.
 *
 * This implementation allows for the integration of application-specific user entities
 * with Spring Security's authentication mechanisms. It encapsulates the user's username,
 * password, and granted authorities, which represent the roles or permissions the user
 * has within the application.
 *
 * The class also includes methods to determine whether the account
 * is expired, locked, or enabled, and whether the credentials (password) have expired.
 * These methods currently return true, indicating the account is always considered valid
 * for simplicity. They can be customized as need be.
 */

public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserRecord userRecord) {
        this.username = userRecord.getUsername();
        this.password = userRecord.getPassword();
        // authorities = userRecord.getRoles();
        //if I want to add roles to userRecord in the future like admin^^

    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}


