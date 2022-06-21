package ru.tfs.commontypes.kafka;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/** DTO для отправки вакцинации в сервис генерации qr кодов */
@Data
public class VaccinationTransportDto {
	/** Уникальный идентификатор вакцинации */
	@NotBlank
	private String 				uuid;

	/** Дата вакцинации */
	@NotNull
	private LocalDateTime		date;

	/** Документ вакцинированного */
	@NotNull
	private Integer				document;
}
