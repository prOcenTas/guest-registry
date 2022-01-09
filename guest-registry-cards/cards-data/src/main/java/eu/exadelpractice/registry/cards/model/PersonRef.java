package eu.exadelpractice.registry.cards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonRef {
	private String id;
	private String name;
	private String surname;
}
