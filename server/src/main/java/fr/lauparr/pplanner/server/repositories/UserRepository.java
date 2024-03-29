package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmailAndDateSuppressionIsNull(String username);
}
