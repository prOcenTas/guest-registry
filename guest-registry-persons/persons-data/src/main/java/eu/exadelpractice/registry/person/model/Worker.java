package eu.exadelpractice.registry.person.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker {
	private String id;
	private Person person;
	private WorkerType workerType;
	private CardRef card;
	private String company;
	private String department;
	private String position;
	private LocationRef locationOfOffice;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateOfEmployment;
	private double monthlySalary;
}
