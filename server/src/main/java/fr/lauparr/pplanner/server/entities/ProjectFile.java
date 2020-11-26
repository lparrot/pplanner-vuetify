package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "projects_files")
public class ProjectFile implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String folder;

  private String filename;

  private byte[] data;

  @ManyToOne
  @JsonBackReference(value = "project_file_ref")
  private Project project;

  public List<String> getFolderHierarchy() {
    if (folder != null) {
      return Arrays.asList(folder.split("/"));
    }
    return Collections.singletonList("/");
  }

}
