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
  public <T> ResponseEntity ok() {
    return ResponseEntity.ok(ResponseData.builder().success(true).build());
  }

  /**
   * Réponse OK avec données
   */
  public <T> ResponseEntity ok(T data) {
    return ResponseEntity.ok(ResponseData.builder().success(true).data(data).build());
  }

  /**
   * Réponse OK sans donnée et avec un type
   */
  public <T> ResponseEntity okWithType(String type) {
    return ResponseEntity.ok(ResponseData.builder().success(true).type(type).build());
  }

  /**
   * Réponse OK avec données et avec un type
   */
  public <T> ResponseEntity okWithType(String type, T data) {
    return ResponseEntity.ok(ResponseData.builder().success(true).type(type).data(data).build());
  }

  /**
   * Réponse OK avec erreur fonctionnelle sans donnée
   */
  public <T> ResponseEntity notOk() {
    return ResponseEntity.ok(ResponseData.builder().success(false).build());
  }

  /**
   * Réponse OK avec erreur fonctionnelle et données
   */
  public <T> ResponseEntity notOk(T data) {
    return ResponseEntity.ok(ResponseData.builder().success(false).data(data).build());
  }

  /**
   * Réponse OK avec erreur fonctionnelle et type
   */
  public <T> ResponseEntity notOkWithType(String type) {
    return ResponseEntity.ok(ResponseData.builder().success(false).type(type).build());
  }

  /**
   * Réponse OK avec erreur fonctionnelle, données et type
   */
  public <T> ResponseEntity notOkWithType(String type, T data) {
    return ResponseEntity.ok(ResponseData.builder().success(false).type(type).data(data).build());
  }

  /**
   * Réponse erreur avec message et type
   */
  public ResponseEntity message(String message) {
    return ResponseEntity.ok(UtilsController.createMessageResponse(Level.SEVERE, message));
  }

  /**
   * Réponse erreur avec message et type
   */
  public ResponseEntity message(String message, Level level) {
    return ResponseEntity.ok(UtilsController.createMessageResponse(level, message));
  }

  /**
   * Réponse binaire
   */
  public ResponseEntity file(byte[] data) {
    try {
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
    } catch (Exception e) {
      throw new TaggedApplicationException("http", e);
    }
  }

  /**
   * Réponse fichier pdf
   */
  public ResponseEntity pdf(byte[] data) {
    try {
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(data);
    } catch (Exception e) {
      throw new TaggedApplicationException("http", e);
    }
  }

}
