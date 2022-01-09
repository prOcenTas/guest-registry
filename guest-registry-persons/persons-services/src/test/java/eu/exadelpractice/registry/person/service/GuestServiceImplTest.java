package eu.exadelpractice.registry.person.service;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.model.GuestType;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.repository.GuestRepository;
import eu.exadelpractice.registry.person.service.impl.GuestServiceImpl;
import eu.exadelpractice.registry.person.validator.GuestValidator;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTest {

	@Mock
	private GuestRepository guestRepository;

	@Mock
	private GuestValidator guestValidator;

	@InjectMocks
	private GuestServiceImpl guestServiceImpl;

	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");

	private Person mockPerson2 = new Person("513dsaf", "35186767635", "Max", "Smith", Gender.FEMALE,
			LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"), "american", "+135786123",
			"ms@example.com");

	private LocationRef location = new LocationRef("1", "Meeting with x company");
	private CardRef card = new CardRef("DSfdsafdfvnmmn", "worker card");
	private Guest mockGuest = new Guest("dsafdsfvcxc", mockPerson, card, "Exadel", "Good", location, "noreason",
			GuestType.GUEST);
	private Guest mockGuest2 = new Guest("xzcvxcv", mockPerson2, card, "Google", "Wed", location, "good reason",
			GuestType.VISITOR);

	@Test
	public void getGuestByIdWhenGuestExists() throws Exception {
		Optional<Guest> optionalGuest = Optional.of(mockGuest);
		Mockito.when(guestRepository.findById(mockGuest.getId())).thenReturn(optionalGuest);
		assertEquals(guestServiceImpl.getGuestById(mockGuest.getId()), mockGuest);
	}

	@Test
	public void getGuestByIdWhenGuestIsNull() throws Exception {
		String id = "156sdaffcxv12";
		Optional<Guest> optionalGuest = Optional.ofNullable(null);
		Mockito.when(guestRepository.findById(id)).thenReturn(optionalGuest);
		Exception ex = assertThrows(GuestNotFoundException.class, () -> guestServiceImpl.getGuestById(id));
		assertTrue(ex.getMessage().equals("Guest does not exist with an id " + id));
	}

	@Test
	public void findAll() throws Exception {
		List<Guest> guestList = new ArrayList<Guest>();
		guestList.add(mockGuest);
		guestList.add(mockGuest2);
		Mockito.when(guestRepository.findAll()).thenReturn(guestList);
		assertEquals(guestServiceImpl.findAll(), guestList);
	}

	@Test
	public void createGuest() throws Exception {
		
		Mockito.doAnswer(new Answer<Void>() {
		    @Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
		      mockGuest.setId("fdfsa");
		      //System.out.println("called with arguments: " + Arrays.toString(args));
		      //return null;
			return null;
		    }
		}).when(guestRepository).save(mockGuest);
		//Mockito.when(guestRepository.save(mockGuest)).thenReturn(mockGuest);
		assertTrue(guestServiceImpl.createGuest(mockGuest).equals("fdfsa"));
	}

	@Test
	public void createGuestWhenValidationFails() throws Exception {
		Mockito.doThrow(new PersonNotFoundException("Person does not exist")).when(guestValidator)
				.validateNewGuest(mockGuest);
		//Mockito.when(guestRepository.save(mockGuest)).thenReturn(mockGuest);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> guestServiceImpl.createGuest(mockGuest));
		assertTrue(ex.getMessage().equals("Person does not exist"));
	}

	@Test
	public void updateGuestWhenGuestExists() throws Exception {
		String id = "151fdsafsdaf";
		mockGuest.setId(id);
		mockGuest2.setId(id);
		Optional<Guest> optionalGuest = Optional.of(mockGuest);
		Mockito.when(guestRepository.findById(id)).thenReturn(optionalGuest);
		//Mockito.when(guestRepository.save(mockGuest2)).thenReturn(mockGuest2);
		guestServiceImpl.updateGuest(mockGuest2);
		Mockito.verify(guestRepository, Mockito.times(1)).findById(id);
		Mockito.verify(guestRepository, Mockito.times(1)).save(mockGuest2);
	}

	@Test
	public void updateGuestWhenGuestDosNotExist() throws Exception {
		String id = "FDafdsaf";
		Optional<Guest> optionalGuest = Optional.ofNullable(null);
		Mockito.when(guestRepository.findById(id)).thenReturn(optionalGuest);
		mockGuest2.setId(id);
		Exception ex = assertThrows(GuestNotFoundException.class, () -> guestServiceImpl.updateGuest(mockGuest2));
		assertTrue(ex.getMessage().equals("Guest does not exist with an id " + id));
		Mockito.verify(guestRepository, Mockito.never()).save(Mockito.any());
		Mockito.verify(guestRepository, Mockito.times(1)).findById(id);
	}

	@Test
	public void updateGuestWhenIdNotGiven() throws Exception {
		Mockito.doThrow(new GuestNotFoundException("Guest id value is not given")).when(guestValidator).id(mockGuest2);
		Exception ex = assertThrows(GuestNotFoundException.class, () -> guestServiceImpl.updateGuest(mockGuest2));
		assertTrue(ex.getMessage().equals("Guest id value is not given"));
		Mockito.verify(guestRepository, Mockito.never()).save(Mockito.any());
		Mockito.verify(guestRepository, Mockito.never()).findById(Mockito.any());
	}

	@Test
	public void deleteGuestByIdWhenGuestExists() throws Exception {
		String id = mockGuest.getId();
		Optional<Guest> optionalGuest = Optional.of(mockGuest);
		Mockito.when(guestRepository.findById(id)).thenReturn(optionalGuest);
		guestServiceImpl.deleteGuestById(id);
		Mockito.verify(guestRepository, Mockito.times(1)).findById(id);
		Mockito.verify(guestRepository, Mockito.times(1)).deleteById(id);
	}

	@Test
	public void deleteGuestByIdWhenGuestDoesNotExist() throws Exception {
		String id = "DFsafsdaf";
		Optional<Guest> optionalGuest = Optional.ofNullable(null);
		Mockito.when(guestRepository.findById(id)).thenReturn(optionalGuest);
		Exception ex = assertThrows(GuestNotFoundException.class, () -> guestServiceImpl.deleteGuestById(id));
		assertTrue(ex.getMessage().equals("Guest does not exist with an id " + id));
		Mockito.verify(guestRepository, Mockito.times(1)).findById(id);
		Mockito.verify(guestRepository, Mockito.never()).deleteById(id);
	}
}
