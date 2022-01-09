package eu.exadelpractice.registry.events.controller;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.events.exception.EventNotFoundException;
import eu.exadelpractice.registry.events.model.Event;
import eu.exadelpractice.registry.events.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

	private final EventService service;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void saveEvent(@RequestBody Event event) throws ObjectNotFoundException {
		service.saveEvent(event);
	}

	@GetMapping
	public List<Event> getEvents() {
		return service.getAll();
	}

	@GetMapping("{id}")
	public Event getEvent(@PathVariable String id) throws EventNotFoundException {
		return service.getEvent(id);
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateEvent(@RequestBody Event event) throws EventNotFoundException {
		service.updateEvent(event);
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deleteEvent(@PathVariable String id) throws EventNotFoundException {
		service.deleteEvent(id);
	}
}
