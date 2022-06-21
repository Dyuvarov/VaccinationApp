package ru.tfs.qr.qrservice.dto;

import lombok.Data;

/** Dto QR кода для ответа на клиентский запрос */
@Data
public class QrCodeResponseDto {
	/** хэш MD5 вместо настоящего QR кода */
	private String	code;
}
