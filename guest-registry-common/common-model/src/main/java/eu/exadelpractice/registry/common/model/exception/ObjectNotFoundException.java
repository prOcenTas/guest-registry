package eu.exadelpractice.registry.common.model.exception;

public abstract class ObjectNotFoundException extends Exception {

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
