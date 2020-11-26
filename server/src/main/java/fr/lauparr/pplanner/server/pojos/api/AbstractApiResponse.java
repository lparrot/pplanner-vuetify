package fr.lauparr.pplanner.server.pojos.api;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractApiResponse implements Serializable {

  private boolean success;
  private String type;

}
