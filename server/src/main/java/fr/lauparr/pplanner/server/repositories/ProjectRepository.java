package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
