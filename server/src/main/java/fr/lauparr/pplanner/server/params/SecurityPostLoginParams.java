package fr.lauparr.pplanner.server.params;

import lombok.Data;

@Data
public class SecurityPostLoginParams {
  private String username;
  private String password;
}
