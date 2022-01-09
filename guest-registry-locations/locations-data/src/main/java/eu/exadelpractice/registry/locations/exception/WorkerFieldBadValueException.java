package eu.exadelpractice.registry.locations.exception;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class WorkerFieldBadValueException extends ObjectNotFoundException {
    public WorkerFieldBadValueException(String message) {
        super(message);
    }
}
