package eu.exadelpractice.registry.person.client.impl;

import java.util.List;

import eu.exadelpractice.registry.common.client.GenericClientImpl;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.client.GuestClient;
import eu.exadelpractice.registry.person.model.Guest;

@Service
//@RequiredArgsConstructor
public class GuestClientImpl extends GenericClientImpl<Guest, GuestNotFoundException>  implements GuestClient {

	public GuestClientImpl() {
		super(Guest.class, "guests/");
	}

//	@PostConstruct
//    private void postConstruct() {
//        genericImpl.setType(Guest.class);
//        genericImpl.setExceptionType(GuestNotFoundException.class);
//    }
	
	@Override
	public Guest getGuestById(String id) throws GuestNotFoundException, InternalServerErrorException, BadRequestException {
		return this.getObjectById(id);
	}

	@Override
	public List<Guest> getAllGuests() throws GuestNotFoundException, InternalServerErrorException, BadRequestException  {
		return this.getAllObjects(Guest[].class);
	}

	@Override
	public String createGuest(Guest guest) throws GuestNotFoundException, InternalServerErrorException, BadRequestException {
		return this.createObject(guest);
	}

	@Override
	public void updateGuest(Guest guest) throws GuestNotFoundException, BadRequestException, InternalServerErrorException {
		this.updateObject(guest);
	}

	@Override
	public void deleteGuestById(String id) throws GuestNotFoundException, InternalServerErrorException, BadRequestException {
		this.deleteObjectById(id);
	}

	@Override
	protected GuestNotFoundException createDomainNotFoundException(HttpClientErrorException ex) {
		return new GuestNotFoundException(ex.getMessage());
	}
}
