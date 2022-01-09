package eu.exadelpractice.registry.common.model.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDetails {

	private String message;
	private String errorId;
}
