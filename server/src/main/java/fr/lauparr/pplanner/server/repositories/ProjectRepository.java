package fr.lauparr.pplanner.server.repositories;

import fr.lauparr.pplanner.server.entities.Project;
import fr.lauparr.pplanner.server.repositories.customs.AppRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends AppRepository<Project, String> {

  @Query("select p from Team t left join t.project p left join t.members m left join m.user u where u.id = :userId and p.dateSuppression is null")
  List<Project> findProjectByUser(@Param("userId") String userId);
}
