package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.ProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectBoardRepository extends JpaRepository<ProjectBoard, String> {
}
