package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.lauparr.pplanner.server.pojos.ModifiableEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends ModifiableEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  @JsonIgnore
  private String password;

  @ManyToOne
  private Group group;

  private String email;

  private String firstName;

  private String lastName;

  private String avatar;

  private LocalDate birthday;

  @Builder
  public User(String username, String password, String email, String firstName, String lastName, LocalDate birthday, String avatar, Group group) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.avatar = avatar;
    this.group = group;
  }

  public Claims getClaims() {
    Claims claims = Jwts.claims();
    claims.put("fullname", String.format("%s %s", this.firstName, this.lastName));
    claims.put("email", this.email);
    claims.put("avatar", this.avatar);
    return claims;
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.group.getRoles();
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
