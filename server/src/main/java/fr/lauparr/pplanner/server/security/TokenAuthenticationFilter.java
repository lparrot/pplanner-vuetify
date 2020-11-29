package fr.lauparr.pplanner.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lauparr.pplanner.server.entities.User;
import fr.lauparr.pplanner.server.exceptions.AbstractAppException;
import fr.lauparr.pplanner.server.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  @Value("${app.security.jwt.header-name:Authorization}")
  private String headerName;
  @Value("${app.security.jwt.token-prefix:Bearer}")
  private String tokenPrefix;
  @Autowired
  private TokenService tokenService;

  public TokenAuthenticationFilter() {
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String header = request.getHeader(this.headerName);
    boolean doFilter = true;
    if (header != null && header.startsWith(this.tokenPrefix)) {
      try {
        User user = this.tokenService.getUser(request);
        if (user != null) {
          UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      } catch (AbstractAppException e) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(e.getDetails()));
        doFilter = false;
      } catch (Exception e) {
        SecurityContextHolder.clearContext();
      }
    }
    if (doFilter) {
      chain.doFilter(request, response);
    }
  }
}
