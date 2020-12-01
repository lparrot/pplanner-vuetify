package fr.lauparr.pplanner.server.pojos;

import fr.lauparr.pplanner.server.utils.UtilsDateTime;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Data
public class FileInfo {

  private boolean directory;

  private String extension;

  private String key;

  private String name;

  private String path;

  private String size;

  private String updatedAt;

  @SneakyThrows
  public FileInfo(File file) {
    this.name = file.getName();
    this.path = file.getPath();
    this.key = DirectoryInfo.encode(this.path);
    this.directory = file.isDirectory();
    this.extension = this.directory ? null : FilenameUtils.getExtension(this.name);
    this.size = this.directory ? FileUtils.byteCountToDisplaySize(FileUtils.sizeOfDirectory(file)) : FileUtils.byteCountToDisplaySize(file.length());
    this.updatedAt = UtilsDateTime.formatDate(Files.getLastModifiedTime(Paths.get(file.toURI())).toInstant());
  }
}
