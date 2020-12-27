package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.lauparr.pplanner.server.entities.abstracts.UUIDModifiableEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(callSuper = false)
public class User extends UUIDModifiableEntity implements UserDetails {

  @NotBlank
  private String email;

  @JsonIgnore
  private String password;

  @ManyToOne
  private Group group;

  @JsonManagedReference(value = "user_member_ref")
  @OneToOne(mappedBy = "user")
  private Member member;

  @Builder
  public User(String email, String password, Group group) {
    this.email = email;
    this.password = password;
    this.group = group;
  }

  public Claims getClaims() {
    Claims claims = Jwts.claims();
    claims.put("fullname", this.getMember() != null ? this.getMember().getFullname() : null);
    claims.put("email", this.getEmail());
    claims.put("avatar", this.member.getAvatar());
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
