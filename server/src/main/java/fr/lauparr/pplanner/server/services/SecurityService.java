package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.Member;
import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.pojos.JwtToken;
import fr.lauparr.pplanner.server.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtService jwtService;

  public JwtToken login(String username, String password) {
    Member member = this.memberRepository.findByUsernameAndDateSuppressionIsNull(username).orElse(null);

    // Si aucun utilisateur n'a été trouvé à partir du login
    if (member == null) {
      throw new MessageException("Mauvais login ou mot de passe");
    }

    // Si le mot de passe entré ne correspond pas à celui en base de données
    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new MessageException("Mauvais login ou mot de passe");
    }

    String token = jwtService.createToken(member);

    return JwtToken.builder()
      .token(token)
      .claims(jwtService.getClaims(token))
      .build();
  }

}
