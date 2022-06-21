package ru.tfs.qr.qrservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;

import javax.validation.Valid;

/** Читает сообщения из kafka */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaVaccinationMessageProcessor {

	private final VaccinationInfoService vaccinationInfoService;

	@KafkaListener(topics = "${kafka.vaccination-topic}")
	public void processVaccination(@Valid VaccinationTransportDto vaccination) {
		log.info("Получено сообщение о вакцинации. Uuid: " + vaccination.getUuid());
		if (vaccinationInfoService.findByUuid(vaccination.getUuid()).isPresent()) {
			log.info("Информация о вакцинации уже сохранена. Uuid: " + vaccination.getUuid());
			return;
		}
		vaccinationInfoService.create(vaccination);
		log.info("Сщщбщение обработано успешно. Uuid: " + vaccination.getUuid());
	}
}
