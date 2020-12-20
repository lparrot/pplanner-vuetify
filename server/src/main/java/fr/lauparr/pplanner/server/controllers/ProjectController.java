package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.params.ProjectPatchParams;
import fr.lauparr.pplanner.server.params.ProjectPostBoardParams;
import fr.lauparr.pplanner.server.pojos.api.AbstractController;
import fr.lauparr.pplanner.server.projections.ProjectProj;
import fr.lauparr.pplanner.server.projections.ProjectWithBoardsProj;
import fr.lauparr.pplanner.server.services.ProjectService;
import fr.lauparr.pplanner.server.services.TeamService;
import fr.lauparr.pplanner.server.utils.UtilsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${app.api.prefix}/projects")
public class ProjectController extends AbstractController {

  @Autowired
  private ProjectService projectService;
  @Autowired
  private TeamService teamService;

  @GetMapping
  public ResponseEntity getAllProjects() {
    return this.ok(this.projectService.getAllProjects());
  }

  @GetMapping("/{id}")
  public ResponseEntity getProjectById(@PathVariable("id") String id, @RequestParam(name = "boards", defaultValue = "false") boolean withBoards) {
    Class projectionClass = withBoards ? ProjectWithBoardsProj.class : ProjectProj.class;
    return this.ok(UtilsDao.convertToDto(this.projectService.getProjectById(id), projectionClass));
  }

  @PatchMapping("/{id}")
  public ResponseEntity patchProjectById(@PathVariable("id") String id, @RequestBody ProjectPatchParams params) {
    return this.ok(this.projectService.patchProjectById(id, params));
  }

  @GetMapping("/{id}/teams")
  public ResponseEntity getTeamByProjectId(@PathVariable("id") String id) {
    return this.ok(this.teamService.findTeamByProjectId(id));
  }

  @GetMapping("/{id}/statistics")
  public ResponseEntity getStatisticsByProjectId(@PathVariable("id") String id) {
    return this.ok(this.projectService.getStatisticsByProjectId(id));
  }

  @PostMapping("/{id}/boards")
  public ResponseEntity createBoardByProjectId(@PathVariable("id") String id, @RequestBody ProjectPostBoardParams params) {
    return this.ok(this.projectService.createBoardByProjectId(id, params));
  }

}
