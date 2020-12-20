package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.lauparr.pplanner.server.entities.abstracts.UUIDEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "projects_files")
public class ProjectFile extends UUIDEntity {

  private String folder;

  private String filename;

  private byte[] data;

  @ManyToOne
  @JsonBackReference(value = "project_file_ref")
  private Project project;

  public List<String> getFolderHierarchy() {
    if (this.folder != null) {
      return Arrays.asList(this.folder.split("/"));
    }
    return Collections.singletonList("/");
  }

}
