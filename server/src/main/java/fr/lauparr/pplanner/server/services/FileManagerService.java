package fr.lauparr.pplanner.server.services;

import fr.lauparr.pplanner.server.exceptions.MessageException;
import fr.lauparr.pplanner.server.pojos.DirectoryInfo;
import fr.lauparr.pplanner.server.pojos.FileInfo;
import org.apache.commons.io.FilenameUtils;
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

  public FileInfo getRootFileByProjectId(String id) {
    File directory = this.getDirectoryByProjectIdAndEncodedKey(id, null);
    return new FileInfo(directory);
  }

  public List<DirectoryInfo> getProjectDirectoriesByProjectId(String id, String key) {
    File[] directories = this.getDirectoryByProjectIdAndEncodedKey(id, key).listFiles(File::isDirectory);

    if (directories != null) {
      return Arrays.stream(directories).map(DirectoryInfo::new).collect(Collectors.toList());
    }

    return new ArrayList<>();
  }

  public List<FileInfo> getProjectDirectoryContentsByProjectId(String id, String key) {
    File[] files = this.getDirectoryByProjectIdAndEncodedKey(id, key).listFiles();

    if (files != null) {
      return Arrays.stream(files).map(FileInfo::new).collect(Collectors.toList());
    }

    return new ArrayList<>();
  }

  public File getDirectoryByProjectIdAndEncodedKey(String id, String key) {
    String path;
    if (key != null) {
      path = DirectoryInfo.decode(key);
    } else {
      path = FilenameUtils.normalize(String.format("%s/%s", this.projectsPath, id));
    }

    File file = new File(path);

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
