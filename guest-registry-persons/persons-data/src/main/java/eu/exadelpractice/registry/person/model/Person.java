package eu.exadelpractice.registry.person.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import eu.exadelpractice.registry.common.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	private String id;
	private String nationalId;
	private String name;
	private String surname;
	private Gender gender;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateOfBirth; 
	private Address address;
	private String nationality;
	private String phoneNumber;
	private String email;
}
