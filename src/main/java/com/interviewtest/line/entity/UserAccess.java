
package com.interviewtest.line.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Oki-Peter
 */
public class UserAccess extends org.springframework.security.core.userdetails.User {
    
    private UsersAccount user;
    
    public UserAccess(UsersAccount user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), authorities);
        this.user = user;
    }

    public UsersAccount getUser() {
        return this.user;
    }

    public void setUser(UsersAccount user) {
        this.user = user;
    }
    
}
