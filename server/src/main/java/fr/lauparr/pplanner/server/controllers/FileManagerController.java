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
@RequestMapping(value = "${app.api.prefix}/file_manager")
public class FileManagerController extends AbstractController {

  @Autowired
  private FileManagerService fileManagerService;

  @GetMapping("/projects/{id}/root")
  public ResponseEntity getRootFileByProjectId(@PathVariable("id") Long id) {
    return this.ok(this.fileManagerService.getRootFileByProjectId(id));
  }

  @GetMapping("/projects/{id}/files/{key}")
  public ResponseEntity getProjectDirectoriesByProjectId(@PathVariable("id") Long id, @PathVariable(value = "key") String key) {
    return this.ok(this.fileManagerService.getProjectDirectoriesByProjectId(id, key));
  }

  @GetMapping("/projects/{id}/files/{key}/contents")
  public ResponseEntity getProjectDirectoryContentsByProjectId(@PathVariable("id") Long id, @PathVariable(value = "key") String key) {
    return this.ok(this.fileManagerService.getProjectDirectoryContentsByProjectId(id, key));
  }

}
