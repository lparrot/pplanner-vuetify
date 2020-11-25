package fr.lauparr.pplanner.server.pojos;

import fr.lauparr.pplanner.server.exceptions.TaggedApplicationException;
import fr.lauparr.pplanner.server.utils.UtilsController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.logging.Level;

public abstract class AbstractController {

  /**
   * Réponse OK sans donnée
   */
  public <T> ResponseEntity<ResponseData> ok() {
    return ResponseEntity.ok(ResponseData.builder().success(true).build());
  }

  /**
   * Réponse OK avec données
   */
  public <T> ResponseEntity<ResponseData> ok(T data) {
    return ResponseEntity.ok(ResponseData.builder().success(true).data(data).build());
  }

  /**
   * Réponse OK sans donnée et avec un type
   */
  public ResponseEntity<ResponseData> okWithType(String type) {
    return ResponseEntity.ok(ResponseData.builder().success(true).type(type).build());
  }

  /**
   * Réponse OK avec données et avec un type
   */
  public <T> ResponseEntity<ResponseData> okWithType(String type, T data) {
    return ResponseEntity.ok(ResponseData.builder().success(true).type(type).data(data).build());
  }

  /**
   * Réponse OK avec erreur fonctionnelle sans donnée
   */
  public ResponseEntity<ResponseData> notOk() {
    return ResponseEntity.ok(ResponseData.builder().success(false).build());
  }

  /**
   * Réponse OK avec erreur fonctionnelle et données
   */
  public <T> ResponseEntity<ResponseData> notOk(T data) {
    return ResponseEntity.ok(ResponseData.builder().success(false).data(data).build());
  }

  /**
   * Réponse OK avec erreur fonctionnelle et type
   */
  public ResponseEntity<ResponseData> notOkWithType(String type) {
    return ResponseEntity.ok(ResponseData.builder().success(false).type(type).build());
  }

  /**
   * Réponse OK avec erreur fonctionnelle, données et type
   */
  public <T> ResponseEntity<ResponseData> notOkWithType(String type, T data) {
    return ResponseEntity.ok(ResponseData.builder().success(false).type(type).data(data).build());
  }

  /**
   * Réponse erreur avec message et type
   */
  public ResponseEntity<ResponseError> message(String message) {
    return ResponseEntity.ok(UtilsController.createMessageResponse(Level.SEVERE, message));
  }

  /**
   * Réponse erreur avec message et type
   */
  public ResponseEntity<ResponseError> message(String message, Level level) {
    return ResponseEntity.ok(UtilsController.createMessageResponse(level, message));
  }

  /**
   * Réponse binaire
   */
  public ResponseEntity<byte[]> file(byte[] data) {
    try {
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
    } catch (Exception e) {
      throw new TaggedApplicationException("http", e);
    }
  }

  /**
   * Réponse fichier pdf
   */
  public ResponseEntity<byte[]> pdf(byte[] data) {
    try {
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(data);
    } catch (Exception e) {
      throw new TaggedApplicationException("http", e);
    }
  }

}
