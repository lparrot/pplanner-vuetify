package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.pojos.DirectoryInfo;
import fr.lauparr.pplanner.server.pojos.FileInfo;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileManagerService {

  @Value("${app.global.projects-path}")
  private String projectsPath;

  public List<DirectoryInfo> getProjectDirectoriesByProjectId(Long id, String key) {
    File[] directories = this.getDirectoryByProjectIdAndEncodedKey(id, key).listFiles(File::isDirectory);

    if (directories != null) {
      return Arrays.stream(directories).map(directory -> new DirectoryInfo(directory, String.format("%s/%s", this.projectsPath, id))).collect(Collectors.toList());
    }

    return new ArrayList<>();
  }

  public Object getProjectDirectoryContentsByProjectId(Long id, String key) {
    File[] files = this.getDirectoryByProjectIdAndEncodedKey(id, key).listFiles();

    if (files != null) {
      return Arrays.stream(files).map(file -> new FileInfo(file, String.format("%s/%s", this.projectsPath, id))).collect(Collectors.toList());
    }

    return new ArrayList<>();
  }

  public File getDirectoryByProjectIdAndEncodedKey(Long id, String key) {
    String path = DirectoryInfo.decode(key);

    File file = new File(String.format("%s/%s/%s", this.projectsPath, id, path));

    if (!file.exists()) {
      try {
        FileUtils.forceMkdir(file);
      } catch (IOException e) {
        throw new MessageException(String.format("Erreur lors de la création du dossier %s", file.getPath()));
      }
    }

    return file;
  }
}
