package fr.lauparr.pplanner.server.entities;

import fr.lauparr.pplanner.server.pojos.ModifiableEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "projects")
@EqualsAndHashCode(callSuper = false)
public class Project extends ModifiableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;

  public Project() {
  }

  @Builder
  public Project(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
}
