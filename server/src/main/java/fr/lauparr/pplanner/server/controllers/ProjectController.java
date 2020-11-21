package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.params.ProjectPatchParams;
import fr.lauparr.pplanner.server.pojos.AbstractController;
import fr.lauparr.pplanner.server.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${info.api.prefix}/projects")
public class ProjectController extends AbstractController {

  @Autowired
  private ProjectService projectService;

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

}
