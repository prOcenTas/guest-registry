package eu.exadelpractice.registry.locations.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class WorkerRef {
	private String workerId;
	private String name;
	private String surname;
}
