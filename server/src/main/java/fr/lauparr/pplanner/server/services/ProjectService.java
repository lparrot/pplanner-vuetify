package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.Project;
import fr.lauparr.pplanner.server.entities.User;
import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.params.ProjectPatchParams;
import fr.lauparr.pplanner.server.pojos.Statistics;
import fr.lauparr.pplanner.server.projections.ProjectSimpleProjection;
import fr.lauparr.pplanner.server.repositories.ProjectRepository;
import fr.lauparr.pplanner.server.utils.UtilsBean;
import fr.lauparr.pplanner.server.utils.UtilsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  public List<ProjectSimpleProjection> getAllProjects() {
    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return UtilsDao.convertListDto(this.projectRepository.findProjectByUser(principal.getId()), ProjectSimpleProjection.class);
  }

  public Project patchProjectById(Long id, ProjectPatchParams params) {
    Project project = this.getProjectById(id);
    UtilsBean.copyProperties(params, project, true);
    this.projectRepository.save(project);
    return project;
  }

  public Statistics getStatisticsByProjectId(Long id) {
    Project project = this.getProjectById(id);
    Statistics statistics = new Statistics();
    statistics.setMembers(project.getTeam().getMembers().size());
    return statistics;
  }

  public Project getProjectById(Long id) {
    return this.projectRepository.findById(id).orElseThrow(() -> new MessageException(String.format("Impossible de trouver le projet ayant l'id %s", id)));
  }
}
