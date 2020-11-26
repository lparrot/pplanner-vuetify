package fr.lauparr.pplanner.server.pojos.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiResponse extends AbstractApiResponse {

  private Object data;

  public ApiResponse() {
    setSuccess(true);
  }

  public ApiResponse(Object data) {
    this();
    this.data = data;
  }
}
