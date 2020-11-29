package fr.lauparr.pplanner.server.security;

import fr.lauparr.pplanner.server.entities.User;
import fr.lauparr.pplanner.server.exceptions.TaggedApplicationException;
import fr.lauparr.pplanner.server.utils.UtilsDateTime;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class TokenAuthenticationService {

  public static final String USERNAME = "username";
  private static final SignatureAlgorithm signature = SignatureAlgorithm.HS256;
  @Value("${socle.security.jwt.token-prefix:Bearer}")
  private String tokenPrefix;
  @Value("${socle.security.jwt.header-name:Authorization}")
  private String headerName;
  @Value("${socle.security.jwt.secret-key:S3cr3t_K3y*}")
  private String secretKey;

  @Autowired
  private AppUserDetailsService userDetailsService;

  /**
   * Création d'un token JWT avec les informations du compte utilisateur
   */
  public String createToken(User utilisateur) {
    // Aucun token si l'utilisateur est vide
    if (utilisateur == null) {
      return null;
    }
    // Récupération des informations utilisateur
    Claims claims = utilisateur.getClaims();

    // Récupération des id des rôles de l'utilisateur
    if (utilisateur.getAuthorities() != null) {
      claims.put("roles", utilisateur.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    // Récupération du nom de compte de l'utilisateur
    claims.put(TokenAuthenticationService.USERNAME, utilisateur.getUsername());
    claims.setSubject(utilisateur.getUsername());

    return Jwts.builder().setClaims(claims).signWith(TokenAuthenticationService.signature, this.secretKey).compact();
  }

  /**
   * Création d'un token JWT avec expiration
   */
  public String createToken(Claims claims, LocalDateTime expiration) {
    JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims);

    if (expiration != null) {
      jwtBuilder.setExpiration(UtilsDateTime.convertLocalDateTimeToDate(expiration));
    }

    return jwtBuilder.signWith(TokenAuthenticationService.signature, this.secretKey).compact();
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
        Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        if (claims.get(TokenAuthenticationService.USERNAME) == null) {
          claims.put(TokenAuthenticationService.USERNAME, claims.getSubject());
        }
        return claims;
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
