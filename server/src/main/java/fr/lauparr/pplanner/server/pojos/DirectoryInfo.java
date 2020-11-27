package fr.lauparr.pplanner.server.pojos;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

@Data
public class DirectoryInfo {

  private String name;

  private String path;

  private boolean empty;

  public DirectoryInfo(File file, String basePath) {
    name = file.getName();
    path = StringUtils.remove(file.getPath(), basePath);
  }
}
