package ru.tfs.qr.qrservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;
import ru.tfs.qr.qrservice.dto.QrCodeResponseDto;
import ru.tfs.qr.qrservice.dto.ValidationResponseDto;
import ru.tfs.qr.qrservice.entity.QrCode;
import ru.tfs.qr.qrservice.entity.VaccinationInfo;
import ru.tfs.qr.qrservice.repository.VaccinationInfoRepository;
import ru.tfs.qr.qrservice.service.QrCode.QrCodeService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VaccinationInfoService {
	private final VaccinationInfoRepository	repository;

	private final VaccinationInfoMapper		mapper;

	private final QrCodeService				qrCodeService;

	@Value("${vaccination.days-duration}")
	private Integer vaccinationDuration;

	@Transactional
	public VaccinationInfo create(VaccinationTransportDto vaccinationTransportDto) {
		QrCode qrCode = qrCodeService.createQrCode(vaccinationTransportDto);

		VaccinationInfo vaccinationInfo = mapper.vaccinationTransportDtoToVaccinationInfo(vaccinationTransportDto);
		vaccinationInfo.setQrCode(qrCode);

		return repository.save(vaccinationInfo);
	}

	@Transactional(readOnly = true)
	public Optional<VaccinationInfo> findByUuid(String uuid) {
		return repository.findVaccinationInfoByUuid(uuid);
	}

	@Transactional(readOnly = true)
	public QrCodeResponseDto findNewestQrByDocument(Integer document) {
		VaccinationInfo vaccinationInfo = findTopByDocument(document)
				.orElseThrow(() -> new EntityNotFoundException("Не найдена информация о вакцинации по документу: " + document));

		return qrCodeService.createQrCodeResponseDto(vaccinationInfo.getQrCode());
	}

	@Transactional(readOnly = true)
	public ValidationResponseDto validateQrCode(String code) {
		ValidationResponseDto responseDto = new ValidationResponseDto();
		responseDto.setValidated(false);

		VaccinationInfo vaccinationInfo = repository.findVaccinationInfoByQrCodeCode(code).orElse(null);
		if (vaccinationInfo == null) {
			responseDto.setMessage("QR-код не найден");
		} else if (!checkDuration(vaccinationInfo.getDate())) {
			responseDto.setMessage("Срок действия QR-кода истек");
		} else {
			responseDto.setValidated(true);
		}

		return responseDto;
	}

	private boolean checkDuration(LocalDateTime vaccinationDate) {
		return ChronoUnit.DAYS.between(vaccinationDate, LocalDateTime.now()) <= vaccinationDuration.longValue();
	}

	private Optional<VaccinationInfo> findTopByDocument(Integer document) {
		return repository.findTopByDocumentOrderByDateDesc(document);
	}
}
