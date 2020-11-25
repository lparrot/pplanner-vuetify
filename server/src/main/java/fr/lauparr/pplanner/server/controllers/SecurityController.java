package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.params.SecurityPostLoginParams;
import fr.lauparr.pplanner.server.pojos.AbstractController;
import fr.lauparr.pplanner.server.services.JwtService;
import fr.lauparr.pplanner.server.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "${info.api.prefix}/security")
public class SecurityController extends AbstractController {

  @Autowired
  private SecurityService securityService;
  @Autowired
  private JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity postLogin(@RequestBody SecurityPostLoginParams params) {
    return ok(securityService.login(params.getUsername(), params.getPassword()));
  }

  @GetMapping("/user")
  public ResponseEntity getUser(HttpServletRequest request) {
    return ok(jwtService.getClaims(request));
  }

}
