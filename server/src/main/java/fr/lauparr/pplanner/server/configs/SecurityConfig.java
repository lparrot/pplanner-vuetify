package fr.lauparr.pplanner.server.configs;

import fr.lauparr.pplanner.server.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${app.api.prefix:/api}")
  private String apiPrefix;
  @Autowired
  private TokenAuthenticationFilter authenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Désactivation du jeton CSRF
    http.csrf().disable().httpBasic().disable();

    // Suppression des headers type HSTS de Spring Security
    http.headers().httpStrictTransportSecurity().disable();

    // Retourne une erreur 401 si un problème d'authentification survient
    http.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED));

    // Pas de cache car pas de notion de session
    http.requestCache().requestCache(new NullRequestCache());
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Réponse statut 200 lors du handler logout
    http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));

    // Ajout du filter JWT
    http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    // Prefixe d'url pour la sécurité
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.antMatcher(this.apiPrefix + "/**").authorizeRequests();

    // Sécurité sur toutes les api
    urlRegistry
      .antMatchers(HttpMethod.POST, this.apiPrefix + "/auth/login**").permitAll()
      .antMatchers(HttpMethod.GET, this.apiPrefix + "/auth/user**").permitAll()
      .anyRequest().not().hasAuthority("ROLE_ANONYMOUS");
  }

  /**
   * Liste des resources a ignorer dans la sécurité
   */
  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
      .antMatchers("/sw.js");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Override
  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
