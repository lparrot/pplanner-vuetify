package fr.lauparr.pplanner.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lauparr.pplanner.server.entities.Member;
import fr.lauparr.pplanner.server.exceptions.AbstractAppException;
import fr.lauparr.pplanner.server.services.JwtService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    System.out.println("JwtAuthenticationFilter.doFilterInternal");
    String header = request.getHeader(this.jwtService.getHeaderName());
    boolean doFilter = true;
    if (header != null && header.startsWith(this.jwtService.getTokenPrefix())) {
      try {
        Member user = this.jwtService.getUser(request);
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
