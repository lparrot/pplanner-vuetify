package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.Member;
import fr.lauparr.pplanner.server.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  @Autowired
  private MemberRepository memberRepository;

  public Member login(String username, String password) {
//    Member member = this.memberRepository.findByUsernameAndDateSuppressionIsNull(username).;

    return Member.builder().build();
  }

}
