package fr.lauparr.pplanner.server.utils;

import fr.lauparr.pplanner.server.pojos.ApiConstraint;
import fr.lauparr.pplanner.server.pojos.ResponseError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.*;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public abstract class ControllerUtils {

	public static ResponseError createExceptionResponse(Throwable throwable) {
		Map detail = new HashMap();
		detail.put("class", throwable.getClass());
		detail.put("trace", Arrays.stream(throwable.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()));
		return ResponseError.builder().error(throwable.getMessage()).type("exception").detail(detail).build();
	}

	public static ResponseError createMessageResponse(Level level, String message, String title) {
		Map detail = new HashMap();
		detail.put("level", level.getName());
		detail.put("title", title);
		return ResponseError.builder().error(message).type("message").detail(detail).build();
	}

	public static ResponseError createMessageResponse(Level level, String message) {
		return createMessageResponse(level, message, null);
	}

	public static ResponseError createValidationResponse(List<ApiConstraint> violations) {
		Map detail = new HashMap();
		violations.forEach(violation -> {
			detail.put(violation.getField(), violation);
		});
		return ResponseError.builder().error("Validation error").type("validation").detail(detail).build();
	}

	public static <T> void validate(T data) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(data);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}

	public static List<ApiConstraint> createViolations(ConstraintViolationException exception) {
		return exception.getConstraintViolations().stream().map(ApiConstraint::new).collect(Collectors.toList());
	}

	public static List<ApiConstraint> createViolations(MethodArgumentNotValidException exception) {
		return exception.getBindingResult().getAllErrors().stream().map(ApiConstraint::new).collect(Collectors.toList());
	}

	public static List<ApiConstraint> createViolations(Set<ConstraintViolation> violations) {
		return violations.stream().map(ApiConstraint::new).collect(Collectors.toList());
	}

}