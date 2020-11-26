package fr.lauparr.pplanner.server.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;

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

  @ManyToMany(cascade = CascadeType.MERGE)
  @JsonManagedReference(value = "team_member_ref")
  private List<Member> members = new ArrayList<>();

  @OneToOne
  @JsonBackReference(value = "project_team_ref")
  private Project project;

  protected Team() {
  }

  @Builder
  public Team(@NotBlank String name, Project project) {
    this.name = name;
    this.project = project;
  }

  public void addMember(Member member) {
    this.members.add(member);
  }
}
