package eu.exadelpractice.registry.person.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.exadelpractice.registry.person.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "User")
public class UserEntity {
	@Id
	private String id;
	@DBRef
	private PersonEntity person;
	private String password;
	private Role role;

}
