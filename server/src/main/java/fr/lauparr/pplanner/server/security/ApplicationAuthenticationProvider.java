package fr.lauparr.pplanner.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationAuthenticationProvider extends DaoAuthenticationProvider {

  private final static String BAD_LOGIN_OR_PASSWORD = "Bad login or password ...";

  @Autowired
  private ApplicationUserDetailsService userDetailsService;

  @PostConstruct
  public void init() {
    this.setUserDetailsService(this.userDetailsService);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    System.out.println("ApplicationAuthenticationProvider.authenticate");
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    String username = token.getName();

    UserDetails user = this.userDetailsService.loadUserByUsername(username);

    if (user == null) {
      throw new BadCredentialsException(BAD_LOGIN_OR_PASSWORD);
    }

    return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }
}
