package fr.lauparr.pplanner.server.entities;

import fr.lauparr.pplanner.server.pojos.ModifiableEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "projects")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Project extends ModifiableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  @Builder
  public Project(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
}

