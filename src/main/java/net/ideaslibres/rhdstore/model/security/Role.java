package net.ideaslibres.rhdstore.model.security;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private String role;

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}