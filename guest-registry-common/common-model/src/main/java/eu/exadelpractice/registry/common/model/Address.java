package eu.exadelpractice.registry.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	private String street;
	private int buildingNumber;
	private int apartmentNumber;
	private String city;
	private String country;

}
