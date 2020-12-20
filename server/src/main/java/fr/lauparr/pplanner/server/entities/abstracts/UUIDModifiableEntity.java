package fr.lauparr.pplanner.server.entities.abstracts;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class UUIDModifiableEntity extends ModifiableEntity {

  @Id
  protected String id;

  @PrePersist
  public void autofill() {
    this.setId(UUID.randomUUID().toString());
  }

}


