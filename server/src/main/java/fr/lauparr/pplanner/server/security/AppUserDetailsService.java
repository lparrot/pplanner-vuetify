package fr.lauparr.pplanner.server.security;

import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository securityUserDao;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return this.securityUserDao.findByUsernameAndDateSuppressionIsNull(username).orElseThrow(() -> new MessageException("Mauvais login ou mot de passe"));
  }

}
