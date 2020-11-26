package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.lauparr.pplanner.server.pojos.api.ModifiableEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Project extends ModifiableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  private String description;

  @OneToOne(mappedBy = "project")
  @JsonManagedReference(value = "project_team_ref")
  private Team team;

  @OneToMany(mappedBy = "project")
  @JsonManagedReference(value = "project_file_ref")
  private List<ProjectFile> files = new ArrayList<>();

  @Builder
  public Project(Long id, String name, String description, Team team) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.team = team;
  }
}

