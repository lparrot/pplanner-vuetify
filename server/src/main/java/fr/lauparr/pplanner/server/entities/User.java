package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.lauparr.pplanner.server.entities.abstracts.UUIDModifiableEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends UUIDModifiableEntity implements UserDetails {

  @NotBlank
  private String email;

  @JsonIgnore
  private String password;

  @ManyToOne
  private Group group;

  private String firstName;

  private String lastName;

  private String avatar;

  private LocalDate birthday;

  @JsonManagedReference(value = "user_member_ref")
  @OneToOne(mappedBy = "user")
  private Member member;

  @Builder
  public User(String email, String password, String firstName, String lastName, LocalDate birthday, String avatar, Group group) {
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
  public String getUsername() {
    return this.getEmail();
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.group == null) {
      return AuthorityUtils.NO_AUTHORITIES;
    }
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
