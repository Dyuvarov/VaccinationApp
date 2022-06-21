package ru.tfs.qr.qrservice.service.QrCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;
import ru.tfs.qr.qrservice.dto.QrCodeResponseDto;
import ru.tfs.qr.qrservice.entity.QrCode;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
public class QrCodeService {
	private final MessageDigest md5Digest;

	private final QrCodeMapper	mapper;

	/** Создает QR код о вакцинации */
	public QrCode createQrCode(VaccinationTransportDto vaccination) {
		md5Digest.reset(); //сброс прошлого состояния

		md5Digest.update(vaccination.getUuid().getBytes(StandardCharsets.UTF_8));
		md5Digest.update(vaccination.getDocument().toString().getBytes(StandardCharsets.UTF_8));
		md5Digest.update(vaccination.getDate().toString().getBytes(StandardCharsets.UTF_8));

		return new QrCode(new String(md5Digest.digest()));
	}

	public QrCodeResponseDto createQrCodeResponseDto(QrCode qrCode) {
		return mapper.qrCodeToQrCodeResponseDto(qrCode);
	}
}
