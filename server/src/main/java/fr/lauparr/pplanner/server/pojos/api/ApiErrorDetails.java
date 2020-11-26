package fr.lauparr.pplanner.server.pojos.api;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class ApiErrorDetails {

  private Map<String, Object> data;
  private final String message;
  private String className;
  private List<String> trace = new ArrayList<>();

  /**
   * Constructeur
   */
  public ApiErrorDetails(Throwable e) {
    if (e != null) {
      this.message = e.getMessage();
      this.className = e.getClass().getSimpleName();
      this.trace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
    } else {
      this.message = "NULL";
    }
  }

  /**
   * Constructeur
   */
  public ApiErrorDetails(Throwable e, Map<String, Object> data) {
    this(e);
    this.data = data;
  }

  /**
   * Méthode pour ajouter des données à l'exception
   */
  public void addData(String key, Object value) {
    if (this.data == null) {
      this.data = new HashMap<>();
    }
    this.data.put(key, value);
  }

}
