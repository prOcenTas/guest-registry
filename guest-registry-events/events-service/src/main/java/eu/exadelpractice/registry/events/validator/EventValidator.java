package eu.exadelpractice.registry.events.validator;

import eu.exadelpractice.registry.cards.exception.LocationFieldBadValueException;
import eu.exadelpractice.registry.cards.exception.WorkerFieldBadValueException;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.events.exception.EventNotFoundException;
import eu.exadelpractice.registry.events.model.Event;
import eu.exadelpractice.registry.events.model.LocationRef;
import eu.exadelpractice.registry.events.model.WorkerRef;
import eu.exadelpractice.registry.locations.client.LocationClient;
import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.person.client.WorkerClient;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventValidator {
	// private final LocationValidator locationValidator;
	// private final EventRepository eventRepository;

	private final LocationClient locationClient;
	private final WorkerClient workerClient;

	public void id(Event event) throws EventNotFoundException, LocationFieldBadValueException {
		if (event.getId() == null) {
			throw new EventNotFoundException("Event id value is not given");
		}

		validateLocationId(event, event.getLocationRef());
	}

	private void validateLocationId(Event event, LocationRef locationRef) throws LocationFieldBadValueException {
		if(locationRef != null && event.getLocationRef().getId() != null){
			Location loadedLocation=null;
			try{
				loadedLocation = locationClient.getLocationById(event.getLocationRef().getId());
				locationRef.setLocationTitle(loadedLocation.getLocationTitle());
			}
			catch (LocationNotFoundException e){
				throw new LocationFieldBadValueException("Wrong location selected, Location ID: "
						+ event.getLocationRef().getId());
			} catch (InternalServerErrorException | BadRequestException e) {
				e.printStackTrace();
			}
		}
	}

//	private void validateWorkerId(Event event, WorkerRef workerRef) throws WorkerFieldBadValueException {
//		if(workerRef != null && event.getPersonRef() != null){
//			Worker loadedWorker = null;
//			try{
//				loadedWorker = workerClient.getWorkerById(event.getPersonRef().contains(workerRef.getWorkerId()));
//				workerRef.setName(loadedWorker.getPerson().getName());
//				workerRef.setSurname(loadedWorker.getPerson().getSurname());
//			}
//			catch (WorkerNotFoundException e){
//				throw new WorkerFieldBadValueException("Wrong worker selected, Worker ID: " + event.getPersonRef().contains(workerRef.getWorkerId()));
//			}
//		}
//	}
}
