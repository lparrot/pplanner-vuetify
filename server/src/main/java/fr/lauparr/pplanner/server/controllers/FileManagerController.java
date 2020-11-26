package fr.lauparr.pplanner.server.controllers;

import fr.lauparr.pplanner.server.pojos.api.AbstractController;
import fr.lauparr.pplanner.server.services.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${info.api.prefix}/file_manager")
public class FileManagerController extends AbstractController {

  @Autowired
  private FileManagerService fileManagerService;

  @GetMapping("/projects/{id}")
  public ResponseEntity getFoldersByProjectId(@PathVariable("id") Long id) {
    return this.ok(this.fileManagerService.getFoldersByProjectId(id));
  }


}
