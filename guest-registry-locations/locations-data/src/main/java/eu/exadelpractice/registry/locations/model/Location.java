package eu.exadelpractice.registry.locations.model;

import eu.exadelpractice.registry.common.model.Address;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Location {
	private String locationId;
	private LocationType locationType;
	private String locationTitle;
	private Address address;
	private WorkerRef manager;
}
