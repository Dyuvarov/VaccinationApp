package ru.tfs.qr.qrservice.service.QrCode;

import org.mapstruct.Mapper;
import ru.tfs.qr.qrservice.dto.QrCodeResponseDto;
import ru.tfs.qr.qrservice.entity.QrCode;

@Mapper(componentModel = "spring")
public interface QrCodeMapper {
	QrCodeResponseDto qrCodeToQrCodeResponseDto(QrCode qrCode);
}
