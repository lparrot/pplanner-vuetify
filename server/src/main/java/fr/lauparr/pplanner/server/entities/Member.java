package fr.lauparr.pplanner.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.lauparr.pplanner.server.entities.abstracts.UUIDModifiableEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "members")
public class Member extends UUIDModifiableEntity {

  private String fullname;

  private String avatar;

  private String job;

  @JsonBackReference(value = "user_member_ref")
  @OneToOne
  private User user;

  @ManyToMany(mappedBy = "members")
  @JsonBackReference(value = "team_member_ref")
  private List<Team> teams = new ArrayList<>();

  protected Member() {
  }

  @Builder
  public Member(String fullname, String avatar, String job, @Singular List<Team> teams) {
    this.fullname = fullname;
    this.avatar = avatar;
    this.job = job;
    this.teams = teams;
  }

  public Member(User user) {
    this.fullname = String.format("%s %s", user.getLastName(), user.getFirstName());
    this.avatar = user.getAvatar();
    this.user = user;
  }
}
