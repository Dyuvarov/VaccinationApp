package ru.tfs.spring.web.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;
import ru.tfs.spring.web.entity.Vaccination;
import ru.tfs.spring.web.service.VaccinationService;

import java.time.LocalDateTime;
import java.util.List;

/**  Отправляет информацию о вакцинации в другие сервисы */
@Component
@RequiredArgsConstructor
@Slf4j
public class VaccinationSender {
	private final VaccinationService					vaccinationService;

	private final KafkaTemplate<String, VaccinationTransportDto>	kafkaProducer;

	@Value("${kafka.vaccination-topic}")
	private String	kafkaTopic;

	/** Отправить новые вакцинации в kafka */
	@Scheduled(fixedDelayString = "${kafka.send.delayMS}")
	public void sendVaccinations() {
		List<Vaccination> vaccinations = vaccinationService.getAllUnhandled();
		for(Vaccination vaccination : vaccinations) {
			var transportDto = vaccinationService.createTransportDto(vaccination);
			var record = new ProducerRecord<>(kafkaTopic, vaccination.getUuid(), transportDto);
			var future = kafkaProducer.send(record);

			future.addCallback(new ListenableFutureCallback<SendResult<String, VaccinationTransportDto>>() {
				@Override
				public void onFailure(Throwable ex) {
					log.error("Данные о вакцинации не отправлены в kafka. UUID: " + vaccination.getUuid(), ex);
				}

				@Override
				public void onSuccess(SendResult<String, VaccinationTransportDto> result) {
					log.info("Данные о вакцинации успешно отправлены в kafka. UUID: " + vaccination.getUuid());
					vaccination.setHandleDate(LocalDateTime.now());
					vaccinationService.createAndFlush(vaccination);
				}
			});
		}
	}
}
