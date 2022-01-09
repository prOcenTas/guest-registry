package eu.exadelpractice.registry.person.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guest {
	private String id;
	private Person person;
	private CardRef card;
	private String company;
	private String position;
	private LocationRef location;
	private String reasonForVisiting;
	private GuestType guestType;

}
