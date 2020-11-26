package fr.lauparr.pplanner.server.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
public class FileManagerService {

  public final static String PROJECT_FILE_PATH = "/projects/%s";

  @Value("${app.global.work-path}")
  private String workPath;

  public String[] getFoldersByProjectId(Long id) {
    File file = new File(Paths.get(this.workPath, String.format(FileManagerService.PROJECT_FILE_PATH, id)).toUri());
    if (file.exists()) {
      return file.list((current, name) -> new File(current, name).isDirectory());
    }
    return new String[]{};
  }

}
