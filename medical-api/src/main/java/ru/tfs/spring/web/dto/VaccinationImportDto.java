package ru.tfs.spring.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/** Класс импорта данных о вакцинации */
@Getter
@Setter
public class VaccinationImportDto {
	/** Уникальный идентификатор вакцинации */
	@NotNull
	private String			uuid;

	/** Имя вакцинированного */
	@NotNull
	private String			firstName;

	/** Фамилия вакцинированного */
	@NotNull
	private String			lastName;

	/** Отчество вакцинированного */
	private String			patronymic;

	/** Номер пасспорта вакцинированного */
	@NotNull
	private Integer			passport;

	/** Дата вакцинации */
	@NotNull
	private LocalDateTime	date;

	/** Название вакцины */
	@NotNull
	private String			vaccine;

	/** Уникальный идентификатор пункта вакцинации */
	@NotNull
	private String			pointUUID;

	/** Название пункта вакцинации */
	@NotNull
	private String			point;

	/** Город пункта вакцинации */
	@NotNull
	private String			city;

	/** Адрес пункта вакцинации */
	@NotNull
	private String			address;
}
