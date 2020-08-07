package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
