package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, String> {

  Team findByProjectId(String id);
}
