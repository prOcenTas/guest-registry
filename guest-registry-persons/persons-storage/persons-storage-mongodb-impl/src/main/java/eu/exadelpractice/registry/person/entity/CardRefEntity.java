package eu.exadelpractice.registry.person.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRefEntity {
	private String id;
	private String cardTitle;
}
