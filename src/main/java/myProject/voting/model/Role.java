package myProject.voting.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Secret_Hero on 05.09.2017.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

}
