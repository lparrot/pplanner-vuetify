package fr.lauparr.pplanner.server.configs;

import fr.lauparr.pplanner.server.security.NoRedirectStrategy;
import fr.lauparr.pplanner.server.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${security.public-path}")
  private String[] publicPath;

  @Value("${info.api.prefix}")
  private String apiPrefix;

  @Autowired
  private WebProperties.Resources resourceProperties;

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

  private NegatedRequestMatcher getProtectedUrls() {
    List<AntPathRequestMatcher> list = new ArrayList<>();

    for (String path : this.publicPath) {
      AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(this.apiPrefix + path);
      list.add(antPathRequestMatcher);
    }
    for (String location : this.resourceProperties.getStaticLocations()) {
      AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(location);
      list.add(antPathRequestMatcher);
    }
    return new NegatedRequestMatcher(new OrRequestMatcher(list.toArray(new AntPathRequestMatcher[0])));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
    TokenAuthenticationFilter filter = new TokenAuthenticationFilter(this.getProtectedUrls());
    filter.setAuthenticationManager(this.authenticationManagerBean());
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

  @Override
  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
