package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.Project;
import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.params.ProjectPatchParams;
import fr.lauparr.pplanner.server.repositories.ProjectRepository;
import fr.lauparr.pplanner.server.utils.UtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  public List<Project> getAllProjects() {
    return this.projectRepository.findAll(Sort.by("name"));
  }

  public Project getProjectById(Long id) {
    return this.projectRepository.findById(id).orElseThrow(() -> new MessageException("Unable to find project with id " + id));
  }

  public Project patchProjectById(Long id, ProjectPatchParams params) {
    Project project = this.getProjectById(id);
    UtilsBean.copyProperties(params, project, true);
    this.projectRepository.save(project);
    return project;
  }

}
