package eu.exadelpractice.registry.person.validator;



import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.model.GuestType;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class GuestValidatorTest {

	@Mock
	private PersonRepository personRepository;
	//@Mock
	//private LocationRepository locationRepository;
	@Mock
	private PersonValidator personValidator;
	/*@Mock
	private LocationValidator locationValidator;
	@Mock
	private CardRepository cardRepository;
	@Mock
	private CardValidator cardValidator;*/
	@InjectMocks
	private GuestValidator guestValidator;
	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");

	private LocationRef location = new LocationRef("1", "Meeting with x company");
	private CardRef card = new CardRef("DSfdsafdfvnmmn", "worker card");
	private Guest mockGuest = new Guest("dsafdsfvcxc", mockPerson, card, "Exadel", "Good", location, "noreason",
			GuestType.GUEST);

	@Test
	public void id() throws Exception {
		try {
			guestValidator.id(mockGuest);
		} catch (GuestNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}

	@Test
	public void idWhenIdNull() throws Exception {
		mockGuest.setId(null);
		Exception ex = assertThrows(GuestNotFoundException.class, () -> guestValidator.id(mockGuest));
		assertEquals(ex.getMessage(), "Guest id value is not given");
	}

	@Test
	public void validateNewGuestSuccessfully() throws Exception {
		Mockito.when(personRepository.findById(mockGuest.getPerson().getId())).thenReturn(Optional.of(mockPerson));
		//Mockito.when(locationRepository.findById(mockGuest.getLocation().getLocationId()))
		//		.thenReturn(Optional.of(mockLocation));
		// Mockito.when(workerRepository.findByPerson_id(mockWorker.getPerson().getId())).thenReturn(null);
		//Mockito.when(cardRepository.findById(mockGuest.getCard().getId())).thenReturn(Optional.of(card));
		try {
			guestValidator.validateNewGuest(mockGuest);
		} catch (ObjectNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}

	@Test
	public void validateNewGuestWhenPersonNotGiven() throws Exception {
		mockGuest.setPerson(null);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> guestValidator.validateNewGuest(mockGuest));
		assertEquals(ex.getMessage(), "Person not given");
	}

	@Test
	public void validateNewGuestWhenPersonDoesNotExist() throws Exception {
		Exception ex = assertThrows(PersonNotFoundException.class, () -> guestValidator.validateNewGuest(mockGuest));
		assertEquals(ex.getMessage(), "Person does not exist");
	}

	/*@Test
	public void validateNewGuestWhenLocationDoesNotExist() throws Exception {
		Mockito.when(personRepository.findById(mockGuest.getPerson().getId())).thenReturn(Optional.of(mockPerson));
		Exception ex = assertThrows(LocationNotFoundException.class, () -> guestValidator.validateNewGuest(mockGuest));
		assertEquals(ex.getMessage(), "Location does not exist");
	}*/
}
