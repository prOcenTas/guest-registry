package eu.exadelpractice.registry.person.controller;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("guests")
@RequiredArgsConstructor
public class GuestController {
	private final GuestService guestService;

	//@Value("${library.module1.my-property-01}")
	//private String param01;

	@GetMapping
	public List<Guest> findAllGuests() {
		return guestService.findAll();
	}

	@GetMapping("{id}")
	public Guest getGuestById(@PathVariable String id) throws GuestNotFoundException {

		//if (param01 == null){
		//	throw new IllegalArgumentException("para is null");
		//}

		return guestService.getGuestById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public String createGuest(@RequestBody Guest guest) throws ObjectNotFoundException {
		return guestService.createGuest(guest);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping
	public void updateGuest(@RequestBody Guest guest) throws ObjectNotFoundException {
		guestService.updateGuest(guest);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deleteGuest(@PathVariable String id) throws GuestNotFoundException {
		guestService.deleteGuestById(id);
	}
}
