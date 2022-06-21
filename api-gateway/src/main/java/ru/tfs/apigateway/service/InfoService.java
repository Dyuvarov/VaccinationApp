package ru.tfs.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.tfs.apigateway.client.MedicalReactiveFeignClient;
import ru.tfs.apigateway.client.PersonReactiveFeignClient;
import ru.tfs.apigateway.client.QrReactiveFeignClient;
import ru.tfs.apigateway.dto.PersonInfoDto;


@Service
@RequiredArgsConstructor
public class InfoService {
	private final PersonReactiveFeignClient		personClient;

	private final QrReactiveFeignClient			qrClient;

	private final MedicalReactiveFeignClient	medicalClient;

	/** Собирает информацию о вакцинированном по его паспорту из всех сервисов */
	public PersonInfoDto getPersonInfoByPassport(Integer passport) {
		PersonInfoDto personInfoDto = new PersonInfoDto();
		Mono<Object> personMono = personClient.personInfoByPassport(passport);
		Mono<Object> qrMono = qrClient.getNewestQrByDocument(passport);
		Mono<Object> vaccinationMono = medicalClient.vaccinationByDocument(passport);

		personInfoDto.setPersonInfo(personMono.toFuture().join());
		personInfoDto.setQrCode(qrMono.toFuture().join());
		personInfoDto.setVaccination(vaccinationMono.toFuture().join());
		return personInfoDto;
	}
}
