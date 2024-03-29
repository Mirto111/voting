package ru.projects.voting.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Роль пользователя.
 */

public enum Role implements GrantedAuthority {
  USER,
  ADMIN;

  @Override
  public String getAuthority() {
    return "ROLE_" + name();
  }

}
