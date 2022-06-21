package ru.tfs.qr.qrservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_vaccination_info")
@Getter
@Setter
public class VaccinationInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long			id;

	/** Уникальный идентификатор вакцинации */
	private String			uuid;

	/** Дата вакцинации */
	private LocalDateTime	date;

	/** Документ вакцинированного */
	private Integer			document;

	/** QR код */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "qr_id", referencedColumnName = "id")
	private QrCode			qrCode;
}
