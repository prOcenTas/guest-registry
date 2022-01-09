package eu.exadelpractice.registry.locations.entity;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.locations.model.LocationType;
import eu.exadelpractice.registry.locations.model.WorkerRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Document(collection = "Location")
public class LocationEntity {
    @Id
    private String locationId;
    private LocationType locationType;
    private String locationTitle;
    private Address address;
    private WorkerRef manager;
}