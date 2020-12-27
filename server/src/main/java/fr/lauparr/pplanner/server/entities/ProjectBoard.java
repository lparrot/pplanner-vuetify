package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.lauparr.pplanner.server.entities.abstracts.UUIDModifiableEntity;
import fr.lauparr.pplanner.server.enums.BoardVisibility;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "project_boards")
@EqualsAndHashCode(callSuper = true)
public class ProjectBoard extends UUIDModifiableEntity implements Serializable {

  private String name;

  private String description;

  @Enumerated(EnumType.STRING)
  private BoardVisibility visibility;

  @ManyToOne
  @JsonBackReference(value = "board_parent_ref")
  private ProjectBoard parent;

  @ManyToOne
  @JsonBackReference(value = "project_board_ref")
  private Project project;

  @OneToMany(mappedBy = "parent")
  @JsonManagedReference(value = "board_parent_ref")
  private List<ProjectBoard> children = new ArrayList<>();

  @OneToMany(mappedBy = "board", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
  @JsonManagedReference(value = "module_board_ref")
  private List<ProjectBoardModule> modules = new ArrayList<>();

  public void addChildren(ProjectBoard board) {
    board.setParent(this);
    this.children.add(board);
  }

  public void addModule(ProjectBoardModule module) {
    module.setBoard(this);
    this.getModules().add(module);
  }

}
