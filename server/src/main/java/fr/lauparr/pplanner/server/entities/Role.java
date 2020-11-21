package fr.lauparr.pplanner.server.entities;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

  ROLE_ADMIN("Role d'administration"),
  ROLE_USER("Role d'utilisation normale");

  @Getter
  private final String description;

  Role(String description) {
    this.description = description;
  }

  @Override
  public String getAuthority() {
    return this.name();
  }
}
