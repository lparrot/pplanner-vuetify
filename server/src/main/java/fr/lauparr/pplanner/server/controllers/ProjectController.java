package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.params.ProjectPatchParams;
import fr.lauparr.pplanner.server.pojos.api.AbstractController;
import fr.lauparr.pplanner.server.services.ProjectService;
import fr.lauparr.pplanner.server.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${info.api.prefix}/projects")
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
  public ResponseEntity getProjectById(@PathVariable("id") Long id) {
    return this.ok(this.projectService.getProjectById(id));
  }

  @PatchMapping("/{id}")
  public ResponseEntity patchProjectById(@PathVariable("id") Long id, @RequestBody ProjectPatchParams params) {
    return this.ok(this.projectService.patchProjectById(id, params));
  }

  @GetMapping("/{id}/teams")
  public ResponseEntity getTeamByProjectId(@PathVariable("id") Long id) {
    return this.ok(this.teamService.findTeamByProjectId(id));
  }

  @GetMapping("/{id}/statistics")
  public ResponseEntity getStatisticsByProjectId(@PathVariable("id") Long id) {
    return this.ok(this.projectService.getStatisticsByProjectId(id));
  }

  @GetMapping("/{id}/files")
  public ResponseEntity getProjectFilesByProjectId(@PathVariable("id") Long id) {
    return this.ok(this.projectService.getProjectFilesByProjectId(id));
  }

}
