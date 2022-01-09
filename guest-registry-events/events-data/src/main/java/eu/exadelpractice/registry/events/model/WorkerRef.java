package eu.exadelpractice.registry.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class WorkerRef {
	private String workerId;
	private String name;
	private String surname;
}
