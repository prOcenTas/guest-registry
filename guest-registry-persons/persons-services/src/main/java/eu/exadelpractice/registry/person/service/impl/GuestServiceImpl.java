package eu.exadelpractice.registry.person.service.impl;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.repository.GuestRepository;
import eu.exadelpractice.registry.person.service.GuestService;
import eu.exadelpractice.registry.person.validator.GuestValidator;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
	private final GuestRepository guestRepository;
	private final GuestValidator guestValidator;

	@Override
	public List<Guest> findAll() {
		return guestRepository.findAll();
	}

	@Override
	public String createGuest(Guest guest) throws ObjectNotFoundException {
		guest.setId(null);
		guestValidator.validateNewGuest(guest);
		guestRepository.save(guest);
		return guest.getId();
	}

	@Override
	public Guest getGuestById(String id) throws GuestNotFoundException {
		return guestRepository.findById(id)
				.orElseThrow(() -> new GuestNotFoundException("Guest does not exist with an id " + id));
	}

	@Override
	public void updateGuest(Guest guest) throws ObjectNotFoundException {
		guestValidator.id(guest);
		guestValidator.validateNewGuest(guest);
		this.getGuestById(guest.getId());
		guestRepository.save(guest);
	}

	@Override
	public void deleteGuestById(String id) throws GuestNotFoundException {
		this.getGuestById(id);
		guestRepository.deleteById(id);
	}

}
