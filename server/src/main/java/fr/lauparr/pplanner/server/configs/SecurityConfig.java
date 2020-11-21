package fr.lauparr.pplanner.server.configs;

import fr.lauparr.pplanner.server.security.NoRedirectStrategy;
import fr.lauparr.pplanner.server.security.TokenAuthenticationFilter;
import fr.lauparr.pplanner.server.security.TokenAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Value("${security.public-path}")
  private String[] publicPath;
  @Value("${info.api.prefix}")
  private String apiPrefix;

  @Autowired
  private TokenAuthenticationProvider authenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .headers().httpStrictTransportSecurity().disable()

      .and()
      .requestCache().requestCache(new NullRequestCache())

      .and()
      .exceptionHandling()
      .defaultAuthenticationEntryPointFor(this.forbiddenEntryPoint(), this.getProtectedUrls())

      .and()
      .authenticationProvider(this.authenticationProvider)
      .addFilterBefore(this.restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
      .authorizeRequests()
      .requestMatchers(this.getProtectedUrls())
      .authenticated()

      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .csrf().disable()
      .httpBasic().disable()
      .formLogin().disable()
      .logout().disable();
  }

  NegatedRequestMatcher getProtectedUrls() {
    return new NegatedRequestMatcher(new OrRequestMatcher(
      Arrays.stream(this.publicPath).map(path -> new AntPathRequestMatcher(this.apiPrefix + path)).collect(Collectors.toList())
    ));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
    TokenAuthenticationFilter filter = new TokenAuthenticationFilter(this.getProtectedUrls());
    filter.setAuthenticationManager(this.authenticationManager());
    filter.setAuthenticationSuccessHandler(this.successHandler());
    return filter;
  }

  @Bean
  SimpleUrlAuthenticationSuccessHandler successHandler() {
    SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
    successHandler.setRedirectStrategy(new NoRedirectStrategy());
    return successHandler;
  }

  @Bean
  FilterRegistrationBean disableAutoRegistration(TokenAuthenticationFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean(filter);
    registration.setEnabled(false);
    return registration;
  }

  @Bean
  AuthenticationEntryPoint forbiddenEntryPoint() {
    return new HttpStatusEntryPoint(FORBIDDEN);
  }
}
