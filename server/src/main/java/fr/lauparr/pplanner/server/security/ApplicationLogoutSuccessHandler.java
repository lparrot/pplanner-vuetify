package fr.lauparr.pplanner.server.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApplicationLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    System.out.println("ApplicationLogoutSuccessHandler.onLogoutSuccess");
    super.onLogoutSuccess(request, response, authentication);
  }
}
