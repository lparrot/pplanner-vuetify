package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.Member;
import fr.lauparr.pplanner.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  @Query("select m from Member m where m.user = :user")
  Optional<Member> findMemberByUser(@Param("user") User user);

}
