package ru.tfs.spring.web.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;
import ru.tfs.spring.web.dto.VaccinationImportDto;
import ru.tfs.spring.web.entity.Vaccination;
import ru.tfs.spring.web.entity.VaccinationPoint;
import ru.tfs.spring.web.entity.Vaccine;
import ru.tfs.spring.web.repository.VaccinationRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class VaccinationService {
	private final VaccinationRepository repository;

	private final VaccinationMapper		mapper;

	private final EntityManager			entityManager;

	@Transactional
	public Vaccination create(Vaccination vaccination) {
		return repository.save(vaccination);
	}

	/** Сохраняет сущность с неотложной записью в бд */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Vaccination createAndFlush(Vaccination vaccination) {
		vaccination = repository.save(vaccination);
		entityManager.flush();
		return vaccination;
	}

	@Transactional
	public Vaccination createFromVaccinationImportDto(VaccinationImportDto dto, Vaccine vaccine, VaccinationPoint point) {
		Vaccination vaccination = new Vaccination();
		vaccination.setUuid(dto.getUuid());
		vaccination.setDate(dto.getDate());
		vaccination.setVaccine(vaccine);
		vaccination.setVaccinationPoint(point);
		vaccination.setFirstName(dto.getFirstName());
		vaccination.setLastName(dto.getLastName());
		vaccination.setPatronymic(dto.getPatronymic());
		vaccination.setDocument(dto.getPassport());
		return create(vaccination);
	}

	@Transactional(readOnly = true)
	public Vaccination getVaccinationByDocument(Integer documentNumber) {
		return repository.findTopByDocumentOrderByDate(documentNumber)
				.orElseThrow(() -> new EntityNotFoundException("Не найдена информация о вакцинации по документу: " + documentNumber));
	}

	/** Получить все вакцинации, которые не были отправлены в kafka */
	@Transactional(readOnly = true)
	public List<Vaccination> getAllUnhandled() {
		return repository.findVaccinationByHandleDateIsNull();
	}

	public VaccinationTransportDto createTransportDto(Vaccination vaccination) {
		return mapper.vaccinationToTransportDto(vaccination);
	}
}
