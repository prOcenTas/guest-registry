package eu.exadelpractice.registry.common.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import eu.exadelpractice.registry.common.model.ErrorHelper;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class HandlerException {

	private final ErrorHelper errorHelper;

	@ExceptionHandler(value = ObjectNotFoundException.class)
	public ResponseEntity<String> exception(ObjectNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<String> exception(BadRequestException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InternalServerErrorException.class)
	public ResponseEntity<String> exception(InternalServerErrorException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetails> handleBadRequestException(Exception e) {
		String errorId = errorHelper.createErrorId();
		// log.error("ErrorHandler. Bad Request errorId: {}", errorId, e);
		return new ResponseEntity<>(ExceptionDetails.builder().message(e.getMessage()).errorId(errorId).build(),
				HttpStatus.BAD_REQUEST);
	}

}
