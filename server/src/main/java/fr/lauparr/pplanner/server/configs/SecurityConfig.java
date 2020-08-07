package fr.lauparr.pplanner.server.configs;

import fr.lauparr.pplanner.server.security.*;
import fr.lauparr.pplanner.server.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${info.api.prefix:/api}")
  private String apiPrefix;

  @Autowired
  private ApplicationAuthenticationProvider authenticationProvider;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private ApplicationLoginSuccessHandler loginSuccessHandler;
  @Autowired
  private ApplicationLoginFailureHandler loginFailureHandler;
  @Autowired
  private ApplicationLogoutSuccessHandler logoutSuccessHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .headers().httpStrictTransportSecurity().disable()
      .and()
      .authenticationProvider(this.authenticationProvider)
      .exceptionHandling().authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
      .and()
      .requestCache().requestCache(new NullRequestCache())
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .formLogin().loginProcessingUrl(this.apiPrefix + "/security/login")
      .successHandler(this.loginSuccessHandler).failureHandler(this.loginFailureHandler)
      .and()
      .logout().logoutUrl(this.apiPrefix + "/security/logout").logoutSuccessHandler(this.logoutSuccessHandler)
      .and()
      .addFilterAt(new ApplicationUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(new JwtAuthenticationFilter(this.jwtService), UsernamePasswordAuthenticationFilter.class);

    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.antMatcher(this.apiPrefix + "/**").authorizeRequests();

    urlRegistry
      .antMatchers(this.apiPrefix + "/security/login").permitAll()
      .antMatchers(this.apiPrefix + "/security/logout").permitAll()
      .anyRequest().not().hasAuthority("ROLE_ANONYMOUS");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
