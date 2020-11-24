package fr.lauparr.pplanner.server.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "teams")
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @ManyToMany
  @JsonManagedReference(value = "team_member_ref")
  private List<Member> members = new ArrayList<>();

  @ManyToOne
  @JsonBackReference(value = "project_team_ref")
  private Project project;

  protected Team() {
  }

  @Builder
  public Team(@NotBlank String name, @Singular List<Member> members, Project project) {
    this.name = name;
    this.members = members;
    this.project = project;
  }
}
