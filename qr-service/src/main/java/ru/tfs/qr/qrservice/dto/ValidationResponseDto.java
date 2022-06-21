package ru.tfs.qr.qrservice.dto;

import lombok.Getter;
import lombok.Setter;

/** DTO ответа на клиентские запросы валидации */
@Getter
@Setter
public class ValidationResponseDto {
	/** Признак того, валидация пройдена */
	private Boolean validated;

	/** Описание ошибки, если валидация не пройдена */
	private String message;
}
