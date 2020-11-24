package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "members")
public class Member implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullName;

  private String avatar;

  @ManyToMany(mappedBy = "members")
  @JsonBackReference(value = "team_member_ref")
  private List<Team> teams = new ArrayList<>();

  protected Member() {
  }

  @Builder
  public Member(String fullName, String avatar, @Singular List<Team> teams) {
    this.fullName = fullName;
    this.avatar = avatar;
    this.teams = teams;
  }
}
