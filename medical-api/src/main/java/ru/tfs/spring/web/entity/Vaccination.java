package ru.tfs.spring.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="t_vaccination")
@Getter
@Setter
public class Vaccination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long 				id;

	private String 				uuid;

	private LocalDateTime		date;

	@ManyToOne
	@JoinColumn(name="vaccine_id", referencedColumnName = "id")
	private Vaccine				vaccine;

	@ManyToOne
	@JoinColumn(name = "vaccination_point_id", referencedColumnName = "id")
	private VaccinationPoint	vaccinationPoint;

	@Column(name="first_name")
	private String				firstName;

	@Column(name="last_name")
	private String				lastName;

	private String				patronymic;

	private Integer				document;

	/** Дата отпарвки в kafka*/
	@Column(name="handle_date")
	private LocalDateTime		handleDate;
}
