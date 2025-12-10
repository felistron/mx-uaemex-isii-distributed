package mx.uaemex.fi.backend.logic.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.backend.persistence.model.Empleado;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class UserDetailsAdapter implements UserDetails {
    private final Empleado empleado;
    private final String hashedPassword;

    @NonNull
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roles = new ArrayList<SimpleGrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return roles;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @NonNull
    @Override
    public String getUsername() {
        return empleado.getRfc();
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
