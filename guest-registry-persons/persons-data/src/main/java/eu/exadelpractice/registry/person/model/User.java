package eu.exadelpractice.registry.person.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String id;
	private Person person;
	private String password;
	private Role role;

}
