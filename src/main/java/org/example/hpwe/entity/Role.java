package org.example.hpwe.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER, BANNED;

    @Override
    public String getAuthority() {
        return name();
    }
}
