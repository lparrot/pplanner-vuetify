package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.Project;
import fr.lauparr.pplanner.server.entities.ProjectBoard;
import fr.lauparr.pplanner.server.entities.User;
import fr.lauparr.pplanner.server.entities.abstracts.ModifiableEntity;
import fr.lauparr.pplanner.server.params.ProjectPatchParams;
import fr.lauparr.pplanner.server.params.ProjectPostBoardParams;
import fr.lauparr.pplanner.server.pojos.Statistics;
import fr.lauparr.pplanner.server.projections.ProjectProj;
import fr.lauparr.pplanner.server.repositories.ProjectBoardRepository;
import fr.lauparr.pplanner.server.repositories.ProjectRepository;
import fr.lauparr.pplanner.server.utils.UtilsBean;
import fr.lauparr.pplanner.server.utils.UtilsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private ProjectBoardRepository projectBoardRepository;

  public List<ProjectProj> getAllProjects() {
    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return UtilsDao.convertListDto(this.projectRepository.findProjectByUser(principal.getId()), ProjectProj.class);
  }

  public Project patchProjectById(String id, ProjectPatchParams params) {
    Project project = this.getProjectById(id);
    UtilsBean.copyProperties(params, project, true);
    this.projectRepository.save(project);
    return project;
  }

  public Statistics getStatisticsByProjectId(String id) {
    Project project = this.getProjectById(id);
    Statistics statistics = new Statistics();
    statistics.setMembers(project.getTeam().getMembers().size());
    return statistics;
  }

  public ProjectBoard createBoardByProjectId(String id, ProjectPostBoardParams params) {
    Project project = this.getProjectById(id);
    ProjectBoard projectBoard = new ProjectBoard();
    projectBoard.setName(params.getName());
    projectBoard.setVisibility(params.getVisibility());

    projectBoard.setProject(project);
    project.getBoards().add(projectBoard);

    projectBoard = this.projectBoardRepository.save(projectBoard);

    return projectBoard;
  }

  public Project getProjectById(String id) {
    return this.projectRepository.findById(id, ModifiableEntity.whereActives()).orElseThrow(() -> new EntityNotFoundException(String.format("Impossible de trouver le projet ayant l'id %s", id)));
  }
}
