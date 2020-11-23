package fr.lauparr.pplanner.server.security;

import fr.lauparr.pplanner.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public ApplicationUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return this.userRepository.findByUsernameAndDateSuppressionIsNull(username).orElseThrow(() -> new UsernameNotFoundException("Bad login or password"));
  }
}
