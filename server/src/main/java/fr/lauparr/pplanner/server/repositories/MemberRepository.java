package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByUsernameAndDateSuppressionIsNull(String username);
}
