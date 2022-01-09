package eu.exadelpractice.registry.person.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.exadelpractice.registry.person.model.WorkerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Worker")
public class WorkerEntity {
	@Id
	private String id;
	@DBRef(lazy = false)
	private PersonEntity person;
	private WorkerType workerType;
	private CardRefEntity card;
	private String company;
	private String department;
	private String position;
	private LocationRefEntity locationOfOffice;
	private LocalDate dateOfEmployment;
	private double monthlySalary;
}
