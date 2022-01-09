package eu.exadelpractice.registry.person.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRef {
	private String id;
	private String cardTitle;
}
