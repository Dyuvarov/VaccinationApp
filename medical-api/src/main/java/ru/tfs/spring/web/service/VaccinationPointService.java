package ru.tfs.spring.web.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.web.dto.VaccinationImportDto;
import ru.tfs.spring.web.entity.VaccinationPoint;
import ru.tfs.spring.web.repository.VaccinationPointRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VaccinationPointService {
	private final VaccinationPointRepository repository;

	@Transactional
	public VaccinationPoint create(VaccinationPoint point) {
		return repository.save(point);
	}

	@Transactional
	public VaccinationPoint createFromVaccinationImportDto(VaccinationImportDto dto) {
		VaccinationPoint vaccinationPoint = new VaccinationPoint();
		vaccinationPoint.setUuid(dto.getPointUUID());
		vaccinationPoint.setAddress(dto.getAddress());
		vaccinationPoint.setCity(dto.getCity());
		vaccinationPoint.setName(dto.getPoint());
		return create(vaccinationPoint);
	}

	@Transactional
	public Optional<VaccinationPoint> findByUuid(String uuid) {
		return repository.findVaccinationPointByUuid(uuid);
	}
}
