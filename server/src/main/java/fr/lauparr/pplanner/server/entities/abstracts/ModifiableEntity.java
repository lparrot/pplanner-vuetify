package fr.lauparr.pplanner.server.entities.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class ModifiableEntity implements Serializable {

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  protected LocalDateTime dateCreation;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  protected LocalDateTime dateModification;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  protected LocalDateTime dateSuppression;

  @PrePersist
  @PreUpdate
  public void prePersist() {
    if (this.dateCreation == null) {
      this.dateCreation = LocalDateTime.now();
    }
    if (this.isUpdatableDate()) {
      this.dateModification = LocalDateTime.now();
    }
  }

  public void remove() {
    this.dateSuppression = LocalDateTime.now();
  }

  /**
   * Méthode qui permet de savoir si on veut mettre à jour la dateModification à
   * chaque persist ou update.
   */
  @JsonIgnore
  public boolean isUpdatableDate() {
    return true;
  }

  public boolean isActive() {
    return this.dateSuppression == null;
  }

  public static <T extends ModifiableEntity> Specification<T> whereActives() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("dateSuppression"));
  }

  public static <T extends ModifiableEntity> Specification<T> whereInactives() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("dateSuppression"));
  }

}
