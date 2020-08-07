package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.pojos.AbstractController;
import fr.lauparr.pplanner.server.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${info.api.prefix}/projects")
public class ProjectController extends AbstractController {

  @Autowired
  private ProjectRepository projectRepository;

  @GetMapping
  public ResponseEntity getAll() {
    return ok(projectRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity getById(@PathVariable Long id) {
    return ok(projectRepository.findById(id));
  }

}
