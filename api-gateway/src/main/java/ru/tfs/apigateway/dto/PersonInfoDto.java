package ru.tfs.apigateway.dto;

import lombok.Getter;
import lombok.Setter;

/** Dto для агрегации информации о вакцинированном, собранной из всех сервисов */
@Getter
@Setter
public class PersonInfoDto {
	/** Краткая информация о вакцинированном */
	private Object	personInfo;

	/** QR-код о вакцинации */
	private Object	qrCode;

	/** Информация о вакцинации */
	private Object	vaccination;
}
