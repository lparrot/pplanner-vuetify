package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.entities.Project;
import fr.lauparr.pplanner.server.entities.User;
import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.params.ProjectPatchParams;
import fr.lauparr.pplanner.server.pojos.DirectoryInfo;
import fr.lauparr.pplanner.server.pojos.Statistics;
import fr.lauparr.pplanner.server.projections.ProjectSimpleProjection;
import fr.lauparr.pplanner.server.repositories.ProjectRepository;
import fr.lauparr.pplanner.server.utils.UtilsBean;
import fr.lauparr.pplanner.server.utils.UtilsDao;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  @Value("${app.global.projects-path}")
  private String projectsPath;

  public List<ProjectSimpleProjection> getAllProjects() {
    final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return UtilsDao.convertListDto(this.projectRepository.findProjectByUser(principal.getId()), ProjectSimpleProjection.class);
  }

  public Project patchProjectById(Long id, ProjectPatchParams params) {
    Project project = this.getProjectById(id);
    UtilsBean.copyProperties(params, project, true);
    this.projectRepository.save(project);
    return project;
  }

  public Statistics getStatisticsByProjectId(Long id) {
    Project project = getProjectById(id);
    Statistics statistics = new Statistics();
    statistics.setMembers(project.getTeam().getMembers().size());
    return statistics;
  }

  public List<DirectoryInfo> getProjectFilesByProjectId(Long id, String rootPath) {
    File file = new File(String.format("%s/%s/%s", projectsPath, id, rootPath == null ? "" : rootPath));

    if (!file.exists()) {
      try {
        FileUtils.forceMkdir(file);
      } catch (IOException e) {
        throw new MessageException(String.format("Erreur lors de la création du dossier %s", file.getPath()));
      }
    }

    final File[] directories = file.listFiles(File::isDirectory);

    if (directories != null) {
      return Arrays.stream(directories).map(directory -> new DirectoryInfo(directory, String.format("%s/%s", projectsPath, id))).collect(Collectors.toList());
    }

    return new ArrayList<>();
  }

  public Project getProjectById(Long id) {
    return this.projectRepository.findById(id).orElseThrow(() -> new MessageException(String.format("Impossible de trouver le projet ayant l'id %s", id)));
  }
}
