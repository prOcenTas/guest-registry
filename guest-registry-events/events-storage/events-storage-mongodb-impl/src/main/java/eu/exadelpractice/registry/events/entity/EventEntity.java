package eu.exadelpractice.registry.events.entity;

import eu.exadelpractice.registry.events.model.LocationRef;
import eu.exadelpractice.registry.events.model.WorkerRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "Event")
public class EventEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private LocationRef locationRef;
    private List<WorkerRef> personRef;
}
