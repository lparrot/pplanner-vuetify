package fr.lauparr.pplanner.server.pojos;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Base64;

@Data
public class DirectoryInfo {

  private String key;

  private String name;

  private String path;

  private boolean empty;

  @SneakyThrows
  public DirectoryInfo(File file, String basePath) {
    this.name = file.getName();

    this.path = StringUtils.remove(file.getPath(), FilenameUtils.normalize(basePath));

    this.empty = this.checkIsEmpty(file);
    this.key = DirectoryInfo.encode(this.path);
  }

  public static String encode(String path) {
    return Base64.getEncoder().withoutPadding().encodeToString(path.getBytes());
  }

  public static String decode(String encodedPath) {
    if (encodedPath == null) {
      return "";
    }
    return new String(Base64.getDecoder().decode(encodedPath));
  }

  private boolean checkIsEmpty(File file) {
    if (file.isDirectory()) {
      File[] directories = file.listFiles(File::isDirectory);
      if (directories == null) {
        return true;
      }
      return directories.length <= 0;
    }
    return false;
  }
}
