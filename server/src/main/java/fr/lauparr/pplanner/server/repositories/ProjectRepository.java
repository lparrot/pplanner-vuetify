package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

  @Query("select p from Team t left join t.project p left join t.members m where m.id = :userId")
  List<Project> findProjectByUser(@Param("userId") Long userId);
}
