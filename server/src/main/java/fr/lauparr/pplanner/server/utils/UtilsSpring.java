package fr.lauparr.pplanner.server.utils;

import fr.lauparr.pplanner.server.configs.ApplicationContextProvider;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.Set;

public interface UtilsSpring {

  static <T> T getBean(Class<T> clazz) {
    return ApplicationContextProvider.getApplicationContext().getBean(clazz);
  }

  static String getProperty(String key) {
    return UtilsSpring.getBean(Environment.class).getProperty(key);
  }

  static <T> T getProperty(String key, Class<T> clazz) {
    return UtilsSpring.getBean(Environment.class).getProperty(key, clazz);
  }

  static String getUrlFromRequest(HttpServletRequest request) {
    try {
      if (request != null) {
        return ServletUriComponentsBuilder.fromServletMapping(request).build().toString();
      }
    } catch (IllegalStateException e) {
      return "";
    }
    return null;
  }

  static HttpServletRequest getRequest() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes instanceof ServletRequestAttributes) {
      return ((ServletRequestAttributes) requestAttributes).getRequest();
    }
    return null;
  }

  static <T> void validate(T data) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<T>> violations = validator.validate(data);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

}
