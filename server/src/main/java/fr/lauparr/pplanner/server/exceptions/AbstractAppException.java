package fr.lauparr.pplanner.server.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.lauparr.pplanner.server.pojos.api.ApiError;
import fr.lauparr.pplanner.server.pojos.api.ApiErrorDetails;
import lombok.Getter;

@JsonIgnoreProperties("withTrace")
public abstract class AbstractAppException extends RuntimeException {

  /**
   * Typage de l'exception
   */
  @Getter
  protected final String type;

  /**
   * Détail de l'erreur
   */
  @Getter
  protected final ApiErrorDetails details = new ApiErrorDetails(this);

  /**
   * Constructeur
   */
  public AbstractAppException(String message) {
    super(message);
    this.type = null;
  }

  /**
   * Constructeur
   */
  public AbstractAppException(String type, String message) {
    super(message);
    this.type = type;
  }

  /**
   * Constructeur
   */
  public AbstractAppException(Throwable cause) {
    super(cause);
    this.type = null;
  }

  /**
   * Constructeur
   */
  public AbstractAppException(String type, Throwable cause) {
    super(cause);
    this.type = type;
  }

  /**
   * Transforme l'exception en réponse HTTP
   */
  public ApiError toResponse() {
    return new ApiError(this, this.type);
  }

}
