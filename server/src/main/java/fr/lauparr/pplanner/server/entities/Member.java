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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "members")
public class Member extends UUIDModifiableEntity {

  private String avatar;

  private String job;

  private String email;

  private LocalDate birthday;

  private String firstName;

  private String lastName;

  @JsonBackReference(value = "user_member_ref")
  @OneToOne
  private User user;

  @ManyToMany(mappedBy = "members")
  @JsonBackReference(value = "team_member_ref")
  private List<Team> teams = new ArrayList<>();

  protected Member() {
  }

  @Builder
  public Member(String avatar, String job, String email, LocalDate birthday, String firstName, String lastName, @Singular List<Team> teams) {
    this.avatar = avatar;
    this.job = job;
    this.email = email;
    this.birthday = birthday;
    this.teams = teams;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Member(User user) {
    this.email = user.getEmail();
    this.user = user;
  }

  public String getFullname() {
    return String.format("%s %s", this.firstName, this.lastName);
  }
}
