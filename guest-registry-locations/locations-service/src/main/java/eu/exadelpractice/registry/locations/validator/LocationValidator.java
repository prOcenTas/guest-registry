package eu.exadelpractice.registry.locations.validator;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.exception.WorkerFieldBadValueException;
import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.locations.model.WorkerRef;
import eu.exadelpractice.registry.person.client.WorkerClient;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationValidator {

    private final WorkerClient workerClient;

    public void id(Location location) throws LocationNotFoundException, WorkerFieldBadValueException {
        if (location.getLocationId() == null) {
            throw new LocationNotFoundException("Location id value is not given");
        }

        validateWorkerId(location, location.getManager());
    }

    public void validateWorkerId(Location location, WorkerRef workerRef) throws WorkerFieldBadValueException {
        if (workerRef != null && location.getManager().getWorkerId() != null) {
            Worker loadedWorker = null;
            try {
                loadedWorker = workerClient.getWorkerById(location.getManager().getWorkerId());
                workerRef.setName(loadedWorker.getPerson().getName());
                workerRef.setSurname(loadedWorker.getPerson().getSurname());
            }
            catch (WorkerNotFoundException e){
                throw new WorkerFieldBadValueException("Wrong worker selected, Worker ID: "
                        + location.getManager().getWorkerId());
            } catch (InternalServerErrorException | BadRequestException e) {
                e.printStackTrace();
            }
        }
    }
}
