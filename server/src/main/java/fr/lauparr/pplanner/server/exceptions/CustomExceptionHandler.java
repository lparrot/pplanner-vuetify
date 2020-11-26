package fr.lauparr.pplanner.server.exceptions;

import fr.lauparr.pplanner.server.pojos.api.ResponseError;
import fr.lauparr.pplanner.server.utils.UtilsController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Handler personnalisé pour l'interception des exceptions venant du container (non catchées par le développeur)
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  public final ResponseEntity handleApplicationException(ApplicationException e, WebRequest request) {
    ResponseError exceptionResponse = UtilsController.createExceptionResponse(e);
    return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(TaggedApplicationException.class)
  public final ResponseEntity handleTaggedApplicationException(TaggedApplicationException e, WebRequest request) {
    ResponseError exceptionResponse = UtilsController.createExceptionResponse(e);
    exceptionResponse.getDetail().put("tag", e.getTag());
    return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MessageException.class)
  public final ResponseEntity handleMessageException(MessageException e, WebRequest request) {
    ResponseError exceptionResponse = UtilsController.createMessageResponse(e.getLevel(), e.getMessage(), e.getTitle());
    return new ResponseEntity(exceptionResponse, HttpStatus.OK);
  }

  @ExceptionHandler(Throwable.class)
  public final ResponseEntity handleMessageException(Throwable e, WebRequest request) {
    ResponseError exceptionResponse = UtilsController.createExceptionResponse(e);
    return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity handlerConstraintViolationException(ConstraintViolationException e, WebRequest request) {
    return new ResponseEntity(UtilsController.createValidationResponse(UtilsController.createViolations(e)), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return new ResponseEntity(UtilsController.createValidationResponse(UtilsController.createViolations(e)), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
    ResponseError exceptionResponse = UtilsController.createExceptionResponse(e);
    return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
