package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.User;
import fr.lauparr.pplanner.server.exceptions.TaggedApplicationException;
import fr.lauparr.pplanner.server.security.AppUserDetailsService;
import fr.lauparr.pplanner.server.utils.UtilsDateTime;
import io.jsonwebtoken.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class TokenService {

  private static final SignatureAlgorithm signature = SignatureAlgorithm.HS256;
  @Getter
  @Value("${security.jwt.token-prefix:Bearer}")
  private String tokenPrefix;
  @Getter
  @Value("${security.jwt.header-name:Authorization}")
  private String headerName;
  @Getter
  @Value("${security.jwt.secret-key:S3cr3t_K3y*}")
  private String secretKey;

  @Autowired
  private AppUserDetailsService userDetailsService;

  /**
   * Création d'un token JWT avec les informations du compte utilisateur
   */
  public String createToken(User user) {
    // Aucun token si l'utilisateur est vide
    if (user == null) {
      return null;
    }
    // Récupération des informations utilisateur
    Claims claims = user.getClaims();

    // Récupération des id des rôles de l'utilisateur
    if (user.getAuthorities() != null) {
      claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    // Récupération du nom de compte de l'utilisateur
    claims.setSubject(user.getUsername());

    return Jwts.builder().setClaims(claims).signWith(TokenService.signature, this.secretKey).compact();
  }

  /**
   * Création d'un token JWT avec expiration
   */
  public String createToken(Claims claims, LocalDateTime expiration) {
    JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims);

    if (expiration != null) {
      jwtBuilder.setExpiration(UtilsDateTime.convertLocalDateTimeToDate(expiration));
    }

    return jwtBuilder.signWith(TokenService.signature, this.secretKey).compact();
  }

  /**
   * Récupération de l'utilisateur par rapport au token
   */
  public User getUser(String token) {
    Claims claims = this.getClaims(token);
    return (User) this.userDetailsService.loadUserByUsername(claims.getSubject());
  }

  /**
   * Récupération de l'utilisateur par rapport au token passé dans les headers de la requête
   */
  public User getUser(HttpServletRequest request) {
    String token = request.getHeader(this.headerName);
    return this.getUser(token);
  }

  /**
   * Récupération des informations contenues dans le token
   */
  public Claims getClaims(String token) {
    if (token != null) {
      token = token.replace(String.format("%s ", this.tokenPrefix), "");
      try {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
      } catch (JwtException e) {
        throw new TaggedApplicationException("jwt", "Erreur lors de la conversion du token: " + e.getMessage());
      }
    }
    return null;
  }

  /**
   * Récupération des informations contenues dans le token passé dans les headers de la requête
   */
  public Claims getClaims(HttpServletRequest request) {
    return this.getClaims(request.getHeader(this.headerName));
  }
}
