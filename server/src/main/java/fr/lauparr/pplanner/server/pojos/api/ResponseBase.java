package fr.lauparr.pplanner.server.pojos.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class ResponseBase {

  private boolean success;

}
