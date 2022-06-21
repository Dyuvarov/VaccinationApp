package ru.tfs.spring.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="t_vaccination_point")
@Getter
@Setter
public class VaccinationPoint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	id;

	private String	uuid;

	private String	name;

	private String	city;

	private String	address;
}
