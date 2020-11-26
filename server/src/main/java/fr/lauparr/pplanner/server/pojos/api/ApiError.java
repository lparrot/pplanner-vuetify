package fr.lauparr.pplanner.server.pojos.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ApiError extends AbstractApiResponse {

  private ApiErrorDetails error;

  public ApiError(Throwable e) {
    this();
    this.setSuccess(false);
  }

  public ApiError(Throwable e, Map<String, Object> pData) {
    this();
    this.setSuccess(false);
    if (pData != null) {
      pData.forEach(this.error::addData);
    }
  }

  public ApiError(Throwable e, String type) {
    this();
    this.setSuccess(false);
    this.setType(type);
  }

  public ApiError(Throwable e, String type, Map<String, Object> pData) {
    this();
    this.setSuccess(false);
    this.setType(type);
    this.error = new ApiErrorDetails(e);
    if (pData != null) {
      pData.forEach(this.error::addData);
    }
  }
}
