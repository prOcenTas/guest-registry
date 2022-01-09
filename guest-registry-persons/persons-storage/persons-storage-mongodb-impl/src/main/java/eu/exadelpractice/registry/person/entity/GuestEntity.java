package eu.exadelpractice.registry.person.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.exadelpractice.registry.person.model.GuestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Guest")
public class GuestEntity {
	@Id
	private String id;
	@DBRef
	private PersonEntity person;
	private CardRefEntity card;
	private String company;
	private String position;
	private LocationRefEntity location;
	private String reasonForVisiting;
	private GuestType guestType;

}
