package fr.lauparr.pplanner.server.entities;

import fr.lauparr.pplanner.server.entities.abstracts.UUIDModifiableEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Group extends UUIDModifiableEntity {

  @NotBlank
  private String name;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  private List<Role> roles = new ArrayList<>();

  @Builder
  public Group(String name, @Singular List<Role> roles) {
    this.name = name;
    this.roles = roles;
  }
}
