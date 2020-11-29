package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.params.SecurityPostLoginParams;
import fr.lauparr.pplanner.server.pojos.api.AbstractController;
import fr.lauparr.pplanner.server.services.SecurityService;
import fr.lauparr.pplanner.server.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "${app.api.prefix}/security")
public class SecurityController extends AbstractController {

  @Autowired
  private SecurityService securityService;
  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity postLogin(@RequestBody SecurityPostLoginParams params) {
    return this.ok(this.securityService.login(params.getUsername(), params.getPassword()));
  }

  @GetMapping("/user")
  public ResponseEntity getUser(HttpServletRequest request) {
    return this.ok(this.tokenService.getClaims(request));
  }

}
