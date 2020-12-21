package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.lauparr.pplanner.server.entities.abstracts.UUIDModifiableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "project_board_modules")
@EqualsAndHashCode(callSuper = true)
public class ProjectBoardModule extends UUIDModifiableEntity {

  private String type;

  private String name;

  @Column(columnDefinition = "LONGTEXT")
  private String state;

  @ManyToOne
  @JsonBackReference(value = "module_board_ref")
  private ProjectBoard board;

}
