package eu.exadelpractice.registry.cards.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
	private String id;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate validFrom;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate validTo;
	private String cardTitle;
	private LocationRef locationRef;
	private PersonRef personRef;
}
