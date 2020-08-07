package fr.lauparr.pplanner.server.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
		if (isUpdatableDate()) {
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

}