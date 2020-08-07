package fr.lauparr.pplanner.server.entities;

import fr.lauparr.pplanner.server.pojos.ModifiableEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Group extends ModifiableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
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
