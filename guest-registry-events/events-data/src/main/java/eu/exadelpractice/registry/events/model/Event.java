package eu.exadelpractice.registry.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event {
	private String id;
	private String name;
	private String description;
	private LocationRef locationRef;
	private List<WorkerRef> personRef;
}
