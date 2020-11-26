package fr.lauparr.pplanner.server.pojos.api;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;

@Data
public class JwtToken {
  private String token;
  private Claims claims;

  @Builder
  public JwtToken(String token, Claims claims) {
    this.token = token;
    this.claims = claims;
  }
}
