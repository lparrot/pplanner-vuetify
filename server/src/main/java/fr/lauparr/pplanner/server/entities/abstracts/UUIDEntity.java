package fr.lauparr.pplanner.server.entities.abstracts;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.UUID;

@Data
@MappedSuperclass
public class UUIDEntity implements Serializable {

  @Id
  protected String id;

  @PrePersist
  public void autofill() {
    this.setId(UUID.randomUUID().toString());
  }

}
