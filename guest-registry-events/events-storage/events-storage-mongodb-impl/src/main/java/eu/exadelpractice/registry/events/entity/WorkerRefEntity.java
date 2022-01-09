package eu.exadelpractice.registry.events.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class WorkerRefEntity {
    private String workerId;
    private String name;
    private String surname;
}
