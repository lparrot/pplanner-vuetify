package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.User;
import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.pojos.JwtToken;
import fr.lauparr.pplanner.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtService jwtService;

  public JwtToken login(String username, String password) {
    User user = this.userRepository.findByUsernameAndDateSuppressionIsNull(username).orElse(null);

    // Si aucun utilisateur n'a été trouvé à partir du login
    if (user == null) {
      throw new MessageException("Mauvais login ou mot de passe");
    }

    // Si le mot de passe entré ne correspond pas à celui en base de données
    if (!this.passwordEncoder.matches(password, user.getPassword())) {
      throw new MessageException("Mauvais login ou mot de passe");
    }

    String token = this.jwtService.createToken(user);

    return JwtToken.builder()
      .token(token)
      .claims(this.jwtService.getClaims(token))
      .build();
  }

}
