package de.judgeman.SpringBootWithSpringSecurityExample.Model;

import de.judgeman.SpringBootWithSpringSecurityExample.Enums.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser extends User implements UserDetails {

    public AuthenticatedUser(User user) {
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setPermissions(user.getPermissions());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for(Permission permission : getPermissions()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.toString()));
        }

        return grantedAuthorities;
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
}
