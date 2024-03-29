package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.User;
import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.pojos.api.JwtToken;
import fr.lauparr.pplanner.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SecurityService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private TokenService tokenService;

  public JwtToken login(String username, String password) {
    User user = this.userRepository.findByEmailAndDateSuppressionIsNull(username).orElse(null);

    // Si aucun utilisateur n'a été trouvé à partir du login
    if (user == null) {
      throw new MessageException("Mauvais login ou mot de passe");
    }

    // Si le mot de passe entré ne correspond pas à celui en base de données
    if (!this.passwordEncoder.matches(password, user.getPassword())) {
      throw new MessageException("Mauvais login ou mot de passe");
    }

    String token = this.tokenService.createToken(user);

    return JwtToken.builder()
      .token(token)
      .claims(this.tokenService.getClaims(token))
      .build();
  }

  public User getUserData(String name) {
    return this.userRepository.findByEmailAndDateSuppressionIsNull(name).orElseThrow(() -> new EntityNotFoundException("L'utilisateur n'existe pas "));
  }
}
