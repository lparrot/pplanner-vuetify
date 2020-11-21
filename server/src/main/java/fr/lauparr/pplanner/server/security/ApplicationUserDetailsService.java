package fr.lauparr.pplanner.server.security;

import fr.lauparr.pplanner.server.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Autowired
  public ApplicationUserDetailsService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return this.memberRepository.findByUsernameAndDateSuppressionIsNull(username).orElseThrow(() -> new UsernameNotFoundException("Bad login or password"));
  }
}
