package eu.exadelpractice.registry.person.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.person.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Person")
public class PersonEntity {

	@Id
	private String id;
	private String nationalId;
	private String name;
	private String surname;
	private Gender gender;
	private LocalDate dateOfBirth;
	private Address address;
	private String nationality;
	private String phoneNumber;
	private String email;
}
